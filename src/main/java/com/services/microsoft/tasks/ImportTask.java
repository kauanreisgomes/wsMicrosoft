package com.services.microsoft.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.services.microsoft.models.SecurityUpdates;
import com.services.microsoft.services.SecurityUpdateService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ImportTask{

  private static final Logger log = LoggerFactory.getLogger(ImportTask.class);
     
    private final SecurityUpdateService services;

    public ImportTask(SecurityUpdateService service){
        this.services = service;
    }

    /**
     * @apiNote Importa as Atualizações de segurança da microsoft.
     * @throws JSONException
     * @throws ParseException
     */
    @Scheduled(fixedRate = 5*60000)
    public void ImportUpdatesFromMicrosoft(){

        try {
            String url = "https://api.msrc.microsoft.com/cvrf/v2.0/updates";
    
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
    
            if (conn.getResponseCode() != 200) {
                log.error("Error " + conn.getResponseCode() + " when getting data from URL " + url);
            }
    
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    
            String output = "";
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
    
            conn.disconnect();
    
            JSONObject json = new JSONObject(new String(output.getBytes()));
           
            JSONArray listUpdatesFromMicrosoft = json.getJSONArray("value");
            List<SecurityUpdates> listUpdates = new ArrayList<>();
          
            //Pecorre a lista de atualizações da microsoft
            for (int i = 0; i < listUpdatesFromMicrosoft.length(); i++) {
              
              JSONObject update = listUpdatesFromMicrosoft.getJSONObject(i);
    
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
              dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
              
              Date currentDate = dateFormat.parse(update.getString("CurrentReleaseDate"));
              Date initialDate = dateFormat.parse(update.getString("InitialReleaseDate"));
              
              String severity = null;
    
              if(update.get("Severity") != null){
                severity = update.get("Severity").toString();
              }
    
              listUpdates.add(new SecurityUpdates(update.getString("ID"), update.getString("Alias"), update.getString("CvrfUrl"), 
              update.getString("DocumentTitle"), currentDate, initialDate, severity));
            }
           
            services.addSeveralUpdates(listUpdates);

            log.info("Successfully Import security updates!");
    
          } catch (IOException | JSONException | ParseException ex) {
            
            log.error("Error in "+ImportTask.class.getName()+" on importing security updates from microsoft!", ex);
             
          }
    }
    
    
}
