package com.qg.transmetteur.controller;

import com.qg.transmetteur.model.*;
import com.qg.transmetteur.service.VideoService;
import com.qg.transmetteur.utils.DownloadVideo;
import com.qg.transmetteur.utils.NewsAPISearch;
import com.qg.transmetteur.utils.SearchQueryProcessing;
import com.qg.transmetteur.utils.YoutubeAPISearch;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/youtube")
@Controller
public class VideoController
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
    public DownloadVideo downloadVideo;

    @RequestMapping("/search")
    public String getVideo(@ModelAttribute Search search, ModelMap modelMap) throws IOException, JSONException, ParseException, NoSuchAlgorithmException, KeyManagementException
    {
        search.setResultNumber("100");
        //Initialisation des entités Video en récupérant les urls et les dates de publication des resultats de la recherche
        List<Video> videosList = youtubeAPISearch.getIdsFromQuerySearchResults(searchQueryProcessing.processYoutubeQuery(search));
        //Ajout des autres propriétés des entités Video via un appel à un service embeded
        videosList = youtubeAPISearch.getVideosFromIdSearchResults(videosList);
        modelMap.put("videos", videosList);
        modelMap.put("channelId", new Video());

        modelMap.put("search", search);

        return "fragments/video/videoSearchResults.html :: videoSearchResults";
    }

    @PostMapping(value="/searchByChannelId", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String getVideoByChannelId(String channelId, ModelMap modelMap, Search search) throws IOException, JSONException, ParseException, NoSuchAlgorithmException, KeyManagementException
    {
        search.setResultNumber("100");
        search.setSortBy("date");
        //Initialisation des entités Video en récupérant les urls et les dates de publication des resultats de la recherche
        List<Video> videosList = youtubeAPISearch.getIdsFromQuerySearchResults(searchQueryProcessing.processYoutubeQuery(search) + "&channelId=" + channelId);
        //Ajout des autres propriétés des entités Video via un appel à un service embeded
        videosList = youtubeAPISearch.getVideosFromIdSearchResults(videosList);
        modelMap.put("videos", videosList);
        modelMap.put("search", search);
        modelMap.put("channelId", new Video());


        return "fragments/video/videoSearchResults.html :: videoSearchResults";
    }

    @PostMapping(value="/saveVideo", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String saveVideo(Video video,
                           ModelMap modelMap, Search search) throws IOException, JSONException, ParseException, NoSuchAlgorithmException, KeyManagementException
    {

        video.setDownloadLink(downloadVideo.generateDownloadLink(video.getUrl()));

        videoService.saveVideo(video);


        Alerte alerte = new Alerte();
        alerte.setMessage("Vidéo ajoutée à Mon Espace");
        alerte.setType("alert-success");
        modelMap.put("alerte", alerte);

        return "youtube :: alert";
    }

}
