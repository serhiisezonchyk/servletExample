package test;

import model.CustomerEntity;

import javax.persistence.*;
import java.util.List;

public class test {
    private  static EntityManagerFactory ENTITY_MANAGER_FACTORY
            = Persistence.createEntityManagerFactory("java3");
    public static void main(String[] args) {
        readEntities(ENTITY_MANAGER_FACTORY, "");
    ENTITY_MANAGER_FACTORY.close();
    }
    private static void readEntities(EntityManagerFactory emf, String a) {
        EntityManager em;
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            // Создаем объект запроса.
            Query query = em.createQuery("SELECT x FROM CustomerEntity x " + a);
            // Получаем результат запроса.
            List<?> list = query.getResultList();
            // Проверяем результат на пустоту.
            if (!list.isEmpty()) {
                // Выводим все группы и их студентов.
                for (Object object : list) {
                    CustomerEntity customer = (CustomerEntity) object;
                    System.out.println(customer.getName());
                }
            } else {
                System.out.println("No customers");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Откат транзакции в случае ошибки.
            em.getTransaction().rollback();
        } finally {
            // Завершение работы с менеджером сущностей.
            em.close();
        }
    }
}
