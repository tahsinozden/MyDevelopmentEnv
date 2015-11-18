#include <iostream>
/**
 * It is a value listener which listens a specified value, and when the value of the variable changes,
 * it performs the specified operation passed by an external function.
 */
template<class VarType>
class CValueHandler {
	private:
		VarType m_value;
	
	public:
		 CValueHandler(VarType val){
		 	m_value = val;
		 }
		 VarType getValue(){
		 	return m_value;
		 }
		 void setValue(VarType val){
		 	// do nothing if the value has not been changed
		 	if (m_value != val){
		 		m_value = val;
		 		if ( *valueChanged != nullptr){
		 			// inject the external function here
					 valueChanged();		
				 }
		 		else
		 			std::cout << "No specific function is set!" << std::endl;	
			 }
			 else
				std::cout << "The value has the same value!" << std::endl;	
		 }
		 void (*valueChanged)() = nullptr;
};

void whenValueChnaged(){
	std::cout << __FUNCTION__ << " says that ";
	std::cout << "Now the value has been modified!" << std::endl;
}

void makeSound(){
	std::cout << __FUNCTION__ << "says that I am another rule" << std::endl;
	std::cout << "\a\a\a";
}
void testMechanism(){
	std::string x;
	std::cout << "Input a value : ";
	std::cin >> x;
	CValueHandler<std::string>* val = new CValueHandler<std::string>(x);
	// inject an external function to the class mechanism
	val->valueChanged = &whenValueChnaged;
	
	int counter = 0;
	while(true){
		std::cout << "Input another value : ";
		std::cin >> x;
		
		if ( counter % 2 == 0)
			val->valueChanged = &whenValueChnaged;		
		// now change the rule when the value has been chnaged
		else
			val->valueChanged = &makeSound;
			
		val->setValue(x);
		++counter;
	}
}
int main(){
	// test the functionality
	testMechanism();
	return 0;
}
