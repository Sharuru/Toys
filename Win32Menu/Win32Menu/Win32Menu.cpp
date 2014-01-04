// Win32Menu.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <iomanip>
#include <string>
#include <windows.h>
#include <conio.h>
#include "ExtraFunction.h"
#include "Text.h"

using namespace std;

int main(int argc, char* argv[])
{
	ExtraFunction extra;
	Text text;
	int x, y;
	char key;
	/*	Program Start	*/
	cout << "Use UP and DOWN to choose, RIGHT to confirm, LEFT to exit." << endl;
	cout << "If you have input blocking problem, please hit SPACE.";
	//extra.advPrint(4, 3, text.obj[1], 0, 1);
	extra.advPrint(4, 6, text.menu[1], 1, 1);
	cout << setw(21) << text.menu[2] << endl
	     << setw(32) << text.menu[3] << endl
	     << setw(21) << text.menu[4] << endl
         << setw(21) << text.menu[5] << endl;
	x = 1, y = 2;	//initialize the data
	MainScr:
	while (1)	//Forever Loop
	{
		x = extra.itemChooser(x,3,5,2,text.menu);
		extra.gotoXY(15, 5); cout << "You are now choosing: " << x << "   Flag = " << extra.flag;
		if (extra.flag == 1)
		{
			extra.setColor(2);
			extra.gotoXY(16, 5); cout << "You Choosed: " << x;
			extra.setColor(0);
			if (x == 3)
			{
				goto Sub3;
			}
		}
		else if (extra.flag == 0)
		{
			extra.advPrint(17, 5, "You ask Exit", 3, 0);
		}
	}
	/*	Sub Menu	*/
	Sub3:
	extra.advPrint(5, 55, "This is Sub Menu of Menu 3", 2, 0);
	while (1)
	{
		_getch();
		key = _getch();
		if (key == 0x4B)
		{
			extra.advPrint(5, 55, "                                  ", 2, 0);		//Clean Screen when Back
			goto MainScr;
		}
	}
	getchar();
	return 0;
}