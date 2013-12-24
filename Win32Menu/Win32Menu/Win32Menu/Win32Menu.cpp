// Win32Menu.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <windows.h>
#include <conio.h>

using namespace std;
void gotoXY(int x, int y);

int main(int argc, char* argv[])
{
	int x, y;
	char key;
	gotoXY(1, 2); cout << "-> " ;
	cout << "This is Menu 1." << endl 
	<< "     This is Menu 2." << endl 
	<< "     This is Menu 3." << endl 
	<< "     This is Menu 4." << endl 
	<< "     This is Menu 5." << endl;
	gotoXY(1, 2);	//Back to Top
	x = 1, y = 2;	//initialize the data
	while (1)	//Forever Loop
	{		
		_getch();	//Throw first four code		
		key = _getch();
		if (key == 0x48)	//Key UP
		{
			x = x - 1;
			if (x <= 1 )
			{
				x = 1;
			}
		}
		else if (key == 0x50)	//Key DOWN
		{
			x = x + 1;
			if (x >= 5)
			{
				x = 5;
			}
		}
		gotoXY(x - 1, y); cout << "   ";
		gotoXY(x, y); cout << "-> ";
		gotoXY(x + 1, y); cout << "   ";
	}			
}


void gotoXY(int x, int y)
{
	COORD coord;
	coord.X = y;
	coord.Y = x;
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), coord);
}