/**
 * define RGB struct and 
 * the compare function which used in popularity algorithm's map
 */

#ifndef RGB_
#define RGB_

// the RGB struct
typedef struct RGB_struct {
  unsigned char r;
  unsigned char g;
  unsigned char b;

}  RGB;

// RGB compare function used in popularity algorithm's map
struct RGBcomp {
  bool operator() (const RGB& lhs, const RGB& rhs) const
  {
	if(lhs.r != rhs.r){
		return lhs.r < rhs.r;
	}else if (lhs.g != rhs.g){
		return lhs.g < rhs.g;
	}
	else {
		return lhs.b < rhs.b;
	}
  }
};

#endif
 

