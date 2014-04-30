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
@Table(name = "rol", catalog = "timesheet")
public class Rol implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "idRol", unique = true, nullable = false)
	private Integer idRol;
	
	@Column(name = "Rol", length = 45)
	private String rol;
	
	@Column(name = "active")
	private Boolean active;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rol")
	private Set<User> users = new HashSet<User>(0);

	public Rol() {
	}

	public Rol(Integer idRol, String rol, Boolean active, Set<User> users) {
		super();
		this.idRol = idRol;
		this.rol = rol;
		this.active = active;
		this.users = users;
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
			String err="An error occurs while try to save rol element";
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
			String err="An error occurs while try to save or update rol element";
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
			String err="An error occurs while try to delete rol element";
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
			String err="An error occurs while try to update rol element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public List<Rol> getAll() {
		return getList(0, 0, true);
    }
	
	public List<Rol> getList(int firstResult,int maxResult) {
        return getList(firstResult, maxResult, false);
    }
	
	private List<Rol> getList(int firstResult,int maxResult,boolean all){
		List<Rol> lr=new ArrayList<Rol>();
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
	
	public Rol getById(Integer id) {
		Rol r = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("idRol", id));
	       c.add(Restrictions.eq("active", Boolean.TRUE));
	       r=(Rol) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   String err="An error occurs while try to get elements";
			System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return r;
	}
	
	public Rol getByRolName(String rolName) {
		Rol r = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("rol", rolName));
//	       c.add(Restrictions.eq("active", Boolean.TRUE));
	       r=(Rol) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   String err="An error occurs while try to get elements";
			System.err.println(err);
			e.printStackTrace();
       }finally{
    	   HibernateUtil.closeSession();
       }
       return r;
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
		sb.append("idRol : ").append(this.getIdRol()).append(", ");
		sb.append("Rol : ").append(this.getRol());
		sb.append("]");
		return sb.toString();
	}
	
	
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Rol)) {
			return false;
		}
		Rol r=(Rol)arg0;
		return r.getIdRol().equals(getIdRol());
	}
}
