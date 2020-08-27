package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.entity.Chambre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;

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

    public void update(Chambre chambre){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(chambre);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }


    public Long getCapaciteMax(){
       Long capaciteMaxHotel = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Long> query = session.getNamedQuery("chambre.findCapaciteMax");
            query.setParameter("idHotel", Utilitaire.getIdHotel());

            try {
                capaciteMaxHotel = query.getSingleResult();
                logger.trace("lu");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }

        return  capaciteMaxHotel;
    }

    public List<Chambre> getChambreNotArchived(){
        List<Chambre> lesChampresnotArchvied = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Chambre> query = session.getNamedQuery("chambre.findByNotArchiver");

            try {
                lesChampresnotArchvied = query.getResultList();
                logger.trace("lu");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }

        return lesChampresnotArchvied;
    }

    public List<Chambre> getChambreDispoAtDay(LocalDate date){
        List<Chambre> lesChambresDispo = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Chambre> query = session.createQuery("from Chambre c where c.id not in " +
                    "(select chambre.id from Chambre chambre inner join chambre.reservations  r where r.dateNuitee =:date) and c.archiver=false and c.hotel.id=:idHotel");
            query.setParameter("date", date);
            query.setParameter("idHotel", Utilitaire.getIdHotel());

            try {
                lesChambresDispo = query.getResultList();
                logger.trace("lu");
            }catch (Exception e){
                logger.fatal((e.getMessage()));
            }
        }catch (Throwable t) {
                logger.fatal(t.getMessage());
            }
        return lesChambresDispo;
    }




}
