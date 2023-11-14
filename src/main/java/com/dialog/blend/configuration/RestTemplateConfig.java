package com.dialog.blend.configuration;

/**
 * RestTemplateConfig is a singleton class responsible for configuring and providing a RestTemplate instance
 * with a fixed connection pool to manage HTTP connections efficiently.
 * 
* @author Ashutosh Pandey
 */
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {

  private static RestTemplateConfig instance;

  private RestTemplateConfig() {
  }

  private RestTemplate restTemplate;

  /**
   * Returns the singleton instance of RestTemplateConfig.
   */
  public static RestTemplateConfig loadRestTemplate() {
    if (instance == null)
      instance = new RestTemplateConfig();
    return instance;
  }

  /**
   * Retrieves the fixed RestTemplate instance with a connection pool.
   */
  public RestTemplate getFixedRestTemplate() {
    if (restTemplate == null)
      restTemplate = getRestTemplate();
    return restTemplate;
  }

  /**
   * Creates and configures a new RestTemplate instance with a custom HttpClient
   * that utilizes a connection pool for improved connection management.
   */
  public RestTemplate getRestTemplate() {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    // Set the maximum total connections
    connectionManager.setMaxTotal(100);

    // Set the maximum connections per route (per target host)
    // connectionManager.setDefaultMaxPerRoute(20);

    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(5000);
    factory.setReadTimeout(5000);

    return new RestTemplate(factory);
  }

}
