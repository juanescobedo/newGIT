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

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "user", catalog = "timesheet")
public class User implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idUser", unique = true, nullable = false)
	private Integer idUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idRol", nullable = false)
	private Rol rol;
	
	@Column(name = "sapId", length = 10)
	private String sapId;
	
	@Column(name = "User", length = 45)
	private String user;
	
	@Column(name = "Password", length = 45)
	private String password;
	
	@Column(name = "usrName", length = 45)
	private String usrName;
	
	@Column(name = "lastName", length = 45)
	private String lastName;
	
	@ManyToOne
    @JoinColumn(name="idManager")
    private User manager;
	
	@OneToMany(mappedBy="manager")
    private Set<User> managerUsers = new HashSet<User>();
	
	@ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="idTeamLead")
	private User teamLead;
	
	@OneToMany(mappedBy="teamLead",cascade={CascadeType.ALL})
    private Set<User> teamLeadUsers = new HashSet<User>();
	
	@Column(name = "active")
	private Boolean active;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Timesheet> timesheets = new HashSet<Timesheet>(0);
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="User_bucket_assigned",joinColumns={@JoinColumn(name="idUser")},
			inverseJoinColumns={@JoinColumn(name="idBucket")})
	private Set<Bucket> userBucketAssigneds = new HashSet<Bucket>(0);

	public User() {
	}

	public User(Rol rol) {
		this.rol = rol;
	}

	public User(Integer idUser, Rol rol, String sapId, String user,
			String password, String usrName, String lastName, User manager,
			Set<User> managerUsers, User teamLead, Set<User> teamLeadUsers,
			Boolean active, Set<Timesheet> timesheets,
			Set<Bucket> userBucketAssigneds) {
		super();
		this.idUser = idUser;
		this.rol = rol;
		this.sapId = sapId;
		this.user = user;
		this.password = password;
		this.usrName = usrName;
		this.lastName = lastName;
		this.manager = manager;
		this.managerUsers = managerUsers;
		this.teamLead = teamLead;
		this.teamLeadUsers = teamLeadUsers;
		this.active = active;
		this.timesheets = timesheets;
		this.userBucketAssigneds = userBucketAssigneds;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<User> getManagerUsers() {
		return managerUsers;
	}

	public void setManagerUsers(Set<User> managerUsers) {
		this.managerUsers = managerUsers;
	}

	public User getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(User teamLead) {
		this.teamLead = teamLead;
	}

	public Set<User> getTeamLeadUsers() {
		return teamLeadUsers;
	}

	public void setTeamLeadUsers(Set<User> teamLeadUsers) {
		this.teamLeadUsers = teamLeadUsers;
	}

	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getSapId() {
		return this.sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsrName() {
		return this.usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Timesheet> getTimesheets() {
		return this.timesheets;
	}

	public void setTimesheets(Set<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

	public Set<Bucket> getUserBucketAssigneds() {
		return userBucketAssigneds;
	}

	public void setUserBucketAssigneds(Set<Bucket> userBucketAssigneds) {
		this.userBucketAssigneds = userBucketAssigneds;
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
			String err="An error occurs while try to save user element";
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
			String err="An error occurs while try to save or update user element";
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
			String err="An error occurs while try to update user element";
			System.err.println(err);
		}finally{
			HibernateUtil.closeSession();
		}
		return bsave;
	}
	
	public User getById(Integer id) {
		User u = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("idUser", id));
	       c.add(Restrictions.eq("active", Boolean.TRUE));
	       u=(User) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   e.printStackTrace();
    	   String err="An error occurs while try to get elements";
    	   System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return u;
	}
	
	public User getBySapId(String id) {
		User u = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria c=session.createCriteria(getClass());
	       c.add(Restrictions.eq("sapId", id));
	       c.add(Restrictions.eq("active", Boolean.TRUE));
	       u=(User) c.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   e.printStackTrace();
    	   String err="An error occurs while try to get elements";
    	   System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return u;
	}
	
	public User getByLogin(String user,String password) {
        User u = null;
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       Criteria cr=session.createCriteria(getClass());
	       cr.add(Restrictions.eq("user", user));
	       cr.add(Restrictions.eq("password", password));
	       cr.add(Restrictions.eq("active", Boolean.TRUE));
           u =(User) cr.uniqueResult();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   String err="An error occurs while try to get user element";
			System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return u;
   }
	
	public List<Bucket> getUnAssignedBuckets() {
        List<Bucket> lb= new ArrayList<Bucket>();
       try {
    	   Session session=HibernateUtil.getDBSession();
	       HibernateUtil.beginTransaction();
	       String sql="select b.idBucket, b.bucketName,b.active,b.idBussinesline,bl.active,bl.businessLine from user u join user_bucket_assigned ub on u.idUser=ub.idUser join bucket b on b.idBucket!=ub.idBucket join bussines_line bl on bl.idBusinessLine=b.idBussinesline where u.idUser=:idU";
	       Query q= session.createSQLQuery(sql).addEntity(Bucket.class).setParameter("idU", getIdUser());
	       lb=q.list();
           HibernateUtil.commitTransaction();
       } catch (Exception e) {
    	   e.printStackTrace();
    	   String err="An error occurs while try to get user element";
			System.err.println(err);
       }finally{
    	   HibernateUtil.closeSession();
       }
       return lb;
   }
	
	public List<User> getAll() {
		return getList(0, 0, true);
    }
	
	public List<User> getList(int firstResult,int maxResult) {
        return getList(firstResult, maxResult, false);
    }
	
	private List<User> getList(int firstResult,int maxResult,boolean all){
		List<User> lu=new ArrayList<User>();
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
			lu=c.list();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			String err="An error occurs while try to get elements";
			System.err.println(err);
			//e.printStackTrace();
		}finally{
			HibernateUtil.closeSession();
	    }
		return lu;
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
		sb.append("idUser : ").append(this.getIdUser()).append(", ");
		sb.append("user : ").append(this.getUser()).append(", ");
		sb.append("password : ").append(this.getPassword()).append(", ");
		sb.append("usrName : ").append(this.getUsrName()).append(", ");;
		sb.append("lastName : ").append(this.getLastName()).append(", ");
		sb.append("active : ").append(this.getActive()).append(", ");
		sb.append("rol : ").append(this.getRol());
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof User)) {
			return false;
		}
		User u=(User)arg0;
		return u.getIdUser().equals(getIdUser());
	}
}
