package com.services.microsoft.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class SecurityUpdates implements Serializable{

  
    /*@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @Getter @Setter
    private Long ID;*/

    @Id
    @Column(nullable = false, updatable = false)
    @Getter @Setter
    private String ID;

    @Getter @Setter
    private String Alias;

    @Getter @Setter
    private String CvrfUrl;

    @Getter @Setter
    private String DocumentTitle;

    @Column(nullable = false)
    @Getter @Setter
    private Date CurrentReleaseDate;

    @Column(nullable = false)
    @Getter @Setter
    private Date InitialReleaseDate;

    @Getter @Setter
    private String Severity;

    public SecurityUpdates(){}

    public SecurityUpdates(String ID, String Alias, String CvrfUrl, String DocumentTitle, Date CurrentReleaseDate, Date InitialReleaseDate, String Severity){
        this.ID = ID;
        this.Alias = Alias;
        this.CvrfUrl = CvrfUrl;
        this.DocumentTitle = DocumentTitle;
        this.CurrentReleaseDate = CurrentReleaseDate;
        this.InitialReleaseDate = InitialReleaseDate;
        this.Severity = Severity;
    }

    @Override
    public String toString() {
        return "Security_Update{"+
                "ID='"+ID+"'"+
                "Alias='"+Alias+"'"+
                "CvrfUrl='"+CvrfUrl+"'"+
                "DocumentTitle='"+DocumentTitle+"'"+
                "CurrentReleaseDate='"+CurrentReleaseDate+"'"+
                "InitialReleaseDate='"+InitialReleaseDate+"'"+
                "Severity='"+Severity+"'"+
                "}";
    }
}
