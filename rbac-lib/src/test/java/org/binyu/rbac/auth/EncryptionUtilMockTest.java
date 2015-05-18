/********************************************************************
 * File Name:    EncryptionUtilMockTest.java
 *
 * Date Created: Mar 16, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import java.lang.reflect.Method;

import org.binyu.rbac.auth.EncryptionUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test EncryptionUtil methods
 */
public class EncryptionUtilMockTest {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	private EncryptionUtil util = new EncryptionUtil();

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------
	@Test(dataProvider = "hmac_test_data")
	public void testEncryptHMAC(String origin, String badOrigin) {
		String encrypted = util.encryptHMAC(origin);
		Assert.assertNotNull(encrypted);
		Assert.assertTrue(encrypted.length() >= 16);
		Assert.assertTrue(util.matchesHMAC(origin, encrypted));
		Assert.assertFalse(util.matchesHMAC(badOrigin, encrypted));
	}

	// PROTECTED METHODS ----------------------------------------------

	// PRIVATE METHODS ------------------------------------------------
	@DataProvider(name = "hmac_test_data")
	public Object[][] createDataNormal(Method method) {

		return new Object[][] { new Object[] { "test", "badPwd" },
				new Object[] { "test1", "" }, new Object[] { "test2", null },
				new Object[] { "", "badPwd" }, new Object[] { null, "badPwd" }, };
	}
	// ACCESSOR METHODS -----------------------------------------------

}
