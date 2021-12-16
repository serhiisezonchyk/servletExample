package auth;

import GUI.Add;
import GUI.Edit;
import model.RolesEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationPage","/RegistryNewUser"})
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("java3");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/RegistrationPage":
                showRegistrationPage(request,response);
                break;
            case "/RegistryNewUser":
                registryNewUser(request,response);
                break;
        }
    }

    private void registryNewUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserCheck uc = new UserCheck();
        EntityManager em = emf.createEntityManager();
        String name = request.getParameter("name");
        String phone = request.getParameter("login");
        if(uc.usernameIsExist(phone)){
            request.getRequestDispatcher("/WEB-INF/view/loginExistPage.jsp").forward(request, response);
            em.close();
            return;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        byte[] messageDigest = md.digest(request.getParameter("password").getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashpass = number.toString(16);
        String password = hashpass;
        boolean legality = Boolean.parseBoolean(request.getParameter("legality"));
        Add a = new Add();
        a.saveCustomer(emf,name,phone, legality);

        RolesEntity roles = em.find(RolesEntity.class, Integer.parseInt(request.getParameter("rolesId")));
        String login = request.getParameter("login");
        String pass_help = request.getParameter("passHelp");
        a.saveUsers(emf,login,password,roles,pass_help,name);
        em.close();
        response.sendRedirect("/");
    }

    private void showRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
