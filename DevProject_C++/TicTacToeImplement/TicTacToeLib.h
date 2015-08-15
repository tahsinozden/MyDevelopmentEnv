#ifndef TicTacToeLib_h
#define TicTacToeLib_h
#include <iostream>
#include "Global.h"
using namespace std;

int maxX = 0, maxY = 0;

enum Players
{
     X = 0,
     O,
     Empty
     };
class TicTacToe
{
   private:
      int table[3][3];
      int MaxPosX, MaxPosY;
      Players WINNER;
      Players CURRENTPLAYER;
      bool GAMEOVER;
      string PlayerConvert(int x, int y);
      void EvaluaterValueAssigner(int tTable[][3],vector<vector<Point> > evArray, Players p);
      //void CopyTable(int tempTable[][3]);
      //string PlayerToString(Players p);
   public:           
   TicTacToe();
   void ShowTable();
   void init_Game();
   void PerformMove(int x, int y, Players player);
   void PerformMove(Players player);
   void InputMove(Players player);
   bool IsValidMove(int x, int y);
   bool GameOverChecker();
   void ShowWinner();
   void setCurrentPlayer(Players p);
   string PlayerToString(Players p);
   int Evaluator(Players player,  int tempTable[3][3]);
   void CopyTable(int tempTable[][3]);
   //void ValidMoves(vector<vector<int> > moves); // cannot be vector<vector<int>> 
   void ValidMoves(vector<Point> &moves); 
   int MAX(Players p, int table[3][3], int deep);
   int MIN( Players p, int table[3][3], int deep);
   void DisplayTable(int table[3][3]);
};

void TicTacToe::ShowTable()
{
     cout << endl << endl << "--------------------------------------------------------------" << endl << endl;
     cout << "       ";
     for(int i=0;i<3;i++) cout << " " << i << " ";
     cout << endl;
     for(int i=0;i<3;i++)  
     {
           cout << "     " << i << " ";
           for(int j=0;j<3;j++) cout << " " << this->PlayerConvert(i,j) << " ";
           cout << endl;    
     }        
}

TicTacToe::TicTacToe()
{
   for(int i=0;i<3;i++)  
      {
         for(int j=0;j<3;j++) table[i][j] = Players(2);    
      }                  
}
void TicTacToe::init_Game(){}

void TicTacToe::PerformMove(int x, int y, Players player)
{
     this->table[x][y] = player;
     this->ShowTable();   
     
}
void TicTacToe::PerformMove(Players player)
{
     this->table[this->MaxPosX][this->MaxPosY] = player;
     this->ShowTable();   
     
}
void TicTacToe::InputMove(Players player)
{
     int x,y;
     while(true)
     {
      cout << "Input your move and character (respectively x y O): ";
      cin >> x;
      cin >> y;
      //cin >> player;
      
      if(this->IsValidMove(x,y)) break;
      else cout << "Invalid Move!!!" << endl;
     }
     //setCurrentPlayer();
     this->PerformMove(x,y,player);
          
}

bool TicTacToe::IsValidMove(int x, int y)
{
     if(x <= 2 && x >= 0 && y <= 2 && y >= 0)
     {
          if(table[x][y] == Players(2)) return true;
          else return false;     
     }
     else return false;
}

bool TicTacToe::GameOverChecker()
{
     Players plyr = X;
     plyr = Players(1);
     // ROWS
     int rowCounter = 0;
     for(int p=0;p<2;p++)
     {
      for(int i=0;i<3;i++)
      {
       for(int j=0;j<3;j++)  if(table[i][j] == Players(p))  rowCounter++; 
      
           if(rowCounter == 3)
           {
            this->WINNER = Players(p);
            this->GAMEOVER = true;
            return true;
            }
       rowCounter = 0;     
      } 
     }
     // COLUMNS
     int columnCounter = 0;
     for(int p=0;p<2;p++)
     {
      for(int i=0;i<3;i++)
      {
       for(int j=0;j<3;j++)  if(table[j][i] == Players(p))  columnCounter++; 
      
           if(columnCounter == 3)
           {
            this->WINNER = Players(p);
            this->GAMEOVER = true;
            return true;
            }
       columnCounter = 0;     
      } 
     }
     // FIRST CORNER
     int cornerCounter = 0;
     for(int p=0;p<2;p++)
     {
      for(int i=0;i<3;i++)
        if(table[i][i] == Players(p))  cornerCounter++;    

      if(cornerCounter == 3)
           {
            this->WINNER = Players(p);
            this->GAMEOVER = true;
            return true;
            }
       cornerCounter = 0;  
     }
     // SECOND CORNER
     for(int p=0;p<2;p++)
     {
      for(int i=0;i<3;i++)
        if(table[i][2-i] == Players(p))  cornerCounter++;    

      if(cornerCounter == 3)
           {
            this->WINNER = Players(p);
            this->GAMEOVER = true;
            return true;
            }
       cornerCounter = 0;  
     }
     int fullSpace = 0;
     for(int i=0;i<3;i++){
             for(int j=0;j<3;j++){
                     if(this->table[i][j] != Players(2)) fullSpace++;
                     }
     }
     if(fullSpace == 9){
                  this->WINNER = Players(2);
                  this->GAMEOVER = true;
                  return true;
     }
     
     
     return false; // ELSE RETURN FALSE
}

string TicTacToe::PlayerConvert(int x, int y)
{
       if(table[x][y] == Players(0)) return "X";
       else if(table[x][y] == Players(1)) return "O";
       else return "*";
}

void TicTacToe::ShowWinner()
{
     if(this->WINNER != Players(2)) cout << "Player " << this->PlayerToString(WINNER) << " wins the game!!" << endl; 
     else cout << "TIE!!!" << endl;
}

string TicTacToe::PlayerToString(Players p)
{
  if(p == 0) return "X";
  else return "0";     
}
void TicTacToe::setCurrentPlayer(Players p)
{
      this->CURRENTPLAYER = p;
}

int TicTacToe::Evaluator(Players p,  int tempTable[][3])
{
    //cout << "EVALUATOR" << endl;
     int valueCounter = 0;
     int values[2] = {0,0};
     for(int player=0; player < 2; player++)     
     {      
                     // ROWS
                     int rowCounter = 0;
     
                     for(int i=0;i<3;i++)
                     {
                             for(int j=0;j<3;j++) 
                             {
                              if(tempTable[i][j] == Players(p) || tempTable[i][j] == Players(2)) 
                              {
                                if(tempTable[i][j] == Players(p)) rowCounter++; 
                              }
                              else // if it is oppenent break the loop
                              {
                                 rowCounter = 0;
                                 break;  
                              }
                             }
                             if(rowCounter == 3) valueCounter += 1000;
                             else if(rowCounter == 2) valueCounter += 3;
                             else if(rowCounter == 1) valueCounter += 1;
                             else valueCounter += 0;
                          rowCounter = 0;     
                          } 
     
                   // COLUMNS
                   int columnCounter = 0;
     
                   for(int i=0;i<3;i++)
                   {
                           for(int j=0;j<3;j++) 
                           {
                                    if(tempTable[j][i] == Players(p) || tempTable[j][i] == Players(2)) 
                                    {
                                     if(tempTable[j][i] == Players(p)) columnCounter++; 
                                    }
                                    else
                                    {
                                        columnCounter = 0;
                                        break;
                                    }
                           }
                                   
                           if(columnCounter == 3) valueCounter += 1000;
                           else if(columnCounter == 2) valueCounter += 3;
                           else if(columnCounter == 1) valueCounter += 1;
                           else valueCounter += 0;
                           columnCounter = 0;     
                     } 
     
                     // FIRST CORNER
                  int cornerCounter = 0;
     
                  for(int i=0;i<3;i++)
                  {
                          if(tempTable[i][i] == Players(p) || tempTable[i][i] == Players(2))  
                          {
                            if(tempTable[i][i] == Players(p))   cornerCounter++;    
                          }
                          else 
                          {
                               cornerCounter = 0;
                               break;
                          }
                  }
                  if(cornerCounter == 3) valueCounter += 1000;
                  else if(cornerCounter == 2) valueCounter += 3;
                  else if(cornerCounter == 1) valueCounter += 1;
                  else cornerCounter += 0;
                  cornerCounter = 0;  
     
               // SECOND CORNER
    
               for(int i=0;i<3;i++)
               {
                       if(tempTable[i][2-i] == Players(p) || tempTable[i][2-i] == Players(2))  
                       {
                            if(tempTable[i][2-i] == Players(p))   cornerCounter++;    
                       }
                       else 
                       {
                            cornerCounter = 0;
                            break;
                       }   
               }
               if(cornerCounter == 3) valueCounter += 1000;
               else if(cornerCounter == 2) valueCounter += 3;
               else if(cornerCounter == 1) valueCounter += 1;
               else cornerCounter += 0;

               cornerCounter = 0;  
               
      values[player] = valueCounter;
      valueCounter = 0;
      p = p == Players(0) ? Players(1) : Players(0);
     }
     return (values[0] - values[1]);
}

void TicTacToe::CopyTable(int tempTable[][3])
{
     for(int i=0;i<3;i++)
     {
      for(int j=0;j<3;j++) tempTable[i][j] = this->table[i][j];       
     }
}

void TicTacToe::ValidMoves(vector<Point> &moves)
{
     int count = 0;
     moves.resize(9);
      for(int i=0;i<3;i++)
      {
       for(int j=0;j<3;j++) 
       {
        if(this->IsValidMove(i,j)) 
        {
              moves[count] = Point(i,j);
              count++;    
        }
        //if(this->IsValidMove(i,j)) moves.insert(count, Point(i,j));
        //moves.insert()
            
       }       
      }    
      moves.resize(count);
      cout << "count : " << count << endl;
      cout << "Valid moves size : " << moves.size() << endl;
}

int TicTacToe::MAX(Players p, int table[3][3], int deep)
{
    //Players other = (p == Players(1)? Players(0) : Players(1));
    //cout << "deep : " << deep << endl;
    int score;
    if(deep == 0 || this->GameOverChecker()){
            score = Evaluator(p, table);
            //cout << "-MAX END-" << endl;
            return score;    
            }    
    int maxScore = INT_MIN;
 
     for(int i = 0 ; i < 3 ; ++i){
          for(int j = 0 ; j < 3 ; ++j){
                if(table[i][j] == Players(2)){
                      table[i][j] = Players(p);
                      score = MIN(p, table, deep-1 );
 
                      if(score > maxScore  ){
                            maxScore = score;
                            maxX = i;
                            maxY = j;
                            this->MaxPosX = i;
                            this->MaxPosY = j;
                            //cout << "FLAG_MAX : " << score << " ( " << i << "," << j << " ) player : " << p << endl;
                            //cout << "maxScore : " << maxScore << endl;
                      }
                      //cout << "FLAG_MAX : " << score << " ( " << i << "," << j << " ) player : " << p << endl;
                      //ShowTable();
                      table[i][j] = Players(2);
                }
          }
     }
 
     //return maxScore;
}

int TicTacToe::MIN(Players p, int table[3][3], int deep)
{
    int score;
    if(deep == 0 || this->GameOverChecker()){
            //return Evaluator(p, table);    
            score = Evaluator(p, table);
            //cout << "-MIN END-" << endl;
            return score; 
            }    
 
     int minScore = INT_MAX;
 
     for(int i = 0 ; i < 3 ; ++i){
          for(int j = 0 ; j < 3 ; ++j){
                if(table[i][j] == Players(2)){
                      table[i][j] = Players(p);
                      score = MAX(p, table, deep-1 );
 
                      if(score < minScore ){
                            minScore = score;
                            //cout << "minScore : " << minScore << endl;
                      }
                      //cout << "FLAG_MIN : " << score << " ( " << i << "," << j << " ) player : " << p <<  endl;
                      //ShowTable();
                      table[i][j] = Players(2);
                }
          }
     }
 
     //return minScore;
}

void TicTacToe::DisplayTable(int table[3][3])
{
     cout << endl << endl << "--------------------------------------------------------------" << endl << endl;
     cout << "       ";
     for(int i=0;i<3;i++) cout << " " << i << " ";
     cout << endl;
     for(int i=0;i<3;i++)  
     {
           cout << "     " << i << " ";
           for(int j=0;j<3;j++) cout << " " << Players(table[i][j]) << " ";
           cout << endl;    
     }   
}
#endif
