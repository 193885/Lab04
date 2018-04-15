package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studDao;
	
	public Model() {
		
		corsoDao= new CorsoDAO ();
		studDao = new StudenteDAO();
	}
	
	public List<Corso> getTuttiICorsi() {

		return corsoDao.getTuttiICorsi();
	}

	public Studente getStudente(String matricola) {
		
		return studDao.getStudenti().get(matricola);
	
	}
	
	public List <Studente> getIscrittiAlCorso(Corso c) {
				
		return corsoDao.getStudentiIscrittiAlCorso(c.getCodins());

	}
	
	public List <Corso> getCorsoByMatricola(String matricola){
		
		return studDao.getCorsiPerStudente(matricola);
	}

	public boolean controlloIscrizione(String matr, Corso c) {
		
		if(studDao.studenteIscritto(c).contains(matr)) 
				
				return true;
				
			else
				return false;
	}
}
