using System;
using System.Security.Cryptography.X509Certificates;
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
            SetLog("Current OS version: " + Environment.OSVersion);
            SetLog("Waiting for operation...");
        }

#region Function

        private void SetLog(string message)
        {
            textBoxLog.AppendText(DateTime.Now.ToLongTimeString() + " " + message + "\n");    
        }

        private void CertHandler(string operation)
        {
            var cert = new X509Certificate2(_certPath, _certPassword);
            var store = new X509Store(StoreName.My);
            store.Open(OpenFlags.ReadWrite);
            SetLog("Certificate info:");
            SetLog(cert.Issuer);
            SetLog(cert.Subject);
            switch (operation)
            {
                case "Add":
                    store.Add(cert);
                    break;
                case "Remove":
                    store.Remove(cert);
                    break;
            }
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
                SetLog("Registering your certificate...");
                try
                {
                    CertHandler("Add");
                    SetLog("Registration complete.");
                }
                catch (Exception ex)
                {
                    SetLog(ex.Message);
                    SetLog("Error occurred.");
                }
            }
            else
            {
                MessageBox.Show(@"Please check your information.", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                SetLog("Error occurred.");
            }
        }

        private void buttonRemove_Click(object sender, EventArgs e)
        {
            _certPassword = textBoxPassword.Text;
            if (_certPath != "" && _certPassword != "")
            {
                SetLog("Removing your certificate...");
                try
                {
                    CertHandler("Remove");
                    SetLog("Remove complete.");
                }
                catch (Exception ex)
                {
                    SetLog(ex.Message);
                    SetLog("Error occurred.");
                }
            }
            else
            {
                MessageBox.Show(@"Please check your information.", @"Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                SetLog("Error occurred.");
            }
        }

        #endregion

    }
}
