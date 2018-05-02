package simRFID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class simulador {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lowerBound.txt"), "utf-8"));
		int multiplicador = 100; //tamanho do incremento de tags entre as as baterias de simula��es
		for (int multiplicado = 1; multiplicado < 11; multiplicado++){
			int sel =1; // 1 pra lower bound, 2 pra shoute, 3 pra eom lee
			int repetitions = 2000; // repeti��es do simulador
			int tagsTotal = multiplicado * multiplicador; // numero total de tags
			int f0 = 64; // tamanho/n�mero de slots do frame inicial
			int sc = 0, totalC = 0; // slots de colis�o, e total global de slots de colis�o
			int ss = 0, slotsTotal = 0; // slots de sucesso e total global de slots
			int sv = 0, totalV = 0; // slots vazios e total global de slots vazios
			int[] frame; //array de inteiro simula um frame
			long startTime =System.currentTimeMillis();
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
					if (sel == 1){ //lowerbound
						frameSize = sc *2; 
					}
					else if(sel == 2){ //schoute
						double schoute = Math.floor(sc * 2.39);
						frameSize = (int) schoute;
					}
					else if (sel == 3){ //eom lee
						double bk, ek, yk, ydif;
						double y0 = 2.0;
						int f;
						int l = frameSize;
						do {
							bk = l / ((y0 * sc) +ss);
							ek = Math.exp(-1.0/bk);
							yk = (1 - ek)/(bk * ( 1- (1 + (1/bk))* ek));
							ydif = Math.abs(y0 - yk);
							y0 = yk;
						} while (ydif>0.001);
						f = (int) Math.ceil(yk * sc);
						frameSize = f;
					}
					sc = 0; //reseta numeros de colis�es pra cada itera��o do simulador
					ss = 0; //reseta numeros de sucessos pra cada itera��o do simulador
					sv = 0; //reseta numeros de slots vazios pra cada itera��o do simulador
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime =endTime - startTime;
			//System.out.println(("N:\t" + multiplicado * multiplicador) + "\t| Slots:\t" + slotsTotal/repetitions + "\t| Vazios:\t" + totalV/repetitions + "\t| Colis�es:\t" + totalC/repetitions + "\t| Tempo:\t" + totalTime+"\n"); //imprime m�dias dos totais ao fim das repeti��es da simula��o
			writer.write((multiplicado * multiplicador) + " " + slotsTotal/repetitions + " " + totalV/repetitions + " " + totalC/repetitions + " " + totalTime +"\n");
		}
		writer.close();
	}
}
