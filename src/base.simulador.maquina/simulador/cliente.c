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
#include <sys/socket.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <assert.h>

#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define TIMER_RESET 20
#define THAT_HELLO "HELLO"
#define HELLO_CODE 3
#define CONFIG_FILE 2
#define ACK_CODE 150 
#define NACK_CODE 151
#define CONNECTION_CONTROL 20
#define PROTOCOL_PREVENT 2
#define HASH_MAX_LENGTH 3
#define MENSAGEM_MAX_LENGHT 520
#define MAX_PATH_LENGTH 80
#define NUM_THREADS_MAX 50
#define SA struct sockaddr

#define LOG_FILE "log_file.txt"
#define SERVER_SSL_CERT_FILE "server.pem"
#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)

/* Variáveis globais para acesso facilitado */
typedef struct {
int numero_mensagens;
unsigned short id;
unsigned char* statusG;
SSL* sockfdG;
FILE *fp;
pthread_mutex_t	mutex_saver;
} variaveis_globais;

/* Estrutura para preencher o socket */
typedef struct {
    unsigned char versao;
    unsigned char codigo;
    unsigned short id;
    short tamanho;
    char mensagem[MENSAGEM_MAX_LENGHT];
} mensagem_troca;

/* Estrutura para efetuarmos a comunicação da melhor maneira */
typedef struct {
    char* mensagem_to_print;
} mensagem;

typedef struct {
    char path[MAX_PATH_LENGTH];
    char maquina[HASH_MAX_LENGTH];
    unsigned char id[PROTOCOL_PREVENT];
} parametros;

typedef struct {
    unsigned char id[2];
    unsigned char status;
} status;

typedef struct {
	unsigned char controlo;
	unsigned char rControlo;
	char* configFile;
} config;

typedef struct {
	char* ip;
	char* name;
	unsigned char* id;
	unsigned short idE;
	int porta;
	int portaServ;
	int portaUDP;
	int cadencia;
	unsigned char* status;
	config configFile;
} argumentos;

variaveis_globais* g;

/* Source: https://bit.ly/2ANT8C8 */ 
char* strdupM(const char *src) {
    size_t len = strlen(src) + 1;
    char *s = malloc(len);
    if (s == NULL)
        return NULL;
    return (char *)memcpy(s, src, len);
}

/* Função usada para escrever em um ficheiro de texto as mensagens não enviadas corretamente */
unsigned char logFile(const char* texto, FILE* fp) {

	return -1;
} 

int helloRequest(mensagem_troca request, unsigned short idE) {
	  request.versao = 0; request.codigo = 0; request.id = idE; strcpy(request.mensagem, THAT_HELLO); request.tamanho = strlen(request.mensagem);
	
    SSL_write(g->sockfdG, &request, sizeof(request));
		memset(&request, 0, sizeof(request)); 
    SSL_read(g->sockfdG, &request, sizeof(request));
    if(request.codigo == 151) {
        perror("[Cliente_Simulador] Ligação com o servidor rejeitada, por validação manual. (helloRequest)\n");
        return -1;
    }
		return 0;
}


/* Função para enviar a mensagem ao SCM
*  Source: https://bit.ly/2XBUI1B */
void* client_sender(void* arg) {
    mensagem* senderAux = (mensagem*) arg; mensagem_troca sender;
    sender.versao = 0; sender.codigo = 1; sender.id = g->id;
    strcpy(sender.mensagem,senderAux->mensagem_to_print); free(senderAux->mensagem_to_print); sender.tamanho = (short) strlen(sender.mensagem);
    char* log = strdupM(sender.mensagem);
		pthread_mutex_lock(&g->mutex_saver);
    int error = SSL_write(g->sockfdG, &sender, sizeof(sender));
    if(error < 0) {
        perror("[Cliente_Simulador] Erro a escrever a mensagem pretendida no socket (client_Sender)\n");
		if(g->fp) logFile(log,g->fp);
        pthread_exit((void*) 1);
    }
		puts(sender.mensagem);
    bzero(&sender.codigo, sizeof(sender.codigo));
    SSL_read(g->sockfdG, &sender, sizeof(sender));
    if(sender.codigo == 151) {
        puts("[Cliente_Simulador] O servidor rejeitou a mensagem. (client_Sender)");
		if(g->fp) logFile(log,g->fp);
        *g->statusG = 151;
        pthread_exit((void*) 1);
    } else {
        *g->statusG = 150;
    }
    pthread_mutex_unlock(&g->mutex_saver);
		free(log);
    pthread_exit((void*) NULL);
}

/* Importa todas as mensagens de uma máquina de um ficheiro 
*  Retorna num array de mensagens */
void* import_message(void* arg) {
    parametros* leitura = (parametros*) arg;
    int i = 0, k = 1, data_size = sizeof(mensagem);
    char *p; char delim[] = ";";
    char auxMenF[MENSAGEM_MAX_LENGHT]; char* auxMenD;
    mensagem* return_array = (mensagem*) malloc (data_size); void* tmp;
    if(!return_array) {
        perror("[Cliente_Simulador] Não foi possível realizar o malloc. (import_message)\n");
        pthread_exit((void*) 1);
    }
    g->fp=fopen(leitura->path, "r+");
    while(1) {
        if(feof(g->fp)) {
            break ;
        }
        fgets(auxMenF, sizeof(auxMenF), g->fp);
        auxMenD = strdupM(auxMenF); p = auxMenD; p[strlen(auxMenD) - 1] = 0;
        strtok(auxMenF, delim);

        /* Verificamos se a mensagem corresponde à máquina */
        if(!strncmp(auxMenF, (char*) leitura->id, strlen((char*) leitura->id))) {
						return_array[i].mensagem_to_print = strdupM(auxMenD);
            k++;
            i++;
            g->numero_mensagens++;
            tmp = (mensagem*) realloc(return_array, k*data_size);
            if(!tmp) {
                perror("[Cliente_Simulador] Não foi possível realizar o realloc (import_message).\n"); pthread_exit((void*) 1);
            } else {
                return_array = (mensagem*) tmp;
            }
        }
			bzero(auxMenF, MENSAGEM_MAX_LENGHT);
			free(auxMenD);
    } 
    fclose(g->fp);
    pthread_exit(return_array);
}

int controller(argumentos* first) {
    int n, err;
    char path[MAX_PATH_LENGTH]; char* configAux; char* configSaver = strdupM(first->configFile.configFile);
    pthread_t thread; parametros leitura;

    /*Pedimos o path do ficheiro */
    puts("[Cliente_Simulador] Introduza o path do ficheiro que contem as mensagems. (controller)");
    scanf("%s", path);
    if (strncmp("sair", path, 4) == 0) {
        puts("[Cliente_Simulador] Path igual a sair. Desligando o simulador. (controller)\n"); exit(1);
    } else {
        strcpy(leitura.path, path); strcpy((char*) leitura.id, (char*) first->id); g->numero_mensagens = 0;
        pthread_create(&thread, NULL, import_message, &leitura);
    }
    void* ret;
    err = pthread_join(thread, &ret);
    if(err < 0) {
        perror("[Cliente_Simulador] Não foi possível importar as mensagens (controller). \n");
        pthread_exit((void*) 1);
    }
    mensagem* todas_mensagens = (mensagem*) ret;
    pthread_t* threads = (pthread_t*) malloc(g->numero_mensagens * sizeof(pthread_t));
		g->fp = fopen(LOG_FILE, "w+");
		
		if(!g->fp) {
				perror("[Cliente_Simulador] Erro a criar/abrir o ficheiro de log de erros. As mensagens não enviadas não serão salvas. (controller)\n");
		}
		/* Inicializamos o mutex para controlar o socket */
		pthread_mutex_init(&g->mutex_saver, NULL);
    /*Ciclo para enviar as mensagens para o servidor */
    for(n=0; n<g->numero_mensagens; n++) {
				
					/* O rControlo (controlo de reset) para enviar a hello request a simular o reset do simulador 
					*  Colocamos o mesmo em primeiro, porque considerámos que o RESET tem prioridade sobre o CONFIG
					*/	
					if(first->configFile.rControlo == HELLO_CODE) {
							puts("[Cliente_Simulador] Efetuando pedido RESET. (controller)");
							sleep(TIMER_RESET);
							mensagem_troca* aux = malloc(sizeof(mensagem_troca));
							if(!aux) {
								perror("[Cliente_Simulador] Erro a alocar memória para criar a estrutura auxiliar de mensagem_troca. (controller)\n");
							} else {
									err = helloRequest(*aux, g->id);
								if(err < 0) {
									puts("[Cliente_Simulador] Não foi possível efetuar o pedido RESET. (controller)");
									first->configFile.rControlo = NACK_CODE;
									} else {
										puts("[Cliente_Simulador] Pedido RESET efetuado com sucesso. (controller)");
										first->configFile.rControlo = ACK_CODE;
									}
							}
					}

				/* O código de controlo 2 demonstra que o ficheiro já foi completamente recebido pelo servidor (UC1014) */
				if(first->configFile.controlo == CONFIG_FILE) {
							puts("[Cliente_Simulador] O simulador detetou um novo ficheiro de configurações. (controller)");
							configAux = strdupM(first->configFile.configFile);
							if(!configAux) {
							perror("[Cliente_Simulador] Erro a importar o novo ficheiro de configurações. O simulador irá continuar a rodar com o mesmo. (controller)");
							} else {
							free(configSaver);
							configSaver = configAux;
							first->configFile.controlo = 0;
							puts("[Cliente_Simulador] Ficheiro de configurações importado com sucesso. (controller)");
							}
						}

					pthread_create(&threads[n], NULL, client_sender, &todas_mensagens[n]);
      		sleep(first->cadencia);
				}

    /* Esperamos pela execução de todas as Threads. E destruímos o respetivo mutex */
    for(n=0; n<g->numero_mensagens; n++) {
        pthread_join(threads[n], NULL);
    }
		 pthread_mutex_destroy(&g->mutex_saver);

    /* Limpamos a HEAP */
		free(threads);
		free(configSaver);
  	free(todas_mensagens);
		fclose(g->fp);
		free(g);
    return 0;
}


void* rootS(void* arg) {
    argumentos* lista = (argumentos*) arg;

	char line[100];
    int sockfd; int r;
    struct sockaddr_in servaddr;

    /* Criamos o socket e fazemos a verificação padrão de erros */
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        perror("[Cliente_Simulador] Erro na criação do socket. (rootS)\n");
        pthread_exit((void*) 1);
    }

    /* bzero coloca todos os bytes a 0 */
    bzero(&servaddr, sizeof(servaddr));


    /* Atribuímos os respetivos dados para que a ligação se efetue */
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = inet_addr(lista->ip);
    servaddr.sin_port = htons(lista->porta);

    /* Conectamos o socket com o server */
    if (connect(sockfd, (SA*)&servaddr, sizeof(servaddr)) != 0) {
        perror("[Cliente_Simulador] Erro na criação da conexão. (rootS)\n");
        pthread_exit((void*) 1);
    }
    puts("[Cliente_Simulador] Criação e configuração do socket efetuada com sucesso. (rootS)");

    const SSL_METHOD *method=SSLv23_client_method();
    SSL_CTX *ctx = SSL_CTX_new(method);

    // Load client's certificate and key
	char* p; p = lista->name; p[strlen(lista->name) -1] = 0;
	bzero(line, sizeof(line));
    strcpy(line, lista->name);
    strcat(line,".pem");
    SSL_CTX_use_certificate_file(ctx, line, SSL_FILETYPE_PEM);
	bzero(line, sizeof(line));
    strcpy(line, lista->name);
    strcat(line,".key");
    SSL_CTX_use_PrivateKey_file(ctx, line, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx)) {
        perror("[Cliente_Simulador] Não foi possível carregar a Key &/| o Certificado. (rootS)\n");
        close(sockfd);
        pthread_exit((void*) 1);
    }
		free(lista->name);

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER,NULL);

    /* Consideramos que o certificado do certificado é confiável */
    SSL_CTX_load_verify_locations(ctx,SERVER_SSL_CERT_FILE,NULL);

    /* Limitamos a conexão TLS a determinas ciphers */
    SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    SSL *sslConn = SSL_new(ctx);
    SSL_set_fd(sslConn, sockfd);
    if(SSL_connect(sslConn)!= 1) {
        perror("[Cliente_Simulador] Não foi possível efetuar a ligação de TLS. (rootS)\n");
        SSL_free(sslConn);
        close(sockfd);
        pthread_exit((void*) 1);
    }

    if(SSL_get_verify_result(sslConn) != X509_V_OK) {
        perror("[Cliente_Simulador] O certificado do Server é inválido e/ou não existe. (rootS)\n");
        SSL_free(sslConn);
        close(sockfd);
        pthread_exit((void*) 1);
    }

    X509* cert=SSL_get_peer_certificate(sslConn);
    X509_free(cert);

    if(cert == NULL) {
        perror("[Cliente_Simulador] Sem certificado providenciado pelo Servidor. (rootS)\n");
        SSL_free(sslConn);
        close(sockfd);
        pthread_exit((void*) 1);
    }

    mensagem_troca* request = malloc(sizeof(mensagem_troca));
		g = (variaveis_globais*) malloc(sizeof(variaveis_globais));
		if(!g) {
			perror("[Cliente_Simulador] Erro a atribuir memória dinâmica para suportar as variáveis globais. Desligando o simulador. (rootS)\n");
			pthread_exit((void*) 1);
		}
    g->sockfdG = sslConn;

    /* Tentativa de contacto com o servidor */
		r = helloRequest(*request, lista->idE);
		if(r < 0) {
				puts("[Cliente_Simulador] Desligando o cliente do simulador. (rootS)");
				free(request);
		} else {
				free(request);
				g->statusG = lista->status;
				g->id = lista->idE;
    		controller(lista);
				puts("[Cliente_Simulador] Todas as mensagens foram enviadas. (rootS)");
		}

    SSL_free(g->sockfdG);
    close(sockfd);
    pthread_exit((void*) NULL);
}