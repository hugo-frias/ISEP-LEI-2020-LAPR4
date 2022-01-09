#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <pthread.h>
#include <semaphore.h>
#include "SimulacaoController.h"

#define PATH_SIZE 133

int main(){

	char path[PATH_SIZE];
	short test;
	int NUM_LP, NUM_MAQ, NUM_IT, TIMER;

	printf("Pretende executar a versão de testes? (0 - não , 1 - sim) \n");
	scanf("%hu", &test);

	if(test != 0) {
		printf("Introduza o Path do ficheiro de mensagens. Cada linha dará lugar a uma instância mensagem. \n");
		scanf("%s", path);
		initialize(0, 0, 0, 0, path, test);
		exit(0);
	}

	printf("Introduza o número de linhas. \n");
	scanf("%d", &NUM_LP);

	printf("Introduza o número de máquinas. \n");
	scanf("%d", &NUM_MAQ);

	printf("Introduza o número de iterações. \n");
	scanf("%d", &NUM_IT);

	printf("Introduza o intervalo de tempo (em segundos) para análise pelo pai. \n");
	scanf("%d", &TIMER);

	printf("Introduza o Path do ficheiro de mensagens. Cada linha dará lugar a uma instância mensagem. \n");
	scanf("%s", path);

	return initialize(NUM_LP, NUM_MAQ, NUM_IT, TIMER, path, 0);

}