import edu.princeton.cs.randomhash.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class HashTests {
	String PATH;
	private int collisions;
	HashSet<Long>[] setsArray;
	long elapsedTime;
	int numberOfTests;
	
	public HashTests(int numberOfTests, String PATH) {
		this.PATH = PATH;
		this.setsArray = new HashSet[numberOfTests];
		for(int k=0; k<numberOfTests; k++) {
			setsArray[k] = new HashSet<Long>();
		}
		this.collisions = 0;
		this.numberOfTests = numberOfTests;
	}
	
	
	// Codice per il testing su una famiglia di funzioni Hash
	// Di dimensione "numberOfTests"
	public void doTests() throws IOException {	
		RandomHashFamily rhf = new RandomHashFamily(numberOfTests);
		try {
			BufferedReader br = new BufferedReader(new FileReader(PATH));
			String line;
			long startTime = System.nanoTime();
			int collsFun[] = new int[numberOfTests];
            while ((line = br.readLine()) != null) {
            	String[] words = line.split(" ");
            	for (String word : words) {
            		if(!word.isEmpty()) {
            			long[] hashes = rhf.hashes(word);
            			for(int k=0; k<numberOfTests; k++) {
            				if(setsArray[k].contains(hashes[k])) {
            					collisions+=1;
            					collsFun[k]+=1;
            				} else {
            					setsArray[k].add(hashes[k]);
            				}
            			}
            			
            		}	
                }
            }
            br.close();
            long endTime = System.nanoTime();
            
            elapsedTime = endTime - startTime;
            Double elapsedTimeInSeconds = (elapsedTime/(Math.pow(10, 9)));     
            System.out.println("Collisioni: "+collisions);
            System.out.println("Collisioni medie: "+(collisions/numberOfTests));
            System.out.println("Tempo di esecuzione totale: "+elapsedTimeInSeconds+" sec.");
            System.out.println("Tempo di esecuzione medio: "+(elapsedTimeInSeconds/numberOfTests)+" sec.");
            for(int i=0; i<numberOfTests; i++) {
            	System.out.println("Collisioni funzione "+i+": "+collsFun[i]);
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	// Codice per il testing su un numero di funzioni Hash 
	// distinte pari a "numberOfTests"
	public void doTests2() throws IOException {
		HashSet<Long> allColls = new HashSet<Long>();
		int allCollisions = 0;
		for(int i=0; i<numberOfTests; i++) {
			HashSet<Long> distincts = new HashSet<Long>();
			int collisions = 0;
			RandomHashFamily rhf = new RandomHashFamily(1);
			try {
				BufferedReader br = new BufferedReader(new FileReader(PATH));
				String line;
	            while ((line = br.readLine()) != null) {
	            	String[] words = line.split(" ");
	            	for (String word : words) {
	            		if(!word.isEmpty()) {
	            			long hash = rhf.hash(word);
	            			if(distincts.contains(hash)) {
	            				collisions+=1;
	            				allColls.add(hash);
	            			} else {
	            				distincts.add(hash);
	            			}	
	            		}	
	                }
	            }
	            allCollisions+=collisions;
	            br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
			System.out.println("Collisioni parziali: "+allCollisions);
	        System.out.println("Size allColls parziale: "+allColls.size());
		}
		System.out.println("\nCollisioni totali: "+allCollisions);
        System.out.println("Size allColls totale: "+allColls.size());
        Iterator itr = allColls.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
	}

}
