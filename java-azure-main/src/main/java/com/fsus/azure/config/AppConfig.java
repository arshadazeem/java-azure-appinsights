package com.fsus.azure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Config to hold environment specific configuration properties
 *
 * @author arshad.azeem
 *
 */
@Component
@Configuration
public class AppConfig {

	/* tomcat port */
	@Value("${server.port:8080}")
	private int serverPort;

	/* App Insights InstrumentationKey */
	@Value("${applicaton.insights.instrumentation.key:<ADD_KEY_TO_PROP_FILE>")
	private String appInsightsInstrumentationKey;

	public int getServerPort() {
		return this.serverPort;
	}

	public String getAppInsightsInstrumentationKey() {
		return appInsightsInstrumentationKey;
	}

}
