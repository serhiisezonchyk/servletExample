package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.CarEntity;
import model.CustomerEntity;
import model.DeliveryEntity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@WebServlet(name = "DeliveryServlet", urlPatterns = {"/DeliveryPages", "/newDelivery", "/insertDelivery","/deleteDelivery", "/editDelivery", "/updateDelivery","/listDelivery"})
public class DeliveryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newDelivery":
                    showNewForm(request, response);
                    break;
                case "/insertDelivery":
                    insertDelivery(request, response);
                    break;
                case "/deleteDelivery":
                    deleteDelivery(request, response);
                    break;
                case "/editDelivery":
                    showEditForm(request, response);
                    break;
                case "/updateDelivery":
                    updateDelivery(request, response);
                    break;
                case "/listDelivery":
                    listDelivery(request, response);
                    break;
                default:
                    listDelivery(request, response);
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

    private void listDelivery(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        DeliveryEntity[] deliveryEntities ;
        EntityList el = new EntityList();
        deliveryEntities = el.readEntities(emf, "DeliveryEntity").toArray(new DeliveryEntity[0]);
        request.setAttribute("listDelivery", deliveryEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/DeliveryPages/list-delivery.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ShowroomEntity[] showroomEntities ;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);

        CarEntity[] carEntities ;
        el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/DeliveryPages/delivery-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
        EntityManager em = emf.createEntityManager();
        DeliveryEntity existingDelivery = em.find(DeliveryEntity.class, deliveryId);
        request.setAttribute("delivery", existingDelivery);

        ShowroomEntity[] showroomEntities;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        request.setAttribute("currentshowroomId", existingDelivery.getShowrooms().getShowroomId());

        CarEntity[] carEntities ;
        el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        request.setAttribute("currentcarId", existingDelivery.getCars().getCarId());
        em.close();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/DeliveryPages/delivery-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertDelivery(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        String addressFrom = request.getParameter("addressFrom");

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date.setTime(sdf.parse(request.getParameter("dateDel")));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String delivery_type = request.getParameter("deliveryType");
        float price = Float.parseFloat(request.getParameter("price"));
        ShowroomEntity showroom = em.find(ShowroomEntity.class, Integer.parseInt(request.getParameter("showroomId")));
        CarEntity car = em.find(CarEntity.class, Integer.parseInt(request.getParameter("carId")));
        Add add = new Add();
        add.saveDelivery(emf,addressFrom,date,delivery_type,price,showroom,car);
        em.close();
        response.sendRedirect("DeliveryPages");
    }

    private void updateDelivery(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
        String addressFrom = request.getParameter("addressFrom");

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date.setTime(sdf.parse(request.getParameter("dateDel")));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        String delivery_type = request.getParameter("deliveryType");
        float price = Float.parseFloat(request.getParameter("price"));
        ShowroomEntity showroom = em.find(ShowroomEntity.class, Integer.parseInt(request.getParameter("showroomId")));
        CarEntity car = em.find(CarEntity.class, Integer.parseInt(request.getParameter("carId")));
        Edit e = new Edit();
        e.editDelivery(emf, deliveryId,addressFrom,date,delivery_type,price,showroom,car);
        em.close();
        response.sendRedirect("DeliveryPages");
    }

    private void deleteDelivery(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
        Del d = new Del();
        try {
            d.delDelivery(emf, em.find(DeliveryEntity.class, deliveryId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("DeliveryPages");
    }
}
