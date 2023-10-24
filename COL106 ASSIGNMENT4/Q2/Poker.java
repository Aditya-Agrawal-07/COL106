import heap_package.Node;
import heap_package.Heap;
import java.util.ArrayList;

public class Poker{

	private int city_size;            // City Population
	public int[] money;		         // Denotes the money of each citizen. Citizen ids are 0,1,...,city_size-1. 

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Allowed to use only PriorityQueue data structure globally but can use ArrayList inside methods. 
	   3. Can create at max 4 priority queues.
	*/

	public void initMoney(){
		// Do not change this function.
		for(int i = 0;i<city_size;i++){
			money[i] = 100000;							// Initially all citizens have $100000. 
		}
	}

	public Poker(int city_size, int[] players, int[] max_loss, int[] max_profit){

		/* 
		   1. city_size is population of the city.
		   1. players denotes id of the citizens who have come to the Poker arena to play Poker.
		   2. max_loss[i] denotes the maximum loss player "players[i]"" can bear.
		   3. max_profit[i] denotes the maximum profit player "players[i]"" will want to get.
		   4. Initialize the heap data structure(if required). 
		   n = players.length 
		   Expected Time Complexity : O(n).
		*/

		this.city_size = city_size;
		this.money = new int[this.city_size];
		this.initMoney();

		// To be filled in by the student
	}

	public ArrayList<Integer> Play(int[] players, int[] bids, int winnerIdx){

		/* 
		   1. players.length == bids.length
		   2. bids[i] denotes the bid made by player "players[i]" in this game.
		   3. Update the money of the players who has played in this game in array "money".
		   4. Returns players who will leave the poker arena after this game. (In case no
		      player leaves, return an empty ArrayList).
                   5. winnerIdx is index of player who has won the game. So, player "players[winnnerIdx]" has won the game.
		   m = players.length
		   Expected Time Complexity : O(mlog(n))
		*/
 
		int winner = players[winnerIdx];					// Winner of the game.

		ArrayList<Integer> playersToBeRemoved = new ArrayList<Integer>();     // Players who will be removed after this game. 

		// To be filled in by the student

		return playersToBeRemoved;
	}

	public void Enter(int player, int max_loss, int max_profit){

		/*
			1. Player with id "player" enter the poker arena.
			2. max_loss is maximum loss the player can bear.
			3. max_profit is maximum profit player want to get. 
			Expected Time Complexity : O(logn)
		*/

		// To be filled in by the student
	}

	public ArrayList<Integer> nextPlayersToGetOut(){

		/* 
		   Returns the id of citizens who are likely to get out of poker arena in the next game. 
		   Expected Time Complexity : O(1). 
		*/

		ArrayList<Integer> players = new ArrayList<Integer>();    // Players who are likely to get out in next game.

		// To be filled in by the student

		return players;
	}

	public ArrayList<Integer> playersInArena(){

		/* 
		   Returns id of citizens who are currently in the poker arena. 
		   Expected Time Complexity : O(n).
		*/

		ArrayList<Integer> currentPlayers = new ArrayList<Integer>();    // citizens in the arena.

		// To be filled in by the student

		return currentPlayers;
	}

	public ArrayList<Integer> maximumProfitablePlayers(){

		/* 
		   Returns id of citizens who has got most profit. 
			
		   Expected Time Complexity : O(1).
		*/

		ArrayList<Integer> citizens = new ArrayList<Integer>();    // citizens with maximum profit.

		// To be filled in by the student

		return citizens;
	}

	public ArrayList<Integer> maximumLossPlayers(){

		/* 
		   Returns id of citizens who has suffered maximum loss. 
			
		   Expected Time Complexity : O(1).
		*/

		ArrayList<Integer> citizens = new ArrayList<Integer>();     // citizens with maximum loss.

		// To be filled in by the student

		return citizens;
	}

}