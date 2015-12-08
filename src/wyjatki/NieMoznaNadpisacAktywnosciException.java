package wyjatki;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NieMoznaNadpisacAktywnosciException extends Exception
{
	private static final long serialVersionUID = 1L;

	public NieMoznaNadpisacAktywnosciException()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uwaga!");
		alert.setHeaderText(null);
		alert.setContentText("Nie można nadpisać aktywności. Proszę usunąć zaznaczenie");
		alert.showAndWait();
	}
}
