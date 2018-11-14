package work_from_home;

import java.util.ArrayList;

import org.omg.PortableServer.ServantActivatorHelper;

public class ResolveHangman {
	
	private String potencialChars = "abcdefghijklmnopqrstuvwxyz";
	private int[] countChars= new int[potencialChars.length()];
	private char[] amountOfCharsInWords = potencialChars.toCharArray();
	boolean checkedForLength = false; 
	private ArrayList<Character> usedChars = new ArrayList<Character>();
	private ArrayList<String> wordList;
	
	public ResolveHangman() {
		this.wordList = this.loadWordsFromFile();
	}
	
	private ArrayList<String> loadWordsManual() {
		//Datei Einlesen Und W�rter laden:
		
		
		//Aktuell Liste mit W�rtern:
		ArrayList<String> words = new ArrayList<>();
		words.add("aalfang");
		words.add("Stromaggregat");
		words.add("Computergehaeuse");
		words.add("Feuerwerk");
		words.add("Feuerwehr");
		words.add("Puzzleteil");
		words.add("Pizzateig");
		words.add("Gleichberechtigungsbeauftragter");
		words.add("Haengewandschrankhalterung");
		words.add("lokomotive");
		words.add("photovoltaikanlage");
		words.add("Autowaschanlage");
		words.add("Element");
		words.add("Wagenheber");
		words.add("Haarwurzel");
		words.add("develop");
		words.add("Anwendungsentwickler");
		words.add("Fachmann");
		words.add("Feuerwehrmann");
		words.add("Jahr");
		words.add("Uhr");
		words.add("Prozent");
		words.add("Million");
		words.add("Mensch");
		words.add("gehen");
		words.add("verschieden");
		words.add("Leben");
		words.add("allerdings");
		words.add("verstehen");
		words.add("Mutter");
		words.add("ueberhaupt");
		words.add("besonders");
		words.add("politisch");
		words.add("Gesellschaft");
		words.add("moeglichkeit");
		words.add("Unternehmen");
		words.add("buch");
		words.add("haben");
		words.add("ich");
		words.add("werden");
		words.add("sie");
		words.add("dies");
		words.add("Grundstuecksverkehrsgenehmigungszustaendigkeitsuebertragungsverordnung");
		words.add("Rindfleischetikettierungsueberwachungsaufgabenuebertragungsgesetz");
		words.add("Verkehrsinfrastrukturfinanzierungsgesellschaft");
		words.add("Gleichgewichtsdichtegradientenzentrifugation");
		return words;
	}
	
	private ArrayList<String> loadWordsFromFile() {
		/*
		 * TODO: lade W�rter von Datei
		 */
		return loadWordsManual();
	}
	
	/**
	 * Findet den naechsten Buchstaben anhand aller Bekannten W�rter 
	 * @param theWord
	 * @return
	 */
	public char getChar(char[] theWord) {
		replaceVowelInList();
		StringBuilder mergeAllCharsToString = new StringBuilder();
		for(int x=0;x<theWord.length;x++) {
			mergeAllCharsToString = mergeAllCharsToString.append(theWord[x]);
		}
		
		//Wenn usedChars == empty -> remove alle Eintr�ge mit anderer L�nge aus wordList
		if(usedChars.isEmpty()) {
			sortFromLength(theWord.length,wordList);
			return getCharToUse(amountOfCharsInWords);
		}else{
			countAndSort();
			testTheChar(theWord);
		}
		System.out.println(wordList);
		return getCharToUse(amountOfCharsInWords);
	}
	
	/**
	 * Pr�fe ob zuletzt benutzter Buchstabe im Wort vorhanden war
	 * @param theWord
	 */
	private void testTheChar(char[] theWord) {
		Character lastUsed = usedChars.get(0);
		StringBuilder arrayIntoString = new StringBuilder();
		//zuerst muss ich pr�fen ob ich gerade in meinem wort den ersten buchstaben herausgefunden habe 
		if(theWord[0]==Character.toUpperCase(lastUsed)) {
			Character checkFirstChar = theWord[0];
			if(!checkFirstChar.toString().equals(potencialChars)) {
				lastUsed = checkFirstChar;
				charExist(lastUsed, theWord);
				}
		}else {
			for(int a=0; a<theWord.length;a++) {
				arrayIntoString = arrayIntoString.append(theWord[a]);
			}
			String hasTheWord = ""+arrayIntoString;
			hasTheWord.toLowerCase();
			if(hasTheWord.contains(lastUsed.toString())){
				charExist(lastUsed, theWord);
			}else {
				charDoesNotExist(lastUsed, theWord);
			}
		}
	}

	/**
	 * Entfernt alle W�rter, welche den zuletzt benutzten Buchstaben beinhalten
	 * @param charExist
	 * @param theWord
	 */
	private void charDoesNotExist(Character charExist,char[] theWord) {
		for(int a =0; a<wordList.size();a++) {
			if(wordList.get(a).contains(charExist+"")) {
				wordList.remove(a);
			}
		}
		
	}

	/**
	 * Setzt alle Felder das Array countChars auf 0
	 */
	private void resetCounterFromcountCharacter() {
		for (int i = 0; i < countChars.length; i++) {
			countChars[i] = 0;
		}
	}
	
	/**
	 * Sucht aus der Liste alle w�rter mit der L�nge  des gesuchten Wortes heraus und l�scht die restlichen 
	 * @param lengthFromWord
	 * @param list
	 */
	private void sortFromLength(int lengthFromWord, ArrayList<String> list) {
		for (int i = 0; i < wordList.size(); i++) {
			int lengthWordInListe = wordList.get(i).length();
			if(lengthWordInListe!=lengthFromWord) {
				wordList.remove(i);
				i--;
			}
		}
		wordList.trimToSize();
		countAndSort();
	}
	
	/**
	 * L�scht alle Eintr�ge der Liste, indenen der Buchstabe nicht an der selben Stelle vorhanden ist (ausgehend von dem ersten erscheinen des Buchstaben in theWord)
	 * @param charExist
	 */
	private void charExist(Character charExist,char[] theWord) {
		int indexOfChar=0;
		//Finde stelle des Buchstaben in theWord heraus
		for(int i =0;i<theWord.length;i++) {
			Character theWordIndex = theWord[i];
			if(theWordIndex==charExist) {
				indexOfChar = i;
				break;
			}
		}
		//teste ob eingesetzte stelle des buchstaben gleich der stelle im wort von wordList ist 
		for(int i=0;i<wordList.size();i++) {
			if(wordList.get(i).charAt(indexOfChar)!=charExist) {
				wordList.remove(i);
				i--;
			}
		}
		
		wordList.trimToSize();
	}
		
	/**
	 * aufrufen der Methoden: resetCounterFromDigitList,countCharacter,sortArrays
	 */
	private void countAndSort() {
		resetCounterFromcountCharacter();
		countCharacter(countChars,amountOfCharsInWords);
		sortArrays(countChars,amountOfCharsInWords);
	}
	
	
	/**
	 * Sucht anhand der Menge der Buchstaben in den Potentiellen W�rtern den h�ufigsten, noch nicht benutzten, Buchstaben heraus 
	 * @return char 
	 */
	private char getCharToUse(char[] amountOfCharsInAllWords) {
		int lastCharInArrayChars = amountOfCharsInAllWords.length-1;
		Character bestChar = amountOfCharsInAllWords[lastCharInArrayChars];
		String alreadyUsed = usedChars.toString();
		//solange buchstabe schon benutzt wurde -> teste nachfolgenden Buchstaben
		while(alreadyUsed.contains(bestChar.toString())){
			lastCharInArrayChars--;
			bestChar = amountOfCharsInAllWords[lastCharInArrayChars];
		}
		this.usedChars.add(0, bestChar);
		return bestChar;
	}
	
	/**
	 * Z�hlt alle Buchstaben durch die in der Liste vorhanden sind 
	 * @param countChars
	 * @param allChars
	 */
	private void countCharacter(int[] countChars,char[] allChars) {
		String allWords = mergeWordList().toLowerCase();
		for(int i=0;i<allWords.length();i++) {
			for(int x=0;x<allChars.length;x++) {
				if(allChars[x]==allWords.charAt(i)) {
					countChars[x]++;
				}
			}
		}
	}
	
	/**
	 * Durchsucht alle W�rter nach Umlaute und ersetzt diese durch ae/ue/oe
	 */
	private void replaceVowelInList() {
		for (int i = 0; i < wordList.size(); i++) {
			String searchForVowel = wordList.get(i);
			if(searchForVowel.contains("�")) {
				searchForVowel.replace("�", "oe");
				wordList.remove(i);
				wordList.add(i, searchForVowel);
			}
			if(searchForVowel.contains("�")) {
				searchForVowel.replace("�", "ae");
				wordList.remove(i);
				wordList.add(i, searchForVowel);
			}
			if(searchForVowel.contains("�")) {
				searchForVowel.replace("�", "ue");
				wordList.remove(i);
				wordList.add(i, searchForVowel);
			}
		}
	}
	
	/**
	 * Setzt alle w�rter zu einem ganzen String zusammen
	 * @return
	 */
	private String mergeWordList() {
		int sizeOfList = wordList.size();
		StringBuilder sb = new StringBuilder(); 
		for(int a=0;a<sizeOfList;a++) {
			sb = sb.append(wordList.get(a));
		}
		String allWords = sb.toString();
		return allWords;
	}
	
	/**
	 * Sortiert countChars und allChars array nach h�ufigkeit hoechster zahl von CountChars (Via BubbleSort)
	 * @param countChars
	 * @param allChars
	 */
	private void sortArrays(int[] countChars, char[] allChars) {
		for(int o=0;o<allChars.length;o++) {
			for(int a=1;a<allChars.length;a++) {
				if(countChars[a-1]>countChars[a]) {
					int tempZahl = countChars[a];
					char tempChar = allChars[a];
					countChars[a] = countChars[a-1];
					allChars[a] = allChars[a-1];
					countChars[a-1] = tempZahl;
					allChars[a-1] = tempChar;
					
				}
			}
		}
	}

}