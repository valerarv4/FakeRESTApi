package net.azurewebsites.fakerestapi.core.restassuredclient;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestClientWrapper {

    private final RequestSpecification client;

    public RestClientWrapper(RequestSpecification client) {
        this.client = client;
    }

    public HttpResponseWrapper get(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.get(path, pathParams).then());
    }

    public HttpResponseWrapper post(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.post(path, pathParams).then());
    }

    public HttpResponseWrapper put(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.put(path, pathParams).then());
    }

    public HttpResponseWrapper patch(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.patch(path, pathParams).then());
    }

    public HttpResponseWrapper delete(String path, Object... pathParams) {
        return new HttpResponseWrapper(client.delete(path, pathParams).then());
    }

    public RestClientWrapper body(Object body) {
        client.body(body);

        return this;
    }

    public RestClientWrapper addRequestParams(Map<String, Integer> params) {
        client.params(params);

        return this;
    }

    public RestClientWrapper pathParam(String parameterName, Object parameterValue) {
        client.pathParam(parameterName, parameterValue);

        return this;
    }

    public RestClientWrapper header(String headerName, Object headerValue, Object... pathParams) {
        client.header(headerName, headerValue, pathParams);

        return this;
    }

    public RestClientWrapper queryParam(String parameterName, Object... parameterValues) {
        client.queryParam(parameterName, parameterValues);

        return this;
    }
}
