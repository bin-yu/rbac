/**
 * 
 */
package org.binyu.rbac.backend.controllers;

import java.util.Map;

import org.binyu.rbac.backend.AbstractWebIntegrationTest;
import org.binyu.rbac.backend.controllers.dtos.ExtResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Administrator
 *
 */
public class ResourceControllerIntegrationTest extends AbstractWebIntegrationTest
{
  private static final String ADMIN_USER = "admin";
  private static final String ADMIN_PWD = "admin";
  private static final String TEST_USER = "test";
  private static final String TEST_PWD = "test";

  /**
   * When send request to GET /resources without auth;
   * Then the response should have unauthorized error code.
   * @throws Exception
   */
  @Test
  public void testGetAllResourceNamesWithoutSession() throws Exception
  {
    ResponseEntity<Map> resources = restTemplate.exchange(
        constructFullURL("/resources"), HttpMethod.GET, null,
        Map.class);
    Assert.assertEquals(resources.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  /**
   * When send request to GET /resources without wrong session id;
   * Then the response should have unauthorized error code.
   * @throws Exception
   */
  @Test
  public void testGetAllResourceNamesWithInvalidSession() throws Exception
  {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.COOKIE, "JSESSIONID=0753485577CB67AFE93644753C3BF1C1");
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
    ResponseEntity<Map> resources = restTemplate.exchange(
        constructFullURL("/resources"), HttpMethod.GET, requestEntity,
        Map.class);
    Assert.assertEquals(resources.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Given user "admin" with GET permission on resource "resources"
   * When send request to GET /resources with "admin" user's auth but with invalid csrf token
   * Then the response should have FORBIDDEN error code.
   * @throws Exception
   */
  /*@Test
  public void testGetAllResourceNamesWithInvalidCSRF() throws Exception
  {
    HttpHeaders headers = doLogin(ADMIN_USER, ADMIN_PWD);
    headers.set("X-CSRF-TOKEN", "c2c54504-3bfe-4cdd-99db-1fce2e3639b7");
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
    ResponseEntity<Map> resources = restTemplate.exchange(
        constructFullURL("/resources"), HttpMethod.GET, requestEntity,
        Map.class);
    Assert.assertEquals(resources.getStatusCode(), HttpStatus.FORBIDDEN);
  }*/

  /**
   * Given user "test" without permission on resource "resources";
   * When send request to GET /resources with "test" user's auth;
   * Then the response should have forbidden error code.
   * @throws Exception
   */
  @Test
  public void testGetAllResourceNamesWithNoPermission() throws Exception
  {
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(doLogin(
        TEST_USER, TEST_PWD));
    ResponseEntity<Map> resources = restTemplate.exchange(
        constructFullURL("/resources"), HttpMethod.GET, requestEntity,
        Map.class);
    Assert.assertEquals(resources.getStatusCode(), HttpStatus.FORBIDDEN);
  }

  /**
   * Given user "admin" with GET permission on resource "resources"
   * When send request to GET /resources with "admin" user's auth
   * Then the response should return the resource list
   * 
   * @throws Exception
   */
  @Test
  public void testGetAllResourceNamesWithPermission() throws Exception
  {
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(doLogin(
        ADMIN_USER, ADMIN_PWD));
    ResponseEntity<ExtResource[]> resources = restTemplate.exchange(
        constructFullURL("/resources"), HttpMethod.GET, requestEntity,
        ExtResource[].class);
    Assert.assertEquals(resources.getStatusCode(), HttpStatus.OK);
    Assert.assertTrue(resources.getBody().length > 0);
  }
}
