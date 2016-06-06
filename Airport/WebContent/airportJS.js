function getPlaneInFlight() {
	document.location.href("ControlerServlet");
}

function selectChange(){
	var champs = document.getElementsByClassName("champsForm");
	for (var i = 0; i < champs.length; i++) {
		champs[i].style.display = "none";
	}
	
	var opt = document.getElementById("dynamicList").value;
	switch (opt) {
	case "1":
		var departAirport = document.getElementById("departAirport");
		departAirport.style.display = "flex";
		break;
	case "2":
		var departAirport = document.getElementById("departAirport");
		departAirport.style.display = "flex";
		break;
	case "3":
		var planeID = document.getElementById("planeID");
		planeID.style.display = "flex";
		break;

	default:
		break;
	}
}