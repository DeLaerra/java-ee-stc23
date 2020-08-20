package lesson22.servlets;

import lesson22.dao.UserDao;
import lesson22.pojo.User;
import lesson22.pojo.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("daoU");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("PageTitle", "New User");
        req.getRequestDispatcher("/adduser.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String user_nickname = req.getParameter("user_nickname");
        String user_password = req.getParameter("user_password");
        String user_email = req.getParameter("user_email");

        User user = new UserBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setNickname(user_nickname)
                .setPassword(user_password)
                .setEmail(user_email).createUser();

        userDao.addUser(user);

        resp.sendRedirect(req.getContextPath() + "/person/list");
    }


}
