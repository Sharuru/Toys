#pragma once
#include <windows.h>
#include <string>
#include "Text.h"

using namespace std; 

class ExtraFunction
{
public:
	ExtraFunction();
	~ExtraFunction();
	
	HANDLE thisConsole;
	CONSOLE_CURSOR_INFO cursor_info;

	char key;
	int flag;

	Text text;

	const char UP = 0x48;
	const char DOWN = 0x50;
	const char LEFT = 0x4B;
	const char RIGHT = 0x4D;

	void gotoXY(int x, int y);
	void setColor(int type);
	void advPrint(int x, int y, string text,int type,int line);
	int itemChooser(int x, int y, int maxItem, int fix, string item[]);
	void screenCleaner(int x, int xArea, int y, int yArea,int type);
};

