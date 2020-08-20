package lesson22.servlets;

import lesson22.dao.UserDao;
import lesson22.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edituser")
public class EditUserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("daoU");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userUuid = req.getParameter("id");
        if (userUuid == null) {
            throw new ServletException("Missing parameter user_uuid");
        }
        User user = userDao.getUserByUuid(userUuid);
        if (user == null) {
            resp.setStatus(404);
            req.setAttribute("PageBody", "notfound.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
            return;
        }
        req.setAttribute("user", user);
        req.setAttribute("PageTitle", "Изменение пользователя");
//        req.setAttribute("PageBody", "form.jsp");
        req.getRequestDispatcher("/edituser.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user_uuid = req.getParameter("user_uuid");
        String user_nickname = req.getParameter("user_nickname");
        String user_password = req.getParameter("user_password");
        String user_email = req.getParameter("user_email");


        User user = userDao.getUserByUuid(user_uuid);
        userDao.editUser(user, user_nickname, user_password, user_email);

        resp.sendRedirect(req.getContextPath() + "/person/list");

    }
    
}
