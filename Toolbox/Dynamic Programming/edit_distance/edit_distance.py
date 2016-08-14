# Uses python3
def edit_distance(s, t):
    distance = [[0 for i in range(len(t) + 1)] for j in range(len(s) + 1)]
    for i in range(len(s) + 1):
        for j in range(len(t) + 1):
            if i == 0:
                distance[i][j] = j
            elif j == 0:
                distance[i][j] = i
            elif s[i - 1] == t [j - 1]:
                distance[i][j] = distance[i - 1][j - 1]
            else:
                distance[i][j] = min(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + 1)
    #print(distance)            
    return distance[len(s)][len(t)]

if __name__ == "__main__":
    print(edit_distance(input(), input()))
