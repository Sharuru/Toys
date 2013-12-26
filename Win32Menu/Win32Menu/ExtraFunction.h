#pragma once
#include <windows.h>
#include <string>

using namespace std; 

class ExtraFunction
{
public:
	ExtraFunction();
	~ExtraFunction();
	
	HANDLE thisConsole;
	CONSOLE_CURSOR_INFO cursor_info;

	string a = { "a" };
	void gotoXY(int x, int y);
	void setColor(int type);
	void advPrint(int x, int y, string menu,int type);
};

