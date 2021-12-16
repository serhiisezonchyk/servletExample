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

@WebServlet(name = "SellerPageServlet", urlPatterns = {"/ShowroomSeller", "/CarsSeller", "/OrderSeller","/MessageCallSeller"})
public class SellerPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/ShowroomSeller":
                showroomSeller(request,response);
                break;
            case "/OrderSeller":
                orderSeller(request,response);
                break;
            case "/CarsSeller":
                carsSeller(request,response);
                break;
            case "/MessageCallSeller":
                messageCallSeller(request,response);
                break;
        }
    }

    private void messageCallSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCheck uc = new UserCheck();
        String log = (String) request.getSession().getAttribute("login");
        SellerEntity seller = uc.getSellerByLogin(log);
        request.setAttribute("currentSellerUser", seller);

        ReqToCallManagerEntity[] reqToCallManager;
        EntityList el = new EntityList();
        String query = "select o " +
                " from ReqToCallManagerEntity o left  join o.sellers s where s.sellerId = "+seller.getSellerId();
        System.out.println(query);
        reqToCallManager = el.entitiesByQuery(emf, query).toArray(new ReqToCallManagerEntity[0]);
        request.setAttribute("listReqToCallManager", reqToCallManager);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/SellerPageAction/message-to-call.jsp");
        dispatcher.forward(request, response);
    }

    private void carsSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarEntity[] carEntities;
        EntityList el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/cars-to-order.jsp");
        dispatcher.forward(request, response);
    }

    private void orderSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderEntity[] orderEntities ;
        EntityList el = new EntityList();
        UserCheck uc = new UserCheck();
        String query = "select o " +
                " from OrderEntity o left  join o.sellers s where s.sellerId = "+uc.getSellerByLogin((String) request.getSession().getAttribute("login")).getSellerId();
        System.out.println(query);
        orderEntities = el.entitiesByQuery(emf, query).toArray(new OrderEntity[0]);
        request.setAttribute("listOrder", orderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/orders-for-users.jsp");
        dispatcher.forward(request, response);
    }

    private void showroomSeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShowroomEntity[] showroomEntities ;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/showroom-for-users.jsp");
        dispatcher.forward(request, response);
    }
}
