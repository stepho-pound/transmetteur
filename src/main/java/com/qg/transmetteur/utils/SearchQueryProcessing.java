package com.qg.transmetteur.utils;

import com.qg.transmetteur.model.Search;
import org.springframework.stereotype.Service;

@Service
public class SearchQueryProcessing
{
    public String processYoutubeQuery(Search search){
        String processedQuery="part=snippet";
        if(search.getQuery()!= null)
            processedQuery = processedQuery.concat("&q=" + search.getQuery());
        if(search.getLanguage()!= null)
            processedQuery = processedQuery.concat("&language="+search.getLanguage());
        if(search.getFrom()!= null)
            processedQuery = processedQuery.concat("&publishedAfter=" + search.getFrom());
        if(search.getTo()!= null)
            processedQuery = processedQuery.concat("&publishedBefore=" + search.getTo());
        if(search.getSortBy()!= null)
            processedQuery = processedQuery.concat("&order=" + search.getSortBy());
        else
            processedQuery = processedQuery.concat("&order=" + "date");
        if(search.getResultNumber()!= null)
            processedQuery = processedQuery.concat("&maxResults=" + search.getResultNumber());
        return processedQuery;
    }

    public String processNewsQuery(Search search){
        String processedQuery="q=\""+search.getQuery()+"\"&relevanceLanguage="+search.getLanguage().toLowerCase();
        if(search.getFrom()!= null)
            processedQuery = processedQuery.concat("&from=" + search.getFrom());
        if(search.getTo()!= null)
            processedQuery = processedQuery.concat("&to=" + search.getTo());
        if(search.getSortBy()!= null)
            processedQuery = processedQuery.concat("&sortBy=" + search.getSortBy());
        else
            processedQuery = processedQuery.concat("&sortBy=" + "publishedAt");
        if(search.getResultNumber()!= null)
            processedQuery = processedQuery.concat("&pageSize=" + search.getResultNumber());
        return processedQuery;
    }
}
