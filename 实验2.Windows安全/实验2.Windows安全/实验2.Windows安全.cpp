// 实验2.Windows安全.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <Windows.h>

using namespace std;

int modexp(int a, int b, int n);

int _tmain(int argc, _TCHAR* argv[])
{
	int item;
	system("title 实验2.Windows安全");
	cout << endl << " 实验2.Windows安全" << endl;
	while (1){
		cout << endl << " 请选择演示项目：" << endl;
		cout << endl << " [1] 快速求模幂";
		cout << endl << " [2] 快速求素数" << endl;
		cout << endl << " [*] 请选择：";
		cin >> item;
		switch (item){
		case 1:		//快速求模幂 a ^ b mod n
		{
			int a, b, n;
			cout << endl << " [*] 请分别输入式 a ^ b mod n 对应的变量值：" << endl;
			cout << " a = ";
			cin >> a;
			cout << " b = ";
			cin >> b;
			cout << " n = ";
			cin >> n;
			cout << endl << " [*] 计算得：" << a << " ^ " << b << " mod " << n << " = " <<
				modexp(a, b, n) << endl;
		}
		}
	}
	return 0;
}


//函数部分
int modexp(int a, int b, int n){
	int ret = 1;
	int tmp = a;
	while (b){
		//基数存在
		if (b & 0x1)
		ret = ret * tmp % n;
		tmp = tmp * tmp % n;
		b >>= 1;
	}
	return ret;
}
