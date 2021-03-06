//J. Burg, Wake Forest University 
//Part of Octree for Indexed Color implementation
//October 2005

#ifndef IMAGE_
#define IMAGE_
#include <fstream>
#include "globals.h"
#include "Bitmap.h"

//This is Image.h

using namespace std;

class Image {
   friend istream& operator>>(istream& in, Image& x);
   public:
      void convertToIndexed(void);
      void createIndexedFile(RGB [], unsigned char [][300]);
   private:
      Bitmap bitmap;
      int width;
      int height;
};
#endif
