# Uses python3
import sys, time

@profile
def get_fibonaccihuge(n, m):
    pisano = [0, 1]
    a = 0
    b = 1
    if (n <= 1):
        return n
    else:
        while True:
            if (len(pisano) % 2 == 0):
                a = (a + b) % m
                pisano.append(a)
            else:
                b = (a + b) % m
                pisano.append(b)

            if (pisano[len(pisano) - 2] == 0 and pisano[len(pisano) - 1] == 1):
                pisano = pisano[0:-2]
                break
    #print(pisano)            
    return (pisano[n % len(pisano)])

if __name__ == '__main__':
    input = sys.stdin.readline();
    n, m = map(int, input.split())
    
    start_time = time.time()
    print(get_fibonaccihuge(n, m))
    print("--- %s seconds ---" % (time.time() - start_time))
