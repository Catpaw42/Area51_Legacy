package spil;

import java.util.Arrays;

//this class is only used because we're trying to take into account the possibility of multiple winners (draw)
//its used to sort the players after their score, so that any similar scores become apparent.
//in most games this will be redundant.
public class SortPlayers
{
	//uses the merge-sort recursive algorithm.
	public static Player[] mergeSort(Player[] players)
	{
		//first we split the given array into elements of one.
		if (players.length > 1)
		{
			int middle = players.length / 2;
			//take the left part.
			Player[] left = Arrays.copyOfRange(players, 0, middle);
			//take the remaining right part.
			Player[] right = Arrays.copyOfRange(players,middle, players.length);
			//return the two elements, merged into one.
			return merge(mergeSort(left), mergeSort(right));
		}
		//if the array only contains one element, just return that element.
		return players;
	}
	//merges two arrays so into one sorted array.
	private static Player[] merge(Player[] left, Player[] right)
	{
		//holds the total elements we're using on this merge call.
		int totalElements = left.length + right.length;
		//array to hold the sorted values
		Player[] A = new Player[totalElements];
		//integers to iterate the corresponding arrays.
		int total_i = 0;
		int left_i = 0;
		int right_i = 0;
		//while we have'nt yet processes all numbers.
		while (total_i < totalElements)
		{
			//if we still have numbers left in both arrays.
			if (left_i < left.length && right_i < right.length)
			{
				//if the left number is the smallest add that to the A array.
				if (left[left_i].getAccount().getScore() < right[right_i].getAccount().getScore())
				{
					A[total_i] = left[left_i];
					left_i++;
					total_i++;
				} else //if its not, add the right side number.
				{
					A[total_i] = right[right_i];
					right_i++;
					total_i++;
				}
			} else // if one of the two sides are out of numbers, add the rest from the other side.
			{
				while (left_i < left.length)
				{
					A[total_i] = left[left_i];
					total_i++;
					left_i++;
				}
				while (right_i < right.length)
				{
					A[total_i] = right[right_i];
					total_i++;
					right_i++;
				}

			}
		}
		return A; // return the sorted array.
	}

}
