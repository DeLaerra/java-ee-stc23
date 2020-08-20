package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.pojo.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deletearticle")
public class DeleteArticleServlet extends HttpServlet {
    private ArticleDao articleDao;

    @Override
    public void init() throws ServletException {
        articleDao = (ArticleDao) getServletContext().getAttribute("dao");
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String article_uuid = req.getParameter("article_uuid");

        Article article = articleDao.getArticleByUuid(article_uuid);
        articleDao.deleteArticle(article);

        resp.sendRedirect(req.getContextPath() + "/allarticles");

    }
}
