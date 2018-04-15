/**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="choiceCorso"
    private ChoiceBox <Corso> choiceCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscritti"
    private Button btnCercaIscritti; // Value injected by FXMLLoader

    @FXML // fx:id="txtId"
    private TextField txtId; // Value injected by FXMLLoader

    @FXML // fx:id="checkRicerca"
    private Button  checkRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorso"
    private Button btnCercaCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void doCercaCorso(ActionEvent event) {
    	
    	if (txtId.getText().matches("[0-9]+") == false)
    	
    		txtResult.setText("Matricola errata");
    	
	    else if((model.getStudente(txtId.getText()) ) == null) 
	    		
	    	txtResult.setText("Matricola non presente");
    	
    	if(choiceCorso.getSelectionModel().getSelectedItem() == null  &&   model.getStudente(txtId.getText())  != null) {
    		
    		if( model.getCorsoByMatricola(txtId.getText()).size() == 0)
    			
    			txtResult.setText("studente non iscritto ad alcun corso");
    		
    		else {
    		
	    		txtResult.clear();
				
		    	for ( Corso c : model.getCorsoByMatricola(txtId.getText()) )
		    	
		    	txtResult.appendText(c.stampa());
    		}
    	}
    	    	
	    if ( choiceCorso.getSelectionModel().getSelectedItem() != null  &&   model.getStudente(txtId.getText())  != null ) {
	    			    		
	    	if( model.controlloIscrizione( txtId.getText(), choiceCorso.getSelectionModel().getSelectedItem()) ) 
	    			
	    		txtResult.setText("Studente già iscritto a questo corso");
	   	    		
	    	else
	    			
	    		txtResult.setText("Studente non iscritto al corso");
	    }
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	
    	if( choiceCorso.getSelectionModel().getSelectedItem() == null) 
    		
    		txtResult.setText("Nessun corso selezionato");

    	else {
    					
			if(  model.getIscrittiAlCorso(choiceCorso.getSelectionModel().getSelectedItem()).size() == 0 )
				
				txtResult.setText("nessun iscritto al corso");
			else {
				
				txtResult.clear();
				
		    	for ( Studente s : model.getIscrittiAlCorso(choiceCorso.getSelectionModel().getSelectedItem()) ) 
		    	
		    		txtResult.appendText(s.stampa());
			}
    	}
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	if (txtId.getText().matches("[0-9]+") == false)
    	
    		txtResult.setText("Matricola errata");
    	
	    else if( (model.getStudente(txtId.getText()) ) == null) 
	    		
	    	txtResult.setText("Matricola non presente");
    	
    	if(choiceCorso.getSelectionModel().getSelectedItem() != null  &&   model.getStudente(txtId.getText())  != null) {
    		
    		if( model.controlloIscrizione( txtId.getText(), choiceCorso.getSelectionModel().getSelectedItem()) ) 
    			
	    		txtResult.setText("Studente già iscritto a questo corso");
	   	    		
    		else {
    			
    			Studente s = new Studente(txtId.getText());
    			
    			if ( model.iscriviStudACorso( s , choiceCorso.getSelectionModel().getSelectedItem() ) )
    				
    				txtResult.setText("Studente iscritto al corso!");
    		}
    	}
    }

    @FXML
    void doReset(ActionEvent event) {

    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtId.clear();
    	choiceCorso.getSelectionModel().clearSelection();;
    	
    }

    @FXML
    void doRicerca(ActionEvent event) {
    	
    	if (txtId.getText().matches("[0-9]+") == false) {
        	
    		txtResult.setText("Matricola errata");
    		txtNome.clear();
    		txtCognome.clear();
    	}
    	  		
    	else if( (model.getStudente(txtId.getText()) ) == null ) {
    				
    		txtResult.setText("Matricola non presente");
    		txtNome.clear();
    	    txtCognome.clear();
    			
    	}
    		
    	else {
    	
    			txtResult.clear();
		    	txtNome.setText( (model.getStudente(txtId.getText()) ).getNome());
		    	txtCognome.setText( (model.getStudente(txtId.getText()) ).getCognome());
    	} 		
    }
    
 	public void setModel(Model model) {
		this.model = model;
        choiceCorso.getItems().addAll(model.getTuttiICorsi());
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choiceCorso != null : "fx:id=\"choiceCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert checkRicerca != null : "fx:id=\"checkRicerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaCorso != null : "fx:id=\"btnCercaCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        
    }
}
