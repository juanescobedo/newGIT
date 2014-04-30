package com.hcl.usaaa.timesheet.security.testauto;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication{

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		 return Login.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SignInSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Login.class;
	}    

}
