using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace HonokaControlPanel
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            //系统启动时检测
            textBoxLog.Text = DateTime.Now.ToString("hh:mm:ss") + " - 控制面板已启动 \r\n";
            textBoxLog.ScrollToCaret();
            //输出当前路径
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 当前运行目录：" + Environment.CurrentDirectory + "\r\n";
            textBoxLog.ScrollToCaret();
            //TODO:检测环境
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 等待用户操作... \r\n";
            textBoxLog.ScrollToCaret();
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            //选择启动
            buttonStart.Text = "启动中...";
            textBoxLog.ScrollToCaret();
            buttonStart.Enabled = false;
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 正在启动 Honoka... \r\n";
            textBoxLog.ScrollToCaret();
            //启动数据库
            Process startDbProcess = new Process();
            ProcessStartInfo dbStartInfo = new ProcessStartInfo();
            dbStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            dbStartInfo.FileName = "cmd.exe";
            dbStartInfo.Arguments = "/C  " + Environment.CurrentDirectory + "\\pgsql\\bin\\pg_ctl.exe " + "-D " + Environment.CurrentDirectory + "\\pgsql\\data" + " -l logfile start";
            startDbProcess.StartInfo = dbStartInfo;
            startDbProcess.Start();
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 数据库启动命令已发送... \r\n";
            textBoxLog.ScrollToCaret();
            while (!checkProcessExist("postgres"))
            {
                Thread.Sleep(500);
            }
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 数据库已成功启动... \r\n";
            textBoxLog.ScrollToCaret();
            //启动服务器
            Process startSvProcess = new Process();
            ProcessStartInfo svStartInfo = new System.Diagnostics.ProcessStartInfo();
            svStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            svStartInfo.FileName = "cmd.exe";
            svStartInfo.Arguments = "/C  " + Environment.CurrentDirectory + "\\tomcat\\bin\\catalina.bat run";
            startSvProcess.StartInfo = svStartInfo;
            startSvProcess.Start();
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 服务器启动命令已发送... \r\n";
            textBoxLog.ScrollToCaret();
            while (!checkProcessExist("java"))
            {
                Thread.Sleep(500);
            }
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 服务器已成功启动... \r\n";
            textBoxLog.ScrollToCaret();
            buttonStart.Text = "已启动";
            buttonStop.Enabled = true;
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - Honoka 启动完毕... \r\n";
            textBoxLog.ScrollToCaret();
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 请稍作等待以完成资源载入... \r\n";
            textBoxLog.ScrollToCaret();
            Thread.Sleep(3000);
            buttonOpenHonoka.Enabled = true;
        }

        private void buttonStop_Click(object sender, EventArgs e)
        {
            //选择停止
            buttonStop.Text = "停止中...";
            buttonOpenHonoka.Enabled = false;
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 正在停止 Honoka... \r\n";
            textBoxLog.ScrollToCaret();
            //停止服务器
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 服务器停止命令已发送... \r\n";
            textBoxLog.ScrollToCaret();
            Process stopSvProcess = new Process();
            ProcessStartInfo svStartInfo = new ProcessStartInfo();
            svStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            svStartInfo.FileName = "cmd.exe";
            svStartInfo.Arguments = "/C  " + Environment.CurrentDirectory + "\\tomcat\\bin\\catalina.bat stop";
            stopSvProcess.StartInfo = svStartInfo;
            stopSvProcess.Start();
            //释放资源
            Process[] processlist = Process.GetProcesses();
            foreach (Process theprocess in processlist)
            {
                if (theprocess.ProcessName == "java")
                {
                    theprocess.Kill();
                }
            }
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 服务器已成功停止... \r\n";
            textBoxLog.ScrollToCaret();
            //停止数据库
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 数据库停止命令已发送... \r\n";
            textBoxLog.ScrollToCaret();
            Process stopDbProcess = new Process();
            ProcessStartInfo dbStartInfo = new ProcessStartInfo();
            dbStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            dbStartInfo.FileName = "cmd.exe";
            dbStartInfo.Arguments = "/C  " + Environment.CurrentDirectory + "\\pgsql\\bin\\pg_ctl.exe " + "-D " + System.Environment.CurrentDirectory + "\\pgsql\\data" + " -m fast stop";
            stopDbProcess.StartInfo = dbStartInfo;
            stopDbProcess.Start();
            while (checkProcessExist("postgres"))
            {
                Thread.Sleep(500);
            }
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - 数据库已成功停止... \r\n";
            textBoxLog.ScrollToCaret();
            buttonStop.Text = "停止";
            buttonStop.Enabled = false;
            buttonStart.Text = "启动";
            buttonStart.Enabled = true;
            textBoxLog.Text += DateTime.Now.ToString("hh:mm:ss") + " - Honoka 停止完毕... \r\n";
            textBoxLog.ScrollToCaret();
        }

        private Boolean checkProcessExist(String procName)
        {
            Process[] processlist = Process.GetProcesses();
            foreach (Process theprocess in processlist)
            {
                if(theprocess.ProcessName == procName)
                {
                    return true;
                }
            }
            return false;
        }

        private void buttonOpenHonoka_Click(object sender, EventArgs e)
        {
            Process.Start("http://localhost:8080/Honoka");
        }
    }
}
