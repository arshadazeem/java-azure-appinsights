package com.fsus.azure.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fsus.azure.common.util.RestClient;
import com.google.common.base.Strings;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import com.microsoft.applicationinsights.web.internal.RequestTelemetryContext;
import com.microsoft.applicationinsights.web.internal.ThreadContext;
import com.microsoft.applicationinsights.web.internal.correlation.TelemetryCorrelationUtils;

/**
 * Controller to perform generic operations
 * 
 * @author arshad.azeem
 *
 */
@RestController
public class GeneralOperationsController {
	
	  @Autowired
	  private RestClient restClient;
	  
	  private static Double NUM_CALLS = 0.0;

	  /**
	   * Lists request headers
	   * @param headers
	   * @return
	   */
	  @ResponseStatus(OK)
	  @RequestMapping(value = "/general/listHeaders", method = GET, produces = APPLICATION_JSON_VALUE)
	  public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers) {
		  StringBuilder sb = new StringBuilder();
		  
		  sb.append("Request Headers from the Service:\n");
		  sb.append("---------------------------------------------------------------------------");
		  
	      headers.forEach((key, value) -> {
	    	  sb.append("key: " + key + ", value: " + value);
	    	  sb.append("\n");
	          System.out.println(String.format("Header '%s' = %s", key, value));
	      });
	   
	      return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	  }
	  
	  
	  @ResponseStatus(OK)
	  @RequestMapping(value = "/general/content", method = GET, produces = APPLICATION_JSON_VALUE)
	  public String getDummyContent(RequestEntity<String> requestEntity) {
		  
		  RequestTelemetryContext context = ThreadContext.getRequestTelemetryContext();
	      RequestTelemetry requestTelemetry = context.getHttpRequestTelemetry();
		  
		  String dependencyId = "Dependency-Id-" + TelemetryCorrelationUtils.generateChildDependencyId();
	      RemoteDependencyTelemetry dependencyTelemetry = new RemoteDependencyTelemetry("dependency-getcontent-svc");
	      
	      // Set Correlation Info - Id, OperationId & OperationParentId
	      dependencyTelemetry.setId(dependencyId);
	      dependencyTelemetry.getContext().getOperation().setId(
	          requestTelemetry.getContext().getOperation().getId()
	      );
	      dependencyTelemetry.getContext().getOperation().setParentId(
	    		  requestTelemetry.getContext().getOperation().getParentId()
	      );
	      
	      // Duration - is this needed?
	      Duration duration = new Duration(0,0,0,0,10); 
	      dependencyTelemetry.setDuration(duration);
	      
	      // add custom properties
	      dependencyTelemetry.getProperties().put("myCustomProp1", "myVal1");
	      dependencyTelemetry.getProperties().put("myCustomProp2", "myVal2");
	      dependencyTelemetry.getProperties().put("myCustomProp3", "myVal3");

	      TelemetryConfiguration configuration = TelemetryConfiguration.getActive();
	      
	      TelemetryClient telemetryClient = new TelemetryClient(configuration);

	      double randomSuffix = Math.random();
	      
	      // Trace
	      telemetryClient.trackTrace("Mock Trace - " + randomSuffix);
	      telemetryClient.trackTrace("Mock Trace - " + randomSuffix, SeverityLevel.Information);
	      
	      Map<String, String> eventMap = new HashMap<String, String>();
	      
	      eventMap.put("eventName", "availability service call");
	      eventMap.put("eventNextHop", "internal content service");
	      
	      
	      Map<String, Double> metricsMap = new HashMap<String, Double>();
	      NUM_CALLS++;
	      metricsMap.put("numOfCallsPerServerInstance", NUM_CALLS);
	      
	      telemetryClient.trackEvent("getcontent-from-avail", eventMap, metricsMap);
	      telemetryClient.trackMetric("myTrackedMetric", NUM_CALLS);
	     // telemetryClient.trackHttpRequest(name, timestamp, duration, responseCode, success);
	      
	      telemetryClient.trackDependency(dependencyTelemetry);
		  
		  
	    ResponseEntity<String> content = restClient.getRestTemplate().exchange(buildServiceUrl("success-msg"), HttpMethod.GET, null, String.class);

	    String response = StringUtils.EMPTY;
	    if(content != null && StringUtils.isNotBlank(content.getBody()))
	    {
	    	response = "General Content Service Response: " + content.getBody();
	    }
	    	
	    return response;

	  }

	
	/**
	 * Builds the service url based on endpoint
	 * @param endpoint
	 * @return
	 */
	private final String buildServiceUrl(final String endpoint) {

		String port = System.getProperty("server.port");

		if (Strings.isNullOrEmpty(port)) {
			port = "8080";
		}

		final String url = "http://localhost:" + port + "/general/" + endpoint;
		return url;
	}

}
