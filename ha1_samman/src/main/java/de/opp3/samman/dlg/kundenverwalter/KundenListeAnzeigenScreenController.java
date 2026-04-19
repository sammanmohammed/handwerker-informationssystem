package de.opp3.samman.dlg.kundenverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KundenListeAnzeigenScreenController implements Initializable, ControlledScreen {

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
        private TableColumn<Kunde, String> tc_strasse;
        @FXML
        private TableColumn<Kunde, Integer> tc_hausNr;
        @FXML
        private TableColumn<Kunde, String> tc_plz;
        @FXML
        private TableColumn<Kunde, String> tc_ort;

        private final ObservableList<Kunde> kundenListe = FXCollections.observableArrayList();

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
                tc_strasse.setCellValueFactory(
                                new PropertyValueFactory<Kunde, String>("strasse"));
                tc_hausNr.setCellValueFactory(
                                new PropertyValueFactory<Kunde, Integer>("hausNr"));
                tc_plz.setCellValueFactory(
                                new PropertyValueFactory<Kunde, String>("plz"));
                tc_ort.setCellValueFactory(
                                new PropertyValueFactory<Kunde, String>("ort"));

                tv_kunden.setItems(kundenListe);
        }

        @Override
        public void initData() {
                kundenListe.clear();
                Collection<Kunde> data = myController.getKundenManager().ladeAlleKunde();
                kundenListe.setAll(data.stream().sorted(Comparator.comparing(Kunde::getKunde_nr)).toList());
        }

        @FXML
        private void bt_zurueckClicked() {
                myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
        }

        @FXML
        private void bt_kundenlisteClicked() {
                myController.getKundenManager().ladeAlleKunde();

        }

}
