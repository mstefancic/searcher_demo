package com.searcher;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.searcher.service.SearchService;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SearcherApplicationTests extends AbstractJUnit4SpringContextTests {

    public static final String ALIBABA_API_LINK = "http://gw.api.alibaba.com/openapi/param2/2/portals.open/api.listPromotionProduct/29707?fields=commission,productId,productTitle,originalPrice,salePrice&sort=orignalPriceUp&keywords=";

    @Autowired
    private SearchService searchService ;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private InputStream inputStream;

    private String jsonTxt ="";

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetMessage() {
        mockServer.expect(requestTo(ALIBABA_API_LINK + "samsung")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

        String jsonResponse = searchService.searchForProducts("samsung");

        mockServer.verify();
        assertThat(jsonResponse, allOf(containsString("resultSuccess")));
    }

    @Test
    public void testGetMessage_500() {
        mockServer.expect(requestTo(ALIBABA_API_LINK + "samsung")).andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        String jsonResponse = searchService.searchForProducts("samsung");

        mockServer.verify();
        assertThat(jsonResponse, allOf(containsString("FAILED"), containsString("500")));
    }

    @Test
    public void testGetMessage_404() {
        mockServer.expect(requestTo(ALIBABA_API_LINK + "samsung")).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        String jsonResponse = searchService.searchForProducts("samsung");

        mockServer.verify();
        assertThat(jsonResponse, allOf(containsString("FAILED"), containsString("404")));
    }

    @Test
    public void testReturnProductsforKeyword_Samsung_succesfully() {

        inputStream = Test.class.getResourceAsStream("/static/json_files/samsung_result.json");
        try {
            jsonTxt = IOUtils.toString( inputStream );
        } catch (IOException p_e) {
            p_e.printStackTrace();
        }

        mockServer.expect(requestTo(ALIBABA_API_LINK + "samsung")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonTxt, MediaType.APPLICATION_JSON));

        String jsonResponse = searchService.searchForProducts("samsung");

        mockServer.verify();
        assertThat(jsonResponse, allOf(containsString("\"totalResults\": 1384550")));
    }

    @Test
    public void testReturnProductsforKeyword_1112223334444_succesfully() {

        inputStream = Test.class.getResourceAsStream("/static/json_files/empty_result.json");
        try {
            jsonTxt = IOUtils.toString( inputStream );
        } catch (IOException p_e) {
            p_e.printStackTrace();
        }

        mockServer.expect(requestTo(ALIBABA_API_LINK + "1112223334444")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(jsonTxt,MediaType.APPLICATION_JSON));

        String jsonResponse = searchService.searchForProducts("1112223334444");

        mockServer.verify();
        assertThat(jsonResponse, allOf(containsString("totalResults\":0")));
    }

}
