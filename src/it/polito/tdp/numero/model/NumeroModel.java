package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.metadata.IIOInvalidTreeException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NumeroModel {
	
	//Fanno parte del modello
	
	private Set<Integer> tentativi;
	private final int NMAX = 100;
	private final int TMAX = 8;

	private int segreto;
	
	//private int tentativiFatti;
	private IntegerProperty tentativiFatti;
	
	private boolean inGioco = false;
	
	

	/**
	 * Costruttore: inizializza le variabili
	 */
	public NumeroModel() {
		inGioco = false;
		tentativiFatti = new SimpleIntegerProperty();
		tentativi = new HashSet<Integer>();
		
		
	}
	
	
	/**
	 * Avvia nuova partita
	 */
	public void newGame() {
		inGioco = true;
		this.segreto = (int) (Math.random() * NMAX) + 1;
		this.tentativiFatti.set(0);
		tentativi = new HashSet<Integer>();
		
		

	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * 
	 * @param t il tentativo
	 * @return 1 se è troppo alto, -1 se è troppo basso, 0 se ha indovinato
	 */
	public int tentativo(int t) {
		
		//Controllo se la partita è in corso
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		//Controllo se l'input è nel range corretto
		if(!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d", 1,NMAX));
		}
		
		
		
		
		//Gestione del tentativo: 3 casi
		
		this.tentativiFatti.set(this.tentativiFatti.get() +1);
		this.tentativi.add(new Integer(t));
		
		if(this.tentativiFatti.get() == this.TMAX) {
			
			//La partita è finita perchè ho finito i tentativi			
			this.inGioco = false;
			
				//non c'è la return perchè devo comunque 
				//controllare se l'ultimo tentativo fatto
				//è corretto
		
		}
		
		if(t == this.segreto) {
			this.inGioco = false;
			return 0; //deciso da noi
		}
		
		if (t>this.segreto) {
			return 1; //Numero troppo ALTO
		}
		
		return -1; //Numero troppo BASSO
		
		
	}
	
	//Così lo posso richiamare dall'esterno
	//Disaccoppiando le due cose se devo modificare la logica 
	//di controllo dell'input lo faccio una sola volta
	public boolean tentativoValido(int t) {
		
		
		if(t<1 || t>NMAX) {
			return false;
		} else {
			if(this.tentativi.contains(new Integer(t))) 
				return false;
			else {
				return true;
			}
		}
		
	}


	public int getSegreto() {
		return segreto;
	}


	public boolean isInGioco() {
		return inGioco;
	}


	public int getTMAX() {
		return TMAX;
	}
	
	public final IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	

	public final int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}
	

	public final void setTentativiFatti(final int tentativiFatti) {
		this.tentativiFattiProperty().set(tentativiFatti);
	}
}
