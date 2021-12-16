package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@WebServlet(name = "ArchiveOrderPageServlet", urlPatterns = {"/ArchiveOrderPages","/deleteArchiveOrder","/listArchiveOrder"})
public class ArchiveOrderPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/deleteArchiveOrder":
                    deleteArchiveOrder(request, response);
                    break;
                case "/listArchiveOrder":
                    listArchiveOrder(request, response);
                    break;
                default:
                    listArchiveOrder(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listArchiveOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        ArchiveOrderEntity[] archiveOrderEntities ;
        EntityList el = new EntityList();
        archiveOrderEntities = el.readEntities(emf, "ArchiveOrderEntity").toArray(new ArchiveOrderEntity[0]);
        request.setAttribute("listArchiveOrder", archiveOrderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ArchiveOrderPages/list-archiveOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteArchiveOrder(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int archiveOrderId = Integer.parseInt(request.getParameter("archiveOrderId"));
        Del d = new Del();
        try {
            d.delArchiveOrder(emf, em.find(ArchiveOrderEntity.class, archiveOrderId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("ArchiveOrderPages");
    }
}
