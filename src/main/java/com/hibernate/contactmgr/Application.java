package com.hibernate.contactmgr;

import com.hibernate.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;


public class Application {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Create std serivce reg obj
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Daniel", "Greener-Vigil")
                .withEmail("DJGV@Mail.com")
                .withPhone(5555558898L)
                .build();

        save(contact);

        //Display list of contacts
        for(Contact c : fetchAllContacts()) {
            System.out.println(c);
        }
        //java 8 solution
        //fetchAllContacts().stream().forEach(System.out::println);
    }


    private static List<Contact> fetchAllContacts() {
        //Open session
        Session session = sessionFactory.openSession();

        //Create Criteria and get a list of them
        Criteria criteria = session.createCriteria(Contact.class);

        List<Contact> contacts = criteria.list();


        //close session
        session.close();
        return contacts;
    }

    private static void save(Contact contact) {
        //open session
        Session session = sessionFactory.openSession();
        //begin transaction
        session.beginTransaction();


        //save contact with session
        session.save(contact);

        //commit transaction
        session.getTransaction().commit();
        //close session
        session.close();
    }
}

