package de.opp3.samman.awk.usecase.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.usecase.IAbrechnungVw;

public class AbrechnungsVerwalter extends GenericDAO<Kundenauftrag>
        implements IAbrechnungVw {

    public AbrechnungsVerwalter() {
        super(Kundenauftrag.class);
    }

    @Override
    public Collection<Kundenauftrag> ladeAbzurechnendeAuftraege() {
        return findListResult(Kundenauftrag.FIND_ABZURECHNENDE, null);
    }

    @Override
    public Collection<AuftragsRessourceZuordung> ladeRechnungspositionen(Kundenauftrag auftrag) {

        Map<String, Object> params = new HashMap<>();
        params.put("auftrag", auftrag);

        return em.createNamedQuery(
                AuftragsRessourceZuordung.FIND_BY_AUFTRAG,
                AuftragsRessourceZuordung.class).setParameter("auftrag", auftrag)
                .getResultList();
    }

    @Override
    public double berechneAuftragssumme(Kundenauftrag auftrag) {
        Collection<AuftragsRessourceZuordung> positionen = ladeRechnungspositionen(auftrag);

        double summe = 0.0;

        if (positionen != null) {
            for (AuftragsRessourceZuordung pos : positionen) {

                double stunden = pos.getStunden();

                double sonder = pos.getIndividuellerStundensatz();
                Ressource res = pos.getRessource();

                double stundensatz;
                if (sonder != 0) {
                    stundensatz = sonder;
                } else {
                    stundensatz = res.getStandrdKostensatz();
                }

                summe += stunden * stundensatz;
            }
        }

        return summe;
    }
//mach das weiter
    @Override
    public void auftragAbgerechnetSetzen(Kundenauftrag auftrag) {
        auftrag.setAbgerechnet(true);
        update(auftrag);
    }
    
}
