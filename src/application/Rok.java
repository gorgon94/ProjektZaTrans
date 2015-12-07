package application;

import javafx.beans.property.SimpleIntegerProperty;

public class Rok 
{
	private final SimpleIntegerProperty idRoku, rok; //1
	
	public Rok(int idRoku, int rok)
	{
		super();
		this.idRoku = new SimpleIntegerProperty(idRoku);
		this.rok = new SimpleIntegerProperty(rok);
	}

	public Integer getIdRoku() {
		return idRoku.getValue();
	}

	public Integer getRok() {
		return rok.getValue();
	}
	
}
