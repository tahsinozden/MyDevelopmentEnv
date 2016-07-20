from unittest import TestCase
from main import Animal

class TestAnimal(TestCase):
    def test_myName(self):
        animal = Animal('tiger', 'predator')
        self.assertEqual(animal.myType, 'My type is tiger')

    def test_str_overloading(self):
        animal = Animal('lion', 'king')
        self.assertEqual('My type is lion and I am king', str(animal))

class TestGeneral(TestCase):
    def test_ternary_conditional_opetator(self):
        self.assertEqual("Greater", ("Greater" if 12 > 0 else "Smaller"))
