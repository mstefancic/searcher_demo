package com.searcher.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Marko Štefančić, AG04 on 24/09/16.
 */
public class ResultDto {

    private Result result;

    private String errorCode;

    @JsonIgnore
    private boolean error;

    public Result getResult() {
        return result;
    }

    public void setResult(final Result p_result) {
        result = p_result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String p_errorCode) {
        errorCode = p_errorCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(final boolean p_error) {
        error = p_error;
    }
}
