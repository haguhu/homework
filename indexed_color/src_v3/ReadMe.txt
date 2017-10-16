You can test the octree program on the 300 x 300 pixel image, sample.raw.  

(The program is hard-coded so that it takes only 300 x 300 pixel images.  Not too hard to change.)

The output will be sampleIndexed.bmp.  You can compare your output to the output file stored with this program. 

I've left two output files for you to look at.  They are different based on how the node to "reduce" is chosen

sampleIndexed1.bmp -- the node that has the largest number of children (> 1) is reduced

sampleIndexed.bmp -- the node that has the smallest number of children (> 1) is reduced

Note that the second method creates a smoother image than the first.  This makes sense.  Reducing a node amounts to approximating a group of colors by some average color.  We want to do this to the fewest colors possible, always using the original color when we can.

