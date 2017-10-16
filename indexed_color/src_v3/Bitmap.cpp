

//#include "math.h"
#include "Bitmap.h"

istream& operator>>(istream& in, Bitmap& x)
{
   x.cols = 300;
   x.rows = 300;
   for (int i = 0; i < x.rows; i++) 
      for (int j = 0; j < x.cols; j++) {
         in.read((char *)&(x.R[i][j]), sizeof(unsigned char));
         in.read((char *)&(x.G[i][j]), sizeof(unsigned char));
         in.read((char *)&(x.B[i][j]), sizeof(unsigned char));
      }
   return in;
}
