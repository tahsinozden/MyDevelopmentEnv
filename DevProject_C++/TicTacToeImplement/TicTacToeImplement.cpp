#include <iostream>
#include <vector>
#include "TicTacToeLib.h"
#include "Global.h"
using namespace std;


// TODO: there are some wrong moves to be fixed.
//int maxX = 0, maxY = 0;
static int dizi[10];
void IAPlays(Players me, int board[3][3], int deeper,TicTacToe newGame);
static int counter = 0;
int Call2(int n);
int Call1(int n)
{
    if(n == 0) return 0;
    dizi[counter] = n;
    counter++;
    int ss = Call2(n-1);
    cout << "CALL1 " << n << endl;
 return 5;    
}

int Call2(int n)
{
    if(n == 0) return 0;
    dizi[counter] = n;
    counter++;
    int ss = Call1(n-1);
    cout << "CALL2 " << n << endl;
 return 6;    
}

int fact(int n)
{
    if(n == 1) return 1;
    return n*fact(n-1);    
}

int main()
{
    int x,y;
    TicTacToe newGame;
    int i=0;
    int score;
    Players me = Players(0);
    int table[3][3];
    int deep = 6;
    /*
    newGame.PerformMove(1,1,Players(0));
    newGame.PerformMove(2,1,Players(0));
    newGame.CopyTable(table);

    newGame.MAX(Players(1),table,2);
    newGame.PerformMove(Players(1));
    */
    newGame.ShowTable();
    while(!newGame.GameOverChecker())
    {
     cout << "Input Move : ";
     cin >> x >> y;
     newGame.PerformMove(x,y,Players(0));
     newGame.CopyTable(table);
     newGame.MAX(Players(1),table,3); 
     newGame.PerformMove(Players(1));                                
    }
    system("PAUSE");
    return 0;
}


void IAPlays(Players me, int board[3][3], int deeper, TicTacToe newGame){
     int maxScore = INT_MIN;
     int maxWidthPosition  = -1;
     int maxHeightPosition = -1;

 
     for(int idxHeight = 0 ; idxHeight < 3 ; ++idxHeight){
        for(int idxWidth = 0 ; idxWidth < 3 ; ++idxWidth){
                if(board[idxHeight][idxWidth] == Players(2)){
 
                      board[idxHeight][idxWidth] = me;
                      int score = newGame.MIN( me, board, deeper - 1 );
 
                      if(score > maxScore){
                            maxScore = score;
                            maxWidthPosition = idxWidth;
                            maxHeightPosition = idxHeight;
                            cout << "score : " << score << endl;
                            cout << maxWidthPosition << " " << maxHeightPosition << endl;
                      }
 
                      board[idxHeight][idxWidth] = Players(2);
                }
          }
     }
 
     board[maxHeightPosition][maxWidthPosition] = me;
     newGame.PerformMove(maxWidthPosition,maxHeightPosition,me);
}
