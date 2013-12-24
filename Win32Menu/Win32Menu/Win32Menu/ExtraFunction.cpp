#include "stdafx.h"
#include "ExtraFunction.h"
#include <windows.h>

ExtraFunction::ExtraFunction()
{
	//thisConsole = GetStdHandle(STD_OUTPUT_HANDLE);	//Get Handle
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

