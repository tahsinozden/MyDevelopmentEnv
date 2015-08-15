using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace newGraphicsv2
{
    public partial class Form3 : Form
    {
        public Form3()
        {
            InitializeComponent();
        }
        public Form3(string a,double kalp, double solunum, double sicaklik)
        {
            InitializeComponent();
            path = a;
            Kalp = kalp;
            Solunum = solunum;
            Sicaklik = sicaklik;

        }
        string path = "";
        double Kalp; double Solunum; double Sicaklik;
        private void Form3_Load(object sender, EventArgs e)
        {
            
            pictureBox1.Image = new Bitmap(@path);
            lblKalpOrtalama.Text = (Math.Round(Kalp)).ToString();
            lblSolunumOrtalama.Text = (Math.Round(Solunum)).ToString();
            lblSicaklikOrtalama.Text = ((int)Sicaklik).ToString() + "." + ((int)((100*Sicaklik) % 100)).ToString() + " °C";
            
        }
        frmAboutMenu yeni = new frmAboutMenu();
        
        int say = 0;
        
        
        private void timer1_Tick(object sender, EventArgs e)
        {
            
            if (say == 4)
            {
                yeni.Close();
            }
            say++;
        }

        private void Form3_FormClosed(object sender, FormClosedEventArgs e)
        {
            //yeni.ShowDialog();

            //timer1.Start();

            
        }

        private void Form3_FormClosing(object sender, FormClosingEventArgs e)
        {
            this.Hide();
            yeni.ShowDialog();
            
            timer1.Start();
        }

       
    }
}
