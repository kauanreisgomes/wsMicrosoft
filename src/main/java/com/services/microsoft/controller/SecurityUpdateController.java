package com.services.microsoft.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.microsoft.models.SecurityUpdates;
import com.services.microsoft.services.SecurityUpdateService;

@RestController
@RequestMapping("/")
public class SecurityUpdateController {
    
    private final SecurityUpdateService services;

    public SecurityUpdateController(SecurityUpdateService service){
        this.services = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SecurityUpdates>> getAllUpdates(){
        var listUpdates = services.findAllUpdates();
        
        //Organiza a lista de acordo com o ID.
        Collections.sort(listUpdates, new Comparator<SecurityUpdates>() {
                                
            @Override
            public int compare(SecurityUpdates updateOne, SecurityUpdates updateTwo){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMMMM");
                try {
                    Date dateOne = dateFormat.parse(updateOne.getID());
                    Date dateTwo = dateFormat.parse(updateTwo.getID());
                    return dateOne.compareTo(dateTwo);
                } catch (ParseException e) {
                    return updateOne.getID().compareTo(updateTwo.getID());
                }   
            }

        });

        return new ResponseEntity<>(listUpdates, HttpStatus.OK);
    }

    /**
     * @apiNote Serviço para encontrar somente uma atualização de segurança, recebendo o ID como parametro.
     * @param ID
     * @return
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<SecurityUpdates> getUpdateByID(@PathVariable("id") @Validated String ID){
        var listUpdates = services.findUpdateByID(ID);
        return new ResponseEntity<>(listUpdates, HttpStatus.OK);
    }

    /**
     * @apiNote Serviço para adicionar uma atualização de segurança, recebendo o objeto SecurityUpdates.
     * @param securityUpdate
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<SecurityUpdates> addSecurityUpdate(@Validated @RequestBody SecurityUpdates securityUpdate){
        var newUpdate = services.addUpdate(securityUpdate);
        return new ResponseEntity<>(newUpdate, HttpStatus.CREATED);
    }

    /**
     * @apiNote Serviço para atualizar uma atualização de segurança.
     * @param securityUpdate
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<SecurityUpdates> updateSecurityUpdate(@Validated @RequestBody SecurityUpdates securityUpdate){
        var updateSecurity = services.addUpdate(securityUpdate);
        return new ResponseEntity<>(updateSecurity, HttpStatus.OK);
    }

    /**
     * @apiNote Serviço para deletar uma atualização de segurança, recebendo o parametro ID.
     * @param ID
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSecurityUpdate(@Validated @PathVariable("id") String ID){
        services.deleteUpdate(ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
