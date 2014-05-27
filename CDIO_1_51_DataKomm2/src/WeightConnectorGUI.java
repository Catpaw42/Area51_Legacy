import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WeightConnectorGUI extends JFrame implements ActionListener
{
	private JPanel panel;
	private JButton[] buttons;
	private JTextArea currentWeight;
	private JTextArea tareWeight;
	private JLabel weightLabel;
	private JLabel tareLabel;
	private JTextArea textArea;
	private JScrollPane textScrollPane;
	private IWeightController controller;

	public WeightConnectorGUI(final IWeightController controller)
	{
		this.controller = controller;
		//Setup of items on GUI
		panel = new JPanel();
		buttons = new JButton[8];
		currentWeight = new JTextArea();
		currentWeight.setFont(new Font(Font.SERIF, Font.PLAIN, 28));
		currentWeight.setEditable(false);
		weightLabel = new JLabel("Weight:");
		weightLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 28));
		weightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tareWeight = new JTextArea();
		tareWeight.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
		tareWeight.setEditable(false);
		tareLabel = new JLabel("Tare:");
		tareLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
		tareLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		textArea = new JTextArea();
		textArea.setEditable(false);
		textScrollPane = new JScrollPane(textArea);
		//Ensures cleanup after termination
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				controller.close();
				((JFrame) e.getSource()).dispose();
			}
		});
		//Defining properties and layout of JFrame
		this.getContentPane().add(panel);
		this.setPreferredSize(new Dimension(615,510));
		this.setResizable(false);
		this.pack();
		
		panel.setLayout(null);
		panel.add(textScrollPane);
		panel.add(currentWeight);
		panel.add(weightLabel);
		panel.add(tareWeight);
		panel.add(tareLabel);
		//Setting up buttons
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton();
			panel.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		buttons[0].setText("Read Weight");
		buttons[0].setBounds(5, 400, 150, 40);
		buttons[1].setText("Tare");
		buttons[1].setBounds(155, 400, 150, 40);
		buttons[2].setText("Zero");
		buttons[2].setBounds(305, 400, 150, 40);
		buttons[3].setText("Display Text");
		buttons[3].setBounds(455, 400, 150, 40);
		buttons[4].setText("Display Weight");
		buttons[4].setBounds(5, 440, 150, 40);
		buttons[5].setText("Set Active User");
		buttons[5].setBounds(155, 440, 150, 40);
		buttons[5].setEnabled(false);
		buttons[5].setToolTipText("Not yet implemented");
		buttons[6].setText("get Active User");
		buttons[6].setBounds(305, 440, 150, 40);
		buttons[6].setEnabled(false);
		buttons[6].setToolTipText("Not yet implemented");
		buttons[7].setText("Quit");
		buttons[7].setBounds(455, 440, 150, 40);
		//Setting up other items on gui
		textScrollPane.setBounds(5, 195, 600, 200);
		currentWeight.setBounds(150, 140, 200, 40);
		weightLabel.setBounds(30, 140, 115, 40);
		tareWeight.setBounds(425, 150, 120, 30);
		tareLabel.setBounds(355, 150, 65, 30);
		//Behold the GUI!
		this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - this.getWidth()/2),(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - this.getHeight()/2));
		this.setVisible(true);
	}
	//Listening for clicks on buttons and invoking corresponding method on controller
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == buttons[0])
		{
			double weight = controller.readWeight();
			this.textArea.append("Weight is: " + weight + "\n");
			this.currentWeight.setText("" + weight + " kg.");
		}
		else if (e.getSource() == buttons[1]) 
		{
			double tare = controller.tareWeight();
			this.textArea.append("Tare weight, stored weight: " + tare + "\n");
			this.currentWeight.setText("0.0"  + " kg.");
			this.tareWeight.setText(tare + " kg.");
		}	
		else if (e.getSource() == buttons[2])
		{
			controller.zeroWeight();
			
			this.textArea.append("Zeroing weight" + "\n");
			this.currentWeight.setText("0.0" + " kg.");
			this.tareWeight.setText("0.0" + " kg.");
		}
		else if (e.getSource() == buttons[3]) 
		{
			String text = JOptionPane.showInputDialog("Enter text to display");
			controller.displayText(text);
			this.textArea.append("Displaying: " + text + "\n");
		}
		else if (e.getSource() == buttons[4]) 
		{
			controller.displayWeight();
			this.textArea.append("Displaying weight" + "\n");
		}
		else if (e.getSource() == buttons[5]) 
		{
			try
			{
				int i = Integer.parseInt(JOptionPane.showInputDialog("Select new active user ID(int)"));
				controller.setActiveUser(i);
				this.textArea.append("Set active user to: " + i + "\n");
			} catch (NumberFormatException e2)
			{
				this.textArea.append("ERROR: Please input an integer number");
			}
		}
		else if (e.getSource() == buttons[6]) 
		{
			try
			{
				int i = Integer.parseInt(JOptionPane.showInputDialog("Input active user ID(int)"));
				controller.getActiveUser(i);
				this.textArea.append("Active user is: " + i + "\n");
			} catch (NumberFormatException e2)
			{
				this.textArea.append("ERROR: Please input an integer number");
			}
		}
		else if (e.getSource() == buttons[7]) 
		{
			controller.close();
			this.setVisible(false);
			this.dispose();
		}
	}
	//Test stub
	public static void main(String[] args)
	{
		new WeightConnectorGUI(new WeightController("localhost", 4567));
	}
}
