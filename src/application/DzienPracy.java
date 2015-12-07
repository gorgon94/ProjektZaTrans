package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DzienPracy 
{
	private final SimpleIntegerProperty idDnia, dzien, rok; //1
	private final SimpleStringProperty miesiac;
	
	public DzienPracy(int idDnia, int dzien, String miesiac, int rok)
	{
		super();
		this.idDnia = new SimpleIntegerProperty(idDnia);
		this.miesiac = new SimpleStringProperty(miesiac);
		this.rok = new SimpleIntegerProperty(rok);
		this.dzien = new SimpleIntegerProperty(dzien);
	}

	public Integer getRok() {
		return rok.getValue();
	}

	public String getMiesiac() {
		return miesiac.getValue();
	}

	public Integer getIdDnia() {
		return idDnia.getValue();
	}

	public Integer getDzien() {
		return dzien.getValue();
	}

}
