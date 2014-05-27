import javax.swing.SwingUtilities;



public class WeightMain
{
	public static void main(String[] args)
	{
		final WeightController weight;
		//Checks if right number of arguments has been passed- else uses localhost and 4567
		if (args.length == 2)
		{
			System.out.println(args[0] + args[1]);
			weight = new WeightController(args[0], Integer.parseInt(args[1]));
		} else
		{
			weight = new WeightController("localhost", 4567);
		}
		//Launches swing GUI on EDT
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new WeightConnectorGUI(weight);
			}
		});
	}
}
