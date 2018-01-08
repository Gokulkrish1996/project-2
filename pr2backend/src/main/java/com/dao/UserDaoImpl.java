package com.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	public void registerUser(User user)
	{
		Session session = sessionFactory.getCurrentSession();		
		session.save(user);			
	}
	
	public boolean isEmailValid(String email)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where email = '"+email+"'");
		User user = (User)query.uniqueResult();
		if (user==null)
			return true;
		else
			return false;		
	}

	public boolean isUsernameValid(String username)
	{
		Session session = sessionFactory.getCurrentSession();
		User user = (User)session.get(User.class, username);
		if (user==null)
			return true;
		else
			return false;
	}

	public User login(User user)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where username=? and password=?");
		query.setString(0,user.getUsername());
		query.setString(1, user.getPassword());
		User validUser=(User)query.uniqueResult();
		return validUser;
		
	}

	public void updateUser(User user)
	{
		Session session=sessionFactory.getCurrentSession();
		session.update(user);		
	}
    public User getUserByUsername(String username)
	{
		Session session=sessionFactory.getCurrentSession();
		User user = (User)session.get(User.class, username);		
		return user;
	}
}