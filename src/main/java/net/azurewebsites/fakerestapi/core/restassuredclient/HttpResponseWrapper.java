package net.azurewebsites.fakerestapi.core.restassuredclient;

import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import net.azurewebsites.fakerestapi.exceptions.HttpException;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;

public record HttpResponseWrapper(@Getter ValidatableResponse response) {

    private static final String DEFAULT_STATUS_CODE_ERROR = "\nExpecting %s status code but was %s!";

    public HttpResponseWrapper expectStatusCode(String errorMessage, int statusCode) {
        validateResponseCode(errorMessage, statusCode);

        return this;
    }

    public HttpResponseWrapper expectStatusCode(int statusCode) {
        validateResponseCode("Response returns inappropriate status code", statusCode);

        return this;
    }

    public HttpResponseWrapper statusCode(int statusCode) {
        response.statusCode(statusCode);

        return this;
    }

    public String getBodyAsString() {
        return response
                .extract()
                .body()
                .asString();
    }

    public int getBodyAsInteger() {
        return response
                .extract()
                .body()
                .as(Integer.class);
    }

    public boolean getBodyAsBoolean() {
        return response
                .extract()
                .body()
                .as(Boolean.class);
    }

    public String getBodyAsString(String jsonPath) {
        return response
                .extract()
                .jsonPath()
                .getString(jsonPath);
    }

    public String getBodyAsPrettyString() {
        return response
                .extract()
                .asPrettyString();
    }

    public <S> S getBodyAs(Class<S> returnType) {
        return response
                .extract()
                .body()
                .as(returnType);
    }

    public <S> List<S> getBodyAsList(Class<S> returnType) {
        return response
                .extract()
                .jsonPath()
                .getList(".", returnType);
    }

    public <S> List<S> getBodyAsList(String jsonPath, Class<S> returnType) {
        return response
                .extract()
                .jsonPath()
                .getList(jsonPath, returnType);
    }

    public int getResponseCode() {
        return response.extract().statusCode();
    }

    public String getHeader(String header) {
        return response.extract().header(header);
    }

    private void validateResponseCode(String errorMessage, int expectedStatusCode) {
        try {
            response.statusCode(equalTo(expectedStatusCode));
        } catch (AssertionError error) {
            throw new HttpException(errorMessage + format(DEFAULT_STATUS_CODE_ERROR, expectedStatusCode, getResponseCode()), error);
        }
    }
}