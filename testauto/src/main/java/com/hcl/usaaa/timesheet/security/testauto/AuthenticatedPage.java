package com.hcl.usaaa.timesheet.security.testauto;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

@AuthorizeInstantiation("MANAGER")
public class AuthenticatedPage extends WebPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void onConfigure() {
		AuthenticatedWebApplication app=(AuthenticatedWebApplication)Application.get();
		if (!AuthenticatedWebSession.get().isSignedIn()) {
			app.restartResponseAtSignInPage();
		}
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Link("goToHomePage"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
		add(new Link("logOut"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				AuthenticatedWebSession.get().invalidate();
				setResponsePage(getApplication().getHomePage());
			}
			
		});
	}
}
