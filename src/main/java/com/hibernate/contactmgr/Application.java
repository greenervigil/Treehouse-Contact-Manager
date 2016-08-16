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

        int id = save(contact);

        //Display list of contacts
        //for(Contact c : fetchAllContacts()) {
          //  System.out.println(c);
        //}

        //java 8 solution
        System.out.printf("%n%nBefore update%n%n");
        fetchAllContacts().stream().forEach(System.out::println);

        //Get persistent contact
        Contact c = findContactByID(id);
        //Update contact
        c.setFirstName("Oliver");

        //Persist the change
        System.out.printf("%n%nUpdating%n%n");
        update(c);
        System.out.printf("%n%nUpdate complete%n%n");
        //Display list of contacts

        System.out.printf("%n%nAfter update%n%n");
        fetchAllContacts().stream().forEach(System.out::println);



    }


    private static Contact findContactByID(int id) {
        //Open session
        Session session = sessionFactory.openSession();
        //Retrieve persistent object
        Contact contact = session.get(Contact.class,id);


        //Close session and return contact
        session.close();
        return contact;
    }

    private static void update(Contact contact){
        //open session
        Session session = sessionFactory.openSession();
        //begin transaction
        session.beginTransaction();
        //commit the transaction
        session.update(contact);
        //Close session
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
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

    private static int save(Contact contact) {
        //open session
        Session session = sessionFactory.openSession();
        //begin transaction
        session.beginTransaction();

        int id = (int)session.save(contact);

        //save contact with session
        session.save(contact);

        //commit transaction
        session.getTransaction().commit();
        //close session
        session.close();
        return id;
    }
}

