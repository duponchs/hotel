package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class ClientDAO {

    static final Logger logger = LogManager.getLogger("ClientDAO");

    public void create(Client client) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
    public Client getById(Integer id){
        Client client = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            client = session.get(Client.class,id);
            logger.trace("client lu");
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return client;
    }
    public Client getByNomPrenomEmail(String nom,String prenom, LocalDate dateNaissance,String email){
        Client client = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Client> query = session.getNamedQuery("client.findByNomPrenomEmail");
            query.setParameter("nom",nom);
            query.setParameter("prenom",prenom);
            query.setParameter("dateNaissance",dateNaissance);
            query.setParameter("email",email);
            try {
                client = query.getSingleResult();
                logger.trace("client lu");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return client;
    }
    public void delete(Client client){
        if (client!=null){
            if(!client.getArchiver()){
                Transaction transaction = null;
                try(Session session = HibernateUtil.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    session.delete(client);
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    logger.fatal(e.getMessage());
                }
            }else{
                logger.trace("L'état du client est archivé, impossible de le supprimer pour le moment");
            }
        }else{
            logger.trace("Une erreur sur la saisie du client est survenue");
        }
    }



}
