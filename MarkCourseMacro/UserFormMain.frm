VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} UserFormMain 
   Caption         =   "Mark course"
   ClientHeight    =   1800
   ClientLeft      =   45
   ClientTop       =   390
   ClientWidth     =   3720
   OleObjectBlob   =   "UserFormMain.frx":0000
   StartUpPosition =   1  '����������
End
Attribute VB_Name = "UserFormMain"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub UserForm_Initialize()

    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "ǳ��"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "ǳ��"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.AddItem "ˮ��ɫ"
    ComboBoxColor.AddItem "��ɫ"
    ComboBoxColor.ListIndex = 1
    
End Sub

Private Sub ButtonConfirm_Click()

    Dim colorCode(10) As Long
        colorCode(0) = 255
        colorCode(1) = 49407
        colorCode(2) = 65535
        colorCode(3) = 5287936
        colorCode(4) = 5296274
        colorCode(5) = 15773696
        colorCode(6) = 12611584
        colorCode(7) = 10498160
        colorCode(8) = 13020235
        colorCode(9) = 5540500
        
    With Application.ReplaceFormat.Interior
        .PatternColorIndex = xlAutomatic
        .Color = colorCode(ComboBoxColor.ListIndex)
        .TintAndShade = 0
        .PatternTintAndShade = 0
    End With
    
    Cells.Replace What:=TextBoxCourseID.Text, Replacement:=TextBoxCourseID.Text, LookAt:=xlPart, _
        SearchOrder:=xlByRows, MatchCase:=False, SearchFormat:=False, _
        ReplaceFormat:=True
        
End Sub


