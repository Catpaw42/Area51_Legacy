package test;

import spil.Player;
import spil.SortPlayers;

public class SortPlayersTest
{
	//quick method to test the sorting algorithm used to find the winning player.
	public static void main(String[] args)
	{
		//create data for our algorithm to sort.
		Player[] p = new Player[10];
		for (int i = 0; i < p.length; i++)
		{
			try
			{
				p[i] = new Player("name" + i);
				//setting it up backwards (worst case).
				p[i].getAccount().setScore(100 - 10*i);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		//overrides one element in the test-array with a duplicate data entry, to show that this is also handled
		try
		{
			p[2] = new Player("name" + 9);
			p[2].getAccount().setScore(50);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		//display unsorted data.
		for (int i = 0; i < p.length; i++)
		{
			System.out.println(p[i].getAccount().getScore());
		}
		//sort the data.
		System.out.println("sorting:");
		p = SortPlayers.mergeSort(p);
		//display sorted data.
		for (int i = 0; i < p.length; i++)
		{
			System.out.println(p[i].getAccount().getScore());
		}
	}

}
