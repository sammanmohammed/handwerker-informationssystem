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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KundenLoeschenScreenController implements Initializable, ControlledScreen {
 private ScreensController myController;
    @FXML
    private TableView<Kunde> tv_kunden;
    @FXML
    private TableColumn<Kunde, Integer> tc_kundenNr;
    @FXML
    private TableColumn<Kunde, String> tc_vorname;
    @FXML
    private TableColumn<Kunde, String> tc_nachname;

    private final ObservableList<Kunde> kundenListe = FXCollections.observableArrayList();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initData() {
        kundenListe.clear();
        Collection<Kunde> kunde = myController.getKundenManager().ladeAlleKunde();
        kundenListe.setAll(kunde.stream().sorted(Comparator.comparing(Kunde::getKunde_nr)).toList());

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tc_kundenNr.setCellValueFactory(new PropertyValueFactory<>("kunde_nr"));
      
        tc_vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
       
        tc_nachname.setCellValueFactory(new PropertyValueFactory<>("name"));
     

        tv_kunden.setItems(kundenListe);
    }

    @FXML
    void bt_kundenloeschenClicked() {
        Kunde gewaehlterKunde = tv_kunden.getSelectionModel().getSelectedItem();
        if (gewaehlterKunde == null) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Bitte wählen Sie einen Kunden");
            a.show();
            return;
        }

        boolean result = myController.getKundenManager().kundenLoeschen(gewaehlterKunde);
        if (result) {
            Alert a = new Alert(Alert.AlertType.WARNING, gewaehlterKunde.getName() + " wurde gelöst");
            a.show();

        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Kunde kann nicht gelöscht werden, da er einen Auftrag hat");
            a.show();
        }
        initData();
        
    }

    @FXML
    private void bt_zurueckClicked() {

        myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
    }
}
