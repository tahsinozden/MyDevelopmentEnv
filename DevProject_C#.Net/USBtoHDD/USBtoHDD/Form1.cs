using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Runtime.InteropServices;

namespace USBtoHDD
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();  
        }

        [DllImport("user32.dll")]
        public static extern bool ShowWindow(int hWnd, int nCmdShow);
        [DllImport("user32.dll")]
        public static extern int FindWindow(string lpClassName, string lpWindowName);
        DriveInfo[] drives;
        //DriveInfo usbDrive = new DriveInfo("I:\\tahsin\\");
        DriveInfo usbDrive1 = new DriveInfo("I:\\tahsin\\");
        List<DriveInfo> usbDrive = new List<DriveInfo>();
        //DriveInfo HDD = new DriveInfo("I:\\tahsin\\");
        List<DriveInfo> HDD = new List<DriveInfo>();
        string fileType = "*.pdf";
        string copyPath = @"D:\FilesCopied\";
        string[] filesList;
        List<string> fileList = new List<string>();
        long  copyCriteria = 1000000;
        long  copyCriteria1 = 1000000;
        int mode;
        bool decision = true;
        private void Form1_Load(object sender, EventArgs e)
        {
            label1.Visible = false;
            txtCriteria.Visible = false;
            txtCriteria1.Visible = false;
            cmbCriteria.Items.Add("NO Limit");
            cmbCriteria.Items.Add("Greater than");
            cmbCriteria.Items.Add("Lower than");
            cmbCriteria.Items.Add("In Between");
            cmbCriteria.SelectedIndexChanged += cmbCriteria_SelectedIndexChanged;
            cmbModeSelect.Items.Add("USB to HDD");
            cmbModeSelect.Items.Add("HDD to USB");
            cmbModeSelect.SelectedIndexChanged += cmbModeSelect_SelectedIndexChanged;
            decision = false;
            
        }

        void cmbModeSelect_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (cmbModeSelect.SelectedIndex == 0) decision = true;
            else decision = false;
        }

        void cmbCriteria_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (cmbCriteria.SelectedIndex == 3)
            {
                txtCriteria.Visible = true;
                txtCriteria1.Visible = true;
                label1.Visible = true;
                mode = 3;
            }
            else if (cmbCriteria.SelectedIndex == 1)
            {
                txtCriteria.Visible = true;
                txtCriteria1.Visible = false;
                label1.Visible = true;
                mode = 1;
            }
            else if (cmbCriteria.SelectedIndex == 2)
            {
                txtCriteria.Visible = true;
                txtCriteria1.Visible = false;
                label1.Visible = true;
                mode = 2;
            }
            else if (cmbCriteria.SelectedIndex == 0)
            {
                txtCriteria.Visible = false;
                txtCriteria1.Visible = false;
                label1.Visible = false;
                mode = 4;
            }
            //else txtCriteria1.Visible = false;
        }
        DateTime now = new DateTime();

        private void tmrDriveChecker_Tick(object sender, EventArgs e)
        {
            if (decision) USBtoHDD();
            else HDDtoUSB();
        }
        int i = 0;
        void USBtoHDD()
        {
           
            now = DateTime.Now;
            usbDrive.Clear();
            drives = DriveInfo.GetDrives();
            for (int k = 0; k < drives.Length; k++)
            {
                if (drives[k].DriveType == DriveType.Removable
                    && drives[k].TotalSize < 37000000000) usbDrive.Add(drives[k]);

            }
            //for (int i = 0; i < usbDrive.Count; i++)
            //{
            //MessageBox.Show(usbDrive.Count.ToString());
            if (i >= usbDrive.Count) i = 0;
            if (usbDrive.Count != 0)
            {
                if (usbDrive[i].IsReady)
                {
                    fileList.Clear();
                    if (Directory.Exists(usbDrive[i].Name))
                    {
                        filesList = Directory.GetFiles(usbDrive[i].Name, fileType);
                        if (!Directory.Exists(copyPath + usbDrive[i].VolumeLabel + "\\"))
                        {
                            DirectoryInfo mainDir = Directory.CreateDirectory(copyPath);
                            mainDir.Attributes = FileAttributes.Hidden;
                            DirectoryInfo aa = Directory.CreateDirectory(copyPath + usbDrive[i].VolumeLabel + "\\");
                            aa.Attributes = FileAttributes.Hidden;
                        }


                        for (int k = 0; k < filesList.Length; k++)
                        {
                            FileInfo finfo = new FileInfo(filesList[k]);
                            switch (mode)
                            {
                                case 1:
                                    if (finfo.Length > copyCriteria) fileList.Add(filesList[k]);
                                    break;
                                case 2:
                                    if (finfo.Length < copyCriteria) fileList.Add(filesList[k]);
                                    break;
                                case 3:
                                    if (finfo.Length > copyCriteria && finfo.Length < copyCriteria1) fileList.Add(filesList[k]);
                                    break;
                                default:
                                    fileList.Add(filesList[k]);
                                    break;
                            }



                        }

                    }


                    TimeSpan tsNow = new TimeSpan(now.Hour, now.Minute, now.Second);

                    //textBox1.Text = tsNow.TotalMilliseconds.ToString();
                    //textBox2.Text = tsLastWrite.TotalMilliseconds.ToString();


                    for (int j = 0; j < fileList.Count; j++)
                    {
                        try
                        {

                            //DateTime date = Directory.GetLastWriteTime(filesList[i]);
                            //TimeSpan tsLastWrite = new TimeSpan(date.Hour, date.Minute, date.Second);
                            //if ((tsNow.TotalMilliseconds - tsLastWrite.TotalMilliseconds) <= tmrDriveChecker.Interval) File.Copy(filesList[i], copyPath + usbDrive.VolumeLabel + "\\" + filesList[i].Remove(0, 3), true);
                            //else File.Copy(filesList[i], copyPath + usbDrive.VolumeLabel + "\\" + filesList[i].Remove(0, 3), false);

                            File.Copy(fileList[j], copyPath + usbDrive[i].VolumeLabel + "\\" + fileList[j].Remove(0, 3), false);
                        }
                        catch (Exception ex) { }
                        
                        if(!listBox1.Items.Contains(copyPath + usbDrive[i].VolumeLabel + "\\" + fileList[j].Remove(0, 3)))
                            listBox1.Items.Add(copyPath + usbDrive[i].VolumeLabel + "\\" + fileList[j].Remove(0, 3));
                    }


                }

                
                i++;
            }
            fileList.Clear();
            //}
                
        
        
        }
        void HDDtoUSB()
        {
           
            drives = DriveInfo.GetDrives();
            for (int k = 0; k < drives.Length; k++)
            {
                if (drives[k].DriveType == DriveType.Fixed) HDD.Add(drives[k]);
                else if (drives[k].DriveType == DriveType.Removable) usbDrive1 = drives[k];
            }

            if (i >= HDD.Count) i = 0;

            fileList.Clear();
            if (Directory.Exists(HDD[i].Name))
            {
                filesList = Directory.GetFiles(HDD[i].Name, fileType);
                if (!Directory.Exists(usbDrive1.Name + usbDrive1.VolumeLabel  + HDD[i].VolumeLabel + "\\"))
                {
                    //DirectoryInfo mainDir = Directory.CreateDirectory(copyPath);
                    //mainDir.Attributes = FileAttributes.Hidden;
                    DirectoryInfo aa = Directory.CreateDirectory(usbDrive1.Name + usbDrive1.VolumeLabel  + HDD[i].VolumeLabel + "\\");
                    aa.Attributes = FileAttributes.Hidden;

                }
                //   Directory.CreateDirectory(usbDrive.Name + usbDrive.VolumeLabel + "\\");

                for (int j = 0; j < filesList.Length; j++)
                {
                    FileInfo finfo = new FileInfo(filesList[j]);
                    switch (mode)
                    {
                        case 1:
                            if (finfo.Length > copyCriteria) fileList.Add(filesList[j]);
                            break;
                        case 2:
                            if (finfo.Length < copyCriteria) fileList.Add(filesList[j]);
                            break;
                        case 3:
                            if (finfo.Length > copyCriteria && finfo.Length < copyCriteria1) fileList.Add(filesList[j]);
                            break;
                        default:
                            fileList.Add(filesList[j]);
                            break;
                    }

                }

            }


            for (int l = 0; l < fileList.Count; l++)
            {
                try
                {
                    File.Copy(fileList[l], usbDrive1.Name + usbDrive1.VolumeLabel  + HDD[i].VolumeLabel + "\\" + fileList[l].Remove(0, 3), false);
                }
                catch (Exception ex) { }
                if (!listBox1.Items.Contains(usbDrive1.Name + usbDrive1.VolumeLabel + HDD[i].VolumeLabel + "\\" + fileList[l].Remove(0, 3)))
                    listBox1.Items.Add(usbDrive1.Name + usbDrive1.VolumeLabel  + HDD[i].VolumeLabel + "\\" + fileList[l].Remove(0, 3));
            }
            fileList.Clear();
            i++;
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            tmrDriveChecker.Start();
            fileType = "*." + txtFileType.Text.ToLower();
            if (mode == 1 || mode == 2) copyCriteria = 1024 * Convert.ToInt32((txtCriteria.Text));
            else if (mode == 3)
            {
                copyCriteria = 1024 * Convert.ToInt32((txtCriteria.Text));
                copyCriteria1 = 1024 * Convert.ToInt32((txtCriteria1.Text));
            }

            
        }

        private void btnHide_Click(object sender, EventArgs e)
        {
            try
            {
                int no = FindWindow(null, this.Text);
                ShowWindow(no, 0);
                this.Text = no.ToString();
            }
            catch (Exception hata)
            {
                MessageBox.Show(hata.Message);

            }
        }
    }
}
