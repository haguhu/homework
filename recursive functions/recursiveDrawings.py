from turtle import *
from random import *

def spiral (initialLength, angle, multiplier):
    if (multiplier<1 and initialLength < 1) or (multiplier>1 and initialLength > 200):
        return
    forward(initialLength)
    left(angle)
    spiral(initialLength * multiplier, angle, multiplier);

def tree (trunkLength, height):
    setheading(90)
    treeIm(trunkLength, height)

def treeIm (trunkLength, height):
    if height < 0:
        return
    forward(trunkLength)
    left(45)
    treeIm(trunkLength/2, height-1)
    right(45)
    right(45)
    treeIm(trunkLength/2, height-1)
    left(45)
    backward(trunkLength)

def drawBox(limits):
    penup()
    setpos(limits[0][0],limits[0][1])
    pendown()

    setheading(0)
    forward(limits[1][0] - limits[0][0])
    left(90)
    forward(limits[1][1] - limits[0][1])
    left(90)
    forward(limits[1][0] - limits[0][0])
    left(90)
    forward(limits[1][1] - limits[0][1])

def forest( n, limits):
    #limits: [(xmin, ymin), (xmax, ymax)]
    xmin = limits[0][0]
    ymin = limits[0][1]
    xmax = limits[1][0]
    ymax = limits[1][1]
    #drawBox(limits)
    for i in range(1, n+1):
        penup()
        setpos( xmin+(xmax - xmin)*random(), ymin + (ymax - ymin)*random())
        pendown()
        tree(random()*200,max(random()*6,3))

def snowflakeSide (sidelength, levels):
    if levels == 0:
        forward(sidelength)
        return;
    snowflakeSide(sidelength/3, levels-1)
    right(60)
    snowflakeSide(sidelength/3, levels-1)
    left(120)
    snowflakeSide(sidelength/3, levels-1)
    right(60)
    snowflakeSide(sidelength/3, levels-1)

def snowflake (sidelength, levels):
    snowflakeSide(sidelength, levels)
    left(120)
    snowflakeSide(sidelength, levels)
    left(120)
    snowflakeSide(sidelength, levels)
    left(120)

def scene():
    forest(5, [(0, 0), (200, 0)])
    for i in range(2,5):
        penup()
        setpos(random()*200,random()*200)
        pendown()
        snowflake(random()*30, i)

    for i in range(0,5):
        penup()
        setpos(random()*200,random()*200)
        pendown()
        spiral(random()*30, -45,0.9)

if __name__=="__main__":
    speed(0)
    scene()
    done()