package com.qg.transmetteur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dossiers")
public class Dossier implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    public Long id;

    private String name;

    @ManyToMany(mappedBy = "dossiers")
    public Set<Video> videoList = new HashSet<>();

    @ManyToMany(mappedBy = "dossiers")
    public Set<News> newsList = new HashSet<>();

}