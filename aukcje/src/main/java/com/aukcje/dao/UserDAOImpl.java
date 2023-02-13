//package com.aukcje.dao;
//
//import com.aukcje.dao.iface.UserDAO;
//import com.aukcje.entity.User;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Repository
//@Transactional
//public class UserDAOImpl implements UserDAO {
//
//    @Autowired
//    SessionFactory sessionFactory;
//
//    @Override
//    public void save(User user) {
//        Session session = sessionFactory.getCurrentSession();
//        if(user.getId() == 0) user.setId(null);
//        session.save(user);
//    }
//
//    @Override
//    public List<User> get() {
//        Session session = sessionFactory.getCurrentSession();
//
//        Query query = session.createQuery("from User", User.class);
//
//        List<User> users = query.getResultList();
//
//        return users;
//    }
//
//    @Override
//    public User getById(int id) {
//        Session session = sessionFactory.getCurrentSession();
//
//        User user = session.get(User.class, id);
//
//        return user;
//    }
//
//    @Override
//    public User getByUsername(String username) {
//        Session session = sessionFactory.getCurrentSession();
//
//        Query query = session.createQuery("from User where username =:username");
//        query.setParameter("username", username);
//
//        User user = (User) query.uniqueResult();
//
//        return user;
//    }
//}
