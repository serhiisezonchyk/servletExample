package web;

import GUI.Add;
import GUI.Del;
import GUI.Edit;
import GUI.EntityList;
import model.ArchiveSellerEntity;
import model.CarEntity;

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

@WebServlet(name = "CarServlet", urlPatterns = {"/CarPages", "/newCar", "/insertCar","/deleteCar", "/editCar", "/updateCar","/listCar"})
public class CarServlet extends HttpServlet {
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newCar":
                    showNewForm(request, response);
                    break;
                case "/insertCar":
                    insertCar(request, response);
                    break;
                case "/deleteCar":
                    deleteCar(request, response);
                    break;
                case "/editCar":
                    showEditForm(request, response);
                    break;
                case "/updateCar":
                    updateCar(request, response);
                    break;
                case "/listCar":
                    listCar(request, response);
                    break;
                default:
                    listCar(request, response);
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

    private void listCar(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        CarEntity[] carEntities;
        EntityList el = new EntityList();
        carEntities = el.readEntities(emf, "CarEntity").toArray(new CarEntity[0]);
        request.setAttribute("listCar", carEntities);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarPages/list-car.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarPages/car-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        EntityManager em = emf.createEntityManager();
        CarEntity existingCar = em.find(CarEntity.class, carId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarPages/car-form.jsp");
        request.setAttribute("car", existingCar);
        em.close();
        dispatcher.forward(request, response);

    }

    private void insertCar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String complectation = request.getParameter("complectation");
        float engine = Float.parseFloat(request.getParameter("engine"));
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int year = Integer.parseInt(request.getParameter("year"));
        Add add = new Add();
        add.saveCar(emf,complectation,engine,model,name,price,quantity,year);
        response.sendRedirect("listCar");
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        String complectation = request.getParameter("complectation");
        float engine = Float.parseFloat(request.getParameter("engine"));
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int year = Integer.parseInt(request.getParameter("year"));
        Edit e = new Edit();
        e.editCar(emf, carId ,complectation,engine,model,name,price,quantity,year);
        response.sendRedirect("listCar");
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EntityManager em = emf.createEntityManager();
        int carId = Integer.parseInt(request.getParameter("carId"));
        Del d = new Del();
        try {
            d.delCar(emf, em.find(CarEntity.class, carId));
        }catch (Exception e ){
            e.printStackTrace();
            em.getTransaction().rollback();
            em.close();
            request.setAttribute("exception",e.getCause().getCause().getCause().getMessage());
            request.getRequestDispatcher("/OtherJSP/Error.jsp").forward(request,response);
            return;
        }
        em.close();
        response.sendRedirect("listCar");
    }

}
