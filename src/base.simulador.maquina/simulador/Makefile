# A Makefile with variables and suffix rules
# variaveis

# Testes: T3 10.8.0.80 9991 3 T3 client1

INCLUDES = cliente.h servidor.h udp.h
SOURCES =  cliente.c servidor.c udp.c main.c
OBJFILES =   cliente.o servidor.o udp.o main.o
EXEC = prog_simulador

# Suffix rules
.SUFFIXES : .c .o
.SUFFIXES : .o .s

# How to build an object .o from a code file .c ; $< -- file name
.c.o:

	gcc -Wall -g -c $<
	

${EXEC}: ${OBJFILES}
	gcc -Wall -g -o ${EXEC} ${OBJFILES} -lpthread -lssl -lcrypto

${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}

config:
	rm -rf config.txt ; rm -rf db_status.txt ; cp 123.txt config.txt ; cp 321.txt db_status.txt

clean:
	rm -f ${OBJFILES} ${EXEC}
