package de.opp3.samman.dlg.auftragverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.AuftragsRessourceZuordung;
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

public class AuftragsbearbeitungScreenController implements Initializable, ControlledScreen {

    private ScreensController myController;

    @FXML
    private TableView<Kundenauftrag> tv_auftraege;
    @FXML
    private TableColumn<Kundenauftrag, Integer> tc_auftragsNr;
    @FXML
    private TableColumn<Kundenauftrag, Object> tc_kunde;
    @FXML
    private TableColumn<Kundenauftrag, java.util.Date> tc_datum;

    @FXML
    private TableView<AuftragsRessourceZuordung> tv_zuordnungen;
    @FXML
    private TableColumn<AuftragsRessourceZuordung, Ressource> tc_ressource;
    @FXML
    private TableColumn<AuftragsRessourceZuordung, Double> tc_stunden;
    @FXML
    private TableColumn<AuftragsRessourceZuordung, Double> tc_satz;

    @FXML
    private ComboBox<Ressource> cb_ressource;
    @FXML
    private TextField tf_stunden;
    @FXML
    private TextField tf_standardSatz;
    @FXML
    private TextField tf_stundensatz;
    @FXML
    private CheckBox cb_abgeschlossen;

    private final ObservableList<Kundenauftrag> auftragsListe = FXCollections.observableArrayList();
    private final ObservableList<AuftragsRessourceZuordung> zuordnungsListe = FXCollections.observableArrayList();
    private final ObservableList<Ressource> ressourcenListe = FXCollections.observableArrayList();

    private Kundenauftrag ausgewaehlterAuftrag;
    private AuftragsRessourceZuordung ausgewaehlteZuordnung;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_auftragsNr.setCellValueFactory(
                new PropertyValueFactory<>("auftragNr"));

        tc_kunde.setCellValueFactory(
                new PropertyValueFactory<>("kunde"));

        tc_datum.setCellValueFactory(
                new PropertyValueFactory<>("datum"));

        tv_auftraege.setItems(auftragsListe);

        tc_ressource.setCellValueFactory(
                new PropertyValueFactory<>("ressource"));
        tc_stunden.setCellValueFactory(
                new PropertyValueFactory<>("stunden"));
        tc_satz.setCellValueFactory(
                new PropertyValueFactory<>("individuellerStundensatz"));

        tv_zuordnungen.setItems(zuordnungsListe);

        cb_ressource.setItems(ressourcenListe);

        cb_ressource.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldRes, newRes) -> {
                    if (newRes != null) {
                        tf_standardSatz.setText(
                                String.valueOf(newRes.getStandrdKostensatz()));
                    } else {
                        tf_standardSatz.clear();
                    }
                });

        tv_auftraege.getSelectionModel().selectedItemProperty().addListener(

                (obs, oldAuftrag, newAuftrag) -> {
                    if (newAuftrag != null) {
                        ausgewaehlterAuftrag = newAuftrag;
                        ladeZuordnungenFuerAuftrag(newAuftrag);
                        cb_abgeschlossen.setSelected(newAuftrag.isBearbeitungAbgeschlossen());
                        formularLeeren();
                    }
                });

        tv_zuordnungen.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldZ, newZ) -> {
                    if (newZ != null) {
                        ausgewaehlteZuordnung = newZ;
                        cb_ressource.setValue(newZ.getRessource());
                        tf_stunden.setText(String.valueOf(newZ.getStunden()));
                        tf_stundensatz.setText(String.valueOf(newZ.getIndividuellerStundensatz()));

                    }
                });
    }

    @Override
    public void initData() {

        auftragsListe.clear();
        zuordnungsListe.clear();
        ressourcenListe.clear();
        ausgewaehlterAuftrag = null;
        ausgewaehlteZuordnung = null;
        cb_abgeschlossen.setSelected(false);

        formularLeeren();

        Collection<Kundenauftrag> auftraege = myController.getAuftragManager().ladeAlleAuftraege();
        auftragsListe.setAll(auftraege.stream().toList());



        Collection<Ressource> alleRessourcen = myController.getRessourcenManager().ladeAlleRessources();
        ressourcenListe.setAll(
                    alleRessourcen.stream()
                            .toList());

    }

    private void ladeZuordnungenFuerAuftrag(Kundenauftrag auftrag) {
        zuordnungsListe.clear();
        if (auftrag.getEingesetzteRessourcen() != null) {
            zuordnungsListe.addAll(auftrag.getEingesetzteRessourcen());
        }
    }

    private void formularLeeren() {
        cb_ressource.getSelectionModel().clearSelection();
        tf_stunden.clear();
        tf_stundensatz.clear();
        tf_standardSatz.clear();
        ausgewaehlteZuordnung = null;
    }

    @FXML
    private void bt_zuordnungSpeichernClicked() {
        if (ausgewaehlterAuftrag.isBearbeitungAbgeschlossen()) {
            new Alert(Alert.AlertType.WARNING,
                    "Abgeschlossene Aufträge können nicht mehr bearbeitet werden.").show();
            return;
        }
        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }
        Ressource res = cb_ressource.getValue();
        if (res == null || tf_stunden.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte Ressource und Stunden angeben!").show();
            return;
        }

        double stunden;
        Double sonderSatz = null;

        try {
            stunden = Double.parseDouble(tf_stunden.getText());
            if (!tf_stundensatz.getText().isEmpty()) {
                sonderSatz = Double.parseDouble(tf_stundensatz.getText());
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,
                      "Stunden und Sonder-Stundensatz müssen gültige Zahlen sein!\n" +
            "Hinweis: Dezimalzahlen müssen mit Punkt geschrieben werden (z.B. 1.5 statt 1,5)").show();
            return;
        }

        if (ausgewaehlteZuordnung == null) {

            myController.getAuftragsbearbeitungManager().ressourceZuordnen(
                    ausgewaehlterAuftrag,
                    res,
                    stunden,
                    sonderSatz);
        } else {

            ausgewaehlteZuordnung.setRessource(res);
            ausgewaehlteZuordnung.setStunden(stunden);
            if (sonderSatz != null) {
                ausgewaehlteZuordnung.setIndividuellerStundensatz(sonderSatz);
            }
            myController.getAuftragsbearbeitungManager().zuordnungAendern(ausgewaehlteZuordnung);
        }

        ladeZuordnungenFuerAuftrag(ausgewaehlterAuftrag);
        formularLeeren();
    }

    @FXML
    private void bt_zuordnungLoeschenClicked() {
        if (ausgewaehlterAuftrag.isBearbeitungAbgeschlossen()) {
            new Alert(Alert.AlertType.WARNING,
                    "Abgeschlossene Aufträge können nicht mehr bearbeitet werden.").show();
            return;
        }
        if (ausgewaehlteZuordnung == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Ressourceneinsatz in der Tabelle auswählen!").show();
            return;
        }

        boolean ok = myController.getAuftragsbearbeitungManager().zuordnungLoeschen(ausgewaehlteZuordnung);
        if (ok) {
            zuordnungsListe.remove(ausgewaehlteZuordnung);
            new Alert(Alert.AlertType.WARNING,
                    "Ressource wrude erfolgrich gelöscht!").show();
            formularLeeren();
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Die Zuordnung konnte nicht gelöscht werden.").show();
        }
    }

    @FXML
    private void bt_auftragSpeichernClicked() {

        if (ausgewaehlterAuftrag == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Auftrag auswählen!").show();
            return;
        }

        // Auftrag abschließen
        ausgewaehlterAuftrag.setBearbeitungAbgeschlossen(true);

        boolean ok = myController.getAuftragsbearbeitungManager()
                .auftragAbschliessen(ausgewaehlterAuftrag);

        if (!ok) {
            new Alert(Alert.AlertType.ERROR,
                    "Auftrag konnte nicht abgeschlossen werden.").show();
            return;
        }

        cb_abgeschlossen.setSelected(true);

        new Alert(Alert.AlertType.INFORMATION,
                "Der Auftrag wurde erfolgreich abgeschlossen!").show();

        initData();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }
}
