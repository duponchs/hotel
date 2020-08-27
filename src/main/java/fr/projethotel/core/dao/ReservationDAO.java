package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Chambre;
import fr.projethotel.core.entity.Client;
import fr.projethotel.core.entity.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Date;

import java.util.List;

public class ReservationDAO {
    static final Logger logger = LogManager.getLogger("ReservationDAO");

    public void create(Reservation reservation) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
    public Reservation getReservationById(Integer id){
        Reservation reservation = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            reservation = session.get(Reservation.class,id);
            logger.trace("reservation lue");
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return reservation;
    }
    public List<Reservation> getListByDate(LocalDate d){
        List<Reservation> listReservations = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Reservation> query = session.getNamedQuery("reservation.findByDate");
            query.setParameter("dateNuitee", d);
            try {
                listReservations = query.list();
                logger.trace("liste lue");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return listReservations;
    }
    public Reservation getByClientDate(Client client, LocalDate date){
        Reservation reservation = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Reservation> query = session.getNamedQuery("reservation.findByClientDate");
            query.setParameter("client",client);
            query.setParameter("dateNuitee", date);
            try {
                reservation = query.getSingleResult();
                logger.trace("reservation lue");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return reservation;
    }

    public void update(Reservation reservation){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
    public void delete(Reservation reservation){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(reservation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
}

