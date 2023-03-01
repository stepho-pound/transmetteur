package com.qg.transmetteur.repository;

import com.qg.transmetteur.model.Dossier;
import com.qg.transmetteur.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long>
{
    List<News> findByOrdered(Boolean ordered);

}
