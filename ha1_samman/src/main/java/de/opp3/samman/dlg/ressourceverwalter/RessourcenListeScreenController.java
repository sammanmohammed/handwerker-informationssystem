package de.opp3.samman.dlg.ressourceverwalter;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RessourcenListeScreenController implements Initializable, ControlledScreen {
    private ScreensController myController;

    @FXML
    private TableView<Ressource> tv_ressourcen;
    @FXML
    private TableColumn<Ressource, Integer> tc_id;
    @FXML
    private TableColumn<Ressource, String> tc_art;
    @FXML
    private TableColumn<Ressource, Double> tc_standrdKostensatz;
    @FXML
    private TableColumn<Ressource, String> tc_name;
    private final ObservableList<Ressource> ressourceListe = FXCollections.<Ressource>observableArrayList();

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;

    }

    @Override
    public void initData() {

        ressourceListe.clear();

        ressourceListe.setAll(myController.getRessourcenManager().ladeAlleRessources().stream().toList());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_art.setCellValueFactory(new PropertyValueFactory<>("art"));
        tc_standrdKostensatz.setCellValueFactory(new PropertyValueFactory<>("standrdKostensatz"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        tv_ressourcen.setItems(ressourceListe);
    }

    @FXML
    public void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);

    }

    @FXML
    public void bt_filternachpriesaufteigend() {
        List<Ressource> sortierteListe = ressourceListe
                .stream()
                .sorted(Comparator.comparing(Ressource::getStandrdKostensatz))
                .toList();
        ressourceListe.setAll(sortierteListe);
    }

 @FXML
public void bt_filternachpriesabsteigend() {
    ressourceListe.sort(Comparator.comparingDouble(Ressource::getStandrdKostensatz).reversed());
}

    @FXML
    public void bt_filterdeaktivieren() {
        initData();
    }

}
