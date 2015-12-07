package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Aktywnosc
{
	private final SimpleIntegerProperty idAktywnosci, idDniaPracy;
	private final SimpleStringProperty nazwaAktywnosci, opis, godzinaOd, godzinaDo;
	
	public Aktywnosc(int idAktywnosci, int idDniaPracy, String nazwaAktywnosci, String opis, String godzinaOd, String godzinaDo)
	{
		super();
		this.idAktywnosci = new SimpleIntegerProperty(idAktywnosci);
		this.idDniaPracy = new SimpleIntegerProperty(idDniaPracy);
		this.nazwaAktywnosci = new SimpleStringProperty(nazwaAktywnosci);
		this.opis = new SimpleStringProperty(opis);
		this.godzinaOd = new SimpleStringProperty(godzinaOd);
		this.godzinaDo = new SimpleStringProperty(godzinaDo);
	}
	
	public String getOpis() {
		return opis.getValue();
	}

	public Integer getIdAktywnosci() {
		return idAktywnosci.getValue();
	}

	public String getNazwaAktywnosci() {
		return nazwaAktywnosci.getValue();
	}

	public Integer getIdDniaPracy() {
		return idDniaPracy.getValue();
	}

	public String getGodzinaOd() {
		return godzinaOd.getValue();
	}

	public String getGodzinaDo() {
		return godzinaDo.getValue();
	}
	
}
