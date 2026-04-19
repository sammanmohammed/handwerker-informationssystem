package de.opp3.samman.awk.usecase;

import java.util.Collection;

import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Kundenauftrag;

public interface IAbrechnungVw {

public Collection<Kundenauftrag> ladeAbzurechnendeAuftraege();
public Collection<AuftragsRessourceZuordung> ladeRechnungspositionen(Kundenauftrag auftrag);
public double berechneAuftragssumme(Kundenauftrag auftrag);
public void auftragAbgerechnetSetzen(Kundenauftrag auftrag);

}