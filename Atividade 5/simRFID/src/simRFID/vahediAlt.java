package simRFID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;

public class vahedi3 {
	/*BigInteger[] factarray = new BigInteger[512];
	public BigInteger getFactorial(BigInteger num) {
		factarray[0] = BigInteger.valueOf(1);
		factarray[1] = BigInteger.valueOf(1);
		BigInteger BItemp= factarray[num.intValue()];
		if(BItemp == null){
			return num.multiply(getFactorial(num.subtract(BigInteger.valueOf(1))));
		}
		else{
			return factarray[num.intValue()];
		}
		return factarray[num.intValue()];
	}	*/
	public static void main(String[] args) throws IOException, FileNotFoundException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("vahedi.txt"), "utf-8"));
		//vahedi3 vahedi = new vahedi3();
		BigInteger[] factarray = new BigInteger[1024];
		factarray[0] = BigInteger.valueOf(1);
		BigInteger result= factarray[0];
		for (int cont=0; cont< factarray.length; cont++){
			result = result.multiply(BigInteger.valueOf(cont));
			factarray[cont] = result;
		}
		int multiplicador = 100; //tamanho do incremento de tags entre as as baterias de simulações
		for (int multiplicado = 1; multiplicado < 11; multiplicado++){
			int repetitions = 200; // repetições do simulador
			int tagsTotal = multiplicado * multiplicador; // numero total de tags
			int f0 = 64; // tamanho/número de slots do frame inicial
			int sc = 0, totalC = 0; // slots de colisão, e total global de slots de colisão
			int ss = 0, slotsTotal = 0; // slots de sucesso e total global de slots
			int sv = 0, totalV = 0; // slots vazios e total global de slots vazios
			int[] frame; //array de inteiro simula um frame
			long startTime =System.currentTimeMillis();
			for (int i = 0; i<repetitions; i++){
				int frameSize = f0; //define o valor do frame inicial pra cada simulação
				int unreadTags = tagsTotal; // define o número total de tags a ler por simulação
				while(unreadTags>0){
					frame = new int[frameSize]; //define o tamanho de cada frame em cada iteração do simulador
					for (int j = 0; j<unreadTags; j++){
						int populate = (int)((Math.random()*(frameSize))); //tira um número de slot aleatorio
						frame[populate] = frame[populate] +1; //popula esse slot aleatorio com uma tag
					}
					for (int j = 0; j<frameSize; j++){
						if (frame[j]>1){
							sc++; // calcula numero de slots com colisão
						} else if (frame[j]==1){
							ss++; // calcula numero de slots com sucesso
						} else if (frame[j]==0){
							sv++; // calcula numero de slots vazios
						}
					}
					unreadTags = unreadTags - ss; //diminui o numero total de tags a ser lida de acordo com slots de sucesso
					totalC = totalC + sc; // acumula numero total de colisões numa simulação
					totalV = totalV + sv; // acumula numero total de slots vazios numa simulaçao
					slotsTotal = slotsTotal + frameSize; // acumula numero total de slots em uma dada simulação
					double next =0, previous =-1;
					int n = ss + (2*sc);
					BigInteger fatL = factarray[frameSize];
					BigInteger fatSC = factarray[sc];
					BigInteger fatSV = factarray[ss];
					BigInteger fatSS = factarray[sv];
					double nTemp = fatL.divide(fatSS.multiply(fatSC.multiply(fatSV))).doubleValue();
					while ((previous < next)&&!(previous>=next)&&(previous!=next)) {
						double p1 = 1-(sv/frameSize); 
						p1 = Math.pow(p1, n);
						BigInteger fatN = factarray[n];;
						BigInteger fatNMSS = factarray[n-ss];;
						BigInteger BIp2 = fatSS.multiply(fatN.divide(fatSS.multiply(fatNMSS)));
						double tempP2 = (Math.pow((frameSize-sv-ss), (n-ss))/Math.pow((frameSize - sv), n));
						double p2 = tempP2 * BIp2.doubleValue();
						double p3 = 0;
						for (int k =0; k < sc; k++){
							for (int v = 0; v < (sc-k); v++){
								BigInteger fatK = factarray[k];
								BigInteger fatV = factarray[v];;
								BigInteger fatSCMK = factarray[sc-k];
								BigInteger fatSCMKMV = factarray[sc-k-v];;
								BigInteger fatNMSSMK = factarray[n-ss-k];
								BigInteger BIp31 = fatSC.divide(fatK.multiply(fatSCMK));
								BigInteger BIp32 = fatSCMK.divide(fatV.multiply(fatSCMKMV));
								BigInteger BIp33 = fatNMSS.divide(fatNMSSMK);
								double tempP31 = (Math.pow((frameSize-sv-ss), (n-ss))/Math.pow((frameSize - sv), n));
								double tempP32 = Math.pow(-1, (k+v));
								p3 += tempP31 * tempP32 * BIp31.doubleValue()*BIp32.doubleValue()*BIp33.doubleValue();
							}
						}	
						previous = next;
						next = (nTemp * p1 * p2 * p3);	
						//System.out.println(nTemp+" "+p1+" "+p2+" "+p3 +" "+ next +" "+previous+" "+frameSize+" "+ss+" "+sv+" "+sc+" "+unreadTags);
						n++;
					}
					//System.out.println("saiu");
					frameSize = (int)(n - 2 - ss);
					sc = 0; //reseta numeros de colisões pra cada iteração do simulador
					ss = 0; //reseta numeros de sucessos pra cada iteração do simulador
					sv = 0; //reseta numeros de slots vazios pra cada iteração do simulador
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime =endTime - startTime;
			System.out.println(("N:\t" + multiplicado * multiplicador) + "\t| Slots:\t" + slotsTotal/repetitions + "\t| Vazios:\t" + totalV/repetitions + "\t| Colisões:\t" + totalC/repetitions + "\t\t| Tempo:\t" + totalTime); //imprime médias dos totais ao fim das repetições da simulação
			writer.write((multiplicado * multiplicador) + " " + slotsTotal/repetitions + " " + totalV/repetitions + " " + totalC/repetitions + " " + totalTime +"\n");
		}
		writer.close();
	}

}