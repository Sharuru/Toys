using System;
using System.Windows.Forms;
using Microsoft.Win32;

namespace ScreenSaver
{
    public partial class SettingsForm : Form
    {
        public SettingsForm()
        {
            InitializeComponent();
            LoadSettings();
        }

        // TODO: 从注册表加载配置
        private void LoadSettings()
        {
            // 从注册表读取设置
            RegistryKey regKey = Registry.CurrentUser.OpenSubKey("SOFTWARE\\SRR\\ScreenSaver");
            // 默认设置
            if (regKey == null)
            {
                textBox.Text = "null";
            }
            else
            {
                textBox.Text = (string)regKey.GetValue("text");
            }
        }

        // TODO: 保存配置到注册表
        private void SaveSettings()
        {
            RegistryKey regKey = Registry.CurrentUser.CreateSubKey("SOFTWARE\\SRR\\ScreenSaver");
            regKey.SetValue("text", textBox.Text);
        }

        private void OkButton_Click(object sender, EventArgs e)
        {
            SaveSettings();
            Close();
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}
