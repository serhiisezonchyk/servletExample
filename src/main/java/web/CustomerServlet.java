package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.CarEntity;
import model.CustomerEntity;
import model.ShowroomEntity;

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

@WebServlet(name = "CustomerServlet", urlPatterns = {"/CustomerPages", "/newCustomer", "/insertCustomer","/deleteCustomer", "/editCustomer", "/updateCustomer","/listCustomer"})
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newCustomer":
                    showNewForm(request, response);
                    break;
                case "/insertCustomer":
                    insertCustomer(request, response);
                    break;
                case "/deleteCustomer":
                    deleteCustomer(request, response);
                    break;
                case "/editCustomer":
                    showEditForm(request, response);
                    break;
                case "/updateCustomer":
                    updateCustomer(request, response);
                    break;
                case "/listCustomer":
                    listCustomer(request, response);
                    break;
                default:
                    listCustomer(request, response);
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

    private void listCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        CustomerEntity[] customerEntities = null;
        EntityList el = new EntityList();
        customerEntities = el.readEntities(emf, "CustomerEntity").toArray(new CustomerEntity[0]);
        request.setAttribute("listCustomer", customerEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerPages/list-customer.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerPages/customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        EntityManager em = emf.createEntityManager();
        CustomerEntity existingCustomer = em.find(CustomerEntity.class, customerId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerPages/customer-form.jsp");
        request.setAttribute("customer", existingCustomer);
        em.close();
        dispatcher.forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        boolean legality = Boolean.parseBoolean(request.getParameter("legality"));
        Add add = new Add();
        add.saveCustomer(emf,name,phone, legality);
        em.close();
        response.sendRedirect("CustomerPages");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String ss = request.getParameter("legility");
        boolean legality = Boolean.parseBoolean(request.getParameter("legality"));
        Edit e = new Edit();
        e.editCustomer(emf, customerId,name,phone, legality);
        em.close();
        response.sendRedirect("CustomerPages");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Del d = new Del();
        try {
            d.delCustomer(emf, em.find(CustomerEntity.class, customerId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("CustomerPages");
    }
}
