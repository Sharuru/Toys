@echo 开始注册
@echo 复制msvcp120.dll至system32目录下
@copy msvcp120.dll %windir%\system32\
@regsvr32 %windir%\system32\msvcp120.dll /s
@echo msvcr120.dll注册成功
@pause