using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using CefSharp.WinForms;
using System.Windows.Forms;

namespace HonokaWinForm
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            //必须进行初始化，否则就出来页面啦。
            CefSharp.Cef.Initialize();

            //实例化控件
            ChromiumWebBrowser wb = new ChromiumWebBrowser("http://www.baidu.com");
            //设置停靠方式
            wb.Dock = DockStyle.Fill;

            //加入到当前窗体中
            this.Controls.Add(wb);
        }
    }
}
