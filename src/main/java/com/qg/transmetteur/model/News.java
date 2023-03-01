package com.qg.transmetteur.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    public Long news_id;

    private String searchQuery;

    @JsonProperty("source")
    public String source;

    @JsonProperty("author")
    public String author;

    @JsonProperty("title")
    public String title;

    @JsonProperty("description")
    @Column(columnDefinition="varchar(1000)")
    public String description;

    @JsonProperty("url")
    @Column(columnDefinition="varchar(1000)")
    public String url;

    @JsonProperty("urlToImage")
    @Column(columnDefinition="varchar(1000)")
    public String urlToImage;

    @JsonProperty("publishedAt")
    public String publishedAt;

    @JsonProperty("content")
    @Column(columnDefinition="varchar(1000)")
    public String content;

    public Boolean ordered =false;

    public String dateEnregistrementBrut;
    public String dateEnregistrement;

}