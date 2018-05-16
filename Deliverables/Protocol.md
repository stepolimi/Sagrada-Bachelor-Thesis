## PROTOCOL


Our protocol for **RMI**  and **Socket** is the same. the difference is the way used to pass commands to server/client.
*For RMI*, communication is defined by an array called action[] in which are placed every command referred to the single action of player. 


for example the client login : 

	action[]=("Login", nickname)  
	
	action[0]=“login”: is the main command;
	
	action[1]=“nickname”: is the second command that contain the nickname. every command depends on the previous.


*For Socket*, communication is defined by the same commands, but, instead of arrays, there is a String which is the concatenation of every commands separated by a “-“.
***For example the client login is “login-nickname”.***
After, control part takes care of the string separation in every commands.



Our protocol is :

## for Client—>Server



- Login action —> action[1]=(“Login”, nickname);

- Logout action—>action[1]=(“Logout”, nickname);

- Dice Select —> action[*]=(“PickDice”, **“DiceSpace”, indexOf);**

					     or “RoundTrack”, roundNumber, indexOfArray);
							 
					     or “DiceBag”);
							 
					     or“Schema”, indexRows, indexColumns); 
					      
					     *next command passed depends if action[1] is a 	dicebag/rountrack/schema/dicespace

- Dice Place —> action[*]=(“PlaceDice”, **“DiceSpace”);**

					      	 or “RoundTrack”, roundNumber, indexOfArray);
					      
					      	 or “DiceBag”);
							 
					      	 or “Schema”, indexRows, indexColumns); 
							 

- Dice Extract—> action[0]=(“ExtractDice”);


- Use of ToolCard—> action[1]=(“UseCard”, numberOfTool);


- Declare end of turn —> action[0]=(“EndTurn”);




## for Server—>Client

- Login action —> action[1]=(“Login”, “Welcome”/“Login_error”);
							


- Pass set of Schemas —>action[4]=(“Schemas”, numofSchema1, numofSchema2, numofSchema3, numofSchema4);
(that player chooses) 


- Dice Select —> action[1]=(“PickDice”, “Successfull”/“Unsuccessfull”);
				    
							


- Dice Place —> action[1]=(“PlaceDice”, “Successfull”/"Unsuccessfull");
					
							

- Dice Extract—> action[2]=(“ExtractDice”, List< numberDice >, List< colourDice >);


- Use of ToolCard —> action[1]=(“UseCard”, “Successfull”/"Unsuccessfull");
					
							