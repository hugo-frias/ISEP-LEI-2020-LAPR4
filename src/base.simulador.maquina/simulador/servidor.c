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
#include <assert.h>
#include <pthread.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <openssl/crypto.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include "cliente.h"

#define PATH "db_maquinas.txt"
#define PATH_CONFIG "config.txt"
#define MENSAGEM_MAX_LENGHT 512
#define VERIFY_MAX_LENGTH 2
#define HELLO_CODE 0
#define CONFIG_CODE 2
#define SIZE_CODE 16
#define STILL_IMPORT 1
#define FINNISH_IMPORT 2
#define ACK_CODE 150
#define NACK_CODE 151
#define SA struct sockaddr
#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)
#define SERVER_SSL_CERT_FILE "server.pem"
#define SERVER_SSL_KEY_FILE "server.key"
#define AUTH_CLIENTS_SSL_CERTS_FILE "authentic-clients.pem"

typedef struct {
SSL_CTX* globalCTX;
int connfdG;
argumentos* lista;
pthread_mutex_t mutexFile;
} variaveis_globais;

typedef struct {
    unsigned char versao;
    unsigned char codigo;
    unsigned short id;
    short tamanho;
    char mensagem[MENSAGEM_MAX_LENGHT];
} mensagem_troca;

variaveis_globais* dei;

/* Acrescentamos memória para suportar as novas mensagens de configuração */
int adicionarLog(char* arr, const char* texto) {
	size_t arrLen = strlen(arr);
	size_t textLen = strlen(texto);
	char* tmp = strdupM(arr);
	arr = (char*) malloc(arrLen + textLen);
	if(!arr) {
		perror("[Servidor_Simulador] Erro a alocar memória para novos Logs. O simulador não irá importar esta configuração na sessão atual. (adicionarLog)\n");
		return -1;
	}
	if(arrLen == 0) {
		strcpy(arr, texto);
	} else {
		strcat(arr, texto);
	}
	free(tmp);
	return 0;
}

/* Função para comunicar com o cliente */
void* controloServidor(void* arg)  {
		int connfdG = *(int*) arg;

    int erro;
    mensagem_troca receiver; mensagem_troca sender;
	
    SSL *sslConn = SSL_new(dei->globalCTX);
    SSL_set_fd(sslConn, connfdG);
	
	
    if(SSL_accept(sslConn) != 1) {
        puts("[Servidor_Simulador] SCM inválido. Certificado não conhecido pelo servidor. Desligando a conexão. (controloServidor)\n");
        SSL_free(sslConn);
        close(connfdG);
        pthread_exit((void*) 1);
    }

    X509* cert=SSL_get_peer_certificate(sslConn);
    X509_free(cert);
	
    /* Se a primeira mensagem for vazia, a ligação é imediatamente terminada */
    erro = SSL_read(sslConn, &receiver, MENSAGEM_MAX_LENGHT);
    sender.versao = 0; sender.id = dei->lista->idE;
    if(erro == 0 || strlen(receiver.mensagem) == 0) {
        printf("[Servidor_Simulador] Desligando a conexão com o SCM. Mensagem vazia. (controloServidor)\n");
        sender.codigo = NACK_CODE;  sprintf(sender.mensagem, "%s", "Mensagem vazia."); sender.tamanho = (short) strlen(sender.mensagem);
        SSL_write(sslConn, &sender, sizeof(sender));
				pthread_exit((void*) 1);
    } else if(receiver.codigo != HELLO_CODE) {
			printf("[Servidor_Simulador] Desligando a conexão com o SCM. Código inicial diferente de 0. (controloServidor)\n");
			sender.codigo = NACK_CODE;  sprintf(sender.mensagem, "%s", "Código inicial diferente de 0."); sender.tamanho = (short) strlen(sender.mensagem);
        SSL_write(sslConn, &sender, sizeof(sender));
				pthread_exit((void*) 1);
		} else if(receiver.id != dei->lista->idE) {
			printf("[Servidor_Simulador] Pedido HELLO recebido por parte do servidor do simulador: %d. ID da máquina não corresponde ao do simulador.(controloServidor)\n", receiver.id);
			sender.codigo = NACK_CODE;  sprintf(sender.mensagem, "%s", "NACK!"); sender.tamanho = (short) strlen(sender.mensagem);
      SSL_write(sslConn, &sender, sizeof(sender));
		}

			printf("[Servidor_Simulador] Pedido HELLO recebido (e aceite) por parte do servidor do simulador: %d. (controloServidor)\n", receiver.id);
			sender.codigo = ACK_CODE;  sprintf(sender.mensagem, "%s", "OK!"); sender.tamanho = (short) strlen(sender.mensagem);
      SSL_write(sslConn, &sender, sizeof(sender));
		
		FILE *fp; int n = 0;
		fp = fopen(PATH_CONFIG, "w+");

    /* Loop infinito -> Comunicação aprovada anteriormente */
    for(;;) {
        SSL_read(sslConn, &receiver, sizeof(receiver));
        /* Caso a mensagem contenha "exit" ou "sair", a conexão é desligada */
        if (strncmp(receiver.mensagem, "exit", 4) == 0 || strncmp(receiver.mensagem, "sair", 4) == 0) {
            printf("[Servidor_Simulador] Mensagem com conteúdo \"exit\" ou \"sair\". Desligando a conexão. (controloServidor)\n");
						sender.codigo = ACK_CODE; sprintf(sender.mensagem, "%s", "OK! Desligando a conexão."); sender.tamanho = (short) strlen(sender.mensagem);
            SSL_write(sslConn, &sender, sizeof(sender));
            break;
        }

        if(strlen(receiver.mensagem) > 0 && strlen(receiver.mensagem) == SIZE_CODE && receiver.codigo == CONFIG_CODE && receiver.id == dei->lista->idE) {
						pthread_mutex_lock(&dei->mutexFile);
						fprintf(fp, "%s", receiver.mensagem);
						pthread_mutex_unlock(&dei->mutexFile);

						if(dei->lista->configFile.controlo == 0) {
							bzero(dei->lista->configFile.configFile, strlen(dei->lista->configFile.configFile));
							dei->lista->configFile.controlo = STILL_IMPORT;
						}

						erro = adicionarLog(dei->lista->configFile.configFile, receiver.mensagem);
						if(erro < 0) {
							perror("[Servidor_Simulador] Erro a escrever na memória o socket com o conteúdo de configuração. (controloServidor)\n");
							break;
						}
            sender.codigo = ACK_CODE;  sprintf(sender.mensagem, "%s", "OK!"); sender.tamanho = (short) strlen(sender.mensagem);
      			SSL_write(sslConn, &sender, sizeof(sender));
						
						n++;
						break;
        } else {
					printf("[Servidor_Simulador] Desligando a conexão com a máquina: %d. Mensagem incorreta e/ou o ID da máquina não corresponde ao do simulador. (controloServidor)\n", receiver.id);
        	sender.codigo = NACK_CODE;  sprintf(sender.mensagem, "%s", "Mensagem inválida."); sender.tamanho = (short) strlen(sender.mensagem);
        	SSL_write(sslConn, &sender, sizeof(sender));
					break;
				}
        /* Limpamos o array de chars do receiver */
				memset(&receiver.mensagem, 0, sizeof(receiver.mensagem));
    }
		dei->lista->configFile.controlo = FINNISH_IMPORT;
		fclose(fp);
		if(n>0) printf("[Servidor_Simulador] Ficheiro de configuração importado com sucesso. Dividido em %d partes. (controloServidor)\n", n);
    SSL_free(sslConn);
    close(connfdG);
    pthread_exit((void*) NULL);
}

/* Função Guia */
void* rootCS (void* arg) {
		argumentos* lista = (argumentos*) arg; dei = malloc(sizeof(variaveis_globais));

    int err, sockfd, connfd;
    unsigned int len;
    struct sockaddr_in servaddr, cli;
    pthread_t thread;

		 /*
     * >> Configuração TCP <<
     */

    /* Criação do socket e validação padrão */
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        perror ("[Servidor_Simulador] Erro na criação do socket. Desligando. (rootCS)\n");
        pthread_exit((void*) 1);
    }
    else {
        printf("[Servidor_Simulador] Criação do socket efetuada com sucesso. (rootCS)\n");
        bzero(&servaddr, sizeof(servaddr));
    }

    /* Atribuímos o IP e a Porta ao Socket */
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servaddr.sin_port = htons(lista->portaServ);

    /* Configurando o socket para os dados do servidor */
    if ((bind(sockfd, (SA*)&servaddr, sizeof(servaddr))) != 0) {
        perror("[Servidor_Simulador] Erro na configuração do servidor. Desligando. (rootCS)\n");
        pthread_exit((void*) 1);
    }
    printf("[Servidor_Simulador] Configuração estabelecida. (rootCS)\n");

    /* Tentativa de comunicação com o cliente */
    if ((listen(sockfd, 5)) != 0) {
        perror("[Servidor_Simulador] Tentativa de conexão falhou. Desligando. (rootCS)\n");
        pthread_exit((void*) 1);
    }

    /*
     * >> Configuração SSL <<
     */

    const SSL_METHOD* method;
    SSL_CTX* ctx;

    method = SSLv23_server_method();
    ctx = SSL_CTX_new(method);

    /* Carregamos o certificado e a key do servidor */
    SSL_CTX_use_certificate_file(ctx, SERVER_SSL_CERT_FILE, SSL_FILETYPE_PEM);
    SSL_CTX_use_PrivateKey_file(ctx, SERVER_SSL_KEY_FILE, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx)) {
        perror("[Servidor_Simulador] Não foi possível carregar o certificado e a key do servidor. (rootCS)\n");
        close(sockfd);
        pthread_exit((void*) 1);
    }

    /* Importamos o ficheiro com os certificados dos clientes que o servidor conhece */
    SSL_CTX_load_verify_locations(ctx,AUTH_CLIENTS_SSL_CERTS_FILE,NULL);

    /* Restringimos a conexão TLS */
    SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    /* O cliente deve disponibilizar um certificado conhecido pelo servidor, caso contrário o "aperto de mão" irá falhar */
    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);


    puts("[Servidor_Simulador] Servidor apto a receber pedidos/requests. (rootCS)");
    len = (unsigned int) sizeof(cli);

    dei->globalCTX = ctx; dei->lista = lista; pthread_mutex_init(&dei->mutexFile, NULL);
	
    for(;;) {
        connfd = accept(sockfd, (SA*)&cli, &len);
        if (connfd < 0) {
            perror("[Servidor_Simulador] Erro na aceitação da comunicação com o cliente. (rootCS)\n");
						break;

        } else if (cli.sin_addr.s_addr != inet_addr(lista->ip)) {
						perror("[Servidor_Simulador] O endereço IP do SCM não é o mesmo do pedido HELLO. Desligando o servidor do simulador. (rootCS)\n");
						break;

				}
        puts("[Servidor_Simulador] Ligação com o SCM estabelecida com sucesso. (rootCS)");
        err = pthread_create(&thread, NULL, controloServidor, &connfd);
        if(err < 0) {
            perror("[Servidor_Simulador] Erro na criação da thread. (Server_Main). (rootCS)\n");
						break;

        }
    }


		pthread_mutex_destroy(&dei->mutexFile);
		free(dei);
    pthread_exit((void*) NULL);
}