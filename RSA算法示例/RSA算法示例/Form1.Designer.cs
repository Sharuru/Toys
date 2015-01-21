namespace RSA算法示例
{
    partial class Form1
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
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label1 = new System.Windows.Forms.Label();
            this.listBoxP = new System.Windows.Forms.ListBox();
            this.listBoxQ = new System.Windows.Forms.ListBox();
            this.buttonCreateKey = new System.Windows.Forms.Button();
            this.textBoxPQ = new System.Windows.Forms.TextBox();
            this.textBoxEN = new System.Windows.Forms.TextBox();
            this.textBoxDN = new System.Windows.Forms.TextBox();
            this.textBoxGuide = new System.Windows.Forms.TextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.numericUpDownP = new System.Windows.Forms.NumericUpDown();
            this.buttonEncrypt = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.textBoxC1 = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.label5 = new System.Windows.Forms.Label();
            this.textBoxP = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.buttonDecrypt = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.textBoxC2 = new System.Windows.Forms.TextBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownP)).BeginInit();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.textBoxGuide);
            this.groupBox1.Controls.Add(this.textBoxDN);
            this.groupBox1.Controls.Add(this.textBoxEN);
            this.groupBox1.Controls.Add(this.textBoxPQ);
            this.groupBox1.Controls.Add(this.buttonCreateKey);
            this.groupBox1.Controls.Add(this.listBoxQ);
            this.groupBox1.Controls.Add(this.listBoxP);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(306, 235);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "产生公钥和私钥";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "选择2个素数p和q";
            // 
            // listBoxP
            // 
            this.listBoxP.FormattingEnabled = true;
            this.listBoxP.ItemHeight = 12;
            this.listBoxP.Items.AddRange(new object[] {
            "5",
            "7",
            "11",
            "13",
            "17",
            "19",
            "23",
            "29",
            "37"});
            this.listBoxP.Location = new System.Drawing.Point(7, 37);
            this.listBoxP.Name = "listBoxP";
            this.listBoxP.Size = new System.Drawing.Size(36, 112);
            this.listBoxP.TabIndex = 1;
            // 
            // listBoxQ
            // 
            this.listBoxQ.FormattingEnabled = true;
            this.listBoxQ.ItemHeight = 12;
            this.listBoxQ.Items.AddRange(new object[] {
            "3",
            "5",
            "7",
            "11",
            "13",
            "17",
            "19",
            "23",
            "29",
            "37"});
            this.listBoxQ.Location = new System.Drawing.Point(49, 37);
            this.listBoxQ.Name = "listBoxQ";
            this.listBoxQ.Size = new System.Drawing.Size(36, 112);
            this.listBoxQ.TabIndex = 2;
            // 
            // buttonCreateKey
            // 
            this.buttonCreateKey.Location = new System.Drawing.Point(91, 36);
            this.buttonCreateKey.Name = "buttonCreateKey";
            this.buttonCreateKey.Size = new System.Drawing.Size(209, 32);
            this.buttonCreateKey.TabIndex = 3;
            this.buttonCreateKey.Text = "产生密钥";
            this.buttonCreateKey.UseVisualStyleBackColor = true;
            this.buttonCreateKey.Click += new System.EventHandler(this.buttonCreateKey_Click);
            // 
            // textBoxPQ
            // 
            this.textBoxPQ.Location = new System.Drawing.Point(91, 74);
            this.textBoxPQ.Name = "textBoxPQ";
            this.textBoxPQ.ReadOnly = true;
            this.textBoxPQ.Size = new System.Drawing.Size(209, 21);
            this.textBoxPQ.TabIndex = 4;
            this.textBoxPQ.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // textBoxEN
            // 
            this.textBoxEN.Location = new System.Drawing.Point(91, 101);
            this.textBoxEN.Name = "textBoxEN";
            this.textBoxEN.ReadOnly = true;
            this.textBoxEN.Size = new System.Drawing.Size(209, 21);
            this.textBoxEN.TabIndex = 5;
            this.textBoxEN.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // textBoxDN
            // 
            this.textBoxDN.Location = new System.Drawing.Point(91, 128);
            this.textBoxDN.Name = "textBoxDN";
            this.textBoxDN.ReadOnly = true;
            this.textBoxDN.Size = new System.Drawing.Size(209, 21);
            this.textBoxDN.TabIndex = 6;
            this.textBoxDN.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // textBoxGuide
            // 
            this.textBoxGuide.Location = new System.Drawing.Point(7, 156);
            this.textBoxGuide.Multiline = true;
            this.textBoxGuide.Name = "textBoxGuide";
            this.textBoxGuide.Size = new System.Drawing.Size(293, 72);
            this.textBoxGuide.TabIndex = 7;
            this.textBoxGuide.Text = "1.选择2个保密的大素数p和q;\r\n2.计算n=p·q和欧拉函数值φ(n)=(p-1)(q-1); \r\n3.选一整数e,且满足1<e<φ(n)和gcd(φ(n)," +
    "e)=1;\r\n4.计算d，且满足d·e=1 mod φ(n);\r\n5.公钥为{e,n}, 私钥为{d,n}";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label4);
            this.groupBox2.Controls.Add(this.textBoxC1);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.buttonEncrypt);
            this.groupBox2.Controls.Add(this.numericUpDownP);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Location = new System.Drawing.Point(12, 254);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(306, 76);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "加密";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(9, 21);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(41, 12);
            this.label2.TabIndex = 0;
            this.label2.Text = "明文P=";
            // 
            // numericUpDownP
            // 
            this.numericUpDownP.Location = new System.Drawing.Point(49, 19);
            this.numericUpDownP.Maximum = new decimal(new int[] {
            1000,
            0,
            0,
            0});
            this.numericUpDownP.Name = "numericUpDownP";
            this.numericUpDownP.Size = new System.Drawing.Size(62, 21);
            this.numericUpDownP.TabIndex = 1;
            this.numericUpDownP.Value = new decimal(new int[] {
            19,
            0,
            0,
            0});
            // 
            // buttonEncrypt
            // 
            this.buttonEncrypt.Location = new System.Drawing.Point(118, 19);
            this.buttonEncrypt.Name = "buttonEncrypt";
            this.buttonEncrypt.Size = new System.Drawing.Size(58, 23);
            this.buttonEncrypt.TabIndex = 2;
            this.buttonEncrypt.Text = "加密";
            this.buttonEncrypt.UseVisualStyleBackColor = true;
            this.buttonEncrypt.Click += new System.EventHandler(this.buttonEncrypt_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(182, 24);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(41, 12);
            this.label3.TabIndex = 3;
            this.label3.Text = "密文C=";
            // 
            // textBoxC1
            // 
            this.textBoxC1.Location = new System.Drawing.Point(230, 19);
            this.textBoxC1.Name = "textBoxC1";
            this.textBoxC1.ReadOnly = true;
            this.textBoxC1.Size = new System.Drawing.Size(70, 21);
            this.textBoxC1.TabIndex = 4;
            this.textBoxC1.Text = "?";
            this.textBoxC1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(106, 45);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(89, 24);
            this.label4.TabIndex = 5;
            this.label4.Text = "使用公钥{e, n}\r\nC = P^e mod n\r\n";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.textBoxC2);
            this.groupBox3.Controls.Add(this.label5);
            this.groupBox3.Controls.Add(this.textBoxP);
            this.groupBox3.Controls.Add(this.label6);
            this.groupBox3.Controls.Add(this.buttonDecrypt);
            this.groupBox3.Controls.Add(this.label7);
            this.groupBox3.Location = new System.Drawing.Point(12, 336);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(306, 76);
            this.groupBox3.TabIndex = 6;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "解密";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(106, 45);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(89, 24);
            this.label5.TabIndex = 5;
            this.label5.Text = "使用私钥{d, n}\r\nP = C^d mod n\r\n";
            // 
            // textBoxP
            // 
            this.textBoxP.Location = new System.Drawing.Point(230, 19);
            this.textBoxP.Name = "textBoxP";
            this.textBoxP.ReadOnly = true;
            this.textBoxP.Size = new System.Drawing.Size(70, 21);
            this.textBoxP.TabIndex = 4;
            this.textBoxP.Text = "?";
            this.textBoxP.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(182, 24);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(41, 12);
            this.label6.TabIndex = 3;
            this.label6.Text = "明文P=";
            // 
            // buttonDecrypt
            // 
            this.buttonDecrypt.Location = new System.Drawing.Point(118, 19);
            this.buttonDecrypt.Name = "buttonDecrypt";
            this.buttonDecrypt.Size = new System.Drawing.Size(58, 23);
            this.buttonDecrypt.TabIndex = 2;
            this.buttonDecrypt.Text = "解密";
            this.buttonDecrypt.UseVisualStyleBackColor = true;
            this.buttonDecrypt.Click += new System.EventHandler(this.buttonDecrypt_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(9, 21);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(41, 12);
            this.label7.TabIndex = 0;
            this.label7.Text = "密文C=";
            // 
            // textBoxC2
            // 
            this.textBoxC2.Location = new System.Drawing.Point(49, 19);
            this.textBoxC2.Name = "textBoxC2";
            this.textBoxC2.Size = new System.Drawing.Size(63, 21);
            this.textBoxC2.TabIndex = 6;
            this.textBoxC2.Text = "?";
            this.textBoxC2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(330, 419);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "RSA算法示例";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownP)).EndInit();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.TextBox textBoxGuide;
        private System.Windows.Forms.TextBox textBoxDN;
        private System.Windows.Forms.TextBox textBoxEN;
        private System.Windows.Forms.TextBox textBoxPQ;
        private System.Windows.Forms.Button buttonCreateKey;
        private System.Windows.Forms.ListBox listBoxQ;
        private System.Windows.Forms.ListBox listBoxP;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox textBoxC1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button buttonEncrypt;
        private System.Windows.Forms.NumericUpDown numericUpDownP;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBoxP;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Button buttonDecrypt;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox textBoxC2;
    }
}

