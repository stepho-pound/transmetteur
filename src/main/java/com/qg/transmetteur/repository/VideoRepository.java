package com.qg.transmetteur.repository;

import com.qg.transmetteur.model.News;
import com.qg.transmetteur.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video,Long>
{

    List<Video> findByOrdered(Boolean ordered);

}
