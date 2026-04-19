import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.model.RessourcenArt;
import de.opp3.samman.awk.usecase.impl.AuftragsverbeitungsVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;
import de.opp3.samman.awk.usecase.impl.RessorcenVerwalter;

public class RessourcenVwTest {
        @Test
        void ressourceOhneZuordnungKannGeloeschtWerden() {

                RessorcenVerwalter resVw = new RessorcenVerwalter();
                Ressource res = new Ressource(
                                RessourcenArt.MASCHINE, 50.0, "Bohrmaschine");
                resVw.ressourceAnlegen(res);

                boolean geloescht = resVw.ressourceLoeschen(res);

                assertTrue(geloescht, "Ressource ohne Zuordnung sollte löschbar sein");
        }

        @Test
        void ressourceMitZuordnungKannNichtGeloeschtWerden() {

                RessorcenVerwalter resVw = new RessorcenVerwalter();
                AuftragsverbeitungsVerwalter bearbVw = new AuftragsverbeitungsVerwalter();
                KundenVerwalter kundenVw = new KundenVerwalter();
                KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();

                Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");
                kundenVw.kundeAnlegen(k);

                Kundenauftrag auftrag = new Kundenauftrag();
                auftrag.setDatum(new java.util.Date());
                auftrag.setTextfeld("Bad renovieren");
                auftrag.setKunde(k);
                auftragVw.auftragAnlegen(auftrag);

                Ressource res = new Ressource(
                                RessourcenArt.GESELLE, 40.0, "Peter Geselle");
                resVw.ressourceAnlegen(res);

                bearbVw.ressourceZuordnen(auftrag, res, 5.0, null);

                boolean geloescht = resVw.ressourceLoeschen(res);

                assertFalse(geloescht,
                                "Ressource, die in Aufträgen verwendet wird, darf nicht gelöscht werden");
        }


        // Testen
        @Test 
        void ladeRessourcenArtTest(){
         RessorcenVerwalter resVw = new RessorcenVerwalter();
         Collection<Ressource> r = resVw.ladeRessourcenArt("Meister");
         r.forEach(rA-> System.out.println(rA.getName()));
                assertTrue(r.isEmpty());
        }

}
