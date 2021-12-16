package auth;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final HttpSession session = req.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        session.removeAttribute("roleUser");
        session.removeAttribute("nameUser");
        session.removeAttribute("currentUsers");
        session.removeAttribute("uuserId");
        session.removeAttribute("rroleId");
        resp.sendRedirect("/");
    }
}
