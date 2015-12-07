package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScenaPracownicy implements Initializable 
{
	@FXML TextField textImie, textNazwisko, textNumerTelefonu, textAdres,textMiasto,textKodPocztowy,textDataUrodzenia, textPesel;
	@FXML Button bZapiszZmiany, bPowrot, bSzukaj, bOdswiez; 
	@FXML TableColumn<Pracownik, String> columnImie;
	@FXML TableColumn<Pracownik, String> columnNazwisko;
	@FXML TableView<Pracownik> tabela;
	@FXML MenuBar menuBar;
	@FXML Menu menuPlik, menuPomoc;
	@FXML MenuItem menuItemZamknij, menuItemDodajPracownika, menuItemOProgramie;
	static int selectedId = -1;
	
	public ScenaPracownicy(){}
	@Override
	public void initialize(URL url, ResourceBundle rb) 
	{
		try 
		{
			BazaDanych.dbConnection();
			Main.listaPracownikow.removeAll(Main.listaPracownikow);
			BazaDanych.wczytajPracownikow();
			tabela.setItems(Main.listaPracownikow);
			columnImie.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("imie"));
			columnNazwisko.setCellValueFactory(new PropertyValueFactory<Pracownik,String>("nazwisko"));
			tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void tableViewClicked()
	{
		TableViewSelectionModel<Pracownik> selectionModel = tabela.getSelectionModel();
		ObservableList<Pracownik> selectedCells = selectionModel.getSelectedItems();
		
		textImie.setText(selectedCells.get(0).getImie());
		textNazwisko.setText(selectedCells.get(0).getNazwisko());
		textNumerTelefonu.setText(selectedCells.get(0).getNumerTelefonu());
		textAdres.setText(selectedCells.get(0).getAdres());
		textMiasto.setText(selectedCells.get(0).getMiasto());
		textKodPocztowy.setText(selectedCells.get(0).getKodPocztowy());
		textDataUrodzenia.setText(selectedCells.get(0).getDataUrodzenia());
		textPesel.setText(selectedCells.get(0).getPesel());
		
		selectedId = selectedCells.get(0).getId();
	}
	public void bZapiszZmianyClicked() throws SQLException
	{
		if (selectedId==-1)
		{
			JOptionPane.showMessageDialog(null, "Wybierz pracownika");
		}
		else
		{
			String imie,nazwisko, numerTelefonu, adres, miasto, kodPocztowy, dataUrodzenia, pesel;
			imie = textImie.getText().toString();
			nazwisko = textNazwisko.getText().toString();
			numerTelefonu = textNumerTelefonu.getText().toString();
			adres  = textAdres.getText().toString(); 
			miasto = textMiasto.getText().toString();
			kodPocztowy = textKodPocztowy.getText().toString(); 
			dataUrodzenia = textDataUrodzenia.getText().toString();
			pesel = textPesel.getText().toString();
			
			BazaDanych.aktualizujPracownikow(selectedId,imie,nazwisko,numerTelefonu,adres,miasto,kodPocztowy,dataUrodzenia,pesel);
			Main.listaPracownikow.removeAll(Main.listaPracownikow);
			BazaDanych.wczytajPracownikow();
			tabela.setItems(Main.listaPracownikow);
			selectedId=-1;
		}
	}
	public void bSzukajZmianyClicked()
	{
		
	}
	public void bOdswiezClicked() throws ClassNotFoundException, SQLException
	{
		BazaDanych.dbConnection();
		Main.clear();
		BazaDanych.wczytajPracownikow();
		tabela.setItems(Main.listaPracownikow);
	}
	public void bPowrotClicked()
	{
		Parent root;
		Scene scene;
		try 
		{
			root = FXMLLoader.load(getClass().getResource("scenaPoZalogowaniu.fxml"));
			scene = new Scene(root);
			Main.window.setScene(scene);
			
		} 
		catch (IOException e) {e.printStackTrace();}
	}
}
