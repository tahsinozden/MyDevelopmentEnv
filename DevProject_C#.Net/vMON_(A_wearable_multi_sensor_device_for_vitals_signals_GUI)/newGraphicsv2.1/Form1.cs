using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO.Ports;

namespace newGraphicsv2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        
        int[] bRates = {2400,4800,9600,19200,31250,38400,57600,115200} ;
        string[] yasaGoreKalp = {"100 - 160", "60 - 140", "60 - 100", "40 - 60" };
        string[] yasaGoreSolunum = {"30 - 60", "24 - 40", "22 - 34", "18 - 30", "12 - 16", "12 - 20" };
        public SerialPort port = new SerialPort();
        string[] portlar = SerialPort.GetPortNames();
        private void Form1_Load(object sender, EventArgs e)
        {
            textBox1.Click += new EventHandler(textBox1_Click);
            string acilabilenPortlar = "";
            for (int i = 0; i < portlar.Length; i++)
            {
                try
                {
                    port.PortName = portlar[i];
                    port.Open();
                    if (port.IsOpen)
                    {
                        acilabilenPortlar += port.PortName + "  ";
                        cmbPortlar.Items.Add(port.PortName);
                    }

                }
                catch (Exception hata)
                {


                }
                port.Close();
            }
            MessageBox.Show("Accessible Ports : " + acilabilenPortlar);

            
        }

        void textBox1_Click(object sender, EventArgs e)
        {
            textBox1.Text = "";
        }
        int yas;
        private void button1_Click(object sender, EventArgs e)
        {
            
            if (textBox1.Text == "Your Age" || textBox1.Text == "")
            {
                MessageBox.Show("Input your age!!!");
            }
            else
            {
                yas = Convert.ToInt32(textBox1.Text);
                degerBelirle(yas);
                port.PortName = cmbPortlar.SelectedItem.ToString();
                port.Open();
                port.BaudRate = 9600;

                MessageBox.Show(port.PortName + " is OPEN.\nBaud Rate : " + port.BaudRate.ToString());

                Form2 yeni = new Form2(port, kalpDeger, solunumDeger);
                this.Hide();
                this.FormClosed += new FormClosedEventHandler(Form1_FormClosed);
                this.Close();
            }
            
            
            
            
        }
        string kalpDeger;
        string solunumDeger;
        void degerBelirle(int Yas)
        {
            if (Yas < 1)
            {
                kalpDeger = yasaGoreKalp[0];
                solunumDeger = yasaGoreSolunum[0];
            }
            else if(Yas >= 1 && Yas < 3)
            {
                kalpDeger = yasaGoreKalp[1];
                solunumDeger = yasaGoreSolunum[1];
            }
            else if (Yas >= 3 && Yas < 6)
            {
                kalpDeger = yasaGoreKalp[1];
                solunumDeger = yasaGoreSolunum[2];
            }
            else if (Yas >= 6 && Yas <12)
            {
                kalpDeger = yasaGoreKalp[1];
                solunumDeger = yasaGoreSolunum[3];
            }
            else if (Yas >= 12 && Yas < 16)
            {
                kalpDeger = yasaGoreKalp[2];
                solunumDeger = yasaGoreSolunum[4];
            }
            else if (Yas >= 17)
            {
                kalpDeger = yasaGoreKalp[2];
                solunumDeger = yasaGoreSolunum[5];
            }
        
        }
        void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            Form2 yeni = new Form2(port,kalpDeger,solunumDeger);
            yeni.ShowDialog();
        }

        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            
        }

        private void aboutVMonitorToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmAboutMenu yeni = new frmAboutMenu();
            yeni.Show();
        }



    }
}
