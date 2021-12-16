package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.ArchiveOrderEntity;
import model.ArchiveSellerEntity;
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

@WebServlet(name = "ArchiveSellerServlet",urlPatterns = {"/ArchiveSellerPages", "/deleteArchiveSeller","/listArchiveSeller"})
public class ArchiveSellerPageServlet extends HttpServlet {
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
                case "/deleteArchiveSeller":
                    deleteArchiveSeller(request, response);
                    break;
                case "/listArchiveSeller":
                    listArchiveSeller(request, response);
                    break;
                default:
                    listArchiveSeller(request, response);
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

    private void listArchiveSeller(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        ArchiveSellerEntity[] archiveSellerEntities ;
        EntityList el = new EntityList();
        archiveSellerEntities = el.readEntities(emf, "ArchiveSellerEntity").toArray(new ArchiveSellerEntity[0]);
        request.setAttribute("listArchiveSeller", archiveSellerEntities);
        request.getRequestDispatcher("/ArchiveSellerPages/list-archiveSeller.jsp").forward(request,response);
    }

    private void deleteArchiveSeller(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int archiveSellerId = Integer.parseInt(request.getParameter("archiveSellerId"));
        Del d = new Del();
        try {
            d.delArchiveSeller(emf, em.find(ArchiveSellerEntity.class, archiveSellerId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("ArchiveSellerPages");
    }

}
