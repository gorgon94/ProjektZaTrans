package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Miesiac 
{
	private final SimpleIntegerProperty idMiesiaca;
	private final SimpleStringProperty miesiac; //1
	
	public Miesiac(int idMiesiaca, String miesiac)
	{
		super();
		this.idMiesiaca = new SimpleIntegerProperty(idMiesiaca);
		this.miesiac = new SimpleStringProperty(miesiac);
	}

	public Integer getIdMiesiaca() {
		return idMiesiaca.getValue();
	}

	public String getMiesiac() {
		return miesiac.getValue();
	}
}
