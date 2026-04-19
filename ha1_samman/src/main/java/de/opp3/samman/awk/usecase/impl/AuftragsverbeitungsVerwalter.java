package de.opp3.samman.awk.usecase.impl;

import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.usecase.IAuftragsbearbeitungVw;

public class AuftragsverbeitungsVerwalter extends GenericDAO<AuftragsRessourceZuordung>
        implements IAuftragsbearbeitungVw {

    public AuftragsverbeitungsVerwalter() {
        super(AuftragsRessourceZuordung.class);
    }

 @Override
public void ressourceZuordnen(Kundenauftrag auftrag, Ressource ressource, double stunden,
        Double sonderStundensatz) {

    double stundensatz;

    if (sonderStundensatz != null) {
        stundensatz = sonderStundensatz;
    } else {
    
        stundensatz = ressource.getStandrdKostensatz();
    }

    AuftragsRessourceZuordung zuordnung =
            new AuftragsRessourceZuordung(auftrag, ressource, stunden, stundensatz);

   
    if (auftrag.getEingesetzteRessourcen() == null) {
        auftrag.setEingesetzteRessourcen(new java.util.ArrayList<>());
    }
    auftrag.getEingesetzteRessourcen().add(zuordnung);

    super.save(zuordnung);
}

    @Override
    public void zuordnungAendern(AuftragsRessourceZuordung zuordnung) {
        super.update(zuordnung);
    }

 @Override
public boolean zuordnungLoeschen(AuftragsRessourceZuordung zuordnung) {

    Kundenauftrag auftrag = zuordnung.getAuftrag();
    if (auftrag != null && auftrag.getEingesetzteRessourcen() != null) {
        auftrag.getEingesetzteRessourcen().remove(zuordnung);
    }

    return super.delete(zuordnung.getId(), AuftragsRessourceZuordung.class);
}

@Override
public boolean auftragAbschliessen(Kundenauftrag auftrag) {
    try {
       
        auftrag.setBearbeitungAbgeschlossen(true);

        
        tr.begin();
        em.merge(auftrag); 
        tr.commit();

        return true;
    } catch (Exception e) {
        if (tr.isActive()) {
            tr.rollback();
        }
        System.out.println("Fehler beim Abschließen des Auftrags: " + e.getMessage());
        return false;
    }
}

}
