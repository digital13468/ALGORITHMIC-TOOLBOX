import math

with open('QuickSort.txt') as f:
    integers = [int(x) for x in f]

globvar = 0
globvar1 = 0
   
def quick_sort(input_array, start, end, rule):
    
    if (len(input_array[start:end+1]) > 1 and end > start):
        global globvar
        globvar = globvar + end - start
        #print(input_array)        
        p = choose_pivot(input_array[start:end+1], rule)

        temp = input_array[start]
        input_array[start] = input_array[start+p]
        input_array[start+p] = temp
        
        new_p = partition(input_array, start, start, end)
        #print(input_array)
        #print(new_p)
        quick_sort(input_array, start, new_p-1, rule)
        quick_sort(input_array, new_p+1, end, rule)

def partition(array, index, s, e):
    global globvar1
    if (index == s):
        i = index + 1
    else:
        i = s
        
    for j in xrange(s, e+1):

        if (j == index):
            continue
        else:
            globvar1 = globvar1 + 1
            if (array[j] > array[index]):
                continue
            else:
                temp = array[i]
                array[i] = array[j]
                array[j] = temp
                i = i + 1 

    if (index < i):
        temp = array[index]
        array[index] = array[i-1]
        array[i-1] = temp
        return i-1
    else:
        temp = array[index]
        array[index] = array[i]
        array[i] = temp
        return i
                
def choose_pivot(array, policy):
    if (policy == 'first'):
        #print(array[0])
        return 0
    elif (policy == 'last'):
        return len(array) - 1
    elif (policy == 'median-of-the-three'):
        first = array[0]
        last = array[-1]
        middle = array[(int)(math.ceil(len(array) / 2.0) - 1)]
        #print(first, last, middle)
        
        if (first > middle):
            if (first > last):
                if (middle < last):
                    #print(last)
                    return len(array) - 1
                else:
                    #print(middle)
                    return (int)(math.ceil(len(array) / 2.0) - 1)
            else:
                #print(first)
                return 0
        else:
            if (first < last):
                if (middle > last):
                    #print(last)
                    return len(array) - 1
                else:
                    #print(middle)
                    return (int)(math.ceil(len(array) / 2.0) - 1)
            else:
                #print(first)
                return 0
    else:
        print 'designated rule did not match'
        
quick_sort(integers, 0, len(integers)-1, 'first')
#quick_sort(integers, 0, len(integers)-1, 'last')
#quick_sort(integers, 0, len(integers)-1, 'median-of-the-three')
#print(integers)
print(globvar)
print(globvar1)
