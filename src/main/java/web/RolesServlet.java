package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.RolesEntity;

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

@WebServlet(name = "RolesServlet", urlPatterns = {"/RolesPages", "/listRoles"})
public class RolesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/listRoles":
                    listRoles(request, response);
                    break;
                default:
                    listRoles(request, response);
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

    private void listRoles(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        RolesEntity[] rolesEntities ;
        EntityList el = new EntityList();
        rolesEntities = el.readEntities(emf, "RolesEntity").toArray(new RolesEntity[0]);
        request.setAttribute("listRoles", rolesEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/RolesPages/list-roles.jsp");
        dispatcher.forward(request, response);
    }


}
