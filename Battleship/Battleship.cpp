#include "Battleship.h"

Battleship::Battleship(string pname) {
	playerName = pname;
	setBoard();
}

void Battleship::setBoard() {
	numOfHitsTaken = 0;
	int randRow;
	int randColumn;
	int randRowOrColumn;

	for (int row = 0; row < BSIZE; row++) {  //initialize seaBoard to Empty '-'.
		for (int column = 0; column < BSIZE; column++) {
			seaBoard[row][column] = '-';
		}
	}

	for (int row = 0; row < BSIZE; row++) { //initialize hitsBoard to Empty '-'.
		for (int column = 0; column < BSIZE; column++) {
			hitsBoard[row][column] = '-';
		}
	}

	randRow = rand() % 6;					//set up ship in seaBoard
	randColumn = rand() % 6;
	seaBoard[randRow][randColumn] = 'B';

	randRowOrColumn = rand() % 2;
	if (randRowOrColumn == 0) {
		if (randRow < 3) {
			for (int i = 0; i < 3; i++) {
				randRow = randRow + 1;
				seaBoard[randRow][randColumn] = 'B';
			}
		}
		else if (randRow > 2) {
			for (int i = 0; i < 3; i++) {
				randRow = randRow - 1;
				seaBoard[randRow][randColumn] = 'B';
			}
		}
	}
	else if (randRowOrColumn == 1) {
		if (randColumn < 3) {
			for (int i = 0; i < 3; i++) {
				randColumn = randColumn + 1;
				seaBoard[randRow][randColumn] = 'B';
			}
		}
		else if (randColumn > 2) {
			for (int i = 0; i < 3; i++) {
				randColumn = randColumn - 1;
				seaBoard[randRow][randColumn] = 'B';
			}
		}
	}


}

void Battleship::printBoards() {
	cout << "\nHits Board:" << endl; //Print Hits Board
	for (int row = 0; row < BSIZE; row++) {
		cout << endl;
		for (int column = 0; column < BSIZE; column++) {
			cout << " " << hitsBoard[row][column];
		}
	}
	cout << endl;

	cout << "\nSea Board:" << endl;  //Print Sea Board
	for (int row = 0; row < BSIZE; row++) {
		cout << endl;
		for (int column = 0; column < BSIZE; column++) {
			cout << " " << seaBoard[row][column];
		}
	}
	cout << endl;
}

void Battleship::markHit(int x, int y, bool hitSuccessful) {
	if (hitSuccessful) {
		hitsBoard[x][y] = 'H';
	}
	else {
		hitsBoard[x][y] = 'M';
	}

}

void Battleship::markSea(int x, int y) {
	if (seaBoard[x][y] == 'B') {
		seaBoard[x][y] = 'b';
		numOfHitsTaken++;
	}
	else if (seaBoard[x][y] = '-') {
		seaBoard[x][y] = 'm';
	}
}

bool Battleship::attemptHit(int x, int y) {
	if (seaBoard[x][y] == 'B') {
		return true;
	}

	else {
		return false;
	}
}

bool Battleship::generateHit(int &x, int &y) {
	bool attemptHitResult;
	x = rand() % 6;
	y = rand() % 6;
	while (hitsBoard[x][y] == 'H' || hitsBoard[x][y] == 'M') {
		x = rand() % 6;
		y = rand() % 6;
	}

	attemptHitResult = attemptHit(x, y);

	return attemptHitResult;
}

string Battleship::getName() {
	return playerName;
}

int Battleship::getHits() {
	return numOfHitsTaken;
}
