//J. Burg, Wake Forest University 
//Part of Octree for Indexed Color implementation
//October 2005
#ifndef Poplularity__
#define Poplularity__
#include <map>
#include "globals.h"
using namespace std;

class Popularity {
   public:
	void insertColor(RGB color);
	void putInTable(RGB []);	
	unsigned char findColor(unsigned char, unsigned char, unsigned char,RGB colorTable[]);
   private:
        map<RGB,int,RGBcomp> colorMap;

};
#endif
