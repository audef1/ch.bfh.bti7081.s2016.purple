package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;

public class AppointmentEventProvider implements CalendarEventProvider{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8261934122713682667L;
	private List<AppointmentEntity> appointments;
	private List<CalendarEvent> events = new ArrayList<>();
	
	public AppointmentEventProvider(List<AppointmentEntity> items) {
		this.appointments = items;
		events = new ArrayList<>();
		
		for (AppointmentEntity a : appointments){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(a.getStartTime());
			Date start = cal.getTime();
			cal.setTime(a.getEndTime());
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