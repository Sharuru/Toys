@echo ��ʼע��
@echo ����msvcp120.dll��system32Ŀ¼��
@copy msvcp120.dll %windir%\system32\
@regsvr32 %windir%\system32\msvcp120.dll /s
@echo msvcr120.dllע��ɹ�
@pause