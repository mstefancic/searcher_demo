package com.searcher.domain;

/**
 * @author Marko Štefančić, AG04 on 23/09/16.
 */
public class Product {

    private String originalPrice;

    private String productTitle;

    private String salePrice;

    private Long productId;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(final String p_originalPrice) {
        originalPrice = p_originalPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(final String p_productTitle) {
        productTitle = p_productTitle;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(final String p_salePrice) {
        salePrice = p_salePrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long p_productId) {
        productId = p_productId;
    }
}
