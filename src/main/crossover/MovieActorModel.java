package main.crossover;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import main.httpconnection.ConnectionManager;
import main.java.helloworld.HelloWorldSpeechlet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alankrit on 24/11/17.
 */
public class MovieActorModel {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldSpeechlet.class);

    public Set<String> getActors() {
        return actors;
    }

    private Set<String> actors;
    private String movie;
    private boolean isSuccessfulFetch = false;
    private int year;
    public MovieActorModel(String movie) {
        this.movie = movie;
        actors = new HashSet<>();
        getMovieActorList();
    }
    private void getMovieActorList() {
        try {
            JSONArray movieObject = getJSONResponseFromIMDBApi();
            if (movieObject != null) {
                if (movieObject.get(0) != null) {
                    JSONObject firstMatchJSONObject = movieObject.getJSONObject(0);
                    year = Integer.parseInt(firstMatchJSONObject.getString("year"));
                    JSONArray castArray = firstMatchJSONObject.getJSONArray("cast");
                    for (int i = 0; i < castArray.length(); i ++) {
                        actors.add(castArray.getJSONObject(i).getString("name"));
                    }
                    isSuccessfulFetch = true;
                }
            }
        } catch (Exception e) {
            log.error("getMovieActorList() | ", e);
        }
    }

    private JSONArray getJSONResponseFromIMDBApi() throws IOException {
        String url = "http://www.theimdbapi.org/api/find/movie?title=" + movie.replace(" ", "+");
        InputStream is = null;
        try {
            HttpUriRequest httpUriRequest = new HttpGet(url);
            httpUriRequest.setHeader("Connection", "Keep-Alive");
            CloseableHttpResponse closeableHttpResponse = ConnectionManager.httpClient.execute(httpUriRequest);
            is = closeableHttpResponse.getEntity().getContent();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } catch (Exception e) {
          log.error("getJSONResponseFromIMDBApi() | ", e);
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MovieActorModel movieActorModel = new MovieActorModel("Mission Impossible");
        System.out.println(movieActorModel.actors);
    }

    public boolean isSuccessfulFetch() {
        return isSuccessfulFetch;
    }

    public void setSuccessfulFetch(boolean successfulFetch) {
        isSuccessfulFetch = successfulFetch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
