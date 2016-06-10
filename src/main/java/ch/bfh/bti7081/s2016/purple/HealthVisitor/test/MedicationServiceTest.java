package ch.bfh.bti7081.s2016.purple.HealthVisitor.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.AppointmentDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.businesslogic.GenericDao;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.AppointmentEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.HealthVisitorEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity.MedicationEntity;
import ch.bfh.bti7081.s2016.purple.HealthVisitor.service.MedicationService;
import junit.framework.TestCase;

public class MedicationServiceTest extends TestCase {
	
	class AppointmentDaoMock extends AppointmentDao {
		private Collection<AppointmentEntity> appointments;
		public AppointmentDaoMock(Collection<AppointmentEntity> appointments){
			this.appointments = appointments;
		}
		
		// @override
		protected void init(){
			// Do nothing
		}

		// @override
		public Collection<AppointmentEntity> getTodaysAppointmentsByHealthVisitor(HealthVisitorEntity healthVisitor){
			return this.appointments;
		}
	}
	
	class MedicationEntityMock extends MedicationEntity {
		 public MedicationEntityMock(String name, double amount){
	    	setName(name);
	    	setAmount(amount);
	    }
	}
	
	class AppointmentEntityMock extends AppointmentEntity {
		public AppointmentEntityMock(MedicationEntityMock... medications){
			setMedications(Arrays.asList(medications));
		}
	} 

	public void testGetMedicationForDayReturnsMedicationsForDay() throws Exception {
		Collection<AppointmentEntity> mockAppointments = new ArrayList<AppointmentEntity>();
		mockAppointments.add(new AppointmentEntityMock(
			new MedicationEntityMock("Medication A", 1),
			new MedicationEntityMock("Medication B", 1),
			new MedicationEntityMock("Medication B", 1),
			new MedicationEntityMock("Medication C", 2)
		));
		mockAppointments.add(new AppointmentEntityMock(
			new MedicationEntityMock("Medication C", 1),
			new MedicationEntityMock("Medication D", 4)
		));		

		
		HealthVisitorEntity healthVisitorMock = new HealthVisitorEntity();
		
		MedicationService medicationService = new MedicationService(new AppointmentDaoMock(mockAppointments));
		Collection<MedicationEntity> medications = medicationService.getMedicationForDay(healthVisitorMock);
		
		this.assertEquals(4, medications.size());
		
		for (MedicationEntity medication: medications){
			switch (medication.getName()) {
			case "Medication A":
				this.assertEquals(1.0, medication.getAmount());
				break;
			case "Medication B":
				this.assertEquals(2.0, medication.getAmount());
				break;
			case "Medication C":
				this.assertEquals(3.0, medication.getAmount());
				break;
			case "Medication D":
				this.assertEquals(4.0, medication.getAmount());
				break;
			default:
				throw new Exception("Unknow medication returned by service");
			}
		}
	}

	public void testGetMedicationForDayReturnsNothing(){
		MedicationService medicationService = new MedicationService(new AppointmentDaoMock(null));
		Collection<MedicationEntity> medications = medicationService.getMedicationForDay(null);
		this.assertEquals(0, medications.size());
	} 
}
