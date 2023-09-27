import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;


public class Main {
	
	public static void calculations() throws IOException, InterruptedException {
		
		// Modificare "PATH" con il PATH dello stream che si desidera utilizzare
		String PATH = "C:\\Users\\matte\\Desktop\\Tesi\\CODICI\\test_case.txt";
		
		// Modificare "numHashes" con il numero di funzioni Hash che si desidera utilizzare.
		// IMPORTANTE!!! "numHashes" DEVE essere un multiplo di "2" !!!
		int numHashes = 256;
		
		// Avvia il timer
		long startTime = System.nanoTime();
		
		// Avvia i Threads
		ArrayList<FlajoletMartinThreads> alfmt = new ArrayList<FlajoletMartinThreads>();
		for(int i=0; i<numHashes; i++) {
			FlajoletMartinThreads fmt = new FlajoletMartinThreads(PATH);
			fmt.start();
			alfmt.add(fmt);
		}
		
		// Attendi e memorizza i risultati
		double[] results = new double[numHashes];
		for(int i=0; i<numHashes; i++) {
			alfmt.get(i).join();
			results[i] = alfmt.get(i).getResult();
		}
		
		// MEDIANO DELLE MEDIE
		int numGruppi = numHashes/32;
		double[] means = new double[numGruppi];
		for (int i=0; i<numGruppi; i++) {
			double[] group = Arrays.copyOfRange(results, i * 32, (i + 1) * 32);
			double sum = 0.0;
			for( double result : group) {
				sum+=result;
			}
			double mean = sum/32;
			means[i] = mean;
		}
		
		Arrays.sort(means);
		double dFM = (means[numGruppi / 2 - 1] + means[numGruppi / 2]) / 2.0;
		
		// Registra il tempo di esecuzione
		long endTime = System.nanoTime();
		long elapsedTime = endTime-startTime;
		Double elapsedTimeInSeconds = (elapsedTime/(Math.pow(10, 9))); 
		
		// Esegui l'algoritmo banale
        double dTriv = trivial(PATH);
        
        // Calcola l'errore commesso da FM
        double err = Math.abs((dTriv/dFM)*100 - 100);
        
        // Stampa i risultati
        System.out.println("Numero di elementi distinti con l'algoritmo banale: " + dTriv);
        System.out.println("Numero di elementi distinti per Flajolet-Martin con "+numHashes+" funzioni Hash: " + dFM); 
        System.out.println("Flajolet-Martin commette un errore del: "+err+"%");
        System.out.println("Tempo di esecuzione: "+elapsedTimeInSeconds);
	}
	
	
	// Algoritmo banale
	public static double trivial(String PATH) throws IOException {
		Set<String> set = new HashSet<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(PATH));
			String line;
            while ((line = br.readLine()) != null) {
            	String[] words = line.split(" ");
            	for (String word : words) {
            		set.add(word);
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return set.size();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Modificare il numero di prove a piacimento
		int numeroProve = 10;
		
		// Effettua le prove
		for(int i=0; i<numeroProve; i++) {
			System.out.println("\nMisurazione "+(i+1));
			calculations();
		}
		
	}

}