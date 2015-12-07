package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ScenaPoZalogowaniu 
{	
		public void dniPracyClicked() throws Exception
		{
			Parent root;
			Scene scene;
			try 
			{
				root = FXMLLoader.load(getClass().getResource("scenaDniPracy.fxml"));
				scene = new Scene(root,960,540);
				Main.window.setScene(scene);
				Main.window.centerOnScreen();
				Main.window.show();
				
			} 
			catch (IOException e) {e.printStackTrace();}
		}
		
		public void pracownicyClicked() throws Exception
		{
			Parent root;
			Scene scene;
			try 
			{
				root = FXMLLoader.load(getClass().getResource("scenaPracownicy.fxml"));
				scene = new Scene(root,960,540);
				Main.window.setScene(scene);
			} 
			catch (IOException e) {System.err.println(e);}
		}
		
		public void rozliczeniaClicked() throws Exception
		{
			Parent root;
			Scene scene;
			try 
			{
				root = FXMLLoader.load(getClass().getResource("scenaRozliczenia.fxml"));
				scene = new Scene(root);
				Main.window.setScene(scene);
			} 
			catch (IOException e) {e.printStackTrace();}
		}
}
