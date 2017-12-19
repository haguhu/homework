/**
 * the interface of the partition algorithm
 */
#ifndef Partition__
#define Partition__
#include <vector>
#include "globals.h"
using namespace std;

class Partition {
   public:
	Partition();
	//store all the colors in the image
	void insertColor(RGB color);
	//using partition algorithm to build the color table
	void putInTable(RGB []);	
	// find color in the color table
	unsigned char findColor(unsigned char, unsigned char, unsigned char,RGB colorTable[]);
   private:
	   // vectors to store the RGB elements in the image file.
    vector<int> vectorR;
	vector<int> vectorG;
	vector<int> vectorB;

	// the boxes of the RGB elements in the image
	int boxR ;
	int boxG ;
	int boxB ;

	// the slices and the number in one slice of the RGB elements in the image
	vector<int> slicesR;
	int numberInOneSliceR ;

	vector<int> slicesG;
	int numberInOneSliceG ;

	vector<int> slicesB;
	int numberInOneSliceB ;

};
#endif
