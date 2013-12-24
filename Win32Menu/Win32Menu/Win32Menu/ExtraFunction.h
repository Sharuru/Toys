#pragma once
#include <windows.h>
class ExtraFunction
{
public:
	ExtraFunction();
	~ExtraFunction();

	//HANDLE thisConsole;
	CONSOLE_CURSOR_INFO cursor_info;

	void gotoXY(int x, int y);
};

