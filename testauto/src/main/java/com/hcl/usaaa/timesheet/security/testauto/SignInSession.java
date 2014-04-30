package com.hcl.usaaa.timesheet.security.testauto;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.hcl.usaa.timesheet.bean.User;
import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

public class SignInSession extends AuthenticatedWebSession{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	public SignInSession(Request request) {
		super(request);
	}

	@Override
	public boolean authenticate(String user, String password) {
		HibernateUtil.configure();
		this.user=new User().getByLogin(user, password);
		HibernateUtil.disconnect();
		System.out.println(this.user);
		return this.user!=null;
//		boolean b=user.equals("u") && password.equals("p");
//		System.out.println(b);
//		return b;
	}

	@Override
	public Roles getRoles() {
		Roles roles=new Roles();
		roles.add(user.getRol().getRol());
		return roles;
	}
}
