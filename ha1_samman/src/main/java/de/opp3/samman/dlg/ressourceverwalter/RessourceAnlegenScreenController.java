package de.opp3.samman.dlg.ressourceverwalter;

import java.net.URL;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.model.RessourcenArt;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RessourceAnlegenScreenController implements ControlledScreen, Initializable {
    @FXML
    private ComboBox<String> cb_art;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_kostensatz;

    private ScreensController myController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cb_art.getItems().setAll(RessourcenArt.alle());

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initData() {
        cb_art.getSelectionModel().clearSelection();
        tf_kostensatz.clear();
        tf_name.clear();
    }

    @FXML
    void bt_speichernClicked() {

        if (cb_art == null || tf_name.getText().isEmpty() || tf_kostensatz.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Die drei Felder müssen aufgefühlt sein");
            a.show();
            return;
        }

        Ressource r = new Ressource();
        r.setArt(cb_art.getValue());
        r.setName(tf_name.getText());
        r.setStandrdKostensatz(Double.parseDouble(tf_kostensatz.getText()));
        myController.getRessourcenManager().ressourceAnlegen(r);
        Alert a = new Alert(Alert.AlertType.WARNING, "Ressource ist angelegt!");
        a.show();
        initData();

    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);
    }

}