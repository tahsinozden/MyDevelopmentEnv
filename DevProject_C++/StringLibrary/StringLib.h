#ifndef StringLib_h
#define StringLib_h
using std::string;

double StringToNumber(string num);  // CONVERTS A STRING TO NUMBER
string NumberToString(double number);  // CONVERTS A NUMBER TO STRING
string StringCombine(string a1,string a2);  // STRING COMBINATION WITH TWO PARAMETERS
string StringCombine(string a1,string a2,string a3);  // STRING COMBINATION WITH THREE PARAMETERS
string SubString(string str,int startIndex);    // RETURNS A SUBSTRING FROM A STARTING INDEX
string SubString(string str,int startIndex,int endIndex);   // RETURNS A SUBSTRING FROM A STARTING TO AN END INDEX
bool EndsWith(string str,string pattern,bool isCaseSensitive);    //  CHECKS IF THE STRING VARIABLE ENDS WITH SPECIFIC PATTERN
bool StartsWith(string str,string pattern, bool isCaseSensitive);   //  CHECKS IF THE STRING VARIABLE STARTS WITH SPECIFIC PATTERN
string Remove(string str,int index);    // RETURNS A SUBSSTRING WITHOUT THE REMOVED CHARACTERS
void Replace(string &str,char replacedItem,char item);      //  REPLACES SPECIFIED CHARACTERS WITH THE CHARACTERS IN CURRENT STRING VARIABLE
void Trim(string &str);  //  REMOVES THE WHITE SPACES FROM THE CURRENT STRING VARIABLE
int LastIndexOf(string str,char item);           // RETURNS THE LAST OCCURANCE INDEX OF THE CHARACTER IN A STRING
int FirstIndexOf(string str,char item);           //  RETURNS THE FIRST OCCURANCE INDEX OF THE CHARACTER IN A STRING
string ToUpperCase(string str);     // RETURNS A STRING IN UPPERCASE LETTERS
string ToLowerCase(string str);      // RETURNS A STRING IN LOWERCASE LETTERS
string *DivideString(string str, int divisionIndex);  // RETURNS A POINTER STRING ARRAY FROM THE SPECIFIED INDEX



// AUXILARY FUNCTIONS
string retStNum(char number);   // RETURNS NUMBERS IN STRING FORMAT
bool IsFloatingNumber(double number);  // CHECKS IF A NUMBER IS FLOATING OR NOT
int Length(string str);   // RETURNS LENGTH OF A STRING
int LengthOfPointerArray(string *ptr);  // RETURNS THE LENGTH OF A STRING ARRAY CREATED BY "string *DivideString(string str, int divisionIndex); " FUNCTION

void show()
{
     std::cout << "Hello" << std::endl;
     }


double StringToNumber(string num)    // wchar_t will be implemented
{
 int i = 0;
 long  double  intNum[100];
 int dotIndex = 0;
 bool floatNumberFlag = false;
 while(num[i] >= '0' && num[i] <= '9' || num[i] == '.')
 {
  if(num[i] == '.')  // the index of the '.' is not used in number array
  {
      dotIndex = i;
      floatNumberFlag = true;
      i++;
      continue;
  }
  else intNum[i] = double(int(num[i])) - 48.0;
  i++;             
 }    
 long  double f = 1.0;
 long  double sum = 0.0;
 if(floatNumberFlag) // to see exact number of the converted string whose length >= 8, "fixed" should be used to display the number
 {                   // but the value is not changed
               // integer part of the number
               for(int j=0; j<dotIndex; j++)
               {
                for(int k = 0;k < dotIndex-j-1;k++) f *= 10;
                sum += f * intNum[j];
                f = 1;    
               }
               // number after dot (floating part)
               for(int j=dotIndex+1; j<i; j++)
               {
                for(int k = dotIndex;k < j;k++) f /= 10.0;
                sum += f * intNum[j];
                f = 1;

               }
 }
 else 
 {
               for(int j=0; j<i; j++)
               {
                for(int k = 0;k < i-j-1;k++) f *= 10;
                sum += f * intNum[j];
                f = 1;    
               }   
 }
 
 return sum;
}

string  NumberToString(double number)
{
      
      char num[100] = {0};
      int len = 0;
      int dotIndex = 0;
      bool floatingNumberFlag = false;
      if(IsFloatingNumber(number)) 
      {
          floatingNumberFlag = true;
          while(IsFloatingNumber(number))  
          {
                number = number * 10; // it multiplies by then because we try to get rid of the floating part of the number
                dotIndex++;        
          }     
      }
      
      while(number > 0)
      {
                   if(floatingNumberFlag && len == dotIndex) // checks floatingNumberFlag and len : if the dotIndex == len then put '.' into the array
                   {
                        num[len] = '.';
                        len++;
                        continue;   // dont go further and go back to while loop
                   }
                   else
                   {
                       num[len] = (char)(((unsigned long long int)number) % 10); // number requires being converted to int because of mod (%)
                       number = (unsigned long long int)(number / 10);
                       len++;         
                       // !!!!!! it is (unsigned long long int) because otherwise it cannot store all data
                       // !!!!! char(1) is equal to 1 but int('1') is equal to int 49
                   }
      } 
           
      string temp = "";
      for(int i=0; i< len;i++)
      {
       temp = StringCombine(retStNum(num[i]),temp);  // adds all numbers to temp in string format
      }
      
      return temp;
}

string retStNum(char number)
{
      string ss = "";
      
      switch((int)number)
      {
         case 1:
              ss =  "1";
              break;     
         case 2:
              ss =  "2";
              break; 
         case 3:
              ss =  "3";
              break;     
         case 4:
              ss =  "4";
              break; 
         case 5:
              ss =  "5";
              break;     
         case 6:
              ss =  "6";
              break; 
         case 7:
              ss =  "7";
              break;     
         case 8:
              ss =  "8";
              break;
         case 9:
              ss =  "9";
              break;     
         case 0:
              ss =  "0";
              break;  
         case '.':  // when int(number) == int('.')
              ss =  ".";
              break;          
                               
                    
      }      
      return ss;
}

bool IsFloatingNumber(double number) // It checks whether the number is float or int
{
     if(number == (unsigned long long int)number) return false; // it is (unsigned long long int) because otherwise it cannot store all data
     else return true;     
}


string StringCombine(string a1,string a2)
{ 
      return a1+a2;    
}

string StringCombine(string a1,string a2,string a3)
{
      return a1+a2+a3;       
}

string SubString(string str,int startIndex)
{

       string sstr = "";
       for(int i=startIndex;i<Length(str);i++)
       {
               sstr += str[i];   
       } 
      return sstr;
}

string SubString(string str,int startIndex,int endIndex)  
{

       string sstr = "";
       for(int i=startIndex;i<endIndex;i++)
       {
               sstr += str[i];          
       } 
      return sstr;
}

int Length(string str) // it does not accept string type parameter, it must be char* parameter.
{
     int i=0;
       while(true)
       {
         if(str[i] == '\0') break;   
         i++;   
       }
     return i;
}

string ToUpperCase(string str)  // TODO Special characters will be added
{
     int len = Length(str);
     string tempStr = "";
     for(int i=0;i<len;i++)
     {
            if(str[i] >= 'a' && str[i] <= 'z') 
            {
                      tempStr += char(int(str[i]) - 0x20);           
            }    
            else tempStr += str[i];
     }     
     return tempStr;
}

string ToLowerCase(string str)  // TODO Special characters will be added
{
     int len = Length(str);
     string tempStr = "";
     for(int i=0;i<len;i++)
     {
            if(str[i] >= 'A' && str[i] <= 'Z') 
            {
                      tempStr += char(int(str[i]) + 0x20);           
            }    
            else tempStr += str[i];
     }     
     return tempStr;
}

bool EndsWith(string str,string pattern, bool isCaseSensitive)
{
    int len = Length(str); 
    int lenp = Length(pattern);  
    string sstr = "";
    string spattern = "";
    if(isCaseSensitive) 
    {
        sstr = SubString(str,len-lenp);
        spattern = pattern;                
    }
    else   // convert to UPPERCASE just to compare the characters
    {
         sstr = ToUpperCase(SubString(str,len-lenp));   
         spattern = ToUpperCase(pattern);
    }
    if(sstr == spattern) return true;
    else return false;
}

bool StartsWith(string str,string pattern, bool isCaseSensitive)
{
    int len = Length(str); 
    int lenp = Length(pattern);  
    string sstr = "";
    string spattern = "";
    if(isCaseSensitive) 
    {
        sstr = SubString(str,0,lenp);
        spattern = pattern;                
    }
    else // convert to UPPERCASE just to compare the characters
    {
         sstr = ToUpperCase(SubString(str,0,lenp));   
         spattern = ToUpperCase(pattern);
    }
    if(sstr == spattern) return true;
    else return false;
}

string Remove(string str,int index)
{
       string sstr = "";
       int len = Length(str);
       for(int i=0;i<len;i++)
       {
           if(i == index) continue;
           sstr += str[i];            
       }
       return sstr;       
}

void Replace(string &str,char replacedItem,char item)
{
     int len = Length(str);
     for(int i=0;i<len;i++)
     {
        if(str[i] == replacedItem) str[i] = item;       
     }     
}

void Trim(string &str)
{
     int i=0;
     string sstr = "";
     while(str[i] != '\0')
     {
        if(str[i] == ' ')
        {
           i++; 
           continue;
        }
        sstr += str[i];
        i++;
     }
     str = sstr;
}

int LastIndexOf(string str,char item)
{
    int index = 0;
    bool itemExists = false;
    for(int i=0;i<Length(str);i++)
    {
       if(str[i] == item) 
       {
                 index = i;
                 itemExists = true;
       }       
    }    
    if(itemExists) return index;
    else return -1;
}

int FirstIndexOf(string str,char item)
{
    for(int i=0;i<Length(str);i++)
    {
       if(str[i] == item) return i;   
    }    

    return -1;  // if the item does not exist, return -1
}

string *DivideString(string str, int divisionIndex)
{
       /*
       In main fucntion, to get the value returned from this function
       i.e.   string *array = *DivideString(str, divisionIndex);
       to get the size of array
       i.e.   int size = LengthOfPointerArray(array);
       */
       string *ptr = new string[3];
       ptr[0] = "2"; // length of array
       ptr[1] = "";
       ptr[2] = "";
       int len = Length(str);
       for(int i=0;i<divisionIndex;i++) ptr[1] += str[i];
       for(int i=divisionIndex;i<len;i++)  ptr[2]  += str[i];    
       return ptr+1;
}

int LengthOfPointerArray(string *ptr)
{
    return (int)StringToNumber(*(--ptr));
}

#endif
