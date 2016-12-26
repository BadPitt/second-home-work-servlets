package ru.innopolis.course3.servlets;

import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleService;
import ru.innopolis.course3.models.comment.Comment;
import ru.innopolis.course3.models.comment.CommentService;
import ru.innopolis.course3.models.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by danil on 25/12/16.
 */
public class ArticlesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");

        if ("add_article".equals(button)) {
            getServletContext().getRequestDispatcher("/articles/add_article.jsp").forward(req, resp);
        } else if ("confirm_add_article".equals(button)) {
            handleAddArticle(req);
            setAllArticles(req);
            getServletContext().getRequestDispatcher("/articles/articles.jsp").forward(req, resp);
        } else if ("edit_article".equals(button)) {
            setArticle(req);
            getServletContext().getRequestDispatcher("/articles/edit_article.jsp").forward(req, resp);
        } else if ("confirm_edit_article".equals(button)) {
            handleEditArticle(req);
            setAllArticles(req);
            getServletContext().getRequestDispatcher("/articles/articles.jsp").forward(req, resp);
        } else if ("delete_article".equals(button)) {
            handleDeleteArticle(req);
            setAllArticles(req);
            getServletContext().getRequestDispatcher("/articles/articles.jsp").forward(req, resp);
        } else if ("view_more".equals(button)) {
            setArticle(req);
            setCommentsForArticle(req);
            getServletContext().getRequestDispatcher("/articles/view_more_article.jsp").forward(req, resp);
        } else if ("send_comment".equals(button)) {
            handleSendComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            getServletContext().getRequestDispatcher("/articles/view_more_article.jsp").forward(req, resp);
        } else if ("edit_comment".equals(button)) {
            setArticle(req);
            setCommentForEdit(req);
            getServletContext().getRequestDispatcher("/articles/view_more_article.jsp").forward(req, resp);
        } else if ("confirm_edit_comment".equals(button)) {
            handleEditComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            getServletContext().getRequestDispatcher("/articles/view_more_article.jsp").forward(req, resp);
        }  else if ("delete_comment".equals(button)) {
            handleDeleteComment(req);
            setArticle(req);
            setCommentsForArticle(req);
            getServletContext().getRequestDispatcher("/articles/view_more_article.jsp").forward(req, resp);
        } else {
            setAllArticles(req);
            getServletContext().getRequestDispatcher("/articles/articles.jsp").forward(req, resp);
        }
    }

    private void handleDeleteComment(HttpServletRequest req) {
        CommentService.removeCommentById(
                Integer.parseInt(req.getParameter("comment_id"))
        );
    }

    private void handleEditComment(HttpServletRequest req) {
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(req.getParameter("comment_id")));
        comment.setSource(req.getParameter("comment_source"));
        comment.setDate(System.currentTimeMillis());
        comment.setArticleId(Integer.parseInt(req.getParameter("article_id")));
        comment.setUser(UserService.getUserByName(req.getParameter("user_name")));
        CommentService.updateComment(comment);
    }

    private void setCommentForEdit(HttpServletRequest req) {
        setCommentsForArticle(req);
        req.setAttribute("edit_comment_id", req.getParameter("comment_id"));
    }

    private void handleSendComment(HttpServletRequest req) {
        Comment comment = new Comment();
        comment.setSource(req.getParameter("comment_source"));
        comment.setDate(System.currentTimeMillis());
        comment.setArticleId(Integer.parseInt(req.getParameter("article_id")));
        comment.setUser(UserService.getUserByName(req.getParameter("user_name")));
        CommentService.addNewComment(comment);
    }

    private void setCommentsForArticle(HttpServletRequest req) {
        req.setAttribute("comments", CommentService.getCommentsByArticleId(
                Integer.parseInt(req.getParameter("article_id"))
                )
        );
    }

    private void handleDeleteArticle(HttpServletRequest req) {
        ArticleService.removeArticleById(
                Integer.parseInt(req.getParameter("article_id"))
        );
    }

    private void setArticle(HttpServletRequest req) {
        Article article = ArticleService.getArticleById(
                Integer.parseInt(req.getParameter("article_id"))
        );
        req.setAttribute("article", article);
    }

    private void handleEditArticle(HttpServletRequest req) {
        Article article = new Article();
        article.setId(Integer.parseInt(req.getParameter("article_id")));
        article.setTitle(req.getParameter("article_title"));
        article.setSource(req.getParameter("article_source"));
        article.setAuthor(
                UserService.getUserById(
                        Integer.parseInt(req.getParameter("article_user_id"))
                )
        );
        article.setDate(System.currentTimeMillis());
        ArticleService.updateArticle(article);
    }

    private void setAllArticles(HttpServletRequest req) {
        req.setAttribute("articles", ArticleService.getAllArticles());
    }

    private void handleAddArticle(HttpServletRequest req) {
        Article article = new Article();
        article.setTitle(req.getParameter("article_title"));
        article.setSource(req.getParameter("article_source"));
        article.setDate(System.currentTimeMillis());
        article.setAuthor(UserService.getUserByName(
                (String) req.getSession().getAttribute("login_id"))
        );
        ArticleService.addNewArticle(article);
    }
}
