namespace Island
{
    partial class IslandForm
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
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(IslandForm));
            this.columnTextBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lengthTextBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.anyTextBox = new System.Windows.Forms.TextBox();
            this.generateButton = new System.Windows.Forms.Button();
            this.logTextBox = new System.Windows.Forms.TextBox();
            this.mainTabControl = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label5 = new System.Windows.Forms.Label();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.nextButton = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.matchButton = new System.Windows.Forms.Button();
            this.matchTextBox = new System.Windows.Forms.TextBox();
            this.shrinkButton = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label6 = new System.Windows.Forms.Label();
            this.splitTextBox = new System.Windows.Forms.TextBox();
            this.workingTextBox = new System.Windows.Forms.TextBox();
            this.titleLabel = new System.Windows.Forms.Label();
            this.workingProgressBar = new System.Windows.Forms.ProgressBar();
            this.mainTabControl.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // columnTextBox
            // 
            this.columnTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.columnTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.columnTextBox.Location = new System.Drawing.Point(6, 98);
            this.columnTextBox.MaxLength = 2100000000;
            this.columnTextBox.Multiline = true;
            this.columnTextBox.Name = "columnTextBox";
            this.columnTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.columnTextBox.Size = new System.Drawing.Size(493, 352);
            this.columnTextBox.TabIndex = 0;
            this.columnTextBox.WordWrap = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(6, 83);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 12);
            this.label1.TabIndex = 1;
            this.label1.Text = "1.Name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(505, 83);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 12);
            this.label2.TabIndex = 3;
            this.label2.Text = "2.Length";
            // 
            // lengthTextBox
            // 
            this.lengthTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.lengthTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lengthTextBox.Location = new System.Drawing.Point(507, 98);
            this.lengthTextBox.MaxLength = 2100000000;
            this.lengthTextBox.Multiline = true;
            this.lengthTextBox.Name = "lengthTextBox";
            this.lengthTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.lengthTextBox.Size = new System.Drawing.Size(103, 352);
            this.lengthTextBox.TabIndex = 2;
            this.lengthTextBox.WordWrap = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(616, 83);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(125, 12);
            this.label3.TabIndex = 5;
            this.label3.Text = "4.Definition Preview";
            // 
            // anyTextBox
            // 
            this.anyTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.anyTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.anyTextBox.Location = new System.Drawing.Point(620, 98);
            this.anyTextBox.MaxLength = 2100000000;
            this.anyTextBox.Multiline = true;
            this.anyTextBox.Name = "anyTextBox";
            this.anyTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.anyTextBox.Size = new System.Drawing.Size(622, 480);
            this.anyTextBox.TabIndex = 4;
            this.anyTextBox.WordWrap = false;
            // 
            // generateButton
            // 
            this.generateButton.BackColor = System.Drawing.Color.LightGreen;
            this.generateButton.Font = new System.Drawing.Font("宋体", 28F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.generateButton.Location = new System.Drawing.Point(6, 456);
            this.generateButton.Name = "generateButton";
            this.generateButton.Size = new System.Drawing.Size(604, 122);
            this.generateButton.TabIndex = 7;
            this.generateButton.Text = "///// 3.Generate /////";
            this.generateButton.UseVisualStyleBackColor = false;
            this.generateButton.Click += new System.EventHandler(this.GenerateButton_Click);
            // 
            // logTextBox
            // 
            this.logTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.logTextBox.Location = new System.Drawing.Point(16, 659);
            this.logTextBox.MaxLength = 2100000000;
            this.logTextBox.Multiline = true;
            this.logTextBox.Name = "logTextBox";
            this.logTextBox.ReadOnly = true;
            this.logTextBox.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.logTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.logTextBox.Size = new System.Drawing.Size(1252, 90);
            this.logTextBox.TabIndex = 9;
            this.logTextBox.WordWrap = false;
            // 
            // mainTabControl
            // 
            this.mainTabControl.Controls.Add(this.tabPage1);
            this.mainTabControl.Controls.Add(this.tabPage2);
            this.mainTabControl.Location = new System.Drawing.Point(16, 43);
            this.mainTabControl.Name = "mainTabControl";
            this.mainTabControl.SelectedIndex = 0;
            this.mainTabControl.Size = new System.Drawing.Size(1256, 610);
            this.mainTabControl.TabIndex = 10;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.groupBox1);
            this.tabPage1.Controls.Add(this.label1);
            this.tabPage1.Controls.Add(this.columnTextBox);
            this.tabPage1.Controls.Add(this.label3);
            this.tabPage1.Controls.Add(this.anyTextBox);
            this.tabPage1.Controls.Add(this.lengthTextBox);
            this.tabPage1.Controls.Add(this.generateButton);
            this.tabPage1.Controls.Add(this.label2);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(1248, 584);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "AnyTran Standard Format Definition Generator";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Location = new System.Drawing.Point(3, 6);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(1239, 74);
            this.groupBox1.TabIndex = 9;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "How to";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(6, 17);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(317, 48);
            this.label5.TabIndex = 8;
            this.label5.Text = "1. Copy column name and item length from file.\r\n2. Press generate button.\r\n3. Cop" +
    "y definition from area and import to AnyTrans.\r\n4. Follow the SOP.";
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.nextButton);
            this.tabPage2.Controls.Add(this.label7);
            this.tabPage2.Controls.Add(this.matchButton);
            this.tabPage2.Controls.Add(this.matchTextBox);
            this.tabPage2.Controls.Add(this.shrinkButton);
            this.tabPage2.Controls.Add(this.label4);
            this.tabPage2.Controls.Add(this.groupBox2);
            this.tabPage2.Controls.Add(this.splitTextBox);
            this.tabPage2.Controls.Add(this.workingTextBox);
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(1248, 584);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Output TXT simple matcher";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // nextButton
            // 
            this.nextButton.BackColor = System.Drawing.Color.Gold;
            this.nextButton.Location = new System.Drawing.Point(6, 543);
            this.nextButton.Name = "nextButton";
            this.nextButton.Size = new System.Drawing.Size(1236, 35);
            this.nextButton.TabIndex = 15;
            this.nextButton.Text = "5.Next";
            this.nextButton.UseVisualStyleBackColor = false;
            this.nextButton.Click += new System.EventHandler(this.NextButton_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(6, 239);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(83, 12);
            this.label7.TabIndex = 14;
            this.label7.Text = "4.Matched TXT";
            // 
            // matchButton
            // 
            this.matchButton.BackColor = System.Drawing.Color.Chartreuse;
            this.matchButton.Location = new System.Drawing.Point(999, 203);
            this.matchButton.Name = "matchButton";
            this.matchButton.Size = new System.Drawing.Size(243, 35);
            this.matchButton.TabIndex = 12;
            this.matchButton.Text = "3.Match";
            this.matchButton.UseVisualStyleBackColor = false;
            this.matchButton.Click += new System.EventHandler(this.MatchButton_Click);
            // 
            // matchTextBox
            // 
            this.matchTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.matchTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.matchTextBox.Font = new System.Drawing.Font("宋体", 19F);
            this.matchTextBox.Location = new System.Drawing.Point(255, 201);
            this.matchTextBox.Name = "matchTextBox";
            this.matchTextBox.Size = new System.Drawing.Size(738, 36);
            this.matchTextBox.TabIndex = 11;
            // 
            // shrinkButton
            // 
            this.shrinkButton.BackColor = System.Drawing.Color.Violet;
            this.shrinkButton.Location = new System.Drawing.Point(6, 201);
            this.shrinkButton.Name = "shrinkButton";
            this.shrinkButton.Size = new System.Drawing.Size(243, 35);
            this.shrinkButton.TabIndex = 10;
            this.shrinkButton.Text = "2.Shrink";
            this.shrinkButton.UseVisualStyleBackColor = false;
            this.shrinkButton.Click += new System.EventHandler(this.ShrinkButton_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(4, 94);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(83, 12);
            this.label4.TabIndex = 9;
            this.label4.Text = "1.Working TXT";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label6);
            this.groupBox2.Location = new System.Drawing.Point(3, 6);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(1239, 85);
            this.groupBox2.TabIndex = 8;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "How to";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(6, 17);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(389, 60);
            this.label6.TabIndex = 0;
            this.label6.Text = resources.GetString("label6.Text");
            // 
            // splitTextBox
            // 
            this.splitTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.splitTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.splitTextBox.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.splitTextBox.Location = new System.Drawing.Point(6, 254);
            this.splitTextBox.MaxLength = 2100000000;
            this.splitTextBox.Multiline = true;
            this.splitTextBox.Name = "splitTextBox";
            this.splitTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.splitTextBox.Size = new System.Drawing.Size(1236, 283);
            this.splitTextBox.TabIndex = 6;
            this.splitTextBox.WordWrap = false;
            // 
            // workingTextBox
            // 
            this.workingTextBox.BackColor = System.Drawing.Color.LightGoldenrodYellow;
            this.workingTextBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.workingTextBox.Cursor = System.Windows.Forms.Cursors.IBeam;
            this.workingTextBox.Location = new System.Drawing.Point(3, 109);
            this.workingTextBox.MaxLength = 2100000000;
            this.workingTextBox.Multiline = true;
            this.workingTextBox.Name = "workingTextBox";
            this.workingTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.workingTextBox.Size = new System.Drawing.Size(1239, 86);
            this.workingTextBox.TabIndex = 1;
            this.workingTextBox.WordWrap = false;
            // 
            // titleLabel
            // 
            this.titleLabel.AutoSize = true;
            this.titleLabel.Font = new System.Drawing.Font("宋体", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.titleLabel.Location = new System.Drawing.Point(13, 13);
            this.titleLabel.Name = "titleLabel";
            this.titleLabel.Size = new System.Drawing.Size(313, 16);
            this.titleLabel.TabIndex = 11;
            this.titleLabel.Text = "TSC Data Convert Job Helper v0.0.1";
            // 
            // workingProgressBar
            // 
            this.workingProgressBar.Location = new System.Drawing.Point(16, 755);
            this.workingProgressBar.Name = "workingProgressBar";
            this.workingProgressBar.Size = new System.Drawing.Size(1252, 23);
            this.workingProgressBar.TabIndex = 16;
            this.workingProgressBar.Value = 100;
            // 
            // IslandForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1284, 781);
            this.Controls.Add(this.workingProgressBar);
            this.Controls.Add(this.titleLabel);
            this.Controls.Add(this.mainTabControl);
            this.Controls.Add(this.logTextBox);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "IslandForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "ISLAND";
            this.mainTabControl.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox columnTextBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox lengthTextBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox anyTextBox;
        private System.Windows.Forms.Button generateButton;
        private System.Windows.Forms.TextBox logTextBox;
        private System.Windows.Forms.TabControl mainTabControl;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.Label titleLabel;
        private System.Windows.Forms.TextBox workingTextBox;
        private System.Windows.Forms.TextBox splitTextBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Button nextButton;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Button matchButton;
        private System.Windows.Forms.TextBox matchTextBox;
        private System.Windows.Forms.Button shrinkButton;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ProgressBar workingProgressBar;
    }
}

