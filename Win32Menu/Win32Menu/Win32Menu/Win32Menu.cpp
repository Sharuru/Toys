// Win32Menu.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <windows.h>
#include <conio.h>
#include "ExtraFunction.h"

using namespace std;

#define UP 0x48
#define DOWN 0x50
#define LEFT 0x4B
#define RIGHT 0x4D

char menu[5][100] = { "This is Menu 1.", "This is Menu 2.", "This is Menu 3. ", "This is Menu 4.", "This is Menu 5." };

int main(int argc, char* argv[])
{
	ExtraFunction extra;
	int x, y;
	char key;
	/*	Program Start	*/
	cout << "Use UP and DOWN to choose, RIGHT to confirm, Left to exit." ;
	extra.gotoXY(2, 2); cout << "-> " ;
	extra.setColor(1);
	cout << menu[0] << endl;
	extra.setColor(0); cout << "     "<< menu[1] << endl << "     " << menu[2] << endl
							<< "     "<< menu[3] << endl << "     " << menu[4] << endl;
	x = 1, y = 2;	//initialize the data
	while (1)	//Forever Loop
	{		
		_getch();	//Throw first four code		
		key = _getch();
		extra.gotoXY(8, 5); cout << "                   ";	//Clean Exit Notice For Debug
		extra.gotoXY(9, 5); cout << "                                 ";	//Clean Choosed Notice For Debug
		if (key == UP)	//Key UP
		{
			x = x - 1;
			if (x <= 1 )	//Zone Control
			{
				x = 1;
			}
		}
		else if (key == DOWN)	//Key DOWN
		{
			x = x + 1;
			if (x >= 5)		//Zone Control
			{
				x = 5;
			}
		}
		else if (key == RIGHT)	//Key RIGHT
		{
			extra.setColor(2);
			extra.gotoXY(9, 5); cout << "You Choosed: " << x;
			extra.setColor(0);
		}
		else if (key == LEFT)	//Key LEFT
		{
			extra.setColor(3);
			extra.gotoXY(8, 5);	cout << "You ask Exit";
			extra.setColor(0);
		}
		extra.gotoXY(x, y); cout << "   "; cout << menu[x - 2];
		extra.gotoXY(x + 1, y); cout << "-> ";
		extra.setColor(1); cout << menu[x - 1]; extra.setColor(0);
		extra.gotoXY(x + 2, y); cout << "   "; cout << menu[x];
		extra.gotoXY(10, 5); cout << "You are now choosing: " << x;
	}			
}
