

#include "Popularity.h"
#include <math.h>
#include <iostream>
#include <fstream>
using namespace std;



void Popularity::insertColor(RGB color) {
	map<RGB,int,RGBcomp>::iterator it;
	it = colorMap.find(color);
	if(it != colorMap.end()){
		it->second++;
	}else{
		colorMap.insert(pair<RGB,int>(color,1));
	}

}

void Popularity::putInTable(RGB colorTable[]) {
	map<RGB,int,RGBcomp>::iterator it;
	
	for(int i = 0; i < 256; i++){
		RGB color;
		int colorNumber = 0;
		it = colorMap.begin();
		while(it != colorMap.end())
		{
			if(colorNumber<it->second){
				colorNumber = it->second;
				color = it->first;
			}
    			it ++;         
		}
		colorTable[i] = color;
		colorMap.erase(color);
	}
	
}


unsigned char Popularity::findColor(unsigned char rBits, unsigned char gBits, unsigned char bBits,RGB colorTable[])
{
	int result[256];
	for(int i =0 ;i < 256;i++){
		result[i] = pow( (colorTable[i].r - (int)rBits ), 2 ) +  pow( (colorTable[i].g - (int)gBits ), 2 )+ pow( (colorTable[i].b - (int)bBits ), 2 );
	}
	
	//find the min
	int index = 0;
	int min = result[0];
	for(int i =0 ;i < 256;i++){
		if(min > result[i]){
			min = result[i];
			index = i;
		}
	}

	return (unsigned char)index;
	

}

