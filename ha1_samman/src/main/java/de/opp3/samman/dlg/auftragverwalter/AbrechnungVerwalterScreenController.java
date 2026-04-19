package de.opp3.samman.dlg.auftragverwalter;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

public class AbrechnungVerwalterScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;

    @FXML
    private TableView<Kundenauftrag> tv_auftraege;
    @FXML
    private TableColumn<Kundenauftrag, Integer> tc_nr;
    @FXML
    private TableColumn<Kundenauftrag, Object> tc_kunde;
    @FXML
    private TableColumn<Kundenauftrag, Date> tc_datum;

    @FXML
    private TextArea ta_rechnung;

    @FXML
    private Button bt_rechnungErstellen;
    @FXML
    private Button bt_alsAbgerechnet;
    @FXML
    private Button bt_zurueck;

    private final ObservableList<Kundenauftrag> auftragsListe = FXCollections.observableArrayList();

    private Kundenauftrag ausgewaehlterAuftrag;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_nr.setCellValueFactory(new PropertyValueFactory<>("auftragNr"));
        tc_kunde.setCellValueFactory(new PropertyValueFactory<>("kunde"));
        tc_datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        tv_auftraege.setItems(auftragsListe);

        bt_rechnungErstellen.setDisable(true);
        bt_alsAbgerechnet.setDisable(true);

        tv_auftraege.getSelectionModel().selectedItemProperty().addListener(
                (obs, alt, neu) -> {
                    ausgewaehlterAuftrag = neu;
                    ta_rechnung.clear();
                    boolean disabled = (neu == null);
                    bt_rechnungErstellen.setDisable(disabled);
                    bt_alsAbgerechnet.setDisable(disabled);
                });
    }

    @Override
    public void initData() {
        auftragsListe.clear();
        ta_rechnung.clear();
        ausgewaehlterAuftrag = null;
        bt_rechnungErstellen.setDisable(true);
        bt_alsAbgerechnet.setDisable(true);

        if (myController != null) {

            Collection<Kundenauftrag> data = myController.getAbrechnungManager().ladeAbzurechnendeAuftraege();
            auftragsListe.setAll(
                    data.stream()
                            .sorted((a1, a2) -> a1.getDatum().compareTo(a2.getDatum()))
                            .toList());
        }
    }

    @FXML
    private void bt_rechnungErstellenClicked() {
        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        String text = erzeugeRechnungstext(ausgewaehlterAuftrag);
        ta_rechnung.setText(text);
    }

    @FXML
    private void bt_alsAbgerechnetClicked() {
        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        myController.getAbrechnungManager().auftragAbgerechnetSetzen(ausgewaehlterAuftrag);

        new Alert(Alert.AlertType.INFORMATION,
                "Der Auftrag wurde als abgerechnet markiert.").show();

        initData();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }

    private String erzeugeRechnungstext(Kundenauftrag auftrag) {
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        DecimalFormat df = new DecimalFormat("0.00");

        Kunde k = auftrag.getKunde();
        String kundeText = "";
        if (k != null) {
            kundeText = k.getVorname() + " " + k.getName();
        } else {
            kundeText = "<kein Kunde>";
        }

        sb.append("Rechnung für Auftrag Nr. ").append(auftrag.getAuftragNr()).append("\n\n");
        sb.append("Kunde: ").append(kundeText).append("\n");

        if (k != null) {
            sb.append("Adresse: ")
                    .append(k.getStrasse()).append(" ")
                    .append(k.getHausNr()).append(", ")
                    .append(k.getPlz()).append(" ")
                    .append(k.getOrt())
                    .append("\n");
        }

        sb.append("Auftragsdatum: ");
        if (auftrag.getDatum() != null) {
            sb.append(sdf.format(auftrag.getDatum()));
        } else {
            sb.append("-");
        }
        sb.append("\n");

        sb.append("Beschreibung: ");
        if (auftrag.getTextfeld() != null) {
            sb.append(auftrag.getTextfeld());
        } else {
            sb.append("-");
        }
        sb.append("\n\n");

        sb.append("Positionen:\n");
        sb.append("----------------------------------------------\n");
        sb.append("Pos | Ressource     | Art       | Stunden | Satz | Betrag\n");
        sb.append("----------------------------------------------\n");

        Collection<AuftragsRessourceZuordung> eintraege = myController.getAbrechnungManager()
                .ladeRechnungspositionen(auftrag);

        double gesamt = 0;
        int pos = 1;

        if (eintraege != null) {
            for (AuftragsRessourceZuordung z : eintraege) {

                Ressource res = z.getRessource();
                String resName = (res != null) ? res.getName() : "-";
                String art = (res != null) ? res.getArt() : "-";

                double stunden = z.getStunden();

                double satz = z.getIndividuellerStundensatz();
                if (satz == 0 && res != null) {
                    satz = res.getStandrdKostensatz();
                }

                double betrag = stunden * satz;

                gesamt += betrag;

                sb.append(pos).append("   ")
                        .append(resName).append("   ")
                        .append(art).append("   ")
                        .append(df.format(stunden)).append("   ")
                        .append(df.format(satz)).append("   ")
                        .append(df.format(betrag))
                        .append("\n");

                pos++;
            }
        }

        sb.append("----------------------------------------------\n");
        sb.append("Gesamtbetrag: ").append(df.format(gesamt)).append(" €\n");

        return sb.toString();
    }
}