#Uses python3

import sys

def edit_distance(s, t):
    distance = [[0 for i in range(len(t) + 1)] for j in range(len(s) + 1)]
    for i in range(len(s) + 1):
        for j in range(len(t) + 1):
            if i == 0:
                distance[i][j] = j
            elif j == 0:
                distance[i][j] = i
            elif s[i - 1] == t[j - 1]:
                distance[i][j] = distance[i - 1][j - 1]
            else:
                distance[i][j] = min(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + 1)
    #print(distance)
    return get_sequence(distance, s, t)            

def get_sequence(dist, s, t):
    #print(s, t)
    i = len(s)
    j = len(t)
    sequence = []
    while i > 0 or j > 0:
        if s[i - 1] == t[j - 1]:
            sequence.append(s[i - 1])
            i = i - 1
            j = j - 1
        else:
            if dist[i][j] == dist[i - 1][j] + 1:
                #sequence.pop(i - 1)
                i = i - 1
            elif dist[i][j] == dist[i][j - 1] + 1:
                #sequence.pop(i)
                j = j - 1
            elif dist[i][j] == dist[i - 1][j - 1] + 1:
                i = i - 1
                j = j - 1
            
            else:
                assert False
    #print(sequence)
    return [sequence[i]for i in range(len(sequence) - 1, -1, -1)]
    
def lcs3(a, b, c):
    d = edit_distance(a, b)
    e = edit_distance(c, d)
    return len(e)

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    an = data[0]
    data = data[1:]
    a = data[:an]
    data = data[an:]
    bn = data[0]
    data = data[1:]
    b = data[:bn]
    data = data[bn:]
    cn = data[0]
    data = data[1:]
    c = data[:cn]
    print(lcs3(a, b, c))
