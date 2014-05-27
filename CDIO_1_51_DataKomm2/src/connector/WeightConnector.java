
package connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightConnector implements IWeightConnector{
	String weightIP;
	int weightSocket;
	Socket socket;
	PrintWriter writer;
	BufferedReader reader;

	public WeightConnector(String weightIP, int weightSocket) {
		super();
		this.weightIP = weightIP;
		this.weightSocket = weightSocket;		
	}

	public void connect() throws ConnectionException {
		socket = new Socket();
		//Tries to connect to remote server via TCP
		try {
			socket.connect(new InetSocketAddress(weightIP,weightSocket));
			
			System.out.println("Connected");
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage()+", remote host not accepting connection");
		}
		//if connection established a Buffered reader and writer is created.
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			System.out.println("Reader and writer initialized");
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage() + "Reader or writer creation failed");
		}
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private String writeToSocket(String printString) {
		//Send message to weight
		writer.print(printString);
		writer.flush();
		System.out.println("reading");
		String returnString = null;
		//Wait for string in return for 100 ms
		try
		{
			socket.setSoTimeout(100);
			returnString = reader.readLine();
			System.out.println(returnString);
		}
		catch(SocketTimeoutException ste)
		{
			//TODO: handle socket timeout here
			ste.printStackTrace();
		}
		catch (IOException ioe) 
		{
			//TODO: handle generic I/O error here
			ioe.printStackTrace();
		}
		//Return what the weight said
		return returnString;
	}

	public double read()
	{
		String returnString = writeToSocket("S\r\n");
		return getDoubleFromString(returnString);
	}

	private double getDoubleFromString(String returnString)
	{
		//Parse string from weight to extract a double.
		Pattern pattern = Pattern.compile("[0-9]+.[0-9]+"); //betyder at den henter en streng der starter med 
															//nogle tal adskilt af et punktum, og derefter nogle flere tal.

		Matcher matcher = pattern.matcher(returnString); //udf�rer match

		List<String> listMatches = new ArrayList<String>();

		while(matcher.find()) //den løber igennem resultaterne og tilføjer dem til en arraylist. Der bør kun være 1 resultat.
		{
			listMatches.add(matcher.group(0));
		}

		//TODO add exceptions eller check om arraylist er tom
		return Double.parseDouble(listMatches.get(0));
	}

	@Override
	public double tare() {
		String returnString = writeToSocket("T\r\n");
		return getDoubleFromString(returnString);

	}

	@Override
	public void zero() {
		writeToSocket("Z\r\n");
	}

	@Override
	public void displayText(String text) {
		writeToSocket("D \""+text+"\"\r\n");

	}

	@Override
	public void displayWeight() {
		System.out.println("displayWeight() is called");
		writeToSocket("dw\r\n");
		System.out.println("displayWeight() should have been called now");

	}

	@Override
	public String getUserInput() {
		//Not working in simulator.
		String inputString = null;
		if (writeToSocket("RM20 4 \"Key in\" \"2\" \"234234\"")=="RM20 B"){
			try {
				inputString = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputString;
	}


}

