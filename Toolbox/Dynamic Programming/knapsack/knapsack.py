# Uses python3
import sys

def optimal_weight(W, w):
    result = 0
    K = [[0 for i in range(W + 1)] for x in range(len(w) + 1)]
    for i in range(len(w) + 1):
        for wt in range(W + 1):
            if i == 0 or wt == 0:
                #print(i, wt)
                K[i][wt] = 0
            elif K[i][wt] + w[i - 1] <= wt:
                K[i][wt] = max(w[i - 1] + K[i - 1][wt - w[i - 1]], K[i - 1][wt])
            else:
                K[i][wt] = K[i - 1][wt]
    return K[i][wt]
    #result = 0
    #for x in w:
    #    if result + x <= W:
    #        result = result + x
    return result

if __name__ == '__main__':
    input = sys.stdin.read()
    W, n, *w = list(map(int, input.split()))
    print(optimal_weight(W, w))
