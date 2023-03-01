package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.*;
import com.qg.transmetteur.service.DossierService;
import com.qg.transmetteur.service.NewsService;
import com.qg.transmetteur.service.VideoService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RequestMapping("/espace")
@Controller
public class EspaceController
{
    @Autowired
    NewsService newsService;
    @Autowired
    DossierService dossierService;

    @Autowired
    HomeController homeController;

    @Autowired
    VideoService videoService;


    @RequestMapping(value = "/dossier/{id}", method = RequestMethod.GET)
    public String getDossier(@PathVariable(name = "id") Long id, ModelMap modelMap) throws IOException, JSONException, ParseException
    {

        Dossier dossier = dossierService.getDossierById(id);


        List<Dossier> dossiers = dossierService.getAllDossier();
        Set<News> myNews = dossier.getNewsList();
        Set<Video> myVideos = dossier.getVideoList();

        modelMap.put("videoList", myVideos);
        modelMap.put("newsList", myNews);
        modelMap.put("videoCount", myVideos.size());
        modelMap.put("newsCount", myNews.size());
        modelMap.put("dossierCount", dossiers.size());
        modelMap.put("totalCount", myVideos.size() + myNews.size());


        modelMap.put("currentDossier", dossier);
        modelMap.put("modifDossier", new Dossier());
        modelMap.put("dossiers", dossiers);

        return "dossier";
    }

    @RequestMapping("/createDossier")
    public String createDossier(@ModelAttribute Dossier dossierToCreate, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        Dossier newDossier = new Dossier();
        newDossier.setName(dossierToCreate.getName());
        dossierService.saveDossier(newDossier);

        Alerte alerte = new Alerte();
        alerte.setMessage("Dossier crée");
        alerte.setType("alert-success");
        modelMap.put("alerte", alerte);

        return "espace :: alert";
    }


    @DeleteMapping(value = "/deleteNews/{id}")
    public String deleteNews(@PathVariable(name = "id") String id, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        newsService.deleteNews(Long.valueOf(id));

        return homeController.getEspace(modelMap);

    }

    @DeleteMapping(value = "/deleteVideo/{id}")
    public String deleteVideo(@PathVariable(name = "id") String id, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        videoService.deleteVideo(Long.valueOf(id));

        return homeController.getEspace(modelMap);

    }



    @RequestMapping("/addNewsToDossier")
    public String addNewsInDossier(@RequestParam(value="newsId") News news,
                                   @RequestParam(value="dossierId") Dossier dossier,
                                   ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        dossierService.addNewsToDossier(dossier,news);
        //newsService.addDossierToNews(news,dossier);

        List<Dossier> dossiers = dossierService.getAllDossier();
        List<News> myNews = newsService.getNewsByOrdered(false);
        List<Video> myVideos = videoService.getVideoByOrdered(false);

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
    @RequestMapping("/addVideoToDossier")
    public String addVideoInDossier(@RequestParam(value="videoId") Video video,
                                   @RequestParam(value="dossierId") Dossier dossier,
                                   ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        dossierService.addVideoToDossier(dossier,video);

        List<Dossier> dossiers = dossierService.getAllDossier();
        List<News> myNews = newsService.getNewsByOrdered(false);
        List<Video> myVideos = videoService.getVideoByOrdered(false);

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
