import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.model.RessourcenArt;
import de.opp3.samman.awk.usecase.impl.AbrechnungsVerwalter;
import de.opp3.samman.awk.usecase.impl.AuftragsverbeitungsVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;
import de.opp3.samman.awk.usecase.impl.RessorcenVerwalter;

public class AbrechnungVwTest {
    @Test
    void berechneAuftragssumme() {
        KundenVerwalter kundenVw = new KundenVerwalter();
        KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
        RessorcenVerwalter resVw = new RessorcenVerwalter();
        AuftragsverbeitungsVerwalter bearbVw = new AuftragsverbeitungsVerwalter();
        AbrechnungsVerwalter abrechVw = new AbrechnungsVerwalter();

        Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");

        kundenVw.kundeAnlegen(k);

        Kundenauftrag auftrag = new Kundenauftrag();
        auftrag.setDatum(new java.util.Date());
        auftrag.setTextfeld("Küche fliesen");
        auftrag.setKunde(k);
        auftrag.setBearbeitungAbgeschlossen(true);
        auftragVw.auftragAnlegen(auftrag);

        Ressource res = new Ressource(RessourcenArt.GESELLE, 30.0, "Fliesenleger");
        resVw.ressourceAnlegen(res);

        bearbVw.ressourceZuordnen(auftrag, res, 4.0, null);

        double summe = abrechVw.berechneAuftragssumme(auftrag);

        assertEquals(120.0, summe,
                "Summe muss Stunden * Stundensatz entsprechen");
    }

    @Test
    void abgerechneterAuftragWirdNichtMehrAlsAbzurechnendGeladen() {
        KundenVerwalter kundenVw = new KundenVerwalter();
        KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
        AbrechnungsVerwalter abrechVw = new AbrechnungsVerwalter();

        Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");

        kundenVw.kundeAnlegen(k);

        Kundenauftrag auftrag = new Kundenauftrag();
        auftrag.setDatum(new java.util.Date());
        auftrag.setTextfeld("Zaun streichen");
        auftrag.setKunde(k);
        auftrag.setBearbeitungAbgeschlossen(true); 
        auftragVw.auftragAnlegen(auftrag);

       
        boolean vorherDrin = abrechVw.ladeAbzurechnendeAuftraege().stream()
                .anyMatch(a -> a.getAuftragNr() == auftrag.getAuftragNr());
        assertTrue(vorherDrin, "Auftrag muss zuerst als abzurechnend erscheinen");

        abrechVw.auftragAbgerechnetSetzen(auftrag);

        boolean nachherDrin = abrechVw.ladeAbzurechnendeAuftraege().stream()
                .anyMatch(a -> a.getAuftragNr() == auftrag.getAuftragNr());
        assertFalse(nachherDrin,
                "Abgerechneter Auftrag darf nicht mehr als abzurechnend erscheinen");
    }
}
