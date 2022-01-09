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

#define MESSAGES_MAX_LENGTH 80
#define HASH_MAX_LENGTH 30
#define PATH_MAX_LENGTH 100

int messages_number;
int fd_write;

typedef struct {
	char hash[HASH_MAX_LENGTH];
	pthread_mutex_t	mutex_saver;
} maquina;

typedef struct {
	maquina* linha;
	int size;
} linha_producao;

typedef struct {
	char hash[HASH_MAX_LENGTH];
	maquina* maquina;
	char message_to_print[MESSAGES_MAX_LENGTH];
} message;

typedef struct {
	int fd[2];
	int mensagens;
	int TIMER;
} system_controll;

/* To generate random hashes
* Source: https://bit.ly/2LDzBq3*/

void gen_random(char *s, const int len) {
    static const char alphanum[] =
        "0123456789"
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        "abcdefghijklmnopqrstuvwxyz";

    for (int i = 0; i < len; ++i) {
        s[i] = alphanum[rand() % (sizeof(alphanum) - 1)];
    }

    s[len] = 0;
}

/* To import a single line of a file 
* Source: https://bit.ly/2XdHrvJ */

int get_line(FILE *fp, char *buffer, size_t buflen)
{
    char *end = buffer + buflen - 1; /* Allow space for null terminator */
    char *dst = buffer;
    int c;
    while ((c = getc(fp)) != EOF && c != '\n' && dst < end)
        *dst++ = c;
    *dst = '\0';
    return((c == EOF && dst == buffer) ? EOF : dst - buffer);
}

/* Function that will creates instances of messages per line of a file */

void* import_message(void* arg) {
	int i = 0, k = 1, data_size = sizeof(message);
	char* path = (char*) arg;
	     message* return_array = (message*) malloc (data_size); message* tmp;
			 if(!return_array) {
				 perror("Não foi possível realizar o malloc. \n");
				 exit(1);
			 }
        FILE *fp=fopen(path, "r");
        while(1) {
            if(feof(fp)) {
                break ;
            }
						gen_random(return_array[i].hash, sizeof(return_array[i].hash));
						get_line(fp, return_array[i].message_to_print, sizeof(return_array[i].message_to_print));
						k++;
						i++;
						 tmp = (message*) realloc(return_array, k*data_size);
						if(!tmp) {
							perror("Não foi possível realizar o realloc (importação de mensagens).\n"); pthread_exit(NULL);
						} else {
							return_array = tmp;
							messages_number++;
						}
        }
        fclose(fp);
				
				pthread_exit(return_array);
}

/* The same machine can't produce two message in one single moment .
* We don't use normal lock, cause we don't need to save that message.
* Forum says that two messages produced by the same machine in the same moment are the same message.
*/

void* print_message(void* arg){
		int error;
		message* shared_data = (message*) arg;
		pthread_mutex_trylock(&shared_data->maquina->mutex_saver);
		char mensagem[HASH_MAX_LENGTH * 2 + MESSAGES_MAX_LENGTH * 2];
		sprintf(mensagem, "Identificador da máquina: %s ; Identificador da mensagem: %s ; Mensagem: %s \n", shared_data->maquina->hash,shared_data->hash, shared_data->message_to_print);
		error = write(fd_write, mensagem, sizeof(mensagem));
		if(error < 0) {
		perror("Erro a escrever a mensagem (print_message). \n");
		pthread_exit(NULL);
	}
		pthread_mutex_unlock(&shared_data->maquina->mutex_saver);
		pthread_exit((void*) NULL);
}

/* The main function that will simulate the SPM */

void main_system(system_controll* godfather) {
	int i, number, error;
	error = close(godfather->fd[1]);
	if(error < 0) {
		perror("Erro a fechar o file descriptor de escrita (main_system). \n");
		pthread_exit(NULL);
	}
	sleep(godfather->TIMER);
	number = godfather->mensagens;
	char mensagem[HASH_MAX_LENGTH * 2 + MESSAGES_MAX_LENGTH * 2];
	printf("Mensagens analisadas: %d. Mensagens: \n", number);
	for(i=0; i<number; i++) {
		error = read(godfather->fd[0], mensagem, sizeof(mensagem)); 
	if(error < 0) {
		perror("Erro a ler a mensagem. \n");
		pthread_exit(NULL);
	} else {
		printf("Mensagem %d: %s. \n", (i+1), mensagem);
	}
	}
	godfather->mensagens=0;
	error = close(godfather->fd[0]);
	if(error < 0) {
		perror("Erro a fechar o file descriptor de leitura (main_system). \n");
		pthread_exit(NULL);
	}
	exit(0);
	}

/* Function to test the creation of message's instances
* It is a manual test (requires user attention) */


void test_function (char* path) {
	pthread_t thread; void* ret;
	messages_number = 0;
	pthread_create(&thread, NULL, import_message, path);
	int error = pthread_join(thread, &ret);
	if(error != 0) {
		perror("Não foi possível importar as mensagens. \n");
		exit(1);
	} else {
			int i;
			message* all_messages = (message*) ret;
			for(i=0; i<messages_number; i++) {
				printf("%s \n", all_messages[i].message_to_print);
			}
			short answer;
			printf("Confirma que a importação foi bem realizada? (0 - não) (1 - sim) \n");
			scanf("%hu", &answer);

			if(answer != 0) {
			 printf("Importação de mensagens bem sucedida. \n");
			 exit(0);
			}
	}
	printf("Importação de mensagens mal sucedida. \n");
	exit(1);
}

/* Function that will controll all the others function in the Controller class */

int initialize (int NUM_LP, int NUM_MAQ, int NUM_IT, int TIMER, char* path, char test) {

if(test != 0) {
	test_function(path);
}

int i, j, nLP, nM, error, controll = 0, data_size = sizeof(linha_producao);
	pthread_t threads[NUM_IT+1]; 

	int fd_aux = shm_open("/shm_1011", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	if (fd_aux < 0) {
		perror("Erro no shm_open. \n");
		exit(1);
	}

  error = ftruncate(fd_aux, sizeof(system_controll));
	if(error < 0) {
		perror("Erro no ftruncate. \n");
		exit(1);
	}

  system_controll *godfather = (system_controll*) mmap(NULL, data_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd_aux, 0);
  if (godfather == NULL) exit(1);

	godfather->TIMER=TIMER;
	godfather->mensagens=0;
	error = pipe(godfather->fd);
	if (error < 0) {
		perror("Erro na criação do pipe (initialize). \n");
		exit(1);
	}
	fd_write = godfather->fd[1];
	pid_t pid_saver = fork();
	if (pid_saver == 0) {
		main_system(godfather);
		exit(0);
	}

	printf("File descriptor (initialize): %d \n", fd_write);
	close(godfather->fd[0]);

	linha_producao *shared_data = (linha_producao*) malloc(data_size * NUM_MAQ);

	if(!shared_data) {
		perror("Não foi possível realizar o malloc inicial. \n"); exit(1);
	}
	
	/* We generate random LP and random MAQ */
	for(i=0; i<NUM_LP; i++) {
		shared_data[i].linha = (maquina*) malloc(sizeof(maquina)*NUM_MAQ);
		if(!shared_data[i].linha) {
			perror("Não foi possível alocal as máquinas dentro das linhas (initialize). \n");
			exit(1);
		}
		for(j=0; j<NUM_MAQ; j++) {
			gen_random(shared_data[i].linha[j].hash, HASH_MAX_LENGTH);
			error = pthread_mutex_init(&shared_data[i].linha[j].mutex_saver, NULL);
			if(error < 0) {
				perror("Não foi possível inicializar o mutex (initialize). \n");
				exit(1);
			}
		}
		shared_data[i].size = NUM_MAQ;
	}
	
	void* ret;
	pthread_create(&threads[controll], NULL, import_message, path);
	error = pthread_join(threads[controll], &ret);
	if(error != 0) {
		perror("Não foi possível importar as mensagens (initialize). \n");
		exit(1);
	} else {
		controll++;
	}

	message* all_messages = (message*) ret;

	for(i=0; i<messages_number; i++) {
		srand(i);
		nLP = rand() % NUM_LP;
		nM = rand() % NUM_MAQ;
		all_messages[i].maquina= (maquina*) &shared_data[nLP].linha[nM];
		error = pthread_create(&threads[controll], NULL, print_message, &all_messages[i]);
		if(error < 0 ) {
			perror("Não foi possível criar a thread (initialize). \n");
			exit(1);
		}
		godfather->mensagens++;
		controll++;
	}

	/* We wait for all the threads */
	for(i=0; i<controll; i++) {
			pthread_join(threads[i], NULL);
	}

	/* We wait for the execution of the main_system (child) */
	int status;
	error = waitpid(pid_saver, &status, 0);
	if (error < 0) {
		perror("Erro no waitpid (initialize). \n");
		exit(1);
	}

	error = close(godfather->fd[1]);
	if(error < 0) {
		perror("Erro a fechar o file descriptor de escrita (initialize). \n");
		exit(1);
	}

	error = munmap(godfather, sizeof(system_controll));
  if (error < 0) {
		perror("Erro no munmap (initialize). \n");
		exit(1);
	}
  error = close(fd_aux);
  if (error < 0) {
		perror("Erro no close (initialize). \n");
		exit(1);
	}
  error = shm_unlink("/shm_1011");
  if (error < 0) {
		perror("Erro no shm_unlink (initialize). \n");
		exit(1);
	}

	for(i=0; i<NUM_LP; i++) {
		free(shared_data[i].linha);
	}

	free(shared_data); free(ret);
	return 0;
}