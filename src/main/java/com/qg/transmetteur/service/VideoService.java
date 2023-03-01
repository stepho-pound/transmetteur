package com.qg.transmetteur.service;

import com.qg.transmetteur.exception.VideoNotFoundException;
import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Video;
import com.qg.transmetteur.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService
{
    @Autowired
    private VideoRepository videoRepository;

    public Video saveVideo(Video video){
        return videoRepository.save(video);
    }

    public List<Video> saveAllVideos(List<Video> videos){
        return videoRepository.saveAll(videos);
    }

    public List<Video> getAllVideos(){
        return videoRepository.findAll();
    }

    public List<Video> getVideoByOrdered(Boolean ordered){
        return videoRepository.findByOrdered(ordered);
    }

    public void orderLabelize(Video video, Boolean action){
        video.setOrdered(action);
        videoRepository.save(video);
    }
    public Video getVideoById(Long id){
        return videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Video by id " + id + " was not found"));
    }

    public String deleteVideo(Long id){
        videoRepository.deleteById(id);
        return "video removed !! " + id;
    }

    public String deleteAllVideos(){
        videoRepository.deleteAll();
        return "All video removed !! ";
    }



}
