#include <iostream>
#include <fstream>
#define Row  9
#define Column  9
using namespace std;

// TODO: Alghoritm needs to be developed. In some cases, it cannot find the path
int goalPosX = -1;
int goalPosY = -1;
int count = 100;

const char EMPTY_PATH = '.';
const char WALL = '#';
const char START_POINT = 'S';
const char END_POINT = 'E';
const char PATH = '+';

void ShowMaze(char maze[Row][Column]);

bool MazeSolver(char maze[Row][Column],int x,int y)
{
     
     if(y >= Row || y < 0 || x >= Column || x < 0)  return false;
     
     if(maze[x][y] == EMPTY_PATH || maze[x][y] == START_POINT) 
     {
                  
     	  maze[x][y] = PATH;
      		
          if(MazeSolver(maze,x,y+1) == true) return true;        
          if(MazeSolver(maze,x+1,y) == true) return true; 
          if(MazeSolver(maze,x-1,y) == true) return true;   
          if(MazeSolver(maze,x,y-1) == true) return true;  
          
          
          maze[x][y] = EMPTY_PATH; 
     }
     else if(maze[x][y] == WALL) return false;
     else if(maze[x][y] == END_POINT ) {
     	goalPosX = x;
     	goalPosY = y;
     	return true;
	 }
     else return false;
}

bool MazeLoader(char maze[Row][Column])
{
    ifstream file;
    file.open("map.txt");
    if(!file.is_open()){
                         cout << "File_Open_Error!!!" <<  endl;
                         //system("PAUSE");
                         return false;
                         }
    int ch;
    char inputCh;
    for(int i=0;i<Row;i++){
             for(int j=0;j<Column;j++){
                     file >> inputCh;
                     maze[i][j] = inputCh;
                     }
             }          
     return true;
}

void ShowMaze(char maze[Row][Column])
{
     char ch;
     for(int i=0;i<Row;i++){
             for(int j=0;j<Column;j++){
                     cout << maze[i][j] << " ";
                     }
             cout << endl;
             }     
     
}
bool getStartPosition(char mz[Row][Column],int &x, int &y){
	x = -1;
	y = -1;
	for (int i=0; i<Row; i++){
		for (int j=0; j<Row; j++){
			if (mz[i][j] == START_POINT){
				x = i;
				y = j;
				break;
			}
		}	
	}
	if ( x != -1 && y != -1) return true;
	else return false;
}
int main()
{
	int startX;
	int startY;
    char mz[Row][Column];
    if(!MazeLoader(mz)){
                       system("PAUSE");
                       return 1;                         
    }
    
    //mz[::startY][::startX] = START_POINT;                                 
    ShowMaze(mz);        
    //mz[::startY][::startX] = EMPTY_PATH;   
    cout << endl << endl;      
	if(!getStartPosition(mz,startX,startY)){
		cout << "No start positions found!" << endl;
		system("PAUSE");
		return -255;
	}
    if(MazeSolver(mz,startX,startY)){
    	if (goalPosX != -1 && goalPosY != -1){
   			    mz[startX][startY] = START_POINT;
				ShowMaze(mz);
		}
		else{
			cout << "NO SOLUTION!" << endl;
		}
      
    }      
    else cout << "NO SOLUTION!" << endl;
    
    system("PAUSE");
    return 0;    
}
