package dbAccess;

public class MyDBException extends Exception {
	
	/**
	 * Constructeur de MyDBException Classe qui permet d'afficher une exception
	 * avec un texte personnalis�
	 *
	 * @param message
	 *            texte pr�cis permettant de mieux comprendre les exceptions qui
	 *            surgissent
	 */
	public MyDBException(String message) {
		super(message);
	}
}
