#ifndef CLIENTE_H_
#define CLIENTE_H_

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

char* strdupM(const char *src);
void* rootS (void* arg);

#endif