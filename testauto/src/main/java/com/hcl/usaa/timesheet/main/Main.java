package com.hcl.usaa.timesheet.main;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import com.hcl.usaa.timesheet.bean.Bucket;
import com.hcl.usaa.timesheet.bean.BussinesLine;
import com.hcl.usaa.timesheet.bean.Hours;
import com.hcl.usaa.timesheet.bean.Rol;
import com.hcl.usaa.timesheet.bean.Status;
import com.hcl.usaa.timesheet.bean.Timesheet;
import com.hcl.usaa.timesheet.bean.User;
import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

public class Main {
	public static void main(String[] args) {
		
		HibernateUtil.configure();
		
//		System.out.println(new User().getList(0, 1).size());
		User u=new User().getBySapId("121");
		Bucket b=new Bucket().getById(1);
		HashSet<Bucket> lb=new HashSet<Bucket>();
		lb.add(b);
		Status s=new Status().getById(1);
		Date d=Calendar.getInstance().getTime();
		Timesheet t=new Timesheet().getById(1);
		Hours h1=new Hours(null, 8, 8, 8, 8, 8, Boolean.TRUE, Boolean.TRUE, t, b);
		
		System.out.println(new User().getAll().size());
		System.out.println(new BussinesLine().getAllBussinesLine().size());
		System.out.println(new Hours().getAllHourss().size());
		System.out.println(new Rol().getAll().size());
		System.out.println(new Status().getAll().size());
		System.out.println(new Timesheet().getAll().size());
		System.out.println(new Rol().getByRolName("MANAGER"));
		
		HibernateUtil.disconnect();
	}
}