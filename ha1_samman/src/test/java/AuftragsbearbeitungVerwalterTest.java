import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.model.RessourcenArt;
import de.opp3.samman.awk.usecase.impl.AuftragsverbeitungsVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;
import de.opp3.samman.awk.usecase.impl.RessorcenVerwalter;

public class AuftragsbearbeitungVerwalterTest {

    @Test
    void ressourceZuordnenOhneSonderpreisNimmtStandardSatz() {
        KundenVerwalter kundenVw = new KundenVerwalter();
        KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
        RessorcenVerwalter resVw = new RessorcenVerwalter();
        AuftragsverbeitungsVerwalter bearbVw = new AuftragsverbeitungsVerwalter();

        Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");
        kundenVw.kundeAnlegen(k);

        Kundenauftrag auftrag = new Kundenauftrag();
        auftrag.setDatum(new java.util.Date());
        auftrag.setTextfeld("Dach reparieren");
        auftrag.setKunde(k);
        auftragVw.auftragAnlegen(auftrag);

        Ressource res = new Ressource(RessourcenArt.MEISTER, 80.0, "Meister Klaus");
        resVw.ressourceAnlegen(res);

        bearbVw.ressourceZuordnen(auftrag, res, 3.0, null);

        assertEquals(1, auftrag.getEingesetzteRessourcen().size());
        AuftragsRessourceZuordung zu = auftrag.getEingesetzteRessourcen().get(0);

        assertEquals(3.0, zu.getStunden());
        assertEquals(80.0, zu.getIndividuellerStundensatz(),
                "Ohne Sonderpreis muss der Standard-Stundensatz übernommen werden");
    }
}
