package connector;

public interface IWeightConnector {
	public class ConnectionException extends Exception {
		public ConnectionException(String message) {
			super(message);
		}

		private static final long serialVersionUID = 8454203088288292932L;
	}
	
	void connect() throws ConnectionException;
	void disconnect();
	double read();
	double tare();
	void zero();
	void displayText(String text);
	void displayWeight();
	String getUserInput();
	
	
}
