package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `users` (\n" +
                                   "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                                   "  `name` VARCHAR(255) NULL,\n" +
                                   "  `lastName` VARCHAR(255) NULL,\n" +
                                   "  `age` SMALLINT NULL,\n" +
                                   "  PRIMARY KEY (`id`))");
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.createSQLQuery("DROP TABLE IF EXISTS `users`");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.createQuery("DELETE User", User.class).executeUpdate();
        }
    }
}

