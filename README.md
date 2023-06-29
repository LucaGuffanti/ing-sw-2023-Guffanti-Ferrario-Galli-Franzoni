# MyShelfie board game

<img src="https://www.craniocreations.it/storage/media/products/54/112/My_Shelfie_box_ITA-ENG.png" width=192px height=192 px align="right" />

MyShelfie Board Game is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2022/2023).

**Teacher** : San Pietro Pierluigi

**Final Score**: not yet evaluated

## Project specification
The project consists of a Java version of the board game *My Shelfie*, made by Cranio Creations.

You can find the full game [here](https://www.craniocreations.it/prodotto/my-shelfie).

The final version includes:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

<!--
## Find out more

| **[Installation][installation-link]**     | **[Compiling][compiling-link]**     |    **[Running][running-link]**       | **[Javadocs][javadocs]** | **[Troubleshooting][troubleshooting-link]**
|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|
| [![i1][installation-image]][installation-link] | [![i2][compiling-image]][compiling-link] | [![i4][running-image]][running-link] | [![i3][javadocs-image]][javadocs] | [![i5][troubleshooting-image]][troubleshooting-link]
-->

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules |✅|
| Complete rules |✅|
| Socket |✅|
| RMI |✅|
| GUI |✅|
| CLI |✅|
| Chat|✅|
| Persistence |✅|
| Multiple games | ⛔|
| Disconnection resilience | ⛔|

#### Legend
[⛔]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[✅]() Implemented

## Usage
**Running the game on Windows**
After cloning the repo, start the server app through the relative Jar file. Now run the client app through the relative Jar. Both Jar's are located in`/deliverables/final/jar` folder.  



```bash
git clone  https://github.com/LucaGuffanti/ing-sw-2023-Guffanti-Ferrario-Galli-Franzoni
cd /ing-sw-2023-Guffanti-Ferrario-Galli-Franzoni/deliverables/final/jar
java -jar PSP17-server.jar
java -jar PSP17-client.jar
```



**Compiling the game using IntelliJ Idea**
To correctly compile the apps, firstly copy the copyrighted game assets folders into the `src/main/resources/images` folder. Then compile through Maven.

** Notes **
These Jars are built to run in a windows enviroment, however the game can be compiled and played on both Linux and MacOs systems. The minimum display resolution to correctly run the game on GUI is 1920x1080.

## Test cases

**Coverage criteria: code lines.**

| Package |Tested Class | Coverage |
|:-----------------------|:------------------|:------------------------------------:|
| Controller | Global server package | 352/406 (86%)
| Controller | Turn managing package | 73/82 (89%)
| Model | Global Package | 919/1017 (90%)
| Model | Game | 129/133 (96%)


## The Team
* [Luca Guffanti](https://github.com/LucaGuffanti)
* [Daniele Ferrario](https://github.com/Ferraah)
* [Marco Galli](https://github.com/Me-P-eM)
* [Davide Franzoni](https://github.com/elfr4nz0)


## Software used
**Diagrams.net** - sequence diagrams

**StarUML** - UML diagrams

**Intellij IDEA Ultimate** - main IDE 

## Copyright and license

My Shelfie is copyrighted 2023.
