package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProszeWpisacNazweException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProszeWpisacNazweException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Proszę wpisać nazwę");
		alert.showAndWait();
	}
}
