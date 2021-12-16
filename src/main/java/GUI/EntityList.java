package GUI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class EntityList {
    public List<?> readEntities(EntityManagerFactory emf, String entity) {
        EntityManager em;
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            // Создаем объект запроса.
            Query query = em.createQuery("SELECT x FROM "+ entity+" x ");
            // Получаем результат запроса
            List<?> list = query.getResultList();
            em.getTransaction().commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            // Откат транзакции в случае ошибки.
            em.getTransaction().rollback();
        } finally {
            // Завершение работы с менеджером сущностей.
            em.close();
        }
        return null;
    }

    public List<?> entitiesByQuery(EntityManagerFactory emf, String squery) {
        EntityManager em;
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            // Создаем объект запроса.
            Query query = em.createQuery(squery);
            // Получаем результат запроса
            List<?> list = query.getResultList();
            em.getTransaction().commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            // Откат транзакции в случае ошибки.
            em.getTransaction().rollback();
        } finally {
            // Завершение работы с менеджером сущностей.
            em.close();
        }
        return null;
    }

}
