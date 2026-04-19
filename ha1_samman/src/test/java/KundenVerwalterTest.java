import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;

public class KundenVerwalterTest {
    @Test
    void testKundeMitAuftraegenKannNichtGeloeschtWerden() {

        KundenVerwalter kundenVerwalter = new KundenVerwalter();
        KundenauftragVerwalter auftragVerwalter = new KundenauftragVerwalter();

        Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");
        kundenVerwalter.kundeAnlegen(k);

        Kundenauftrag auftrag = new Kundenauftrag(
                new Date(System.currentTimeMillis()),
                "Rohr verlegen",
                k);

        auftragVerwalter.auftragAnlegen(auftrag);

        boolean geloescht = kundenVerwalter.kundenLoeschen(k);

        assertFalse(geloescht,
                "Kunde darf nicht gelöscht werden, da noch Aufträge existieren.");

    }

    @Test
    void kundeOhneAuftraegeKannGeloeschtWerden() {

        KundenVerwalter kundenVerwalter = new KundenVerwalter();
        Kunde k = new Kunde("Mustermann", "Max",
                "Musterstraße", 1, "12345", "Musterstadt");
        kundenVerwalter.kundeAnlegen(k);

        boolean geloescht = kundenVerwalter.kundenLoeschen(k);

        assertTrue(geloescht, "Kunde ohne Aufträge sollte gelöscht werden können");
    }

    @Test
    void kundenSuchenByNameTrefferTest() {
        KundenVerwalter kVerwalter = new KundenVerwalter();

        List<Kunde> k = kVerwalter.suchKundeByName("Becker");
        for (Kunde kunde : k) {
            System.out.println("Vorname: " + kunde.getVorname() + "\n" + "Nachname: " + kunde.getName() + "\n"
                    + "Haus Nr.: " + kunde.getHausNr() + "\n"

            );
        }
        assertFalse(k.isEmpty());

    }

    @Test
    void kundenSuchenByNameNichtTrefferTest() {
        KundenVerwalter kWv = new KundenVerwalter();
        List<Kunde> k = kWv.suchKundeByName("Müller");
        assertTrue(k.isEmpty());
    }

    @Test
    void kundenMitAuftraegeListeLaden() {
        KundenVerwalter kWv = new KundenVerwalter();
        Collection<Kunde> k = kWv.ladeAlleKuneMitAuftrag();
        for (Kunde aK : k) {
            System.out.println("Name: " + aK.getName() + " Vorname: " + aK.getVorname());
            System.out.println();
        }
        assertFalse(k.isEmpty());
    }

    @Test
    void sortedKundenNachPLZTest() {
        KundenVerwalter kWv = new KundenVerwalter();
        List<Kunde> k = kWv.sortiereKundenNachPLZ("12345");
        k.forEach(ka -> System.out.println("Kunde: " + ka.getKunde_nr() + "; " + ka.getVorname() + "; " + ka.getPlz()));
        assertTrue(!k.isEmpty());
    }

    @Test
    void countKundeInPLZTest() {
         KundenVerwalter kWv = new KundenVerwalter();

    kWv.kundeAnlegen(new Kunde("Max", "Mustermann", "...", 1, "12345", "..."));
    kWv.kundeAnlegen(new Kunde("Anna", "Müller", "...", 2, "12345", "..."));
   

    Long counter = kWv.countKundeInPLZ("99999");
    assertEquals(3, counter);

    }
    @Test 
    void anyMatchWithNameTest(){
          KundenVerwalter kWv = new KundenVerwalter();
        boolean result = kWv.anyMatchWithName("Müller");
        assertTrue(result);
    }

}
