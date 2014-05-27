import connector.*;
import connector.IWeightConnector.ConnectionException;

/**
 * 
 */

/**
 * @author Martin
 *
 */
public class WeightController implements IWeightController
{
	private WeightConnector weightConnector;

	public WeightController(String ip, int port)
	{
		this.weightConnector = new WeightConnector(ip, port);
		//Tries to establish connection via WeightConnector
		try
		{
			weightConnector.connect();
		} catch (ConnectionException e)
		{
			e.printStackTrace();
			System.exit(0);// Exit program if no connection made.
		}
	}
	/* (non-Javadoc)
	 * @see IWeightController#readWeight()
	 */
	@Override
	//Methods to relay GUI requests to Connector.
	public double readWeight()
	{
		return weightConnector.read();
	}

	/* (non-Javadoc)
	 * @see IWeightController#tareWeight()
	 */
	@Override
	public double tareWeight()
	{
		return weightConnector.tare();
	}

	/* (non-Javadoc)
	 * @see IWeightController#zeroWeight()
	 */
	@Override
	public void zeroWeight()
	{
		weightConnector.zero();
	}

	/* (non-Javadoc)
	 * @see IWeightController#displayText(java.lang.String)
	 */
	@Override
	public void displayText(String text)
	{
		weightConnector.displayText(text);
	}

	/* (non-Javadoc)
	 * @see IWeightController#displayWeight()
	 */
	@Override
	public void displayWeight() {
		weightConnector.displayWeight();
	}

	/* (non-Javadoc)
	 * @see IWeightController#setActiveUser(int)
	 */
	@Override
	//Future methods....
	public void setActiveUser(int ID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see IWeightController#getActiveUser(int)
	 */
	@Override
	public void getActiveUser(int ID) {
		// TODO Auto-generated method stub

	}
	@Override
	public void close() {
		weightConnector.disconnect();
		
	}

}
