package com.hcl.usaaa.timesheet.security.testauto;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

public class Login extends WebPage{

	private static final long serialVersionUID = 1L;
	TextField<String> txtUser;
	PasswordTextField txtPass;
     
     public Login() {
		txtUser=new TextField<String>("username",new Model<String>());
		txtPass=new PasswordTextField("password",new Model<String>());
		Form<Void> f=new Form<Void>("signInForm"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				HibernateUtil.configure();
				String user=txtUser.getValue();
				String pass=txtPass.getValue();
				System.out.println(user + "---"+ pass);
				boolean res=AuthenticatedWebSession.get().signIn(user, pass);
				if (res) {
//					continueToOriginalDestination();
					setResponsePage(AuthenticatedPage.class);
				}
			}
		};
		f.add(txtUser);
		f.add(txtPass);
		add(f);
		
	}
}
