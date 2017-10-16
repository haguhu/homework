//J. Burg, Wake Forest University 
//Part of Octree for Indexed Color implementation
//October 2005

#ifndef OctreeNode_
#define OctreeNode_
#include <fstream>
#include <iostream>
#include <stdlib.h>
#include "globals.h"
using namespace std;

class OctreeNode {
   friend class Octree;
   public:
      OctreeNode();
      OctreeNode(int, unsigned char, unsigned char, unsigned char);
      void insertColor(unsigned char, unsigned char, unsigned char, int);
      OctreeNode* traverseToLeaves(int);
      void combineColors(OctreeNode*);
      unsigned char findColor(unsigned char, unsigned char, unsigned char, int);
      void putInTable(RGB [], unsigned char &);
      void countLeaves(void);
   private:
      OctreeNode* child[8];
      int level;
      int numChildren;
      int numVisits;
      unsigned long rTotal;
      unsigned long gTotal;
      unsigned long bTotal;
      bool isLeaf;
      unsigned char indexedColor;
      bool collapsed;
};
#endif

