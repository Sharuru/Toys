// ʵ��2.Windows��ȫ.cpp : �������̨Ӧ�ó������ڵ㡣
//

#include "stdafx.h"
#include <iostream>
#include <Windows.h>

using namespace std;

int modexp(int a, int b, int n);
bool primejud(int n);

int _tmain(int argc, _TCHAR* argv[])
{
	int item;
	system("title ʵ��2.Windows��ȫ");
	cout << endl << " ʵ��2.Windows��ȫ" << endl;
	while (1){
		cout << endl << " ��ѡ����ʾ��Ŀ��" << endl;
		cout << endl << " [1] ������ģ��";
		cout << endl << " [2] ����������" << endl;
		cout << endl << " [*] ��ѡ��";
		cin >> item;
		switch (item){
		case 1:		//������ģ�� a ^ b mod n
		{
			int a, b, n;
			cout << endl << " [*] ��ֱ�����ʽ a ^ b mod n ��Ӧ�ı���ֵ��" << endl;
			cout << " a = ";
			cin >> a;
			cout << " b = ";
			cin >> b;
			cout << " n = ";
			cin >> n;
			cout << endl << " [*] ����ã�" << a << " ^ " << b << " mod " << n << " = " <<
				modexp(a, b, n) << endl;
		}break;
		case 2:		//����������
		{
			int n;
			cout << endl << " [*] ��������Ҫ�жϵ�����ֵ��" << endl;
			cout << " n = ";
			cin >> n;
			primejud(n) == true ? cout << endl << " [*] " << n << "������" << endl : cout << endl << " [*] " << n << "��������";
			cout << endl;
		}break;
		default:
		{
			cout << endl << " [*] ѡ�����" << endl;
		}
		}
	}
	return 0;
}


//��������
int modexp(int a, int b, int n){
	int ret = 1;
	int tmp = a;
	while (b){
		//��������
		if (b & 0x1)
		ret = ret * tmp % n;
		tmp = tmp * tmp % n;
		b >>= 1;
	}
	return ret;
}

bool primejud(int n){
	for (int loop = 2; loop <= sqrt(n); loop++){
		if (n % loop == 0){
			return false;
		}
	}
	return true;
}
