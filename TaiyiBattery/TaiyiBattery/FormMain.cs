using System;
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
            TimeSpan ts = dateTimePickerEnd.Value - dateTimePickerStart.Value;
            TimeSpan td = dateTimePickerEnd.Value - DateTime.Now;
            double left = td.TotalSeconds/ts.TotalSeconds;
            progressBarBattery.Value = Convert.ToInt32(left*100);
            labelBattery.Text = progressBarBattery.Value + @"%";
        }

        private void dateTimePickerStart_ValueChanged(object sender, EventArgs e)
        {
            CalcTaiyi();
        }

        private void dateTimePickerEnd_ValueChanged(object sender, EventArgs e)
        {
            CalcTaiyi();
        }
    }
}
