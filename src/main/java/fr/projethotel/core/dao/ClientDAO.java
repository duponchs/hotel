package fr.projethotel.core.dao;

import fr.projethotel.core.HibernateUtil;
import fr.projethotel.core.entity.Client;
import fr.projethotel.core.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public List<Client> getListByNomPrenom(String nom,String prenom){
        List<Client> listClients = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Client> query = session.getNamedQuery("client.listfindByNomPrenom");
            query.setParameter("nom",nom);
            query.setParameter("prenom",prenom);
            try {
                listClients = query.list();
                logger.trace("liste lu");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }
        } catch (Throwable t) {
            logger.fatal(t.getMessage());
        }
        return listClients;
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
    public void update(Client client){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.fatal(e.getMessage());
        }
    }
    public List<Reservation> listSearchReservation(Integer id) {
        List<Reservation> reservations = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Reservation> query = session.createQuery(
                    "from Reservation where client.id=:id");
            query.setParameter("id",id);
            try {
                reservations = query.list();
                logger.trace("liste reservation lu");
            }catch (Exception e){
                logger.fatal(e.getMessage());
            }

        }catch (Exception e) {
            logger.fatal(e.getMessage());
        }
        return reservations;
    }
    public void setTrueStatusArchiver(Client client){

    }

}
