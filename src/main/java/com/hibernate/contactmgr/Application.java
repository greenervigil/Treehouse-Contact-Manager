package com.hibernate.contactmgr;

import com.hibernate.contactmgr.model.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


public class Application {
    //private static final SessionFactory sessionFactory = buildSessionFactory();

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
    }
}

