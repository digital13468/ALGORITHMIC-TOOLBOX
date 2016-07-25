# Uses python3
import sys, time

@profile
def get_fibonacci_last_digit(n):
    i = 2
    a = 0
    b = 1
    if (n <= 1):
        return n
    else:
        while (i < n):
            if (i % 2 == 0):
                a = (a + b) % 10
            else:
                b = (a + b) % 10
            i = i + 1
    return (a + b) % 10
    

@profile
def calc_fib(n):
    if (n <= 1):
        return n

    return (calc_fib(n - 1) + calc_fib(n - 2)) % 10

if __name__ == '__main__':
    #input = sys.stdin.read()
    n = int(input())
    #print(n)
    start_time = time.time()
    print(get_fibonacci_last_digit(n))
    print("--- %s seconds ---" % (time.time() - start_time))

    #start_time = time.time()
    #print(calc_fib(n))
    #print("--- %s seconds ---" % (time.time() - start_time))
