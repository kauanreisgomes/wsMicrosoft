package com.services.microsoft.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.microsoft.dao.SecurityUpdateRepo;
import com.services.microsoft.exception.SecurityUpdateNotFoundException;
import com.services.microsoft.models.SecurityUpdates;

@Service
public class SecurityUpdateService {

    private final SecurityUpdateRepo SecurityRepo;

    @Autowired
    public SecurityUpdateService(SecurityUpdateRepo SecurityRepo){
      this.SecurityRepo = SecurityRepo;
    }

    /**
     * @apiNote Adiciona um objeto SecurityUpdates ao database
     * @param update
     * @return
    */
    public SecurityUpdates addUpdate(SecurityUpdates update){
      return SecurityRepo.save(update);
    }

    /**
     * @apiNote Adiciona vários objetos SecurityUpdates ao database
     * @param listUpdates
     * @return
    */
    public void addSeveralUpdates(List<SecurityUpdates> listUpdates){
      SecurityRepo.saveAll(listUpdates);
    }

    /**
     * @apiNote Retorna todas as atualizações no banco de dados.
     * @return
     */
    public List<SecurityUpdates> findAllUpdates(){
      return SecurityRepo.findAll();
    }

     /**
     * @apiNote Retorna um atualização através do ID no banco de dados.
     * @param ID
     * @return
     */
    public SecurityUpdates findUpdateByID(String ID){
      return SecurityRepo.findUpdateByID(ID).orElseThrow(() -> new SecurityUpdateNotFoundException("Security Update by ID "+ID+" was not found!"));
    }

    /**
     * @apiNote Deleta uma atualização através do ID.
     * @param id
     */
    public void deleteUpdate(String id){
      SecurityRepo.deleteById(id);
    }

     
    
}
