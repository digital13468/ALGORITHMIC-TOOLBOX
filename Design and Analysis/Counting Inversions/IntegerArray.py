import math, sys
with open('IntegerArray.txt') as f:
#with open('small test.txt') as f:
    integers = [int(x) for x in f]
#print(integers)
#integers = [4, 3, 2, 1]
#inv = 0
#for i in xrange(len(integers)):
#    for j in xrange(i, len(integers)):
#        if integers[i] > integers[j]:
#            inv = inv + 1
#print(inv)
def sort_and_count(A, n):
    #print(A)
    if n == 1:
        return A, 0
    else:
        left_ind = (int)(math.ceil(1.0*n/2))
        right_ind = (int)(math.ceil(1.0*n/2))
        B, x = sort_and_count(A[0:left_ind], left_ind)
        C, y = sort_and_count(A[left_ind:n], n - right_ind)
        #print id(A)
        D = B + C
        #print id(A)
        z = count_split_inv(D, n)
        
        #print id(A)
    return D, x + y + z
def count_split_inv(A, n):
    #print(A)
    #print(n)
    #D = []
    D = A[:]
    #print id(A)
    #print id(D)
    left_ind = 0
    right_ind = (int)(math.ceil(1.0*n/2))
    inversion = 0
    for k in xrange(n):
        if left_ind < (int)(math.ceil(1.0*n/2)) and right_ind < n:
            if D[left_ind] < D[right_ind]:
                #D.append(A[left_ind])
                A[k] = D[left_ind]
                left_ind = left_ind + 1
                
            elif D[left_ind] > D[right_ind]:
                #D.append(A[right_ind])
                A[k] = D[right_ind]
                right_ind = right_ind + 1
                inversion = inversion + ((int)(math.ceil(1.0*n/2)) - left_ind)
            else:
                print('duplicate number')
                sys.exit()
        else:
            if left_ind >= (int)(math.ceil(1.0*n/2)):
                #D.append(A[right_ind])
                A[k] = D[right_ind]
                right_ind = right_ind + 1
            elif right_ind >= n:
                #D.append(A[left_ind])
                A[k] = D[left_ind]
                left_ind = left_ind + 1
    #print(inversion)
    #print(D)
    #print(A)
    return inversion

sorted_array, number = sort_and_count(integers, len(integers))
#print(sorted_array)
print(number)
os.system("pause")

