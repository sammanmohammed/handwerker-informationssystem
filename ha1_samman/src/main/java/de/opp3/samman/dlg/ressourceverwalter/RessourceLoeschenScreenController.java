package de.opp3.samman.dlg.ressourceverwalter;

import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
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

public class RessourceLoeschenScreenController implements ControlledScreen, Initializable {
    private final ObservableList<Ressource> rList = FXCollections.observableArrayList();
    ScreensController myController;
    @FXML
    private TableView<Ressource> tv_ressource;
    @FXML
    private TableColumn<Ressource, Number> tc_id;
    @FXML
    private TableColumn<Ressource, String> tc_art;
    @FXML
    private TableColumn<Ressource, String> tc_name;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_art.setCellValueFactory(new PropertyValueFactory<>("art"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tv_ressource.setItems(rList);

    }

    @FXML
    void bt_filternachpriesaufteigend() {
        Collection<Ressource> r = myController.getRessourcenManager()
                .ladeAlleRessources()
                .stream()
                .sorted(Comparator.comparing(Ressource::getStandrdKostensatz))
                .toList();

        rList.setAll(r);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initData() {
        rList.clear();
        Collection<Ressource> r = myController.getRessourcenManager().ladeAlleRessources();
        rList.setAll(r.stream().sorted().toList());
    }

    @FXML
    void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.RESSOURCEN_VERWALTER_SCREEN);
    }

    @FXML
    void bt_filterdeaktivieren() {
        initData();
    }
    @FXML
    void bt_filternachpriesabsteigend(){
            Collection<Ressource> r = myController.getRessourcenManager()
                .ladeAlleRessources()
                .stream()
                .sorted(Comparator.comparing(Ressource::getStandrdKostensatz))
                .toList();

        rList.setAll(r);

    }

}
