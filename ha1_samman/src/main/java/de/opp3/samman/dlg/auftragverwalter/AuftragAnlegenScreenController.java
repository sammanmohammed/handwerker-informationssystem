package de.opp3.samman.dlg.auftragverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.model.Kundenauftrag;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuftragAnlegenScreenController implements ControlledScreen, Initializable {

    private ScreensController myController;

    @FXML
    private TableView<Kunde> tv_kunden;
    @FXML
    private TableColumn<Kunde, Integer> tc_kundenNr;
    @FXML
    private TableColumn<Kunde, String> tc_vorname;
    @FXML
    private TableColumn<Kunde, String> tc_nachname;
    @FXML
    private TextArea ta_beschreibung;

    private final ObservableList<Kunde> kundenListe = FXCollections.observableArrayList();

    private Kunde ausgewaehlterKunde;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_kundenNr.setCellValueFactory(
                new PropertyValueFactory<Kunde, Integer>("kunde_nr"));
        tc_vorname.setCellValueFactory(
                new PropertyValueFactory<Kunde, String>("vorname"));
        tc_nachname.setCellValueFactory(
                new PropertyValueFactory<Kunde, String>("name"));

        tv_kunden.setItems(kundenListe);

  
        tv_kunden.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldKunde, newKunde) -> {
                    if (newKunde != null) {
                        ausgewaehlterKunde = newKunde;
                    }
                });
    }

    @FXML
    private void bt_speichernClicked() {

      
        if (ausgewaehlterKunde == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Kunden in der Tabelle auswählen!").show();
            return;
        }

     
        String beschreibung = ta_beschreibung.getText();
        if (beschreibung == null || beschreibung.isBlank()) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte eine Auftragsbeschreibung eingeben!").show();
            return;
        }

        if (beschreibung.length() > 2000) {
            new Alert(Alert.AlertType.WARNING,
                    "Die Beschreibung darf maximal 2000 Zeichen enthalten!").show();
            return;
        }

       
        Kundenauftrag auftrag = new Kundenauftrag();
        auftrag.setKunde(ausgewaehlterKunde);
        auftrag.setTextfeld(beschreibung.trim());
        auftrag.setDatum(new Date()); 
        auftrag.setBearbeitungAbgeschlossen(false);
        auftrag.setAbgerechnet(false);

        myController.getAuftragManager().auftragAnlegen(auftrag);

      
        new Alert(Alert.AlertType.INFORMATION,
                "Auftrag wurde erfolgreich angelegt!").show();

        ta_beschreibung.clear();
        tv_kunden.getSelectionModel().clearSelection();
        ausgewaehlterKunde = null;
    }

    @FXML
    private void bt_zurueckClicked(ActionEvent event) {
        myController.setScreen(Hauptmenue.KUNDENAUFTRAG_SCREEN);
    }

    @Override
    public void initData() {
        kundenListe.clear();
        Collection<Kunde> data = myController.getKundenManager().ladeAlleKunde();
       
         kundenListe.setAll(
                    data.stream()
                            .toList());
        }
    }

