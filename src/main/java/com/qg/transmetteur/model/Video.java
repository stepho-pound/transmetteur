package com.qg.transmetteur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videos")
public class Video implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    public Long video_id;

    public String searchQuery;

    @JsonProperty("title")
    @Column(name="name")
    public String name;

    @JsonProperty("videoId")
    @Column(name="url")
    public String url;

    @JsonProperty("html")
    @Column(columnDefinition="varchar(1000)")
    public String iframe;

    @Column(name="date_Publication")
    public String datePublication;

    public String datePublicationBrut;

    @Column(columnDefinition="varchar(2200)")
    public String downloadLink;

    @JsonProperty("channelId")
    @Column(name="channelId")
    public String channelId;

    @JsonProperty("author_name")
    @Column(name="author")
    public String author;

    @JsonProperty("thumbnail_url")
    @Column(columnDefinition="varchar(1000)")
    public String image;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="video_dossier",
            joinColumns = {@JoinColumn(name="video_id")},
            inverseJoinColumns = { @JoinColumn(name="dossier_id")})
    public Set<Dossier> dossiers = new HashSet<>();

    public String dateEnregistrementBrut;
    public String dateEnregistrement;
}
