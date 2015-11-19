/**
 * This header file contains all necessary functions and attributes for Timer class.
 * \author Tahsin OZDEN
 * \date 2015-11-19
 */
#ifndef TIMER_H
#define TIMER_H
#include <thread>

const int INVALID_TIMER_INTERVAL_VALUE = -1;

class Timer
{
	private:
		// in seconds
		int m_timerInterval;
		std::thread* m_thread;
	public:
		/**
		 * \brief It is a class which enables developers to create their own listeners.
		 * \param interval: time in seconds
		 */
		Timer(int interval);
		/**
		 * \brief It returns the time interval.
		 * \returns interval: time in seconds
		 */
		int getInterval();
		/**
		 * \brief It sets the time interval.
		 * \param interval: time in seconds
		 */
		void setInterval(int val);
		/**
		 * \brief It starts the tick event of the Timer.
		 */
		void start();
		/**
		 * \brief It is tick event of the Timer. It executes the function passed to it.
		 */
		void (*onTickEvent)() = nullptr;
};

#endif
