#pragma once
#include <windows.h>
#include <string>

using namespace std; 

class ExtraFunction
{
private:
	string test[20];
public:
	ExtraFunction();
	~ExtraFunction();
	
	HANDLE thisConsole;
	CONSOLE_CURSOR_INFO cursor_info;

	//string abc[] = { "213", "123" };
	void gotoXY(int x, int y);
	void setColor(int type);
	void advPrint(int x, int y, string menu,int type,int line);
};

