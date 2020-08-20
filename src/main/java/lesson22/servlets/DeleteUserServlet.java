package lesson22.servlets;

import lesson22.dao.UserDao;
import lesson22.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteuser", urlPatterns = "/person/deleteuser")
public class DeleteUserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("daoU");
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user_uuid = req.getParameter("user_uuid");

        User user = userDao.getUserByUuid(user_uuid);
        userDao.deleteUser(user);

        resp.sendRedirect(req.getContextPath() + "/person/list");

    }


}
