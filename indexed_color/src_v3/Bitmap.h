/**
 * define the bitmap
 */
#include <iostream>
#include <fstream>
#include <stdlib.h>

using namespace std;
class Bitmap {
   friend class Image;
   friend istream& operator>>(istream&, Bitmap&);
   public:
	   //the constructor function
      Bitmap() {rows = 300; cols = 300;}
   		//get the rows
      int Rows(void) const {return rows;}
      // get the columns
      int Columns(void) const {return cols;}
   private:
       int rows, cols; // Bitmap dimensions
       unsigned char R[300][300];
       unsigned char G[300][300];
       unsigned char B[300][300];
};  
