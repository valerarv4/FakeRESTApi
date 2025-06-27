package net.azurewebsites.fakerestapi.utils.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static org.testng.xml.XmlSuite.ParallelMode.METHODS;

@Slf4j
public class ThreadCountListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        var threads = getProperty("threadCount", "3");

        if (threads.matches("\\d+")) {
            suite
                    .getXmlSuite()
                    .setParallel(METHODS);
            suite
                    .getXmlSuite()
                    .setThreadCount(parseInt(threads));
            log.info("TestNG: Parallel set as METHODS");
            log.info("TestNG: Thread count set as {}", threads);
        } else {
            throw new RuntimeException("No valid 'threadCount' was provided");
        }
    }
}
