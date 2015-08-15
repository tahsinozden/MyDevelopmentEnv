#ifndef Global_h
#define Global_h

class Point
{
  public:
          int X;
          int Y;
  public:    
          Point(){};
          Point(int x, int y);   
          //void operator=();
};


Point::Point(int x, int y)
{
  this->X = x;
  this->Y = y;
}
#endif
