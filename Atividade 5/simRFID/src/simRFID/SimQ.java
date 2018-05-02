package simRFID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SimQ{
	public static void main(String[] args) throws IOException, FileNotFoundException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("q.txt"), "utf-8"));
		int passos = 10;
		for (int k =1; k <= passos; k++){
			int qtdTags = k * 100;
			double Q, Qfp, Cq;
			int frameSize;
			int vazios = 0,slots = 0,colisoes = 0;
			int aux;
			int controle;
			long startTime =System.currentTimeMillis();
			int repetitions = 2000;
			for (int j = 0; j<repetitions; j++){
				int tagsCount = qtdTags;
				Q = 4;
				Qfp = Q;
				Cq = 0.2;
				//int vazios = 0,slots = 0,colisoes = 0;
				do{
					frameSize = (int) Math.pow(2,Q);
					int[] frame = new int[frameSize];;

					slots += 1;

					controle = 0;
					for(int i = 0; i < tagsCount && controle < 2;i++){
						aux = ((int) (Math.random() *(int) frameSize));
						frame[(int)aux]++;
						if(aux == 0){
							controle++;
						}
					}

					if(controle > 1){
						colisoes++;
						if((Cq + Qfp) < 15){
							Qfp = Cq + Qfp;
						}else{
							Qfp = 15;
						}
					}else if(controle == 1){
						tagsCount--;
					}else{
						vazios++;
						if((Qfp - Cq) > 0){
							Qfp = Qfp - Cq;
						}else{
							Qfp = 0;
						}
					}
					Q = Math.round(Qfp);
				}while (tagsCount>0);
				//System.out.println("N:\t" + qtdTags + "\t| Slots:\t " + slots + "\t| Vazios:\t" + vazios + "\t| Colisões:\t" + colisoes);
				//System.out.println("N:\t" + qtdTags + "\t| Slots:\t " + slots/(j+1) + "\t| Vazios:\t" + vazios/(j+1) + "\t| Colisões:\t" + colisoes/(j+1));
			}
			long endTime =System.currentTimeMillis();
			long totalTime =endTime - startTime;
			//System.out.println("N:\t" + qtdTags + "\t| Slots:\t " + slots/repetitions + "\t| Vazios:\t" + vazios/repetitions + "\t| Colisões:\t" + colisoes/repetitions + "\t|Tempo:\t" + totalTime);
			writer.write((qtdTags) + " " + slots/repetitions + " " + vazios/repetitions + " " + colisoes/repetitions + " " + totalTime +"\n");
		}
		writer.close();
	}
}