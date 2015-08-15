__author__ = 'tahsin'
# It is an implementation of logic gates in digital electronics
# TODO : implement a GUI for simulations
import os
import sys

ERROR = -255
# base class Gate
class Gates(object):
    def __init__(self, pina = None, pinb = None):
        self.PinA = pina
        self.PinB = pinb
        self.Output = None

    def Evaluate(self):
        return

    def Connect(self, item, pinNo = None):
        return

class AND(Gates):
    def __init__(self, pina = None, pinb = None):
        Gates.__init__(self,pina,pinb)
        self.__ObjectID = id(self)

    def Evaluate(self):

        ordinaryBit = None
        gate = None

        if(self.PinA != None and self.PinB != None):

            if(isinstance(self.PinA,Gates) and isinstance(self.PinB,Gates)):
                p1 = self.PinA.Evaluate()
                p2 = self.PinB.Evaluate()

                self.Output = p1 & p2
                return self.Output
            elif(isinstance(self.PinA,Gates)):
                ordinaryBit = self.PinB
                gate = self.PinA

                self.Output = ordinaryBit & gate.Evaluate()
                return self.Output
            elif(isinstance(self.PinB,Gates)):
                ordinaryBit = self.PinA
                gate = self.PinB

                self.Output = ordinaryBit & gate.Evaluate()
                return self.Output
            else:
                self.Output = self.PinA & self.PinB
                return self.Output

        else:
            print("AND Not Initialized!")
            self.Output = None
            return ERROR

    def Connect(self, item, pinNo = None):

        if(self.__ObjectID == id(item)):
            print("Same Gate cannot be connected to itself!")
            return ERROR

        if(pinNo != None):

            if( pinNo == 0 ):
                if(self.PinA == None):
                    self.PinA = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            elif( pinNo == 1 ):
                if(self.PinB == None):
                    self.PinB = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            else:
                print("Currently Not supported!")
                return ERROR

        else:
            if(self.PinA == None):
                self.PinA = item
            elif(self.PinB == None):
                self.PinB = item
            else:
                print("Pins are occupied!")
                return ERROR

class OR(Gates):
    def __init__(self, pina = None, pinb = None):
        Gates.__init__(self,pina,pinb)
        self.__ObjectID = id(self)

    def Evaluate(self):

        ordinaryBit = None
        gate = None

        if(self.PinA != None and self.PinB != None):

            if(isinstance(self.PinA,Gates) and isinstance(self.PinB,Gates)):
                p1 = self.PinA.Evaluate()
                p2 = self.PinB.Evaluate()

                self.Output = p1 | p2
                return self.Output
            elif(isinstance(self.PinA,Gates)):
                ordinaryBit = self.PinB
                gate = self.PinA

                self.Output = ordinaryBit | gate.Evaluate()
                return self.Output
            elif(isinstance(self.PinB,Gates)):
                ordinaryBit = self.PinA
                gate = self.PinB

                self.Output = ordinaryBit | gate.Evaluate()
                return self.Output
            else:
                self.Output = self.PinA | self.PinB
                return self.Output

        else:
            print("OR Not Initialized!")
            self.Output = None
            return ERROR

    def Connect(self, item, pinNo = None):
        if(self.__ObjectID == id(item)):
            print("Same Gate cannot be connected to itself!")
            return ERROR

        if(pinNo != None):

            if( pinNo == 0 ):
                if(self.PinA == None):
                    self.PinA = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            elif( pinNo == 1 ):
                if(self.PinB == None):
                    self.PinB = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            else:
                print("Currently Not supported!")
                return ERROR

        else:
            if(self.PinA == None):
                self.PinA = item
            elif(self.PinB == None):
                self.PinB = item
            else:
                print("Pins are occupied!")
                return ERROR


class NOT(Gates):
    def __init__(self,pin):
        Gates.__init__(self,pina=pin)
        self.Pin = self.PinA
        self.__ObjectID = id(self)

    def Evaluate(self):
        if(self.Pin != None):

            if(isinstance(self.Pin,Gates)):
                self.Output = not self.Pin.Evaluate()
                return self.Output
            else:
                self.Output = not self.Pin
                return self.Output

        else:
            print("No Pins are attached!")
            return ERROR

    def Connect(self, item, pinNo = None):
        if(self.__ObjectID == id(item)):
            print("Same Gate cannot be connected to itself!")
            return ERROR

        if(self.Pin == None):
            self.Pin = item
        else:
            print("Pins are occupied!")
            return ERROR

class XOR(Gates):
    def __init__(self, pina = None, pinb = None):
        Gates.__init__(self,pina,pinb)
        self.__ObjectID = id(self)
		
	# implement XOR gate with two AND , two NOT and one OR gates	
    def __InternalEval(self,pina,pinb):
        gAND1 = AND()
        gAND2 = AND()
        gNOT1 = NOT(pina)
        gNOT2 = NOT(pinb)
        gOR = OR()

		# connect gNOT1 to the first pin of gAND1
        gAND1.Connect(gNOT1,0)
        gAND2.Connect(pina,0)
        # connect pinb to the second pin of gAND1
		gAND1.Connect(pinb,1)
        gAND2.Connect(gNOT2,1)

        gOR.Connect(gAND1,0)
        gOR.Connect(gAND2,1)

        return gOR.Evaluate()

    def Evaluate(self):

        ordinaryBit = None
        gate = None

        if(self.PinA != None and self.PinB != None):

            if(isinstance(self.PinA,Gates) and isinstance(self.PinB,Gates)):
                p1 = self.PinA.Evaluate()
                p2 = self.PinB.Evaluate()

                self.Output = self.__InternalEval(p1,p2)
                return self.Output
            elif(isinstance(self.PinA,Gates)):
                ordinaryBit = self.PinB
                gate = self.PinA

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            elif(isinstance(self.PinB,Gates)):
                ordinaryBit = self.PinA
                gate = self.PinB

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            else:
                self.Output = self.__InternalEval(self.PinA,self.PinB)
                return self.Output

        else:
            print("XOR Not Initialized!")
            self.Output = None
            return ERROR
    def Connect(self, item, pinNo = None):
        if(self.__ObjectID == id(item)):
            print("Same Gate cannot be connected to itself!")
            return ERROR

        if(pinNo != None):

            if( pinNo == 0 ):
                if(self.PinA == None):
                    self.PinA = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            elif( pinNo == 1 ):
                if(self.PinB == None):
                    self.PinB = item
                else:
                    print("Target pin is occupied!")
                    return ERROR
            else:
                print("Currently Not supported!")
                return ERROR

        else:
            if(self.PinA == None):
                self.PinA = item
            elif(self.PinB == None):
                self.PinB = item
            else:
                print("Pins are occupied!")
                return ERROR
class NAND(Gates):
    def __init__(self, pina = None, pinb = None):
        Gates.__init__(self,pina,pinb)
        self.__ObjectID = id(self)

    def __InternalEval(self,pina,pinb):
        gAND = AND(pina,pinb)
        gNOT = NOT(gAND)

        return gNOT.Evaluate()

    def Evaluate(self):

        ordinaryBit = None
        gate = None

        if(self.PinA != None and self.PinB != None):

            if(isinstance(self.PinA,Gates) and isinstance(self.PinB,Gates)):
                p1 = self.PinA.Evaluate()
                p2 = self.PinB.Evaluate()

                self.Output = self.__InternalEval(p1,p2)
                return self.Output
            elif(isinstance(self.PinA,Gates)):
                ordinaryBit = self.PinB
                gate = self.PinA

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            elif(isinstance(self.PinB,Gates)):
                ordinaryBit = self.PinA
                gate = self.PinB

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            else:
                self.Output = self.__InternalEval(self.PinA,self.PinB)
                return self.Output

        else:
            print("NAND Not Initialized!")
            self.Output = None
            return ERROR

        def Connect(self, item, pinNo = None):
            if(self.__ObjectID == id(item)):
                print("Same Gate cannot be connected to itself!")
                return ERROR

            if(pinNo != None):

                if( pinNo == 0 ):
                    if(self.PinA == None):
                        self.PinA = item
                    else:
                        print("Target pin is occupied!")
                        return ERROR
                elif( pinNo == 1 ):
                    if(self.PinB == None):
                        self.PinB = item
                    else:
                        print("Target pin is occupied!")
                        return ERROR
                else:
                    print("Currently Not supported!")
                    return ERROR

            else:
                if(self.PinA == None):
                    self.PinA = item
                elif(self.PinB == None):
                    self.PinB = item
                else:
                    print("Pins are occupied!")
                    return ERROR

class NOR(Gates):
    def __init__(self, pina = None, pinb = None):
        Gates.__init__(self,pina,pinb)
        self.__ObjectID = id(self)

    def __InternalEval(self,pina,pinb):
        gOR = OR(pina,pinb)
        gNOT = NOT(gOR)

        return gNOT.Evaluate()

    def Evaluate(self):

        ordinaryBit = None
        gate = None

        if(self.PinA != None and self.PinB != None):

            if(isinstance(self.PinA,Gates) and isinstance(self.PinB,Gates)):
                p1 = self.PinA.Evaluate()
                p2 = self.PinB.Evaluate()

                self.Output = self.__InternalEval(p1,p2)
                return self.Output
            elif(isinstance(self.PinA,Gates)):
                ordinaryBit = self.PinB
                gate = self.PinA

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            elif(isinstance(self.PinB,Gates)):
                ordinaryBit = self.PinA
                gate = self.PinB

                self.Output = self.__InternalEval(ordinaryBit,gate.Evaluate())
                return self.Output
            else:
                self.Output = self.__InternalEval(self.PinA,self.PinB)
                return self.Output

        else:
            print("NOR Not Initialized!")
            self.Output = None
            return ERROR

        def Connect(self, item, pinNo = None):

            if(self.__ObjectID == id(item)):
                print("Same Gate cannot be connected to itself!")
                return ERROR

            if(pinNo != None):

                if( pinNo == 0 ):
                    if(self.PinA == None):
                        self.PinA = item
                    else:
                        print("Target pin is occupied!")
                        return ERROR
                elif( pinNo == 1 ):
                    if(self.PinB == None):
                        self.PinB = item
                    else:
                        print("Target pin is occupied!")
                        return ERROR
                else:
                    print("Currently Not supported!")
                    return ERROR

            else:
                if(self.PinA == None):
                    self.PinA = item
                elif(self.PinB == None):
                    self.PinB = item
                else:
                    print("Pins are occupied!")
                    return ERROR

def ANDGateTrials():
    g1 = AND(1,1)
    g2 = AND(0,0)
    g3 = AND()

    g3.Connect(g1)
    g3.Connect(g2)

    print(g3.Evaluate())
    #print(g2.Evaluate())

def ORGateTrials():
    g1 = OR(0,0)
    g2 = OR(0,0)
    g3 = OR()

    g3.Connect(g1)
    g3.Connect(g2)

    print(g3.Evaluate())
    #print(g2.Evaluate())

def ANDORGateTrials():
    g1 = OR(1,0)
    g2 = AND(1,1)
    g3 = AND()

    g3.Connect(g1)
    g3.Connect(g2)

    print(g3.Evaluate())
    #print(g2.Evaluate())

def NOTGateTrials():
    g1 = OR(1,0)
    g2 = AND(1,1)
    g3 = AND()

    g3.Connect(g1)
    g3.Connect(g2)
    g4 = NOT(g3)
    print(g3.Evaluate())
    print(g4.Evaluate())


def XORTrials():


    pinA = 1
    pinB = 1

    gAND1 = AND()
    gAND2 = AND()
    gNOT1 = NOT(pinA)
    gNOT2 = NOT(pinB)
    gOR = OR()

    #print(gNOT1.Evaluate())
    #print(gNOT2.Evaluate())

    ##gNOT1.Connect(pinA)
    gAND1.Connect(gNOT1,0)
    gAND2.Connect(pinA,0)

    gAND1.Connect(pinB,1)
    ##gNOT2.Connect(pinB)
    gAND2.Connect(gNOT2,1)

    print(gNOT1.Evaluate())
    print(gNOT2.Evaluate())
    print(gAND1.Evaluate())
    print(gAND2.Evaluate())

    gOR.Connect(gAND1,0)
    gOR.Connect(gAND2,1)

    print(gOR.Evaluate())

def CreateTruthTable():
    """
    for d3 in range(2):
        for d2 in range(2):
            for d1 in range(2):
                for d0 in range(2):
                    pass
    """
    for pinA in range(2):
        for pinB in range(2):
            sys.stdout.write(str(pinA) + str(pinB) + " -> ")
            gAND1 = AND()
            gAND2 = AND()
            gNOT1 = NOT(pinA)
            gNOT2 = NOT(pinB)
            gOR = OR()

            #gNOT1.Connect(pinA)
            gAND1.Connect(gNOT1)
            gAND2.Connect(pinA,0)

            gAND1.Connect(pinB,1)
            #gNOT2.Connect(pinB)
            gAND2.Connect(gNOT2)

            gOR.Connect(gAND1)
            gOR.Connect(gAND2)

            print(gOR.Evaluate())


def MixedTrials():
    g1 = XOR(1,0)
    #g1.Connect(1)
    #g1.Connect(1)
    print(g1.Evaluate())

    g2 = NAND(0,0)
    print(g2.Evaluate())

    g3 = NOR(1,0)
    print(g3.Evaluate())

    #print(id(g1))
    #print(id(g2))
    #print(id(g3))

    g4 = AND(1)
    g4.Connect(1)
    #print(id(3))
    print(g4.Evaluate())

if __name__ == "__main__":
    #ANDGateTrials()
    #ORGateTrials()
    #ANDORGateTrials()
    #NOTGateTrials()

    #XORTrials()
    #CreateTruthTable()
    MixedTrials()
