

#include "Image.h"
#include "Partition.h"
#include "globals.h"
#include "Popularity.h"
#include <iostream>
#include <fstream>

extern ofstream outFile;
void printColorTable(RGB  colorTable[] ){
   for (int i = 0; i < 256; i++) {
	cout<< i<<"   red:"<<(int)colorTable[i].r <<"   green:"<<(int)colorTable[i].g <<"   blue:"<<(int)colorTable[i].b   << endl;

   }
}
//convert to indexed color using popularity algorithm
void Image::convertToIndexedByPopularity(void) {
   Popularity popularity;
   RGB colorTable[256];
   unsigned char bitmapI[300][300];

   /* go through all pixels of the image to find the most popular colors */
   for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
 	 RGB color;
         color.r = bitmap.R[i][j];
         color.g = bitmap.G[i][j];
         color.b = bitmap.B[i][j];

	 popularity.insertColor(color);
      }
   }

   popularity.putInTable(colorTable);

   printColorTable(colorTable);

   for (int i = 0; i < height; i++) 
      for (int j = 0; j < width; j++) 
         bitmapI[i][j] = popularity.findColor(bitmap.R[i][j], bitmap.G[i][j], bitmap.B[i][j],colorTable);
   //Write the heading, color table, and indexed bitmap to the .bmp indexed file
   createIndexedFile(colorTable, bitmapI);
}

//convert to indexed color using partition algorithm
void Image::convertToIndexedByUP(void) {
   Partition partition;
   unsigned char rBits, gBits, bBits;
   unsigned char color;
   RGB colorTable[256];
   unsigned char bitmapI[300][300];

   /* go through all pixels of the image to create the box */
   for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
 	 RGB color;
         color.r = bitmap.R[i][j];
         color.g = bitmap.G[i][j];
         color.b = bitmap.B[i][j];

         partition.insertColor(color);
      }
   }

   partition.putInTable(colorTable);
   printColorTable(colorTable);
   for (int i = 0; i < height; i++) 
      for (int j = 0; j < width; j++) 
         bitmapI[i][j] = partition.findColor(bitmap.R[i][j], bitmap.G[i][j], bitmap.B[i][j], colorTable);
   //Write the heading, color table, and indexed bitmap to the .bmp indexed file
   createIndexedFile(colorTable, bitmapI);
}

//create the indexed color file
void Image::createIndexedFile(RGB colorTable[], unsigned char bitmapI[][300]) {
   char b;
   char m;
   int fileSize;
   short zero;
   int offsetToPixelData;
   int headerSize;
   int imageWidth;
   int imageHeight;
   short one;
   short bitsPerPixel;
   int compressionType;
   int compression;
   int preferredXResolution;
   int preferredYResolution;
   int numColorMapEntries;
   int numSignificantColors;  
   unsigned char z=0;

/*Put the header at the beginning of the .bmp file*/
   b = 'B';
   outFile.write(reinterpret_cast<char *>(&b), 1);
   m = 'M';
   outFile.write(reinterpret_cast<char *>(&m), 1);
   fileSize = 90000;
   outFile.write(reinterpret_cast<char *>(&fileSize), 4);
   zero = 0;
   outFile.write(reinterpret_cast<char *>(&zero), 2);
   outFile.write(reinterpret_cast<char *>(&zero), 2);
   offsetToPixelData = 1078;
   outFile.write(reinterpret_cast<char *>(&offsetToPixelData), 4);
   headerSize = 40;
   outFile.write(reinterpret_cast<char *>(&headerSize), 4);
   imageWidth = 300;
   outFile.write(reinterpret_cast<char *>(&imageWidth), 4);
   imageHeight = 300;
   outFile.write(reinterpret_cast<char *>(&imageHeight), 4);
   one = 1;
   outFile.write(reinterpret_cast<char *>(&one), 2);
   bitsPerPixel = 8;
   outFile.write(reinterpret_cast<char *>(&bitsPerPixel), 2);
   compressionType = 0;
   outFile.write(reinterpret_cast<char *>(&compressionType), 4);
   compression = 0;
   outFile.write(reinterpret_cast<char *>(&compression), 4);
   preferredXResolution = 300;
   outFile.write(reinterpret_cast<char *>(&preferredXResolution), 4);
   preferredYResolution = 300;
   outFile.write(reinterpret_cast<char *>(&preferredYResolution), 4);
   numColorMapEntries = 256;
   outFile.write(reinterpret_cast<char *>(&numColorMapEntries), 4);
   numSignificantColors = 256; 
   outFile.write(reinterpret_cast<char *>(&numSignificantColors), 4);

/*Write the color table into the .bmp file*/
   for (int i = 0; i < 256; i++) {
   	outFile.write(reinterpret_cast<char *>(&colorTable[i].b), 1);
   	outFile.write(reinterpret_cast<char *>(&colorTable[i].g), 1);
   	outFile.write(reinterpret_cast<char *>(&colorTable[i].r), 1);
   	outFile.write(reinterpret_cast<char *>(&z), 1);
   }
/*Write the pixel data into the .bmp file.  Make sure line lengths
are multiples of 4 bytes.  They are in this case.  A line is
300 bytes long.*/
   for (int i = height-1; i >= 0; i--)
      for (int j = 0; j < width; j++) 
   	 outFile.write(reinterpret_cast<char *>(&bitmapI[i][j]), 1);   
}

// redefine the operator >>
istream& operator>>(istream& in, Image& x)
{
   in >> x.bitmap;
   x.width = x.bitmap.Columns();
   x.height = x.bitmap.Rows();
   return in;
}
