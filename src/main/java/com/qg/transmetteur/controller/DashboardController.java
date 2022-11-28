package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Search;
import com.qg.transmetteur.model.Video;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/dashboard")
@RestController
public class DashboardController
{
    @Autowired
    public YoutubeAPISearch youtubeAPISearch;
    @Autowired
    public NewsAPISearch newsAPISearch;
    @Autowired
    public VideoService videoService;
    @Autowired
    public SearchQueryProcessing searchQueryProcessing;
    @Autowired
    NewsService newsService;


    @RequestMapping("/search")
    public String getDashboard(@ModelAttribute Search search, ModelMap modelMap) throws IOException, JSONException, ParseException, NoSuchAlgorithmException, KeyManagementException
    {
        search.setResultNumber("3");
        //Initialisation des entités Video en récupérant les urls et les dates de publication des resultats de la recherche
        List<Video> videosList = youtubeAPISearch.getIdsFromQuerySearchResults(searchQueryProcessing.processYoutubeQuery(search));
        //Ajout des autres propriétés des entités Video via un appel à un service embeded
        videosList = youtubeAPISearch.getVideosFromIdSearchResults(videosList);
        modelMap.put("videos", videosList);
        List<News> newsList = newsAPISearch.getNewsFromQuerySearchResults(searchQueryProcessing.processNewsQuery(search));
        modelMap.put("newsList", newsList);
        modelMap.put("search", search);

        return "dashboard";
    }

    @RequestMapping("/saveNews")
    public String saveNews(@RequestParam(value="source") String source,
                           @RequestParam(value="author") String author,
                           @RequestParam(value="title") String title,
                           @RequestParam(value="description") String description,
                           @RequestParam(value="url") String url,
                           @RequestParam(value="urlToImage") String urlToImage,
                           @RequestParam(value="publishedAt") String publishedAt,
                           @RequestParam(value="content") String content,
                           ModelMap modelMap, Search search) throws IOException, JSONException, ParseException
    {


        News news = new News();
        news.setSource(source);
        news.setAuthor(author);
        news.setTitle(title);
        news.setDescription(description);
        news.setUrl(url);
        news.setUrlToImage(urlToImage);
        news.setPublishedAt(publishedAt);
        news.setContent(content);

        newsService.saveNews(news);

        modelMap.put("search", search);

        return "dashboard";
    }
}
