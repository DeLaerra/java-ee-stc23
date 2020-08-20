package lesson22.servlets;

import lesson22.dao.UserDao;
import lesson22.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "personList", urlPatterns = "/person/list")
public class ListUserServlet extends HttpServlet {
    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("daoU");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.getAllUsers();
        req.setAttribute("users", users);
        req.setAttribute("PageTitle", "Users");
        req.setAttribute("PageBody", "persons.jsp");
        req.getRequestDispatcher("/layout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user_uuid = req.getParameter("user_uuid");

        User user = userDao.getUserByUuid(user_uuid);
        userDao.deleteUser(user);

        resp.sendRedirect(req.getContextPath() + "/person/list");
    }


}
