package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.lab04.model.Studente;
import it.polito.tdp.lab04.model.Corso;


public class StudenteDAO {
	
	public HashMap <String,Studente> getStudenti() {
		
		HashMap <String,Studente> studenti = new HashMap <String,Studente>();

		final String sql = "SELECT * FROM studente";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String matricola = rs.getString("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String corsoDiStudi = rs.getString("CDS");

				Studente s = new Studente( matricola,nome, cognome, corsoDiStudi);
				
				studenti.put(matricola,s);
	
			}
									
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return studenti;
	}
	
	public List <Corso> getCorsiPerStudente(String matricola){
		
		List <Corso> corsi = new ArrayList<>();
		
		final String sql = "select * from corso where codins in (select codins from iscrizione where matricola = ? )";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				Corso c = new Corso (codins, numeroCrediti, nome, periodoDidattico);
				
				corsi.add(c);
			}
			
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return corsi;		
	}

	public List <String> studenteIscritto(Corso c) {
		
		final String sql = "select matricola from iscrizione where codins = ? ";

		List<String> studentiIscritti = new ArrayList <String>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, c.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) 
				
				studentiIscritti.add(rs.getString("matricola"));
							
		} catch (SQLException e) {
			
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return studentiIscritti;
	}

}
