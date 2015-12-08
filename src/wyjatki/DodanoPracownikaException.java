package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DodanoPracownikaException extends Exception 
{
	private static final long serialVersionUID = 1L;

	public DodanoPracownikaException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Zapisano dane pracownika");
		alert.showAndWait();
	}
}
