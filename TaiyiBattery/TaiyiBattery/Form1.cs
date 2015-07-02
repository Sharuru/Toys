using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
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
            timerSetTime.Tick += new System.EventHandler(this.timerSetTime_Tick);
            timerSetTime.Start();
            calcTaiyi();
        }

        private void timerSetTime_Tick(object sender, EventArgs e)
        {
            labelTime.Text = DateTime.Now.ToLongTimeString();
        }

        private void calcTaiyi()
        {
            TimeSpan ts = dateTimePickerEnd.Value - dateTimePickerStart.Value;
            TimeSpan td = dateTimePickerEnd.Value - DateTime.Now;
            double left = td.TotalSeconds/ts.TotalSeconds;
            progressBarBattery.Value = Convert.ToInt32(left*100);
            labelBattery.Text = progressBarBattery.Value.ToString() + "%";
        }

        private void dateTimePickerStart_ValueChanged(object sender, EventArgs e)
        {
            calcTaiyi();
        }

        private void dateTimePickerEnd_ValueChanged(object sender, EventArgs e)
        {
            calcTaiyi();
        }
    }
}
