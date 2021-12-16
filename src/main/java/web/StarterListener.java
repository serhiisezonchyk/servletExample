package web;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StarterListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context initialized");
    }
    public void contextDestroyed(ServletContextEvent sc) {
        System.out.println("Context destroyed");
    }
}
