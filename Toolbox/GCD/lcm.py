# Uses python3
import sys, time

def get_gcd(a, b):
    latest_gcd = 1
    if (a > 0 and b > 0):
        if (a >= b):
            latest_gcd = get_gcd((a % b), b)
        else:
            latest_gcd = get_gcd(a, (b % a))
    else:
        return (a + b)
    return latest_gcd

@profile
def lcm(a, b):
    gcd = get_gcd(a, b)
    return ((a * b) / gcd)

if __name__ == '__main__':
    input = sys.stdin.readline()
    a, b = map(int, input.split())
    start_time = time.time()
    print(lcm(a, b))
    print("--- %s seconds ---" % (time.time() - start_time))

