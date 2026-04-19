package de.opp3.samman.awk.usecase;

import java.util.Collection;


import de.opp3.samman.awk.model.Ressource;

public interface IRessourcenVw {
public void ressourceAnlegen(Ressource ressource);
public void ressourceAendern(Ressource ressource);
public boolean ressourceLoeschen(Ressource ressourceID);
 Collection<Ressource> ladeAlleRessources();
 Collection<Ressource> ladeRessourcenArt(String ressource);
}
