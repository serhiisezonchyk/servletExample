package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.*;
import org.postgresql.util.PSQLException;

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
@WebServlet(name = "OrderServlet", urlPatterns = {"/OrderPages", "/newOrder", "/insertOrder","/deleteOrder", "/editOrder", "/updateOrder","/listOrder"})
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newOrder":
                    showNewForm(request, response);
                    break;
                case "/insertOrder":
                    insertOrder(request, response);
                    break;
                case "/deleteOrder":
                    deleteOrder(request, response);
                    break;
                case "/editOrder":
                    showEditForm(request, response);
                    break;
                case "/updateOrder":
                    updateOrder(request, response);
                    break;
                case "/listOrder":
                    listOrder(request, response);
                    break;
                default:
                    listOrder(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        OrderEntity[] orderEntities ;
        EntityList el = new EntityList();
        orderEntities = el.readEntities(emf, "OrderEntity").toArray(new OrderEntity[0]);
        request.setAttribute("listOrder", orderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderPages/list-order.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShowroomEntity[] showroomEntities;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);

        CarEntity[] carEntities ;
        el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);

        CustomerEntity[] customerEntities;
        el = new EntityList();
        customerEntities = el.readEntities(emf, "CustomerEntity").toArray(new CustomerEntity[0]);
        request.setAttribute("listCustomer", customerEntities);

        SellerEntity[] sellerEntities;
        el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderPages/order-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        EntityManager em = emf.createEntityManager();
        OrderEntity existingOrder = em.find(OrderEntity.class, orderId);
        request.setAttribute("order", existingOrder);
        EntityList el;

        CarEntity[] carEntities;
        el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        request.setAttribute("currentcarId", existingOrder.getCars().getCarId());

        CustomerEntity[] customerEntities;
        el = new EntityList();
        customerEntities = el.readEntities(emf, "CustomerEntity").toArray(new CustomerEntity[0]);
        request.setAttribute("listCustomer", customerEntities);
        request.setAttribute("currentcustomerId", existingOrder.getCustomers().getCustomerId());

        SellerEntity[] sellerEntities;
        el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);
        request.setAttribute("currentsellerId", existingOrder.getSellers().getSellerId());
        em.close();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderPages/order-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
        boolean confirmed = Boolean.parseBoolean(request.getParameter("confirmed"));

        Calendar dateOrd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            dateOrd.setTime(sdf.parse(request.getParameter("dateOrd")));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String payment = request.getParameter("payment");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        SellerEntity seller = em.find(SellerEntity.class, Integer.parseInt(request.getParameter("sellerId")));
        CarEntity car = em.find(CarEntity.class, Integer.parseInt(request.getParameter("carId")));
        CustomerEntity customer = em.find(CustomerEntity.class, Integer.parseInt(request.getParameter("customerId")));
        em.close();
        Add add = new Add();
        add.saveOrder(emf,availability,confirmed, dateOrd,payment,quantity,car,customer, seller);
        response.sendRedirect("OrderPages");
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
        boolean confirmed = Boolean.parseBoolean(request.getParameter("confirmed"));

        Calendar dateOrd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            dateOrd.setTime(sdf.parse(request.getParameter("dateOrd")));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String payment = request.getParameter("payment");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        float price = Float.parseFloat(request.getParameter("price"));
        SellerEntity seller = em.find(SellerEntity.class, Integer.parseInt(request.getParameter("sellerId")));
        CarEntity car = em.find(CarEntity.class, Integer.parseInt(request.getParameter("carId")));
        CustomerEntity customer = em.find(CustomerEntity.class, Integer.parseInt(request.getParameter("customerId")));
        em.close();
        Edit e = new Edit();
        e.editOrder(emf, orderId,availability,confirmed, dateOrd,payment,price,quantity,car,customer, seller);
        response.sendRedirect("OrderPages");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        EntityManager em = emf.createEntityManager();
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Del d = new Del();
        try {
            d.delOrder(emf, em.find(OrderEntity.class, orderId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("OrderPages");
    }
}
