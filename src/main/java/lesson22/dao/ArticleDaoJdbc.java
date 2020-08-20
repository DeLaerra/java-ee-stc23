package lesson22.dao;


import lesson22.connectionManager.ConnectionManager;
import lesson22.pojo.Article;
import lesson22.pojo.ArticleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EJB
public class ArticleDaoJdbc implements ArticleDao {
    private final Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDaoJdbc.class);
    private static final String INSERT_INTO_ARTICLE = "INSERT INTO article values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_FROM_ARTICLE = "SELECT * FROM article WHERE article_uuid = ?";
    private static final String SELECT_ALL_FROM_ARTICLE = "SELECT * FROM article";
    private static final String UPDATE_ARTICLE = "UPDATE article SET header=?, text=? WHERE article_uuid=?";
    private static final String DELETE_FROM_ARTICLE = "DELETE FROM article WHERE article_uuid=?";


    @Inject
    public ArticleDaoJdbc(ConnectionManager connectionManager) {
        this.connection = connectionManager.getConnection();
    }


    @Override
    public boolean addArticle(Article article) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ARTICLE)) {
            preparedStatement.setString(1, article.getHeader());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setString(3, article.getDate());
            preparedStatement.setString(4, article.getAuthor_uuid());
            preparedStatement.setString(5, article.isApproved());
            preparedStatement.setString(6, article.isVisible());
            preparedStatement.setString(7, article.isCommercial());
            preparedStatement.setString(8, article.getArticle_uuid());
            preparedStatement.executeUpdate();
            LOGGER.info("Статья " + article.getArticle_uuid() + " " + article.getHeader() + " добавлена.");
        } catch (SQLException e) {
            LOGGER.error("Попытка добавления новой статьи неудачна!", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteArticle(Article article) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ARTICLE)) {
            preparedStatement.setString(1, article.getArticle_uuid());
            preparedStatement.executeUpdate();
            LOGGER.info("Статья " + article.getHeader() + " удалена.");
        } catch (SQLException e) {
            LOGGER.error("Попытка удаления статьи " + article.getArticle_uuid() + " " + article.getHeader() + " неудачна!", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean editArticle(Article article, String newHeader, String newText) {
        try (PreparedStatement psUpdate = connection.prepareStatement(UPDATE_ARTICLE)) {
            psUpdate.setString(1, newHeader);
            psUpdate.setString(2, newText);
            psUpdate.setString(3, article.getArticle_uuid());
            psUpdate.executeUpdate();

            LOGGER.info("Статья " + article.getArticle_uuid() + " " + newHeader + " изменена.");
        } catch (SQLException e) {
            LOGGER.error("Попытка изменения статьи " + article.getArticle_uuid() + " неудачна", e);
            return false;
        }
        return true;
    }

    @Override
    public Article getArticleByUuid(String uuid) {
        try (PreparedStatement psSelect = connection.prepareStatement(SELECT_FROM_ARTICLE)) {
            psSelect.setString(1, uuid);
            try (ResultSet resultSet = psSelect.executeQuery()) {
                if (resultSet.next()) {
                    return new ArticleBuilder()
                            .setArticle_uuid(resultSet.getString(8))
                            .setHeader(resultSet.getString(1))
                            .setText(resultSet.getString(2))
                            .setDate(resultSet.getString(3))
                            .setAuthor_uuid(resultSet.getString(4))
                            .setIsApproved(resultSet.getString(5))
                            .setIsVisible(resultSet.getString(6))
                            .setIsCommercial(resultSet.getString(7))
                            .createArticle();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Выполнение запроса SELECT_FROM_ARTICLE закончилось неудачей", e);
        }
        return null;
    }


    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        try (PreparedStatement psSelect = connection.prepareStatement(SELECT_ALL_FROM_ARTICLE);
             ResultSet resultSet = psSelect.executeQuery()) {
            while (resultSet.next()) {
                articles.add(new ArticleBuilder()
                        .setArticle_uuid(resultSet.getString(8))
                        .setHeader(resultSet.getString(1))
                        .setText(resultSet.getString(2))
                        .setDate(resultSet.getString(3))
                        .setAuthor_uuid(resultSet.getString(4))
                        .setIsApproved(resultSet.getString(5))
                        .setIsVisible(resultSet.getString(6))
                        .setIsCommercial(resultSet.getString(7))
                        .createArticle());
            }
            return articles;
        } catch (SQLException e) {
            LOGGER.error("Выполнение запроса SELECT_ALL_FROM_ARTICLE закончилось неудачей", e);
        }
        return new ArrayList<>();
    }
}
