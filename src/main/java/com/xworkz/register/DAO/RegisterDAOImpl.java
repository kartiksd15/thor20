package com.xworkz.register.DAO;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.Entity.RegisterEntity;

@Component
public class RegisterDAOImpl implements RegisterDAO {

	@Autowired
	private SessionFactory factory;

	public RegisterDAOImpl() {
		System.out.println("created:\t" + this.getClass().getSimpleName());
	}

	public void setFactory(SessionFactory factory) {
		System.out.println("invoked setFactory..");
		this.factory = factory;
	}

	public void saveAndRegister(RegisterEntity registerEntity) {
		System.out.println("invoked saveUser....");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();
			Serializable serializable = session.save(registerEntity);
			if (Objects.nonNull(serializable)) {
				System.out.println("registration  data saved...");
			} else {
				System.out.println("registration data not save...");
			}
			session.getTransaction().commit();
		} catch (HibernateException h) {
			session.getTransaction().rollback();
			h.printStackTrace();
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}

	}

	public boolean validateUserIDExitOrNo(String userId) {

		System.out.println("invoked validateUserIDExitOrNo....");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();

			System.out.println("enter userId is valid:" + userId);
			String query = "select count(*) from RegisterEntity where userId='" + userId + "'";
			System.out.println("query is valid:" + query);
			Query query2 = session.createQuery(query);
			Long count = (Long) query2.uniqueResult();
			System.out.println("count:" + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (HibernateException h) {
			session.getTransaction().rollback();
			h.printStackTrace();
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}
		return false;

	}

	public boolean validateEmailExitOrNo(String email) {
		System.out.println("invoked validateEmailExitOrNo....");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();
			System.out.println("enter email is valid:" + email);
			String query = "select count(*) from RegisterEntity where email='" + email + "'";
			System.out.println("query is valid:" + query);

			Query query2 = session.createQuery(query);
			Long count = (Long) query2.uniqueResult();
			System.out.println("count:" + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (HibernateException h) {
			session.getTransaction().rollback();
			h.printStackTrace();
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}
		return false;
	}

	@Override
	public boolean loginCheck(String loginEmail, String loginPassword) {

		System.out.println("invoked loginCheck :" + this.getClass().getSimpleName());
		boolean flag = false;
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();

			// to check deplicate email
			String emailHql = "select count(*) from RegisterEntity where email='" + loginEmail + "'";

			Query emailQuery = session.createQuery(emailHql);
			Long emailCount = (Long) emailQuery.uniqueResult();
			System.out.println("value of count for email :" + emailCount);

			// to check deplicate password
			if (emailCount > 0) {
				String passwordHql = "select count(*) from RegisterEntity where password='" + loginPassword + "'";
				Query passwordQuery = session.createQuery(passwordHql);
				Long passwordCount = (Long) passwordQuery.uniqueResult();
				System.out.println("value of count for password :" + passwordCount);
				if (passwordCount > 0) {
					return flag = true;
				} else
					return flag;
			} else
				return flag;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(session))
				session.close();
			System.out.println("Session closed in the finally");
		}

		return false;
	}

	@Override
	public Integer addAttempts(String loginEmail, int noOfAttempts) {

		System.out.println("inside the addAttempts: ");
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();

			String hqlUpdateAttempt = "update RegisterEntity set loginCount='" + noOfAttempts + "' where email='"
					+ loginEmail + "'";
			Query query = session.createQuery(hqlUpdateAttempt);
			Integer totalAttempts = query.executeUpdate();
			System.out.println("return query true or false " + query);
			System.out.println("loginemail: " + loginEmail + "\n" + "nnoOfAttempts :" + totalAttempts);
			session.getTransaction().commit();
			return totalAttempts;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			if (Objects.nonNull(session)) {
				session.close();
				System.out.println("session closed in finnaly fro add attempts");
			}
		}
		return null;
	}
	
	@Override
	public Integer checkAttempts(String loginEmail) {
		System.out.println("inside the checkAttempts: ");
		Session session = null;
		Integer attemptCount = 0;

		try {
			session = factory.openSession();
			session.beginTransaction();

			// to check attempts more than 3 times
			String attemptsHql = "from RegisterEntity where email='" + loginEmail + "'";

			Query attemptQuery = session.createQuery(attemptsHql);
			Object registerEntity = attemptQuery.uniqueResult();
			attemptCount = ((RegisterEntity) registerEntity).getLoginCount();

			System.out.println("value of count for checkAattempts :" + attemptCount);
			if (Objects.isNull(attemptCount)) {
				return 0;
			}
			return attemptCount;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			if (Objects.nonNull(session)) {
				session.close();
				System.out.println("session closed in finnaly fro add attempts");
			}
		}
		return attemptCount;

	}

	@Override
	public boolean updatePassword(RegisterEntity registerEntity) {
		System.out.println("invoking updatePassword:....!!!");
		Session session = null;
		boolean flag = false;

		try {

			session = factory.openSession();
			session.beginTransaction();

			String updatePassHQL = "update from RegisterEntity set password='" + registerEntity.getPassword()
					+ "' where email='" + registerEntity.getEmail() + "'";
			Query passUpdateQuery = session.createQuery(updatePassHQL);
			System.out.println("Query created with new reset password in DAO :" + passUpdateQuery);
			System.out.println("about to update password");
			passUpdateQuery.executeUpdate();
			session.getTransaction().commit();

			System.out.println(" reset password  :" + registerEntity.getPassword());
			return flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

		} finally {
			System.out.println("Session closed insode the resetPass finnaly block");
			if (Objects.nonNull(session))
				session.close();
		}
		return false;
	}

}
