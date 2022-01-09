#include <stdio.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <strings.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <pthread.h>

#include "servidor.h"
#include "cliente.h"
#include "udp.h"

#define PATH_STATUS "db_status.txt"
#define ACK_STS "ack"
#define ACK_CODE 150
#define NACK_STS "nack"
#define NACK_CODE 151
#define PATH_CONFIG "config.txt"
#define MAX_LENGTH_ARGS 150
#define PROTOCOL_PREVENT 10

/* Funçao para importar a configuração inicial da máquina */
int importConfig (void* arg) {
argumentos* aux = (argumentos*) arg;
char line [150]; char* err;
FILE* fp = fopen(PATH_CONFIG, "r+");
while(1) {
	if(feof(fp)) {
		break;
	}
	err = fgets(line, sizeof(line), fp);
	if(!err) {
		return -1;
	}
	aux->configFile.configFile = (char*) realloc(aux->configFile.configFile, sizeof(aux->configFile.configFile) + strlen(line));
	if(!aux->configFile.configFile) {
		return -1;
	}
	strcat(aux->configFile.configFile, line);
}
return 0;
}

/* Importa o status inicial da máquina */
void* import_status (void* arg) {
		argumentos* lista = (argumentos*) arg;
    char auxMenF[PROTOCOL_PREVENT]; char delim[] = ";"; char* auxMenD; char* p;
		status* returnx = (status*) malloc(sizeof(status));
		
		if(!returnx) {
				perror("[Main] Erro a alocar memória para guardar o status inicial da máquina. (import_status)\n");
				pthread_exit((void*) NULL);
		}
		
    FILE* fp = fopen(PATH_STATUS, "r+");
    while(1) {
        if(feof(fp)) {
            break ;
        }
        fgets(auxMenF, sizeof(auxMenF), fp);
        auxMenD = strdupM(auxMenF); p = auxMenD; p[strlen(auxMenD) - 1] = 0;
        p = strtok(auxMenF, delim);

        if(!strncmp(auxMenF, (char*) lista->id, strlen((char*) lista->id))) {
            p = strtok(NULL, delim); strcpy((char*) returnx->id, (char*) lista->id);
					if(!strncmp(p, ACK_STS, strlen(ACK_STS))) {
			       returnx->status = ACK_CODE;
					} else if(!strncmp(p, NACK_STS, strlen(NACK_STS))) {
			    returnx->status = NACK_CODE;
				}
        }
          memset(&auxMenF, 0, sizeof(auxMenF));
				free(auxMenD);
    }
    fclose(fp);
    pthread_exit(returnx);
}

/* Função para incrementar o array de threads já com o cast */
pthread_t* aumentarArray(pthread_t* arr, size_t novoTamanho) {
 pthread_t* tmp = (pthread_t*) realloc(arr, novoTamanho);
 if(!tmp) {
	 perror("[Main] Não foi possível alocar espaço para novas Threads. (aumentarArray)\n");
	 return NULL;
 }
 return tmp;
}

/* Função principal do programa */
int main () {
    pthread_t* threads = (pthread_t*) malloc(sizeof(pthread_t));
    argumentos* auxiliar; status* shared;	void* ret;
    int i, op, err, threadC = 1;

    printf("[Main] Selecione a opção. \n 1 - Simulador de máquina. \n 2 - Sair \n");
    scanf("%d", &op);

    if(op < 0) {
        puts("[Main] A opção não pode ser negativa. Desligando o programa, obrigado. (main)");
        exit(1);
    }
    
    switch(op) {
            case 0:
                op = -1;
            break;
            case 1:
				puts("");
				char line[MAX_LENGTH_ARGS]; char delim[] = " ";

				void* tmp = malloc(sizeof(argumentos));
				if(!tmp) {
					perror("[Main] Erro na criação da estrutura de argumentos. Desligando, obrigado. (main)\n"); exit(1);
				} else {
					auxiliar = (argumentos*) tmp;
				}

			puts("<MAQUINA> <IP> <PORTA SCM> <CADENCIA> <ID EXTERNO> <PORTA SERVIDOR SIMULADOR> <PORTA UDP> <NOME CERTIFICADO>");
			/* Pedimos os argumentos ao Gestor de Projeto */
			printf("[Main] Introduza, por favor, os argumentos para o simulador: ");
			//scanf("%d *[^\n]", &i);
			char c; scanf("%c ", &c);
			fgets (line, sizeof(line), stdin);

			char* ptr = strtok(line, delim);
			auxiliar->id = (unsigned char*) strdupM(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->ip = strdupM(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->porta = atoi(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->cadencia = atoi(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->idE = (unsigned short) atoi(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->portaServ = atoi(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->portaUDP = atoi(ptr);
			ptr = strtok(NULL, delim);
			auxiliar->name = strdupM(ptr);
			
		  /* Importamos o status inicial da máquina */
			/* Usamos uma thread apenas pelo conhecimento adquirido, uma vez que esperamos pela mesma, acabaria por ser desncessária */
    	err = pthread_create(&threads[threadC - 1], NULL, import_status, &auxiliar);
			if(err < 0) {
				perror("[Main] Erro a criar a Thread para importar o status inicial. (main)\n");
				break;
			} else {
				pthread_join(threads[threadC-1], &ret);
				shared = (status*) ret;
				if(!shared) {
				puts("[Main] Erro na importação de mensagens. (main)\n");
				break;
			}
			}	
		
			auxiliar->status = &shared->status;

			auxiliar->configFile.controlo = 0; auxiliar->configFile.configFile = (char*) malloc(PROTOCOL_PREVENT);
			err = importConfig(auxiliar->configFile.configFile);

			if(err < 0) {
				perror("[Main] Erro a importar a configuração inicial para o simulador. (main)\n");
				break;
			}

			/* Criamos a Thread para rodar o cliente do simulador */
			err = pthread_create(&threads[threadC - 1], NULL, rootS, auxiliar);
			
			if(err < 0) {
				perror("[Main] Erro a criar a Thread para rodar o servidor. (main)\n");
				exit(1);
			}

      threadC++;
			threads = aumentarArray(threads, threadC * sizeof(pthread_t));
      if(!threadC) {
				break;
			}

			/* Criamor a Thread para rodar o servidor do simulador */
			err = pthread_create(&threads[threadC - 1], NULL, rootCS, auxiliar);
			
			if(err < 0) {
				perror("[Main] Erro a criar a Thread para rodar o servidor TCP. (main)\n");
				break;
			}

			threadC++;
			threads = aumentarArray(threads, threadC * sizeof(pthread_t));
      if(!threadC) {
				break;
			}

			err = pthread_create(&threads[threadC - 1], NULL, rootUDP, auxiliar);
			if(err < 0) {
				perror("[Main] Erro a criar a Thread para rodar o servidor UDP. (main)\n");
				break;
			}

				break;
            case 2:
								op = -1;
                break;
            default:
						op = -1;
					puts("\n[Main] Opção desconhecida. (main)\n");
				break;
        }

    for(i=0; i<threadC-1; i++) {
        pthread_join(threads[i], NULL);
    }

	puts("[Main] Desligando o programa. Obrigado pela utilização. (main)\n");

	/*Limpamos a HEAP (com todos os seus constituintes individuais) */
	if(op != -1) {
		free(auxiliar->id);
		free(auxiliar->ip);
		free(auxiliar->name);
		free(auxiliar->configFile.configFile);
		free(auxiliar);
		free(ret);
	}
    free(threads);
    return 0;
	}