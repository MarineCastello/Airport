package dbAccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Airline;
import entity.Airport;
import entity.Factory;
import entity.FlightDuration;
import entity.FlightPlan;
import entity.Plane;

public class FlightPlanDAO {
	private DBAccess dbaccess = null;
	private static FlightPlanDAO instance = null;

	/**
	 * Retourne une instance de FlightPlanDAO
	 * 
	 * @return instance de la classe
	 * @throws MyDBException
	 */
	public static FlightPlanDAO getInstance() throws MyDBException {
		if (instance == null) {
			instance = new FlightPlanDAO();
		}
		return instance;
	}

	/**
	 * Constructeur par d�faut Initialisation de la connexion � la base de
	 * donn�es
	 * 
	 * @throws MyDBException
	 */
	private FlightPlanDAO() throws MyDBException {
		super();
		dbaccess = DBAccess.getInstance();
	}

	/**
	 * Connexion � la base de donn�es
	 * 
	 * @throws MyDBException
	 */
	public void getConnection() throws MyDBException {
		try {
			dbaccess.connect();
		} catch (SQLException e) {
			throw new MyDBException("Connexion � la base de donnees impossible");
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
	 * Permet d'ins�rer un nouvel enregistrement dans la table FlightPlan
	 * 
	 * @param m
	 *            FlightPlan � ins�rer
	 * @throws MyDBException
	 */
	public void insertFlightPlan(FlightPlan fp) throws MyDBException {
		// ajout de l'avion avec le bon id_FlightPlan et le bon id_FlightPlan
		PreparedStatement preparedStmt = null;

		getConnection();
		
		// r�cup�ration de l'id du plane 
		Integer idPlane = PlaneDAO.getInstance().selectIdPlane(fp.getPlane().getPlaneName());		
		
		// r�cup�ration de l'id de la flight duration
		Integer idFlightDuration = FlightDurationDAO.getInstance().selectIdFlightDuration(fp.getDuration().getAirportDeparture(), fp.getDuration().getAirportArrival());
		
		// insert du flight plan dans la BDD 
		String requete = "INSERT INTO flight_plan (fk_id_plane, fk_id_flight_duration, departure_time) VALUES (?,?,?);";
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idPlane);
			preparedStmt.setInt(2, idFlightDuration);
			preparedStmt.setDate(3, fp.getDepartureTime());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			throw new MyDBException("Probleme lors de la creation ou de l'ex�cution de la requete d'insertion");
		}
		try {
			preparedStmt.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors du close du statement de l'insertion");
		}
		close();
	}

	/**
	 * Permet d'afficher toute la table FlightPlan
	 * 
	 * @throws MyDBException
	 */
	public List<FlightPlan> selectAll() throws MyDBException {
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportDeparture = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportArrival = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportDeparture, airportArrival, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return lstFlightPlans;
	}
	
	public FlightPlan selectFlightPlanOfPlane(String planeName, String airportDeparture, String airportArrival, Date date) throws MyDBException {
		FlightPlan fp = null;
		
		// r�cup�ration de l'id de l'a�roport de d�part
		Integer idAirportDeparture = PlaneDAO.getInstance().selectIdPlane(airportDeparture);
		
		// r�cup�ration de l'id de l'a�roport d'arriv�e
		Integer idAirportArrival = PlaneDAO.getInstance().selectIdPlane(airportArrival);
		
		ResultSet resultats = null;
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport"
					   + "WHERE Ai1.id_airport = ? AND Ai2.id_airport = ? AND P.plane_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setInt(1, idAirportDeparture);
			preparedStmt.setInt(2, idAirportArrival);
			preparedStmt.setString(3, planeName);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			if (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportD = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportA = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportD, airportA, resultats.getInt("duration"));
				fp = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();

		return fp;
	}
	
	public List<FlightPlan> selectFlightFromAirport(String airport) throws MyDBException {
		List<FlightPlan> lstFlightPlan = new ArrayList<FlightPlan>();
		
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport"
					   + "WHERE Ai1.airport_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, airport);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportDeparture = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportArrival = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportDeparture, airportArrival, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		return lstFlightPlan;
	}
	
	public List<FlightPlan> selectFlightToAirport(String airport) throws MyDBException {
		List<FlightPlan> lstFlightPlan = new ArrayList<FlightPlan>();
		
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport"
					   + "WHERE Ai2.airport_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, airport);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportDeparture = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportArrival = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportDeparture, airportArrival, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		return lstFlightPlan;
	}
	
	public List<FlightPlan> selectFlightBetweenAirports(String airportDeparture, String airportArrival) throws MyDBException {
		List<FlightPlan> lstFlightPlan = new ArrayList<FlightPlan>();
		
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport"
					   + "WHERE Ai1.airport_name = ? AND Ai2.airport_name = ?;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setString(1, airportDeparture);
			preparedStmt.setString(2, airportArrival);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportD = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportA = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportD, airportA, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		return lstFlightPlan;
	}
	
	public List<FlightPlan> selectFlightIntoTimeInterval(Date date1, Date date2) throws MyDBException {
		List<FlightPlan> lstFlightPlan = new ArrayList<FlightPlan>();
		
		ResultSet resultats = null;
		List<FlightPlan> lstFlightPlans = new ArrayList<FlightPlan>();
		String requete = "SELECT P.plane_name, A.airline_name, Ai1.airport_name AS airport_departure, Ai2.airport_name AS airport_arrival, FD.duration, FP.departure_time" 
					   + "FROM rechercheultime.flight_plan FP"
					   + "INNER JOIN rechercheultime.plane P ON FP.fk_id_plane = P.id_plane"
					   + "INNER JOIN rechercheultime.flight_duration FD ON FP.fk_id_flight_duration  = FD.id_flight_duration"    
					   + "INNER JOIN rechercheultime.airline A ON P.fk_id_airline = A.id_airline"
					   + "INNER JOIN rechercheultime.airport Ai1 ON FD.fk_id_airport_departure = Ai1.id_airport"
					   + "INNER JOIN rechercheultime.airport Ai2 ON FD.fk_id_airport_arrival = Ai2.id_airport"
					   + "WHERE FP.departure_time BETWEEN date1 AND date2;";
		PreparedStatement preparedStmt = null;

		// connexion � la base de donn�es
		getConnection();
		// envoi de la requete
		try {
			preparedStmt = dbaccess.getConnection().prepareStatement(requete);
			preparedStmt.setDate(1, date1);
			preparedStmt.setDate(2, date2);
			resultats = preparedStmt.executeQuery();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la cr�ation ou de l'ex�cution da la requete de selection");
		}

		// traitement des r�sultats
		try {
			while (resultats.next()) {
				Airline airline = Factory.createAirline(resultats.getString("airline_name"));
				Plane p = Factory.createPlane(resultats.getString("plane_name"));
				p.setAirline(airline);
				Airport airportD = Factory.createAirport(resultats.getString("airport_departure"));
				Airport airportA = Factory.createAirport(resultats.getString("airport_arrival"));
				FlightDuration fd = Factory.createFlightDuration(airportD, airportA, resultats.getInt("duration"));
				FlightPlan a = Factory.createFlightPlan(p, fd, resultats.getDate("departure_time"));
				lstFlightPlans.add(a);
			}
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la r�cup�ration des donn�es du select");
		}

		// fermeture du ResultSet ainsi que de la connexion
		try {
			preparedStmt.close();
		} catch (SQLException e1) {
			throw new MyDBException("Erreur lors de la fermeture du statement de selection");
		}
		try {
			resultats.close();
		} catch (SQLException e) {
			throw new MyDBException("Erreur lors de la fermeture du ResultSet");
		}
		close();
		
		return lstFlightPlan;
	}
}
