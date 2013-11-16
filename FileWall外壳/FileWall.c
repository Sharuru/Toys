/*这是一个很装逼的FileWall外壳
运行前会煞有介事的显示出一些不明觉厉的东西
然后在读取后会运行程序
相对路径*/ 
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
int main(void) 
{ 
	int i;
	system("title FileWall");		//设置标题 
	system("mode con cols=55 lines=11");		//设置窗体大小 
	printf("\n"); 
	printf("=======================================================");
	printf("                FileWall HIBIKI Edition                ");
	printf("=======================================================");
	printf("正在确认密钥...");
	Sleep(300); 
	printf("确认.\n");
	printf("\n正在初始化运行环境...\n");
	printf("["); 
	Sleep(100);
	for (i=0;i<=52;i++)
	{
		printf("="); 
		Sleep(25);
	}
	printf("]\n");
	printf("                    初始化完毕                  \n"); 
	Sleep(400);
	system("start "" \\上机愉悦计划\\Others\\FileWall_Portable\\FileWall.exe"); //运行程序 
	return 0; 
}
