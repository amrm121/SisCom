package simRFID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class Simulador{ 
	private int sel, increment, repetitions, i_FSize;
	private String output;
	public Simulador(int algo, int i_frame_size, int increment) {
		this.sel = algo;
		switch(this.sel) {
		case 1:
			output = "LowerBound.txt";
			break;
		case 2:
			output = "Shoute.txt";
			break;
		case 3:
			output = "EomLee.txt";
			break;
		default:
				break;
		}
		this.repetitions = 2000;
		this.increment = increment;
		this.i_FSize = i_frame_size;
	}
	public String getAlgo() {
		return output;
	}
	public void Simulation() throws IOException, FileNotFoundException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "utf-8"));
		for (int incremented = 1; incremented < 11; incremented++){
			int tagsTotal = incremented * increment; // numero total de tags
			int sc = 0, totalC = 0; // slots de colisao, e total global de slots de colisao
			int ss = 0, slotsTotal = 0; // slots de sucesso e total global de slots
			int sv = 0, totalV = 0; // slots vazios e total global de slots vazios
			int[] frame; //array de inteiro simula um frame
			long startTime = System.currentTimeMillis();
			for (int i = 0; i<repetitions; i++){
				int frameSize = i_FSize; //define o valor do frame inicial pra cada simulacao
				int unreadTags = tagsTotal; // define o numero total de tags a ler por simulacao
				while(unreadTags>0){
					frame = new int[frameSize]; //define o tamanho de cada frame em cada iteracao do simulador
					for (int j = 0; j<unreadTags; j++){
						int populate = (int)((Math.random()*(frameSize))); //tira um numero de slot aleatorio
						frame[populate] = frame[populate] +1; //popula esse slot aleatorio com uma tag
					}
					for (int j = 0; j<frameSize; j++){
						if (frame[j]>1){
							sc++; // calcula numero de slots com colisao
						} else if (frame[j]==1){
							ss++; // calcula numero de slots com sucesso
						} else if (frame[j]==0){
							sv++; // calcula numero de slots vazios
						}
					}
					unreadTags -= ss; //diminui o numero total de tags a ser lida de acordo com slots de sucesso
					totalC += sc; // acumula numero total de colisoes numa simulacao
					totalV += sv; // acumula numero total de slots vazios numa simulacao
					slotsTotal += frameSize; // acumula numero total de slots em uma dada simulacao
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
					sc = 0; //reseta numeros de colisoes pra cada iteracao do simulador
					ss = 0; //reseta numeros de sucessos pra cada iteracao do simulador
					sv = 0; //reseta numeros de slots vazios pra cada iteracao do simulador
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			if(incremented == 10) {writer.write("T:"+(incremented * increment) + " S:" + slotsTotal/repetitions + " V:" + totalV/repetitions + " C:" + totalC/repetitions + " t:" + totalTime);}
			else {
				writer.write("T:"+(incremented * increment) + " S:" + slotsTotal/repetitions + " V:" + totalV/repetitions + " C:" + totalC/repetitions + " t:" + totalTime +"\n");
			}
		}
		writer.close();
	}
}
