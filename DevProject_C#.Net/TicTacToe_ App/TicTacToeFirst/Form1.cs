using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TicTacToeFirst
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            CreateSquares();
        }
        Label[,] squares;

        Point[,] winnerCells = new Point[,] { 
                                            {new Point(0,0),new Point(0,1),new Point(0,2)},
                                            {new Point(1,0),new Point(1,1),new Point(1,2)},
                                            {new Point(2,0),new Point(2,1),new Point(2,2)},
                                            {new Point(0,0),new Point(1,0),new Point(2,0)},
                                            {new Point(0,1),new Point(1,1),new Point(2,1)},
                                            {new Point(0,2),new Point(1,2),new Point(2,2)},
                                            {new Point(0,0),new Point(1,1),new Point(2,2)},
                                            {new Point(0,2),new Point(1,1),new Point(2,0)}
        };
        void CreateSquares()
        {
            int Xloc = 30;
            int Yloc = 30;
            squares = new Label[3,3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    squares[i, j] = new Label();
                    squares[i, j].Size = new Size(70, 70);
                    squares[i, j].BorderStyle = BorderStyle.Fixed3D;
                    squares[i, j].TextAlign = ContentAlignment.MiddleCenter;
                    squares[i, j].AutoSize = false;
                    squares[i, j].Text = "";
                    squares[i, j].Font = new Font("Arial", 20);
                    
                    squares[i, j].Location = new Point(Xloc,Yloc);
                    Xloc = Xloc + squares[i, j].Width;
                    Controls.Add(squares[i, j]);
                }
                Yloc = Yloc + 70;
                Xloc = 30;
            }
        }
        int checkWinCount = 0;
        int checkWinIndex = 0;
        bool CheckWin(string gamerType)
        {
            int index = 0;
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (squares[winnerCells[i, j].X, winnerCells[i, j].Y].Text == gamerType) 
                    {
                        checkWinCount++;
                        index = i;
                    }
                }
                if (checkWinCount == 3) { checkWinIndex = index; PaintSquare(0,checkWinIndex); return true; }
                else checkWinCount = 0; 
            }
            

            return false; 
        
        }


        void PaintSquare(int place,int index)
        {
            if (index < 3)
            {
                for (int i = 0; i < 3; i++)
                {
                    squares[index, i].BackColor = Color.White;
                }
            }
            else if (index >=3 && index  < 6)
            {
                for (int i = 0; i < 3; i++)
                {
                    squares[i, index%3].BackColor = Color.White;
                }
            }
            else if (index == 6)
            {
                for (int i = 0; i < 3; i++)
                {
                    squares[i, i].BackColor = Color.White;
                }
            }
            else if (index == 7)
            {
                for (int i = 0; i < 3; i++)
                {
                    squares[2 - i, i].BackColor = Color.White;
                }
            }
        }
        void ResetSquare()
        {
            count = 0;
            checkWinIndex = 0;
            checkWinCount = 0; 
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    squares[i, j].Text = "";
                    squares[i, j].BackColor = DefaultBackColor;
                }
            }
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    squares[i, j].Click += Form1_Click;
                    
                }
            }
        
        }

        List<List<string>> tempData = new List<List<string>>();
        List<double> tempTree = new List<double>();
        List<double> data = new List<double>();
        double alpha = -100000, beta = 100000;
        int MaxNode(bool game, int depth)
        {
            double tempMax = -100000;
            double V;
            int index = 0;
            int iPlace = 0;
            if (game && depth <= 0)
            {
                return 100000;
            }
            else
            {


                while (index != (Tree.Count / Tree[0].Count))
                {
                    V = MinNode(depth--, index);
                    
                    if (tempMax <= V)
                    {
                        tempMax = V;
                        iPlace = index;
                    }
                    index++;
                }
            }
            return iPlace;
            
        }

        double MinNode(int depth, int ix)
        {
            double tempMin = 100000;
            if (Tree[ix][0] >= Tree[ix][1])
            {
                tempMin = Tree[ix][1];
            }
            else tempMin = Tree[ix][0];
            return tempMin;
        }
        List<List<string>> tempSquares = new List<List<string>>();
        List<List<double>> Tree = new List<List<double>>();
        List<Point> emptySquares = new List<Point>();
        void BuildTree()
        {
            string gamerSign = "X";
            string goster = "";
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    tempSquares.Add(new List<string>());
                    tempSquares[i].Add(squares[i, j].Text);
                    tempSquares[i][j] = squares[i, j].Text;
                    if (tempSquares[i][j] == "") emptySquares.Add(new Point(i, j));
                    goster += tempSquares[i][j] + " ";
                }
                goster += "\n";
            }
            int checkCount = 0;
            int whiteCount = 0;
            double branchValue = 0;
            double interSum = 0;

            for (int t = 0; t < emptySquares.Count; t++)
            {
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "X";
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        
                        if ((tempSquares[winnerCells[i, j].X][winnerCells[i, j].Y]) == gamerSign)
                        {
                            checkCount++;
                        }
                        else if (tempSquares[winnerCells[i, j].X][winnerCells[i, j].Y] == "") whiteCount++;
                    }
                    if (checkCount == 3) branchValue = 1000;
                    else if (whiteCount > 0 && whiteCount < 3 && (whiteCount + checkCount) == 3) branchValue = 3 - whiteCount;
                    interSum += branchValue;
                    whiteCount = 0;
                    checkCount = 0;
                    branchValue = 0;
                }

                Tree.Add(new List<double>());
                Tree[t].Add(interSum);
                Tree[t][0] = interSum;
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "";
                branchValue = 0;
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "";
                whiteCount = 0;
                checkCount = 0;
                interSum = 0;
            }

            gamerSign = "O";
            for (int t = 0; t < emptySquares.Count; t++)
            {
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "O";
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {

                        if ((tempSquares[winnerCells[i, j].X][winnerCells[i, j].Y]) == gamerSign)
                        {
                            checkCount++;
                        }
                        else if (tempSquares[winnerCells[i, j].X][winnerCells[i, j].Y] == "") whiteCount++;
                    }
                    if (checkCount == 3) branchValue = 1000;
                    else if (whiteCount > 0 && whiteCount < 3 && (whiteCount + checkCount) == 3) branchValue = 3 - whiteCount;
                    interSum += branchValue;
                    whiteCount = 0;
                    checkCount = 0;
                    branchValue = 0;
                }

                Tree.Add(new List<double>());
                Tree[t].Add(interSum);
                Tree[t][1] = interSum;
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "";
                branchValue = 0;
                tempSquares[emptySquares[t].X][emptySquares[t].Y] = "";
                whiteCount = 0;
                checkCount = 0;
                interSum = 0;
            }

            //string aa = "";
            //for (int i = 0; i < Tree.Count / Tree[0].Count; i++)
            //{
            //    for (int j = 0; j < Tree[0].Count; j++)
            //    {
            //        aa = aa + Tree[i][j].ToString() + " " + emptySquares[i].ToString() + " ";
            //    }

            //    aa += "\n";
            //}
            //MessageBox.Show(aa);

            int ix = MaxNode(true, 8);
            //MessageBox.Show("MOVE : " + emptySquares[ix].ToString());
            squares[emptySquares[ix].X, emptySquares[ix].Y].ForeColor = Color.Black;
            squares[emptySquares[ix].X, emptySquares[ix].Y].Text = "X";
            bool r1 = CheckWin("X");
            if (r1)
            {
                ReleaseClick();

                MessageBox.Show("GAME OVER! 'X' wins the GAME");
            }
            tempSquares.Clear();
            emptySquares.Clear();
            Tree.Clear();

        }

        private void Form1_Load(object sender, EventArgs e)
        {
       
        }
        int count = 0;
        void ReleaseClick()
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    squares[i, j].Click -= Form1_Click;
                }
            }

        }
        bool singlePlayer = false;
        void Form1_Click(object sender, EventArgs e)
        {
            //MessageBox.Show(((Label)sender).Location.X.ToString());
            if (singlePlayer)
            {
                if (((Label)sender).Text == "")
                {
                    ((Label)sender).ForeColor = Color.Red;
                    ((Label)sender).Text = "O";
                    
                    bool r1 = CheckWin("O");
                    if (r1)
                    {
                        ReleaseClick();

                        MessageBox.Show("GAME OVER! 'O' wins the GAME");
                    }
                    else BuildTree();
                
                }
            }
            else
            {
                if (((Label)sender).Text == "")
                {
                    if (count % 2 == 0)
                    {

                        ((Label)sender).ForeColor = Color.Black;
                        ((Label)sender).Text = "X";
                        bool r1 = CheckWin("X");
                        if (r1)
                        {
                            ReleaseClick();

                            MessageBox.Show("GAME OVER! 'X' wins the GAME");
                        }
                    }
                    else
                    {
                        ((Label)sender).ForeColor = Color.Red;
                        ((Label)sender).Text = "O";
                        bool r2 = CheckWin("O");
                        if (r2)
                        {
                            MessageBox.Show("GAME OVER! 'O' wins the GAME");
                            ReleaseClick();
                        }
                    }
                    count++;
                }
            }

        }

        private void button1_Click(object sender, EventArgs e)
        {
            ResetSquare();
            if (singlePlayer)
            {
                BuildTree();
                
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (button2.Text == "Single Player")
            {
                button2.Text = "Multiplayer";
                singlePlayer = true;
            }
            else
            {
                button2.Text = "Single Player";
                singlePlayer = false;
            }
            
        }
    }
}
