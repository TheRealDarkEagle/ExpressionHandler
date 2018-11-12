package work_from_home;

import java.util.Scanner;

public class HangmanMain {
	
	public static void main (String[] args) {
		
		Hangman man = new Hangman();
		ResolveHangman computer = new ResolveHangman();
		
		System.out.println("#################################################################");
		System.out.println();
		System.out.println();
		System.out.println("       			 Willkommen zu Hangman! Viel Spa� beim Spielen :)");
		System.out.println();
		System.out.println("Wer soll Spielen? Dr�cken Sie M fuer Mensch und C f�r Computer!");
		Scanner auswahl = new Scanner(System.in);
		String auswahlDesSpielers = auswahl.nextLine();
		auswahl.close();
		if(auswahlDesSpielers.length() != 1) {
			throw new IllegalArgumentException("Bitte geben Sie nur einen Buchstaben ein!");
		}
		if(auswahlDesSpielers.equals("M") || auswahlDesSpielers.equals("m")) {
			man.startGame();	
		}else if(auswahlDesSpielers.equals("C") || auswahlDesSpielers.equals("c")) {
			computer.startGame();
		}
	}
}
