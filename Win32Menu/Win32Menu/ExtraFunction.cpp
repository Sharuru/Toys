#include "stdafx.h"
#include "ExtraFunction.h"
#include <windows.h>
#include <iostream>

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
	if (type == 0)	
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

void ExtraFunction::advPrint(int x, int y, char menu[], int type)
{
	gotoXY(x, y);
	setColor(type);
	cout << menu << endl;
	setColor(0);
}
