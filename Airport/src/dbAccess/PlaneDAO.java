package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Airline;
import entity.Airport;
import entity.Factory;
import entity.Plane;

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

		// récupération de l'id de l'airline
		Integer idAirline = AirlineDAO.getInstance().selectIdAirline(p.getAirline().getAirlineName());
		
		// Récupération de l'id de l'aéroport
		Integer idAirport = AirportDAO.getInstance().selectIdAirport(p.getCurrentAirport().getAirportName());
		
		// ajout de l'avion avec le bon id_airline et le bon id_airport
		String requete = "INSERT INTO plane (plane_name,id_airline, id_airport) VALUES ('" + p.getPlaneName() + "', "
				+ idAirline + ", " + idAirport + ")";
		Statement stmt = null;

		getConnection();
		try {
			stmt = dbaccess.getConnection().createStatement();
			nbMaj = stmt.executeUpdate(requete);
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
		String requete = "SELECT * " + "FROM plane P " + "INNER JOIN airport A ON P.fk_id_airport = A.id_airport "
				+ "INNER JOIN airline Ai ON P.fk_id_airline = Ai.id_airline;";
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
		String requete = "SELECT * "
				+ "FROM plane P INNER JOIN airline A ON P.fk_id_airline = A.id_airline INNER JOIN airport Ai ON P.fk_id_airport = Ai.id_airport "
				+ "WHERE plane_name = " + name + ";";
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
		String requete = "SELECT plane_name, available, airline_name, airport_name, city, country, time_zone "
				+ "FROM rechercheultime.plane P "
				+ "INNER JOIN rechercheultime.airport A ON P.fk_id_airport = A.id_airport "
				+ "INNER JOIN rechercheultime.airline Ai ON P.fk_id_airline = Ai.id_airline "
				+ "WHERE airport_name like ? AND available = 1;";
		PreparedStatement preparedStmt = null;
		Airline airline = null;
		Airport airport = null;

		// connexion à la base de données
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, nameAirport);
			resultat = preparedStmt.executeQuery();
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
				airport = Factory.createAirport(resultat.getString("airport_name"), resultat.getString("city"),
						resultat.getString("country"), resultat.getInt("time_zone"));
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
			preparedStmt.close();
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

	public void updatePlaneAirline(Plane p, Airline a) throws MyDBException {
		ResultSet resultat = null;

		// creation et execution de la requete
		String requete = "SELECT id_plane FROM plane WHERE plane_name=?";
		PreparedStatement preparedStmt;

		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, p.getPlaneName());
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la préparation du statement");
		}

		try {
			resultat = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'exécution de la requête");
		}

		// récupération de l'id de l'eleve qu'il faut modifier
		Integer id = 0;
		try {
			if (resultat.next()) {
				id = resultat.getInt("id_plane");
			}
		} catch (SQLException e2) {
			throw new MyDBException("Erreur lors de la récupération de l'id de l'eleve");
		}

		// récupération de l'id de la airline à modifier
		Integer idAirline = AirlineDAO.getInstance().selectIdAirline(a.getAirlineName());

		// parcours des donnees retournees, modification et ajout dans la base
		// de données
		requete = "UPDATE plane SET fk_id_airline = ? WHERE id_plane = ?";
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idAirline);
			preparedStmt.setInt(2, id);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la préparation du statement de modification");
		}

		try {
			preparedStmt.executeUpdate();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'exécution de la requête de modification");
		}

		// Fermeture des resultSet, preparedStatement et Connection
		try

		{
			resultat.close();
			preparedStmt.close();
			close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'interruption de la connection à la DB");
		}
	}
	
	public void updateCurrentAirport(Plane p, Airport a) throws MyDBException {
		ResultSet resultat = null;

		// creation et execution de la requete
		String requete = "SELECT id_plane FROM plane WHERE plane_name=?";
		PreparedStatement preparedStmt;

		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, p.getPlaneName());
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la préparation du statement");
		}

		try {
			resultat = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'exécution de la requête");
		}

		// récupération de l'id de l'eleve qu'il faut modifier
		Integer id = 0;
		try {
			if (resultat.next()) {
				id = resultat.getInt("id_plane");
			}
		} catch (SQLException e2) {
			throw new MyDBException("Erreur lors de la récupération de l'id de l'eleve");
		}

		// récupération de l'id de la airline à modifier
		Integer idAirport = AirportDAO.getInstance().selectIdAirport(a.getAirportName());

		// parcours des donnees retournees, modification et ajout dans la base
		// de données
		requete = "UPDATE plane SET fk_id_airline = ? WHERE id_plane = ?";
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idAirport);
			preparedStmt.setInt(2, id);
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la préparation du statement de modification");
		}

		try {
			preparedStmt.executeUpdate();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'exécution de la requête de modification");
		}

		// Fermeture des resultSet, preparedStatement et Connection
		try

		{
			resultat.close();
			preparedStmt.close();
			close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de l'interruption de la connection à la DB");
		}
	}
}
