/*����򵥵ĳ�������������ѧ����ʵ��γ�����Ϊ
�ò����������о������񶯵�ʵ����д��һ�����׼�����
���ڼ���ʵ����Ŀ������λ������Լ�ǿ�����������뵯�ɶ�Ӧ����������ʹ��
Ver 0.1.6e Type CTP
��Ϊ���ݾ�15�����Ҽ�����һ���ԵĲ���
���ԾͲ�������ʲô����*/
#include <stdio.h>
#include <math.h>
void line();
int main()
{
	int i;
	float T0=0,T=0,pi=3.1415926,b,up,dn,tge,rad,anw,TT; 
	printf("ʵ�����ݼ����ü��׼����� Ver 0.1.6e Type CTP\n");
	printf("���ʹ��˵�������Դ���뱸ע.\n"); 
	line(); 
	printf("ʵ����Ŀ:�ò����������о�������\n");
	printf("������Դ:ʵ����Ŀ���������.\n");
	line();
	printf("���Կո�Ϊ����ֱ�����T��T0ֵ,�Իس�ȷ��:\n");
	printf("����0 0��������\n");
	line(); 
	printf("����������ϵ��b:\n");
	printf("����ϵ��b = ");
	scanf("%f",&b); 
	line(); 
	for (i=1;i<=500;i++)
	{
		printf("Data %d = ",i);
		scanf("%f %f",&T,&T0);
		if (T==0&&T0==0)		//����0 0����ѭ�� 
		{
			line(); 
			break; 
		} 
		up=b*T0*T0*T;
		dn=pi*(T*T-T0*T0);
		tge=up/dn;
		rad=atan(tge);
		anw=(rad/pi)*180;		//������ת���ɽǶ�ֵ 
		if (anw<=0)		//���ǲ��� 
		{ 
			anw=180+anw; 			
		} 
		TT=T0/T;
		line();
		printf("��λ�����ֵ: %.1f\n",anw);
		printf("��ֵ: %1.3f\n",TT); 
		line(); 
	} 	 
}

void line()
{
	printf("------------------------------------------------------\n"); 
}
