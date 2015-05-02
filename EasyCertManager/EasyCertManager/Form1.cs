using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
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
        public FormMain()
        {
            InitializeComponent();
            FileDeploy();
        }

#region Function

        //If CertUtil.exe existed
        private void FileDeploy()
        {
            textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " Initializing...\n");
            //Try deploy certUtil
            if (File.Exists("C:\\WINDOWS\\system32\\certutil.exe"))
            {
                textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " CertUtil.exe is already existed.\n");
            }
            else
            {
                textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " CertUtil.exe is not exist, deploying...\n");
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
                textBoxLog.AppendText("CertUtil.exe is deployed.\n");
            }
            textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " Initialization finished.\n");
        }
#endregion

#region Events

        private void buttonBrowse_Click(object sender, EventArgs e)
        {
            openFileDialog.InitialDirectory = Environment.CurrentDirectory;
            openFileDialog.ShowDialog();
            textBoxLocation.Text = openFileDialog.FileName;
        }

#endregion

    }

}
