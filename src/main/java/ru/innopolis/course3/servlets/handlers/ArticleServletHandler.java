package ru.innopolis.course3.servlets.handlers;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleService;
import ru.innopolis.course3.models.comment.Comment;
import ru.innopolis.course3.models.comment.CommentService;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Danil Popov
 */
public abstract class ArticleServletHandler extends ServletHandler {

    public static ArticleServletHandler newHandler(String code) {
        switch (code) {
            case "add_article":
                return new AddArticleHandler();
            case "confirm_add_article":
                return new ConfirmAddArticleHandler();
            case "edit_article":
                return new EditArticleHandler();
            case "confirm_edit_article":
                return new ConfirmEditArticleHandler();
            case "delete_article":
                return new DeleteArticleHandler();
            case "view_more":
                return new ViewMoreArticleHandler();
            case "send_comment":
                return new SendCommentHandler();
            case "edit_comment":
                return new EditCommentHandler();
            case "confirm_edit_comment":
                return new ConfirmEditCommentHandler();
            case "delete_comment":
                return new DeleteCommentHandler();
            default:
                return new DefaultArticleHandler();
        }
    }

    void handleDeleteComment(HttpServletRequest req) throws DBException {
        CommentService.removeCommentById(
                Integer.parseInt(req.getParameter("comment_id")),
                Long.parseLong(req.getParameter("comment_update_date"))
        );
    }

    void handleEditComment(HttpServletRequest req) throws DBException {
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(req.getParameter("comment_id")));
        comment.setSource(req.getParameter("comment_source"));
        comment.setDate(Long.parseLong(req.getParameter("comment_date")));
        comment.setUpdateDate(Long.parseLong(req.getParameter("comment_update_date")));
        comment.setArticleId(Integer.parseInt(req.getParameter("article_id")));
        comment.setUser(UserService.getUserByName(req.getParameter("user_name")));
        CommentService.updateComment(comment);
    }

    void setCommentForEdit(HttpServletRequest req) throws DBException {
        setCommentsForArticle(req);
        req.setAttribute("edit_comment_id", req.getParameter("comment_id"));
    }

    void handleSendComment(HttpServletRequest req) throws DBException {
        long date = System.currentTimeMillis();
        Comment comment = new Comment();
        comment.setSource(req.getParameter("comment_source"));
        comment.setDate(date);
        comment.setUpdateDate(date);
        comment.setArticleId(Integer.parseInt(req.getParameter("article_id")));
        comment.setUser(UserService.getUserByName(req.getParameter("user_name")));
        CommentService.addNewComment(comment);
    }

    void setCommentsForArticle(HttpServletRequest req) throws DBException {
        req.setAttribute("comments", CommentService.getCommentsByArticleId(
                Integer.parseInt(req.getParameter("article_id"))
                )
        );
    }

    void handleDeleteArticle(HttpServletRequest req) throws DBException {
        ArticleService.removeArticleById(
                Integer.parseInt(req.getParameter("article_id")),
                Long.parseLong(req.getParameter("article_update_date"))
        );
    }

    void setArticle(HttpServletRequest req) throws DBException {
        Article article = ArticleService.getArticleById(
                Integer.parseInt(req.getParameter("article_id"))
        );
        req.setAttribute("article", article);
    }

    void handleEditArticle(HttpServletRequest req) throws DBException {
        Article article = new Article();
        article.setId(Integer.parseInt(req.getParameter("article_id")));
        article.setTitle(req.getParameter("article_title"));
        article.setSource(req.getParameter("article_source"));
        article.setAuthor(
                UserService.getUserById(
                        Integer.parseInt(req.getParameter("article_user_id"))
                )
        );
        article.setDate(
                Long.parseLong(req.getParameter("article_date"))
        );
        article.setUpdateDate(
                Long.parseLong(req.getParameter("article_update_date"))
        );
        ArticleService.updateArticle(article);
    }

    void setAllArticles(HttpServletRequest req) throws DBException {
        req.setAttribute("articles", ArticleService.getAllArticles());
    }

    void handleAddArticle(HttpServletRequest req) throws DBException {
        long date = System.currentTimeMillis();
        Article article = new Article();
        article.setTitle(req.getParameter("article_title"));
        article.setSource(req.getParameter("article_source"));
        article.setDate(date);
        article.setAuthor(UserService.getUserByName(
                (String) req.getSession().getAttribute("login_id"))
        );
        article.setUpdateDate(date);
        ArticleService.addNewArticle(article);
    }
}

class AddArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        return "/articles/add_article.jsp";
    }
}

class ConfirmAddArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleAddArticle(req);
            setAllArticles(req);
            return "/articles/articles.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class EditArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setArticle(req);
            return "/articles/edit_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmEditArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleEditArticle(req);
            setAllArticles(req);
            return "/articles/articles.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DeleteArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleDeleteArticle(req);
            setAllArticles(req);
            return "/articles/articles.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ViewMoreArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setArticle(req);
            setCommentsForArticle(req);
            return "/articles/view_more_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class SendCommentHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleSendComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            return "/articles/view_more_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class EditCommentHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setArticle(req);
            setCommentForEdit(req);
            return "/articles/view_more_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class ConfirmEditCommentHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleEditComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            return "/articles/view_more_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DeleteCommentHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            handleDeleteComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            return "/articles/view_more_article.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}

class DefaultArticleHandler extends ArticleServletHandler {

    @Override
    public String getPageAddress(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setAllArticles(req);
            return "/articles/articles.jsp";
        } catch (DBException e) {
            return handleError(req, e.getMessage());
        }
    }
}
