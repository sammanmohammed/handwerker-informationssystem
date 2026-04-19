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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class KundenAendernScreenController implements ControlledScreen, Initializable {

    private ScreensController myController;

    @FXML
    private Button bt_zurueck;
    @FXML
    private TableView<Kunde> tv_kunden;
    @FXML
    private TableColumn<Kunde, Integer> tc_kundenNr;
    @FXML
    private TableColumn<Kunde, String> tc_vorname;
    @FXML
    private TableColumn<Kunde, String> tc_nachname;

    @FXML
    private TextField tf_vorname;
    @FXML
    private TextField tf_nachname;
    @FXML
    private TextField tf_kundenNr;
    @FXML
    private TextField tf_plz;
    @FXML
    private TextField tf_ort;
    @FXML
    private TextField tf_strasse;
    @FXML
    private TextField tf_hausNr;

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

                    
                  
                    tf_vorname.setText(newKunde.getVorname());
                    tf_nachname.setText(newKunde.getName());
                    tf_strasse.setText(newKunde.getStrasse());
                    tf_hausNr.setText(String.valueOf(newKunde.getHausNr()));
                    tf_plz.setText(newKunde.getPlz());
                    tf_ort.setText(newKunde.getOrt());
                }
            }
    );
}


    @Override
    public void initData() {
    
        kundenListe.clear();
        Collection<Kunde> data = myController.getKundenManager().ladeAlleKunde();
        kundenListe.setAll(
                    data.stream()
                            .toList());;

       
      
        tf_vorname.clear();
        tf_nachname.clear();
        tf_strasse.clear();
        tf_hausNr.clear();
        tf_plz.clear();
        tf_ort.clear();
        ausgewaehlterKunde = null;
    
    }

    
   


    @FXML
    private void bt_aenderungspeichernClicked() {

        if (ausgewaehlterKunde == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Bitte zuerst einen Kunden in der Tabelle auswählen!").show();
            return;
        }

     
        if (tf_vorname.getText().isEmpty() ||
                tf_nachname.getText().isEmpty() ||
                tf_strasse.getText().isEmpty() ||
                tf_hausNr.getText().isEmpty() ||
                tf_plz.getText().isEmpty() ||
                tf_ort.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING,
                    "Bitte alle Felder ausfüllen!").show();
            return;
        }

        int hausNr;
        try {
            hausNr = Integer.parseInt(tf_hausNr.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING,
                    "Die Hausnummer muss eine gültige Zahl sein!").show();
            return;
        }

     
        ausgewaehlterKunde.setVorname(tf_vorname.getText());
        ausgewaehlterKunde.setName(tf_nachname.getText());
        ausgewaehlterKunde.setStrasse(tf_strasse.getText());
        ausgewaehlterKunde.setHausNr(hausNr);
        ausgewaehlterKunde.setPlz(tf_plz.getText());
        ausgewaehlterKunde.setOrt(tf_ort.getText());

      
        myController.getKundenManager().kundenaender(ausgewaehlterKunde);
    

      
        tv_kunden.refresh();

        new Alert(Alert.AlertType.INFORMATION,
                "Kundendaten wurden erfolgreich geändert!").show();
               initData();
    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.KUNDEN_VERWALTER_SCREEN);
    }
}
