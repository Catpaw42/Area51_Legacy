package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import connector.WeightConnector;

public class TestConnection {
	
	WeightConnector swc; 

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		swc.disconnect();
	}

	@Test
	public void testConnect() {
		swc = new WeightConnector("localhost", 4567); // insert ip and port. default constructor has been deleted
	   

	    //exception.expect(ConnectionException.class);
		//fail("Not yet implemented");
	}

//	@Test
//	public void testDisconnect() {
//		fail("Not yet implemented");
//	}

}
