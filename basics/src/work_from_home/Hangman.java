package work_from_home;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Hangman {

	/**
	 * 
	 * @TODO: eingabe f�r den benutzter erstellen - diese danach auf komische buchstaben pr�fen (����) und wenn vorhanden durch "normale" ersetzten 
	 */
	boolean isFirstCharUpperCase = false;
	static int life =8;
	private static Scanner character;
	char[] usedChars = new char[25];
	int u = 0;
	private ResolveHangman computer;
	ArrayList<String> list = new ArrayList<String>();
	private boolean isHumanPlaying = false;
	private static boolean wordBeginsUpperCase;
	
	public Hangman(boolean isHumanPlaying) {
		this.isHumanPlaying = isHumanPlaying;
	}
	
	
	/**
	 * Startpunkt der Klasse -> aufrufen aller anderen Methoden 
	 * @throws IOException 
	 */
	public void startGame() throws IOException {
		if(!isHumanPlaying) {
			this.computer = new ResolveHangman();
		}
		String wort = insertWordManuel();
		isFirstCharUpperCase(wort);
		wort = toSmall(wort);
		char[] theWord = new char[wort.length()];
		for(int i=0;i<theWord.length;i++) {
			theWord[i]='.';
		}
		for(int s=0;s<usedChars.length;s++) {
			usedChars[s]='.';
		}
		gameLogic(wort,theWord);
		
	}

	private void isFirstCharUpperCase(String wort) {
		String posibilUpperChars ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Character firstChar = wort.charAt(0);
		for(int i =0;i<posibilUpperChars.length();i++) {
			if(firstChar.equals(posibilUpperChars.charAt(i))) {
				wordBeginsUpperCase = true;
			}
		}
		
	}


	/**
	 * Eingabe des zu erratenden Wortes -> momentan Obsolet!
	 * @return
	 */
	@SuppressWarnings("unused")
	private String insertWord() {
		System.out.println("Geben Sie bitte Ihr Wort ein!: ");
		Scanner sc = new Scanner(System.in);
		String wort = sc.next();
		sc.close();
		wort = toSmall(wort);
		return wort;
	}
	
	/**
	 * Eingabe des Buchstaben 
	 * @return
	 */
	public static char letter() {
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
				if(i==0) {
					if(wordBeginsUpperCase) {
						Character firstCharShouldBeUpperCase = theWord[0];
						firstCharShouldBeUpperCase = Character.toUpperCase(theWord[0]);
						theWord[0] = firstCharShouldBeUpperCase;
					}
				}
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
	 * @throws IOException 
	 */
	private String isWin(String wort,char[] theWord) throws IOException {
		String guessedWord ="";
		for(int i=0;i<theWord.length;i++) {
			guessedWord = guessedWord+theWord[i];		
		}
		if(wort.toLowerCase().equals(guessedWord.toLowerCase())) {
			printOnScreen(theWord);
			System.out.println();
			System.out.println("Gewonnen!");
			visualWin();
			return "";
			
		}else {
			return gameLogic(wort,theWord);
			
		}
	}
	
	/**
	 * Aufruf aller Spielmethoden solange leben!=0
	 * @param wort
	 * @param theWord
	 * @return
	 * @throws IOException 
	 */
	private String gameLogic(String wort,char[] theWord) throws IOException {
		while(life != 0) {
			hangingManVisual(life);
			System.out.println("Sie haben noch "+life+" Versuche");
			printOnScreen(theWord);
			System.out.println();
			System.out.println("Bitte geben sie Ihren Buchstaben ein:");
			Character toTest = ' ';
			if(isHumanPlaying) {
				toTest = letter();
			}else {
				// wenn Buchstabe = an erster stelle -> "a".toUpperCase();
				// 
				
				
				toTest = computer.getChar(theWord);
			}
			
			String a = toTest.toString();
			//pr�fe ob buchstabe vorhanden
			if(wort.contains(a)) {
				for(int i=0;i<wort.length();i++) {
					if(wort.charAt(i)==toTest) {
						theWord[i]=toTest;
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
		if(life==0) {
			writeIntoWordGlossar(wort,wordBeginsUpperCase);
		}
		hangingManVisual(life);
		System.out.println("Leider Verloren :(");
		return wort;
	}
	
	private void writeIntoWordGlossar(String wort, boolean wordBeginsUpperCase) throws IOException{
		if(wordBeginsUpperCase) {
			Character firstCharFromWord = wort.charAt(0);
			Character FirstCharIsUpperCase = Character.toUpperCase(firstCharFromWord);
			wort = wort.replaceFirst(firstCharFromWord.toString(), FirstCharIsUpperCase.toString());
			
		}
		FileWriter fw = new FileWriter ("D:\\Danz Kai Adrian empiriecom\\Hangman\\wortsammlung.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write(wort);
		bw.close();
		
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

	/**
	 * Visuell Hangman v.Gewonnen
	 */
	private void visualWin() {
		System.out.println(" ________");
		System.out.println(" |      |");
		System.out.println(" |        ");
		System.out.println(" |        ");
		System.out.println(" |      0 ");
		System.out.println(" |     \\|/  ");
		System.out.println("_|____  |  ");
		System.out.println("|     |/_\\_");
		System.out.println("|_________|");
		}

	private String insertWordManuel() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out.println("Bitte geben Sie ihr Wort ein: ");
		
		String eingabe = br.readLine();
		return eingabe;
	}
	
}
	