#!/usr/bin/env python3

import sys
import string

MAX_LINE_LENGTH = 132
linespacing = 0
formatting = 0 # On: 0 Off: 1
margin = 0
currLineLength = 0
width = 132
lastlineflag = 0

def main():
    global currLineLength
    global lastlineflag
    if len(sys.argv) > 1:
        with open(sys.argv[1], 'r') as fileIn:
            for currLine in fileIn:
                if currLine == '\n':
                    if currLineLength > 0:
                        addNewLines()
                    addNewLines()
                    lastlineflag = 1
                else:
                    handleLine(currLine)
                    lastlineflag = 0
    else:
        for currLine in sys.stdin:
            handleLine(currLine)
    if lastlineflag == 0:
        print()
    fileIn.close()

def addBuffer(): #used to add left margins to output
    global margin
    global currLineLength
    for i in range(0, margin):
        sys.stdout.write(" ")
        currLineLength += 1

def addNewLines(): #called when line length has been reached
    global linespacing
    global currLineLength
    print()
    for i in range(0, int(linespacing)):
        print()
    currLineLength = 0

def printWordsInLine(currLine): #called if line is to be formatted
    global currLineLength
    global width
    words = currLine.split()
    if width == 0:
        sys.exit();
    for i in words:
        i = i.strip(string.whitespace)
        if currLineLength == 0:
            addBuffer()
            sys.stdout.write(i)
            currLineLength += len(i)   
        elif (currLineLength + len(i) + 1) > int(width):
            addNewLines()
            addBuffer()
            sys.stdout.write(i)
            currLineLength += len(i)
        else:
            sys.stdout.write(' ' + i)
            currLineLength += (1 + len(i))


def handleLine(currLine): #handles the given input line
    global margin
    global linespacing
    global formatting
    global width
    global currLineLength
    if currLine.startswith('.LS'):
        argumentsList = currLine.split()
        linespacing = argumentsList[1]
    elif currLine.startswith('.FT'):
        argumentsList = currLine.split()
        if argumentsList[1] == 'off':
            formatting = 1
        else:
            formatting = 0
    elif currLine.startswith('.LM'):
        argumentsList = currLine.split()
        if argumentsList[1].startswith('+'):
            number = argumentsList[1]
            number = number.strip('+')
            number = int(number)
        elif argumentsList[1].startswith('-'):
            number = argumentsList[1]
            number = number.strip('-')
            number = -int(number)
        else:
            number = argumentsList[1]
            number = number.strip('+-')
            number = int(number)
            margin = number
            number = 0
        margin = number + int(margin)
        if margin < 0:
            margin = 0
        elif margin > 20:
            margin = 20
    elif currLine.startswith('.LW'):
        argumentsList = currLine.split()
        width = argumentsList[1]
    elif formatting == 1:
        sys.stdout.write(currLine)
    else:
        printWordsInLine(currLine)

if __name__ == "__main__":
    main()
