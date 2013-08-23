##Capstone Graphing Program:

##math terms
from math import sin, cos, tan, sqrt, log, fabs, asin, acos, atan, log1p
##read in equation argument:
import sys


##useful constants
pi = 3.141592654
e = 2.718281828459
g = 9.8


##Point class
class Point:

    ##xcoord, ycoord
    def __init__(self, xcoord, ycoord):
        self.x = xcoord
        self.y = ycoord

    ##Stretch the point by amount stretch
    def Stretch(self, stretch):
        self.x = self.x*stretch
        self.y = self.y*stretch

    ##return a string of the point
    def to_string(self):
        return  str(self.x) + " " + str(self.y)


##Equation class
class Equation:

    ##string equation, double step
    def __init__(self, equ, s):
        self.step = s
        self.equation = equ

        ##default bounds of the equation
        self.lower = -8
        self.upper = 10

        ##list of points
        self.points = self.toPoints(self.equation)

    ##set the bounds of the equation
    def setBounds(self, lower_bound, upper_bound):
        self.lower = lower_bound
        self.upper = upper_bound

    ##make a list of Points
    def toPoints(self, equ):
        pointlist = []
        x = self.lower
        while x<=self.upper:
            coord = Point(x,eval(equ))
            pointlist.append(coord)
            x+=self.step
        return pointlist

    ##stretch all the points in the equation by the amount stretch
    def Stretch(self, stretch):
        for point in self.points:
            point.Stretch(stretch)

    ##return a string of the points
    def to_string(self):
        to_return = ""
        for point in self.points:
            to_return += point.to_string() + "\n"
        return to_return

equa = str(sys.argv[1])
st = float(sys.argv[2])
y = Equation(equa, st)
print y.to_string()







