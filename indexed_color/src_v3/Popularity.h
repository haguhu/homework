/**
 * the interface of the popularity algorithm
 */
#ifndef Poplularity__
#define Poplularity__
#include <map>
#include "globals.h"
using namespace std;

class Popularity {
   public:
	 //store all the colors in the image
	void insertColor(RGB color);
	//using popularity algorithm to build the color table
	void putInTable(RGB []);	
	// find color in the color table
	unsigned char findColor(unsigned char, unsigned char, unsigned char,RGB colorTable[]);
   private:
	   // a map stored the color in the image to choose the popular colors
        map<RGB,int,RGBcomp> colorMap;

};
#endif
