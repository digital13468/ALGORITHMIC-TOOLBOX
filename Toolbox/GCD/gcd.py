# Uses python3
import sys, time

@profile
def gcd(a, b):
    current_gcd = 1
    for d in range(2, min(a, b) + 1):
        if a % d == 0 and b % d == 0:
            if d > current_gcd:
                current_gcd = d

    return current_gcd

@profile
def gcd1(a, b):
    latest_gcd = 1
    if (a > 0 and b > 0):
        if (a >= b):
            latest_gcd = gcd1((a % b), b)
        else:
            latest_gcd = gcd1(a, (b % a))
    else:
        return (a + b)
    return latest_gcd
    
if __name__ == "__main__":
    input = sys.stdin.readline()
    a, b = map(int, input.split())
    start_time = time.time()
    print(gcd1(a, b))
    print("--- %s seconds ---" % (time.time() - start_time))
    
    start_time = time.time()
    print(gcd(a, b))
    print("--- %s seconds ---" % (time.time() - start_time))
