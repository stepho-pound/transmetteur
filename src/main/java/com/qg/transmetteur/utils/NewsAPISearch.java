package com.qg.transmetteur.utils;

import com.qg.transmetteur.exception.NewsAPIExceedException;
import com.qg.transmetteur.model.News;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class NewsAPISearch
{
    public static final String START_NEWS_API_LINK = "https://newsapi.org/v2/everything?";
    public static final String START_HEADLINE_API_LINK = "https://newsapi.org/v2/top-headlines?";

    public static final String END_API_LINK = "&apiKey=01589b17298747468223b0eeb123cc2e";
    public List<News> getNewsFromQuerySearchResults(String search) throws IOException, ParseException, JSONException
    {
        //search.setQuery(search.getQuery().replaceAll(" ","+"));
        //String completeUrl = String.format();
        URL newsSearchUrl = new URL(String.format(START_NEWS_API_LINK + search + END_API_LINK));
        HttpURLConnection conn = (HttpURLConnection) newsSearchUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200)
        {
            if (responsecode == 429){
                throw new NewsAPIExceedException("You have made too many requests to News API recently. Developer accounts are limited to 100 requests over a 24 hour period (50 requests available every 12 hours). Please upgrade to a paid plan if you need more requests.");

            }else{
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            }
        } else
        {
            String jsonStr = "";
            Scanner scanner = new Scanner(newsSearchUrl.openStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext())
            {
                jsonStr += scanner.nextLine();
            }
            //Close the scanner
            scanner.close();
            JSONObject obj = new JSONObject(jsonStr);
            List<News> newsResultList = new ArrayList<>();
            for (int i = 0; i< obj.getJSONArray("articles").length(); i++){
                News currentNews = new News();

                currentNews.setSearchQuery(search);
                currentNews.setSource(obj.getJSONArray("articles").getJSONObject(i).getJSONObject("source").get("name").toString());
                currentNews.setAuthor(obj.getJSONArray("articles").getJSONObject(i).get("author").toString());
                currentNews.setTitle(obj.getJSONArray("articles").getJSONObject(i).get("title").toString());
                currentNews.setDescription(obj.getJSONArray("articles").getJSONObject(i).get("description").toString());
                currentNews.setUrl(obj.getJSONArray("articles").getJSONObject(i).get("url").toString());
                if(!obj.getJSONArray("articles").getJSONObject(i).get("urlToImage").equals(null))
                    currentNews.setUrlToImage(obj.getJSONArray("articles").getJSONObject(i).get("urlToImage").toString());

                else
                    currentNews.setUrlToImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6tkgV8C7t5V28UK1s8dmJTzpkzvv1j0F19SOlMYR5jqHx7VULwou7eoHynE26HpoUtGU&usqp=CAU");

                currentNews.setPublishedAt(formatDate(obj.getJSONArray("articles").getJSONObject(i).get("publishedAt").toString()));
                currentNews.setContent(obj.getJSONArray("articles").getJSONObject(i).get("content").toString());


                newsResultList.add(currentNews);

            }
            return newsResultList;

        }
    }


    public List<News> getTopHeadlineFromQuerySearchResults(String search) throws IOException, ParseException, JSONException
    {
        //search.setQuery(search.getQuery().replaceAll(" ","+"));
        //String completeUrl = String.format();
        URL newsSearchUrl = new URL(String.format(START_HEADLINE_API_LINK + search + END_API_LINK));
        HttpURLConnection conn = (HttpURLConnection) newsSearchUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200)
        {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else
        {
            String jsonStr = "";
            Scanner scanner = new Scanner(newsSearchUrl.openStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext())
            {
                jsonStr += scanner.nextLine();
            }
            //Close the scanner
            scanner.close();
            JSONObject obj = new JSONObject(jsonStr);
            List<News> newsResultList = new ArrayList<>();
            for (int i = 0; i< obj.getJSONArray("articles").length(); i++){
                News currentNews = new News();

                currentNews.setSource(obj.getJSONArray("articles").getJSONObject(i).getJSONObject("source").get("name").toString());
                currentNews.setAuthor(obj.getJSONArray("articles").getJSONObject(i).get("author").toString());
                currentNews.setTitle(obj.getJSONArray("articles").getJSONObject(i).get("title").toString());
                currentNews.setDescription(obj.getJSONArray("articles").getJSONObject(i).get("description").toString());
                currentNews.setUrl(obj.getJSONArray("articles").getJSONObject(i).get("url").toString());
                if(!obj.getJSONArray("articles").getJSONObject(i).get("urlToImage").equals(null))
                    currentNews.setUrlToImage(obj.getJSONArray("articles").getJSONObject(i).get("urlToImage").toString());

                else
                    currentNews.setUrlToImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6tkgV8C7t5V28UK1s8dmJTzpkzvv1j0F19SOlMYR5jqHx7VULwou7eoHynE26HpoUtGU&usqp=CAU");

                currentNews.setPublishedAt(formatDate(obj.getJSONArray("articles").getJSONObject(i).get("publishedAt").toString()));
                currentNews.setContent(obj.getJSONArray("articles").getJSONObject(i).get("content").toString());


                newsResultList.add(currentNews);

            }
            return newsResultList;

        }
    }
    public String formatDate(String inputDate){
        String datePart = inputDate.split("T")[0];
        String hourPart = inputDate.split("T")[1];
        String mois;
        switch(datePart.split("-")[1]){
            case "01":
                mois = "Janvier";
                break;
            case "02":
                mois = "Février";
                break;
            case "03":
                mois = "Mars";
                break;
            case "04":
                mois = "Avril";
                break;
            case "05":
                mois = "Mai";
                break;
            case "06":
                mois = "Juin";
                break;
            case "07":
                mois = "Juillet";
                break;
            case "08":
                mois = "Août";
                break;
            case "09":
                mois = "Septembre";
                break;
            case "10":
                mois = "Octobre";
                break;
            case "11":
                mois = "Novembre";
                break;
            case "12":
                mois = "Décembre";
                break;
            default:
                throw new IllegalStateException("Unexpected date value: " + datePart.split("-")[1]);
        }
        String outputDate="Publié le " + datePart.split("-")[2] + " " + mois + " "+ datePart.split("-")[0] + " à " + hourPart.split(":")[0] + "h" + hourPart.split(":")[1];
        return outputDate;
    }
}
