using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace TaiyiBattery
{
  public partial class FormMain : Form
    {
        public FormMain()
        {
            InitializeComponent();
        }

        private void FormMain_Load(object sender, EventArgs e)
        {
            dateTimePickerStart.Value = DateTime.Today;
            dateTimePickerEnd.Value = DateTime.Today.AddSeconds(78300);
            timerSetTime.Interval = 1000;
            timerSetTime.Tick += timerSetTime_Tick;
            timerSetTime.Start();
        }

        private void timerSetTime_Tick(object sender, EventArgs e)
        {
            labelTime.Text = DateTime.Now.ToLongTimeString();
            CalcTaiyi();
        }

        private void CalcTaiyi()
        {
            var ts = dateTimePickerEnd.Value - dateTimePickerStart.Value;
            var td = dateTimePickerEnd.Value - DateTime.Now;
            var left = td.TotalSeconds/ts.TotalSeconds;
            progressBarBattery.Value = Convert.ToInt32(left*100);
            if(progressBarBattery.Value <= 10)
            {
                progressBarBattery.SetState(2);     //Error
            }
            else if (progressBarBattery.Value <= 20)
            {
                progressBarBattery.SetState(3); //Warning
            }
            else
            {
                progressBarBattery.SetState(1);     //Normal
            }
            labelBattery.Text = progressBarBattery.Value + @"%";
        }

      private void dateTimePickerStart_ValueChanged_1(object sender, EventArgs e)
        {
            CalcTaiyi();
        }
    }

  public static class ModifyProgressBarColor
  {
      [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = false)]
      static extern IntPtr SendMessage(IntPtr hWnd, uint msg, IntPtr w, IntPtr l);
      public static void SetState(this ProgressBar pBar, int state)
      {
          SendMessage(pBar.Handle, 1040, (IntPtr)state, IntPtr.Zero);
      }
  }
}
