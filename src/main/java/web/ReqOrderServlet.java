package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.*;
import model.ReqOrderEntity;

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

@WebServlet(name = "ReqOrderServlet", urlPatterns = {"/ReqOrderPages", "/newReqOrder", "/insertReqOrder","/deleteReqOrder", "/editReqOrder", "/updateReqOrder","/listReqOrder"})
public class ReqOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newReqOrder":
                    showNewForm(request, response);
                    break;
                case "/insertReqOrder":
                    insertReqOrder(request, response);
                    break;
                case "/deleteReqOrder":
                    deleteReqOrder(request, response);
                    break;
                case "/editReqOrder":
                    showEditForm(request, response);
                    break;
                case "/updateReqOrder":
                    updateReqOrder(request, response);
                    break;
                case "/listReqOrder":
                    listReqOrder(request, response);
                    break;
                default:
                    listReqOrder(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listReqOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        ReqOrderEntity[] reqOrderEntities = null;
        EntityList el = new EntityList();
        reqOrderEntities = el.readEntities(emf, "ReqOrderEntity").toArray(new ReqOrderEntity[0]);
        request.setAttribute("listReqOrder", reqOrderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqOrderPages/list-reqOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerEntity[] customerEntities;
        EntityList el = new EntityList();
        customerEntities = el.readEntities(emf, "CustomerEntity").toArray(new CustomerEntity[0]);
        request.setAttribute("listCustomer", customerEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqOrderPages/reqOrder-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int reqOrderId = Integer.parseInt(request.getParameter("reqOrderId"));
        EntityManager em = emf.createEntityManager();
        ReqOrderEntity existingReqOrder = em.find(ReqOrderEntity.class, reqOrderId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqOrderPages/reqOrder-form.jsp");
        em.close();
        CustomerEntity[] customerEntities;
        EntityList el = new EntityList();
        customerEntities = el.readEntities(emf, "CustomerEntity").toArray(new CustomerEntity[0]);
        request.setAttribute("listCustomer", customerEntities);
        request.setAttribute("reqOrder", existingReqOrder);
        request.setAttribute("currentcustomerId", existingReqOrder.getCustomerId().getCustomerId());
        dispatcher.forward(request, response);
    }

    private void insertReqOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        CustomerEntity customer = em.find(CustomerEntity.class, Integer.parseInt(request.getParameter("customerId")));
        String phone = request.getParameter("phone");
        String question = request.getParameter("question");
        boolean processed = Boolean.parseBoolean(request.getParameter("processed"));
        Add add = new Add();
        add.saveReqOrder(emf, customer,phone,question,processed);
        em.close();
        response.sendRedirect("/");
    }

    private void updateReqOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int reqOrderId = Integer.parseInt(request.getParameter("reqOrderId"));
        CustomerEntity customer = em.find(CustomerEntity.class, Integer.parseInt(request.getParameter("customerId")));
        String phone = request.getParameter("phone");
        String question = request.getParameter("question");
        boolean processed = Boolean.parseBoolean(request.getParameter("processed"));
        Edit e = new Edit();
        e.editReqOrder(emf,reqOrderId, customer,phone,question,processed);
        em.close();
        response.sendRedirect("ReqOrderPages");
    }

    private void deleteReqOrder(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int reqOrderId = Integer.parseInt(request.getParameter("reqOrderId"));
        Del d = new Del();
        try {
            d.delReqOrder(emf, em.find(ReqOrderEntity.class, reqOrderId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("ReqOrderPages");
    }
}
