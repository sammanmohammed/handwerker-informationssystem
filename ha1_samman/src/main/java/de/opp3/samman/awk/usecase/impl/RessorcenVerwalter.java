package de.opp3.samman.awk.usecase.impl;

import java.util.Collection;

import de.opp3.samman.awk.model.Ressource;
import de.opp3.samman.awk.usecase.IRessourcenVw;

public class RessorcenVerwalter extends GenericDAO<Ressource> implements IRessourcenVw {
    public RessorcenVerwalter() {
        super(Ressource.class);
    }

    @Override
    public void ressourceAnlegen(Ressource ressource) {
        super.save(ressource);
    }

    @Override
    public void ressourceAendern(Ressource ressource) {
        super.update(ressource);
    }

    @Override
    public boolean ressourceLoeschen(Ressource ressourcenId) {
        return super.delete(ressourcenId.getId(), Ressource.class);
    }

    @Override
    public Collection<Ressource> ladeAlleRessources() {
        return super.findAll();
    }

    @Override
    public Collection<Ressource> ladeRessourcenArt(String ressourceArt) {
      return ladeAlleRessources().stream().filter(rA-> rA.getArt().equals(ressourceArt)).toList();
    }

}
