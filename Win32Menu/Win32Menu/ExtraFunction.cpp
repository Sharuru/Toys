#include "stdafx.h"
#include "ExtraFunction.h"
#include <windows.h>
#include <iostream>
#include <string>
#include <conio.h>

using namespace std;

ExtraFunction::ExtraFunction()
{
	thisConsole = GetStdHandle(STD_OUTPUT_HANDLE);	//Get Handle
	/*	Set Cursor Info	*/
	cursor_info.bVisible = false;	//Hide the Cursor 
	cursor_info.dwSize = 20;	//Cursor Width 
	SetConsoleCursorInfo(thisConsole, &cursor_info);
}

ExtraFunction::~ExtraFunction()
{
}

void ExtraFunction::gotoXY(int x, int y)
{
	COORD coord;
	coord.X = y;
	coord.Y = x;
	SetConsoleCursorPosition(thisConsole, coord);
}

void ExtraFunction::setColor(int type)
{
	if (type == 0)	//Classic
	{
		SetConsoleTextAttribute(thisConsole, FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_GREEN );
	}		
	else if (type == 1)	//Choose
	{
		SetConsoleTextAttribute(thisConsole, FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_INTENSITY | BACKGROUND_BLUE | BACKGROUND_INTENSITY);
	}	
	else if (type == 2)	//Notice
	{
		SetConsoleTextAttribute(thisConsole, FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_INTENSITY );
	}	
	else if (type == 3)	//Warning
	{
		SetConsoleTextAttribute(thisConsole, FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_INTENSITY | BACKGROUND_RED);
	}
	else
	{
		;
	}
}

void ExtraFunction::advPrint(int x, int y, string text, int type,int line)
{
	gotoXY(x, y);
	setColor(type);
	if (line == 1)
	{
		cout << text << endl;
	}
	else if (line == 0)
	{
		cout << text;
	}
	setColor(0);
}

int ExtraFunction::itemChooser(int x, int y, int maxItem, int fix, string item[])
{
	flag = 2;	//initialize the data
	_getch();	//Throw first four code		
	key = _getch();
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
		if (x >= maxItem)		//Zone Control
		{
			x = maxItem;
		}
	}
	else if (key == RIGHT)	//Key RIGHT
	{
		flag = 1;
	}
	else if (key == LEFT)	//Key LEFT
	{
		flag = 0;
	}
	gotoXY(x + fix, y); cout << text.obj[2] << item[x - 1];
	//advPrint(x + fix + 1, y, text.obj[1], 0, 1);
	advPrint(x + fix + 1, y + 3, item[x], 1, 1);
	gotoXY(x + fix + 2, y); cout << text.obj[2] << item[x + 1];
	return x;
}