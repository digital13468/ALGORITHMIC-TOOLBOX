# Uses python3
def evalt(a, b, op):
    #print(op)
    if op == '+':
        return a + b
    elif op == '-':
        return a - b
    elif op == '*':
        return a * b
    else:
        assert False

def get_maximum_value(dataset):
    #print(dataset)
    v = [(int)(dataset[i]) for i in range(0, len(dataset), 2)]
    #print(v)        
    n = len(v)
    max_value = [[0 for i in range(n)] for j in range(n)]
    min_value = [[0 for i in range(n)] for j in range(n)]
    
    for s in range(n):
        for i in range(n - s):
            
            j = s + i
            #print(i, j)
            if i == j:
                max_value[i][j] = v[i]
                min_value[i][j] = v[i]
            elif j - i == 1:
                max_value[i][j] = evalt(v[i], v[j], dataset[2 * i + 1])
                min_value[i][j] = evalt(v[i], v[j], dataset[2 * i + 1])
            else:
                for k in range(i, j):
                    #print(i, j, k)
                    #print(max_value[i][k], max_value[k + 1][j], min_value[i][k], min_value[k + 1][j])
                    maximum, minimum = get_maxmin(max_value[i][k], max_value[k + 1][j], min_value[i][k], min_value[k + 1][j], dataset[2 * k + 1])
                    max_value[i][j] = max(max_value[i][j], maximum)
                    min_value[i][j] = min(min_value[i][j], minimum)
    #print(max_value)
    return max_value[0][-1]

def get_maxmin(max_value1, max_value2, min_value1, min_value2, op):
    MM = evalt(max_value1, max_value2, op)
    Mm = evalt(max_value1, min_value2, op)
    mM = evalt(min_value1, max_value2, op)
    mm = evalt(min_value1, min_value2, op)
    #print(MM, Mm, mM, mm)
    return max(MM, Mm, mM, mm), min(MM, Mm, mM, mm)

if __name__ == "__main__":
    print(get_maximum_value(input()))
