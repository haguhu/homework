//J. Burg, Wake Forest University 
//Part of Octree for Indexed Color implementation
//October 2005

#ifndef RGB_
#define RGB_

typedef struct RGB_struct {
  unsigned char r;
  unsigned char g;
  unsigned char b;

}  RGB;

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
 

