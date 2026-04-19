package de.opp3.samman.awk.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA1_AuftragsRessourceZuordung")
@NamedQuery(name = "AuftragsRessourceZuordung.findByAuftrag", query = "SELECT z FROM AuftragsRessourceZuordung z WHERE z.auftrag = :auftrag")
public class AuftragsRessourceZuordung implements Serializable {
      public static final String FIND_BY_AUFTRAG = "AuftragsRessourceZuordung.findByAuftrag";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HA1_AUFTRES_SEQ")
    @SequenceGenerator(name = "HA1_AUFTRES_SEQ", sequenceName = "HA1_AUFTRES_SEQ", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "auftrag_nr")
    private Kundenauftrag auftrag;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;

    private double stunden;
    private double individuellerStundensatz;

    public int getId() {
        return id;
    }

    public AuftragsRessourceZuordung(Kundenauftrag auftrag, Ressource ressource, double stunden,
            double individuellerStundensatz) {
        this.auftrag = auftrag;
        this.ressource = ressource;
        this.stunden = stunden;
        this.individuellerStundensatz = individuellerStundensatz;
    }

    public AuftragsRessourceZuordung() {
    }

    public Kundenauftrag getAuftrag() {
        return auftrag;
    }

    public void setAuftrag(Kundenauftrag auftrag) {
        this.auftrag = auftrag;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public double getStunden() {
        return stunden;
    }

    public void setStunden(double stunden) {
        this.stunden = stunden;
    }

    public double getIndividuellerStundensatz() {
        return individuellerStundensatz;
    }

    public void setIndividuellerStundensatz(double individuellerStundensatz) {
        this.individuellerStundensatz = individuellerStundensatz;
    }

}