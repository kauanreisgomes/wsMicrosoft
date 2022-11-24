package com.services.microsoft.dao;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.services.microsoft.models.SecurityUpdates;

public interface SecurityUpdateRepo extends JpaRepository<SecurityUpdates,String> {
    
    void deleteUpdateByID(String ID);

    Optional<SecurityUpdates> findUpdateByID(String ID);

    
}
