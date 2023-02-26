/*** package org.example.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.Objects;

import org.example.model.Audio;


@WebServlet(name = "audio", value = "audio")
public class AudioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final Map<String, Audio> audioMap = new HashMap<>();
    private AtomicLong totalCopiesSold = new AtomicLong(0);
	
    @Override
    public void init() throws ServletException {
        Audio audio_1 = new Audio("Tyler Swift", "Song 1","Album1", 1, 2020, 100, 1000);
        Audio audio_2 = new Audio("Rema", "Song 2","Album2", 2, 2021, 200, 2000);
        Audio audio_3 = new Audio("Ed Sheeran", "Song 3","Album3", 3, 2022, 300, 3000);


        audioMap.put("id_1", audio_1);
        audioMap.put("id_2", audio_2);
        audioMap.put("id_3", audio_3);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
    	String artistname= request.getParameter("artistname");
    	String key = request.getParameter("key");
        
    	Audio audioItem=null;
    	
    	for (Audio audio : audioMap.values()) {

            if (audio.getArtistName() ==artistname) {
                audioItem = audio;
                break;
            }
        
        

        if (audioItem == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        else {
        	String artistName = audioItem.getArtistName();

            String trackTitle = audioItem.getTrackTitle();
            
            String albumTitle=audioItem.getAlbumTitle();

            Integer trackNumber = audioItem.getTrackNumber();

            Integer year = audioItem.getYear();

            Integer numReviews = audioItem.getNumReviews();

            Integer numCopiesSold = audioItem.getNumCopiesSold();

            Audio audioSelected = new Audio (artistName,trackTitle,albumTitle,trackNumber,year,numReviews,numCopiesSold);

        }
        String value=null;
        
        if(Objects.equals(key, "artistname")){
            value = audioSelected.getArtistName();
        }else if (Objects.equals(key,"track title")){
            value=audioSelected.getTrack_title();
        }else if (Objects.equals(key,"track number")){
            value= String.valueOf(audioSelected.getTrack_number());
        }else if (Objects.equals(key,"year")){
            value= String.valueOf(audioSelected.getYear());
        }else if (Objects.equals(key,"reviews count")){
            value= String.valueOf(audioSelected.getReviews_count());
        }else {
            value= String.valueOf(audioSelected.getCopies_sold());
        }

        Integer totalCopiesSold= 0;

        for (Audio audio : audioDB.values()){
            totalCopiesSold=totalCopiesSold+audio.getCopies_sold();
        }

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(audioDB);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.println("GET RESPONSE IN JSON - single element for "+key +" is :"+ gson.toJson(value));

        out.println("GET RESPONSE IN JSON - all elements " + element.toString());

        out.println("Total Copies Sold "+ gson.toJson(totalCopiesSold));
        out.flush();}



        String value = "";

        switch (key.toLowerCase()) {
        case "artistname":
            value = audioItem.getArtistName();
            break;
        case "tracktitle":
            value = audioItem.getTrackTitle();
            break;
        case "albumtitle":
            value = audioItem.getAlbumTitle();
            break;
        case "tracknumber":
            value = Integer.toString(audioItem.getTrackNumber());
            break;
        case "year":
            value = Integer.toString(audioItem.getYear());
            break;
        case "numreviews":
            value = Long.toString(audioItem.getNumReviews());
            break;
        case "numcopiessold":
            value = Long.toString(audioItem.getNumCopiesSold());
            break;
        default:
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
    }    

        response.setContentType("text/plain");
        response.getWriter().write(value);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        Audio audioItem = new Gson().fromJson(request.getReader(), Audio.class);

        if (audioItem == null || audioItem.getArtistName() == null || audioItem.getTrackTitle() == null
                || audioItem.getAlbumTitle() == null || audioItem.getYear() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String key = audioItem.getArtistName() + "_" + audioItem.getTrackTitle();

        if (audioMap.containsKey(key)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        audioItem.setNumCopiesSold(0);
        audioMap.put(key, audioItem);
        response.setStatus(HttpServletResponse.SC_CREATED);

        // Update total number of copies sold
        totalCopiesSold.addAndGet(audioItem.getNumCopiesSold());
    }
} ***/
