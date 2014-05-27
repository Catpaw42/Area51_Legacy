package tests;

import connector.IWeightConnector.ConnectionException;
import connector.WeightConnector;

public class TestMultipleConnections {
	public void testConnections(int numberOfConnections){
		WeightConnector[] weightConnector = new WeightConnector[numberOfConnections];
		for (int i = 0; i < weightConnector.length; i++) {
			weightConnector[i] = new WeightConnector("localhost", 4567);
			try {
				weightConnector[i].connect();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		TestMultipleConnections testMultipleConnections = new TestMultipleConnections();
		testMultipleConnections.testConnections(200);
	}
}

