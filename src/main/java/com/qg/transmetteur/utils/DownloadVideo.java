package com.qg.transmetteur.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
public class DownloadVideo
{

    public String generateDownloadLink(String videoId) throws IOException, NoSuchAlgorithmException, KeyManagementException
    {

        disableSSL();
        String payload = "https://www.youtube.com/watch?v=" + videoId;
        URL url = new URL("https://9convert.com/api/ajaxSearch/index");

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("query", payload);
        params.put("vt", "home");

        byte[] postDataBytes = createFormBody(params);

        HttpURLConnection con  = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        con.setRequestProperty("Cookie", "_ga=GA1.2.402684338.1657725002; _gid=GA1.2.565710706.1657725002; __atuvc=1%7C28; _gat_gtag_UA_190201966_1=1");
        con.setRequestProperty("Accept", "*/*");



        con.setDoInput(true);
        con.setDoOutput(true);

        con.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String response = new String();

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = ((BufferedReader) in).readLine()) != null) {
            stringBuilder.append(line);
        }

        String str = stringBuilder.toString();

        if( str.toString().split("mess\":\"")[1].split("\",")[0].equals(""))
        {
            response = str.toString().split("\"k\":\"")[1].split("\"}")[0];


            url = new URL("https://9convert.com/api/ajaxConvert/convert");
            params = new LinkedHashMap<>();
            params.put("vid", videoId);
            params.put("k", response);

            postDataBytes = createFormBody(params);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setRequestProperty("Cookie", "_ga=GA1.2.402684338.1657725002; _gid=GA1.2.565710706.1657725002; __atuvc=1%7C28; _gat_gtag_UA_190201966_1=1");
            con.setRequestProperty("Accept", "*/*");

            con.setDoInput(true);
            con.setDoOutput(true);

            con.getOutputStream().write(postDataBytes);

            Reader in2 = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            stringBuilder = new StringBuilder();

            while ((line = ((BufferedReader) in2).readLine()) != null)
            {
                stringBuilder.append(line);
            }

            str = stringBuilder.toString();

            response = str.toString().split("\"dlink\":\"")[1].split("\"}")[0];
        }
        return response;

    }
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }


    public byte[] createFormBody(Map<String,Object> params) throws UnsupportedEncodingException
    {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString().getBytes("UTF-8");
    }

    public static void disableSSL() throws NoSuchAlgorithmException, KeyManagementException
    {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

    }
}
