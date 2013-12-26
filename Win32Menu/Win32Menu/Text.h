#pragma once
#include <string>
#include <iostream>

using namespace std;
class Text
{
public:
	Text();
	~Text();
	//int testInt[10] = { 1, 2, 3, 4 };
	//char string menu[7] = {" "};
	string a[0] = { "hello" };
		//int menu[7][100];
//	string menu[] = { "1", "2", "3", "4", "5", "6", "7" };
	//int menu[7][100; ]
	//Caution:Messy Code Problem would happen if you do not declare the first and the last area.
	 void initstr();
};
