package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.AppointmentEntity;

public class AppointmentEventProvider implements CalendarEventProvider{
	
	private List<AppointmentEntity> appointments;
	private List<CalendarEvent> events = new ArrayList<CalendarEvent>();
	
	public AppointmentEventProvider(List<AppointmentEntity> items) {
		this.appointments = items;
		events = new ArrayList<CalendarEvent>();
		
		for (AppointmentEntity a : appointments){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date(a.getStartTime()));
			Date start = cal.getTime();
			
			//get enddate from duration
			cal.add(GregorianCalendar.MINUTE, a.getDuration());
			Date end = cal.getTime();
			
			String name = "Test";
			String description = a.getAddress() + "\n" + a.getPlace();
			
			BasicEvent event = new BasicEvent();
			event.setCaption(name);
			event.setDescription(description);
			event.setStart(start);
			event.setEnd(end);
			
			events.add(event);
		}
	}
	
	public void addEvent(CalendarEvent BasicEvent) {
		events.add(BasicEvent);
	}
	
	public List<CalendarEvent> getEvents() {
		return events;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		return events;
	}

}