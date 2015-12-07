package application;

public class WybranyPracownik extends Pracownik
{
	Pracownik p;
	private int idNowejAktywnosci;
	private float stawka;
	private float stawkaFirmy;
	private String waluta;
	
	public WybranyPracownik(int idNowejAktywnosci, Pracownik p, float stawka, float stawkaFirmy, String waluta) 
	{
		this.p = p;
		this.idNowejAktywnosci = idNowejAktywnosci;
		this.setStawka(stawka);
		this.setStawkaFirmy(stawkaFirmy);
		this.setWaluta(waluta);
	}

	public String getNazwaWybranegoPracownika()
	{
		return p.getImie() +" " + p.getNazwisko();
	}
	
	public Pracownik getPracownik()
	{
		return p;
	}

	public int getIdNowejAktywnosci()
	{
		return idNowejAktywnosci;
	}

	public float getStawka() {
		return stawka;
	}

	public void setStawka(float stawka) {
		this.stawka = stawka;
	}

	public float getStawkaFirmy() {
		return stawkaFirmy;
	}

	public void setStawkaFirmy(float stawkaFirmy) {
		this.stawkaFirmy = stawkaFirmy;
	}

	public String getWaluta() {
		return waluta;
	}

	public void setWaluta(String waluta) {
		this.waluta = waluta;
	}

	
}
