package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import wyjatki.DodanoJuzAktywnoscOTakiejNazwieException;
import wyjatki.DodanoPracownikaException;
import wyjatki.NajpierwDodajAktywnosciException;
import wyjatki.NieMoznaNadpisacAktywnosciException;
import wyjatki.PodanyDzienJestJuzWBazieException;
import wyjatki.ProszeWpisacNazweException;
import wyjatki.WybierzDzienException;
import wyjatki.WybierzGodzinyPracyException;


public class ScenaDodajDzienPracy implements Initializable
{
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML DatePicker kalendarz;
	@FXML ComboBox<String> godzinaOdCombo, minutaOdCombo, godzinaDoCombo, minutaDoCombo, walutaCombo;
	@FXML ListView<String> pracownicyListView, wybraniPracownicyListView, aktywnosciListView;
	@FXML TextField nazwaAktywnosciText, stawkaText, stawkaFirmyText;
	@FXML TextArea opisTextArea;
	@FXML Label wybranyPracownikLabel;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	ArrayList<WybranyPracownik> tempPozostali = new ArrayList<WybranyPracownik>();
	ArrayList<WybranyPracownik> tempWybrani = new ArrayList<WybranyPracownik>();
	
	ArrayList<NowaAktywnosc> listaNowychAktywnosci = new ArrayList<NowaAktywnosc>();
	ObservableList<String> listaNazwAktywnosci = FXCollections.observableArrayList();
	
	ArrayList<WybranyPracownik> listaPozostalychPracownikow = new ArrayList<WybranyPracownik>(); //uzywane po nacisnieciu aktynosci, jesli nie wcisniete uzywamy Main.listaPracownikow
	ObservableList<String> listaPozostalychPracownikowString = FXCollections.observableArrayList();// lista wyswietlana w listview

	ArrayList<WybranyPracownik> listaWybranychPracownikow = new ArrayList<WybranyPracownik>();//ta lista idzie do bazy, 
	ObservableList<String> listaWybranychPracownikowString = FXCollections.observableArrayList(); // lista wyswietlana w listview
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////dfg////
	int wybranyRok, wybranyMiesiac, wybranyDzien;
	
	int isDayInDatabase = -1; //-1 nie wybrano dnia; 1 jest juz taki dzien w bazie; 0 nie ma takiego dnia w bazie
	int aktywnoscChosen = 0;//  aktywnoscChosen >-1  wybrano o aktywnosc o indeskie aktywnoscChosen
	int wybranyPracownikChosen = -1;
	boolean isAktywnoscChosen = false; //false nie wybrano aktywnosci
	boolean isWybranyPracownikChosen = false; //false -> nie wybrano zadnego pracownika
	String aktywnoscItemSelected = null; //aktywnosc -> do doubleclicka
	String pracownikItemSelected = null; //pracownik -> do double clicka
	String wybranyPracownikItemSelected = null; //wybrany pracownik -> do double clicka
/////////////////////////////////////////////////////////////////////////////////////////////////////inicjalizacja
	public void initialize(URL url, ResourceBundle rb)
	{
		try
		{
			////dodanie danych do combobox�w
			godzinaOdCombo.getItems().addAll(ScenaDniPracy.godziny);
			minutaOdCombo.getItems().addAll(ScenaDniPracy.minuty);
			godzinaDoCombo.getItems().addAll(ScenaDniPracy.godziny);
			minutaDoCombo.getItems().addAll(ScenaDniPracy.minuty);
			walutaCombo.getItems().addAll(ScenaDniPracy.waluty);
			
			godzinaOdCombo.setValue("");
			minutaOdCombo.setValue("");
			godzinaDoCombo.setValue("");
			minutaDoCombo.setValue("");

			////dodanie pracownikow z bazy do listview
			BazaDanych.wczytajPracownikow();
			for(int i = 0; i<Main.listaPracownikow.size();i++)
			{
				tempPozostali.add(new WybranyPracownik(listaNowychAktywnosci.size(),Main.listaPracownikow.get(i),0,0,"waluta"));
				listaPozostalychPracownikowString.add(tempPozostali.get(i).getNazwaWybranegoPracownika());
			}
			
			pracownicyListView.setItems(listaPozostalychPracownikowString);
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
////////////////////////////////////////////////////////////////////////////////////////////obsługa kalendarza
	public void dayChosen()
	{
		String data = kalendarz.getValue().toString();
		wybranyDzien = Integer.parseInt(data.substring(8, 10));
		wybranyMiesiac = Integer.parseInt(data.substring(5, 7));
		wybranyRok = Integer.parseInt(data.substring(0, 4));
		isDayInDatabase = BazaDanych.isDayInDatabase(wybranyDzien,wybranyMiesiac, wybranyRok);
		idDayAlert();
	
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////obs�uga ListView
	public void aktywnosciChosen() throws SQLException, NajpierwDodajAktywnosciException
	{
		try
		{	
			clearDaneAktywnosci();
			String newItemSelected = aktywnosciListView.getSelectionModel().getSelectedItem();
			if(aktywnoscItemSelected != null && aktywnoscItemSelected == newItemSelected)//doubleClick
			{
				aktywnoscItemSelected = null;		
				///////////////////obs�uga po doubleclicku\/
				aktywnoscChosen = aktywnosciListView.getSelectionModel().getSelectedIndex();	
				usunAktywnosc();
			}
			else
			{
				aktywnoscChosen = aktywnosciListView.getSelectionModel().getSelectedIndex();
				aktywnoscItemSelected = aktywnosciListView.getSelectionModel().getSelectedItem();//musi byc do doubleclicka
				//////obs�uga po oneClicku\/
				wyswietlPozostalychIWybranychPracownikow();
				
				//czy aktywnosc wybrana  i co wybrano
				nazwaAktywnosciText.setText(listaNowychAktywnosci.get(aktywnoscChosen).getNazwa());
				opisTextArea.setText(listaNowychAktywnosci.get(aktywnoscChosen).getOpis());
				godzinaOdCombo.setValue(listaNowychAktywnosci.get(aktywnoscChosen).getGodzinaOd().substring(0, 2));
				godzinaDoCombo.setValue(listaNowychAktywnosci.get(aktywnoscChosen).getGodzinaDo().substring(0, 2));
				minutaDoCombo.setValue(listaNowychAktywnosci.get(aktywnoscChosen).getGodzinaDo().substring(3, 5));
				minutaOdCombo.setValue(listaNowychAktywnosci.get(aktywnoscChosen).getGodzinaOd().substring(3, 5));
			}
			isAktywnoscChosen = true;
			
		} 
		catch(ArrayIndexOutOfBoundsException e) 
		{  // wywala blad, gdy nie ma aktywnosci
			dodajPracownikowDoListyGlownej();
			try{throw new wyjatki.NajpierwDodajAktywnosciException();}
			catch(NajpierwDodajAktywnosciException er){}	
		}
	}
	
	public void usunAktywnosc() throws SQLException
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie");
		alert.setHeaderText(null);
		alert.setContentText("Usun�� aktywno��?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{///////////////////obsluga usuwania aktywnosci
			
			
		}/////////////////////
		else{}
	}
	
	public void pracownikChosen()
	{
		try
		{
			aktywnoscItemSelected = null; //podowuje odznaczenie doubleclicka w aktywniosciListview
			
			////////////////////////////////////doubleclick
			String newItemSelected = pracownicyListView.getSelectionModel().getSelectedItem();
			int index;
			if(pracownikItemSelected != null && pracownikItemSelected == newItemSelected)
			{
				pracownikItemSelected = null;
				index = pracownicyListView.getSelectionModel().getSelectedIndex();
				///////////obsluga po double clicku (wybieranie pracownikow usuwanie z pracownikow )
				
				if(isAktywnoscChosen == false) //jesli nei wybrano aktywnosci
				{
					//operacja dodawania wybranych i usuwania pozosyalych TYLKO NA STRINGACH
					listaWybranychPracownikowString.add(listaPozostalychPracownikowString.get(index));
					listaPozostalychPracownikowString.remove(index);
					
					//operacje na danych
					tempWybrani.add(tempPozostali.get(index));
					tempPozostali.remove(index);
					
					//wyswietlanie w listview
					pracownicyListView.setItems(listaPozostalychPracownikowString);
					wybraniPracownicyListView.setItems(listaWybranychPracownikowString);
				}
				else//jesli wybrano aktywnosc
				{
					
				}
			}
			else
			{
				pracownikItemSelected = pracownicyListView.getSelectionModel().getSelectedItem(); //musi byc do doubleclicka
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void wybranyPracownikChosen()
	{
		try
		{
			String newItemSelected = wybraniPracownicyListView.getSelectionModel().getSelectedItem();
			int index;
			if(wybranyPracownikItemSelected != null && wybranyPracownikItemSelected == newItemSelected)
			{
				wybranyPracownikItemSelected = null;
				index = wybraniPracownicyListView.getSelectionModel().getSelectedIndex();
				///////////obsluga po double clicku (usuwanie z wybranych pracownikow i dodawanie do pracownikow)
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Potwierdzenie");
				alert.setHeaderText(null);
				alert.setContentText("Usuń pracownika?");
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK)
				{//////////////////////obsluga usuwania wybranego pracownika
					if(isAktywnoscChosen == false)//jesli nie wybrano aktywnosci 
					{///////////////////////////
						//operacja dodawania wybranych i usuwania pozosyalych TYLKO NA STRINGACH
						listaPozostalychPracownikowString.add(listaWybranychPracownikowString.get(index));
						listaWybranychPracownikowString.remove(index);
						
						//operacja na danych
						tempPozostali.add(tempWybrani.get(index));
						tempWybrani.remove(index);
						
						//wyswietlanie w listview
						pracownicyListView.setItems(listaPozostalychPracownikowString);
						wybraniPracownicyListView.setItems(listaWybranychPracownikowString);
					}///////////////////////////
					else//jesli wybrano aktywnosc
					{////////////////////////////
						//nie robimy póki co nic, powinna byc tu modyfikacja (dodanie i usuwanie pracownikow konkretnej aktywnosci)			
					}///////////////////////
				}
				else{}
			}
			else
			{
				wybranyPracownikChosen = wybraniPracownicyListView.getSelectionModel().getSelectedIndex();
				wybranyPracownikItemSelected = wybraniPracownicyListView.getSelectionModel().getSelectedItem();
				
				//obsługa po oneClicku (pokazanie danych pracownika oraz ich edycja po odznaczeniu zaznaczenia)
				if(isAktywnoscChosen == false)//jesli nie wybrano aktywnosci 
				{
					wybranyPracownikLabel.setText(wybranyPracownikItemSelected);
					stawkaText.setText(Float.toString(tempWybrani.get(wybranyPracownikChosen).getStawka()));
					stawkaFirmyText.setText(Float.toString(tempWybrani.get(wybranyPracownikChosen).getStawkaFirmy()));
					walutaCombo.setValue(tempWybrani.get(wybranyPracownikChosen).getWaluta());
				}
				else // jeśli wybrano aktywnosc
				{
					//pokazujemy jakie stawki oraz walute ma pracownik
					wyswietlDaneWybranychPracownikow();
				}
			}
			aktywnoscItemSelected=null;		
			isWybranyPracownikChosen = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////obsluga buttonow
	
	public void bDodajAktywnoscClicked()
	{
		try
		{
			if(isAktywnoscChosen == false)
			{
				if(nazwaAktywnosciText.getText().equals("") == false)
				{
					if(listaNazwAktywnosci.contains(nazwaAktywnosciText.getText()) == false)
					{
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Potwierdzenie");
						alert.setHeaderText(null);
						alert.setContentText("Dodaj aktywność?");
						
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK)
						{
							String nazwa = nazwaAktywnosciText.getText();
							String opis = opisTextArea.getText();
							
							String godzinaOd = godzinaOdCombo.getValue();
							String godzinaDo = godzinaDoCombo.getValue();
							String minutaOd = minutaOdCombo.getValue();
							String minutaDo = minutaDoCombo.getValue();
							if(godzinaOd == "" || godzinaDo == "" || minutaOd == "" || minutaDo == "")
							{
								try{throw new wyjatki.WybierzGodzinyPracyException();}
								catch(WybierzGodzinyPracyException er){}	
							}
							else
							{
								godzinaOd = godzinaOd + ":" + minutaOd;
								godzinaDo = godzinaDo + ":" + minutaDo;
								
								//dodanie tempPozostali do  listy pozostalych pracownikow wraz z idNowejAktywnosci
								listaPozostalychPracownikow.addAll(tempPozostali);

								//dodanie tempWybrani  do  listy wybranych pracownikow wraz z idNowejAktywnosci
								listaWybranychPracownikow.addAll(tempWybrani);
								
								//////////////zapis aktywnosci do listy nowych aktywnosci
								listaNazwAktywnosci.add(nazwaAktywnosciText.getText());
								listaNowychAktywnosci.add(new NowaAktywnosc(nazwa, opis,godzinaOd, godzinaDo));
								
								//wyczyszczenie i dodanie wszystkich pracownikow do tempPozostali wraz z nowym id
								dodajPracownikowDoListyGlownej();
								clearDaneAktywnosci();
								aktywnosciListView.setItems(listaNazwAktywnosci);
							}	
						} 
						else {}//jesli uzytkownik wcisnie cancel		
					}
					else
					{
						try{throw new wyjatki.DodanoJuzAktywnoscOTakiejNazwieException();}
						catch(DodanoJuzAktywnoscOTakiejNazwieException er){}	
					}
				}
				else
				{
					try{throw new wyjatki.ProszeWpisacNazweException();}
					catch(ProszeWpisacNazweException er){}	
				}
			}
			else
			{
				try{throw new wyjatki.NieMoznaNadpisacAktywnosciException();}
				catch(NieMoznaNadpisacAktywnosciException er){}
			}
			bUsunZaznaczenieClicked();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void bDodajDzienClicked()
	{
		if (isDayInDatabase == -1)
		{
			try{throw new wyjatki.WybierzDzienException();}
			catch(WybierzDzienException er){}
		}
		else if (isDayInDatabase == 1)
		{
			try{throw new wyjatki.PodanyDzienJestJuzWBazieException();}
			catch(PodanyDzienJestJuzWBazieException er){}
		}
		else
		{
			try //obsługa jesli wszytsko ok
			{
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void bUsunZaznaczenieClicked() throws SQLException
	{
		
		wybranyPracownikLabel.setText("Nie wybrano pracownika");
		isWybranyPracownikChosen = false;
		stawkaText.clear();
		stawkaFirmyText.clear();
		walutaCombo.setValue("");
		isAktywnoscChosen = false;//odznaczenie w danych aktywnosci
		aktywnoscItemSelected = null; //usuniecie double clikca z aktywnosic
		clearDaneAktywnosci(); // wyczyszczenie okna
		dodajPracownikowDoListyGlownej(); // wyczysczenie okien z pracownikami oraz załadowanie wszystkich pracownikow z bazy do pozostalych pracownikkoa
	}
	
	public void bZapiszPracownikaClicked()
	{
		try
		{
			Float stawka = Float.parseFloat(stawkaText.getText());
			Float stawkaFirmy = Float.parseFloat(stawkaFirmyText.getText());
			String waluta = walutaCombo.getValue();
			
			tempWybrani.get(wybranyPracownikChosen).setStawka(stawka);
			tempWybrani.get(wybranyPracownikChosen).setStawkaFirmy(stawkaFirmy);
			tempWybrani.get(wybranyPracownikChosen).setWaluta(waluta);
			
			try{throw new wyjatki.DodanoPracownikaException();}
			catch(DodanoPracownikaException er){}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////dodatkowe metody
	public void wyswietlDaneWybranychPracownikow()
	{
		stawkaText.clear();
		stawkaFirmyText.clear();
		walutaCombo.setValue("");
		wybranyPracownikLabel.setText("Nie wybrano pracownika");	
		for(int i=0; i<listaWybranychPracownikow.size(); i++)
		{
			if(listaWybranychPracownikow.get(i).getIdNowejAktywnosci() == aktywnoscChosen && listaWybranychPracownikow.get(i).getNazwaWybranegoPracownika().equals(wybranyPracownikItemSelected))
			{	
				stawkaText.setText(Float.toString(listaWybranychPracownikow.get(i).getStawka())); 
				stawkaFirmyText.setText(Float.toString(listaWybranychPracownikow.get(i).getStawkaFirmy()));
				walutaCombo.setValue(listaWybranychPracownikow.get(i).getWaluta());
			}
		}
	}
	
	public void usunZaznaczenie() throws SQLException //fukcja wykonujaca sie po kliknieciu w coś poza elementami okna
	{
		if(isWybranyPracownikChosen == true)
		{
			try
			{	//edycja danych pracownika
				float stawka = Float.parseFloat(stawkaText.getText());
				float stawkaFirmy = Float.parseFloat(stawkaFirmyText.getText());
				String waluta = walutaCombo.getValue();
				
				wybranyPracownikLabel.setText(tempWybrani.get(wybranyPracownikChosen).getNazwaWybranegoPracownika());
				tempWybrani.get(wybranyPracownikChosen).setStawka(stawka);
				tempWybrani.get(wybranyPracownikChosen).setStawkaFirmy(stawkaFirmy);
				tempWybrani.get(wybranyPracownikChosen).setWaluta(waluta);
			}
			catch(Exception e)
			{
				System.err.println(e);
			}	
		}
		wybranyPracownikLabel.setText("Nie wybrano pracownika");
		isWybranyPracownikChosen = false;
		stawkaText.clear();
		stawkaFirmyText.clear();
		walutaCombo.setValue("");
	}
	
	public void wyswietlPozostalychIWybranychPracownikow()
	{
		int i;
		listaPozostalychPracownikowString.clear();
		listaWybranychPracownikowString.clear();
		
		//wyswietlanie pozostalych pracownikow
		for(i=0;i<listaPozostalychPracownikow.size();i++)
		{
			if(aktywnoscChosen == listaPozostalychPracownikow.get(i).getIdNowejAktywnosci())
			{
				listaPozostalychPracownikowString.add(listaPozostalychPracownikow.get(i).getNazwaWybranegoPracownika());
			}
		}
		pracownicyListView.setItems(listaPozostalychPracownikowString); 
		
		//wyswietlanie wybranych pracownikow
		for(i=0;i<listaWybranychPracownikow.size(); i++)
		{
			if(aktywnoscChosen == listaWybranychPracownikow.get(i).getIdNowejAktywnosci())
			{
				listaWybranychPracownikowString.add(listaWybranychPracownikow.get(i).getNazwaWybranegoPracownika());
			}
		}
		wybraniPracownicyListView.setItems(listaWybranychPracownikowString);
	}
	
	public void dodajPracownikowDoListyGlownej() throws SQLException//dodanie 
	{// czysci ekran z pracownikow,wyswietla wszystkich pracownikow, dodaje pracownikow do listy tymczasowej z nowym id
		int id = listaNowychAktywnosci.size();
		Main.listaPracownikow.clear();
		BazaDanych.wczytajPracownikow();
		tempPozostali.clear();
		tempWybrani.clear();
		listaPozostalychPracownikowString.clear();
		listaWybranychPracownikowString.clear();
		for(int i=0; i<Main.listaPracownikow.size();i++)
		{
			tempPozostali.add(new WybranyPracownik(id,Main.listaPracownikow.get(i),0,0," "));
			listaPozostalychPracownikowString.add(Main.listaPracownikow.get(i).getNazwaPracownika());
		}
		pracownicyListView.setItems(listaPozostalychPracownikowString); 
		wybraniPracownicyListView.setItems(listaWybranychPracownikowString);
	}
	
	
	public void idDayAlert()
	{
		if (isDayInDatabase == -1)
		{
			try{throw new wyjatki.WybierzDzienException();}
			catch(WybierzDzienException er){}
		}
		else if (isDayInDatabase == 1)
		{
			try{throw new wyjatki.PodanyDzienJestJuzWBazieException();}
			catch(PodanyDzienJestJuzWBazieException er){}
		}
		else
		{
			
		}
	}
	public void clearDaneAktywnosci()
	{
		godzinaOdCombo.setValue("");
		minutaOdCombo.setValue("");
		godzinaDoCombo.setValue("");
		minutaDoCombo.setValue("");
		walutaCombo.setValue("");
		nazwaAktywnosciText.clear();
		opisTextArea.clear();
		isAktywnoscChosen = false;
	}
	public void clearAktywnosci()
	{
		clearDaneAktywnosci();
		tempWybrani.clear();
		tempPozostali.clear();
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

