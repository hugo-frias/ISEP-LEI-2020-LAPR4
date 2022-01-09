RCOMP 2019-2020 Project - Sprint 5 planning
===========================================
### Sprint master: 1180782 ###

# 1. Sprint's summary #

  The company Smart Shop Floor Management (SSFM) has a specialization in the management and production control areas of industrial units dedicated to different business areas. To take better advantage of the new business opportunities, they want to develop an information system capable of gathering all the data/messages generated by their equipment and also process them in order to:

  - control the production orders and their execution.
  - the management of the time of activity on the machines.
  - the management of the consumption of raw material.
  - the registry of the effective quantity of products produced.

  For this system we will need to develop 3 network applications:
  
  - A central system, related to the "core" system to develop. This system will send and receive messages from the machines.
  - Industrial machines and emulators, corresponding to a real industrial machine or to a software that simulates the general behavior of an industrial machine. These send messages to the central system and they also receive messages from the monitoring and central system.
  - The monitoring system, which is an integral part of the system which purpose is to monitor the state of the industrial machines or the emulatores. This system only send messages to the machines.

  In this sprint, at RCOMP level, we have to apply in some UCs SSL/TLS with authentication through public key certificates (UC 1013/1015). Also, we have to continue to use UDP/TCP communications and all implementations have to follow the Communication Protocol for this project.

# 2. Sprint's backlog #

* UC 1013: As the Project Manager, I want the communications between the SCM and the machines to be safe.
* UC 1014: As the Project Manager, I want the machine simulator to support the reception of configuration files.
* UC 1015: As the Project Manager, I want the communications between the machine simulator and the SCM to be safe.
* UC 1016: As the Project Manager, I want the machine simulator to support reset requests of it's state.
* UC 3010: As the Shop Floor Manager, I want to request the sending of a certain configuration to one machine.
* UC 6002: As the SMM, I want to send one reset request to a given machine.


# 3. Subtasks assignment #

  * 1170500 (Hugo Frias) - UC 1013 and UC 3010
  * 1180730 (Vera Pinto) - UC 1016
  * 1180782 (Diogo Ribeiro) - UC 6002
  * 1181628 (André Novo) - UC 1014 and UC 1015