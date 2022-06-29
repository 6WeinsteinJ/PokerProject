package src;

import java.util.*;
import java.util.List;



public class Main {


	static List<Player> players = new ArrayList<>();


	public static void main(String args[]) {
		int playerCount = 0;
		Scanner scanner = new Scanner(System.in);

		System.out.println("How many players?");
		do {
			System.out.println("Max 9 players!");
			playerCount = scanner.nextInt();
		} while (playerCount < 2 || playerCount > 9);
		scanner.nextLine();
		for (int i = 1; i < playerCount + 1; i++) {

			players.add(new Player(1000, "Player" + i, i));
		}


		GamePanel gui = new GamePanel();

		Game game = new Game(gui);



	}
}


















