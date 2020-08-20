package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.pojo.Article;
import lesson22.pojo.ArticleBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/addarticle")
public class AddArticleServlet extends HttpServlet {
        private ArticleDao articleDao;

        @Override
        public void init() throws ServletException {
            articleDao = (ArticleDao) getServletContext().getAttribute("dao");
            super.init();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("PageTitle", "New Article");
            req.setAttribute("PageBody", "form.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            req.setCharacterEncoding("utf-8");
            String header = req.getParameter("header");
            String text = req.getParameter("text");
            String date = req.getParameter("date");

            Article article = new ArticleBuilder()
                    .setArticle_uuid(UUID.randomUUID().toString())
                    .setAuthor_uuid(UUID.randomUUID().toString())
                    .setHeader(header)
                    .setText(text)
                    .setDate(date)
                    .setIsVisible("true")
                    .createArticle();

            articleDao.addArticle(article);

            resp.sendRedirect(req.getContextPath() + "/allarticles");
        }
}
