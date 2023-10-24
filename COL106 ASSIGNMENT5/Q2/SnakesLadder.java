import java.io.*;
import java.util.*;

public class SnakesLadder extends AbstractSnakeLadders {
	
	int N, M;
	int snakes[];
	int ladders[];
	boolean[] visited;
	int[] dist;
	int[] rev_dist;
	ArrayList<ArrayList<Integer>> rev;
	Integer[] lvlup;
	Integer[] lvldown;
	int[] bestsaanp;

	
	public SnakesLadder(String name)throws Exception{
		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());
		visited = new boolean[N+1];
	    snakes = new int[N];
		ladders = new int[N];
		dist = new int[N + 1];
		rev_dist = new int[N + 1];
		rev = new ArrayList<ArrayList<Integer>>(N);
		bestsaanp = new int[2];
		visited[0] = true;
		dist[0] = 0;
		rev_dist[N] = 0;
	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
			dist[i + 1] = -1;
			rev_dist[i + 1] = -1;
			visited[i + 1] = false;
			ArrayList<Integer> arr = new ArrayList<Integer>();
			rev.add(null);
			rev.set(i, arr);
		}
		
		for(int i=0;i<M;i++){
			String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());
			//System.out.println(source + "," + destination);
			if(source<destination){
				ladders[source] = destination;
			}
			else{
				snakes[source] = destination;
			}
			rev.get(destination).add(source);
        }
		// System.out.println(N + "," + M);
		// System.out.println(" ");

		
		LinkedList<Integer[]> queue = new LinkedList<Integer[]>();
		Integer[] prev = {0,0};
		queue.add(prev);
		while (queue.isEmpty() == false){
			prev = queue.remove();
			for (int i = prev[0] + 1; i <= prev[0] + 6 && i <= N; i++){
				if (i == N && visited[i] == false){
					Integer[] l = new Integer[2];
					l[1] = prev[1] + 1;
					l[0] = N;
					if (dist[l[0]] == -1) {dist[l[0]] = l[1];}
					visited[i] = true;
					queue.add(l);
				}
				else if (visited[i] == false){
					Integer[] l = new Integer[2];
					l[1] = prev[1] + 1;
					visited[i] = true;
					if (ladders[i] == -1 && snakes[i] == -1){
						l[0] = i;
						if (dist[l[0]] == -1) {dist[l[0]] = l[1];}

					}
					else{
						int pos = i;
						while (ladders[pos] != -1 || snakes[pos] != -1){
							if (ladders[pos] != -1){
								pos = ladders[pos];
							}
							else if (snakes[pos] != -1){
								pos = snakes[pos];
							}
						}
						l[0] = pos;
						if (dist[l[0]] == -1) {dist[l[0]] = l[1];}
					}
					queue.add(l);
				}
			}
		}


		for (int j = 0; j <= N; j++){
			visited[j] = false;
		}
		prev[0] = N;
		prev[1] = 0;
		queue.add(prev);
		while (queue.isEmpty() == false){
			prev = queue.remove();
			for (int i = prev[0] - 1; i >= prev[0] - 6 && i >= 1; i--){
				if (visited[i] == false){
					Integer[] l = new Integer[2];
					l[1] = prev[1] + 1;
					if (rev.get(i).isEmpty() == true && snakes[i] == -1 && ladders[i] == -1){
						visited[i] = true;
						l[0] = i;
						if (rev_dist[l[0]] == -1) {rev_dist[l[0]] = l[1];}
						queue.add(l);
					}
					else if (snakes[i] != -1 || ladders[i] != -1){}
					else{
						DFS(i, queue, l[1]);
					}
				}
			}
		}

		lvlup = new Integer[dist[N]];
		lvldown = new Integer[dist[N]];
		lvlup[0] = 0;
		lvldown[0] = N;
		int countup = 0;
		for (int i = N - 1; i >= 1; i--){
			if (dist[i] == -1 || dist[i] > dist[N] - 1){continue;}
			if (lvlup[dist[i]] == null){
				lvlup[dist[i]] = i;
				countup++;
			}
			if (countup == dist[N] - 1){break;}
		}
		int countdown = 0;
		for (int i = 1; i < N; i++){
			if (rev_dist[i] == -1 || rev_dist[i] > dist[N] - 1){continue;}
			if (lvldown[rev_dist[i]] == null){
				lvldown[rev_dist[i]] = i;
				countdown++;
			}
			if (countdown == dist[N] - 1){break;}
		}

		int sum = lvldown.length + lvlup.length - 2;
		int i;
		int second = lvldown.length - 1;
		for (i = 1; i < lvlup.length; i++){
			for (int j = second; j > 0; j--){
				second = j;
				if (sum >= i + j  && lvlup[i] > lvldown[j]){
					bestsaanp[0] = i;
					bestsaanp[1] = j;
					sum = i + j;
				}
				else{break;}
			}
			if (i + second > sum){break;}
			else if (lvlup[i] > lvldown[second]){
				bestsaanp[0] = i;
				bestsaanp[1] = second;
				sum = i + second;
			}

		}

	}


	private void DFS(int cell, LinkedList<Integer[]> queue, Integer a){
		Integer[] r = {cell, a};
		visited[cell] = true;
		queue.add(r);
		if (rev_dist[r[0]] == -1) {rev_dist[r[0]] = a;}
		for (int j = 0; j < rev.get(cell).size(); j++){
			DFS(rev.get(cell).get(j), queue, a);
		}
	}

    
	public int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		return dist[this.N];
	}

	public int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			if (dist[x] == -1 || rev_dist[y] == -1){return -1;}
			if (dist[x] + rev_dist[y] < dist[N]){return 1;}
			return -1;
	}

	public int[] FindBestNewSnake()
	{
		int result[] = {-1, -1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */
		if (bestsaanp == null){return result;}
		if (bestsaanp[0] + bestsaanp[1] < dist[N]){
			result[0] = lvlup[bestsaanp[0]];
			result[1] = lvldown[bestsaanp[1]];
		}
		return result;
	}
}