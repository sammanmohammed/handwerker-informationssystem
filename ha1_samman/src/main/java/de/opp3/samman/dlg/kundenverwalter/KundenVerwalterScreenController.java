package de.opp3.samman.dlg.kundenverwalter;

import de.opp3.samman.application.Hauptmenue;
import de.opp3.samman.dlg.ControlledScreen;
import de.opp3.samman.dlg.ScreensController;
import javafx.fxml.FXML;

public class KundenVerwalterScreenController implements ControlledScreen {

    private ScreensController myController;

    public KundenVerwalterScreenController() {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;

    }

    @Override
    public void initData() {

    }

    @FXML
    private void bt_zurueckClicked() {
        myController.setScreen(Hauptmenue.MAIN_SCREEN);
    }

    @FXML
    private void bt_kundenanlegenClicked() {
        myController.setScreen(Hauptmenue.KUNDENANLEGEN_SCREEN);

    }

    @FXML
    private void bt_kundenloeschenClicked() {
        myController.setScreen(Hauptmenue.KUNDENLOESCHEN_SCREEN);

    }

    @FXML
    private void bt_kundenaendernClicked() {
        myController.setScreen(Hauptmenue.KUNDENAENDERN_SCREEN);

    }

    @FXML
    private void bt_kundenlisteClicked() {
        myController.setScreen(Hauptmenue.KUNDENLISTEANZEIGEN_SCREEN);

    }

    @FXML
    private void bt_kundenauftraege() {
        myController.setScreen(Hauptmenue.KUNDENMITAUFTRAEGE_SCREEN);

    }

}
