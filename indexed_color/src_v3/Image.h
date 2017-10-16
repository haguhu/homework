

#ifndef IMAGE_
#define IMAGE_
#include <fstream>
#include "globals.h"
#include "Bitmap.h"

using namespace std;

class Image {
   friend istream& operator>>(istream& in, Image& x);
   public:
      void convertToIndexed(void);
      void convertToIndexedByUP(void);
      void convertToIndexedByPopularity(void);
      void createIndexedFile(RGB [], unsigned char [][300]);
   private:
      Bitmap bitmap;
      int width;
      int height;
};
#endif
