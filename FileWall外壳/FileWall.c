/*����һ����װ�Ƶ�FileWall���
����ǰ��ɷ�н��µ���ʾ��һЩ���������Ķ���
Ȼ���ڶ�ȡ������г���
���·��*/ 
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
int main(void) 
{ 
	int i;
	system("title FileWall");		//���ñ��� 
	system("mode con cols=55 lines=11");		//���ô����С 
	printf("\n"); 
	printf("=======================================================");
	printf("                FileWall HIBIKI Edition                ");
	printf("=======================================================");
	printf("����ȷ����Կ...");
	Sleep(300); 
	printf("ȷ��.\n");
	printf("\n���ڳ�ʼ�����л���...\n");
	printf("["); 
	Sleep(100);
	for (i=0;i<=52;i++)
	{
		printf("="); 
		Sleep(25);
	}
	printf("]\n");
	printf("                    ��ʼ�����                  \n"); 
	Sleep(400);
	system("start "" \\�ϻ����üƻ�\\Others\\FileWall_Portable\\FileWall.exe"); //���г��� 
	return 0; 
}
