package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
			
				Corso c = new Corso (codins, numeroCrediti, nome, periodoDidattico);
				
				corsi.add(c);
			}
			
			conn.close();
			corsi.add(null);
					
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return corsi;

	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List <Studente > getStudentiIscrittiAlCorso(String CodiceCorso) {
		
		final String sql = "SELECT * FROM studente WHERE matricola "
						+ "	IN (SELECT matricola FROM iscrizione WHERE iscrizione.codins = ? )";

		List<Studente> studentiIscritti = new LinkedList<Studente>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, CodiceCorso);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				String matricola = rs.getString("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String corsoDiStudi = rs.getString("CDS");
				
				Studente s = new Studente( matricola,nome, cognome, corsoDiStudi);
	
				studentiIscritti.add(s);
		
			}
			
			conn.close();
			
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return studentiIscritti;
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql = "INSERT INTO iscrizione (matricola,codins) VALUES ( ? , ? )";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, studente.getMatricola());
			st.setString(2, corso.getCodins());

			int righeModificate = st.executeUpdate();
			
			if(righeModificate ==1)
				
				return true;
						
			conn.close();
			
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return false;
	}
}
