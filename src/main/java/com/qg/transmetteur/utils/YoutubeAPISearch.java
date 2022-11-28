package com.qg.transmetteur.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qg.transmetteur.model.Search;
import com.qg.transmetteur.model.Video;
import com.qg.transmetteur.service.VideoService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class YoutubeAPISearch
{
    @Autowired
    public DownloadVideo downloadVideo;
    public static final String START_API_LINK = "https://www.googleapis.com/youtube/v3/search?";
    public static final String END_API_LINK = "&key=AIzaSyD6KOe0hXak-RMINRSgU5RweszaRvJGKmY";

    @Autowired
    public VideoService videoService;

    public List<Video> getVideosFromIdSearchResults(List<Video> videos) throws IOException

    {

        for (int i=0; i<videos.size(); i++){
            URL url = new URL("https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v="+videos.get(i).getUrl()+"&format=json");
            try
            {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            String jsonStr = "";
            Scanner scanner = new Scanner(url.openStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext())
            {
                jsonStr += scanner.nextLine();
            }
            //Close the scanner
            scanner.close();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Video video = objectMapper.readValue(jsonStr, Video.class);
            video.setIframe(video.getIframe().split("\" frameborder")[0].split("src=\"")[1]);
            videos.get(i).setName(video.getName());
            videos.get(i).setIframe(video.getIframe());
            videos.get(i).setAuthor(video.getAuthor());
            videos.get(i).setImage(video.getImage());
            } catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        return videos;
    }


    public List<Video> getIdsFromQuerySearchResults(String search) throws IOException, ParseException, JSONException, NoSuchAlgorithmException, KeyManagementException
    {
        search = (search.replaceAll(" ","+"));
        String completeUrl = String.format(START_API_LINK + search + END_API_LINK);
        URL youtubeSearchUrl = new URL(completeUrl);
        HttpURLConnection conn = (HttpURLConnection) youtubeSearchUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if (responsecode != 200)
        {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else
        {
            String jsonStr = "";
            Scanner scanner = new Scanner(youtubeSearchUrl.openStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext())
            {
                jsonStr += scanner.nextLine();
            }
            //Close the scanner
            scanner.close();
            JSONObject obj = new JSONObject(jsonStr);
            conn.disconnect();
            List<Video> videoResultList = new ArrayList<>();
            for (int i = 0; i< obj.getJSONArray("items").length(); i++){
                Video currentVideo = new Video();
                if(obj.getJSONArray("items").getJSONObject(i).getJSONObject("id").has("videoId"))
                {
                    currentVideo.setSearchQuery(search);
                    currentVideo.setUrl(obj.getJSONArray("items").getJSONObject(i).getJSONObject("id").get("videoId").toString());
                    currentVideo.setDatePublication(formatDate(obj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").get("publishTime").toString()));
                    currentVideo.setDatePublicationBrut(obj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").get("publishTime").toString());
                    currentVideo.setChannelId(obj.getJSONArray("items").getJSONObject(i).getJSONObject("snippet").get("channelId").toString());

                    currentVideo.setSearchQuery(search);

                    videoResultList.add(currentVideo);
                }
            }
            return videoResultList;

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
        String outputDate="Publiée le " + datePart.split("-")[2] + " " + mois + " "+ datePart.split("-")[0] + " à " + hourPart.split(":")[0] + "h" + hourPart.split(":")[1];
        return outputDate;
    }

}
