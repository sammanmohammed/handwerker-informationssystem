package de.opp3.samman.awk.model;

import static jakarta.persistence.CascadeType.REFRESH;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA1_Kundenauftrag")

@NamedQuery(name = "Kundenauftrag.findAbzurechnende", query = "SELECT k FROM Kundenauftrag k " +
        "WHERE k.bearbeitungAbgeschlossen = true " +
        "AND k.abgerechnet = false")
@NamedQuery(
  name = Kundenauftrag.FIND_KUNDEMTTAUFTRAG,
  query = "SELECT distinct k.kunde FROM Kundenauftrag k WHERE k.kunde IS NOT NULL"
)

        

public class Kundenauftrag implements Serializable {

    public static final String FIND_ABZURECHNENDE = "Kundenauftrag.findAbzurechnende";
    public static final String FIND_KUNDEMTTAUFTRAG = "Kundenauftrag.findKundeMitAuftrag";


    @Id
    @SequenceGenerator(name = "HA1_AUFTRAGSNR_SEQ", sequenceName = "HA1_AUFTRAGSNR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HA1_AUFTRAGSNR_SEQ")
    private int auftragNr;
    private java.util.Date datum;
    private String textfeld;
    public static String getFindAbzurechnende() {
        return FIND_ABZURECHNENDE;
    }

    @ManyToOne(cascade = { REFRESH })
    private Kunde kunde;
    public java.util.Date getDatum() {
        return datum;
    }

    private boolean bearbeitungAbgeschlossen; 
    private boolean abgerechnet;
    @OneToMany(mappedBy = "auftrag", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<AuftragsRessourceZuordung> eingesetzteRessourcen;

    public boolean isBearbeitungAbgeschlossen() {
        return bearbeitungAbgeschlossen;
    }

    public void setBearbeitungAbgeschlossen(boolean bearbeitungAbgeschlossen) {
        this.bearbeitungAbgeschlossen = bearbeitungAbgeschlossen;
    }

    public boolean isAbgerechnet() {
        return abgerechnet;
    }

    public void setAbgerechnet(boolean abgerechnet) {
        this.abgerechnet = abgerechnet;
    }

    public Kundenauftrag() {
    }

    public Kundenauftrag(Date datum, String textfeld, Kunde kunde) {
        this.datum = datum;
        this.textfeld = textfeld;
        this.kunde = kunde;
        this.eingesetzteRessourcen = new ArrayList<>();
    }

    public long getAuftragNr() {
        return auftragNr;
    }

    public List<AuftragsRessourceZuordung> getEingesetzteRessourcen() {
        return eingesetzteRessourcen;
    }

    public void setEingesetzteRessourcen(List<AuftragsRessourceZuordung> eingesetzteRessourcen) {
        this.eingesetzteRessourcen = eingesetzteRessourcen;
    }

    public void setAuftragNr(int auftragNr) {
        this.auftragNr = auftragNr;
    }


    public void setDatum(java.util.Date datum) {
        this.datum = datum;
    }

    public String getTextfeld() {
        return textfeld;
    }

    public void setTextfeld(String textfeld) {
        this.textfeld = textfeld;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    @Override
    public String toString() {
        return "Kundenauftrag [auftragNr=" + auftragNr + ", datum=" + datum + ", textfeld=" + textfeld + ", kunde="
                + kunde + ", bearbeitungAbgeschlossen=" + bearbeitungAbgeschlossen + ", abgerechnet=" + abgerechnet
                + ", eingesetzteRessourcen=" + eingesetzteRessourcen + ", getClass()=" + getClass() + ", getDatum()="
                + getDatum() + ", isBearbeitungAbgeschlossen()=" + isBearbeitungAbgeschlossen() + ", isAbgerechnet()="
                + isAbgerechnet() + ", getAuftragNr()=" + getAuftragNr() + ", getEingesetzteRessourcen()="
                + getEingesetzteRessourcen() + ", getTextfeld()=" + getTextfeld() + ", hashCode()=" + hashCode()
                + ", getKunde()=" + getKunde() + ", toString()=" + super.toString() + "]";
    }
}
