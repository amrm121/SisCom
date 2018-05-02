#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#include <stdbool.h>

double lowerBound(double sucessos, double colisoes, double vazios){
  return 2*colisoes;
}

double eomLee(double sucessos, double colisoes, double vazios){
  double L;
  double gama, beta, new_gama, new_beta;
  double threshold = 0.001;

  gama = 2;
  beta = INFINITY;
  L = sucessos+colisoes+vazios;
  do{
    new_beta = L/((gama*colisoes) + sucessos);
    beta = new_beta;

    new_gama = (1-exp(-(1/beta)))/(beta*(1-(1 + (1/beta))*exp(-(1/beta))));
    gama = new_gama;

  }while(fabs(gama - new_gama) > threshold);

  return ceil(gama*colisoes);
}

void Qsimulator(double* stats_out, double qtdTags){
  double Q, Qfp, Cq;
  double tagsCount = 0;
  double frameSize = 0;
  double vazios = 0;
  double colisoes = 0;
  double slots = 0;
  double aux;
  double controle, i;

  tagsCount = qtdTags;

  Q = 4;
  Qfp = 4;
  Cq = 0.2;

  do{
    frameSize = pow(2,Q);

    double* frame = (double*)calloc(frameSize,sizeof(double));

    slots += frameSize;

    controle = 0;
    for(i=0;i<tagsCount && controle< 2;i++){
      aux = rand() % (int) frameSize;
      frame[(int)aux]++;
      if(aux == 0){
        controle++;
      }
    }

    if(controle == 2){
      colisoes++;
      if((Cq + Qfp) < 15){
        Qfp = Cq + Qfp;
      }else{
        Qfp = 15;
      }
    }else if(controle == 1){
      Qfp = Qfp;
      tagsCount--;
    }else{
      vazios++;
      if((Qfp - Cq) > 0){
        Qfp = Qfp - Cq;
      }else{
        Qfp = 0;
      }
    }

    Q = ceil(Qfp);
    frameSize = pow(2,Q);
    free(frame);
  }while (tagsCount>0);

  stats_out[0] = slots;
  stats_out[1] = colisoes;
  stats_out[2] = vazios;
}

void simulator(double* stats_out, double estimator(double,double,double), double qtdTags, double initialFrame) {
  double slots = 0;/*Guarda o número de slots total utilizados*/
  double tagsCount = 0;
  double vazios = 0;
  double colisoes = 0;
  double frameSize = 0;

  double sucessosFrame = 0;
  double colisoesFrame = 0;
  double vaziosFrame = 0;

  double aux = 0;

  int i;

  tagsCount = qtdTags;
  /*Enquanto ainda houver tags a serem identificadas*/
  while(tagsCount>0){
    slots += frameSize;

    if (frameSize == 0){
      frameSize = initialFrame;
    }else{
      frameSize = estimator(sucessosFrame,colisoesFrame,vaziosFrame);
      sucessosFrame = 0;
      colisoesFrame = 0;
      vaziosFrame = 0;
    }

    double* frame = (double*)calloc(frameSize,sizeof(double));

    aux = tagsCount;

    for(i=0;i<tagsCount;i++){
        frame[rand() % (int) frameSize]++;
    }

    /*Checar Sucessos, colisões e vazios*/
    for(i=0;i<frameSize;i++){

      if(frame[i] == 1){
        tagsCount -= 1;
        sucessosFrame += 1;
      }else if(frame[i] == 0){
        vazios += 1;
        vaziosFrame += 1;
      }else{
        colisoes += 1;
        colisoesFrame += 1;
      }
    }

    free(frame);
  }
  /*Retornar:
    Quantidade total de slots utilizados
    Quantidade total de slots com colisoes
    Quantidade total de slots vazios
*/
  stats_out[0] = slots;
  stats_out[1] = colisoes;
  stats_out[2] = vazios;
}

int main(int argc, char const *argv[]) {
  double stats[3];
  double results[3];
  /*Variáveis de Tempo*/
  clock_t begin,end;
  double time_spent;

  double initialTags = 50;
  double stepsTags = 50;
  double maximuTags = 1000;
  double reiteration = 30;
  double initialFrame = 25;

  double numTags = 0;

  int i;

  int menu;
  char cmenu;
  int est[4];

  srand(time(NULL));

  FILE* LowerBound = fopen("LowerBound.txt", "w");
  FILE* EomLee = fopen("EomLee.txt", "w");
  FILE* Qsim = fopen("SimuladorQ.txt", "w");
  FILE* Qsim2 = fopen("SimuladorQ2.txt", "w");

  printf(":: Configuracoes de Parametros :: ");
  printf("\nNumero de Tags Iniciais: ");
  scanf("%d", &menu);
  initialTags = (double)menu;

  printf("\nNumero de Passo de Tags: ");
  scanf("%d", &menu);
  stepsTags = (double)menu;

  printf("\nNumero de Maximo de Tags: ");
  scanf("%d", &menu);
  maximuTags = (double)menu;

  printf("\nNumero de Iteracoes: ");
  scanf("%d", &menu);
  reiteration = (double)menu;

  printf("\nTamanho Inicial do Quadro: ");
  scanf("%d", &menu);
  initialFrame = (double)menu;

  printf("\n:: Escolha dos Estimadores :: ");
  printf("\nDigite y para sim e n para nao\n");
  printf("\nLower Bound: ");
  scanf("%c ", &cmenu);

  if(cmenu == 'y'){
    est[0] = 1;
  }else{
    est[0] = 0;
  }

  cmenu = 'n';

  printf("\nEom Lee: ");
  scanf("%c ", &cmenu);

  if(cmenu == 'y'){
    est[1] = 1;
  }else{
    est[1] = 0;
  }

  cmenu = 'n';

  printf("\nAlgoritmo Q: ");
  scanf("%c ", &cmenu);

  if(cmenu == 'y'){
    est[2] = 1;
  }else{
    est[2] = 0;
  }

  cmenu = 'n';

  printf("\nOutro: ");
  scanf("%c ", &cmenu);

  if(cmenu == 'y'){
    est[3] = 1;
  }else{
    est[3] = 0;
  }

  est[0] = 1;
  est[1] = 1;
  est[2] = 1;
  est[3] = 0;

  for(numTags = initialTags;numTags < maximuTags; numTags += stepsTags){
    if(est[0]){
      results[0] = 0;
      results[1] = 0;
      results[2] = 0;
      
      for(i=0;i<reiteration;i++){
        stats[0] = 0;
        stats[1] = 0;
        stats[2] = 0;

        begin = clock();
        simulator(stats,lowerBound,numTags,initialFrame);
        end = clock();

        time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
        results[0] += stats[0];
        results[1] += stats[1];
        results[2] += stats[2];
      }

      results[0] = results[0]/reiteration;
      results[1] = results[1]/reiteration;
      results[2] = results[2]/reiteration;
      printf("OKAY\n" );
      fprintf(LowerBound, "%d %d %d %d\n", (int)numTags,(int)results[0],(int)results[1],(int)results[2]);
    }

    if(est[1]){
      results[0] = 0;
      results[1] = 0;
      results[2] = 0;

      for(i=0;i<reiteration;i++){
        stats[0] = 0;
        stats[1] = 0;
        stats[2] = 0;

        begin = clock();
        simulator(stats,eomLee,numTags,initialFrame);
        end = clock();

        time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
        results[0] += stats[0];
        results[1] += stats[1];
        results[2] += stats[2];
      }

      results[0] = results[0]/reiteration;
      results[1] = results[1]/reiteration;
      results[2] = results[2]/reiteration;

      printf("OKAY\n" );
      fprintf(EomLee, "%d %d %d %d\n", (int)numTags,(int)results[0],(int)results[1],(int)results[2]);

      time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
    }

    if(est[2]){
      results[0] = 0;
      results[1] = 0;
      results[2] = 0;

      for(i=0;i<reiteration;i++){
        stats[0] = 0;
        stats[1] = 0;
        stats[2] = 0;

        begin = clock();
        Qsimulator(stats,numTags);
        end = clock();

        time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
        results[0] += stats[0];
        results[1] += stats[1];
        results[2] += stats[2];
      }

      results[0] = results[0]/reiteration;
      results[1] = results[1]/reiteration;
      results[2] = results[2]/reiteration;

      printf("OKAY\n" );
      fprintf(Qsim, "%d %d %d %d\n", (int)numTags,(int)results[0],(int)results[1],(int)results[2]);

      time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
    }

    if(est[3]){
      results[0] = 0;
      results[1] = 0;
      results[2] = 0;

      for(i=0;i<reiteration;i++){
        stats[0] = 0;
        stats[1] = 0;
        stats[2] = 0;

        begin = clock();
        Qsimulator(stats,numTags);
        end = clock();

        time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
        results[0] += stats[0];
        results[1] += stats[1];
        results[2] += stats[2];
      }

      results[0] = results[0]/reiteration;
      results[1] = results[1]/reiteration;
      results[2] = results[2]/reiteration;

      printf("OKAY\n" );
      fprintf(Qsim2, "%d %d %d %d\n", (int)numTags,(int)results[0],(int)results[1],(int)results[2]);

      time_spent = (double)(end-begin)/CLOCKS_PER_SEC;

    }

  }
  fclose(LowerBound);
  fclose(EomLee);
  fclose(Qsim);
  fclose(Qsim2);

  /*Gerar Arquivo de Plot*/
  FILE* plot = fopen("plot_results.txt", "w");
  fprintf(plot, "#Parametros utilizados: \n");
  fprintf(plot,"\n#Numero de Tags Iniciais: %d", (int)initialTags);
  fprintf(plot,"\n#Numero de Passo de Tags: %d", (int)stepsTags);
  fprintf(plot,"\n#Numero de Maximo de Tags: %d", (int)maximuTags);
  fprintf(plot,"\n#Numero de Iteracoes: %d", (int)reiteration);
  fprintf(plot,"\n#Tamanho Inicial do Quadro: %d", (int)initialFrame);

  fprintf(plot,"\n\nset xlabel \"Etiquetas\"");
  fprintf(plot,"\nset terminal wxt size 800,800");
  fprintf(plot,"\nset grid");

  fprintf(plot,"\n\n#Total de Slots");
  fprintf(plot,"\nset terminal wxt 1");
  fprintf(plot,"\nset ylabel \"Slots\"");
  fprintf(plot,"\nset title \"Total de Slots\"");
  fprintf(plot,"\nset yrange [0:3200]");
  fprintf(plot,"\nplot ");

  if(est[0]){
    fprintf(plot,"\"LowerBound.txt\" using 1:2 title \"LowerBound\" smooth unique with linespoints");
    if (est[1]) {
      fprintf(plot,",");
    }
  }
  if (est[1]) {
    fprintf(plot,"\"EomLee.txt\" using 1:2 title \"EomLee\" smooth unique with linespoints");
    if (est[2]) {
      fprintf(plot,",");
    }
  }else{
    if (est[2]) {
      fprintf(plot,",");
    }
  }
  if (est[2]) {
    fprintf(plot,"\"SimuladorQ.txt\" using 1:2 title \"Qsim\" smooth unique with linespoints");
    if (est[3]) {
      fprintf(plot,",");
    }
  }else{
    if (est[3]) {
      fprintf(plot,",");
    }
  }
  if (est[3]) {
    fprintf(plot,"\"SimuladorQ2.txt\" using 1:2 title \"Qsim2\" smooth unique with linespoints");
  }

  fprintf(plot,"\n\n#Total de Slots Vazios");
  fprintf(plot,"\nset terminal wxt 2");
  fprintf(plot,"\nset ylabel \"Slots\"");
  fprintf(plot,"\nset title \"Total de Slots Vazios\"");
  fprintf(plot,"\nset yrange [0:1100]");
  fprintf(plot,"\nplot ");
  if(est[0]){
    fprintf(plot,"\"LowerBound.txt\" using 1:4 title \"LowerBound\" smooth unique with linespoints");
    if (est[1]) {
      fprintf(plot,",");
    }
  }
  if (est[1]) {
    fprintf(plot,"\"EomLee.txt\" using 1:4 title \"EomLee\" smooth unique with linespoints");
    if (est[2]) {
      fprintf(plot,",");
    }
  }else{
    if (est[2]) {
      fprintf(plot,",");
    }
  }
  if (est[2]) {
    fprintf(plot,"\"SimuladorQ.txt\" using 1:4 title \"Qsim\" smooth unique with linespoints");
    if (est[3]) {
      fprintf(plot,",");
    }
  }else{
    if (est[3]) {
      fprintf(plot,",");
    }
  }
  if (est[3]) {
    fprintf(plot,"\"SimuladorQ2.txt\" using 1:4 title \"Qsim2\" smooth unique with linespoints");
  }

  fprintf(plot,"\n\n#Total de Colisoes");
  fprintf(plot,"\nset terminal wxt 3");
  fprintf(plot,"\nset ylabel \"Colisoes\"");
  fprintf(plot,"\nset title \"Total de Colisoes\"");
  fprintf(plot,"\nset yrange [0:1800]");
  fprintf(plot,"\nplot ");
  if(est[0]){
    fprintf(plot,"\"LowerBound.txt\" using 1:3 title \"LowerBound\" smooth unique with linespoints");
    if (est[1]) {
      fprintf(plot,",");
    }
  }
  if (est[1]) {
    fprintf(plot,"\"EomLee.txt\" using 1:3 title \"EomLee\" smooth unique with linespoints");
    if (est[2]) {
      fprintf(plot,",");
    }
  }else{
    if (est[2]) {
      fprintf(plot,",");
    }
  }
  if (est[2]) {
    fprintf(plot,"\"SimuladorQ.txt\" using 1:3 title \"Qsim\" smooth unique with linespoints");
    if (est[3]) {
      fprintf(plot,",");
    }
  }else{
    if (est[3]) {
      fprintf(plot,",");
    }
  }
  if (est[3]) {
    fprintf(plot,"\"SimuladorQ2.txt\" using 1:3 title \"Qsim2\" smooth unique with linespoints");
  }

  fclose(plot);
  return 0;
}
