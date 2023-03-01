package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.Alerte;
import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Video;
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
import java.util.List;
import java.util.Set;

@RequestMapping("/dossier")
@Controller
public class DossierController
{
    @Autowired
    NewsService newsService;
    @Autowired
    DossierService dossierService;

    @Autowired
    VideoService videoService;

    @Autowired
    EspaceController espaceController;



    @RequestMapping(value = "/deleteDossier/{id}", method = RequestMethod.DELETE)
    public String deleteDossier(@PathVariable(name="id") Long id, ModelMap modelMap) throws IOException, JSONException, ParseException
    {

        dossierService.deleteDossierById(id);
        Alerte alerte = new Alerte();
        alerte.setMessage("Dossier supprimé");
        alerte.setType("alert-success");
        modelMap.put("alerte", alerte);

        return "espace :: alert";
    }
    @RequestMapping(value = "/modifNomDossier", method = RequestMethod.PUT)
    public String modifyNameDossier(@ModelAttribute Dossier dossierToModify, ModelMap modelMap) throws IOException, JSONException, ParseException
    {

        dossierService.getDossierById(dossierToModify.getDossier_id()).setName(dossierToModify.getName());

        Dossier dossier = dossierService.getDossierById(dossierToModify.getDossier_id());
        dossierService.saveDossier(dossier);

        modelMap.put("currentDossier", dossier);

        modelMap.put("videoCount", dossier.getVideoList().size());
        modelMap.put("newsCount", dossier.getNewsList().size());
        modelMap.put("totalCount", dossier.getVideoList().size() + dossier.getNewsList().size());

        return "dossier :: dossierRecap";
    }

    @DeleteMapping(value = "/deleteNewsFromDossier/{dossierid}/{newsid}")
    public String deleteNewsFromDossier(@PathVariable(name = "dossierid") String dossierid, @PathVariable(name = "newsid") String newsid, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        dossierService.deleteNewsFromDossier(dossierService.getDossierById(Long.valueOf(dossierid)), newsService.getNewsById(Long.valueOf(newsid)));

        Dossier dossier = dossierService.getDossierById(Long.valueOf(dossierid));


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

    @DeleteMapping(value = "/deleteVideoFromDossier/{dossierid}/{videoid}")
    public String deleteVideoFromDossier(@PathVariable(name = "dossierid") String dossierid, @PathVariable(name = "videoid") String videoid, ModelMap modelMap) throws IOException, JSONException, ParseException
    {
        dossierService.deleteVideoFromDossier(dossierService.getDossierById(Long.valueOf(dossierid)), videoService.getVideoById(Long.valueOf(videoid)));

        Dossier dossier = dossierService.getDossierById(Long.valueOf(dossierid));


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
}
