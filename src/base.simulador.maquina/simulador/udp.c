#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h>
#include <pthread.h>
#include "cliente.h"

#define MENSAGEM_MAX_LENGHT 500
#define ACK_CODE 150
#define NACK_CODE 151
#define HELLO_CODE 0
#define RESET_CODE 3
#define HELLO_REQUEST 3
#define BIG_TIMER 30
#define SMALL_TIMER 10
#define MAX_TRIES 5

/* Estrutura para efetuar a comunicação com o SMM */
typedef struct {
    unsigned char versao;
    unsigned char codigo;
    unsigned short id;
    short tamanho;
    char mensagem[MENSAGEM_MAX_LENGHT];
} mensagem_troca;

/* Source: https://bit.ly/2UDL7Xw */
void* rootUDP (void* arg) {
	argumentos* lista = (argumentos*) arg;
    int op, i, sockfd; ssize_t nB;
    unsigned char controlo = 0;
    struct sockaddr_in servaddr, cliaddr; 
      
    /*Criamos o FD para o Socket */
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0) { 
        perror("[Servidor_UDP] Erro na criação do socket e atribuição do file descriptor. (rootUDP)\n"); 
        exit(EXIT_FAILURE); 
    } 
    
	/* Colocamos todos os bytes das estruturas inicialmente a 0 */
    memset(&servaddr, 0, sizeof(servaddr)); 
    memset(&cliaddr, 0, sizeof(cliaddr)); 
      
    /* Atribuímos a informação do servidor à estrutura correspondente */
    servaddr.sin_family    = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY; 
    servaddr.sin_port = htons(lista->portaUDP);
      
    /* Fazemos o BIND o socket */
    if (bind(sockfd, (const struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) { 
        perror("[Servidor_UDP] Erro a fazer bind do socket. (rootUDP)\n");
        exit(EXIT_FAILURE); 
    } 
      
		unsigned int len = sizeof(cliaddr);
		/* Não usamos memória dinâmica para alocar o receiver. Porque o servidor UDP apenas irá ser desligado com CTRL + C */
		mensagem_troca receiver;
		lista->configFile.rControlo = 0;

		puts("[Servidor_UDP] O servidor UDP está apto a receber pedidos/requests. (rootUDP)");

	for(;;) {
		nB = recvfrom(sockfd, &receiver, sizeof(mensagem_troca), MSG_WAITALL, (struct sockaddr *) &cliaddr,&len);
		if(nB < 0) {
			perror("[Servidor_UDP] Erro a ler o socket de UDP. (rootUDP)\n");
			break;
		}
		
		op = (int) receiver.codigo;
			switch (op) {
				case HELLO_CODE:
					puts("[Servidor_UDP] Pedido HELLO recebido. (rootUDP)");
					memset(receiver.mensagem, 0, strlen(receiver.mensagem)); receiver.codigo = *(unsigned char*) lista->status; op = (int) receiver.codigo; receiver.id = lista->idE; strcpy(receiver.mensagem, "Estado da máquina."); receiver.tamanho = (short) strlen(receiver.mensagem); sendto(sockfd, &receiver, sizeof(mensagem_troca), MSG_CONFIRM, (const struct sockaddr *) &cliaddr, len);
					if(op == ACK_CODE) {
						controlo = 1;
						puts("[Servidor_UDP] Resposta ao HELLO: ACK. (rootUDP)");
					} else {
						puts("[Servidor_UDP] Resposta ao HELLO: NACK. (rootUDP)");
					}
				break;
				
				case RESET_CODE:
				puts("[Servidor_UDP] Pedido RESET recebido. (rootUDP)");
				if(controlo == 0) {
					puts("[Servidor_UDP] Pedido RESET recebido sem pedido HELLO prévio. (rootUDP)");
					memset(receiver.mensagem, 0, strlen(receiver.mensagem)); receiver.codigo = NACK_CODE; receiver.id = lista->idE; strcpy(receiver.mensagem, "Pedido HELLO não realizado e/ou sem ACK prévio."); receiver.tamanho = (short) strlen(receiver.mensagem); sendto(sockfd, &receiver, sizeof(mensagem_troca), MSG_CONFIRM, (const struct sockaddr *) &cliaddr, len);
					break;
				}
					lista->configFile.rControlo = HELLO_REQUEST; sleep(BIG_TIMER);
					for(i=0; i<MAX_TRIES; i++) {
							if(lista->configFile.rControlo != HELLO_REQUEST) {
								op = (int) lista->configFile.rControlo;
								switch(op) {
									case ACK_CODE:
									
									memset(receiver.mensagem, 0, strlen(receiver.mensagem)); receiver.codigo = ACK_CODE; receiver.id = lista->idE; strcpy(receiver.mensagem, "ACK"); receiver.tamanho = (short) strlen(receiver.mensagem); controlo = 0; sendto(sockfd, &receiver, sizeof(mensagem_troca), MSG_CONFIRM, (const struct sockaddr *) &cliaddr, len);
									puts("[Servidor_UDP] Estado de máquina ACK enviado. (rootUDP)");
									break;

									case NACK_CODE:

									memset(receiver.mensagem, 0, strlen(receiver.mensagem)); receiver.codigo = NACK_CODE; receiver.id = lista->idE; strcpy(receiver.mensagem, "NACK"); receiver.tamanho = (short) strlen(receiver.mensagem); 
									sendto(sockfd, &receiver, sizeof(mensagem_troca), MSG_CONFIRM, (const struct sockaddr *) &cliaddr, len);
									puts("[Servidor_UDP] Estado de máquina NACK enviado. (rootUDP)");
									break;

									default:
									break;
								}
								break;
							}
							sleep(SMALL_TIMER);
					}
					if(i == MAX_TRIES) {
							puts("[Servidor_UDP] Estado de máquina predifinido (NACK) enviado. (rootUDP)");
							receiver.codigo = NACK_CODE; memset(receiver.mensagem, 0, strlen(receiver.mensagem)); strcpy(receiver.mensagem, "PredNACK"); receiver.tamanho = (short) strlen(receiver.mensagem);
							sendto(sockfd, &receiver, sizeof(mensagem_troca), MSG_CONFIRM, (const struct sockaddr *) &cliaddr, len);
					}
				default:
				break;
			}
			/* Colocamos os bytes a 0 como forma de prevenção ao cast */
			memset(&op, 0, sizeof(op));
		}
	
	puts("[Servidor_UDP] Desligando o servidor UDP. Obrigado. (rootUDP)");
    close(sockfd);
    return 0; 
} 