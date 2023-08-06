package util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.Query;
import model.User;


public class HibernateUtil {

	public static Session getHibernateSession() {
		
				// B1: Create Config
		
				Configuration conf = new Configuration();
				conf.configure("hibernate.cfg.xml");
				conf.addAnnotatedClass(User.class);
				//conf.addAnnotatedClass(Role.class);
				
				// B2: Create SessionFacotry
				
				SessionFactory sessionFactory = conf.buildSessionFactory();
				
				// B3: Open Session
				
				Session session = sessionFactory.openSession();
				
			

				
				return session;
				
	}
	
	
	public boolean checkPassword(String userName, String passWord) {
		boolean result = false;
		// check password
		Session session = HibernateUtil.getHibernateSession();
	
		// lay  user co username = ? ==> so sanh password
		
		String hql = "FROM User WHERE userName = :name";
		User user = session.createQuery(hql, User.class).setParameter("name", userName).getSingleResult();
		//user = ["lin", 1000, 123] => username= Linh
		// user = null; => usernae = trieu
		
		// check password = true ==> result = true
		if (user != null &&  passWord.equalsIgnoreCase(user.getPassWord())) {
			result = true;
		}
		
		return result;
	}
	
	public int getCurrentMoney(String userName) {
		
		Session session = HibernateUtil.getHibernateSession();
		
		String hql = "FROM User WHERE userName = :name";
		User user = session.createQuery(hql, User.class).setParameter("name", userName).getSingleResult();
		int currentMoney =  user.getCurrentMoney();
		return currentMoney;
	}
	
	public int saveCurrentMoney(String userName, int currentMoney) {
		
		Session session = HibernateUtil.getHibernateSession();
		Transaction trans = session.beginTransaction();

		String hql = "UPDATE User SET  currentMoney = :money WHERE  userName = :name ";

		Query query = session.createQuery(hql);
		query.setParameter("money", currentMoney);
		query.setParameter("name", userName);

		int result = query.executeUpdate();

		trans.commit();
		session.close();
		
		return result;
		
	}
	
    public int napTien(String userName, int currentMoney) {
		
		Session session = HibernateUtil.getHibernateSession();
		Transaction trans = session.beginTransaction();

		String hql = "UPDATE User SET  currentMoney = :money WHERE  userName = :name ";

		Query query = session.createQuery(hql);
		query.setParameter("money", currentMoney);
		query.setParameter("name", userName);

		int result = query.executeUpdate();

		trans.commit();
		session.close();
		
		return result;
		
	}
	
	
	
	public List<User> getListUsers (String userName){
		Session session = HibernateUtil.getHibernateSession();
		String hql = "FROM User WHERE username = :name";
		List<User> listUsers = session.createQuery(hql, User.class).setParameter("name", userName).list();
		return listUsers;
		
	} 
}
