// 实验1.古典加密设计.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <stdlib.h>
#include <iostream>
#include <string>
#include <ctype.h>

using namespace std;

string Encrypt(string P, char key);
string unEncrypt(string C, char key);

int _tmain(int argc, _TCHAR* argv[])
{
	string P, C, key;
	system("title 实验1.古典密码设计");
	cout << endl << " 实验1.古典密码设计" << endl;
	cout << endl << " [*] 请输入明文：";
	cin >> P;
	while (true){
		cout << endl << " [*] 请输入加密密钥（单个小写英文字符）：";
		cin >> key;
		if (key.length() != 1){
			cout << endl << " [*] 加密密钥仅能为单个小写英文字符！" << endl;
		}
		else{
			if (isalpha(key[0]) != 2){
				cout << endl << " [*] 加密密钥仅能为单个小写英文字符！" << endl;
			}
			else{
				break;
			}
		}
	}
	C = Encrypt(P, key[0]);
	cout << endl << " [*] 密文为：" << C << endl;
	P = unEncrypt(C, key[0]);
	cout << endl << " [*] 解密后明文为：" << P << endl;
	getchar();
	getchar();
	return 0;
}

string Encrypt(string P, char key){
	string C;
	for (int i = 0; i < int(P.length()); i++){
		if (int(P[i] >= 65 && P[i] <= 90)){
			cout << "Big";
			if ((P[i] + int(key) - 96) > 90){
				C += P[i] + (int(key) - 96) - 26;
			}
			else{
				C += P[i] + (int(key) - 96);
			}
		}
		else{
			cout << "small";
			if ((P[i] + int(key) - 96) > 122){
				C += P[i] + (int(key) - 96) - 26;
			}
			else{
				C += P[i] + (int(key) - 96);
			}

		}
// 		if (int(P[i]) > 96 && (P[i] + int(key) - 96) > 122){
// 			C += P[i] + (int(key) - 96) - 26;
// 		}
// 		else if (int(P[i] < 97) && (P[i] + int(key) - 96) > 90){
// 			C += P[i] + (int(key)-96) - 26;
// 		}
// 		else{
// 			C += P[i] + (int(key) - 96);
// 		}
	}
	return C;
}

string unEncrypt(string C, char key){
	string P;
	for (int i = 0; i < int(C.length()); i++){
		if (int(C[i] > 96) && (C[i] - (int(key) - 96)) < 97){
			P += C[i] - (int(key) - 96) + 26;
		}
		else{
			P += C[i] - (int(key) - 96);
		}
	}
	return P;
}
