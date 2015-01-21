using System;
using System.Windows.Forms;

namespace RSA算法示例
{
    public partial class Form1 : Form
    {
        //所有参数都在这里
        private long _numN, _numP, _numQ, _numE, _numD, _numPhi, _msgP, _msgC;

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
            textBoxC1.Text = @"?";
            textBoxC2.Text = @"?";
            textBoxP.Text = @"?";
        }

        private void buttonCreateKey_Click(object sender, EventArgs e)
        {
            //按下产生密钥时先清空密文C1和明文P位
            textBoxC1.Text = @"?";
            textBoxP.Text = @"?";
            //然后开始计算
            _numP = Convert.ToInt64(listBoxP.SelectedItem);
            _numQ = Convert.ToInt64(listBoxQ.SelectedItem);
            _numN = _numP*_numQ;
            _numPhi = (_numP - 1)*(_numQ - 1);
            textBoxPQ.Text = @"n = pq = " + _numN;
            _numE = CalcE(_numPhi);
            textBoxEN.Text = @"公钥为{e,n} = { " + _numE + @", " + _numN + @"}";
            _numD = CalcD(_numPhi, _numE);
            textBoxDN.Text = @"私钥为{d,n} = { " + _numD + @", " + _numN + @"}";
        }

        private static long CalcE(long phi)
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
//             Random Re = new Random();
//             //从质数表中随机抽取，但是有时会发生响应问题，故不采用
//             while(true)
//             {
//                 int i = Re.Next(218);
//                 if (prime[i] < phi)
//                 {
//                     return prime[i];
//                 }
//             }
            //取而代之的是返回固定值5
            return 5;
        }

        private static long CalcD(long phi, long e)
        {
            //模取余运算
            for (long d = 1;; d++)
            {
                if ((e*d)%phi == 1)
                {
                    return d;
                }
            }
        }

        private long Encrypt(long e, long n)
        {
            //返回C = P^e mod n
            return (long) (Math.Pow(Convert.ToInt64(numericUpDownP.Value),e))%n;
        }

        private long Decrypt(long dd, long n)
        {
            //返回P = C^d mod n
            long ans = 1;
            long a = Convert.ToInt64(textBoxC2.Text);
            long b = dd;
            long c = n;
            a = a%c;
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
            _msgC = Encrypt(_numE, _numN);
            textBoxC1.Text = _msgC.ToString();
            textBoxC2.Text = _msgC.ToString();
        }

        private void buttonDecrypt_Click(object sender, EventArgs e)
        {
            _msgP = Decrypt(_numD, _numN);
            textBoxP.Text = _msgP.ToString();
        }
    }
}
