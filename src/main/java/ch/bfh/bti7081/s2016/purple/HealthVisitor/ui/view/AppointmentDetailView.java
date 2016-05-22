package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

public class AppointmentDetailView extends CustomComponent implements View {
	public static final String NAME = "AppointmentDetail";
	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	private final VerticalLayout general;

	public AppointmentDetailView() {
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);

		general = new VerticalLayout();
		general.setMargin(new MarginInfo(false, false, true, true));
		general.setSpacing(true);

		ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
		Image logo = new Image("Logo", resource);
		logo.setWidth("300px");
		logo.setCaption("");
		logo.addClickListener(clickevent ->
			getUI().getNavigator().navigateTo(DashboardView.NAME));
		

		Label lblHeader = new Label("Termin Detail");
		lblHeader.setStyleName("header");
		
		Label lblTitle = new Label("Aktueller Termin: ");
		lblTitle.setStyleName("h1");
		
		Button btnArrival = new Button("Ankunft bestätigen");
		btnArrival.addClickListener(clickevent -> 
			btnArrivalClicked(btnArrival));
		
		GridLayout data = new GridLayout(3, 5);
		
//		Google-Maps-Implementation: Not working
		
//		Label tfAddress = new Label();
		
//		JavaScript.getCurrent().addFunction("com.google.maps", new JavaScriptFunction() {
//
//			Label tfAddress;
//			
//			@Override
//			public void call(JsonArray arguments) throws JsonException {
//				GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714");
//				try {
//					GeocodingResult[] results = GeocodingApi.geocode(context, "Wankdorffeldstrasse 102, 3014 Bern").await();
//					logger.debug(results[0].formattedAddress);
//					tfAddress = new Label(results[0].formattedAddress);
//					data.addComponent(tfAddress);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		});
		
		LatLon pos = new LatLon(46.9648208, 7.453848);
		
		GoogleMap map = new GoogleMap("AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714", null, "german");
		map.setSizeFull();
		map.addMarker("Wankdorffeldstrasse 102, 3014 Bern", pos, false, null);
		map.setMinZoom(4);
		map.setMaxZoom(16);
		map.setCenter(pos);
		
		
//		BrowserFrame maps =new BrowserFrame("google.ch", new ExternalResource("https://www.google.ch/maps/place/Wankdorffeldstrasse+102,+3014+Bern/@46.9648208,7.453848,17z/data=!3m1!4b1!4m5!3m4!1s0x478e39f0fa9a50af:0x188b61a7f226307d!8m2!3d46.9648172!4d7.456042")); 
//		maps.setSizeFull();
		general.addComponent(map);
		
		
		
		
		
		
		

		// The Layout for the Logo
		GridLayout top = new GridLayout(2, 2);
		top.setSizeFull();
		top.addComponent(lblHeader, 0, 0);
		top.addComponent(logo, 1, 0);
		top.addComponent(lblTitle, 0, 1);
		top.addComponent(btnArrival, 1, 1);
		top.setComponentAlignment(logo, Alignment.TOP_RIGHT);
		top.setComponentAlignment(btnArrival, Alignment.TOP_LEFT);
		top.setMargin(new MarginInfo(true, true, false, true));

		// Set the root layout
		general.addComponent(top);
		general.addComponent(data);
		setCompositionRoot(general);

	}
	
	private void btnArrivalClicked(Button btn){
		btn.setCaption("Ende bestätigen");
		// TODO Persistence Current DateTime to the DB.
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO Auto-generated method stub

	}

}
