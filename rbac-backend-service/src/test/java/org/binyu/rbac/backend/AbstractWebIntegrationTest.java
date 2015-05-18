/**
 * 
 */
package org.binyu.rbac.backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.binyu.rbac.auth.dtos.ExternalAuthenticationObject;
import org.binyu.rbac.auth.dtos.ExternalUserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

/**
 * @author Administrator
 *
 */
@SpringApplicationConfiguration(classes = { RootConfiguration.class })
@ActiveProfiles({ "web-integration" })
@WebIntegrationTest(randomPort = true)
public class AbstractWebIntegrationTest extends
    AbstractTestNGSpringContextTests
{

  protected static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
  @Value("${local.server.port}")
  protected int port;
  protected RestTemplate restTemplate;

  @BeforeMethod
  public void setup()
  {
    restTemplate = createRestTemplate();
  }

  protected String constructFullURL(String relativePath)
      throws MalformedURLException
  {
    return new URL("http", "localhost", port, relativePath).toString();
  }

  /**
   * @param pwd
   * @param user
   * @return
   */
  protected HttpEntity<ExternalAuthenticationObject> constructLoginRequest(
      String user, String pwd)
  {
    ExternalAuthenticationObject body = new ExternalAuthenticationObject(
        user, pwd);
    HttpEntity<ExternalAuthenticationObject> requestEntity = new HttpEntity<ExternalAuthenticationObject>(
        body);
    return requestEntity;
  }

  /**
   * Call this method to do login, it will return HttpHeaders for you to
   * proceed requests within the same session. the HttpHeaders will have both
   * session id and csrf token set.
   * 
   * @return
   * @throws MalformedURLException
   */
  protected HttpHeaders doLogin(String user, String pwd)
      throws MalformedURLException
  {
    // do login
    ResponseEntity<ExternalUserProfile> resp = restTemplate.exchange(
        constructFullURL("/login"), HttpMethod.POST,
        constructLoginRequest(user, pwd), ExternalUserProfile.class);
    Assert.assertEquals(resp.getStatusCode(), HttpStatus.OK);
    ExternalUserProfile userProfile = resp.getBody();
    Assert.assertNotNull(userProfile);
    List<String> tokenl = resp.getHeaders().get(DEFAULT_CSRF_HEADER_NAME);
    Assert.assertTrue(tokenl != null && tokenl.size() > 0);
    String token = tokenl.get(0);
    List<String> setCookie = resp.getHeaders().get(HttpHeaders.SET_COOKIE);
    Assert.assertTrue(setCookie != null && setCookie.size() > 0);
    String[] cookies = setCookie.get(0).split(";");
    Assert.assertTrue(cookies.length > 0);
    String sessionKV = cookies[0];
    Assert.assertTrue(sessionKV.startsWith("JSESSIONID"));

    // add sessionId and csrf token to http header
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.COOKIE, sessionKV);
    headers.add(DEFAULT_CSRF_HEADER_NAME, token);
    return headers;
  }

  protected RestTemplate createRestTemplate()
  {
    HttpClient httpClient = HttpClients.custom().build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
        httpClient);
    RestTemplate template = new RestTemplate(requestFactory);
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(
        1);
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    // messageConverters.add(new StringHttpMessageConverter());
    template.setMessageConverters(messageConverters);
    template.setErrorHandler(new ResponseErrorHandler()
    {

      @Override
      public boolean hasError(ClientHttpResponse response)
          throws IOException
      {
        // let the business method to handle the error code.
        return false;
      }

      @Override
      public void handleError(ClientHttpResponse response)
          throws IOException
      {
      }

    });
    return template;
  }
}
