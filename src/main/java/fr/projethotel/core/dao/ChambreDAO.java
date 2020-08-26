package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.Utilitaire;
import fr.projethotel.core.entity.Chambre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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


}
