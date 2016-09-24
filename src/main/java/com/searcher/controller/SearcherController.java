package com.searcher.controller;

import com.searcher.domain.Product;
import com.searcher.domain.Result;
import com.searcher.domain.ResultDto;
import com.searcher.service.SearchService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Marko Štefančić, AG04 on 23/09/16.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class SearcherController {

    private static final String MODEL_ATTRIBUTE_KEYWORD = "keyword";
    private static final String MODEL_ATTRIBUTE_ERROR = "error";
    private static final String MODEL_ATTRIBUTE_RESULTS = "results";
    private static final String INDEX_PAGE = "index";

    @Autowired
    private transient SearchService searchService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(final ModelMap p_modelMap) {
        return INDEX_PAGE;
    }

    @RequestMapping(value = "/do-search", method = RequestMethod.POST)
    public String search(@RequestParam(MODEL_ATTRIBUTE_KEYWORD) final String p_keyword, final ModelMap p_modelMap) {

        final String jsonResponse = searchService.searchForProducts(p_keyword);
        final ResultDto resultDto = searchService.parseResponse(jsonResponse);
        if (resultDto.isError()) {
            p_modelMap.addAttribute(MODEL_ATTRIBUTE_ERROR, "Error in processing request!");
        } else {
            final Result result = resultDto.getResult();
            p_modelMap.addAttribute(MODEL_ATTRIBUTE_RESULTS, result != null ? result.getProductList(): new ArrayList<Product>());
        }
        p_modelMap.addAttribute(MODEL_ATTRIBUTE_KEYWORD, p_keyword);
        return INDEX_PAGE;
    }
}
