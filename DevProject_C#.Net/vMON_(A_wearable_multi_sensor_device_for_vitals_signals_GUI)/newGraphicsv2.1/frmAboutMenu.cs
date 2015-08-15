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
    public partial class frmAboutMenu : Form
    {
        public frmAboutMenu()
        {
            InitializeComponent();
        }

        private void frmAboutMenu_Load(object sender, EventArgs e)
        {
            string a = "This software was developed for EEE 499 Graduation Project \n\n" + 
            "Title: A wearable multisensor device for vital signals\n\n" + 
            "Overview of the project\n" +
            "The project aims to design and construction of a wearable multisensor device for remote monitoring and keeping record of vital signals during mild physical exercise like jogging. Vital signals to be measured are pulse rate, respiratory rate, and body temperature. Signals will be sensed and transmitted to a computer in a remote location in real time during exercise. For each signal, data history will be saved in computer harddrive in a suitable format.";
            richTextBox1.Text = a;
            label3.Text = " > İBRAHİM ÇELEBİ\n" + " > KAZIM DEMİR\n" + " > TAHSİN ÖZDEN\n";
            label4.Text = "GAZİANTEP UNIVERSITY";
            label5.Text = "ELECTRICAL AND ELECTRONICS ENGINEERING DEPARTMENT";
            pictureBox1.Image = Properties.Resources.igantep3;
        }
    }
}
