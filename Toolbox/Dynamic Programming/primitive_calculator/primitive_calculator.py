# Uses python3
import sys

#def optimal_sequence(n):
#    sequence = []
#    while n >= 1:
#        sequence.append(n)
#        if n % 3 == 0:
#            n = n // 3
#        elif n % 2 == 0:
#            n = n // 2
#        else:
#            n = n - 1
#    return reversed(sequence)

def optimal_sequence(n):
    
    sequence = [0, 1]
    if n == 1:
        return sequence[1:]
    for i in range(2, n + 1):
        temp = n
        if i % 3 == 0:
            temp = min(sequence[(int)(i / 3)] + 1, temp)
        if i % 2 == 0:
            #print(i/2)
            temp = min(sequence[(int)(i / 2)] + 1, temp)
        temp = min(sequence[i - 1] + 1, temp)
        sequence.append(temp)
        #print(i)
    return get_sequence(sequence, n)

def get_sequence(sequence, n):
    i = n
    final = []
    while i > 1:
        final.append(i)
        temp = i
        tmp = i
        if i % 3 == 0 and sequence[(int)(i / 3)] < temp:
            temp = sequence[(int)(i / 3)]
            tmp = (int)(i / 3)
        if i % 2 == 0 and sequence[(int)(i / 2)] < temp:
            temp = sequence[(int)(i / 2)]
            tmp = (int)(i / 2)
        if sequence[i - 1] < temp:
            temp = sequence[i - 1]
            tmp = i - 1
        i = tmp
    final.append(1)
    return sorted(final)
        
input = sys.stdin.read()
n = int(input)
sequence = list(optimal_sequence(n))
print(len(sequence) - 1)
for x in sequence:
    print(x, end=' ')
