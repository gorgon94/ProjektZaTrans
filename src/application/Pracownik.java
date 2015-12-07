package application;

public class Pracownik 
{//tu by³y private
	protected int idPrac; //1
	protected String imie; //2
	protected String nazwisko;//3
	protected String dataUrodzenia;//4 
	protected String pesel;//5
	protected String adres;//6
	protected String miasto;//7
	protected String kodPocztowy;//8
	protected String numerTelefonu;//9
	
	public Pracownik(){}
	
	public Pracownik(int _idPrac, String _imie, String _nazwisko, String _dataUrodzenia, String _pesel, String _adres, String _miasto, String _kodPocztowy, String _numerTelefonu) 
	{
		idPrac = _idPrac;
		imie = _imie;
		nazwisko = _nazwisko;
		dataUrodzenia = _dataUrodzenia;
		pesel = _pesel;
		adres = _adres;
		miasto = _miasto;
		kodPocztowy = _kodPocztowy;
		numerTelefonu = _numerTelefonu;
	}

	public int getId() {return idPrac;}
	public String getImie() {return imie;}
	public String getNazwisko() {return nazwisko;}
	public String getDataUrodzenia() {return dataUrodzenia;}
	public String getPesel() {return pesel;}
	public String getAdres() {return adres;}
	public String getMiasto() {return miasto;}
	public String getKodPocztowy() {return kodPocztowy;}
	public String getNumerTelefonu() {return numerTelefonu;}
	
	public String getNazwaPracownika()
	{
		return imie+ " " + nazwisko;
	}
}
