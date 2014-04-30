package com.hcl.usaa.timesheet.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.hcl.usaa.timesheet.hibernate.util.HibernateUtil;

/**
 * Sergio Arellano
 */
@Entity
@Table(name = "timesheet", catalog = "timesheet")
public class Timesheet implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTimeSheet", unique = true, nullable = false)
	private Integer idTimeSheet;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idUser", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idStatus", nullable = false)
	private Status status;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "startDate", length = 10)
	private Date startDate;
	
	@Column(name = "active")
	private Boolean active;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "timesheet")
	private Set<Hours> hourses = new HashSet<Hours>(0);

	public Timesheet() {
	}

	public Timesheet(User user, Status status) {
		this.user = user;
		this.status = status;
	}

	public Timesheet(Integer idtimeSheet, User user, Status status,
			Date startDate, Boolean active, Set<Hours> hourses) {
		super();
		this.idTimeSheet = idtimeSheet;
		this.user = user;
		this.status = status;
		this.startDate = startDate;
		this.active = active;
		this.hourses = hourses;
	}

	public Integer getIdtimeSheet() {
		return this.idTimeSheet;
	}

	public void setIdtimeSheet(Integer idtimeSheet) {
		this.idTimeSheet = idtimeSheet;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Hours> getHourses() {
		return this.hourses;
	}

	public void setHourses(Set<Hours> hourses) {
		this.hourses = hourses;
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
			String err="An error occurs while try to save timesheet element";
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
			String err="An error occurs while try to save or update timesheet element";
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
			setActive(Boolean.FALSE);
			session.update(this);
			HibernateUtil.commitTransaction();
			bsave=true;
		} catch (Exception e) {
			rollBackCurrentTransaction();
			e.printStackTrace();
			String err="An error occurs while try to delete timesheet element";
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
			String err="An error occurs while try to update timesheet element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public List<Timesheet> getAll() {
		return getList(0, 0, true);
    }
	
	public List<Timesheet> getList(int firstResult,int maxResult) {
        return getList(firstResult, maxResult, false);
    }
	
	private List<Timesheet> getList(int firstResult,int maxResult,boolean all){
		List<Timesheet> lr=new ArrayList<Timesheet>();
		Session session=null;
		Query q=null;
		try {
			session=HibernateUtil.getDBSession();
			HibernateUtil.beginTransaction();
			Criteria c=session.createCriteria(getClass());
			c.add(Restrictions.eq("active", Boolean.TRUE));
			if (!all) {
				c.setFirstResult(firstResult);
				c.setMaxResults(maxResult);
			}
			lr=c.list();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			String err="An error occurs while try to get elements";
			System.err.println(err);
			//e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
	    }
		return lr;
	}
	
	public Timesheet getById(Integer id) {
		Timesheet t = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("idTimeSheet", id));
	       c.add(Restrictions.eq("active", Boolean.TRUE));
	       t=(Timesheet) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   String err="An error occurs while try to get elements";
			System.err.println(err);
			e.printStackTrace();
       }finally{
    	   HibernateUtil.closeSession();
       }
       return t;
	}
	
	private void rollBackCurrentTransaction(){
		try{
			HibernateUtil.rollBackTransaction();
		}catch(Exception ex1){
			System.err.println("An error occurs while try to rollback the current transaction");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("idTimesheet : ").append(this.getIdtimeSheet()).append(", ");
		sb.append("user : ").append(this.getUser().toString()).append(", ");
		sb.append("status : ").append(this.getStatus().toString()).append(", ");
		sb.append("startDate : ").append(this.getStartDate()).append(", ");
		sb.append("active : ").append(this.getActive());
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Rol)) {
			return false;
		}
		Timesheet ts=(Timesheet)arg0;
		return ts.getIdtimeSheet().equals(getIdtimeSheet());
	}

}
