package com.hcl.usaa.timesheet.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

/**
 * Sergio Arellano
 */
@Entity
@Table(name = "status", catalog = "timesheet")
public class Status implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idStatus", unique = true, nullable = false)
	private Integer idStatus;
	
	@Column(name = "Status", length = 45)
	private String status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	private Set<Timesheet> timesheets = new HashSet<Timesheet>(0);

	public Status() {
	
	}

	public Status(Integer idStatus, String status, Set<Timesheet> timesheets) {
		super();
		this.idStatus = idStatus;
		this.status = status;
		this.timesheets = timesheets;
	}

	public Integer getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Timesheet> getTimesheets() {
		return this.timesheets;
	}

	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}
	
	public boolean save(){
		boolean bsave=false;
		try {
			Session session = HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			session.save(this);
			HibernateUtil.commitTransaction();
			bsave=true;
		} catch (Exception e) {
			rollBackCurrentTransaction();
			e.printStackTrace();
			String err="An error occurs while try to save status element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public boolean saveOrUpdate(){
		boolean bsave=false;
		try {
			Session session = HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			session.saveOrUpdate(this);
			HibernateUtil.commitTransaction();
			bsave=true;
		} catch (Exception e) {
			rollBackCurrentTransaction();
			e.printStackTrace();
			String err="An error occurs while try to save or update status element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public boolean delete(){
		boolean bsave=false;
		try {
			Session session = HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			session.delete(this);
			HibernateUtil.commitTransaction();
			bsave=true;
		} catch (Exception e) {
			rollBackCurrentTransaction();
			e.printStackTrace();
			String err="An error occurs while try to delete status element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public boolean update(){
		boolean bsave=false;
		try {
			Session session = HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			session.update(this);
			HibernateUtil.commitTransaction();
			bsave=true;
		} catch (Exception e) {
			rollBackCurrentTransaction();
			e.printStackTrace();
			String err="An error occurs while try to update status element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public List<Status> getAll(){
		return getList(0, 0, true);
	}
	
	public List<Status> getList(int firstResult,int maxResult){
		return getList(firstResult, maxResult, false);
	}
	
	private List<Status> getList(int firstRestul,int maxResult,boolean all) {
		List<Status> cliList=new ArrayList<Status>();
		Session session=null;
		try {
			session=HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			Criteria c=session.createCriteria(getClass());
			if (!all) {
				c.setFirstResult(firstRestul);
				c.setMaxResults(maxResult);
			}
			cliList=c.list();
			HibernateUtil.commitTransaction();
			HibernateUtil.closeSession();
		} catch (Exception e) {
			e.printStackTrace();
		System.err.println("An error occurs while try to get status elements");
		}
		return cliList;
	}
	
	public List<Status> getByStatus(String status) {
		List<Status> cliList=new ArrayList<Status>();
		Session session=null;
		try {
			session=HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			Criteria c=session.createCriteria(getClass());
			c.add(Restrictions.eq("status", status));
			cliList=c.list();
			HibernateUtil.commitTransaction();
			HibernateUtil.closeSession();
		} catch (Exception e) {
			e.printStackTrace();
		System.err.println("An error occurs while try to get status elements");
		}
		return cliList;
	}
	
	public Status getById(Integer id) {
		Status s = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("idStatus", id));
	       s=(Status) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   String err="An error occurs while try to get elements";
    	   System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return s;
	}

	@Override
	public String toString(){
		StringBuilder sb =  new StringBuilder();	
		sb.append("[");
		sb.append("idStatus ").append(idStatus);
		sb.append("status : ").append(status);
		sb.append("]");
		return sb.toString();
	}
	
	public boolean equals(Status s){
		return s.getIdStatus().equals(getIdStatus());
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Status)) {
			return false;
		}
		Status s=(Status)arg0;
		return s.getIdStatus().equals(getIdStatus());
	}
	
	private void rollBackCurrentTransaction(){
		try{
			HibernateUtil.rollBackTransaction();
		}catch(Exception ex1){
			System.err.println("An error occurs while try to rollback the current transaction");
		}
	}

}
