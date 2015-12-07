package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ScenaDniPracy implements Initializable 
{
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@FXML
	TableView<DzienPracy> tabela;
	@FXML
	TableColumn<DzienPracy, Integer> dzienColumn;
	@FXML
	TableColumn<DzienPracy, String> miesiacColumn;
	@FXML
	TableColumn<DzienPracy, Integer> rokColumn;
	@FXML
	ListView<String> aktywnosciListView, pracownicyListView;
	@FXML
	TextArea opisText;
	@FXML
	ComboBox<Integer> rokCombo;
	@FXML
	ComboBox<String> miesiacCombo;
	@FXML
	ComboBox<String> godzinaOdCombo;
	@FXML
	ComboBox<String> godzinaDoCombo;
	@FXML
	ComboBox<String> minutaOdCombo;
	@FXML
	ComboBox<String> minutaDoCombo;
	@FXML
	ComboBox<String> walutaCombo;
	@FXML
	TextField stawkaText, stawkaFirmyText;
	@FXML
	Label sumaGodzinLabel, walutaLabel, walutaLabel2, kosztPracownikaLabel, zarobekLabel, pracownikLabel;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	ObservableList<Integer> listaLatDoCombo = FXCollections.observableArrayList();
	ObservableList<String> listaMiesiecyDoCombo = FXCollections.observableArrayList();
	ObservableList<String> listaAktywnosciDoListView = FXCollections.observableArrayList();
	ObservableList<String> listaPracownikowWAktywnosciDoListView = FXCollections.observableArrayList();
	ObservableList<String> listaGodzinOdListView = FXCollections.observableArrayList();
	ObservableList<String> listaGodzinDoListView = FXCollections.observableArrayList();
	static ObservableList<String> godziny = FXCollections.observableArrayList
	(
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00"
	);
	static ObservableList<String> minuty = FXCollections.observableArrayList
	(
			"00", "15", "30", "45"
	);
	static ObservableList<String> waluty = FXCollections.observableArrayList
	(
			"PLN", "euro"
	);

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	float sumaGodzin, wynagrodzeniePracownika, zarobekFirmy;
	int selectedRok = -1, selectedMiesiac = -1, selectedDzien = -1, selectedAktywnosc = -1, selectedPracownik = -1;
	int tabSelectedAktywnosc[];
	boolean tableViewClicked = false;
	boolean aktywnosciClicked = false;
	boolean pracownicyClicked = false;
	static int rok,miesiac;
	
	static Stage okno = new Stage();
	///////////////////////////////////////////////////////////////////////////////////////////////////
	public ScenaDniPracy() 
	{
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) 
	{
		inicjalizacja();
	}

	public void inicjalizacja()
	{
		int i;
		try {
			BazaDanych.dbConnection();
			Main.listaDniPracy.clear();
			BazaDanych.wczytajLataMiesiace();
			for (i = 0; i < Main.listaLat.size(); i++) 
			{
				listaLatDoCombo.add(Main.listaLat.get(i).getRok());
			}
			rokCombo.setValue(Main.listaLat.get(0).getRok()); 
			selectedRok = 1;
			rokCombo.setItems(listaLatDoCombo);
			for (i = 0; i < Main.listaMiesiecy.size(); i++) 
			{
				listaMiesiecyDoCombo.add(Main.listaMiesiecy.get(i).getMiesiac());
			}
			miesiacCombo.setValue(Main.listaMiesiecy.get(0).getMiesiac());
			selectedMiesiac = 1;
			miesiacCombo.setItems(listaMiesiecyDoCombo);
			if (selectedRok != -1 && selectedMiesiac != -1)
			{
				daneDoTableView(selectedMiesiac, selectedRok);
			}
			

			// dodanie danych do combo z godzinami pracy
			godzinaOdCombo.getItems().addAll(godziny);
			godzinaDoCombo.getItems().addAll(godziny);
			minutaOdCombo.getItems().addAll(minuty);
			minutaDoCombo.getItems().addAll(minuty);
			walutaCombo.getItems().addAll(waluty);
		} catch (SQLException | ClassNotFoundException e) 
		{
			System.err.println(e);
		}
	}

	public void aktualizujCombo() 
	{

		try {
			walutaLabel.setText("PLN");
			walutaLabel2.setText("PLN");
			if (tableViewClicked == true && aktywnosciClicked == true) {
				liczGodzinyPracy();
				if (pracownicyClicked == true) {
					liczWynagrodzeniePracownika();
					liczZarobekFirmy();
					walutaLabel.setText(walutaCombo.getValue());
					walutaLabel2.setText(walutaCombo.getValue());
				}
			}
		} catch (Exception e) {}
	}

	public void clear() 
	{
		opisText.clear();
		godzinaOdCombo.setValue("");
		godzinaDoCombo.setValue("");
		minutaOdCombo.setValue("");
		minutaDoCombo.setValue("");
		walutaCombo.setValue("");
		sumaGodzinLabel.setText("0");
		walutaLabel.setText("PLN");
		walutaLabel2.setText("PLN");
		kosztPracownikaLabel.setText("0");
		zarobekLabel.setText("0");
		stawkaText.setText("");
		stawkaFirmyText.setText("");

		selectedAktywnosc = -1;
		selectedPracownik = -1;
	}

	public void clearAll() 
	{
		listaAktywnosciDoListView.clear();
		listaPracownikowWAktywnosciDoListView.clear();
		clear();
	}

	public void daneDoTableView(int selectedMiesiac, int selectedRok) 
	{
		Main.listaDniPracy.removeAll(Main.listaDniPracy);
		BazaDanych.wczytajDniPracy(selectedMiesiac, selectedRok);
		tabela.setItems(Main.listaDniPracy);
		dzienColumn.setCellValueFactory(new PropertyValueFactory<DzienPracy, Integer>("dzien"));
		miesiacColumn.setCellValueFactory(new PropertyValueFactory<DzienPracy, String>("miesiac"));
		rokColumn.setCellValueFactory(new PropertyValueFactory<DzienPracy, Integer>("rok"));
	}

	public void rokChosen() 
	{
		clearAll();
		clearDaySelection();
		aktywnosciClicked = false;
		tableViewClicked = false;
		pracownicyClicked = false;
		selectedRok = rokCombo.getSelectionModel().getSelectedIndex() + 1;
		if (selectedMiesiac != -1)
			daneDoTableView(selectedMiesiac, selectedRok);
	}

	public void clearDaySelection() 
	{
		selectedDzien = -1;
	}

	public void miesiacChosen() 
	{
		clearAll();
		clearDaySelection();
		tableViewClicked = false;
		aktywnosciClicked = false;
		pracownicyClicked = false;
		selectedMiesiac = miesiacCombo.getSelectionModel().getSelectedIndex() + 1;
		if (selectedMiesiac != -1)
			daneDoTableView(selectedMiesiac, selectedRok);
	}

	public void tableViewClicked() throws SQLException 
	{
		aktywnosciClicked = false;
		pracownicyClicked = false;
		try {
			BazaDanych.dbConnection();
			clearAll();
			// wybieranie dnia i wczytywanie aktywnosci do listView na podstawie
			// id wybranego dnia
			selectedDzien = tabela.getSelectionModel().getSelectedItem().getIdDnia();
			Main.listaAktywnosci.clear();
			listaAktywnosciDoListView.clear();
			BazaDanych.wczytajAktywnosci(selectedDzien);
			int[] tab = new int[Main.listaAktywnosci.size()];
			tabSelectedAktywnosc = tab;

			for (int i = 0; i < Main.listaAktywnosci.size(); i++) 
			{
				listaAktywnosciDoListView.add(Main.listaAktywnosci.get(i).getNazwaAktywnosci());
				tab[i] = Main.listaAktywnosci.get(i).getIdAktywnosci(); // przekazuje
																		// idaktywnosci
																		// do
																		// tabeliu
			}
			aktywnosciListView.setItems(listaAktywnosciDoListView);
			tableViewClicked = true;

		} catch (Exception e) 
		{
			System.err.print(e);
		}
	}

	public void aktywnosciListViewClicked() throws SQLException, ClassNotFoundException 
	{
		// wczytywanie pracownikow, opisu oraz godzin do listview na podstawie
		// id Wybrajen aktywnosci
		// obiekty z listyAktywnosci pobieramy przy pomocy
		// selectedAktywnoscListView
		// obiekty z listaPracownikowWAktywnosci pobieramy przy pomocy
		// selectedAktywnosc
		pracownicyClicked = false;
		if (tableViewClicked == true)
		{
			try 
			{
				clear();
				BazaDanych.dbConnection();
				int selectedAktywnoscListView = aktywnosciListView.getSelectionModel().getSelectedIndex();
				selectedAktywnosc = tabSelectedAktywnosc[selectedAktywnoscListView];// przetwarzam zaznaczony index na idAktywnosci do bazy
				Main.listaPracownikowWAktywnosci.clear();
				listaPracownikowWAktywnosciDoListView.clear();
				BazaDanych.wczytajPracownikowWAktywnosci(selectedAktywnosc);
				opisText.setText(Main.listaAktywnosci.get(selectedAktywnoscListView).getOpis());
				for (int i = 0; i < Main.listaPracownikowWAktywnosci.size(); i++) 
				{
					listaPracownikowWAktywnosciDoListView.add(Main.listaPracownikowWAktywnosci.get(i).getPracownik());
				}
				// ustawianie wartosci poczatkowej w combobox
				aktywnosciClicked = true;
				godzinaOdCombo.setValue(Main.listaAktywnosci.get(selectedAktywnoscListView).getGodzinaOd().substring(0, 2));
				godzinaDoCombo.setValue(Main.listaAktywnosci.get(selectedAktywnoscListView).getGodzinaDo().substring(0, 2));
				minutaOdCombo.setValue(Main.listaAktywnosci.get(selectedAktywnoscListView).getGodzinaOd().substring(3, 5));
				minutaDoCombo.setValue(Main.listaAktywnosci.get(selectedAktywnoscListView).getGodzinaDo().substring(3, 5));
				liczGodzinyPracy();
				pracownicyListView.setItems(listaPracownikowWAktywnosciDoListView);

			}
			catch (Exception e) 
			{
				System.err.println(e);
			}
		}
	}

	public void pracownicyListViewClicked() throws ClassNotFoundException, SQLException 
	{
		pracownicyClicked = true;
		if (aktywnosciClicked == true && tableViewClicked == true) 
		{
			try 
			{
				BazaDanych.dbConnection();
				int selectedPracownikListView = pracownicyListView.getSelectionModel().getSelectedIndex();
				selectedPracownik = Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getIdPrac(); // wczytanie idPrac na podstawie id z Listview
				stawkaText.setText(Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getStawkaPracownika().toString());
				stawkaFirmyText.setText(Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getStawkaFirmy().toString());
				walutaCombo.setValue(Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getWaluta());
				walutaLabel.setText(Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getWaluta());
				walutaLabel2.setText(Main.listaPracownikowWAktywnosci.get(selectedPracownikListView).getWaluta());
				liczWynagrodzeniePracownika();
				liczZarobekFirmy();

			} 
			catch (Exception e) 
			{
				System.err.println(e);
			}
		}
	}

	public void liczWynagrodzeniePracownika()
	{
		sumaGodzin = Float.parseFloat(sumaGodzinLabel.getText());
		float stawka = Float.parseFloat(stawkaText.getText());
		wynagrodzeniePracownika = sumaGodzin * stawka;
		// zaokr¹glanie do 2 miejsca po przecinku
		wynagrodzeniePracownika *= 100;
		wynagrodzeniePracownika = Math.round(wynagrodzeniePracownika);
		wynagrodzeniePracownika /= 100;
		kosztPracownikaLabel.setText(Float.toString(wynagrodzeniePracownika));
	}

	public void liczGodzinyPracy() 
	{
		// godzina roczpoczecia pracy
		float godzinaOd = Float.parseFloat(godzinaOdCombo.getValue());
		float minutaOd = Float.parseFloat(minutaOdCombo.getValue()) / 60;
		// godzina zakoñczenia pracy
		float godzinaDo = Float.parseFloat(godzinaDoCombo.getValue());
		float minutaDo = Float.parseFloat(minutaDoCombo.getValue()) / 60;

		float hRoz = godzinaOd + minutaOd;
		float hZak = godzinaDo + minutaDo;

		sumaGodzin = hZak - hRoz;
		sumaGodzinLabel.setText(Float.toString(sumaGodzin));

	}

	public void liczZarobekFirmy()
	{
		float przychod = (Float.parseFloat(stawkaFirmyText.getText())) * sumaGodzin;
		float zarobek = przychod - wynagrodzeniePracownika;
		// zaokr¹glanie do 2 miejsca po przecinku
		zarobek *= 100;
		zarobek = Math.round(zarobek);
		zarobek /= 100;
		zarobekLabel.setText(Float.toString(zarobek));
	}

	public void bDodajDzienPracyClicked() throws ClassNotFoundException, SQLException
	{
		
		Parent root;
		try 
		{
			rok = rokCombo.getValue();
			miesiac = miesiacCombo.getSelectionModel().getSelectedIndex()+1;
			BazaDanych.dbConnection();
			root = FXMLLoader.load(getClass().getResource("scenaDodajDzienPracy.fxml"));
			okno.setTitle("Program ZA-TRANS");
			okno.setScene(new Scene(root, 720, 405));
			okno.setResizable(false);
			okno.show();
		} 
		catch (IOException e) 
		{
			System.err.println(e);
		}

	}

	public void bPowrotClicked()
	{
		Parent root;
		Scene scene;
		try 
		{
			Main.clear();
			clearAll();
			root = FXMLLoader.load(getClass().getResource("scenaPoZalogowaniu.fxml"));
			scene = new Scene(root);
			Main.window.setScene(scene);

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void bZapiszZmianyClicked() throws ClassNotFoundException, SQLException 
	{
		// selectedRok=-1, selectedMiesiac=-1, selectedDzien=-1,
		// selectedAktywnosc=-1, selectedPracownik=-1;
		if (selectedDzien == -1) 
		{
			JOptionPane.showMessageDialog(null, "Wybierz dzieñ pracy");
		} 
		else if (selectedAktywnosc == -1) 
		{
			JOptionPane.showMessageDialog(null, "Wybierz aktywnoœæ");
		}
		else 
		{
			if (selectedPracownik == -1) 
			{
				aktualizujAktywnosci();
				clearAll();
				Main.clear();
				daneDoTableView(selectedMiesiac, selectedRok);
				selectedDzien = -1;
				selectedAktywnosc = -1;
				JOptionPane.showMessageDialog(null, "Zaktualizowano dane aktywnoœci");
			} 
			else
			{
				aktualizujAktywnosci();
				try 
				{
					aktualizujPracownikaWAktywnosci();
					clearAll();
					Main.clear();
					daneDoTableView(selectedMiesiac, selectedRok);
					selectedDzien = -1;
					selectedAktywnosc = -1;
					selectedPracownik = -1;
					JOptionPane.showMessageDialog(null, "Zaktualizowano dane aktywnoœci i pracownika");
				} 
				catch (NumberFormatException e) 
				{
					JOptionPane.showMessageDialog(null, "Podaj poprawny format danych np. 12.5");
				}
			}
		}
	}

	public void aktualizujPracownikaWAktywnosci() throws ClassNotFoundException, SQLException
	{
		// dane do zaktualizowania z bazie
		float stawkaPracownika = Float.parseFloat(stawkaText.getText());
		float stawkaFirmy = Float.parseFloat(stawkaFirmyText.getText());
		System.out.println("stawkaText.getText(): " + stawkaText.getText());
		System.out.println("stawkaFirmyText.getText(): " + stawkaFirmyText.getText());
		String waluta = walutaCombo.getValue();
		BazaDanych.dbConnection();
		BazaDanych.aktualizujPracownikaWAktywnosci(selectedAktywnosc, selectedPracownik, stawkaPracownika, stawkaFirmy,waluta);
	}

	public void aktualizujAktywnosci() throws ClassNotFoundException, SQLException
	{
		String opis = opisText.getText();
		String godzinaOd = godzinaOdCombo.getValue();
		String minutaOd = minutaOdCombo.getValue();
		String godzinaDo = godzinaDoCombo.getValue();
		String minutaDo = minutaDoCombo.getValue();
		String hOd = godzinaOd + ":" + minutaOd;
		String hDo = godzinaDo + ":" + minutaDo;
		BazaDanych.dbConnection();
		BazaDanych.aktualizujAktywnosci(selectedAktywnosc, opis, hOd, hDo);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
