package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dbAccess.DBAccess;
import dbAccess.MyDBException;

public class DBAccess {
	private Connection con = null;
	private static DBAccess instance = null;

	/**
	 * Retourne une instance de DBAccess : classe qui permet d'accéder à la base
	 * de données
	 * 
	 * @throws MyDBException
	 */
	public static DBAccess getInstance() throws MyDBException {
		if (instance == null) {
			instance = new DBAccess();
		}
		return instance;
	}

	/**
	 * Constructeur par défaut de la classe
	 * 
	 * @throws MyDBException
	 */
	private DBAccess() throws MyDBException {
		super();

		// chargement du pilote
		try {
			loadDriver();
		} catch (ClassNotFoundException e) {
			throw new MyDBException("Driver inexistant");
		}
	}

	/**
	 * Permet de charger le pilote mysql
	 * 
	 * @throws ClassNotFoundException
	 */
	public void loadDriver() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	/**
	 * Permet d'ouvrir une connexion
	 * 
	 * @return la connexion ouverte
	 * @throws SQLException
	 */
	public Connection connect() throws SQLException {
		// jdbc:mysql://nomhote:port/nombdd
		String DBurl = "jdbc:mysql://localhost:3306/rechercheultime";
		con = DriverManager.getConnection(DBurl, "root", "Silenus2015");

		return con;
	}

	/**
	 * Retourne la connexion
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Ferme la connexion
	 * 
	 * @throws MyDBException
	 * @throws SQLException
	 */
	public void close() throws MyDBException {
		try {
			con.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture de la connexion");
		}
	}
}
