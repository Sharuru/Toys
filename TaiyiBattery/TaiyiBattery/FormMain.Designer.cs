namespace TaiyiBattery
{
    partial class FormMain
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormMain));
            this.labelGuide1 = new System.Windows.Forms.Label();
            this.dateTimePickerStart = new System.Windows.Forms.DateTimePicker();
            this.dateTimePickerEnd = new System.Windows.Forms.DateTimePicker();
            this.labelGuide2 = new System.Windows.Forms.Label();
            this.labelGuide3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.progressBarBattery = new System.Windows.Forms.ProgressBar();
            this.labelTime = new System.Windows.Forms.Label();
            this.timerSetTime = new System.Windows.Forms.Timer(this.components);
            this.labelBattery = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // labelGuide1
            // 
            this.labelGuide1.AutoSize = true;
            this.labelGuide1.Location = new System.Drawing.Point(12, 54);
            this.labelGuide1.Name = "labelGuide1";
            this.labelGuide1.Size = new System.Drawing.Size(155, 12);
            this.labelGuide1.TabIndex = 0;
            this.labelGuide1.Text = "今天太医充电完成的时间是:";
            // 
            // dateTimePickerStart
            // 
            this.dateTimePickerStart.CustomFormat = "HH:mm";
            this.dateTimePickerStart.Format = System.Windows.Forms.DateTimePickerFormat.Time;
            this.dateTimePickerStart.Location = new System.Drawing.Point(173, 48);
            this.dateTimePickerStart.Name = "dateTimePickerStart";
            this.dateTimePickerStart.ShowUpDown = true;
            this.dateTimePickerStart.Size = new System.Drawing.Size(82, 21);
            this.dateTimePickerStart.TabIndex = 1;
            this.dateTimePickerStart.Value = new System.DateTime(2015, 7, 2, 11, 30, 0, 0);
            this.dateTimePickerStart.ValueChanged += new System.EventHandler(this.dateTimePickerStart_ValueChanged_1);
            // 
            // dateTimePickerEnd
            // 
            this.dateTimePickerEnd.CustomFormat = "HH:mm";
            this.dateTimePickerEnd.Format = System.Windows.Forms.DateTimePickerFormat.Time;
            this.dateTimePickerEnd.Location = new System.Drawing.Point(173, 79);
            this.dateTimePickerEnd.Name = "dateTimePickerEnd";
            this.dateTimePickerEnd.ShowUpDown = true;
            this.dateTimePickerEnd.Size = new System.Drawing.Size(82, 21);
            this.dateTimePickerEnd.TabIndex = 3;
            this.dateTimePickerEnd.Value = new System.DateTime(2015, 7, 2, 22, 0, 0, 0);
            this.dateTimePickerEnd.ValueChanged += new System.EventHandler(this.dateTimePickerStart_ValueChanged_1);
            // 
            // labelGuide2
            // 
            this.labelGuide2.AutoSize = true;
            this.labelGuide2.Location = new System.Drawing.Point(12, 85);
            this.labelGuide2.Name = "labelGuide2";
            this.labelGuide2.Size = new System.Drawing.Size(155, 12);
            this.labelGuide2.TabIndex = 2;
            this.labelGuide2.Text = "预计今天太医没电的时间是:";
            // 
            // labelGuide3
            // 
            this.labelGuide3.AutoSize = true;
            this.labelGuide3.Location = new System.Drawing.Point(12, 23);
            this.labelGuide3.Name = "labelGuide3";
            this.labelGuide3.Size = new System.Drawing.Size(83, 12);
            this.labelGuide3.TabIndex = 4;
            this.labelGuide3.Text = "现在的时间是:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(12, 117);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(131, 12);
            this.label4.TabIndex = 6;
            this.label4.Text = "太医目前的剩余电量为:";
            // 
            // progressBarBattery
            // 
            this.progressBarBattery.ForeColor = System.Drawing.Color.Green;
            this.progressBarBattery.Location = new System.Drawing.Point(14, 148);
            this.progressBarBattery.Name = "progressBarBattery";
            this.progressBarBattery.Size = new System.Drawing.Size(241, 19);
            this.progressBarBattery.Step = 1;
            this.progressBarBattery.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.progressBarBattery.TabIndex = 7;
            // 
            // labelTime
            // 
            this.labelTime.AutoSize = true;
            this.labelTime.Location = new System.Drawing.Point(101, 23);
            this.labelTime.Name = "labelTime";
            this.labelTime.Size = new System.Drawing.Size(53, 12);
            this.labelTime.TabIndex = 8;
            this.labelTime.Text = "00:00:00";
            // 
            // labelBattery
            // 
            this.labelBattery.AutoSize = true;
            this.labelBattery.Location = new System.Drawing.Point(149, 117);
            this.labelBattery.Name = "labelBattery";
            this.labelBattery.Size = new System.Drawing.Size(29, 12);
            this.labelBattery.TabIndex = 9;
            this.labelBattery.Text = "100%";
            // 
            // FormMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(267, 185);
            this.Controls.Add(this.labelBattery);
            this.Controls.Add(this.labelTime);
            this.Controls.Add(this.progressBarBattery);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.labelGuide3);
            this.Controls.Add(this.dateTimePickerEnd);
            this.Controls.Add(this.labelGuide2);
            this.Controls.Add(this.dateTimePickerStart);
            this.Controls.Add(this.labelGuide1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormMain";
            this.Text = "太医电量";
            this.Load += new System.EventHandler(this.FormMain_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelGuide1;
        private System.Windows.Forms.DateTimePicker dateTimePickerStart;
        private System.Windows.Forms.DateTimePicker dateTimePickerEnd;
        private System.Windows.Forms.Label labelGuide2;
        private System.Windows.Forms.Label labelGuide3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ProgressBar progressBarBattery;
        private System.Windows.Forms.Label labelTime;
        private System.Windows.Forms.Timer timerSetTime;
        private System.Windows.Forms.Label labelBattery;
    }
}

