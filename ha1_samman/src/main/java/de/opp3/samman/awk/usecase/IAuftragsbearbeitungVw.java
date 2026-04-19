package de.opp3.samman.awk.usecase;

import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;

public interface IAuftragsbearbeitungVw {

    void ressourceZuordnen(Kundenauftrag auftrag, Ressource ressource, 
                           double stunden, Double sonderStundensatz);

    void zuordnungAendern(AuftragsRessourceZuordung zuordnung);

    boolean zuordnungLoeschen(AuftragsRessourceZuordung zuordnung);

    boolean auftragAbschliessen(Kundenauftrag auftrag);
}

