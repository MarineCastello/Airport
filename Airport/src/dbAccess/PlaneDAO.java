package dbAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbAccess.DBAccess;
import dbAccess.MyDBException;
import entity.Plane;
import entity.Airline;
import entity.Airport;
import entity.Factory;

public class PlaneDAO {
	private DBAccess dbaccess = null;
	private static PlaneDAO instance = null;

	/**
	 * Retourne une instance de PlaneDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static PlaneDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new PlaneDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par défaut Initialisation de la connexion à la base de
	 * données
	 * 
	 * @throws MyDBException
	 */
	private PlaneDAO() throws MyDBException {
		super();
		dbaccess = DBAccess.getInstance();
	}

	/**
	 * Connexion à la base de données
	 * 
	 * @throws MyDBException
	 */
	public void getConnection() throws MyDBException {
		try {
			dbaccess.connect();
		} catch (SQLException e) {
			throw new MyDBException("Connexion à la base de donnees impossible");
		}
	}

	/**
	 * Fermeture de la connexion
	 * 
	 * @throws MyDBException
	 */
	public void close() throws MyDBException {
		dbaccess.close();
	}

	/**
	 * Permet d'insérer un nouvel enregistrement dans la table Plane
	 * 
	 * @param m
	 *            Plane à insérer
	 * @return nombre d'enregistrements impactés
	 * @throws MyDBException
	 */
	public Integer insertPlane(Plane p) throws MyDBException {
		Integer nbMaj = 0;

		// récupération de l'idAirline
		Integer idAirline = 0;
		ResultSet resultat = null;
		String requete = "SELECT id_airline " + 
						"FROM airline " + 
						"WHERE airline_name = " + p.getAirline().getAirlineName() + ";";
		Statement stmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultat = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				idAirline = resultat.getInt("id_airline");
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		if (p == null) {
			throw new MyDBException("Aucune donnée récupérée depuis la base de données");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		// récupération de l'idAirport
		Integer idAirport = 0;
		resultat = null;
		requete = "SELECT id_airport " + 
				  "FROM airport " + 
				  "WHERE airport_name = " + p.getCurrentAirport().getAirportName() + ";";
		stmt = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultat = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				idAirport = resultat.getInt("id_airport");
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		if (p == null) {
			throw new MyDBException("Aucune donnée récupérée depuis la base de données");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		// ajout de l'avion avec le bon id_airline et le bon id_airport
		requete = "INSERT INTO plane (plane_name,id_airline, id_airport) VALUES ('" + p.getPlaneName() + "', " + idAirline + ", " + idAirport + ")";
		stmt = null;

		getConnection();
		try {
			stmt = dbaccess.getConnection().createStatement();
			// nbMaj = stmt.executeUpdate(requete);
		} catch (SQLException e) {
			throw new MyDBException("Probleme lors de la creation ou de l'exécution de la requete d'insertion");
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors du close du statement de l'insertion");
		}
		close();

		return nbMaj;
	}

	/**
	 * Permet d'afficher toute la table Plane
	 * 
	 * @throws MyDBException
	 */
	public List<Plane> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<Plane> lstPlanes = new ArrayList<Plane>();
		String requete = "SELECT * " + 
						 "FROM plane P " + 
							"INNER JOIN airport A ON P.fk_id_airport = A.id_airport " + 
							"INNER JOIN airline Ai ON P.fk_id_airline = Ai.id_airline;";
		Statement stmt = null;
		Airline airline = null;
		Airport airport = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultats = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			while (resultats.next()) {
				int av = resultats.getInt("available");
				boolean available;
				if (av == 1) {
					available = true;
				} else {
					available = false;
				}
				airline = Factory.createAirline(resultats.getString("airline_name"));
				airport = Factory.createAirport();
				airport.setAirportName(resultats.getString("airport_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"), airport, airline, available);
				lstPlanes.add(p);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return lstPlanes;
	}

	/**
	 * Permet de recupérer un Plane selon son id
	 * 
	 * @param id
	 *            identifiant du Plane à récupérer
	 * @return le Plane que l'on souhaite
	 * @throws MyDBException
	 */
	public Plane selectPlane(String name) throws MyDBException {
		Plane p = null;
		ResultSet resultat = null;
		String requete = "SELECT * " + 
						"FROM plane P INNER JOIN airline A ON P.fk_id_airline = A.id_airline INNER JOIN airport Ai ON P.fk_id_airport = Ai.id_airport " + 
						"WHERE plane_name = " + name + ";";
		Statement stmt = null;
		Airline airline = null;
		Airport airport = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultat = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				int av = resultat.getInt("available");
				boolean available;
				if (av == 1) {
					available = true;
				} else {
					available = false;
				}
				airline = Factory.createAirline(resultat.getString("airline_name"));
				airport = Factory.createAirport();
				airport.setAirportName(resultat.getString("airport_name"));
				p = Factory.createPlane(resultat.getString("plane_name"), airport, airline, available);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		if (p == null) {
			throw new MyDBException("Aucune donnée récupérée depuis la base de données");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return p;
	}

	/**
	 * Permet de recupérer un Plane selon l'aéroport demandé
	 * 
	 * @param id
	 *            identifiant du Plane à récupérer
	 * @return le Plane que l'on souhaite
	 * @throws MyDBException
	 */
	public List<Plane> selectAvailablePlanes(String nameAirport) throws MyDBException {
		List<Plane> lstPlanes = new ArrayList<Plane>();
		Plane p = null;
		ResultSet resultat = null;
		String requete = "SELECT plane_name, available, airline_name, airport_name, city, country, time_zone " +
						 "FROM rechercheultime.plane P " + 
						 "INNER JOIN rechercheultime.airport A ON P.fk_id_airport = A.id_airport " +
						 "INNER JOIN rechercheultime.airline Ai ON P.fk_id_airline = Ai.id_airline " +
						 "WHERE airport_name like " + nameAirport + " AND available = 1;";
		Statement stmt = null;
		Airline airline = null;
		Airport airport = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			stmt = dbaccess.getConnection().createStatement();
			resultat = stmt.executeQuery(requete);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la création ou de l'exécution da la requete de selection");
		}

		// traitement des résultats
		try {
			if (resultat.next()) {
				int av = resultat.getInt("available");
				boolean available;
				if (av == 1) {
					available = true;
				} else {
					available = false;
				}
				airline = Factory.createAirline(resultat.getString("airline_name"));
				airport = Factory.createAirport(resultat.getString("airport_name"), resultat.getString("city"), resultat.getString("country"), resultat.getInt("time_zone"));
				airport.setAirportName(resultat.getString("airport_name"));
				p = Factory.createPlane(resultat.getString("plane_name"), airport, airline, available);
				lstPlanes.add(p);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la récupération des données du select");
		}

		if (p == null) {
			throw new MyDBException("Aucune donnée récupérée depuis la base de données");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			stmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultat.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return lstPlanes;
	}
}
