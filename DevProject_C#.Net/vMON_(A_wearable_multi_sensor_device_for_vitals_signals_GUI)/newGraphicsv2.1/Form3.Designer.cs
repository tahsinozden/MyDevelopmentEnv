namespace newGraphicsv2
{
    partial class Form3
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form3));
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.lblKalpOrtalama = new System.Windows.Forms.Label();
            this.lblSolunumOrtalama = new System.Windows.Forms.Label();
            this.lblSicaklikOrtalama = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.pictureBox1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pictureBox1.Location = new System.Drawing.Point(27, 26);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(795, 398);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            // 
            // lblKalpOrtalama
            // 
            this.lblKalpOrtalama.BackColor = System.Drawing.Color.Red;
            this.lblKalpOrtalama.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lblKalpOrtalama.Font = new System.Drawing.Font("Microsoft Sans Serif", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.lblKalpOrtalama.ForeColor = System.Drawing.Color.White;
            this.lblKalpOrtalama.Location = new System.Drawing.Point(24, 457);
            this.lblKalpOrtalama.Name = "lblKalpOrtalama";
            this.lblKalpOrtalama.Size = new System.Drawing.Size(135, 83);
            this.lblKalpOrtalama.TabIndex = 1;
            this.lblKalpOrtalama.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblSolunumOrtalama
            // 
            this.lblSolunumOrtalama.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(192)))));
            this.lblSolunumOrtalama.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lblSolunumOrtalama.Font = new System.Drawing.Font("Microsoft Sans Serif", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.lblSolunumOrtalama.ForeColor = System.Drawing.Color.White;
            this.lblSolunumOrtalama.Location = new System.Drawing.Point(291, 457);
            this.lblSolunumOrtalama.Name = "lblSolunumOrtalama";
            this.lblSolunumOrtalama.Size = new System.Drawing.Size(135, 83);
            this.lblSolunumOrtalama.TabIndex = 2;
            this.lblSolunumOrtalama.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblSicaklikOrtalama
            // 
            this.lblSicaklikOrtalama.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
            this.lblSicaklikOrtalama.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lblSicaklikOrtalama.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.lblSicaklikOrtalama.Location = new System.Drawing.Point(550, 457);
            this.lblSicaklikOrtalama.Name = "lblSicaklikOrtalama";
            this.lblSicaklikOrtalama.Size = new System.Drawing.Size(174, 83);
            this.lblSicaklikOrtalama.TabIndex = 3;
            this.lblSicaklikOrtalama.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label4
            // 
            this.label4.BackColor = System.Drawing.Color.Red;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, ((System.Drawing.FontStyle)(((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic)
                            | System.Drawing.FontStyle.Underline))), System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(156, 457);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(72, 83);
            this.label4.TabIndex = 29;
            this.label4.Text = "BPM";
            this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label7
            // 
            this.label7.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(0)))), ((int)(((byte)(192)))));
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, ((System.Drawing.FontStyle)(((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic)
                            | System.Drawing.FontStyle.Underline))), System.Drawing.GraphicsUnit.Point, ((byte)(162)));
            this.label7.ForeColor = System.Drawing.Color.White;
            this.label7.Location = new System.Drawing.Point(421, 457);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(72, 83);
            this.label7.TabIndex = 30;
            this.label7.Text = "PPM";
            this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // timer1
            // 
            this.timer1.Interval = 1000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // Form3
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(853, 561);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.lblSicaklikOrtalama);
            this.Controls.Add(this.lblSolunumOrtalama);
            this.Controls.Add(this.lblKalpOrtalama);
            this.Controls.Add(this.pictureBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form3";
            this.Text = "Summary Window";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form3_FormClosing);
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form3_FormClosed);
            this.Load += new System.EventHandler(this.Form3_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Label lblKalpOrtalama;
        private System.Windows.Forms.Label lblSolunumOrtalama;
        private System.Windows.Forms.Label lblSicaklikOrtalama;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Timer timer1;
    }
}