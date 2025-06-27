package net.azurewebsites.fakerestapi.core.restassuredclient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RestClientFactory {

    static {
        useRelaxedHTTPSValidation();
    }

    private static RequestSpecification buildBaseSpec(String baseUri) {
        return given()
                .baseUri(baseUri)
                .filters(
                        new AllureRestAssured(),
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter()
                )
                .contentType(ContentType.JSON);
    }

    public static RestClientWrapper getClient(String baseUri) {
        return new RestClientWrapper(buildBaseSpec(baseUri));
    }

    public static RestClientWrapper getClient(String baseUri, int version) {
        var reqSpec = buildBaseSpec(baseUri).basePath("/v" + version);

        return new RestClientWrapper(reqSpec);
    }
}
