package com.searcher.service;

import com.searcher.domain.ResultDto;

/**
 * @author Marko Štefančić, AG04 on 23/09/16.
 */
public interface SearchService {

    String searchForProducts(String keyword);

    ResultDto parseResponse(final String jsonResponse);
}
