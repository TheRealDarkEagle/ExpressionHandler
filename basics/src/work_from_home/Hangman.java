package work_from_home;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Hangman {

	/**
	 * @ToDo	visuellen hangman machen 
	 * 			
	 * 
	 */
	
	static int life =8;
	int toWin;
	int isRight;
	private static Scanner character;
	char[] usedChars = new char[25];
	int u = 0;
	
	/**
	 * Startpunkt der Klasse -> aufrufen aller anderen Methoden 
	 */
	public void startGame() {
		String wort = getWordFromList();
		wort = toSmall(wort);
		toWin = wort.length();
		char[] theWord = new char[wort.length()];
		for(int i=0;i<theWord.length;i++) {
			theWord[i]='.';
		}
		for(int s=0;s<usedChars.length;s++) {
			usedChars[s]='.';
		}
		gameLogic(wort,theWord);
		
	}

	/**
	 * Eingabe des zu erratenden Wortes -> momentan Obsolet!
	 * @return
	 */
//	private String insertWord() {
//		System.out.println("Geben Sie bitte Ihr Wort ein!: ");
//		Scanner sc = new Scanner(System.in);
//		String wort = sc.next();
//		sc.close();
//		wort = toSmall(wort);
//		return wort;
//	}
	
	/**
	 * Eingabe des Buchstaben 
	 * @return
	 */
	private static char letter() {
		character = new Scanner(System.in);
		char c = character.next().charAt(0);
		return c;
	}
	
	/**
	 * Kleinschreibung des zu suchenden Wortes 
	 * @param wort
	 * @return
	 */
	
	private static String toSmall(String wort) {
		wort = wort.toLowerCase();
		return wort;
	}
	
	/**
	 * bef�llung der leeren Array Felder mit . -> wenn in array[i]=='.' darstellung durch _ 
	 * @param theWord
	 */
	private static void printOnScreen(char[] theWord) {
		for(int i=0;i<theWord.length;i++) {
			if(theWord[i]!='.') {
				System.out.print(theWord[i]+" ");
			}else {
				System.out.print("_ ");
				
			}
		}
	}

	/**
	 * Pr�ft bisher eingegebene Buchstaben ob zusammengesetzt gleich dem Suchwort.
	 * @param wort
	 * @param theWord
	 * @return
	 */
	private String isWin(String wort,char[] theWord) {
		String guessedWord ="";
		for(int i=0;i<theWord.length;i++) {
			guessedWord = guessedWord+theWord[i];		
		}
		if(wort.equals(guessedWord)) {
			printOnScreen(theWord);
			System.out.println();
			System.out.println("Gewonnen!");
			return "Hurra!! Gewonnen!";
		}else {
			return gameLogic(wort,theWord);
			
		}
	}
	
	/**
	 * Aufruf aller Spielmethoden solange leben!=0
	 * @param wort
	 * @param theWord
	 * @return
	 */
	private String gameLogic(String wort,char[] theWord) {
		while(life != 0) {
			hangingManVisual(life);
			System.out.println("Sie haben noch "+life+" Versuche");
			printOnScreen(theWord);
			System.out.println();
			System.out.println("Bitte geben sie Ihren Buchstaben ein:");
			Character toTest = letter();
			String a = toTest.toString();
			//pr�fe ob buchstabe vorhanden
			if(wort.contains(a)) {
				for(int i=0;i<wort.length();i++) {
					if(wort.charAt(i)==toTest) {
						theWord[i]=toTest;
						isRight+=1;
					}
				}
				alreadyUsedChars(toTest);
				return isWin(wort,theWord);
			}else {
				life -=1;
				alreadyUsedChars(toTest);
				return gameLogic(wort,theWord);
			}
		}
		hangingManVisual(life);
		System.out.println("Leider Verloren :(");
		return wort;
	}
	
	/**
	 * Array welches bisher eingegebene Buchstaben beeinhaltet - Pr�ft ob Buchstabe schon eingegeben wurde
	 * @param used
	 */
	private void alreadyUsedChars(char used) {
		for(int i=0;i<usedChars.length;i++) {
			if(usedChars[i]==used) {
				System.out.println();
				System.out.println("Fehler! Buchstabe schon benutzt! Bitte versuchen Sie einen anderen Buchstaben");
				System.out.println();
				return;
			}
		}
		usedChars[u] = used;
		System.out.println();
		System.out.println("Ihre benutzten Buchstaben:");
		for(int o=0;o<usedChars.length;o++) {
			if(usedChars[o]!='.') {
				System.out.print(usedChars[o]+" ");	
			}
		}
		System.out.println();
		System.out.println();
		u++;
	}
	
	/**
	 * Liste mit m�glichen W�rtern und heraussuchen eines per Zufall
	 * @return
	 */
	//Erstellen einer Liste mit W�rter
private String getWordFromList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Stromaggregat");
		list.add("Computergehaeuse");
		list.add("Feuerwerk");
		list.add("Feuerwehr");
		list.add("Puzzleteil");
		list.add("Pizzateig");
		list.add("Gleichberechtigungsbeauftragter");
		list.add("Haengewandschrankhalterung");
		list.add("lokomotive");
		list.add("photovoltaikanlage");
		int i = random(list.size());
		return list.get(i);
	}
	
	/**
	 * Erzeugen einer zuf�lligen Zahl zwischen 0 bis 10
	 * @param maxNumber
	 * @return
	 */
	private int random(int maxNumber) {
		Random wuerfel = new Random();
		int zahl = 0+wuerfel.nextInt(maxNumber);
		return zahl;
	}

	/**
	 * Erzeugen des Visuellen "Hangman" Fortschritt anhand von restlichen Leben
	 * @param life
	 */
	private void hangingManVisual(int life) {
		switch(life) {
		case 8:
			System.out.println(" ________");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 7:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 6:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 5:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |      |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 4:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |     /|");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 3:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |     /|");
			System.out.println(" |      |");
			System.out.println(" |");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 2:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |     /|");
			System.out.println(" |      |");
			System.out.println(" |     /");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 1:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      0");
			System.out.println(" |     /|");
			System.out.println(" |      |");
			System.out.println(" |     / \\");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		case 0:
			System.out.println(" ________");
			System.out.println(" |      |");
			System.out.println(" |      X");
			System.out.println(" |     /|\\");
			System.out.println(" |      |");
			System.out.println(" |     / \\");
			System.out.println("_|____");
			System.out.println("|     |___");
			System.out.println("|_________|");
			break;
		}
	}

}
	