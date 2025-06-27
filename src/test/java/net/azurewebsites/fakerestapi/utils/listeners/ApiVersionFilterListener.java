package net.azurewebsites.fakerestapi.utils.listeners;

import net.azurewebsites.fakerestapi.config.EnvironmentConfig;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

public class ApiVersionFilterListener implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return methods
                .stream()
                .filter(method -> {
                    var realMethod = method
                            .getMethod()
                            .getConstructorOrMethod()
                            .getMethod();
                    var annotation = realMethod.getAnnotation(ApiVersion.class);
                    if (isNull(annotation)) {
                        return true;
                    }

                    return stream(annotation.value())
                            .anyMatch(version -> version == EnvironmentConfig.getInstance().getAppVersion());
                })
                .toList();
    }
}
