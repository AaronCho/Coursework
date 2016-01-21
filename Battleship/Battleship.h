/*
------------------------------------------------------------------------------------
|						                                   |
|				     Battleship                                    |
|						                                   |
------------------------------------------------------------------------------------
|-seaBoard <array> BSIZE x BSIZE : Character				           |
|-hitsBoard <array> BSIZE x BSIZE: Character		                   	   |
|-playerName : String						                   |
|-numOfHitsTaken : Integer					                   |
------------------------------------------------------------------------------------
|+Battleship(playerName : String)					      	   |
|+setBoard() : Void					                 	   |
|+printBoards() : Void							           |
|+markHit(x:Integer, y:Integer, hitSuccessful:Boolean) : Void     	           |
|+markSea(x:Integer, y:Integer) : Void	                	                   |
|+attemptHit(x:Integer, y:Integer) : Boolean		                           |
|+generateHit(x:Integer, y:Integer) : Boolean	         	                   |
|+getName() : String						                   |
|+getHits() : Integer   				                           |
------------------------------------------------------------------------------------
*/

#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>

using namespace std;

const int BSIZE = 6;
const int BTLSHP_SIZE = 4;

class Battleship {
private:
	char seaBoard[BSIZE][BSIZE];
	char hitsBoard[BSIZE][BSIZE];
	string playerName;
	int numOfHitsTaken;
public:
	Battleship(string);
	void setBoard();
	void printBoards();
	void markHit(int, int, bool);
	void markSea(int, int);
	bool attemptHit(int, int);
	bool generateHit(int&, int&);
	string getName();
	int getHits();

};
