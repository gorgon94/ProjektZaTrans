package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PodanyDzienJestJuzWBazieException extends Exception
{
	private static final long serialVersionUID = 1L;

	public PodanyDzienJestJuzWBazieException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Podany dzień jest już w bazie danych. Wybierz inny.");
		alert.showAndWait();
	}
}
