#include "stdafx.h"
#include "Text.h"
#include <string>

using namespace std;

Text::Text()
{
	initText();
}

Text::~Text()
{
}

void Text::initText()
{
	//Caution:Messy Code Problem would happen if you do not declare the first and the last area
	obj[0] = "  ";
	obj[1] = "->";
	obj[2] = "   ";
	menu[0] = "  ";
	menu[1] = "This is Menu 1.";
	menu[2] = "This is Menu 2.";
	menu[3] = "This is Menu 3. [Sub Menu]";
	menu[4] = "This is Menu 4.";
	menu[5] = "This is Menu 5.";
	menu[6] = "  ";
}