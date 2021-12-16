package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.ReqOrderEntity;
import model.SellerEntity;
import model.ReqToCallManagerEntity;

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

@WebServlet(name = "ReqToCallManagerServlet", urlPatterns = {"/ReqToCallManagerPages", "/newReqToCallManager", "/insertReqToCallManager","/deleteReqToCallManager", "/editReqToCallManager", "/updateReqToCallManager","/listReqToCallManager"})
public class ReqToCallManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newReqToCallManager":
                    showNewForm(request, response);
                    break;
                case "/insertReqToCallManager":
                    insertReqToCallManager(request, response);
                    break;
                case "/deleteReqToCallManager":
                    deleteReqToCallManager(request, response);
                    break;
                case "/editReqToCallManager":
                    showEditForm(request, response);
                    break;
                case "/updateReqToCallManager":
                    updateReqToCallManager(request, response);
                    break;
                case "/listReqToCallManager":
                    listReqToCallManager(request, response);
                    break;
                default:
                    listReqToCallManager(request, response);
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

    private void listReqToCallManager(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        ReqToCallManagerEntity[] reqToCallManagerEntities = null;
        EntityList el = new EntityList();
        reqToCallManagerEntities = el.readEntities(emf, "ReqToCallManagerEntity").toArray(new ReqToCallManagerEntity[0]);
        request.setAttribute("listReqToCallManager", reqToCallManagerEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqToCallManagerPages/list-reqToCallManager.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SellerEntity[] sellerEntities;
        EntityList el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqToCallManagerPages/reqToCallManager-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int reqToCallManagerId = Integer.parseInt(request.getParameter("reqToCallManagerId"));
        EntityManager em = emf.createEntityManager();
        ReqToCallManagerEntity existingReqToCallManager = em.find(ReqToCallManagerEntity.class, reqToCallManagerId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ReqToCallManagerPages/reqToCallManager-form.jsp");
        em.close();
        SellerEntity[] sellerEntities;
        EntityList el = new EntityList();
        sellerEntities = el.readEntities(emf, "SellerEntity").toArray(new SellerEntity[0]);
        request.setAttribute("listSeller", sellerEntities);
        request.setAttribute("reqToCallManager", existingReqToCallManager);
        request.setAttribute("currentsellerId", existingReqToCallManager.getSellers().getSellerId());
        dispatcher.forward(request, response);
    }

    private void insertReqToCallManager(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        SellerEntity seller = em.find(SellerEntity.class, Integer.parseInt(request.getParameter("sellerId")));
        String requests = request.getParameter("request");
        boolean processed = Boolean.parseBoolean(request.getParameter("processed"));
        Add add = new Add();
        add.saveReqToCallManager(emf, seller,requests,processed);
        em.close();
        response.sendRedirect("/");
    }

    private void updateReqToCallManager(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        EntityManager em = emf.createEntityManager();
        int reqToCallManagerId = Integer.parseInt(request.getParameter("reqToCallManagerId"));
        SellerEntity seller = em.find(SellerEntity.class, Integer.parseInt(request.getParameter("sellerId")));
        String requests = request.getParameter("request");
        boolean processed = Boolean.parseBoolean(request.getParameter("processed"));
        Edit e = new Edit();
        e.editReqToCallManager(emf,reqToCallManagerId, seller,requests,processed);
        em.close();
        response.sendRedirect("ReqToCallManagerPages");
    }

    private void deleteReqToCallManager(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int reqToCallManagerId = Integer.parseInt(request.getParameter("reqToCallManagerId"));
        Del d = new Del();
        try {
            d.delReqToCallManager(emf, em.find(ReqToCallManagerEntity.class, reqToCallManagerId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("ReqToCallManagerPages");
    }
}
