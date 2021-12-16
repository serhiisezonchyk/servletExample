package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.ReqToCallManagerEntity;
import model.SellerEntity;
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

@WebServlet(name = "SellerServlet",urlPatterns = {"/SellerPages", "/newSeller", "/insertSeller","/deleteSeller","/editSeller", "/updateSeller","/listSeller"})
public class SellerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);
        try {
            switch (action) {
                case "/newSeller":{
                    showNewForm(request, response);
                    break;}
                case "/insertSeller":
                    insertSeller(request, response);
                    break;
                case "/deleteSeller":
                    deleteSeller(request, response);
                    break;
                case "/editSeller":
                    showEditForm(request, response);
                    break;
                case "/updateSeller":
                    updateSeller(request, response);
                    break;
                case "/listSeller":
                    listSeller(request, response);
                    break;
                default:
                    listSeller(request, response);
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

    private void listSeller(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        SellerEntity[] sellerEntities ;
        EntityList el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);
        request.getRequestDispatcher("/SellerPages/list-seller.jsp").forward(request,response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShowroomEntity[] showroomEntities;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/SellerPages/seller-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        EntityManager em = emf.createEntityManager();
        SellerEntity existingSeller = em.find(SellerEntity.class, sellerId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/SellerPages/seller-form.jsp");
        em.close();
        ShowroomEntity[] showroomEntities;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        request.setAttribute("seller", existingSeller);
        request.setAttribute("currentshowroomId", existingSeller.getShowrooms().getShowroomId());
        dispatcher.forward(request, response);
    }

    private void insertSeller(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String phone = request.getParameter("phone");
        ShowroomEntity showroom = em.find(ShowroomEntity.class, Integer.parseInt(request.getParameter("showroomId")));
        Add add = new Add();
        add.saveSeller(emf,name,phone, position,showroom);
        em.close();
        response.sendRedirect("SellerPages");
    }

    private void updateSeller(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String position = request.getParameter("position");
        ShowroomEntity showroom = em.find(ShowroomEntity.class,Integer.parseInt(request.getParameter("showroomId")));
        Edit e = new Edit();
        e.editSeller(emf, sellerId,name,phone, position,showroom);
        em.close();
        response.sendRedirect("SellerPages");
    }

    private void deleteSeller(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        Del d = new Del();
        try {
            d.delSeller(emf, em.find(SellerEntity.class, sellerId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("SellerPages");
    }
}
