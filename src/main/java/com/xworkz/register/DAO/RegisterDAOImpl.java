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
	public RegisterEntity feachEmail(String mail) {
		System.out.println("invoking feachEmailAndPassword");
		Session session = null;
		RegisterEntity registerEntity = null;

		try {
			session = factory.openSession();

			String hql = " from RegisterEntity where email='" + mail + "'";

			System.out.println("creating query");
			Query query = session.createQuery(hql);

			System.out.println("getting unique result..");
			registerEntity = (RegisterEntity) query.uniqueResult();
			System.out.println("entity..:" + registerEntity);
			if (registerEntity != null) {
				System.out.println("getting data:");
				return registerEntity;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public int updateLoginCount(int loginCount, int id) {
		System.out.println("invoke updateLoginCount.. ");
		Session session = null;
		try {
			session = factory.openSession();
			session.beginTransaction();

			String hql = " from RegisterEntity e set e.loginCount ='" + loginCount + "'where e.id'" + id + "'";
			System.out.println("created query:");
			Query query = session.createQuery(hql);

			System.out.println("getting unique result is update");
			int result = query.executeUpdate();
			System.out.println("result:" + result);
			session.getTransaction().commit();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(session)) {
				session.close();
			}

		}
		return 0;
	}

}
