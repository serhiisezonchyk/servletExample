package web;

import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import auth.UserCheck;
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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name="UserPageServlet", urlPatterns = {"/reeditUserCustomer","/deleteUserU","/ShowroomUser","/OrderUser","/CarsUser","/MessageOrderUser","/updateUserCustomer"})
public class UserPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/deleteUserU":
                    deleteUserU(request, response);
                    break;
                case "/reeditUserCustomer":
                    showUserCustomer(request, response);
                    break;
                case "/updateUserCustomer":
                    reeditUserCustomer(request, response);
                    break;
                case "/ShowroomUser":
                    showroomUser(request,response);
                    break;
                case "/OrderUser":
                    orderUser(request,response);
                    break;
                case "/CarsUser":
                    carsUser(request,response);
                    break;
                case "/MessageOrderUser":
                    messageOrderUser(request,response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void messageOrderUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCheck uc = new UserCheck();
        String log = (String) request.getSession().getAttribute("login");
        CustomerEntity customer = uc.getCustomerByLogin(log);
        request.setAttribute("currentCustomerUser", customer);

        ReqOrderEntity[] reqOrderEntities ;
        EntityList el = new EntityList();
        String query = "select o " +
                " from ReqOrderEntity o left  join o.customers s where s.customerId = "+customer.getCustomerId();
        System.out.println(query);
        reqOrderEntities = el.entitiesByQuery(emf, query).toArray(new ReqOrderEntity[0]);
        request.setAttribute("listReqOrder", reqOrderEntities);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/message-page.jsp");
        dispatcher.forward(request, response);
    }

    private void showUserCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCheck uc = new UserCheck();
        String pss = (String)request.getSession().getAttribute("password");
        String log = (String) request.getSession().getAttribute("login");
        CustomerEntity customer = uc.getCustomerByLogin(log);
        UsersEntity users = uc.getUserByLoginPassword(log,pss);
        request.setAttribute("currentCustomerUser", customer);
        request.setAttribute("currentUser", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/change-info-page.jsp");
        dispatcher.forward(request, response);
    }

    private void carsUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarEntity[] carEntities;
        EntityList el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/cars-to-order.jsp");
        dispatcher.forward(request, response);
    }

    private void orderUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderEntity[] orderEntities ;
        EntityList el = new EntityList();
        UserCheck uc = new UserCheck();
        String query = "select o " +
                " from OrderEntity o left  join o.customers s where s.customerId = "+uc.getCustomerByLogin((String) request.getSession().getAttribute("login")).getCustomerId();
        System.out.println(query);
        orderEntities = el.entitiesByQuery(emf, query).toArray(new OrderEntity[0]);
        request.setAttribute("listOrder", orderEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/orders-for-users.jsp");
        dispatcher.forward(request, response);
    }

    private void showroomUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShowroomEntity[] showroomEntities ;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPageActions/showroom-for-users.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void reeditUserCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        boolean legality = Boolean.parseBoolean(request.getParameter("legality"));
        Edit e = new Edit();
        e.editCustomer(emf, customerId,name,phone, legality);

        int usersId = Integer.parseInt(request.getParameter("usersId"));
        RolesEntity roles = em.find(RolesEntity.class, Integer.parseInt(request.getParameter("rolesId")));
        String password;
        String pass_help;
        if(request.getParameter("passwordNew").isEmpty()){
            password = request.getParameter("password");
            pass_help = request.getParameter("passHelp");
        }else{
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
            byte[] messageDigest = md.digest(request.getParameter("passwordNew").getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashpass = number.toString(16);
            password = hashpass;
            pass_help = request.getParameter("passHelpNew");
        }

        e.editUsers(emf,usersId,phone,password,roles,pass_help,name);
        em.close();
        response.sendRedirect("/logout");
    }

    private void deleteUserU(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int userId = Integer.parseInt(request.getParameter("usersId"));
        Del d = new Del();
        try {
            d.delUsers(emf, em.find(UsersEntity.class, userId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("/logout");
    }
}
