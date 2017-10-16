
#ifndef Partition__
#define Partition__
#include <vector>
#include "globals.h"
using namespace std;

class Partition {
   public:
	Partition();
	void insertColor(RGB color);
	void putInTable(RGB []);	
	unsigned char findColor(unsigned char, unsigned char, unsigned char,RGB colorTable[]);
   private:
        vector<int> vectorR;
	vector<int> vectorG;
	vector<int> vectorB;

	int boxR ;
	int boxG ;
	int boxB ;

	vector<int> slicesR;
	int numberInOneSliceR ;

	vector<int> slicesG;
	int numberInOneSliceG ;

	vector<int> slicesB;
	int numberInOneSliceB ;

};
#endif
