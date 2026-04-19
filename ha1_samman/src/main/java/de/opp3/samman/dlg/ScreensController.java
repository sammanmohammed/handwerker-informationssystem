
package de.opp3.samman.dlg;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.opp3.samman.awk.usecase.IAbrechnungVw;
import de.opp3.samman.awk.usecase.IAuftragsbearbeitungVw;
import de.opp3.samman.awk.usecase.IKundenVw;
import de.opp3.samman.awk.usecase.IKundenauftragVw;
import de.opp3.samman.awk.usecase.IRessourcenVw;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ScreensController extends StackPane {

	private HashMap<String, Node> screens = new HashMap<>();
	private HashMap<String, ControlledScreen> controllers = new HashMap<>();  
	public static String sourcePath = "";
	private IKundenVw kundenManager;
	private IKundenauftragVw auftragManager;
	private IRessourcenVw ressourcenManager;
	private IAuftragsbearbeitungVw auftragsbearbeitungManager;
	private IAbrechnungVw abrechnungManager;

	public ScreensController() {
    super();
  
}

	
	public HashMap<String, Node> getScreens() {
		return screens;
	}


	public void setScreens(HashMap<String, Node> screens) {
		this.screens = screens;
	}


	public HashMap<String, ControlledScreen> getControllers() {
		return controllers;
	}


	public void setControllers(HashMap<String, ControlledScreen> controllers) {
		this.controllers = controllers;
	}


	public static String getSourcePath() {
		return sourcePath;
	}


	public static void setSourcePath(String sourcePath) {
		ScreensController.sourcePath = sourcePath;
	}


	public IRessourcenVw getRessourcenManager() {
		return ressourcenManager;
	}


	public void setRessourcenManager(IRessourcenVw ressourcenManager) {
		this.ressourcenManager = ressourcenManager;
	}


	public IAuftragsbearbeitungVw getAuftragsbearbeitungManager() {
		return auftragsbearbeitungManager;
	}


	public void setAuftragsbearbeitungManager(IAuftragsbearbeitungVw auftragsbearbeitungManager) {
		this.auftragsbearbeitungManager = auftragsbearbeitungManager;
	}


	public IAbrechnungVw getAbrechnungManager() {
		return abrechnungManager;
	}


	public void setAbrechnungManager(IAbrechnungVw abrechnungManager) {
		this.abrechnungManager = abrechnungManager;
	}


	
	
	
	public void addScreen(String name, Node screen) { 
	       screens.put(name, screen); 
	   } 
	
	public boolean loadScreen(String name, String resource) { 
		System.out.println("Name: "+name);
		System.out.println("Resource: "+resource);

		String file = System.getProperty("user.dir")+"/target/classes/"+resource;
		System.out.println(file);
		
	    try { 

	       FXMLLoader myLoader = new FXMLLoader();
	       File f = new File(file);
	       URL url = f.toURI().toURL();
	       myLoader.setLocation(url);
//	       System.out.println("Location: "+myLoader.getLocation());
	       
	       Parent loadScreen = (Parent) myLoader.load(); 
	       ControlledScreen myScreenControler = 
	              ((ControlledScreen) myLoader.getController());
	       this.controllers.put(name, myScreenControler);
	       myScreenControler.setScreenParent(this); 
	       addScreen(name, loadScreen); 
	       System.out.println("Anzahl Screens: "+screens.size());
	       return true; 
	     }catch(Exception e) { 
	    	 System.out.println("Fehler beim laden von "+file);
	    	 System.out.println(e.getMessage()); 
	    	 return false; 
	     }
	     
	} 

	
	public boolean setScreen(final String name) { 
	     
		@SuppressWarnings("unused")
		Node screenToRemove;
        if(screens.get(name) != null){   //screen loaded
        	 if(!getChildren().isEmpty()){    //if there is more than one screen
        		 	getChildren().add(0, screens.get(name));     //add the screen
        		 	screenToRemove = getChildren().get(1);
        		 	getChildren().remove(1);                    //remove the displayed screen
        	 }else{
        		 getChildren().add(screens.get(name));       //no one else been displayed, then just show
        	}
        	this.controllers.get(name).initData();  // Aufruf der InitData in jedem Controller beim Szenewechsel (z.B. zum Aktualisieren der TableView)
        	return true;
         }else {
        	 System.out.println("screen hasn't been loaded!!! \n");
        	 return false;
         }     
	}
	
	public boolean unloadScreen(String name) { 
	
		if(screens.remove(name) == null) { 
			System.out.println("Screen didn't exist"); 
	        return false; 
	      } else { 
	           return true; 
	     } 
	}

	public void print() {
		Set<String> keys = screens.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()){
			System.out.println("key: "+it.next());
		} 
		
	}

	public void setKundenManager(IKundenVw kundenManager) {
		this.kundenManager = kundenManager;
	}
	
	public IKundenVw getKundenManager(){
		return this.kundenManager;
	}
		
	public void setAuftragManager(IKundenauftragVw auftragManager){
		this.auftragManager = auftragManager;
	}
	public IKundenauftragVw getAuftragManager(){
		return this.auftragManager;
	}
	
	public ControlledScreen getController(String name){
		return this.controllers.get(name);
	}
	
}