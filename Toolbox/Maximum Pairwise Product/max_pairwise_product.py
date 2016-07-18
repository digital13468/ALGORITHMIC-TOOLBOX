# Uses python3
#import sys
#n = int(input())
#b = sys.stdin.read()
#a = [int(x) for x in b.split()]
#assert(len(a) == n)

import random, sys, time
a = [random.randint(0, 100000) for r in xrange(200000)]
n = len(a)
result = 0

time_start = time.clock()
for i in range(0, n):
    for j in range(i+1, n):
        if a[i]*a[j] > result:
            result = a[i]*a[j]
time_elapsed = (time.clock() - time_start)
print(result)
print(time_elapsed)

time_start = time.clock()
max_1 = a[0]
max_2 = a[1]
for i in range(2, n):
    if a[i] > max_1:
        max_1 = a[i]
    elif a[i] > max_2:
        max_2 = a[i]
time_elapsed = (time.clock() - time_start)
print(max_1 * max_2)
print(time_elapsed)
