using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace RSA算法示例
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //程序Load时，自动选择2个素数，默认p=7，q=17
            listBoxP.SelectedIndex = 1;
            listBoxQ.SelectedIndex = 5;
            //程序Load时，自动生成一个明文P，默认P=19
            numericUpDownP.Value = 19;
            //程序Load时，将密文C以及解密明文P置问号
            textBoxC1.Text = "?";
            textBoxC2.Text = "?";
            textBoxP.Text = "?";
        }

        private void buttonCreateKey_Click(object sender, EventArgs e)
        {
            //按下“产生密钥”后开始计算
            long n, p, q, ee, dd, phi;
            p = Convert.ToInt64(listBoxP.SelectedItem);
            q = Convert.ToInt64(listBoxQ.SelectedItem);
            n = p*q;
            phi = (p - 1)*(q - 1);
            textBoxPQ.Text = "n = pq = " + n;
            ee = CalcE(phi);
            textBoxEN.Text = "公钥为{e,n} = { " + ee + ", " + n + "}";
            dd = CalcD(phi, ee);
            textBoxDN.Text = "私钥为{d,n} = { " + dd + ", " + n + "}";
        }

        private long CalcE(long phi)
        {
            //由于两个不相同的质数必定互素，为提高计算效率预先硬编码1~37*37的质数表
            long[] prime =
            {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
                101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193,
                197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307,
                311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421,
                431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547,
                557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
                661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797,
                809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929,
                937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039,
                1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151,
                1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237, 1249, 1259,
                1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367
            };
            //随机选择e
            Random Re = new Random();
            return 5;
//             while(true)
//             {
//                 int i = Re.Next(218);
//                 if (prime[i] < phi)
//                 {
//                     return prime[i];
//                 }
//             }
        }

        private long CalcD(long phi, long ee)
        {
            for (long d = 1;; d++)
            {
                if ((ee*d)%phi == 1)
                {
                    return d;
                }
            }
        }

        private long Encrypt(long ee, long n)
        {
            return (long) (Math.Pow(Convert.ToInt64(numericUpDownP.Value),ee))%n;
        }

        private long Decrypt(long dd, long n)
        {
            //return (long) (Math.Pow(Convert.ToInt64(textBoxC2.Text),dd));
            long ans = 1;
            long a = Convert.ToInt64(textBoxC2.Text);
            long c = n;
            a = a%c;
            long b = dd;
            while (b > 0)
            {
                if (b%2 == 1)
                {
                    ans = (ans*a)%c;
                }
                b = b / 2;
                a = (a * a) % c;
            }
            return ans;
        }

        private void buttonEncrypt_Click(object sender, EventArgs e)
        {
            long n, p, q, ee, dd, phi, cc;
            p = Convert.ToInt64(listBoxP.SelectedItem);
            q = Convert.ToInt64(listBoxQ.SelectedItem);
            n = p*q;
            phi = (p - 1)*(q - 1);
            ee = CalcE(phi);
            dd = CalcD(phi, ee);
            cc = Encrypt(5, n);
            textBoxC1.Text = cc.ToString();
            textBoxC2.Text = cc.ToString();
        }

        private void buttonDecrypt_Click(object sender, EventArgs e)
        {
            long n, p, q, ee, dd, phi, cc;
            p = Convert.ToInt64(listBoxP.SelectedItem);
            q = Convert.ToInt64(listBoxQ.SelectedItem);
            n = p * q;
            phi = (p - 1) * (q - 1);
            ee = CalcE(phi);
            dd = CalcD(phi, ee);
            p = Decrypt(dd, n);
            textBoxP.Text = p.ToString();
        }
    }
}
