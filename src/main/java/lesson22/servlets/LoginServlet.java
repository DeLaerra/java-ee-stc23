package lesson22.servlets;

import lesson22.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("daoU");
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_email = req.getParameter("user_email");
        String user_password = req.getParameter("user_password");


       if (userDao.getUserByEmailAndPassword(user_email, user_password)) {
           req.setAttribute("PageBody", "main.jsp");
           req.getRequestDispatcher("/layout.jsp")
                   .forward(req, resp);
        }
        else {
            resp.setStatus(403);
            req.setAttribute("PageBody", "forbidden.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
        }

    }
}
