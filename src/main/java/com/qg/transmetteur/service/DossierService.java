package com.qg.transmetteur.service;

import com.qg.transmetteur.exception.DossierNotFoundException;
import com.qg.transmetteur.exception.UserNotFoundException;
import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Video;
import com.qg.transmetteur.repository.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DossierService
{
    @Autowired
    private DossierRepository dossierRepository;
    @Autowired
    private NewsService newsService;
    @Autowired
    private VideoService videoService;

    public Dossier saveDossier(Dossier dossier){
        return dossierRepository.save(dossier);
    }

    public Dossier getDossierById(Long id){
        return dossierRepository.findById(id).orElseThrow(()-> new DossierNotFoundException("Dossier by id " + id + " was not found"));
    }
    public List<Dossier> getAllDossier(){
        return dossierRepository.findAll();
    }

    public void deleteDossierById(Long id){
        dossierRepository.deleteById(id);
        System.out.println("dossier removed !! " + id);
    }
    public void deleteDossier(Dossier dossier){
        dossierRepository.delete(dossier);
        System.out.println("dossier " + dossier.getDossier_id() + " removed !! ");
    }
    public String deleteAll(){
        dossierRepository.deleteAll();
        return "all dossier removed !! ";
    }

    public void addNewsToDossier(Dossier dossier, News news){

        newsService.orderLabelize(news, true);
        dossier.getNewsList().add(news);
        dossierRepository.save(dossier);
        System.out.println("News " + news.getNews_id() + " add to dossier "+ dossier.getDossier_id() );
    }

    public void addVideoToDossier(Dossier dossier, Video video){

        videoService.orderLabelize(video, true);
        dossier.getVideoList().add(video);
        dossierRepository.save(dossier);
        System.out.println("Video " + video.getVideo_id() + " add to dossier "+ dossier.getDossier_id() );
    }

    public void deleteNewsFromDossier(Dossier dossier, News news){
        newsService.orderLabelize(news, false);
        Set<News> newsList = dossier.getNewsList();
        newsList.remove(news);
        dossier.setNewsList(newsList);
        dossierRepository.save(dossier);
        System.out.println("news " + news.getNews_id() + " from dossier " + dossier.getDossier_id() + " removed !! ");
    }

    public void deleteVideoFromDossier(Dossier dossier, Video video){
        videoService.orderLabelize(video, false);
        Set<Video> videoList = dossier.getVideoList();
        videoList.remove(video);
        dossier.setVideoList(videoList);
        dossierRepository.save(dossier);
        System.out.println("video " + video.getVideo_id() + " from dossier " + dossier.getDossier_id() + " removed !! ");
    }
}
