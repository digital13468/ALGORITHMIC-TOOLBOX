
# Uses python3
import time

@profile
def calc_fib(n):
    if (n <= 1):
        return n

    return calc_fib(n - 1) + calc_fib(n - 2)

@profile
def calc_fib1(n):
    i = 2
    a = 0
    b = 1
    if (n <= 1):
        return n
    else:
        while (i < n):
            if (i % 2 == 0):
                a = a + b
            else:
                b = a + b
            i = i + 1
    return a + b
    
n = int(input())
start_time = time.time()
print(calc_fib(n))
print("--- %s seconds ---" % (time.time() - start_time))

start_time = time.time()
print(calc_fib1(n))
print("--- %s seconds ---" % (time.time() - start_time))
