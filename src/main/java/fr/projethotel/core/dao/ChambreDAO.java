package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Chambre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ChambreDAO {

    static final Logger logger = LogManager.getLogger("ChambreDAO");


    public void create(Chambre chambre) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(chambre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }

}
