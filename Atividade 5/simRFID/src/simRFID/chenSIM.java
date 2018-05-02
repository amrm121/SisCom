package simRFID;

import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class chenSIM {
	public BigInteger getFactorial(BigInteger num) {
		if (num.intValue() == 0) return BigInteger.valueOf(1);
		if (num.intValue() == 1) return BigInteger.valueOf(1);
		return num.multiply(getFactorial(num.subtract(BigInteger.valueOf(1))));
	}	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chen.txt"), "utf-8"));
		chenSIM chen = new chenSIM();
		int multiplicador = 100; //tamanho do incremento de tags entre as as baterias de simula��es
		for (int multiplicado = 1; multiplicado < 11; multiplicado++){
			int repetitions = 2000; // repeti��es do simulador
			int tagsTotal = multiplicado * multiplicador; // numero total de tags
			int f0 = 64; // tamanho/n�mero de slots do frame inicial
			int sc = 0, totalC = 0; // slots de colis�o, e total global de slots de colis�o
			int ss = 0, slotsTotal = 0; // slots de sucesso e total global de slots
			int sv = 0, totalV = 0; // slots vazios e total global de slots vazios
			int[] frame; //array de inteiro simula um frame
			long startTime =System.currentTimeMillis();
			long iterations =0;
			for (int i = 0; i<repetitions; i++){
				int frameSize = f0; //define o valor do frame inicial pra cada simula��o
				int unreadTags = tagsTotal; // define o n�mero total de tags a ler por simula��o
				while(unreadTags>0){
					frame = new int[frameSize]; //define o tamanho de cada frame em cada itera��o do simulador
					for (int j = 0; j<unreadTags; j++){
						int populate = (int)((Math.random()*(frameSize))); //tira um n�mero de slot aleatorio
						frame[populate] = frame[populate] +1; //popula esse slot aleatorio com uma tag
					}
					for (int j = 0; j<frameSize; j++){
						if (frame[j]>1){
							sc++; // calcula numero de slots com colis�o
						} else if (frame[j]==1){
							ss++; // calcula numero de slots com sucesso
						} else if (frame[j]==0){
							sv++; // calcula numero de slots vazios
						}
					}
					unreadTags = unreadTags - ss; //diminui o numero total de tags a ser lida de acordo com slots de sucesso
					totalC = totalC + sc; // acumula numero total de colis�es numa simula��o
					totalV = totalV + sv; // acumula numero total de slots vazios numa simula�ao
					slotsTotal = slotsTotal + frameSize; // acumula numero total de slots em uma dada simula��o
					double next =0, previous =-1;
					int n = ss + (2*sc);
					frameSize = ss + sc + sv;
					BigInteger fatl = chen.getFactorial(BigInteger.valueOf(frameSize));
					BigInteger fatc = chen.getFactorial(BigInteger.valueOf(sc));
					BigInteger fatv = chen.getFactorial(BigInteger.valueOf(sv));
					BigInteger fats = chen.getFactorial(BigInteger.valueOf(ss));
					double nTemp = fatl.divide(fats.multiply(fatc.multiply(fatv))).doubleValue();
					while (previous < next) {
						double pv = Math.pow((1-(1/frameSize)), n);
						double ps = (n/frameSize) * Math.pow(1-(1/frameSize), n-1);
						double pc = 1 - pv - ps;
						previous = next;
						next = nTemp * Math.pow(pv, sv) * Math.pow(ps, ss) * Math.pow(pc, sc);
						n++;

					}
					frameSize = n - 2;
					sc = 0; //reseta numeros de colis�es pra cada itera��o do simulador
					ss = 0; //reseta numeros de sucessos pra cada itera��o do simulador
					sv = 0; //reseta numeros de slots vazios pra cada itera��o do simulador
					iterations++;
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime =endTime - startTime;
			//System.out.println(("N:\t" + multiplicado * multiplicador) + "\t| Slots:\t" + slotsTotal/repetitions + "\t| Vazios:\t" + totalV/repetitions + "\t| Colis�es:\t" + totalC/repetitions + "\t\t| Tempo:\t" + totalTime+"\t| i:\t"+iterations); //imprime m�dias dos totais ao fim das repeti��es da simula��o
			writer.write((multiplicado * multiplicador) + " " + slotsTotal/repetitions + " " + totalV/repetitions + " " + totalC/repetitions + " " + totalTime + " "+ iterations+"\n");
		}
		writer.close();
	}

}