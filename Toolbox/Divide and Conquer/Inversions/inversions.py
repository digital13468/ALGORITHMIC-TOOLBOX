# Uses python3
import sys

def get_number_of_inversions(a, left, right):
    number_of_inversions = 0
    if right - left == 1:
        return [a[left]], number_of_inversions
    ave = (left + right) // 2
    #print((left + right) // 2)
    b, inversions = get_number_of_inversions(a, left, ave)
    #print('b:' + str(b))
    number_of_inversions += inversions
    
    c, number_of_inversions = get_number_of_inversions(a, ave, right)
    number_of_inversions += inversions
    d = b + c
    inversions = merge(d)
    number_of_inversions += inversions
    
    return d, number_of_inversions

def merge(b):
    #print(b)
   
    number_of_inversion = 0
    left_ind = 0
    right_ind = len(b) // 2
    c = b[:]
    for i in range(len(b)):
        if left_ind < len(b) // 2 and right_ind < len(b):
            if c[left_ind] < c[right_ind]:
                b[i] = c[left_ind]
                left_ind += 1
            elif c[left_ind] == c[right_ind]:
                b[i] = c[right_ind]
                right_ind += 1
                number_of_inversion = number_of_inversion + len(b) // 2 - left_ind - 1
            else:
                b[i] = c[right_ind]
                right_ind += 1
                number_of_inversion = number_of_inversion + len(b) // 2 - left_ind
        else:
            if left_ind >= len(b) // 2:
                b[i] = c[right_ind]
                right_ind = right_ind + 1
            elif right_ind >= n:
                b[i] = c[left_ind]
                left_ind = left_ind + 1
    #print(b)
    #print(number_of_inversion)
    return number_of_inversion
                
if __name__ == '__main__':
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    #b = n * [0]
    #print(b)
    print(get_number_of_inversions(a, 0, len(a)))
