@isTest 
public class DroneControllerTest {
	String result;
	 @isTest static void testDroneController() {
		DroneController dc = new DroneController();
		System.assertNotEquals('',dc.getResult());
	}
}