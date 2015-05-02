using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Windows.Forms;

namespace EasyCertManager
{
    public partial class FormMain : Form
    {
        private string _certPath = "";
        private string _certPassword = "";

        public FormMain()
        {
            InitializeComponent();
            FileDeploy();
        }

#region Function

        private void SetLog(string message)
        {
            textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " " + message + "\n");    
        }

        //If CertUtil.exe existed
        private void FileDeploy()
        {
            SetLog("Initializing...");
            //Try deploy certUtil
            if (File.Exists("C:\\WINDOWS\\system32\\certutil.exe"))
            {
                SetLog("CertUtil.exe is already existed.");
            }
            else
            {
                SetLog("CertUtil.exe is not exist, deploying...");
                var certUtil = Properties.Resources.certutil;
                var fileStream = new FileStream("C:\\WINDOWS\\system32\\certutil.exe", FileMode.Create);
                var binaryWriter = new BinaryWriter(fileStream);
                try
                {
                    binaryWriter.Write(certUtil);
                }
                catch(Exception e)
                {
                    MessageBox.Show(@"Error message: " + e.Message, @"Deploy error", MessageBoxButtons.OK,
                        MessageBoxIcon.Error);
                }
                binaryWriter.Close();
                fileStream.Close();
                SetLog("CertUtil.exe is deployed");
            }
            SetLog("Initialization finished.");
        }

        private void RunCmd(string command)
        {
            var process = new Process
            {
                StartInfo =
                {
                    FileName = "cmd.exe",
                    UseShellExecute = false,
                    RedirectStandardInput = true,
                    RedirectStandardOutput = true,
                    CreateNoWindow = true
                }
            };
            process.StartInfo.Arguments = "/c " + command; 
            process.Start();
            process.BeginOutputReadLine();
            process.OutputDataReceived += OutputHandler;
            process.Close();
        }

        private void RegCert()
        {
            SetLog("Start registering your certificate...");
            SetLog("-------Please Check the information below-------");
            RunCmd("CertUtil -user -f -p " + _certPassword + " -importpfx " + _certPath);
        }

#endregion

#region Events

        private void buttonBrowse_Click(object sender, EventArgs e)
        {
            openFileDialog.InitialDirectory = Environment.CurrentDirectory;
            openFileDialog.ShowDialog();
            _certPath = openFileDialog.FileName;
            textBoxLocation.Text = _certPath;
        }

        private void buttonReg_Click(object sender, EventArgs e)
        {
            _certPassword = textBoxPassword.Text;
            if (_certPath != "" && _certPassword != "")
            {
                RegCert();
            }
            else
            {
                MessageBox.Show(@"Please check your information.", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                SetLog("Error occurred.");
            }
        }

        private void OutputHandler(object sendingProcess, DataReceivedEventArgs outLine)
        {
            CheckForIllegalCrossThreadCalls = false;
            if (!String.IsNullOrEmpty(outLine.Data))
            {
                SetLog(outLine.Data);
            }
            else
            {
                SetLog("-234234234234----");
            }
        }

#endregion



    }

}
