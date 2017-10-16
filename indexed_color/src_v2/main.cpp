/**Octree algorithm for indexed color
Implemented by Jennifer Burg, October 2005
Other files include OctreeNode.cpp,
OctreeNode.h, Bitmap.cpp, Bitmap.h
Image.cpp, Image.h

This is main.cpp.

This program was compiled and run with g++ under Linux 

The program runs on raw interleaved RGB color files.  
You will be prompted for a file name.  
You need to use a file with dimensions 300 x 300
since these are hard-coded into the program (or you
can change the dimensions and recompile.  I know --
bad programming practice.  :)   )

If your raw input image file is called sample.raw,
the file will be converted to a .bmp indexed color
file called sampleIndexed.bmp.

You can get sample input and output files from the same
directory where you picked up this program.  
(http://www.cs.wfu.edu/~burg/nsf-due-0340969/worksheets/programs/OctreeForIndexedColor/)

*/

#include "Image.h"
#include <stdlib.h>
#include <string>
#include <fstream>

using namespace std;
ifstream infile;
ofstream outFile;

int main(void)
{
   Image im;
   string fileName;
   int pos;

   cout << "What image file do you want to read?" << endl;
   cin >> fileName;

   infile.open(fileName.c_str(), ios::in|ios::binary);
   if (!infile) {
      cout << "Couldn't read input file " << fileName << endl;
      exit(1);
   }   
   //Append Indexed.raw to the input file name to create the
   //output file name
   pos = fileName.find('.');
   fileName.replace(pos, fileName.length()-1, "Indexed.bmp");
   outFile.open(fileName.c_str(), ios::out|ios::binary);
   if (!outFile) {
      cout << "Couldn't open output file " << fileName << endl;
      exit(1);
   }

   infile >> im;
   //im.convertToIndexed();
   string algorithmSelected;
   cout << "Please select the algorithm which you want to run with (input 1 for popularity and 2 for uniform partitioning)" << endl;
   cin >> algorithmSelected;
   if(algorithmSelected.compare("1") == 0){
	cout<< "run with popularity algorithm " <<endl;
	im.convertToIndexedByPopularity();
   }else if (algorithmSelected.compare("2") == 0){
	cout<< "run with uniform partitioning algorithm" <<endl;
	im.convertToIndexedByUP();
   }else {
	cout<< "wrong input!!" <<endl;
   }

   return 0;
}
