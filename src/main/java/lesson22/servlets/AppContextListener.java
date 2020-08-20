package lesson22.servlets;

import lesson22.dao.ArticleDao;
import lesson22.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    @Inject
    private ArticleDao articleDao;

    @Inject
    private UserDao userDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String isDao = servletContext.getInitParameter("isDao");
        if (isDao.equals("true")) {
            servletContext.setAttribute("dao", articleDao);
            servletContext.setAttribute("daoU", userDao);
            logger.info("Added attributes DAO");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.removeAttribute("dao");
        servletContext.removeAttribute("daoU");
        logger.info("Removed attribute DAO");
    }
}
