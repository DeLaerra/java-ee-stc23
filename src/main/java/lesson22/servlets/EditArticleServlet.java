package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.pojo.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editarticle")
public class EditArticleServlet extends HttpServlet {
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
            req.setAttribute("PageBody", "notfound.jsp");
            req.getRequestDispatcher("/layout.jsp")
                    .forward(req, resp);
            return;
        }
        req.setAttribute("article", article);
        req.setAttribute("PageTitle", "Изменение статьи");
        req.setAttribute("PageBody", "form.jsp");
        req.getRequestDispatcher("/editarticle.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String article_uuid = req.getParameter("article_uuid");
        String article_header = req.getParameter("article_header");
        String article_text = req.getParameter("article_text");

        Article article = articleDao.getArticleByUuid(article_uuid);
        articleDao.editArticle(article, article_header, article_text);

        resp.sendRedirect(req.getContextPath() + "/allarticles");

    }


}
