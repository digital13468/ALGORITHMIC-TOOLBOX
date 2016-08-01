# Uses python3
import sys

def optimal_summands(n):
    summands = []
    #start_number = 1
    if n > 2:
        summands.append(1)
        sub_optimal_summands(n - 1, 2, summands)
    else:
        summands.append(n)
    return summands

def sub_optimal_summands(n, start, array):
    if n > 2 * start:
        array.append(start)
        sub_optimal_summands(n - start, start + 1, array)
    else:
        array.append(n)

if __name__ == '__main__':
    input = sys.stdin.read()
    n = int(input)
    summands = optimal_summands(n)
    print(len(summands))
    for x in summands:
        print(x, end=' ')
