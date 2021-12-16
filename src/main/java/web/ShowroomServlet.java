package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
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

@WebServlet(name = "ShowroomServlet", urlPatterns = {"/ShowroomPages", "/newShowroom", "/insertShowroom","/deleteShowroom", "/editShowroom", "/updateShowroom","/listShowroom"})
public class ShowroomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newShowroom":
                    showNewForm(request, response);
                    break;
                case "/insertShowroom":
                    insertShowroom(request, response);
                    break;
                case "/deleteShowroom":
                    deleteShowroom(request, response);
                    break;
                case "/editShowroom":
                    showEditForm(request, response);
                    break;
                case "/updateShowroom":
                    updateShowroom(request, response);
                    break;
                case "/listShowroom":
                    listShowroom(request, response);
                    break;
                default:
                    listShowroom(request, response);
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

    private void listShowroom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        ShowroomEntity[] showroomEntities ;
        EntityList el = new EntityList();
        showroomEntities = el.readEntities(emf, "ShowroomEntity").toArray(new ShowroomEntity[0]);
        request.setAttribute("listShowroom", showroomEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowroomPages/list-showroom.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowroomPages/showroom-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int showroomId = Integer.parseInt(request.getParameter("showroomId"));
        EntityManager em = emf.createEntityManager();
        ShowroomEntity existingShowroom = em.find(ShowroomEntity.class, showroomId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowroomPages/showroom-form.jsp");
        request.setAttribute("showroom", existingShowroom);
        em.close();
        dispatcher.forward(request, response);

    }

    private void insertShowroom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String city = request.getParameter("city");//name
        String address = request.getParameter("address");//email
        String phone = request.getParameter("phone");//country
        Add add = new Add();
        add.saveShowroom(emf,city, address,phone);
        response.sendRedirect("ShowroomPages");
    }

    private void updateShowroom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int showroomId = Integer.parseInt(request.getParameter("showroomId"));
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Edit e = new Edit();
        e.editShowroom(emf, showroomId ,city, address,phone);
        response.sendRedirect("ShowroomPages");
    }

    private void deleteShowroom(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int showroomId = Integer.parseInt(request.getParameter("showroomId"));
        Del d = new Del();
        try {
            d.delShowroom(emf, em.find(ShowroomEntity.class, showroomId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("ShowroomPages");
    }

}

