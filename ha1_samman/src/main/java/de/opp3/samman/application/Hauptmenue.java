package de.opp3.samman.application;

import java.util.Optional;

import de.opp3.samman.dlg.ScreensController;
import de.opp3.samman.awk.usecase.IAbrechnungVw;
import de.opp3.samman.awk.usecase.IAuftragsbearbeitungVw;
import de.opp3.samman.awk.usecase.IKundenVw;
import de.opp3.samman.awk.usecase.IKundenauftragVw;
import de.opp3.samman.awk.usecase.IRessourcenVw;
import de.opp3.samman.awk.usecase.impl.AbrechnungsVerwalter;
import de.opp3.samman.awk.usecase.impl.AuftragsverbeitungsVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenVerwalter;
import de.opp3.samman.awk.usecase.impl.KundenauftragVerwalter;
import de.opp3.samman.awk.usecase.impl.RessorcenVerwalter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Hauptmenue extends Application {

	private Stage mainStage;

	private IKundenVw kundenManager;
	private IKundenauftragVw auftragManager;
	private IRessourcenVw ressourcenManager;
	private IAuftragsbearbeitungVw auftragsbearbeitungManager;
	private IAbrechnungVw abrechnungManager;

	// Hauptmenue Szene
	public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "dlg/hauptmenue/Hauptmenue.fxml";
	// Kunenverwaltungsszenen
	public static final String KUNDEN_VERWALTER_SCREEN = "kundenverwalter";
	public static final String KUNDEN_VERWALTER_SCREEN_FXML = "dlg/kundenverwalter/KundenVerwalter_Screen.fxml";

	public static final String KUNDENANLEGEN_SCREEN = "kundenanlegen";
	public static final String KUNDENANLEGEN_SCREEN_FXML = "dlg/kundenverwalter/KundenAnlegen_Screen.fxml";

	public static final String KUNDENLOESCHEN_SCREEN = "kundenloeschen";
	public static final String KUNDENLOESCHEN_SCREEN_FXML = "dlg/kundenverwalter/KundenLoeschen_Screen.fxml";

	public static final String KUNDENAENDERN_SCREEN = "kundenaendern";
	public static final String KUNDENAENDERN_SCREEN_FXML = "dlg/kundenverwalter/KundenAendern_Screen.fxml";

	public static final String KUNDENLISTEANZEIGEN_SCREEN = "kundenliste anzeigen";
	public static final String KUNDENLISTEANZEIGEN_SCREEN_FXML = "dlg/kundenverwalter/KundenListeAnzeigen_Screen.fxml";
	
	public static final String KUNDENMITAUFTRAEGE_SCREEN = "kunden mit Aufträge anzeigen";
	public static final String KUNDENMITAUFTRAEGE_SCREEN_FXML = "dlg/kundenverwalter/KundenAuftragListeAnzeigen_Screen.fxml";



	// Aufträge Verwaltungsszenen

	public static final String KUNDENAUFTRAG_SCREEN = "kundenauftrag";
	public static final String KUNDENAUFTRAG_SCREEN_FXML = "dlg/auftragverwalter/KundenAuftrag_Screen.fxml";

	public static final String AUFTRAGSBEARBEITUNG_SCREEN = "auftragsbearbeitung";
	public static final String AUFTRAGSBEARBEITUNG_SCREEN_FXML = "dlg/auftragverwalter/Auftragsbearbeitung_Screen.fxml";

	public static final String ABRECHNUNG_SCREEN = "abrechnung";
	public static final String ABRECHNUNG_SCREEN_FXML = "dlg/auftragverwalter/Abrechnung_Screen.fxml";

	public static final String AUFTRAGANLEGEN_SCREEN = "auftraganlegen";
	public static final String AUFTRAGANLEGEN_SCREEN_FXML = "dlg/auftragverwalter/AuftragAnlegen_Screen.fxml";

	// Ressourcenverwaltungsszennen
	public static final String RESSOURCEN_VERWALTER_SCREEN = "ressourcenverwalter";
	public static final String RESSOURCEN_VERWALTER_SCREEN_FXML = "dlg/ressourceverwalter/Ressourcen_Verwalter.fxml";

	public static final String RESSOURCEANLEGEN_SCREEN = "ressourceanlegen";
	public static final String RESSOURCEANLEGEN_SCREEN_FXML = "dlg/ressourceverwalter/RessourceAnlegen_Sreen.fxml";

	public static final String RESSOURCELOESCHEN_SCREEN = "ressourceloeschen";
	public static final String RESSOURCELOESCHEN_SCREEN_FXML = "dlg/ressourceverwalter/RessourceLoeschen_Screen.fxml";

	public static final String RESSOURCEAENDERN_SCREEN = "ressourceaendern";
	public static final String RESSOURCEAENDERN_SCREEN_FXML = "dlg/ressourceverwalter/RessourceAendern_Screen.fxml";

	public static final String RESSOURCENLISTE_SCREEN = "ressourcenliste";
	public static final String RESSOURCENLISTE_SCREEN_FXML = "dlg/ressourceverwalter/RessourcenListe_Screen.fxml";

	@Override
	public void start(Stage primaryStage) {

		this.mainStage = primaryStage;

		// Usecase-Manager anlegen
		this.kundenManager = new KundenVerwalter();
		this.auftragManager = new KundenauftragVerwalter();
		this.ressourcenManager = new RessorcenVerwalter();
		this.auftragsbearbeitungManager = new AuftragsverbeitungsVerwalter();
		this.abrechnungManager = new AbrechnungsVerwalter();

		// Screen-Verwalter
		ScreensController mainContainer = new ScreensController();

		// Manager an den ScreensController übergeben
		mainContainer.setKundenManager(this.kundenManager);
		mainContainer.setAuftragManager(this.auftragManager);
		mainContainer.setRessourcenManager(this.ressourcenManager);
		mainContainer.setAuftragsbearbeitungManager(this.auftragsbearbeitungManager);
		mainContainer.setAbrechnungManager(this.abrechnungManager);

		// alle Screens laden
		// screen von Hauptmenü laden
		mainContainer.loadScreen(MAIN_SCREEN, MAIN_SCREEN_FXML);

		// screens von Kundenverwalter laden
		mainContainer.loadScreen(KUNDEN_VERWALTER_SCREEN, KUNDEN_VERWALTER_SCREEN_FXML);
		mainContainer.loadScreen(KUNDENANLEGEN_SCREEN, KUNDENANLEGEN_SCREEN_FXML);
		mainContainer.loadScreen(KUNDENLOESCHEN_SCREEN, KUNDENLOESCHEN_SCREEN_FXML);
		mainContainer.loadScreen(KUNDENAENDERN_SCREEN, KUNDENAENDERN_SCREEN_FXML);
		mainContainer.loadScreen(KUNDENLISTEANZEIGEN_SCREEN, KUNDENLISTEANZEIGEN_SCREEN_FXML);
		mainContainer.loadScreen(KUNDENMITAUFTRAEGE_SCREEN, KUNDENMITAUFTRAEGE_SCREEN_FXML);
		
		// screens von Kundenauftragverwalter laden
		mainContainer.loadScreen(KUNDENAUFTRAG_SCREEN, KUNDENAUFTRAG_SCREEN_FXML);
		mainContainer.loadScreen(AUFTRAGSBEARBEITUNG_SCREEN,
				AUFTRAGSBEARBEITUNG_SCREEN_FXML);
		mainContainer.loadScreen(ABRECHNUNG_SCREEN, ABRECHNUNG_SCREEN_FXML);
		mainContainer.loadScreen(AUFTRAGANLEGEN_SCREEN, AUFTRAGANLEGEN_SCREEN_FXML);
	

		// screens von Ressourceverwalter laden
		mainContainer.loadScreen(RESSOURCEN_VERWALTER_SCREEN,
				RESSOURCEN_VERWALTER_SCREEN_FXML);
		mainContainer.loadScreen(RESSOURCEANLEGEN_SCREEN, RESSOURCEANLEGEN_SCREEN_FXML);
		mainContainer.loadScreen(RESSOURCELOESCHEN_SCREEN, RESSOURCELOESCHEN_SCREEN_FXML);
		mainContainer.loadScreen(RESSOURCEAENDERN_SCREEN, RESSOURCEAENDERN_SCREEN_FXML);
		mainContainer.loadScreen(RESSOURCENLISTE_SCREEN, RESSOURCENLISTE_SCREEN_FXML);

		// Hauptmenü anzeigen
		mainContainer.setScreen(MAIN_SCREEN);
		//style.css
		mainContainer.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

		try {
			Group root = new Group();
			root.getChildren().add(mainContainer);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(confirmCloseEventHandler);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final EventHandler<WindowEvent> confirmCloseEventHandler = event -> {

		Alert closeConfirmation = new Alert(
				Alert.AlertType.CONFIRMATION,
				"Are you sure you want to exit?");
		Button exitButton = (Button) closeConfirmation.getDialogPane()
				.lookupButton(ButtonType.OK);
		exitButton.setText("Exit");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		closeConfirmation.initOwner(mainStage);

		closeConfirmation.setX(mainStage.getX() + 150);
		closeConfirmation.setY(mainStage.getY() - 300 + mainStage.getHeight());

		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if (!ButtonType.OK.equals(closeResponse.orElse(null))) {
			event.consume();
		}
	};

	
}
