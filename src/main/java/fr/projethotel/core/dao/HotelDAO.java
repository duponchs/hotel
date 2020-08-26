package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Client;
import fr.projethotel.core.entity.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public Hotel getByNom(String nom){
        Hotel hotel = null;

            try(Session session = HibernateUtil.getSessionFactory().openSession()) {
                Query<Hotel> query = session.getNamedQuery("hotel.findByNom");
                query.setParameter("nom",nom);
                 hotel = query.getSingleResult();
                logger.trace("client lu");
            } catch (Throwable t) {
                logger.fatal(t.getMessage());
            }
            return hotel;

    }

    public void update(Hotel hotel){
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(hotel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
}
