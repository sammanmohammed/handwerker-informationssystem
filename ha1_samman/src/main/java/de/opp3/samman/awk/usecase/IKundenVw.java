package de.opp3.samman.awk.usecase;

import java.util.Collection;
import java.util.List;

import de.opp3.samman.awk.model.Kunde;

public interface IKundenVw {
    public void kundeAnlegen(Kunde aKunde);

    public boolean kundenLoeschen(Kunde aKunde);

    public void kundenaender(Kunde aKundenID);

    boolean kundeExistiert(String vorname, String nachname, String plz);

    Collection<Kunde> ladeAlleKunde();

    Collection<Kunde> ladeAlleKuneMitAuftrag();
     List<Kunde> suchKundeByName(String nachname);
}
