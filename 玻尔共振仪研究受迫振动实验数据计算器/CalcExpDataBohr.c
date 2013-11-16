/*这个简单的程序是用来给大学物理实验课程中名为
用玻尔共振仪研究受迫振动的实验所写的一个简易计算器
用于计算实验项目二中相位差计算以及强迫力矩周期与弹簧对应固有周期所使用
Ver 0.1.6e Type CTP
因为数据就15个而且几乎是一次性的产物
所以就不用数组什么的了*/
#include <stdio.h>
#include <math.h>
void line();
int main()
{
	int i;
	float T0=0,T=0,pi=3.1415926,b,up,dn,tge,rad,anw,TT; 
	printf("实验数据计算用简易计算器 Ver 0.1.6e Type CTP\n");
	printf("相关使用说明请查阅源代码备注.\n"); 
	line(); 
	printf("实验项目:用玻尔共振仪研究受迫振动\n");
	printf("数据来源:实验项目二相关内容.\n");
	line();
	printf("请以空格为间隔分别输入T及T0值,以回车确认:\n");
	printf("输入0 0结束计算\n");
	line(); 
	printf("请输入阻尼系数b:\n");
	printf("阻尼系数b = ");
	scanf("%f",&b); 
	line(); 
	for (i=1;i<=500;i++)
	{
		printf("Data %d = ",i);
		scanf("%f %f",&T,&T0);
		if (T==0&&T0==0)		//输入0 0跳出循环 
		{
			line(); 
			break; 
		} 
		up=b*T0*T0*T;
		dn=pi*(T*T-T0*T0);
		tge=up/dn;
		rad=atan(tge);
		anw=(rad/pi)*180;		//弧度制转换成角度值 
		if (anw<=0)		//负角补正 
		{ 
			anw=180+anw; 			
		} 
		TT=T0/T;
		line();
		printf("相位差计算值: %.1f\n",anw);
		printf("比值: %1.3f\n",TT); 
		line(); 
	} 	 
}

void line()
{
	printf("------------------------------------------------------\n"); 
}
