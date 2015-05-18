/**
 * 
 */
package org.binyu.rbac.example.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */

@RestController
public class TestController {
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	public String sayHello(@PathVariable String name) {
		return "hello," + name;
	}
}
