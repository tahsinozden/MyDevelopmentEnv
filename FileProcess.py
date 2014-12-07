#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Tahsin
#
# Created:     27.11.2014
# Copyright:   (c) Tahsin 2014
# Licence:     <your licence>
#-------------------------------------------------------------------------------

import os

def main():
    pass

def PrintArray(array):
    for item in array:
        print "%s" %item

if __name__ == '__main__':
    main()

    name = "newFile.txt"


    if os.path.lexists(name):
        fileProc = open(name,"r+")
        print "SUCCESS"
    else:
        print "ERROR"
        fileProc = open(name,"w+")

    fileProc = open(name,"w")
    words = [ "tahsin", "ozden", "benim", "adim"]

    PrintArray(words)
    for item in words:
        fileProc.write(item + " ")

    fileProc.close()



