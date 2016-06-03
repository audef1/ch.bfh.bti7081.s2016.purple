package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;

public class GoogleMapsComponent extends GoogleMap{

	private static String apikey = "AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714";
	private LatLon coordinates;
	private String address;
	
	public GoogleMapsComponent(String address){
		super(apikey, null, "german");
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
		this.addMarker(this.address , this.coordinates, false, null);
		this.setSizeFull();
		this.setMinZoom(4);
		this.setMaxZoom(16);
		this.setCenter(this.coordinates);
		this.setZoom(16);
	}
}
