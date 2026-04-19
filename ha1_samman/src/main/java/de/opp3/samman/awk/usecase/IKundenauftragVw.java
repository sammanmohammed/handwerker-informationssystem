package de.opp3.samman.awk.usecase;

import java.util.Collection;

import de.opp3.samman.awk.model.Kundenauftrag;

public interface IKundenauftragVw {
    public boolean auftragAnlegen(Kundenauftrag aAuftrag);

    public void auftragAendern(Kundenauftrag aAuftrag);

    public boolean auftragLoeschen(Kundenauftrag auftragId);

    public Collection<Kundenauftrag> ladeAlleAuftraege();

    public Collection<Kundenauftrag> alleAuftreageVomKundenLaden(int kunde_nr);

    public boolean pruefeOffeneAuftraege(int kunde_nr);
    public Collection<Kundenauftrag> auftraegeNachDatum();

}
