#include "Timer.h"
#include <iostream>
#include <thread>
#include <unistd.h>

Timer::Timer(int interval){
	setInterval(interval);
}

int Timer::getInterval(){
	return m_timerInterval;
}

void Timer::setInterval(int val){
	if ( val > 0)
		m_timerInterval = val;
	else {
		std::cout << "Invalid Value!" << std::endl;
		m_timerInterval = INVALID_TIMER_INTERVAL_VALUE;
	}
}

void Timer::start(){
	if ( m_timerInterval == INVALID_TIMER_INTERVAL_VALUE){
		std::cout << "m_timerInterval is NULL." << std::endl;
		return;
	}
	while(true){
		m_thread = new std::thread(onTickEvent);		
		sleep(m_timerInterval);
	}
}


