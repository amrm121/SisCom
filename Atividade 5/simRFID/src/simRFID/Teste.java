package simRFID;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Teste {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		for(int i = 1; i < 4; i++) {
			Simulador sim = new Simulador(i, 64, 100);
			sim.Simulation();
			FileReader fr = new FileReader(sim.getAlgo());
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			System.out.println(sim.getAlgo());
			while((line = br.readLine()) != null) {
				System.out.println(line);
				int tags = Integer.parseInt(line.substring(line.indexOf("T")+2, line.indexOf(" ")));
				line = line.substring(line.indexOf(" ")+1);
				System.out.println(line);
				int slots = Integer.parseInt(line.substring(line.indexOf("S")+2, line.indexOf(" ")));
				System.out.println(tags + "  :  " + slots);
			}
			System.out.println();
			br.close();
		}

	}

}
