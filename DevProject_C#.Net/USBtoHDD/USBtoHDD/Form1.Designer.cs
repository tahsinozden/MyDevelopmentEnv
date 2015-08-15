namespace USBtoHDD
{
    partial class Form1
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
            this.tmrDriveChecker = new System.Windows.Forms.Timer(this.components);
            this.listBox1 = new System.Windows.Forms.ListBox();
            this.txtCriteria1 = new System.Windows.Forms.TextBox();
            this.txtCriteria = new System.Windows.Forms.TextBox();
            this.cmbCriteria = new System.Windows.Forms.ComboBox();
            this.txtFileType = new System.Windows.Forms.TextBox();
            this.btnStart = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.cmbModeSelect = new System.Windows.Forms.ComboBox();
            this.btnHide = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // tmrDriveChecker
            // 
            this.tmrDriveChecker.Interval = 6000;
            this.tmrDriveChecker.Tick += new System.EventHandler(this.tmrDriveChecker_Tick);
            // 
            // listBox1
            // 
            this.listBox1.FormattingEnabled = true;
            this.listBox1.Location = new System.Drawing.Point(12, 74);
            this.listBox1.Name = "listBox1";
            this.listBox1.Size = new System.Drawing.Size(210, 186);
            this.listBox1.TabIndex = 0;
            // 
            // txtCriteria1
            // 
            this.txtCriteria1.Location = new System.Drawing.Point(364, 48);
            this.txtCriteria1.Name = "txtCriteria1";
            this.txtCriteria1.Size = new System.Drawing.Size(100, 20);
            this.txtCriteria1.TabIndex = 1;
            // 
            // txtCriteria
            // 
            this.txtCriteria.Location = new System.Drawing.Point(364, 22);
            this.txtCriteria.Name = "txtCriteria";
            this.txtCriteria.Size = new System.Drawing.Size(100, 20);
            this.txtCriteria.TabIndex = 2;
            // 
            // cmbCriteria
            // 
            this.cmbCriteria.FormattingEnabled = true;
            this.cmbCriteria.Location = new System.Drawing.Point(228, 22);
            this.cmbCriteria.Name = "cmbCriteria";
            this.cmbCriteria.Size = new System.Drawing.Size(121, 21);
            this.cmbCriteria.TabIndex = 3;
            this.cmbCriteria.Text = "Size Select";
            // 
            // txtFileType
            // 
            this.txtFileType.Location = new System.Drawing.Point(228, 74);
            this.txtFileType.Name = "txtFileType";
            this.txtFileType.Size = new System.Drawing.Size(121, 20);
            this.txtFileType.TabIndex = 4;
            // 
            // btnStart
            // 
            this.btnStart.Location = new System.Drawing.Point(274, 195);
            this.btnStart.Name = "btnStart";
            this.btnStart.Size = new System.Drawing.Size(75, 60);
            this.btnStart.TabIndex = 5;
            this.btnStart.Text = "START";
            this.btnStart.UseVisualStyleBackColor = true;
            this.btnStart.Click += new System.EventHandler(this.btnStart_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(361, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(20, 13);
            this.label1.TabIndex = 6;
            this.label1.Text = "kB";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(228, 55);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(50, 13);
            this.label2.TabIndex = 7;
            this.label2.Text = "File Type";
            // 
            // cmbModeSelect
            // 
            this.cmbModeSelect.FormattingEnabled = true;
            this.cmbModeSelect.Location = new System.Drawing.Point(12, 22);
            this.cmbModeSelect.Name = "cmbModeSelect";
            this.cmbModeSelect.Size = new System.Drawing.Size(121, 21);
            this.cmbModeSelect.TabIndex = 8;
            this.cmbModeSelect.Text = "Mode Select";
            // 
            // btnHide
            // 
            this.btnHide.Location = new System.Drawing.Point(364, 195);
            this.btnHide.Name = "btnHide";
            this.btnHide.Size = new System.Drawing.Size(75, 60);
            this.btnHide.TabIndex = 9;
            this.btnHide.Text = "HIDE";
            this.btnHide.UseVisualStyleBackColor = true;
            this.btnHide.Click += new System.EventHandler(this.btnHide_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 267);
            this.Controls.Add(this.btnHide);
            this.Controls.Add(this.cmbModeSelect);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnStart);
            this.Controls.Add(this.txtFileType);
            this.Controls.Add(this.cmbCriteria);
            this.Controls.Add(this.txtCriteria);
            this.Controls.Add(this.txtCriteria1);
            this.Controls.Add(this.listBox1);
            this.Name = "Form1";
            this.Text = "USB2HDD - HDD2USB SpyCopier v1.0";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Timer tmrDriveChecker;
        private System.Windows.Forms.ListBox listBox1;
        private System.Windows.Forms.TextBox txtCriteria1;
        private System.Windows.Forms.TextBox txtCriteria;
        private System.Windows.Forms.ComboBox cmbCriteria;
        private System.Windows.Forms.TextBox txtFileType;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cmbModeSelect;
        private System.Windows.Forms.Button btnHide;
    }
}

