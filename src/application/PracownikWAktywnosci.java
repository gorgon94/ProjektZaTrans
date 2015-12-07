package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PracownikWAktywnosci
{
	private final SimpleIntegerProperty id, idAktywnosci, idPrac;
	
	private final SimpleStringProperty imie, nazwisko, waluta;
	
	private final SimpleFloatProperty stawkaPracownika, stawkaFirmy;
	
	
	public PracownikWAktywnosci(int id, int idAktywnosci,int idPrac, String imie, String nazwisko, String waluta, float stawkaPracownika, float stawkaFirmy)
	{
		super();
		this.id = new SimpleIntegerProperty(id);
		this.idAktywnosci = new SimpleIntegerProperty(idAktywnosci);
		this.idPrac = new SimpleIntegerProperty(idPrac);
		this.imie = new SimpleStringProperty(imie);
		this.nazwisko = new SimpleStringProperty(nazwisko);
		this.waluta = new SimpleStringProperty(waluta);
		this.stawkaPracownika = new SimpleFloatProperty(stawkaPracownika);
		this.stawkaFirmy = new SimpleFloatProperty(stawkaFirmy);
	}

	public Integer getIdAktywnosci() {
		return idAktywnosci.getValue();
	}

	public String getNazwisko() {
		return nazwisko.getValue();
	}

	public String getWaluta() {
		return waluta.getValue();
	}

	public String getImie() {
		return imie.getValue();
	}

	public Float getStawkaPracownika() {
		return stawkaPracownika.getValue();
	}

	public Float getStawkaFirmy() {
		return stawkaFirmy.getValue();
	}

	public Integer getIdPrac() {
		return idPrac.getValue();
	}

	public Integer getId() {
		return id.getValue();
	}
	public String getPracownik(){
		return getImie() +" "+  getNazwisko();
	}
}
