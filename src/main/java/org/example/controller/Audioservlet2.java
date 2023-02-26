package org.example.controller;

import org.example.model.Audio2;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;

@WebServlet(name = "audios", urlPatterns = "/audiosItem")
public class Audioservlet2 extends HttpServlet {
	ConcurrentHashMap<String, Audio2> audioDB = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        Audio2 audio_1 = new Audio2("Ed Sheeran", "Photograph","X", 1, 2014, 200, 1001);
        Audio2 audio_2 = new Audio2("Sia", "Unstoppable", "thisisacting",2, 2016, 100, 1000);
        Audio2 audio_3 = new Audio2("Weekend", "Out of time","Dawn FM", 3, 2022, 400, 2000);


        audioDB.put("id_1", audio_1);
        audioDB.put("id_2", audio_2);
        audioDB.put("id_3", audio_3);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String artistName = request.getParameter("artist name");
        String key = request.getParameter("key");
        String value=null;


        Audio2 foundAudio= null;

    //only return the first Audio object that it encounters with the matching artist name.
        for (Audio2 audio : audioDB.values()) {

            if (audio.getArtist_name().equals(artistName)) {
                foundAudio = audio;
                break;
            }
        }

        if (foundAudio == null) {
            response.getWriter().println("Artist not found.");
        }
        else{
            String Artist_name = foundAudio.getArtist_name();

            String Track_title = foundAudio.getTrack_title();
            
            String Album_title=foundAudio.getAlbumTitle();

            Integer Track_number = foundAudio.getTrack_number();

            Integer Year = foundAudio.getYear();

            Integer Reviews_count = foundAudio.getReviews_count();

            Integer Copies_sold = foundAudio.getCopies_sold();

            Audio2 audioSelected = new Audio2(Artist_name, Track_title,Album_title, Track_number, Year, Reviews_count, Copies_sold);


            if(Objects.equals(key, "artist name")){
                value=audioSelected.getArtist_name();
                
            }else if (Objects.equals(key,"track title")){
                value=audioSelected.getTrack_title(); 
            }else if (Objects.equals(key,"album title")){
                value=audioSelected.getAlbumTitle();}
            
            else if (Objects.equals(key,"track number")){
                value= String.valueOf(audioSelected.getTrack_number());
            }else if (Objects.equals(key,"year")){
                value= String.valueOf(audioSelected.getYear());
            }else if (Objects.equals(key,"reviews count")){
                value= String.valueOf(audioSelected.getReviews_count());
            }else {
                value= String.valueOf(audioSelected.getCopies_sold());
            }

            Integer totalCopiesSold= 0;

            for (Audio2 audio : audioDB.values()){
                totalCopiesSold=totalCopiesSold+audio.getCopies_sold();
            }

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(audioDB);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.println("GET RESPONSE IN JSON - single element for "+key +" is :"+ gson.toJson(value));
            out.println();
            
            out.println("Total Copies Sold "+ gson.toJson(totalCopiesSold));
            
            out.println();
            out.println("GET RESPONSE IN JSON - all elements " + element.toString());

            
            out.flush();}


    }
}
