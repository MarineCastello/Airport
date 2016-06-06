package presentation;

import dbAccess.MyDBException;
import dbAccess.PlaneDAO;

public class Exec {

	public static void main(String[] args) {
		
		PlaneDAO planeDAO = null;
		try {
			planeDAO = PlaneDAO.getInstance();
		} catch (MyDBException e1) {
			System.out.println("Essaie encore !");
		}
		try {
			System.out.println(planeDAO.selectAvailablePlanes("Lyon Saint-Exupéry"));
		} catch (MyDBException e) {
			System.out.println("Echec !");
		}
	}

}
