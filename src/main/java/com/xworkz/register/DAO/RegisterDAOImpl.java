package com.xworkz.register.DAO;

import java.io.Serializable;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xworkz.register.DTO.LoginDTO;
import com.xworkz.register.Entity.RegisterEntity;
import com.xworkz.register.controller.RegisterController;

@Component
public class RegisterDAOImpl implements RegisterDAO {

	@Autowired
	private SessionFactory factory;

	private static final Logger log = Logger.getLogger(RegisterDAOImpl.class);

	public RegisterDAOImpl() {
		// System.out.println("created:\t" + this.getClass().getSimpleName());
		log.info("created :\t," + this.getClass().getSimpleName());

	}

	public void setFactory(SessionFactory factory) {
		// System.out.println("invoked setFactory..");
		log.info("invoking setFactory..");
		this.factory = factory;
	}

	public void saveAndRegister(RegisterEntity registerEntity) {
		// System.out.println("invoked saveUser....");
		log.info("invoking saveAndregister:..");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();
			Serializable serializable = session.save(registerEntity);
			if (Objects.nonNull(serializable)) {
				// System.out.println("registration data saved...");
				log.info("registeration data saved....");
			} else {
				// System.out.println("registration data not save...");
				log.info("registration data not save...");
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			// e.printStackTrace();
			log.error(e.getMessage(), e);
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}

	}

	public boolean validateUserIDExitOrNo(String userId) {

		log.info("invoked validateUserIDExitOrNo....");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();

			log.info("enter userId is valid:" + userId);
			String query = "select count(*) from RegisterEntity where userId='" + userId + "'";
			log.info("query is valid:" + query);
			Query query2 = session.createQuery(query);
			Long count = (Long) query2.uniqueResult();
			log.info("count:" + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (HibernateException h) {
			session.getTransaction().rollback();
			log.error(h.getMessage(), h);
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}
		return false;

	}

	public boolean validateEmailExitOrNo(String email) {
		log.info("invoked validateEmailExitOrNo....");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();
			log.info("enter email is valid:" + email);
			String query = "select count(*) from RegisterEntity where email='" + email + "'";
			log.info("query is valid:" + query);

			Query query2 = session.createQuery(query);
			Long count = (Long) query2.uniqueResult();
			log.info("count:" + count);
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

		log.info("invoked loginCheck :" + this.getClass().getSimpleName());
		boolean flag = false;
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();

			// to check deplicate email
			String emailHql = "select count(*) from RegisterEntity where email='" + loginEmail + "'";

			Query emailQuery = session.createQuery(emailHql);
			Long emailCount = (Long) emailQuery.uniqueResult();
			log.info("value of count for email :" + emailCount);

			// to check deplicate password
			if (emailCount > 0) {
				String passwordHql = "select count(*) from RegisterEntity where password='" + loginPassword + "'";
				Query passwordQuery = session.createQuery(passwordHql);
				Long passwordCount = (Long) passwordQuery.uniqueResult();
				log.info("value of count for password :" + passwordCount);
				if (passwordCount > 0) {
					return flag = true;
				} else
					return flag;
			} else
				return flag;

		} catch (Exception e) {
			session.getTransaction().rollback();
			log.error(e.getMessage(), e);
		} finally {
			if (Objects.nonNull(session))
				session.close();
			log.info("Session closed in the finally");
		}

		return false;
	}

	@Override
	public Integer addAttempts(String loginEmail, int noOfAttempts) {

		log.info("inside the addAttempts: ");
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();

			String hqlUpdateAttempt = "update RegisterEntity set loginCount='" + noOfAttempts + "' where email='"
					+ loginEmail + "'";
			Query query = session.createQuery(hqlUpdateAttempt);
			Integer totalAttempts = query.executeUpdate();
			log.info("return query true or false " + query);
			log.info("loginemail: " + loginEmail + "\n" + "nnoOfAttempts :" + totalAttempts);
			session.getTransaction().commit();
			return totalAttempts;

		} catch (Exception e) {
			session.getTransaction().rollback();
			log.error(e.getMessage(), e);

		} finally {
			if (Objects.nonNull(session)) {
				session.close();
				log.info("session closed in finnaly fro add attempts");
			}
		}
		return null;
	}

	@Override
	public Integer checkAttempts(String loginEmail) {
		log.info("inside the checkAttempts: ");
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

			log.info("value of count for checkAattempts :" + attemptCount);
			if (Objects.isNull(attemptCount)) {
				return 0;
			}
			return attemptCount;

		} catch (Exception e) {
			session.getTransaction().rollback();
			log.error(e.getMessage(), e);

		} finally {
			if (Objects.nonNull(session)) {
				session.close();
				log.info("session closed in finnaly fro add attempts");
			}
		}
		return attemptCount;

	}

	@Override
	public boolean updatePassword(RegisterEntity registerEntity) {
		log.info("invoking updatePassword:....!!!");
		Session session = null;
		boolean flag = false;

		try {

			session = factory.openSession();
			session.beginTransaction();

			String updatePassHQL = "update from RegisterEntity set password='" + registerEntity.getPassword()
					+  "',loginCount='"
							+ registerEntity.getLoginCount()+ "' where email='" + registerEntity.getEmail() + "'";
			Query passUpdateQuery = session.createQuery(updatePassHQL);
			log.info("Query created with new reset password in DAO :" + passUpdateQuery);
			log.info("about to update password and reset attempt set zero");
			passUpdateQuery.executeUpdate();
			session.getTransaction().commit();

			log.info(" reset password  :" + registerEntity.getPassword());
			return flag = true;
		} catch (Exception e) {

			session.getTransaction().rollback();
			log.error(e.getMessage(), e);

		} finally {

			if (Objects.nonNull(session))

				session.close();
			log.info("Session closed insode the resetPass finnaly block");
		}
		return false;
	}

}
