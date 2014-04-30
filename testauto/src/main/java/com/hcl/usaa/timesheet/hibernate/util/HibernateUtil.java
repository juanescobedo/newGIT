/**
 * 
 */
package com.hcl.usaa.timesheet.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * @author Sergio Arellano
 * 
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	/**
	 * creates an instance of sessionfactory
	 */
	public static synchronized void configure() {
		
		try{
			//Load configuration from default hibernate.cfg.xml file
			configuration = new AnnotationConfiguration().configure("hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
			
		}catch(Throwable ex){
			System.err.println("Fail while try to build session from hibernate.cfg.xml file");
			ex.printStackTrace();
		//	throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * rebuild session factory
	 * @throws Exception
	 */
	public static  void rebuildSessionFactory() throws Exception{
		synchronized(sessionFactory){
			try{
				sessionFactory = configuration.buildSessionFactory();
			}catch(Throwable t){
				throw new Exception(t);
			}
		}
	}

	/**
	 * returns a db session
	 * @return
	 */
	public static synchronized Session getDBSession() throws Exception {
		Session session = threadSession.get();
		// keeps only one session object per connection 
		if(session == null){
			session = sessionFactory.openSession();
			threadSession.set(session);
		}
		
		return session;
	}
	
	/**
	 * close db session 
	 */
	public static void closeSession(){
		try{
			Transaction txn =  threadTransaction.get();
			if(txn != null){
				threadTransaction.set(null);
			}
			Session session  = threadSession.get();
			
			if(session != null && session.isOpen()){
				session.clear();
				session.close();
			}
			threadSession.set(null);
		}catch(Exception ex){
			System.err.println("Fail while try to close session");
		}
	}

	/**
	 * allows the user begin a transaction
	 * @return
	 * @throws Exception
	 */
	public static Transaction beginTransaction() throws Exception {
		Transaction txn = threadTransaction.get();
		if(txn == null){
			txn = getDBSession().beginTransaction();
			threadTransaction.set(txn);
		}
		return txn;
	}
	
	/**
	 * commit a transaction
	 * @throws Exception
	 */
	public static void commitTransaction() throws Exception{
		Transaction txn = threadTransaction.get();
		if(txn != null && !txn.wasCommitted() && !txn.wasRolledBack()){
			txn.commit();
			threadTransaction.set(null);
		}
		
	}
	
	/**
	 * rollback the current transaction 
	 * @throws Exception
	 */
	public static void rollBackTransaction() throws Exception{
		Transaction txn = threadTransaction.get();
		if(txn != null && !txn.wasCommitted() && !txn.wasRolledBack()){
			txn.rollback();
			threadTransaction.set(null);
		}
	}
	
	public static void disconnect(){
		Session s = threadSession.get();
		Transaction txn = threadTransaction.get();
		try{
			if(txn != null){
				threadTransaction.set(null);
			}
			if(s !=null && s.isConnected() && s.isOpen()){
				s.disconnect();
				threadSession.set(null);
			}
			configuration = null;
			sessionFactory = null;
		}catch(Exception ex){
			System.err.println("error while try to desconnect from DB");
		}
		
	}
}
