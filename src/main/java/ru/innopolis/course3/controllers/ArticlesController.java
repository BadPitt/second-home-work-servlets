package ru.innopolis.course3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.services.ArticleService;
import ru.innopolis.course3.models.comment.Comment;
import ru.innopolis.course3.services.CommentService;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.services.UserService;

import java.util.List;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/articles/")
public class ArticlesController extends BaseController {

    private ArticleService articleService;

    private UserService userService;

    private CommentService commentService;

    @Autowired
    @Qualifier("articleService")
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    @Qualifier("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier("commentService")
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/")
    public String showArticles(Model model) throws DBException {
        model.addAttribute("articles", articleService.getAllTransactionally());
        return "articles";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "add_article")
    public String addArticle(Model model) {
        Article article = new Article();
        article.setAuthor(new User());
        model.addAttribute("article", article);
        return "add_article";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "confirm_add_article",
            method = RequestMethod.POST)
    public String confirmAddArticle(@ModelAttribute(name = "article") Article article,
                                    @RequestParam(name = "user_name", defaultValue = "") String authorName
                                    ) throws DBException {

        long date = System.currentTimeMillis();
        article.setAuthor(userService.getUserByName(authorName));
        article.setDate(date);
        article.setUpdateDate(date);
        articleService.addNewModelTransactionally(article);

        return "redirect:/articles/";
    }

    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('ROLE_ADMIN') or " +
            "#authorName == principal.username)")
    @RequestMapping(params = "edit_article", method = RequestMethod.POST)
    public String editArticle(@RequestParam(name = "article_id", defaultValue = "0") long articleId,
                              @RequestParam(name = "user_name", defaultValue = "") String authorName,
                              @RequestParam(name = "article_update_date", defaultValue = "0") long articleUpdateDate,
                              Model model) throws DBException {

        Article article = articleService.getByIdTransactionally(articleId);
        model.addAttribute("article", article);

        return "edit_article";
    }

    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('ROLE_ADMIN') or " +
            "#authorName == principal.username)")
    @RequestMapping(params = "confirm_edit_article",
            method = RequestMethod.POST)
    public String confirmEditArticle(@ModelAttribute(name = "model") Article article,
                                     @RequestParam(name = "article_user_name", defaultValue = "") String authorName
                                     ) throws DBException {

        article.setAuthor(userService.getUserByName(authorName));
        articleService.updateTransactionally(article);

        return "redirect:/articles/";
    }

    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('ROLE_ADMIN') or " +
            "#authorName == principal.username)")
    @RequestMapping(params = "delete_article",
            method = RequestMethod.POST)
    public String deleteArticle(@RequestParam(name = "article_id", defaultValue = "0") long articleId,
                                @RequestParam(name = "user_id", defaultValue = "0") long userId,
                                @RequestParam(name = "user_name", defaultValue = "") String authorName,
                                @RequestParam(name = "article_update_date", defaultValue = "0") long articleUpdateDate
                                ) throws DBException {
        articleService.removeModelTransactionally(articleId, articleUpdateDate);
        return "redirect:/articles/";
    }

    @RequestMapping(params = "view_more")
    public String viewMoreArticle(@RequestParam(name = "article_id", defaultValue = "0") long articleId,
                                  Model model) throws DBException {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        Article article = articleService.getByIdTransactionally(articleId);

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);

        return "view_more_article";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "send_comment",
            method = RequestMethod.POST)
    public String addComment(@RequestParam(name = "article_id", defaultValue = "0") long articleId,
                             @RequestParam(name = "comment_source", defaultValue = "") String source
                             ) throws DBException {

        long date = System.currentTimeMillis();
        User user = userService.getUserByName(getCurrentUserName());

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUser(user);
        comment.setSource(source);
        comment.setDate(date);
        comment.setUpdateDate(date);

        commentService.addNewModelTransactionally(comment);

        return "redirect:/articles/?view_more&article_id=" + articleId;
    }

    @RequestMapping(params = "edit_comment",
            method = RequestMethod.POST)
    public String editComment(@RequestParam(name = "comment_id", defaultValue = "0") int commentId,
                              @RequestParam(name = "article_id", defaultValue = "0") int articleId,
                              RedirectAttributes redir) {

        redir.addFlashAttribute("editCommentId", commentId);

        return "redirect:/articles/?view_more&article_id=" + articleId;
    }

    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('ROLE_ADMIN') or " +
            "#authorName == principal.username)")
    @RequestMapping(params = "confirm_edit_comment",
            method = RequestMethod.POST)
    public String confirmEditComment(@RequestParam(name = "comment_id", defaultValue = "0") int commentId,
                                     @RequestParam(name = "article_id", defaultValue = "0") int articleId,
                                     @RequestParam(name = "comment_update_date", defaultValue = "0") long updateDate,
                                     @RequestParam(name = "comment_date", defaultValue = "0") long date,
                                     @RequestParam(name = "comment_source", defaultValue = "0") String source,
                                     @RequestParam(name = "user_name", defaultValue = "") String authorName
                                     ) throws DBException {

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUpdateDate(updateDate);
        comment.setDate(date);
        comment.setSource(source);
        comment.setId(commentId);
        comment.setUser(userService.getUserByName(authorName));

        commentService.updateTransactionally(comment);

        return "redirect:/articles/?view_more&article_id=" + articleId;
    }

    @PreAuthorize("isAuthenticated() and " +
            "(hasRole('ROLE_ADMIN') or " +
            "#author == principal.username)")
    @RequestMapping(params = "delete_comment",
            method = RequestMethod.POST)
    public String deleteComment(@RequestParam(name = "comment_id", defaultValue = "0") int commentId,
                                @RequestParam(name = "comment_update_date", defaultValue = "0") long updateDate,
                                @RequestParam(name = "user_name", defaultValue = "") String author,
                                @RequestParam(name = "article_id", defaultValue = "0") int articleId
                                ) throws DBException {

        commentService.removeModelTransactionally(commentId, updateDate);

        return "redirect:/articles/?view_more&article_id=" + articleId;
    }
}
