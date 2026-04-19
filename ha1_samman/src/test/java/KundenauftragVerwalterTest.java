import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;

public class KundenauftragVerwalterTest {
        @Test
        void auftragMitKurzemTextWirdAngelegt() {
                KundenVerwalter kWv = new KundenVerwalter();
                KundenauftragVerwalter aWv = new KundenauftragVerwalter();
                Kunde k = new Kunde("Maram", "Max",
                                "Musterstraße", 1, "49087", "Osnabrück");
                kWv.kundeAnlegen(k);
                Kundenauftrag a = new Kundenauftrag();
                a.setDatum(new java.util.Date());
                a.setKunde(k);
                a.setTextfeld("Heizung ");
                boolean pruef = aWv.auftragAnlegen(a);
                assertTrue(pruef, "Alles ist ok!");

        }

        @Test
        void auftragMitZuLangemTextWirdAbgelehnt() {
                KundenVerwalter kundenVw = new KundenVerwalter();
                KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();

                Kunde k = new Kunde("Mustermann", "Max", "Musterstraße", 1, "12345", "Musterstadt");
                kundenVw.kundeAnlegen(k);

                String text = "**Auftragsbeschreibung: Komplette Badsanierung und Modernisierung einer barrierefreien Nasszelle inklusive Installation neuer Heizkörper und Verlegung von Fliesen im gesamten Badezimmer sowie Anschluss einer bodengleichen Dusche mit Linearablauf und Einbau einer neuen Badewanne mit Whirlpool-Funktion. Zusätzlich Erneuerung der gesamten Elektroinstallation mit feuchtraumgeeigneten Steckdosen und LED-Beleuchtung, Montage eines neuen Waschtisch-Units mit Unterschrank und Spiegelschrank, Verlegung von Fußbodenheizung mit anschließender Zementestrich-Ausgleichsschicht und wasserdichter Abdichtung aller Nassbereiche nach aktueller DIN-Norm. Installation einer mechanischen Belüftungsanlage mit Feuchtigkeitssensor, Einbau von vier Heizkörpern inklusive Thermostatventilen, Verlegung von Sanitärkeramik in Fischgrät-Optik für Wandflächen und Großformat-Platten im Bodenbereich, Versetzung der Waschmaschinen-Anschlüsse, Setzen einer neuen Badezimmertür aus Sicherheitsglas, Anbringen von Handtuchhaltern, Seifenspendern und Toilettenpapierhalter aus Chrom, Verputzen aller Wände mit Sanitätsputz, Spachteln und Streichen in dezentem Grauton, Verlegung von Sockelleisten aus Aluminium, Montage eines medizinschranks für notwendige Hilfsmittel, Einbau eines Waschbecken-Ablaufes mit Siphon, Anschluss aller Armaturen inklusive thermostatischer Mischbatterie für Dusche und Badewanne, Verlegung von Kalt- und Warmwasserleitungen in Schlitzen, Dämmung aller Leitungen gegen Schallbildung, Herstellung einer gefälleichten Entwässerung für die Dusche, Ausgleich unebener Wände mit Ausgleichsmasse, Anschluss an bestehende Abwasserleitung mit Reinigungsöffnung, Verfugung aller Fliesen mit Epoxidharz-Fuge für optimale Hygiene, Einbau einer Duschkabine mit Schiebetür aus Temperglas, Montage von drei Einbaustrahlern mit dimmbarer LED-Beleuchtung, Installation eines Spiegels mit Beleuchtung und Demistersensor, Verlegung von Anschlüssen für elektrische Zahnbürsten-Ladestation, Einbau eines Haartrockner-Anschlusses, Setzen einer neuen Badewannenverkleidung aus wasserfestem Gipskarton, Anbringen von Haltegriffen gemäß DIN 18040 für barrierefreies Wohnen, Herstellung einer nichttragenden Trennwand für Toilettenbereich, Verlegung von Antirutsch-Fliesen im Duschbereich, Einbau eines Röhrenradiators als Handtuchtrockner, Anschluss an bestehende Heizungsanlage mit Volumenstromregler, Verputzen der Decke mit wasserfestem Putz, Anstrich mit Silikatfarbe für optimale Raumluft, Verlegung von Bodeneinläufen mit Geruchsverschluss, Installation eines Wasserstopp-Automaten für maximale Sicherheit, Einbau eines Abfluss-Systems mit Reinigungsöse, Montage von Eckregalen und Ablageflächen aus Glas, Anschluss einer automatischen Duftstoff-Dispenser-Anlage, Verlegung von Kabeln für zukünftige Smart-Home-Funktionen, Herstellung aller Übergänge zu angrenzenden Räumen mit S\r\n"
                                + //
                                "\r\n" + //
                                "";

                Kundenauftrag auftrag = new Kundenauftrag();
                auftrag.setDatum(new java.util.Date());
                auftrag.setTextfeld(text);
                auftrag.setKunde(k);

                boolean erfolg = auftragVw.auftragAnlegen(auftrag);

                assertFalse(erfolg,
                                "Auftrag mit Text > 2000 Zeichen darf nicht angelegt werden");
        }

        @Test
        void kundeExistiertTest() {
                KundenVerwalter kundenVw = new KundenVerwalter();
                Kunde k = new Kunde("Müller", "Kostantin", "Müller Straße", 1, "12345", "Musterstadt");
                kundenVw.kundeAnlegen(k);
                boolean exsiteiert = kundenVw.kundeExistiert("Kostantin", "Müller", "12345");
                System.out.println("Kunde: " + k.getVorname() + " " + k.getName() + " existiert!");
                assertTrue(exsiteiert);

        }

        @Test
        void alleAuftreageVomKundeLadenTes() {
                KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
                Collection<Kundenauftrag> a = auftragVw.alleAuftreageVomKundenLaden(5);
                a.forEach(a1 -> System.out
                                .println(a1.getAuftragNr() + "; " + a1.getDatum() + " ;" + a1.getKunde().getName()));
                assertFalse(a.isEmpty());

        }

        // Teste die
        @Test
        void pruefeOffeneAuftraegeTest() {
                KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
                boolean offenAuftreage = auftragVw.pruefeOffeneAuftraege(5);
                assertTrue(offenAuftreage);

        }

        // Teste die
        @Test 
        void auftraegeNachDatumTest(){
               KundenauftragVerwalter auftragVw = new KundenauftragVerwalter();
                List<Kundenauftrag> a =
        new ArrayList<>(auftragVw.auftraegeNachDatum());
               a.forEach(ab-> System.out.println(ab.getAuftragNr()+"; "+ ab.getKunde().getName()+"; "+ab.getDatum()));
               assertFalse(a.isEmpty());
               for (int i = 0; i < a.size() - 1; i++) {
        assertTrue(
            !a.get(i).getDatum().after(a.get(i + 1).getDatum()),
            "Aufträge sind nicht nach Datum sortiert"
        );
    }


        }

}
