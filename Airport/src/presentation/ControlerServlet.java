package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Plane;
import services.PlaneService;

/**
 * Servlet implementation class ControlerServlet
 */
@WebServlet("/ControlerServlet")
public class ControlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}
	
	public void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if (request.getParameter("DEPARTAIRPORT") != null && request.getParameter("CHECKBOX") == null) {
			getListPlaneByAirport(request, response);
		}
	}
	
	public void getListPlaneByAirport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ModelBeanPlaneInAirport beanPlaneInAirport = new ModelBeanPlaneInAirport("", new ArrayList<String>());
		
		// Traitement des entrees de la page d'accueil
		if (request.getParameter("DEPARTAIRPORT") != null) {
			String departAirport = "";
			
			try {
				departAirport = request.getParameter("DEPARTAIRPORT");
				
				// metier
				PlaneService planeService = PlaneService.getInstance();
				List<String> lstPlane = planeService.selectAvailablePlanes(departAirport);
				beanPlaneInAirport = new ModelBeanPlaneInAirport(departAirport, lstPlane);
			} catch (Exception e) {
				System.out.println("Erreur lors de la recuperation du bean 'PlaneInAirport'");
			}
		}
		
		// Passage a la vueListPlaneInAirport.jsp
		request.setAttribute("beanPlaneInAirport", beanPlaneInAirport);
		request.getRequestDispatcher("/vueListPlaneInAirport.jsp").forward(request, response);
	}
}
