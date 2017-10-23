/**
 * the interface of the Image
 */

#ifndef IMAGE_
#define IMAGE_
#include <fstream>
#include "globals.h"
#include "Bitmap.h"

using namespace std;

class Image {
   friend istream& operator>>(istream& in, Image& x);
   public:
	   // convert to indexed color using partition algorithm
      void convertToIndexedByUP(void);
   		// convert to indexed color using popularity algorithm
      void convertToIndexedByPopularity(void);
      //create the indexed color file
      void createIndexedFile(RGB [], unsigned char [][300]);
   private:
      Bitmap bitmap;
      int width;
      int height;
};
#endif
