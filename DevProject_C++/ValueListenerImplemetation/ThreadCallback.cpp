/**
 * It is a simple value listener implementation using threads and a callback function 
 * passed to another function inside a thread.
 *
 */
 
#include <iostream>
#include <windows.h>  // includes Sleep function for windows
#include <thread>  // includes thread
#include <unistd.h> // includes sleep function


bool processFlag = false;
// it displays a nessage 
void showMessage(std::string msg){
	std::cout << __FUNCTION__ << " -> " + msg << std::endl;
}
// it compares the current value with the destination value and then callbacks another function
void valueChecker(int *val, int destVal, void (*funcCallback)(std::string)){
	while(true){
		if ( *val == destVal && !::processFlag){
			funcCallback("Hit the target!");
			::processFlag = true;
			break;
		} 
//		Sleep(1000);
		sleep(1); // 1 second
		std::cout << __FUNCTION__ << " -> now checking..." << std::endl;
		std::cout << "Current Value : " << *val << std::endl;
	}
}
// it increments the value
void valueChanger(int* val, int tgtVal){
	while(true){
		std::cout << "The value is changed to " << *val << std::endl;
		if ( *val == tgtVal || ::processFlag){
			std::cout << __FUNCTION__ << " -> Target reached!" << std::endl;
			break;
		} 
//		Sleep(2000); // just fow windows
		sleep(2); // 2 means 2 seconds, works for both unix and windows
		++(*val);
	}
}
int main(){
	// intialize the value
	int* value = new int(5);
	int destValue = 10;
	std::cout << "Current Value : " << *value << std::endl;
	std::cout << "Destination Value : " << destValue << std::endl << std::endl;
	
	// create a listener thread
	std::thread valueListener(valueChecker, value, destValue, showMessage); 
	// create a value changer thread for testing
	std::thread valueModifier(valueChanger, value, 20);
	
	// joins the threads to the main thread,
	// the main thread will wait for both threads to be ended.
	valueListener.join();
	valueModifier.join();
	return 0;
}
