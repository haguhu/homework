/**Octree algorithm for indexed color
Implemented by Jennifer Burg, October 2005
Other files include OctreeNode.cpp,
OctreeNode.h, Bitmap.cpp, Bitmap.h
main.cpp, Image.h

This is Image.cpp*/

#include "Image.h"
#include "Octree.h"
#include "globals.h"
#include <iostream>
#include <fstream>

extern ofstream outFile;
int numLeaves;

void Image::convertToIndexed(void) {
   Octree tree;
   unsigned char rBits, gBits, bBits;
   OctreeNode* ptrToReduce;
   unsigned char color;
   RGB colorTable[256];
   unsigned char bitmapI[300][300];
   unsigned char num = 0; /*Used as an argument to putInTable;
                          the next color index to be put in the color table*/

/* go through all pixels of the image to create the octree */
   for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
         rBits = bitmap.R[i][j];
         gBits = bitmap.G[i][j];
         bBits = bitmap.B[i][j];

         tree.root.insertColor(rBits, gBits, bBits, 0);
         numLeaves = 0;
         tree.root.countLeaves();

         if(numLeaves > 256)
         {
           //traverse tree to find colors to combine as one
           ptrToReduce = tree.root.traverseToLeaves(0);
           tree.root.combineColors(ptrToReduce);
         }
      }
   }
//Put the r,g,b, values in the color table by traversing the octree to its
//leaf nodes.  When a leaf node is reached, the node is given an index
//number, numbering consecutively from 0 to 255.
   tree.root.putInTable(colorTable, num);
//Create a bitmap with the indexed colors.  This will later be written out
//to the .bmp indexed file
   for (int i = 0; i < height; i++) 
      for (int j = 0; j < width; j++) 
         bitmapI[i][j] = tree.root.findColor(bitmap.R[i][j], bitmap.G[i][j], bitmap.B[i][j], 0);
//Write the heading, color table, and indexed bitmap to the .bmp indexed file
   createIndexedFile(colorTable, bitmapI);
}

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

istream& operator>>(istream& in, Image& x)
{
   in >> x.bitmap;
   x.width = x.bitmap.Columns();
   x.height = x.bitmap.Rows();
   return in;
}
