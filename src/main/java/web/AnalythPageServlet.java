package web;

import GUI.EntityList;
import auth.UserCheck;
import model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AnalythPageServlet", urlPatterns = {"/ShowroomAnalyth", "/CarsAnalyth","/OrderAnalyth","/SellerAnalyth"})
public class AnalythPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/ShowroomAnalyth":
                showroomAnalyth(request,response);
                break;
            case "/OrderAnalyth":
                orderAnalyth(request,response);
                break;
            case "/CarsAnalyth":
                carsAnalyth(request,response);
                break;
            case "/SellerAnalyth":
                sellerAnalyth(request,response);
                break;
        }
    }

    private void sellerAnalyth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SellerEntity[] sellerEntities ;
        EntityList el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);
        request.getRequestDispatcher("/UsersPageActions/seller-for-user.jsp").forward(request,response);
    }


    private void carsAnalyth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarEntity[] carEntities;
        EntityList el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/cars-to-order.jsp");
        dispatcher.forward(request, response);
    }

    private void orderAnalyth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderEntity[] orderEntities ;
        EntityList el = new EntityList();
        UserCheck uc = new UserCheck();
        orderEntities = el.readEntities(emf, "OrderEntity").toArray(new OrderEntity[0]);
        request.setAttribute("listOrder", orderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/orders-for-users.jsp");
        dispatcher.forward(request, response);
    }

    private void showroomAnalyth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShowroomEntity[] showroomEntities ;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/showroom-for-users.jsp");
        dispatcher.forward(request, response);
    }
}
