using System;
using System.Drawing;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using Microsoft.Win32;

namespace ScreenSaver
{
    public partial class ScreenSaverForm : Form
    {
        #region Win32 API functions

        [DllImport("user32.dll")]
        static extern IntPtr SetParent(IntPtr hWndChild, IntPtr hWndNewParent);

        [DllImport("user32.dll")]
        static extern int SetWindowLong(IntPtr hWnd, int nIndex, IntPtr dwNewLong);

        [DllImport("user32.dll", SetLastError = true)]
        static extern int GetWindowLong(IntPtr hWnd, int nIndex);

        [DllImport("user32.dll")]
        static extern bool GetClientRect(IntPtr hWnd, out Rectangle lpRect);

        #endregion

        private Point mouseLocation;
        private bool previewMode = false;
        private Random rand = new Random();

        public ScreenSaverForm()
        {
            InitializeComponent();
        }

        public ScreenSaverForm(Rectangle Bounds)
        {
            InitializeComponent();
            this.Bounds = Bounds;
        }

        // 绘制中央 LOGO
        protected override void OnPaintBackground(PaintEventArgs e)
        {
            Bitmap logoImage = Properties.Resources.LOGO;

            // 取屏幕较短的一边计算 LOGO 图片的宽度（最短边的一半）
            int logoSize = (ClientSize.Width <= ClientSize.Height ? ClientSize.Width : ClientSize.Height) / 2;
            base.OnPaintBackground(e);

            // 绘制区域决定
            Rectangle logoArea = new Rectangle((ClientSize.Width - logoSize) / 2, (ClientSize.Height - logoSize) / 2, logoSize, logoSize);
            e.Graphics.DrawImage(logoImage, logoArea);
        }

        // 屏保加载时的处理
        private void ScreenSaverForm_Load(object sender, EventArgs e)
        {
            // 读取设定
            LoadSettings();

            Cursor.Hide();
            TopMost = true;

            // 文本定时器
            moveTimer.Interval = 5000;
            moveTimer.Tick += new EventHandler(MoveTimer_Tick);
            moveTimer.Start();
        }

        // 文本定时器行为
        private void MoveTimer_Tick(object sender, System.EventArgs e)
        {
            // Move text to new location
            textLabel.Text = "////////// PRTS ACTIVATED ////////// \nUPDATED AT: " + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            textLabel.Left = rand.Next(Math.Max(1, Bounds.Width - textLabel.Width));
            textLabel.Top = rand.Next(Math.Max(1, Bounds.Height - textLabel.Height));
        }


        // TODO: 从注册表加载设定
        private void LoadSettings()
        {
            // 从注册表读取设置
            RegistryKey regKey = Registry.CurrentUser.OpenSubKey("SOFTWARE\\SRR\\ScreenSaver");
            // 默认设置
            if (regKey == null)
            {
                // TODO
                if (!previewMode)
                {
                    textLabel.Font = new Font("Arial", 16);
                    textLabel.Padding = new Padding(20);
                }
                textLabel.Text = "////////// PRTS ACTIVATED ////////// \nUPDATED AT: " + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            }
            else
            {
                textLabel.Text = (string)regKey.GetValue("text");
            }
        }

        // 设定界面中的预览模式处理
        public ScreenSaverForm(IntPtr PreviewWndHandle)
        {
            InitializeComponent();

            SetParent(this.Handle, PreviewWndHandle);
            SetWindowLong(this.Handle, -16, new IntPtr(GetWindowLong(this.Handle, -16) | 0x40000000));
            _ = GetClientRect(PreviewWndHandle, out Rectangle ParentRect);
            Size = ParentRect.Size;
            Location = new Point(0, 0);

            // 调小字体大小
            textLabel.Font = new Font("Arial", 8);
            textLabel.Padding = new Padding(8);
                ;

            previewMode = true;
        }

        // 鼠标移动时退出屏保
        private void ScreenSaverForm_MouseMove(object sender, MouseEventArgs e)
        {
            if (!previewMode)
            {
                if (!mouseLocation.IsEmpty)
                {
                    if (Math.Abs(mouseLocation.X - e.X) > 5 ||
                        Math.Abs(mouseLocation.Y - e.Y) > 5)
                        Application.Exit();
                }
                mouseLocation = e.Location;
            }
        }

        // 鼠标点击时退出屏保
        private void ScreenSaverForm_MouseClick(object sender, MouseEventArgs e)
        {
            if (!previewMode)
                Application.Exit();
        }

        // 按下键盘时退出屏保
        private void ScreenSaverForm_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!previewMode)
                Application.Exit();
        }
    }
}
