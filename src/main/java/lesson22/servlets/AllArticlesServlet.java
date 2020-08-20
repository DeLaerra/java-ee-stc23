package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.pojo.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(value = "/allarticles", name = "Articles")
public class AllArticlesServlet extends HttpServlet {
    private ArticleDao articleDao;
    private Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void init() throws ServletException {
        articleDao = (ArticleDao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Article> articles = articleDao.getAllArticles();
        req.setAttribute("articles", articles);
        req.setAttribute("PageTitle", "Articles");
        req.setAttribute("PageBody", "allarticles.jsp");
        req.getRequestDispatcher("/layout.jsp")
                .forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String article_uuid = req.getParameter("article_uuid");

        Article article = articleDao.getArticleByUuid(article_uuid);
        articleDao.deleteArticle(article);

        resp.sendRedirect(req.getContextPath() + "/allarticles");

    }

}
