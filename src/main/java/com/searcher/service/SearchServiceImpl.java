package com.searcher.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searcher.domain.ResultDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marko Štefančić, AG04 on 23/09/16.
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String searchForProducts(final String p_keyword) {

        final URI uri;
        String jsonResponse = "";

        try {
            uri = UriBuilder.fromUri("http://gw.api.alibaba.com/openapi/param2/2/portals.open/api.listPromotionProduct/29707?fields=commission,productId,productTitle,originalPrice,salePrice&sort=orignalPriceUp&keywords=" + URLEncoder
                    .encode(p_keyword, "UTF-8")).build();

            jsonResponse = restTemplate.getForObject(uri,String.class);

        } catch (UnsupportedEncodingException p_e) {
            jsonResponse = "GET not succesfull";
        } catch (HttpStatusCodeException e) {
            jsonResponse = "Get FAILED with HttpStatusCode: " + e.getStatusCode() + "|" + e.getStatusText();
        } catch (RuntimeException e) {
            jsonResponse = "Get FAILED";
        }

        return jsonResponse;
    }

    public ResultDto parseResponse(final String jsonResponse) {
        ResultDto resultDto = new ResultDto();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ResultDto> mapType = new TypeReference<ResultDto>() {};

        try {
            resultDto = mapper.readValue(jsonResponse, mapType);
        } catch (IOException p_e) {
            p_e.printStackTrace();
            resultDto.setError(true);
        }
        return resultDto;
    }
}
