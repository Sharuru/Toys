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
	system("mode con cols=100 lines=20"); 
	ExtraFunction extra;
	Text text;
	int x;
	/*	Program Start	*/
	cout << "Use UP and DOWN to choose, RIGHT to confirm, LEFT to exit." << endl;
	cout << "If you have input blocking problem, please hit SPACE.";
	//extra.advPrint(4, 3, text.obj[1], 0, 1);
	extra.advPrint(4, 6, text.menu[1], 1, 1);
	cout << setw(21) << text.menu[2] << endl
		 << setw(32) << text.menu[3] << endl
		 << setw(21) << text.menu[4] << endl
		 << setw(21) << text.menu[5] << endl;
	x = 1;	//initialize the data
MainScr:
	while (1)	//Forever Loop
	{
		x = extra.itemChooser(x, 3, 5, 2, text.menu);
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
	extra.advPrint(7, 58, text.submenu[1], 1, 1);
	extra.advPrint(8, 58, text.submenu[2], 0, 1);
	extra.advPrint(9, 58, text.submenu[3], 0, 1);
	x = 1;	//initialize the data
	while (1)
	{
		x = extra.itemChooser(x, 55, 3, 5, text.submenu);
		if (extra.flag == 0)
		{
			extra.screenCleaner(55, 27, 5, 5, 0);
			x = 3;
			goto MainScr;
		}
	} 
	return 0;
}