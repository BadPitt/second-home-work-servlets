package ru.innopolis.course3.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.ArticleService;

/**
 * @author Danil Popov
 */
@Controller
@RequestMapping("/articles/")
public class ArticlesController {

    @Autowired
    @Qualifier("articleService")
    private ArticleService articleService;

    @RequestMapping("/")
    public String showArticles(Model model) throws DBException {
        model.addAttribute("articles", articleService.getAllArticles());
        return "articles";
    }


}
