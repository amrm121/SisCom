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
		long startTime = System.currentTimeMillis(); //inicio o contador do tempo de simulacao 
		for (int incremented = 1; incremented < 11; incremented++){
			int Tags = incremented * increment;
			int sc = 0, tC = 0, ss = 0, tS = 0, sv = 0, tV = 0; //inicializo os valores da simulacao, slots em colisao, total de colisoes na simulacao, slots com sucesso...
			int[] frame; //o frame do aloha representado por um array de int
			for (int i = 0; i<repetitions; i++){
				int frameSize = i_FSize; 
				int unreadTags = Tags; //o numero restante de tags a serem lidas, inicialmente e o numero total de tags
				while(unreadTags>0){ //enquanto eu nao ler todas as tags, inicia a simulacao
					frame = new int[frameSize]; //atualiza o tamanho de cada frame em cada iteracao do simulador
					for (int j = 0; j<unreadTags; j++){ //simulo a resposta da tag incrementando o valor da posicao do array dentro do tamanho do frame 
						int tagAnswer = (int)((Math.random()*(frameSize))); //"resposta" da tag, um numero aleatorio representando a escolha do slot
						frame[tagAnswer]++; //incremento no "leitor" que uma tag escolheu aquele slot
					}
					for (int j = 0; j<frameSize; j++){ //varro o array, e vejo em cada posicao, se houve colisao, sucesso ou vazio
						if (frame[j]>1){ //colisao, pelo menos duas tags escolheram aquele slot
							sc++;
						} else if (frame[j]==1){ //sucesso, apenas uma tag escolheu aquele slot
							ss++; 
						} else if (frame[j]==0){ //vazio
							sv++; 
						}
					}
					unreadTags -= ss; //diminui o numero total de tags a ser lida de acordo com slots de sucesso
					tC += sc; // acumula numero total de colisoes numa simulacao
					tV += sv; // acumula numero total de slots vazios numa simulacao
					tS += frameSize; // acumula numero total de slots em uma dada simulacao
					if (sel == 1){ //lowerbound
						frameSize = sc *2; 
					}
					else if (sel == 2){ //eom lee
						double bk, ek, yk, ydif;
						double y0 = 2.0;
						int f;
						int l = frameSize; 
						do {
							bk = l / ((y0 * sc) +ss);
							ek = Math.exp(-(1.0/bk)); //e^-(1/Bk)
							yk = (1 - ek)/(bk * ( 1 - (1 + (1/bk))*ek));
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
			long tTime = (System.currentTimeMillis() - startTime);
			double totalTime = (double)tTime/1000;
			if(incremented == 10) {writer.write("T:"+(incremented * increment) + " S:" + tS/repetitions  + " V:" + tV/repetitions + " C:" + tC/repetitions + " t:" + totalTime);}
			else {
				writer.write("T:"+(incremented * increment) + " S:" + tS/repetitions  +  " V:" + tV/repetitions + " C:" + tC/repetitions + " t:" + totalTime +"\n");
			}
		}
		writer.close();
	}
}
