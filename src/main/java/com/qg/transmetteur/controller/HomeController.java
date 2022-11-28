package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Search;
import com.qg.transmetteur.model.Video;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class HomeController
{

    @Autowired
    public YoutubeAPISearch youtubeAPISearch;
    @Autowired
    public NewsAPISearch newsAPISearch;
    @Autowired
    public VideoService videoService;
    @Autowired
    public NewsService newsService;
    @Autowired
    public DossierService dossierService;
    @Autowired
    SearchQueryProcessing searchQueryProcessing;


    @RequestMapping("/dashboard")
    public String getDashboard(ModelMap modelMap) throws IOException, JSONException, ParseException, NoSuchAlgorithmException, KeyManagementException
    {
        Search dashboardSearch = new Search();
        dashboardSearch.setResultNumber("3");
        dashboardSearch.setQuery("covid");
        dashboardSearch.setSortBy("date");
        dashboardSearch.setLanguage("FR");


        //Initialisation des entités Video en récupérant les urls et les dates de publication des resultats de la recherche
        List<Video> videosList = youtubeAPISearch.getIdsFromQuerySearchResults(searchQueryProcessing.processYoutubeQuery(dashboardSearch));
        //Ajout des autres propriétés des entités Video via un appel à un service embeded
        videosList = youtubeAPISearch.getVideosFromIdSearchResults(videosList);
        modelMap.put("videos", videosList);

        dashboardSearch.setQuery("\"covid\"");
        dashboardSearch.setSortBy("publishedAt");
        dashboardSearch.setLanguage("fr");
        List<News> newsList = newsAPISearch.getNewsFromQuerySearchResults(searchQueryProcessing.processNewsQuery(dashboardSearch));
        modelMap.put("newsList", newsList);

        modelMap.put("search", new Search());

        return "dashboard";
    }
    @RequestMapping("/youtube")
    public String getYoutube(ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        modelMap.put("search", new Search());
        modelMap.put("channelId", new Video());

        return "youtube";
    }
    @RequestMapping("/news")
    public String getNews(ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        modelMap.put("search", new Search());

        List<Dossier> dossiers = dossierService.getAllDossier();
        modelMap.put("dossiers", dossiers);
        modelMap.put("newDossier", new Dossier());
        return "news";
    }
    @RequestMapping("/twitter")
    public String getTwitter() throws IOException
    {
        return "twitter";
    }
    @RequestMapping("/espace")
    public String getEspace(ModelMap modelMap) throws IOException
    {
        List<Dossier> dossiers = dossierService.getAllDossier();
        List<News> myNews = newsService.getAllNews();
        List<Video> myVideos = videoService.getAllVideos();

        modelMap.put("videoList", myVideos);
        modelMap.put("newsList", myNews);
        modelMap.put("videoCount", myVideos.size());
        modelMap.put("newsCount", myNews.size());
        modelMap.put("totalCount", myNews.size() + myVideos.size());

        modelMap.put("dossierCount", dossiers.size());


        modelMap.put("dossiers", dossiers);
        modelMap.put("newDossier", new Dossier());

        return "espace";
    }


}
