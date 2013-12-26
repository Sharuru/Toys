// Win32Menu.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <windows.h>
#include <conio.h>
#include "ExtraFunction.h"

using namespace std;

#define UP 0x48
#define DOWN 0x50
#define LEFT 0x4B
#define RIGHT 0x4D

string obj[2] = { "  ", "->" };
string menu[7] = { "  ", "This is Menu 1.", "This is Menu 2.", "This is Menu 3.", "This is Menu 4.", "This is Menu 5.", "  " };
//Caution:Messy Code Problem would happen if you do not declare the first and the last area when you do not use advPrint to print info

int main(int argc, char* argv[])
{
	ExtraFunction extra;
	int x, y;
	char key;
	cout << extra.a[0];
	/*	Program Start	*/
	cout << "Use UP and DOWN to choose, RIGHT to confirm, LEFT to exit.";
	extra.advPrint(2, 2, obj[1], 0);
	extra.advPrint(2, 5, menu[1], 1);
	extra.advPrint(3, 5, menu[2], 0);
	extra.advPrint(4, 5, menu[3], 0);
	extra.advPrint(5, 5, menu[4], 0);
	extra.advPrint(6, 5, menu[5], 0);
	x = 1, y = 2;	//initialize the data
	while (1)	//Forever Loop
	{
		_getch();	//Throw first four code		
		key = _getch();
		//	extra.gotoXY(17, 5); cout << "                   ";	//Clean Exit Notice For Debug
		//	extra.gotoXY(16, 5); cout << "                                 ";	//Clean Choosed Notice For Debug
		if (key == UP)	//Key UP
		{
			x = x - 1;
			if (x <= 1)		//Zone Control
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
			extra.gotoXY(16, 5); cout << "You Choosed: " << x;
			extra.setColor(0);
		}
		else if (key == LEFT)	//Key LEFT
		{
			extra.setColor(3);
			extra.gotoXY(17, 5); cout << "You ask Exit";
			extra.setColor(0);
		}
		extra.advPrint(x, 2, obj[0], 0); 
		extra.advPrint(x, 5, menu[x - 1], 0);
		//extra.gotoXY(x, 2); cout << "   " << menu[x - 1];
		extra.advPrint(x + 1, 2, obj[1], 0);
		//extra.gotoXY(x + 1, 2); cout << "-> ";
		extra.advPrint(x + 1, 5, menu[x],1);
		//extra.setColor(1); cout << menu[x]; extra.setColor(0);
		extra.advPrint(x+2, 2, obj[0], 0);
		extra.advPrint(x+2, 5, menu[x + 1], 0);
		//extra.gotoXY(x + 2, 2); cout << "   " << menu[x + 1];
		extra.gotoXY(15, 5); cout << "You are now choosing: " << x;
	}
}