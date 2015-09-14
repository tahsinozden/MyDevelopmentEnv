"""
	This script aims to show usage of decorators.
	Mainly shows how they can be used in logs.
"""

import time
from datetime import datetime

def tell_me_your_name(func):
	"""	This function can be used as a decorator to print executed function name"""
	def _wrapper(*args):
		print 'Called Func. Name ', func.__name__
		func(*args)

	return _wrapper

# it prints the called function name
@tell_me_your_name
def func_proc():
	print "I am a function"


def check_params(func):
	"""	This can be used to check input paramaters passed to a funtion """
	def wrapper(h,w):
		if isinstance(h, str) or isinstance(w,str):
			print 'Error: invalid input params'
			return
		else:
			print "params ok"
			# it must return the function because 
			# the function returns a value
			return func(h,w)
	return wrapper

# must be used as a decorator to be functional
def show_exec_time(func):
	""" This can be used to show execution time of a called function """
	# *args is used becuase we cannot be sure how many arguments each function can have
	def my_wrapper(*args):
		start =  datetime.now()
		# *args must be passed to the passed function
		func(*args)
		end = datetime.now()
		print 'time elapsed : ', (end - start), 'seconds'
	return my_wrapper

@show_exec_time
# it checks the input parameters
@check_params
def get_area(h,w):
	return h * w

# shows the execution elapsed time of the function
@show_exec_time
@tell_me_your_name
def process():
	print 'I am starting!'
	time.sleep(1)
	print 'I am ending '
	time.sleep(1)

if __name__ == '__main__':
	func_proc()
	print get_area("43","4")
	print get_area(43,4)
	print datetime.now()
	process()