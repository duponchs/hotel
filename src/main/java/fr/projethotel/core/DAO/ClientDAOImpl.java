package fr.projethotel.core.DAO;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientDAOImpl {
    public void create(Client client) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
