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

@Service
public class DossierService
{
    @Autowired
    private DossierRepository dossierRepository;

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
        System.out.println("dossier " + dossier.getId() + " removed !! ");
    }
    public String deleteAll(){
        dossierRepository.deleteAll();
        return "all dossier removed !! ";
    }

    public void addNewsToDossier(Dossier dossier, News news){
        dossier.getNewsList().add(news);
        System.out.println("News " + news.getId() + " add to dossier "+ dossier.getId() );
    }

    public void addVideoToDossier(Dossier dossier, Video video){
        dossier.getVideoList().add(video);
        System.out.println("Video " + video.getId() + " add to dossier "+ dossier.getId() );

    }
}
