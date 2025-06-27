package net.azurewebsites.fakerestapi.core.fakerestapi.clients;

import net.azurewebsites.fakerestapi.config.EnvironmentConfig;
import net.azurewebsites.fakerestapi.core.restassuredclient.RestClientFactory;
import net.azurewebsites.fakerestapi.core.restassuredclient.RestClientWrapper;

abstract class AbstractClient {

    private static final EnvironmentConfig environmentConfig = EnvironmentConfig.getInstance();

    public RestClientWrapper getClient() {
        return RestClientFactory.getClient(environmentConfig.getAppUrl(), environmentConfig.getAppVersion());
    }

}
