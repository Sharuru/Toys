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
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
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
            this.groupBox1.Size = new System.Drawing.Size(286, 235);
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
            this.buttonCreateKey.Size = new System.Drawing.Size(189, 32);
            this.buttonCreateKey.TabIndex = 3;
            this.buttonCreateKey.Text = "产生密钥";
            this.buttonCreateKey.UseVisualStyleBackColor = true;
            // 
            // textBoxPQ
            // 
            this.textBoxPQ.Location = new System.Drawing.Point(91, 74);
            this.textBoxPQ.Name = "textBoxPQ";
            this.textBoxPQ.Size = new System.Drawing.Size(189, 21);
            this.textBoxPQ.TabIndex = 4;
            // 
            // textBoxEN
            // 
            this.textBoxEN.Location = new System.Drawing.Point(91, 101);
            this.textBoxEN.Name = "textBoxEN";
            this.textBoxEN.Size = new System.Drawing.Size(189, 21);
            this.textBoxEN.TabIndex = 5;
            // 
            // textBoxDN
            // 
            this.textBoxDN.Location = new System.Drawing.Point(91, 128);
            this.textBoxDN.Name = "textBoxDN";
            this.textBoxDN.Size = new System.Drawing.Size(189, 21);
            this.textBoxDN.TabIndex = 6;
            // 
            // textBoxGuide
            // 
            this.textBoxGuide.Location = new System.Drawing.Point(7, 156);
            this.textBoxGuide.Multiline = true;
            this.textBoxGuide.Name = "textBoxGuide";
            this.textBoxGuide.Size = new System.Drawing.Size(273, 72);
            this.textBoxGuide.TabIndex = 7;
            this.textBoxGuide.Text = "1.选择2个保密的大素数p和q;\r\n2.计算n=p·q和欧拉函数值φ(n)=(p-1)(q-1); \r\n3.选一整数e,且满足1<e<φ(n)和gcd(φ(n)," +
    "e)=1;\r\n4.计算d，且满足d·e=1 mod φ(n);\r\n5.公钥为{e,n}, 私钥为{d,n}";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Location = new System.Drawing.Point(12, 254);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(286, 72);
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
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(310, 379);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "RSA算法示例";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
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
    }
}

