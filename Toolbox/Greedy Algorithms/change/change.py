# Uses python3
import sys

def get_change(m):
	#print m
	b = [10, 5, 1]
	change = 0
	for i in xrange(len(b)):

		change = change + m/b[i]
		m = m%b[i]

	#print change
	return change

if __name__ == '__main__':
    m = int(sys.stdin.readline())
    print(get_change(m))
