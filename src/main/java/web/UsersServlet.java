package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.CustomerEntity;
import model.RolesEntity;
import model.UsersEntity;

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
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = {"/UsersPages", "/newUsers", "/insertUsers","/deleteUsers", "/editUsers", "/updateUsers","/listUsers"})
public class UsersServlet extends HttpServlet{
        private static final long serialVersionUID = 1L;
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("java3");

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String action = request.getServletPath();
            try {
                switch (action) {
                    case "/newUsers":
                        showNewForm(request, response);
                        break;
                    case "/insertUsers":
                        insertUsers(request, response);
                        break;
                    case "/deleteUsers":
                        deleteUsers(request, response);
                        break;
                    case "/editUsers":
                        showEditForm(request, response);
                        break;
                    case "/updateUsers":
                        updateUsers(request, response);
                        break;
                    case "/listUsers":
                        listUsers(request, response);
                        break;
                    default:
                        listUsers(request, response);
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

        private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
            UsersEntity[] usersEntities = null;
            EntityList el = new EntityList();
            usersEntities = el.readEntities(emf, "UsersEntity").toArray(new UsersEntity[0]);
            request.setAttribute("listUsers", usersEntities);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPages/list-users.jsp");
            dispatcher.forward(request, response);
        }

        private void showNewForm(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            RolesEntity[] rolesEntities;
            EntityList el = new EntityList();
            rolesEntities = el.readEntities(emf, "RolesEntity").toArray(new RolesEntity[0]);
            request.setAttribute("listRoles", rolesEntities);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPages/users-form.jsp");
            dispatcher.forward(request, response);
        }

        private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            int usersId = Integer.parseInt(request.getParameter("usersId"));
            EntityManager em = emf.createEntityManager();
            UsersEntity existingUsers = em.find(UsersEntity.class, usersId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UsersPages/users-form.jsp");
            em.close();
            RolesEntity[] rolesEntities;
            EntityList el = new EntityList();
            rolesEntities = el.readEntities(emf, "RolesEntity").toArray(new RolesEntity[0]);
            request.setAttribute("listRoles", rolesEntities);
            request.setAttribute("users", existingUsers);
            request.setAttribute("currentrolesId", existingUsers.getRoles().getRolesId());
            dispatcher.forward(request, response);
        }

        private void insertUsers(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException {
            EntityManager em = emf.createEntityManager();
            RolesEntity roles = em.find(RolesEntity.class, Integer.parseInt(request.getParameter("rolesId")));
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String pass_help = request.getParameter("passHelp");
            String name = request.getParameter("name");
            Add add = new Add();
            add.saveUsers(emf, login,password,roles,pass_help,name);
            em.close();
            response.sendRedirect("UsersPages");
        }

        private void updateUsers(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException {
            EntityManager em = emf.createEntityManager();
            int usersId = Integer.parseInt(request.getParameter("usersId"));
            RolesEntity roles = em.find(RolesEntity.class, Integer.parseInt(request.getParameter("rolesId")));
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String pass_help = request.getParameter("passHelp");
            String name = request.getParameter("name");
            Edit e = new Edit();
            e.editUsers(emf,usersId,login,password,roles,pass_help,name);
            em.close();
            response.sendRedirect("UsersPages");
        }

        private void deleteUsers(HttpServletRequest request, HttpServletResponse response)
                throws Exception {
            EntityManager em = emf.createEntityManager();
            int usersId = Integer.parseInt(request.getParameter("usersId"));
            Del d = new Del();
            try {
                d.delUsers(emf, em.find(UsersEntity.class, usersId));
            }catch (Exception e ){
                e.printStackTrace();
                em.getTransaction().rollback();
                em.close();
                request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
                request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
                return;
            }
            em.close();
            response.sendRedirect("UsersPages");
        }


}
