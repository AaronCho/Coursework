#include "Battleship.h"

int main() {
	int menuSelection;
	int playerNumOfHits;
	int computerNumOfHits;
	int xcoor;
	int ycoor;
	int xrand;
	int yrand;
	bool playerAttempt;
	bool computerAttempt;
	srand((unsigned int)time(NULL));

	Battleship player("Aaron");
	Battleship computer("Computer");

	cout << "Welcome to Battleship!" << endl;

	do {
		playerNumOfHits = player.getHits();
		computerNumOfHits = computer.getHits();
		cout << "\nBattleship Menu:" << endl
			<< "1. Print Your Boards" << endl
			<< "2. Print The Computer's Boards (No Peeking!)" << endl
			<< "3. Take a Turn" << endl
			<< "4. Quit" << endl << endl;
		cin >> menuSelection;

		if (menuSelection == 1) {
			player.printBoards();
		}
		else if (menuSelection == 2) {
			computer.printBoards();
		}
		else if (menuSelection == 3) {
			cout << "\nWhat x coordinate do you want to attack? (0 - 5)" << endl;
			cin >> xcoor;
			cout << "\nWhat y coordinate do you want to attack? (0 - 5)" << endl;
			cin >> ycoor;

			playerAttempt = computer.attemptHit(xcoor, ycoor);  //player turn
			computer.markSea(xcoor, ycoor);
			player.markHit(xcoor, ycoor, playerAttempt);
			if (playerAttempt) {
				cout << "\nYou hit the Computer's Battleship!" << endl;
			}
			else {
				cout << "\nMiss!" << endl;
			}

			computerAttempt = player.generateHit(xrand, yrand);  

			if (computerAttempt) {
				cout << "\nThe Computer hit your Battleship!" << endl;
			}
			else {
				cout << "\nThe Computer Missed!" << endl;
			}
			player.markSea(xrand, yrand); 
			computer.markHit(xrand, yrand, computerAttempt);
			playerNumOfHits = player.getHits();
			computerNumOfHits = computer.getHits();


		}
		else if (menuSelection == 4) {
			cout << "\nHave a good day!" << endl;
		}

		if (playerNumOfHits >= BTLSHP_SIZE) {
			cout << "\nThe computer sank my battleship!" << endl;
		}

		if (computerNumOfHits >= BTLSHP_SIZE) {
			cout << "\nYou sank the computer's battleship!" << endl;
		}

	} while (menuSelection != 4 && playerNumOfHits < BTLSHP_SIZE && computerNumOfHits < BTLSHP_SIZE);


	system("pause");
	return 0;
}
