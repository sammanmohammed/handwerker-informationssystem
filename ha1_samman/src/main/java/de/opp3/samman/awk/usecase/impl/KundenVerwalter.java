package de.opp3.samman.awk.usecase.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.opp3.samman.awk.model.Kunde;
import de.opp3.samman.awk.usecase.IKundenVw;
import jakarta.persistence.EntityManager;

public class KundenVerwalter extends GenericDAO<Kunde> implements IKundenVw {
  EntityManager em = emf.createEntityManager();

  public KundenVerwalter() {
    super(Kunde.class);
  }

  @Override
  public void kundeAnlegen(Kunde aKunde) {

    super.save(aKunde);
  }

  @Override
  public boolean kundenLoeschen(Kunde aKundenNr) {
    return super.delete(aKundenNr.getKunde_nr(), Kunde.class);
  }

  @Override
  public void kundenaender(Kunde aKunde) {
    super.update(aKunde);
  }

  @Override
  public boolean kundeExistiert(String vorname, String nachname, String plz) {

    Map<String, Object> params = new HashMap<>();
    params.put("vorname", vorname);
    params.put("nachname", nachname);
    params.put("plz", plz);

    List<Kunde> treffer = super.findListResult(
        Kunde.FIND_BY_NAME_PLZ,
        params);

    return !treffer.isEmpty();
  }

  @Override
  public Collection<Kunde> ladeAlleKunde() {
    return findAll();
  }

  @Override
  public Collection<Kunde> ladeAlleKuneMitAuftrag() {

    List<Kunde> k = super.excuteAQuery();
    return k;
  }

  @Override
  public List<Kunde> suchKundeByName(String nachname) {
    List<Kunde> result = em.createNamedQuery(Kunde.FIND_KUNDE_BY_NAME, Kunde.class).setParameter("name", nachname)
        .getResultList();
    return result;

  }

  public List<Kunde> sortiereKundenNachPLZ(String PLZ) {

    return ladeAlleKunde().stream().filter(k -> k.getPlz().equals(PLZ)).toList();

  }
  
  public Long countKundeInPLZ(String PLZ) {

    return ladeAlleKunde().stream().filter(k -> k.getPlz().equals(PLZ)).count();

  }
  public boolean anyMatchWithName(String name){
    return ladeAlleKunde().stream().anyMatch(k->k.getName().equals(name));

  

}
}
