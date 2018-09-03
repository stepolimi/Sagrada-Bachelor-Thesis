

**Group 19 [10525479 BAGARIN STEFANO - 10490724 BANFI STEFANO ALESSANDRO - 10527150 BRESCIANI MATTEO]**

From a **Cranio Game:registered:**: 

![Screenshot](src/main/resources/assets/Screenshoot/title.png) 

For more information about original boarder game visit http://www.craniocreations.it/prodotto/sagrada/
Or read [Game's Rule (ITA)](/Rules.pdf)

## Instruction for Running jar 

To run Client or Server application follow this statement:

- Browse in the terminal until your position is in *Deliverables* directory using:
 ```
 cd Directory
 ...
 cd sagrada_familia
 cd Deliverables
 ```

- Then use the the following command to open Server or Client application:

```
cd Client
java -jar Client.jar
 ```
 or 
 ```
cd Server
java -jar Server.jar

 ```

## Function implemeted on project

### Rules      <img align="right" width="100" height="100" src = src/main/resources/assets/Screenshoot/tool-cards.png>
- [x] Complete Rules: We've implemented all 12 ToolCard. 

### Networking:  
- [x] **RMI**;  <img align="center" width="30" height="30" src = src/main/resources/assets/image/rmi.png> 
- [x] **Socket**;  <img align="center" width="30" height="30" src = src/main/resources/assets/image/socket.png> 

### View Interface:
- [x] Graphic User Interface; (**GUI**)
- [x] Command Line Interface; (**CLI**)

 You can choose one of them as soon as Application starts. 

### Advanced functionality: <img align="right" width="200" height="150" src = src/main/resources/assets/Screenshoot/schemaEditor.png>
<img align="right" width="200" height="150" src = src/main/resources/assets/Screenshoot/schema.png>

- [x] **Dynamic Schema Card**: application builds schema (where client plays) run-time. It was added also a Schema Editor, in which every player can, before playing, built his own schema with a simple drag and drop action. This editor should be used to import new Schema that Cranio could realease with new version of Sagrada

- [x] **Multiple Games**: Server can gesture more than one game at the same moment. So if you're more than 4 players don't worry! You can play anyway in 2 or more different match concurrently.

### Other functionality:

- use of Lambda Expression of Java 8 (Functional)


