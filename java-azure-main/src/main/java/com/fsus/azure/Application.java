package com.fsus.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;

/**
 * bootstraps spring boot application - 1
 *
 */
@EnableAutoConfiguration
@ComponentScan({"com.fsus.azure.*"})
@SpringBootApplication
public class Application {
	
  private static String INSTRUMENTATION_KEY = "d9ad457c-5139-4b65-97e4-d2f5b97e1364";
	
  public static void main(final String[] args) {
    System.out.println("Spring boot app");
    SpringApplication.run(Application.class, args);
  }

  /**
   * create bean for <code>RestTemplate</code>
   * 
   * @return
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  @Bean
  public String telemetryConfig() {
      
      if (INSTRUMENTATION_KEY != null) {
          TelemetryConfiguration.getActive().setInstrumentationKey(INSTRUMENTATION_KEY);
      }
      
      System.out.println("********************************************");
      System.out.println(" INSTRUMENTATION key is: " + INSTRUMENTATION_KEY);
      System.out.println("********************************************");
      return INSTRUMENTATION_KEY;
  }

  /**
   * Programmatically registers a FilterRegistrationBean to register WebRequestTrackingFilter
   * @param webRequestTrackingFilter
   * @return Bean of type {@link FilterRegistrationBean}
   */
  @Bean
  public FilterRegistrationBean webRequestTrackingFilterRegistrationBean() {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(new WebRequestTrackingFilter("application"));
      registration.addUrlPatterns("/*");
      registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
      
      System.out.println("********************************************");
      System.out.println(" Registration.isEnabled() : " + registration.isEnabled());
      System.out.println("********************************************");
      
      return registration;
  }


}
