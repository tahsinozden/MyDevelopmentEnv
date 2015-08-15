using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Media;
using System.IO.Ports;
using Microsoft.Office.Interop.Excel;
using Excel = Microsoft.Office.Interop.Excel;
using System.IO;
namespace newGraphicsv2
{
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }
        SerialPort port;
        string normalKalpDeger;
        string normalSolunumDeger;
        public Form2(SerialPort gelenPort, string k, string s)
        {
            InitializeComponent();
            port = gelenPort;
            lblPortName.Text = port.PortName;
            normalKalpDeger = k;
            normalSolunumDeger = s;
        }
        // datareceived içinde label i kullanabilmek için
        delegate void updateLabelTextDelegate(string newText);
        private void updateLabelText(string newText)
        {
            if ( lblKalpAtim.InvokeRequired) 
            {
                // worker thread
                updateLabelTextDelegate del = new updateLabelTextDelegate(updateLabelText);
                lblKalpAtim.Invoke(del, new object[] { newText });
            }
            else
            {
                // UI thread
                lblKalpAtim.Text = newText;
            }
        }
        // Cross-thread operation not valid hatsının çözümü

        delegate void updateLabelTextDelegate1(string newText);
        private void updateLabelTextSolunum(string newText)
        {
            if (lblKalpAtim.InvokeRequired)
            {
                // worker thread
                updateLabelTextDelegate1 del = new updateLabelTextDelegate1(updateLabelTextSolunum);
                lblSolunum.Invoke(del, new object[] { newText });
            }
            else
            {
                // UI thread
                lblSolunum.Text = newText;
            }
        }

        delegate void updateLabelTextDelegate2(string newText);
        private void updateLabelTextSicaklik(string newText)
        {
            if (lblSicaklik.InvokeRequired)
            {
                // worker thread
                updateLabelTextDelegate2 del = new updateLabelTextDelegate2(updateLabelTextSicaklik);
                lblSicaklik.Invoke(del, new object[] { newText });
            }
            else
            {
                // UI thread
                lblSicaklik.Text = newText;
            }
        }


        Graphics cizim;
        Graphics cizim2;
        Graphics cizim3;
        Pen kalem;
        Pen kalem1;
        Pen kalem2;
        Pen beyazKalem;
        Pen inceKalem;
       
        List<Graphics> cizimler = new List<Graphics>();
        List<Graphics> cizimler2 = new List<Graphics>();
        
        
        
        
        List<int> boyutlar = new List<int>();
        List<int> boyutlarSolunum = new List<int>();
        SoundPlayer ses = new SoundPlayer();

        TimeSpan simdi;
        TimeSpan simdi1;
        private void Form2_Load(object sender, EventArgs e)
        {
            kalem = new Pen(Color.Black, 4);
            kalem1 = new Pen(Color.Black, 4);
            kalem2 = new Pen(Color.Black, 4);
            beyazKalem = new Pen(Color.White, 2);
            inceKalem = new Pen(Color.Black, 2);
            
            pictureBox1.BackColor = Color.White;
            cizim = pictureBox1.CreateGraphics(); // arka plan çizimi
            cizimler.Add(pictureBox1.CreateGraphics()); // ön plandaki sinyallerin çizimi
            boyutlar.Add(0); // birinci sinyalin konum bilgisi
            
            
            pictureBox2.BackColor = Color.White;
            cizim2 = pictureBox2.CreateGraphics(); // arka plan çizimi
            cizimler2.Add(pictureBox2.CreateGraphics()); // ön plandaki sinyallerin çizimi
            boyutlarSolunum.Add(0);
            
            pictureBox3.BackColor = Color.White;
            cizim3 = pictureBox3.CreateGraphics(); // arka plan çizimi

            
            
            int aralik = 20; // aralık azalırsa ekran daha hızlı yenilenir ve şekil de hızlanır
            timer1.Interval = aralik;
            timer2.Interval = aralik;
            timer3.Interval = aralik;
            timer4.Interval = aralik;
            timer5.Interval = aralik;
            timer1.Start();
            timer4.Start();
            timer5.Start();
            tmrVeriAlma.Start();
            ses.Stream = Properties.Resources.beep_7;
            lblPortName.Text = port.PortName;
            simdi = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
            simdi1 = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
            mag.Add(0);
            hizAyariKalp = 50;
            hizAyariSolunum = 50;
            hizAyariSicaklik = 1;
            //hScrollBar1.SetBounds(20, 80, 176, 17);
            
            vScrollBar1.Maximum = 90;
            vScrollBar1.Minimum = 40; // gelen pulse ların doğru işlenebilmesi için 35 den büyük olmalı
            vScrollBar2.Maximum = 90;
            vScrollBar2.Minimum = 40; // gelen pulse ların doğru işlenebilmesi için 20 den büyük olmalı
            vScrollBar3.Maximum = 10;
            vScrollBar3.Minimum = 1;
            
            vScrollBar1.Value = vScrollBar1.Maximum - hizAyariKalp + 40;
            vScrollBar2.Value = vScrollBar2.Maximum - hizAyariSolunum + 40;
            vScrollBar3.Value = vScrollBar3.Maximum - hizAyariSicaklik;
            vScrollBar1.Scroll += new ScrollEventHandler(vScrollBar1_Scroll);
            vScrollBar2.Scroll += new ScrollEventHandler(vScrollBar2_Scroll);
            vScrollBar3.Scroll += new ScrollEventHandler(vScrollBar3_Scroll);
            lblSicaklikReferans.Text = "     36.5";
            lblKalpZamanCizgisi.BackColor = Color.Red;
            //string a = "";
            //for (int i = 1; a.Length < pictureBox1.Width; i++)
            //{
            //    for (int j = 0; j < (pictureBox1.Width / (12 / (hizAyariKalp / vScrollBar1.Minimum))) - 15; j++)
            //    {
            //        a = a + " ";
            //    }
            //    a = a + ((double)i / 2.0).ToString();
            //}

            //lblKalpZamanCizgisi.Text = a;                                                                     
            zamanCizgisi(pictureBox1, hizAyariKalp, vScrollBar1, lblKalpZamanCizgisi);
            zamanCizgisi(pictureBox2, hizAyariSolunum, vScrollBar2, lblSolunumZamanCizgisi);
            zamanCizgisi(pictureBox3, hizAyariSicaklik, vScrollBar3, lblSicaklikZamanCizgisi);

            updateLabelText("0");
            updateLabelTextSolunum("0");
            tmrKalpAtimYokBelirle.Start(); // kalp durma kontrol başlatma
            tmrSolukYokBelirle.Start();  // soluk durma kontrol başlatma
            label8.Text = normalKalpDeger + " BPM";
            label9.Text = normalSolunumDeger + " PPM";
        }
        void zamanCizgisi(PictureBox pbox,int ayar,VScrollBar vbar,System.Windows.Forms.Label lbl) //  zaman çizgisi fonksiyonu
        {
            string a = "";
            for (int i = 1; a.Length < pbox.Width; i++) // a string inin boyu picturebox ın uzunluğundan küçük olacak
            {
                for (int j = 0; j < (pbox.Width / (12 / (ayar / vbar.Minimum))) - 15; j++) // 12 ye bölmemin sebebi 0.5 saniye aralıklarla rakamları yerleştirmek. denemelerle buldum. 1 saniye aralık için 6 ya bölünür
                {                                                                           // (ayar / vbar.Minimum) oranı minumum değerde 1 olması için yaptım 40 / 40 = 1 80 / 40 = 2 => 12 / 1 = 12 , 12 / 2 = 6 o zaman ekranda daha az sayı görünecek
                    a = a + " ";
                }
                a = a + ((double)i / 2.0).ToString();
            }

            lbl.Text = a;   
        }
        void vScrollBar3_Scroll(object sender, ScrollEventArgs e)
        {
            hizAyariSicaklik = vScrollBar3.Maximum - vScrollBar3.Value + 1;
            //zamanCizgisi(pictureBox3, hizAyariSicaklik, vScrollBar3, lblSicaklikZamanCizgisi);
        }

        void vScrollBar2_Scroll(object sender, ScrollEventArgs e)
        {
            hizAyariSolunum = vScrollBar2.Maximum - vScrollBar2.Value + vScrollBar2.Minimum;
            zamanCizgisi(pictureBox2, hizAyariSolunum, vScrollBar2, lblSolunumZamanCizgisi);
        }

        void vScrollBar1_Scroll(object sender, ScrollEventArgs e)
        {
            hizAyariKalp = vScrollBar1.Maximum - vScrollBar1.Value + 40;
            zamanCizgisi(pictureBox1, hizAyariKalp, vScrollBar1, lblKalpZamanCizgisi);
     
        }

        

        TimeSpan sonra;
        TimeSpan sonra1;
        double aralik;
        double aralikSolunum;
        List<int> mag = new List<int>();
        

        private void timer1_Tick(object sender, EventArgs e)
        {
            cizim.Clear(Color.White);
            cizim2.Clear(Color.White);
            
            timer1.Stop();
            timer2.Start();
        }

        private void timer3_Tick(object sender, EventArgs e)
        {
            cizim.Clear(Color.White);
            cizim2.Clear(Color.White);
            
            timer3.Stop();
            timer1.Start();
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            grafikCiz(10, 100, 60, 15, 30, 5);
            grafikCizSolunum(10, 100, 20, 15);
            
            timer2.Stop();
            timer3.Start();
        }

        private void timer4_Tick(object sender, EventArgs e) // seri portu gelen veri için kontrol ediyor
        {
            port.DataReceived += new SerialDataReceivedEventHandler(port_DataReceived); 
        }
        
        char[] veri = new char[1000];
       

        List<int> kalpAtim = new List<int>();
        List<int> solukAlma = new List<int>();
        List<double> vucutSicaklik = new List<double>();
        int kalp = 0, soluk = 0;
        double vSicaklik;
        string alinanVeri = "";
        int ilkSay = 0;
        int ilkSayKalp = 0;
        int ilkSaySolunum = 0; 
        double tempDerece = 0;
        int sicaklikDegisimTolerans = 1;
        int kalpDegisimTolerans = 2;
        int solunumDegisimTolerans = 2;
        int tempKalp;
        int tempSoluk;
        int temSolunum;
        void port_DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            //port.Read(veri, 0, 1);
           
            try
            {
                alinanVeri = port.ReadLine();
                if (boyutlar[boyutlar.Count - 1] > 160 && alinanVeri == "x")
                {
                    deadAktifEt = true;
                    kalpVeriKontrolEt = false;
                    kalpKarar = 2;
                    boyutlar.Add(0); // grafik çizilirken son gelenin ilk başta görünmesi için 0 değeri verilir
                    ses.Play();
                    cizimler.Add(pictureBox1.CreateGraphics());


                    // Kalp atım sıklığı
                    sonra = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
                    aralik = sonra.TotalSeconds - simdi.TotalSeconds;
                    simdi = sonra;
                    //kalp = (int)(60.0 / aralik);
                    int tem = (int)(60.0 / aralik);
                    if (tem < 120 && tem> 35)
                    {
                        if (ilkSayKalp == 3)
                        {
                            tempKalp = tem;
                            kalp = tempKalp;
                            
                            updateLabelText(tempKalp.ToString());
                        }
                        else if (Math.Abs(tempKalp - tem) < kalpDegisimTolerans && ilkSayKalp > 3) // kalp aralık : 35 - 200
                        {
                            
                            tempKalp = tem;
                            kalp = tempKalp;
                            kalpDegisimTolerans = 2;
                            updateLabelText(tempKalp.ToString());
                            //kalp = (int)(60.0 / aralik);
                            
                        }

                        else
                        {
                            kalp = tempKalp;
                            kalpDegisimTolerans = kalpDegisimTolerans + 2;
                        }

                        ilkSayKalp++;
                    
                    }
                    
                }
                else if (boyutlarSolunum[boyutlarSolunum.Count - 1] > 20 && alinanVeri == "y")
                {
                    
                    boyutlarSolunum.Add(0);
                    cizimler2.Add(pictureBox2.CreateGraphics());

                    // soluk alıp verme sıklığı
                    sonra1 = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
                    aralikSolunum = sonra1.TotalSeconds - simdi1.TotalSeconds;
                    simdi1 = sonra1;
                    temSolunum = (int)(60.0 / aralikSolunum);
                    
                    if (temSolunum < 70 )
                    {
                        if (ilkSaySolunum == 3) // ilk 3 saniye veri alınmıyor
                        {
                            tempSoluk = temSolunum;
                            soluk = tempSoluk; // ilk veri alınıyor

                            updateLabelTextSolunum(tempSoluk.ToString());
                        }
                        else if (Math.Abs(tempSoluk - temSolunum) < solunumDegisimTolerans && ilkSaySolunum > 3) // gelen veri toleranstan küçükse veri saklanıyor
                        {

                            tempSoluk = temSolunum;
                            soluk = tempSoluk;
                            solunumDegisimTolerans = 2;
                            updateLabelTextSolunum(tempSoluk.ToString());

                        }

                        else
                        {
                            soluk = tempSoluk;
                            solunumDegisimTolerans = solunumDegisimTolerans + 10;
                        }
                        ilkSaySolunum++;

                    }

                }

                else if (System.Char.IsNumber(alinanVeri, 0))
                {
                    double derece = double.Parse(alinanVeri) ;
                    int ilkKısım = (int)derece / 100;
                    int kalan = (int)derece % 100;
                    derece = Math.Round(derece / 100.0, 2);

                    if (derece >= 20 && derece < 40)
                    {

                        if (ilkSay == 0)
                        {
                            vSicaklik = derece;
                            sonY = 100 + 4 * (36 - (int)derece );
                 
                            tempDerece = derece;
                            updateLabelTextSicaklik((ilkKısım ) + "." + (kalan) + "°C");//******
                        }
                        else if ((ilkSay != 0) && Math.Abs(tempDerece - derece) < sicaklikDegisimTolerans)
                        {
                            vSicaklik = derece;
                            sonY = 100 + 4 * (36 - (int)derece );

                            tempDerece = derece;
                            updateLabelTextSicaklik((ilkKısım ) + "." + (kalan) + "°C"); //****
                            sicaklikDegisimTolerans = 1; // tolerans default değerine çekiliyor
                        }
                        else
                        {
                            vSicaklik = tempDerece;
                            sicaklikDegisimTolerans++; // Tolerans arttırılıyor

                        }

                        ilkSay++;
                    }
                }
                
            }
            catch (Exception a)
            {
                
               
            }
           
            
           
        }

        int hizAyariKalp; // hızayarı artarsa şekil daha hızlı hareket eder ve daha çok pulse yakalar (filtre daha geçirgen olur)
        int hizAyariSolunum;
        int hizAyariSicaklik;
        void grafikCiz(int x,int y,int ilkBoy,int ilkEn,int ikinciBoy,int ikinciEn)
        {
            
            cizim.DrawLine(inceKalem, 0, y, 1000, y); // düz çizgi
            for (int i = 0; i < cizimler.Count; i++)
            {
                if (i == cizimler.Count - 1)
                {
                    kalem = new Pen(Color.Red, 4);
                }
                else
                {
                    kalem = new Pen(Color.Black, 4);
                }
                cizimler[i].DrawLine(kalem, x + boyutlar[i], y, ilkEn + boyutlar[i], y - ilkBoy);
                cizimler[i].DrawLine(kalem, ilkEn + boyutlar[i], y - ilkBoy, ilkEn + ikinciEn + boyutlar[i], y + ikinciBoy );
                cizimler[i].DrawLine(kalem, ilkEn + ikinciEn + boyutlar[i], y + ikinciBoy, ilkEn + 2 * ikinciEn + boyutlar[i], y);
                
               
                boyutlar[i] += hizAyariKalp; // her bir grafiğin değişen zamanla konum bilgisi
                                             // grafiğin ilerlemesini sağlar
            }
        }

        void grafikCizSolunum(int x, int y, int Boy,int En)
        {
            
            cizim2.DrawLine(inceKalem, 0, y, 1000, y); // düz çizgi
            for (int i = 0; i < cizimler2.Count; i++)
            {
                if (i == cizimler2.Count - 1)
                {
                    kalem1 = new Pen(Color.DarkBlue, 4);
                }
                else
                {
                    kalem1 = new Pen(Color.Black, 4);
                }

                cizimler2[i].DrawLine(kalem1, x + boyutlarSolunum[i], y, x + boyutlarSolunum[i], y - Boy);
                cizimler2[i].DrawLine(kalem1, x + boyutlarSolunum[i], y - Boy, x + En + boyutlarSolunum[i], y - Boy);
                cizimler2[i].DrawLine(kalem1, x + En + boyutlarSolunum[i], y - Boy, x + En + boyutlarSolunum[i], y);


                boyutlarSolunum[i] += hizAyariSolunum; // her bir grafiğin değişen zamanla konum bilgisi
            }
        }

        
        int ilkX = 0, sonX = 0; // sıcaklık için değişen konum bilgileri yatay eksen için
        int ilkY = 100, sonY = 100;  // sıcaklık için değişen konum bilgileri dikey eksen için
        //int ilerlemeMiktariSicaklik = 8;
        void grafikCizSicaklik(int x, int y)
        {

            cizim3.DrawLine(inceKalem, 0, y, 1000, y); // düz çizgi
            
                sonX += hizAyariSicaklik;
                
                cizim3.DrawLine(kalem2, ilkX, ilkY,sonX, sonY);
                
                
                ilkX = sonX;
                ilkY = sonY;

        }
        
        
        
        int yol = 0;
        private void timer5_Tick(object sender, EventArgs e)
        {
            grafikCizSicaklik(10, 100);
            //timer5Reset++;
            yol += hizAyariSicaklik;   // 560 sicaklik monitörünün uzunluğu
            if (yol >= pictureBox3.Width) // ekran dolduğunda ekranı temizle  // gidilen yol hizAyariSicaklik lineer değişmeyebileceğinden dolayı yol ile gitmek daha iyi sonuç verir.
            {
                cizim3.Clear(Color.White);
                ilkX = 0;   // ekran dolduğunda başlangıca dönmek için sıfırla
                sonX = 0; // ekran dolduğunda başlangıca dönmek için sıfırla
                //timer5Reset = 0;
                yol = 0;
            }
        }

       
        
        List<string> saniyeIndex = new List<string>();
        string saat, dakika, saniye;
        int kalpOrtalama = 0;
        int solunumOrtalama = 0;
        int i = 0;
        bool degerGoster = false;
        int sampleRate = 5;
        private void tmrVeriAlma_Tick(object sender, EventArgs e)
        {
            saat = (DateTime.Now.Hour < 10 ? "0" + DateTime.Now.Hour.ToString() : DateTime.Now.Hour.ToString());
            dakika = (DateTime.Now.Minute < 10 ? "0" + DateTime.Now.Minute.ToString() : DateTime.Now.Minute.ToString());
            saniye = (DateTime.Now.Second < 10 ? "0" + DateTime.Now.Second.ToString() : DateTime.Now.Second.ToString());
            saniyeIndex.Add(saat + ":" + dakika + "." + saniye + ".." + DateTime.Now.Millisecond);
            kalpAtim.Add(kalp);
            solukAlma.Add(soluk);
            vucutSicaklik.Add(vSicaklik);
            kalpOrtalama = kalpOrtalama + kalpAtim[kalpAtim.Count - i - 1];
            solunumOrtalama = solunumOrtalama + solukAlma[solukAlma.Count - i - 1];

            //if (i == sampleRate)
            //{
            //    if (degerGoster)
            //    {
            //        updateLabelText((kalpOrtalama / sampleRate).ToString());
            //        updateLabelTextSolunum((solunumOrtalama / sampleRate).ToString());
            //        solunumOrtalama = 0;
            //        kalpOrtalama = 0;
            //        i = 0;
            //    }
            //    else
            //    {
            //        updateLabelText("0");
            //        updateLabelTextSolunum("0");
            //        solunumOrtalama = 0;
            //        kalpOrtalama = 0;
            //        i = 0;
            //    }
            //    degerGoster = true;

            //}
            //i++;
           
        }

        Microsoft.Office.Interop.Excel.Application xlUygulama;
        Microsoft.Office.Interop.Excel.Workbook xlKitap;
        Microsoft.Office.Interop.Excel.Worksheet xlSayfa;
        object misValue = System.Reflection.Missing.Value;
        
        private void Form2_FormClosed(object sender, FormClosedEventArgs e)
        {
            dead.Stop();
            tmrKalpAtimYokBelirle.Stop();
            tmrSolukYokBelirle.Stop();
            timer1.Stop();
            timer2.Stop();
            timer3.Stop();
            timer4.Stop();
            timer5.Stop();
            DialogResult cevap = MessageBox.Show("WOULD YOU LIKE TO SAVE THE DATA ?", "SaveToFileDialog", MessageBoxButtons.YesNo, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
            


            if (cevap == DialogResult.Yes)
            {
                
                string path = "";
                saveFileDialog1.Filter = "Excel Workbooks (*.xls)|*.xls";
                DialogResult kayıt = saveFileDialog1.ShowDialog();
                
                if (kayıt == DialogResult.OK)
                {
                    saveFileDialog1.Title = "SaveToFile Dialog";
                    path = saveFileDialog1.FileName;
                    //saveFileDialog1.OverwritePrompt = true;
                    
                    xlUygulama = new Microsoft.Office.Interop.Excel.Application();
                    xlUygulama.Visible = true;

                    xlKitap = xlUygulama.Workbooks.Add();
                    xlSayfa = xlKitap.Worksheets.Application.Sheets[1];
                    xlSayfa.Cells[1, 2] = "HEART BEAT RATE (BPM)";
                    xlSayfa.Cells[1, 3] = "RESPIRATION RATE (PPM)";
                    xlSayfa.Cells[1, 4] = "BODY TEMPERATURE (°C)";

                    int sumKalp = 0;
                    int sumSolunum = 0;
                    double sumSicaklik = 0.0;
                    double mKalp, mSolunum, mSicaklik;
                    int baslangic = 7;
                    for (int i = baslangic; i < saniyeIndex.Count; i++)
                    {
                        xlSayfa.Cells[i + 2 - baslangic, 1] = saniyeIndex[i];
                        xlSayfa.Cells[i + 2 - baslangic, 2] = kalpAtim[i];
                        xlSayfa.Cells[i + 2 - baslangic, 3] = solukAlma[i];
                        xlSayfa.Cells[i + 2 - baslangic, 4] = vucutSicaklik[i];
                        sumKalp += kalpAtim[i];
                        sumSolunum += solukAlma[i];
                        sumSicaklik += vucutSicaklik[i];
                    }
                    mKalp = sumKalp / (double)(kalpAtim.Count - baslangic);
                    mSolunum = sumSolunum / (double)(solukAlma.Count - baslangic);
                    mSicaklik = sumSicaklik / (double)(vucutSicaklik.Count - baslangic);
                    xlSayfa.Cells[(saniyeIndex.Count + 2 - baslangic), 1] = "MEAN";
                    xlSayfa.Cells[(saniyeIndex.Count + 2 - baslangic), 2] = mKalp;
                    xlSayfa.Cells[(saniyeIndex.Count + 2 - baslangic), 3] = mSolunum;
                    xlSayfa.Cells[(saniyeIndex.Count + 2 - baslangic), 4] = mSicaklik;
                    Excel.Range grafikHucreler; // çizim yapılacak hücreler için 
                    Excel.ChartObjects xlGrafik = (Microsoft.Office.Interop.Excel.ChartObjects)xlSayfa.ChartObjects(Type.Missing); // grafik sayfa ile ilişkilendiriliyor
                    Excel.ChartObject cizim = (Microsoft.Office.Interop.Excel.ChartObject)xlGrafik.Add(250, 20, 600, 300); // grafiğin sayfadaki yeri belirlenip sayafaya ekleniyor
                    Excel.Chart cizimSayfasi = cizim.Chart;   // grafik en son chart a aktarılıyor
                    string sonHucre;
                    sonHucre = "D" + (saniyeIndex.Count + 1).ToString();
                    grafikHucreler = xlSayfa.get_Range("A1", sonHucre); // çizim yapılacak hücreler belirleniyor
                    cizimSayfasi.SetSourceData(grafikHucreler, misValue);  // veriler alınıyor
                    cizimSayfasi.ChartType = Microsoft.Office.Interop.Excel.XlChartType.xlLine;  // grafiğin tipi seçiliyor

                    
                    xlKitap.SaveAs(@path, Microsoft.Office.Interop.Excel.XlFileFormat.xlWorkbookNormal, misValue, misValue, misValue, misValue, Microsoft.Office.Interop.Excel.XlSaveAsAccessMode.xlExclusive, misValue, misValue, misValue, misValue, misValue);
                    path = path.Remove(path.Length - 4); // .xls uzantısı silinir
                    cizimSayfasi.Export(@path, "BMP", misValue);

                    //dosya kaydediliyor
                    timer1.Stop();
                    timer2.Stop();
                    timer3.Stop();
                    timer4.Stop();
                    timer5.Stop();
                    //this.Close(); // form kapanır

                    
                    MessageBox.Show("Saved to a File.\n" + @path);
                    //DialogResult res = MessageBox.Show("SELECTION", "REMEMBER Selection", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                    //if (res == DialogResult.Yes)
                    //{
                        
                    //}
                    Form3 yeni = new Form3(path, mKalp,mSolunum,mSicaklik);
                    yeni.ShowDialog();
                }
                else
                {
                    MessageBox.Show("NOT Saved to a File!.");
                }
               
            }
            else if (cevap == DialogResult.No || cevap == DialogResult.Cancel)
            {
                MessageBox.Show("NOT Saved to a File!.");
            }
            this.Hide();
        }
        bool kalpVeriKontrolEt = false;
        bool tempKalpVeriKontrolEt = false;
        int kalpKarar = 2;
        int tempKalpKarar;
        bool deadAktifEt = false; // başlangıçta kalp durma kontrolünün çalışmasını engeller ilk beat geldikten sonra aktif yapmak için kullandım
        SoundPlayer dead = new SoundPlayer(Properties.Resources.beep_12);
        private void timer6_Tick(object sender, EventArgs e)
        {
            tempKalpVeriKontrolEt = kalpVeriKontrolEt;
            tempKalpKarar = kalpKarar;
            TimeSpan k = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
            if ((k.TotalSeconds - simdi.TotalSeconds) > 10 && deadAktifEt)
            {
                kalpVeriKontrolEt = true;
                kalpKarar = 1;
                //dead.Play();
                degerGoster = false;
                //kalp = 0;
                //updateLabelText("0");
             
            }
            if (kalpKarar != tempKalpKarar) // dead sesinin sürekli çalması için kesik kesik çalmayı önledim
            {
                if (kalpKarar == 1)
                {
                    dead.PlayLooping();
                }
                else if (kalpKarar == 2) // kalpKarar portDataReceived içinde değişiyor
                {
                    dead.Stop();
                }
            }
        }

        private void tmrSolukYokBelirle_Tick(object sender, EventArgs e)
        {
            TimeSpan s = new TimeSpan(DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond);
            if ((s.TotalSeconds - simdi1.TotalSeconds) > 3)
            {
                //soluk = 0;
                //updateLabelTextSolunum("0");
            }
        }
       
    }
}
