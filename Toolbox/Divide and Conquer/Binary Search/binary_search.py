# Uses python3
import sys, math

def binary_search(a, x, left, right):
    if left <= right:
        i = math.floor((left + right) / 2)
        if a[i] == x:
            return i
        elif a[i] >= x:
            return binary_search(a, x, left, right - 1)
        else:
            return binary_search(a, x, left + 1, right)
    else:
        return -1

def linear_search(a, x):
    for i in range(len(a)):
        if a[i] == x:
            return i
    return -1

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n = data[0]
    m = data[n + 1]
    a = data[1 : n + 1]
    #print(data)
    #print(n)
    #print(m)
    #print(a)
    for x in data[n + 2:]:
        print(binary_search(a, x, 0, len(a) - 1), end=' ')
        #print(linear_search(a, x), end = ' ')
