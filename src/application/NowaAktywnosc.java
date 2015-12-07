package application;

public class NowaAktywnosc 
{
	private String nazwa, opis,godzinaOd, godzinaDo;
	
	public NowaAktywnosc(String nazwa, String opis, String godzinaOd, String godzinaDo)
	{
		this.nazwa = nazwa;
		this.opis = opis;
		this.godzinaOd = godzinaOd;
		this.godzinaDo = godzinaDo;
	}

	public String getOpis() {
		return opis;
	}


	public String getNazwa() {
		return nazwa;
	}
	public String getGodzinaDo() {
		return godzinaDo;
	}


	public String getGodzinaOd() {
		return godzinaOd;
	}
}
