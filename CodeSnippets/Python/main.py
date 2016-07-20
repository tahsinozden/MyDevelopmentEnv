
# the class needs to be inherited from object
class Animal(object):
    """ class field setter example using decorators """
    def __init__(self, animal_type=None, animal_name=None):
        self.animalType = animal_type
        self.animalName = animal_name
        # self.myType = ''

    # setter attribute function needs to have the same name, animalType
    @property
    def animalType(self):
        return self.__animalType
    @animalType.setter
    def animalType(self, value):
        self.__animalType = value
    @animalType.getter
    def animalType(self):
        return self.__animalType

    @property
    def animalName(self):
        return self.__animalName
    @animalName.setter
    def animalName(self, value):
        self.__animalName = value
    @animalName.getter
    def animalName(self):
        return self.__animalName

    # this property has just get method, if we try to set a value
    # to this field, an error is thrown 'AttributeError: can't set attribute'
    @property
    def myType(self):
        return self._myType
    # @myType.setter
    # def myType(self, value):
    #     self._myType = value
    @myType.getter
    def myType(self):
        self._myType = "My type is " + self.animalType
        return self._myType

    def __str__(self):
        return 'My type is {} and I am {}'.format(self.animalType, self.animalName)

def ternary_conditional_operator():
    """ equivalent for ( a > b ? val1 : val2 ) """
    a, b = 4, 7
    msg = 'a is greater' if a > b else 'b is greater'
    print(msg)
    # swap the values
    a, b = b, a
    msg = 'a is greater' if a > b else 'b is greater'
    print(msg)

def main():
    ternary_conditional_operator()

if __name__ == '__main__':
    main()


