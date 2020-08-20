package lesson22.dao;

import lesson22.pojo.Article;

import java.util.List;

public interface ArticleDao {
    boolean addArticle(Article article);

    boolean deleteArticle(Article article);

    boolean editArticle(Article article, String header, String text);

    Article getArticleByUuid(String uuid);

    List<Article> getAllArticles();
}
