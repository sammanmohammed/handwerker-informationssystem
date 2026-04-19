package de.opp3.samman.awk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA1_Ressourcen")
public class Ressource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HA1_RES_SEQ")
    @SequenceGenerator(name = "HA1_RES_SEQ", sequenceName = "HA1_RES_SEQ", allocationSize = 1)
    private int id;
    private String art;
    private double standrdKostensatz;
    private String name;
    @OneToMany(mappedBy = "ressource")
    private List<AuftragsRessourceZuordung> auftraege;

    public Ressource() {
    }

    public Ressource(String art, double standrdKostensatz, String name) {
        this.art = art;
        this.standrdKostensatz = standrdKostensatz;
        this.name = name;
        this.auftraege = new ArrayList<>();
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getStandrdKostensatz() {
        return standrdKostensatz;
    }

    public void setStandrdKostensatz(double standrdKostensatz) {
        this.standrdKostensatz = standrdKostensatz;
    }

    public List<AuftragsRessourceZuordung> getAuftraege() {
        return auftraege;
    }

    public void setAuftraege(List<AuftragsRessourceZuordung> auftraege) {
        this.auftraege = auftraege;
    }

    @Override
    public String toString() {

        return name + " (" + art + ")";

    }

}
