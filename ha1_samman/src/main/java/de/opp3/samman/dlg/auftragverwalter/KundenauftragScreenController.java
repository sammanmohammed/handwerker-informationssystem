package de.opp3.samman.dlg.auftragverwalter;



import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.fxml.FXML;


public class KundenauftragScreenController implements ControlledScreen{

    private ScreensController myController;
   

    public KundenauftragScreenController() {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;

    }

    @Override
    public void initData() {
       
    }
    @FXML 
    private void bt_auftraganlegenClicked(){
        myController.setScreen(Hauptmenue.AUFTRAGANLEGEN_SCREEN);

    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.MAIN_SCREEN);
    }
    @FXML
    private void bt_abrechnungClicked() {
        myController.setScreen(Hauptmenue.ABRECHNUNG_SCREEN);
    }
      @FXML
    private void bt_auftragsbearbeitungClicked() {
        myController.setScreen(Hauptmenue.AUFTRAGSBEARBEITUNG_SCREEN);
    }

  
}
