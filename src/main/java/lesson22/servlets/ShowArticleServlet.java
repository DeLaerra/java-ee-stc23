package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.pojo.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/showarticle", name = "ShowArticle")
public class ShowArticleServlet extends HttpServlet {
    private ArticleDao articleDao;

    @Override
    public void init() throws ServletException {
        articleDao = (ArticleDao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String articleUuid = req.getParameter("id");
        if (articleUuid == null) {
            throw new ServletException("Missing parameter article_uuid");
        }
        Article article = articleDao.getArticleByUuid(articleUuid);
        if (article == null) {
            resp.setStatus(404);
            req.setAttribute("PageTitle", "Articles");
            req.setAttribute("PageBody", "notfound.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
            return;
        }
        req.setAttribute("article", article);
        req.setAttribute("PageTitle", "Articles");
        req.setAttribute("PageBody", "showarticle.jsp");
        req.getRequestDispatcher("/layout.jsp")
                .forward(req, resp);
    }
}
