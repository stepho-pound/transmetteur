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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    public Long dossier_id;

    private String name;

    @ManyToMany
    public Set<Video> videoList = new HashSet<>();

    @ManyToMany
    public Set<News> newsList = new HashSet<>();

}