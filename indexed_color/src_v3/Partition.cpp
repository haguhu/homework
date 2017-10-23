/**
 * the implementation of the partition algorithm
 */

#include "Partition.h"
#include <math.h>
#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;

Partition::Partition(){
	int boxR =0;
	int boxG =0;
	int boxB =0;

	int numberInOneSliceR =0;
	int numberInOneSliceG =0;
	int numberInOneSliceB =0;
}

//store all the colors in the image
void Partition::insertColor(RGB color) {
    vectorR.push_back(color.r);
	vectorG.push_back(color.g);
	vectorB.push_back(color.b);

}

//using partition algorithm to build the color table
void Partition::putInTable(RGB colorTable[]) {
	// 8 * 8 * 4
	sort(vectorR.begin(), vectorR.end());
	sort(vectorG.begin(), vectorG.end());
	sort(vectorB.begin(), vectorB.end());
	 boxR = vectorR[vectorR.size()-1] - vectorR[0];
	 boxG = vectorG[vectorG.size()-1] - vectorG[0];
	 boxB = vectorB[vectorB.size()-1] - vectorB[0];

	cout << "boxR:" << boxR <<endl;
	cout << "boxG:" << boxG <<endl;
	cout << "boxB:" << boxB <<endl;

	numberInOneSliceR = boxR / 8;
	//cout <<"numberInOneSliceR: " <<numberInOneSliceR <<endl;
	int lastPos = 0;
	for(int i = 0; i < 8; i++){
		int sum = 0;
		int count = 0;
		int j;
		for( j = lastPos; vectorR[j] < vectorR[0]+ (i+1)*numberInOneSliceR; j++){
			sum += vectorR[i*numberInOneSliceR + j];
			count ++ ;
		}
		lastPos = j;
		//cout << i <<"sum: " <<sum <<endl;
		slicesR.push_back((int)floor((double)(sum)/(double)count));
	}

	numberInOneSliceG = boxG / 8;
	//cout <<"numberInOneSliceR: " <<numberInOneSliceR <<endl;
	 lastPos = 0;
	for(int i = 0; i < 8; i++){
		int sum = 0;
		int count = 0;
		int j;
		for( j = lastPos; vectorG[j] < vectorG[0]+ (i+1)*numberInOneSliceG; j++){
			sum += vectorG[i*numberInOneSliceG + j];
			count ++ ;
		}
		lastPos = j;
		//cout << i <<"sum: " <<sum <<endl;
		slicesG.push_back((int)floor((double)(sum)/(double)count));
	}

	numberInOneSliceB = boxB / 4;
	//cout <<"numberInOneSliceR: " <<numberInOneSliceR <<endl;
	 lastPos = 0;
	for(int i = 0; i < 4; i++){
		int sum = 0;
		int count = 0;
		int j;
		for( j = lastPos; vectorB[j] < vectorB[0]+ (i+1)*numberInOneSliceB; j++){
			sum += vectorB[i*numberInOneSliceB + j];
			count ++ ;
		}
		lastPos = j;
		//cout << i <<"sum: " <<sum <<endl;
		slicesB.push_back((int)floor((double)(sum)/(double)count));
	}

	for(int i = 0; i < 256 ; i++){
		int rDim = i / (1<<5);
		colorTable[i].r = slicesR[rDim];
		int gDim = (i % (1<<5))/(1<<2);
		colorTable[i].g = slicesG[gDim];
		int bDim = i % (1<<2);
		colorTable[i].b = slicesB[bDim];
	} 
	
}

//find color in the color table
unsigned char Partition::findColor(unsigned char rBits, unsigned char gBits, unsigned char bBits,RGB colorTable[])
{

	int rDim = (((int)rBits - vectorR[0]) / numberInOneSliceR );	
	if(rDim ==8) rDim --;
	int gDim = (((int)gBits - vectorG[0]) / numberInOneSliceG );
	if(gDim ==8) gDim --;
	int bDim = (((int)bBits - vectorB[0]) / numberInOneSliceB );
	if(bDim ==4) bDim --;
	return (unsigned char)((rDim<<5)+(gDim<<2)+bDim );
}

