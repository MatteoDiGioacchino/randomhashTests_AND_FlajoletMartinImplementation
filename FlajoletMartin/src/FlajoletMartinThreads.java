import edu.princeton.cs.randomhash.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FlajoletMartinThreads extends Thread {
	
	private volatile double result = 0;
	private String PATH;
	
	public FlajoletMartinThreads(String PATH) {
		this.PATH = PATH;
	}
	
	private static boolean isBitSet(int value, int index) {
	    return ((value >> index) & 1) == 1;
	}
	
	public static int setBit(int value, int index) {
	    int mask = 1 << index;
	    return value | mask;
	}
	
	
	private static int trailing(String binaryString) {
		String reversedString = new StringBuilder(binaryString).reverse().toString();
	    for (int i = 0; i < reversedString.length(); i++) {
	        if (reversedString.charAt(i) == '1') {
	            return i;
	        }
	    }
	    return 0;
	}
	
	
	// Il codice è identico a quello presentato nella trattazione, ma la funzione Hash
	// viene generata ALL'INTERNO dell'algoritmo, e non passata come parametro
	
	private void flajoletMartin(String PATH) throws IOException {
		
		int BITMAP = 0b0;
		RandomHashFamily rhf = new RandomHashFamily(1);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(PATH));
			String line;
            while ((line = br.readLine()) != null) {
            	String[] words = line.split(" ");
            	for (String word : words) {
            		if(!word.isEmpty()) {
            			long hashed = rhf.hash(word);
                		String binhashed = String.format("%32s",Long.toBinaryString(hashed)).replace(' ', '0');
                		int k = trailing(binhashed);
                		BITMAP = setBit(BITMAP,k);
            		}
                }
            }
            br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int r = 0;
		for(int i=0; i<32; i++) {
			if(!isBitSet(BITMAP,i)) {
				r=i;
				break;
			}
		}
		this.result = Math.pow(2, r);
	}
	
	@Override
	public void run() {
		try {
			flajoletMartin(PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getResult() {
		return this.result;
	}
	
}
