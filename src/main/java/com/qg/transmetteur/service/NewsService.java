package com.qg.transmetteur.service;

import com.qg.transmetteur.exception.UserNotFoundException;
import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.UserAccount;
import com.qg.transmetteur.repository.NewsRepository;
import com.qg.transmetteur.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService
{

    @Autowired
    private NewsRepository newsRepository;

    public News saveNews(News news){
        return newsRepository.save(news);
    }

    public News getNewsById(Long id){
        return newsRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User by id " + id + " was not found"));
    }
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    public List<News> getNewsByOrdered(Boolean ordered){
        return newsRepository.findByOrdered(ordered);
    }

    public void orderLabelize(News news, Boolean action){
        news.setOrdered(action);
        newsRepository.save(news);
    }

    public void deleteNews(Long id){
        newsRepository.deleteById(id);
        System.out.println("news removed !! " + id);
    }

    public String deleteAll(){
        newsRepository.deleteAll();
        return "all news removed !! ";
    }

}
