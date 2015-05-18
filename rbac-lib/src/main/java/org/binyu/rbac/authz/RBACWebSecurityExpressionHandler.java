/********************************************************************
 * File Name:    RBACWebSecurityExpressionHandler.java
 *
 * Date Created: Mar 8, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.authz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

/**
 * Customized ExpressionHandler to parse SPEL expression in access("")
 *
 */
@Component
public class RBACWebSecurityExpressionHandler extends
		DefaultWebSecurityExpressionHandler {
	// CONSTANTS ------------------------------------------------------

	// CLASS VARIABLES ------------------------------------------------

	// INSTANCE VARIABLES ---------------------------------------------
	@Autowired
	PermissionHandler permissionHandler;

	// CONSTRUCTORS ---------------------------------------------------

	// PUBLIC METHODS -------------------------------------------------

	// PROTECTED METHODS ----------------------------------------------
	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(
			Authentication authentication, FilterInvocation invocation) {
		StandardEvaluationContext ec = super.createEvaluationContextInternal(
				authentication, invocation);
		ec.setVariable("rbac", new RBACSecurityExpressionMethods(
				permissionHandler, authentication, invocation));
		return ec;
	}
	// PRIVATE METHODS ------------------------------------------------

	// ACCESSOR METHODS -----------------------------------------------

}
