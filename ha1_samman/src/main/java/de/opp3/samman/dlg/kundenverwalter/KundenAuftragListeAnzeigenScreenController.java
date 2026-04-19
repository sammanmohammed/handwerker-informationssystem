package de.opp3.samman.dlg.kundenverwalter;

import java.net.URL;
import java.util.Collection;
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

public class KundenAuftragListeAnzeigenScreenController implements ControlledScreen, Initializable {
    ScreensController mController;

    private final ObservableList<Kunde> kundenListe = FXCollections.observableArrayList();

    @FXML
    private TableView<Kunde> tv_kunden;
    @FXML
    private TableColumn<Kunde, Integer> tc_kundeNr;
    @FXML
    private TableColumn<Kunde, String> tc_vorname;
    @FXML
    private TableColumn<Kunde, String> tc_nachname;
    @FXML
    private javafx.scene.control.Label lbl_anzahl;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.mController = screenPage;
    }

    @Override
    public void initData() {
        kundenListe.clear();
        Collection<Kunde> k = mController.getKundenManager().ladeAlleKuneMitAuftrag();
        kundenListe.setAll(k.stream().toList());
        long anzahl = k.stream().count();
        lbl_anzahl.setText("Kunden mit Auftrag: "+ anzahl);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       tc_kundeNr.setCellValueFactory(new PropertyValueFactory<>("kunde_nr"));
       tc_vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
       tc_nachname.setCellValueFactory(new PropertyValueFactory<>("name"));
       tv_kunden.setItems(kundenListe);
     
    }

    @FXML
    private void bt_zurueckClicked() {

        mController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
    }

    

}
