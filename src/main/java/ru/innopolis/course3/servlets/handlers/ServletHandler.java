package ru.innopolis.course3.servlets.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.innopolis.course3.models.article.ArticleService;
import ru.innopolis.course3.models.comment.CommentService;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Danil Popov
 */
public abstract class ServletHandler {

    //private ApplicationContext context =
            //new ClassPathXmlApplicationContext("spring.xml");

    @Autowired
    @Qualifier("articleService")
    private ArticleService articleService;

    @Autowired
    @Qualifier("commentService")
    private CommentService commentService;// = context
            //.getBean("commentService", CommentService.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;// = context
            //.getBean("userService", UserService.class);
    
    public ArticleService getArticleService() {
        return articleService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public UserService getUserService() {
        return userService;
    }

    public abstract String getPageAddress(HttpServletRequest req, HttpServletResponse resp);

    String handleError(HttpServletRequest req, String message) {
        req.setAttribute("error_message", message);
        return "error_page";
    }
}
