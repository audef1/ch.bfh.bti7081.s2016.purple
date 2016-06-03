package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class GoogleMapsComponent {

	public final String apikey = "AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714";
	private LatLon coordinates;
	private String address;
	
	public GoogleMapsComponent(String address){
		this.address = address;
		GeoApiContext context = new GeoApiContext().setApiKey(apikey);
		GeocodingResult[] results = null;
		double lat;
		double lng;
		try {
			results = GeocodingApi.geocode(context, address).await();
			lat = results[0].geometry.location.lat;
			lng = results[0].geometry.location.lng;
		} catch (Exception e) {
			e.printStackTrace();
			lat = 46.9648208;
			lng = 7.453848;
		}
		
		this.coordinates = new LatLon(lat, lng);
	}

	public LatLon getCoordinates() {
		return this.coordinates;
	}
	
	public GoogleMap getComponent(){
		GoogleMap map = new GoogleMap(apikey, null, "german");
		
		map.addMarker(this.address , this.coordinates, false, null);
		map.setSizeFull();
		map.setMinZoom(4);
		map.setMaxZoom(16);
		map.setCenter(this.coordinates);
		map.setZoom(16);
		
		return map;
	}

}
