/**Octree algorithm for indexed color
Implemented by Jennifer Burg, October 2005
Other files include main.cpp, Octree.h
OctreeNode.h, Bitmap.cpp, Bitmap.h
Image.cpp, Image.h.

This is OctreeNode.cpp*/

#include "OctreeNode.h"
#include <iostream>
#include <fstream>
using namespace std;

extern ofstream outFile;
extern int numLeaves;

//------------------------------------------------------------------------------
//OctreeNode Constructor
//This constructor is used only for the root node
//------------------------------------------------------------------------------
OctreeNode::OctreeNode() {
   level = 0;
   numVisits = 0;
   numChildren = 0;
   rTotal = gTotal = bTotal = 0;
   for (int i = 0; i < 8; i++)
      child[i] = NULL;
   isLeaf = false;
   collapsed = false;
}

//------------------------------------------------------------------------------
//OctreeNode Constructor
//Using this constructor creates a new OctreeNode with r, g, and b values set
//------------------------------------------------------------------------------
OctreeNode::OctreeNode(int lev, unsigned char rSoFar,
unsigned char gSoFar, unsigned char bSoFar) {
   level = lev;
   numVisits = 1;
   numChildren = 0;
   rTotal = rSoFar;
   gTotal = gSoFar;
   bTotal = bSoFar;
   for (int i = 0; i < 8; i++)
      child[i] = NULL;
   isLeaf = true;
   collapsed = false;
}

//------------------------------------------------------------------------------
//insertColor
//given unsigned chars that represent r, g, and b values, and an int i that
//represents the level, insertColor adds the correct r, g, and b bits to the
//OctreeNode and moves the r, g, and b values along to the next level by
//recursively calling insertColor
//------------------------------------------------------------------------------
void OctreeNode::insertColor(unsigned char rBits, unsigned char gBits,
unsigned char bBits, int i) {
   OctreeNode *nd;
   unsigned char rgbBits, rBit, gBit, bBit;
   unsigned char rBitsNew, gBitsNew, bBitsNew;
   unsigned char mask;
   
/*If the node has been collapsed, you can't expand from
there any further.*/
   if (collapsed) {
      rTotal += rBits;
      gTotal += gBits;
      bTotal += bBits;
      numVisits++;
   }
/*If this is not level 8, then you're not at the leaf
node level yet.*/
   else  if (i < 8) {
      numVisits++;
      rTotal += rBits;
      gTotal += gBits;
      bTotal += bBits;
/*Figure out which child to insert into*/
      mask = 1 << 7;
      rBitsNew = rBits << i;
      gBitsNew = gBits << i;
      bBitsNew = bBits << i;

      rBit = rBitsNew & mask;

      if (rBit == 0) rgbBits = 0; else rgbBits = 1;
      rgbBits <<= 1;
      gBit = gBitsNew & mask;

      if (gBit != 0) rgbBits += 1;
      rgbBits <<= 1;
      bBit = bBitsNew & mask;

      if (bBit != 0) rgbBits += 1;
/* insert color to determined child */
/* child has not been visited yet*/
      if (child[(int)rgbBits] == NULL) {
         nd = new OctreeNode(i+1, rBits, gBits, bBits);
         child[(int)rgbBits] = nd;
         numChildren++;
         isLeaf = false;
         nd->insertColor(rBits, gBits, bBits, i+1);
      }
   /*child has been visited before*/
      else
         child[(int)rgbBits] -> insertColor(rBits, gBits, bBits, i+1);
   }
   else if (i == 8) {
      numVisits++;
      rTotal += rBits;
      gTotal += gBits;
      bTotal += bBits;
   }
}

//------------------------------------------------------------------------------
//traverseToLeaves
//Recursive function that travels down the octree until it reaches
//the place to insert the color.
//------------------------------------------------------------------------------
OctreeNode* OctreeNode::traverseToLeaves(int i)
{  
  OctreeNode* bestToReplace = this;
  OctreeNode* newBest;

  if (!isLeaf)
  {
    for(int n = 0; n < 8; n++)
    {
      if(child[n] != NULL)
      {
        newBest = child[n] -> traverseToLeaves(i + 1);

        if(newBest -> level > bestToReplace -> level)
        {
          if(newBest -> numChildren > 1)
          {
            bestToReplace = newBest;
            newBest = NULL;
          }
        }
        else if(newBest->level == bestToReplace->level && newBest -> numChildren < bestToReplace -> numChildren && newBest -> numChildren > 1)
        {
          bestToReplace = newBest;
          newBest = NULL;
        }
      }
    }
  }
  return bestToReplace;
}

//------------------------------------------------------------------------------
//combineColors
//Removed the children nodes.  The color associated with this node will
//be the average of the colors that have passed through this node.
//------------------------------------------------------------------------------
void OctreeNode::combineColors(OctreeNode* ptr)
{
  ptr -> numChildren = 0;
  for(int n = 0; n < 8; n++) {
    delete (ptr -> child[n]);
    ptr->child[n] = NULL;
  }
  ptr -> isLeaf = true;
  ptr -> collapsed = true;
}

//------------------------------------------------------------------------------
void OctreeNode::putInTable(RGB colorTable[], unsigned char &numEntries) {
/*Traverse the octree to the leaf nodes.  When a leaf node is encountered,
give it a number, numbering consecutively from 0 to 255.  Put the corresponding
r,g,b values in a color table to be written into the .bmp indexed file later.*/
   if (isLeaf) {
      unsigned char r, g, b;
      r = rTotal/numVisits;
      g = gTotal/numVisits;
      b = bTotal/numVisits;
      colorTable[numEntries].r = (unsigned char) r;
      colorTable[numEntries].g = (unsigned char) g;
      colorTable[numEntries].b = (unsigned char) b;
      indexedColor = (unsigned char) numEntries;
      cout << "numVisits=" << numVisits << " ";
      cout << "rTotal=" << rTotal << endl;
      cout << "[" << (int) numEntries << "]  " << (int) r << ", " << (int) g << ", " << (int) b << endl;
      numEntries++;
   }
   else {
      for (int n = 0; n < 8; n++) {
         if (child[n] != NULL)
            child[n]->putInTable(colorTable, numEntries);
      }
   }
}

//------------------------------------------------------------------------------
//findColor
//After the octree has been constructed, traverse the tree to find the
//indexed color which will replace the original color

unsigned char OctreeNode::findColor(unsigned char rBits, unsigned char gBits,
unsigned char bBits, int i)
{
   unsigned char rgbBits, rBit, gBit, bBit;
   unsigned char rBitsNew, gBitsNew, bBitsNew;
   unsigned char mask;

   if (!isLeaf) {
/*Figure out which child to go to*/
      mask = 1 << 7;
      rBitsNew = rBits << i;
      gBitsNew = gBits << i;
      bBitsNew = bBits << i;

      rBit = rBitsNew & mask;

      if (rBit == 0) rgbBits = 0; else rgbBits = 1;
      rgbBits <<= 1;
      gBit = gBitsNew & mask;

      if (gBit != 0) rgbBits += 1;
      rgbBits <<= 1;
      bBit = bBitsNew & mask;

      if (bBit != 0) rgbBits += 1;
      return (child[(int)rgbBits])->findColor(rBits, gBits, bBits, i+1);
   }
   else 
      return indexedColor;
}

//------------------------------------------------------------------------------
//countLeaves
//Count the leaf nodes of the octree.  If there are more than
//256, the tree will have to be collapsed.
void OctreeNode::countLeaves()
{
  if(!isLeaf)
  {
    for(int n = 0; n < 8; n++)
    {
      if(child[n] != NULL)
        child[n]->countLeaves();
    }
   }
   else
      numLeaves++;
}

