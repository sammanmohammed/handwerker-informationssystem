package de.opp3.samman.awk.usecase.impl;

import java.util.Collection;
import java.util.Comparator;


import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.usecase.IKundenauftragVw;

public class KundenauftragVerwalter extends GenericDAO<Kundenauftrag> implements IKundenauftragVw {
    public KundenauftragVerwalter() {
        super(Kundenauftrag.class);
    }

    @Override
    public boolean auftragAnlegen(Kundenauftrag aAuftrag) {
        if (aAuftrag.getTextfeld() != null &&
                aAuftrag.getTextfeld().length() > 2000) {

            return false;
        }

        super.save(aAuftrag);
        return true;
    }

    @Override
    public void auftragAendern(Kundenauftrag aAuftrag) {
        super.update(aAuftrag);
    }

    @Override
    public boolean auftragLoeschen(Kundenauftrag auftragId) {
        return super.delete(auftragId, Kundenauftrag.class);
    }

    public boolean auftragAbschliessen(Kundenauftrag auftrag) {
        auftrag.setBearbeitungAbgeschlossen(true);
        super.save(auftrag);
        return true;

    }

    @Override
    public Collection<Kundenauftrag> ladeAlleAuftraege() {
        return super.findAll();
    }

    @Override
    public Collection<Kundenauftrag> alleAuftreageVomKundenLaden(int kunde_nr) {

        Collection<Kundenauftrag> auftraege = ladeAlleAuftraege().stream()
                .filter(a -> a.getKunde().getKunde_nr() == kunde_nr).toList();
        return auftraege;
    }

    @Override
    public boolean pruefeOffeneAuftraege(int kunde_nr) {

        Collection<Kundenauftrag> auftraege = ladeAlleAuftraege().stream()
                .filter(a -> a.getKunde().getKunde_nr() == kunde_nr && a.isBearbeitungAbgeschlossen() == false)
                .toList();
        return (!auftraege.isEmpty());
    }

    @Override
    public Collection<Kundenauftrag> auftraegeNachDatum() {
        Collection<Kundenauftrag> a = ladeAlleAuftraege().stream().sorted(Comparator.comparing(Kundenauftrag::getDatum)).toList();
        return a;
    }
    

}
