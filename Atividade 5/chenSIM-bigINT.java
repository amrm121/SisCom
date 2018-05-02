package simRFID;

import java.math.BigInteger;

public class chenSIM {
	public static BigInteger factorial(int j) {
	    BigInteger factorial = BigInteger.valueOf(1);

	    for (int i = 1; i <= j; i++) {
	        factorial = factorial.multiply(BigInteger.valueOf(i));
	    }
	    return factorial;
	}
	public static BigInteger getFactorial(BigInteger num) {
	      if (num.intValue() == 0) return BigInteger.valueOf(1);
	      if (num.intValue() == 1) return BigInteger.valueOf(1);
	      return num.multiply(getFactorial(num.subtract(BigInteger.valueOf(1))));
	  }	
	/*public long fatorial (long f){
		if (f>0){
			long fact = 1; 
			for (long i = 1; i <= f; i++) {
				fact *= i;
			}
			return fact;
		} else {
			return 1;
		}
	}*/
	public static void main(String[] args) {
		int multiplicador = 100; //tamanho do incremento de tags entre as as baterias de simulações
		chenSIM chen = new chenSIM();
//		long aaa =  chen.factorial(64).longValue();
		//System.out.println(aaa);
		for (int multiplicado = 1; multiplicado < 11; multiplicado++){
			int repetitions = 2000; // repetições do simulador
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
					frameSize = ss + sc + sv;
					//int fatl = 1;//new BigInteger chen.factorial(frameSize);
					//int fatv = 1;//chen.factorial(sv);
					//int fatc = 1;//chen.factorial(sc);
					//int fats = 1;//chen.factorial(ss);
					BigInteger fatl = BigInteger.valueOf(1);
					fatl = chenSIM.getFactorial(fatl);
					BigInteger fatv = BigInteger.valueOf(1);
					fatv = chenSIM.getFactorial(fatv);
					BigInteger fatc = BigInteger.valueOf(1);
					fatc = chenSIM.getFactorial(fatc);
					BigInteger fats = BigInteger.valueOf(1);
					fats = chenSIM.getFactorial(fats);					

					
					System.out.println(fatl.toString());
					System.out.println(frameSize);
					System.out.println(fats.toString());
					System.out.println(ss);
					System.out.println(fatv.toString());
					System.out.println(sv);
					System.out.println(fatc.toString());
					System.out.println(sc);
					//long cons = (long) (fatl/(fats*fatc*fatv));				
					BigInteger temp = fatl.divide(fats.multiply(fatc.multiply(fatv)));
					long cons = temp.longValue();
					while (previous < next) {
						double pv = Math.pow((1-(1/frameSize)), n);
						double ps = (n/frameSize) * Math.pow(1-(1/frameSize), n-1);
						double pc = 1 - pv - ps;
						previous = next;
						next = cons * Math.pow(pv, sv) * Math.pow(ps, ss) * Math.pow(pc, sc);
						n++;
					}
					frameSize = n - 2;
					sc = 0; //reseta numeros de colisões pra cada iteração do simulador
					ss = 0; //reseta numeros de sucessos pra cada iteração do simulador
					sv = 0; //reseta numeros de slots vazios pra cada iteração do simulador
				}
			}
			//colArray[multiplicador] = totalC/2000;
			//vazArray[multiplicador] = totalV/2000;
			//totalArray[multiplicador] = slotsTotal/2000;
			long endTime = System.currentTimeMillis();
			long totalTime =endTime - startTime;
			System.out.println(("N: " + multiplicado * multiplicador) + "\t|Slots : " + slotsTotal/repetitions + "\t|Vazios: " + totalV/repetitions + "\t|Colisões: " + totalC/repetitions + "\t\t|Tempo: " + totalTime); //imprime médias dos totais ao fim das repetições da simulação
		}
	}

}
