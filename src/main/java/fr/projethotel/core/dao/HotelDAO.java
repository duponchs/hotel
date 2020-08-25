package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HotelDAO {

    static final Logger logger = LogManager.getLogger("HotelDAO");


    public void create(Hotel hotel) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
}
