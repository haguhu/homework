/**
 * the main function
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



   infile >> im;
      
   int algorithmSelected;
   cout << "Please select the algorithm which you want to run with (input 1 for popularity and 2 for uniform partitioning)" << endl;
   cin >> algorithmSelected;
   if(algorithmSelected == 1){
	cout<< "run with popularity algorithm " <<endl;
	//Append Indexed_popularity.bmp to the output file name
   	pos = fileName.find('.');
   	fileName.replace(pos, fileName.length()-1, "Indexed_popularity.bmp");
   	outFile.open(fileName.c_str(), ios::out|ios::binary);
   	if (!outFile) {
      		cout << "Couldn't open output file " << fileName << endl;
      		exit(1);
   	}
	im.convertToIndexedByPopularity();
   }else if (algorithmSelected == 2){
	cout<< "run with uniform partitioning algorithm" <<endl;
	//Append Indexed_partitioning.bmp to the output file name
   	pos = fileName.find('.');
   	fileName.replace(pos, fileName.length()-1, "Indexed_partitioning.bmp");
   	outFile.open(fileName.c_str(), ios::out|ios::binary);
   	if (!outFile) {
      		cout << "Couldn't open output file " << fileName << endl;
      		exit(1);
   	}
	im.convertToIndexedByUP();
   }else {
	cout<< "wrong input!!" <<endl;
   }

   return 0;
}
