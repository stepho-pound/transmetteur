package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.*;
import com.qg.transmetteur.service.DossierService;
import com.qg.transmetteur.service.NewsService;
import com.qg.transmetteur.service.VideoService;
import com.qg.transmetteur.utils.NewsAPISearch;
import com.qg.transmetteur.utils.SearchQueryProcessing;
import com.qg.transmetteur.utils.YoutubeAPISearch;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/news")
@Controller
public class NewsController
{

    @Autowired
    public NewsAPISearch newsAPISearch;
    @Autowired
    public NewsService newsService;
    @Autowired
    public DossierService dossierService;
    @Autowired
    SearchQueryProcessing searchQueryProcessing;


    @RequestMapping("/search")
    public String getNews(@ModelAttribute Search search, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        List<News> newsList = newsAPISearch.getNewsFromQuerySearchResults(searchQueryProcessing.processNewsQuery(search));
        modelMap.put("newsList", newsList);
        modelMap.put("search", search);


        return "fragments/news/newsSearchResults.html :: newsSearchResults";
    }


    @PostMapping(value="/saveNews", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public String saveNews(News news,
                            ModelMap modelMap, Search search) throws IOException, JSONException, ParseException
    {
        if(news.getTitle()!="" && !news.getTitle().equals(""))
            newsService.saveNews(news);

        Alerte alerte = new Alerte();
        alerte.setMessage("Vidéo ajoutée à Mon Espace");
        alerte.setType("alert-success");
        modelMap.put("alerte", alerte);

        return "news :: alert";

    }

}
