package com.searcher.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Marko Štefančić, AG04 on 24/09/16.
 */
public class Result {

    private Integer totalResults;

    @JsonProperty("products")
    private List<Product> productList;

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final Integer p_totalResults) {
        totalResults = p_totalResults;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(final List<Product> p_productList) {
        productList = p_productList;
    }
}
