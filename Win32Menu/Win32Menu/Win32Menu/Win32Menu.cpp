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
#define RegularColor "BACKGROUND_RED | BACKGROUND_GREEN |BACKGROUND_BLUE "
int main(int argc, char* argv[])
{
	HANDLE thisConsole;
	thisConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	ExtraFunction extra;
	int x, y;
	char key;
	/*	Program Start	*/
	SetConsoleTextAttribute(thisConsole, BACKGROUND_RED | BACKGROUND_GREEN | BACKGROUND_BLUE);
	cout << "Use UP and DOWN to choose, RIGHT to confirm, Left to exit." ;
	extra.gotoXY(1, 2); cout << "-> " ;
	cout << "This is Menu 1." << endl 
	<< "     This is Menu 2." << endl 
	<< "     This is Menu 3." << endl 
	<< "     This is Menu 4." << endl 
	<< "     This is Menu 5." << endl;
	x = 1, y = 2;	//initialize the data
	while (1)	//Forever Loop
	{		
		_getch();	//Throw first four code		
		key = _getch();
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
			extra.gotoXY(9, 5); cout << "You Choosed" << x;
		}
		else if (key == LEFT)	//Key LEFT
		{
			extra.gotoXY(8, 5);	cout << "You ask Exit";
		}
		extra.gotoXY(x - 1, y); cout << "   ";
		extra.gotoXY(x, y); cout << "-> ";
		extra.gotoXY(x + 1, y); cout << "   ";
		extra.gotoXY(10, 5); cout << "You are now choosing: " << x;
	}			
}
