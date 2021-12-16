package auth;

import model.UsersEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.nonNull;

/**
 * Acidification filter.
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {
        UserCheck uc = new UserCheck();
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password;
        if(req.getParameter("password") == null){
            password = null;
        }else{
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] messageDigest = md.digest(req.getParameter("password").getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashpass = number.toString(16);
            password = hashpass;
        }


        final HttpSession session = req.getSession();

        //Logged user.
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {

            final String role = session.getAttribute("roleUser").toString();

            moveToMenu(req, res, role);


        } else if (uc.userIsExist(login, password)) {

            final UsersEntity users = uc.getUserByLoginPassword(login, password);

            req.getSession().setAttribute("password", users.getPassword());
            req.getSession().setAttribute("login", users.getLogin());
            req.getSession().setAttribute("roleUser", users.getRoles().getRole());
            req.getSession().setAttribute("rroleId", users.getRoles().getRolesId());
            req.getSession().setAttribute("nameUser", users.getName());
            req.getSession().setAttribute("uuserId", users.getUsersId());
            req.getSession().setAttribute("currentUsers", users);
            moveToMenu(req, res, users.getRoles().getRole());

        } else {
            moveToMenu(req, res,"unknown");
        }
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final String role)
            throws ServletException, IOException {


        if (role.equals("admin")) {
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, res);
        } else if (role.equals("user")) {
            req.getRequestDispatcher("/WEB-INF/view/userindex.jsp").forward(req, res);
        }else if(role.equals("manager")){
            req.getRequestDispatcher("/WEB-INF/view/sellerindex.jsp").forward(req, res);
        }else if(role.equals("analyth")){
            req.getRequestDispatcher("/WEB-INF/view/analythindex.jsp").forward(req, res);
        }else if (role.equals("call_manager")){
            req.getRequestDispatcher("/WEB-INF/view/call_managerindex.jsp").forward(req, res);
        }else {
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }
    @Override
    public void destroy() {
    }

}