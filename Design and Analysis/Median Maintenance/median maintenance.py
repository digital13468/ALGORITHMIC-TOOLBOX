import math, time

def initialize():
    #with open('dijkstraData.txt') as f:
    with open('Median.txt') as f:
        integers = []
        for line in f:
            integers.append(int(line))
            #print(len(integers))
    return integers

def median_with_heap(integers):
    heap_high = []
    heap_low = [integers[0]]
    medians = [integers[0]]
    #print(integers)
    for i in range(1, len(integers)):
        x = integers[i]
        if x < heap_low[0]:
            heap_low_insert(heap_low, x)
        else:
            heap_high_insert(heap_high, x)
        y = len(heap_high)
        z = len(heap_low)
        if y - z > 1:
            minimum = heap_extract_min(heap_high)
            heap_low_insert(heap_low, minimum)
        if z - y > 1:
            maximum = heap_extract_max(heap_low)
            heap_high_insert(heap_high, maximum)
        if (i+1) % 2 == 0:
            medians.append(heap_low[0])
        else:
            y = len(heap_high)
            z = len(heap_low)
            if y > z:
                medians.append(heap_high[0])
            else:
                medians.append(heap_low[0])
        #print(medians)
    return medians

        
def heap_extract_min(array):
    if array == []:
        
        return None
    else:
        #print(len(array))
        minimum = array[0]
        heap_high_delete(array, 0)
        return minimum

def heap_extract_max(array):
    if array == []:
        
        return None
    else:
        #print(len(array))
        maximum = array[0]
        heap_low_delete(array, 0)
        return maximum
    
def heap_high_delete(array, ID):
    if len(array) > 1:
        array[ID], array[-1] = array[-1], array[ID]
        array.pop(-1)
        heap_high_bubble_down(array, ID)
    else:
        array.pop(-1)

def heap_low_delete(array, ID):
    if len(array) > 1:
        array[ID], array[-1] = array[-1], array[ID]
        array.pop(-1)
        heap_low_bubble_down(array, ID)
    else:
        array.pop(-1)
        
def heap_high_bubble_down(array, ID):
    i = ID
    #print(i, array)
    while i < len(array):
        #print(i)
        if len(array) > 2 * i + 1:
            left_child = 2 * i + 1
        else:
            break

        if len(array) > 2 * i + 2:
            right_child = 2 * i + 2
        else:
            right_child = None
        #print(list(array[i].values())[0], list(array[left_child].values())[0], list(array[right_child].values())[0])    
        if right_child != None:            
            if array[i] > array[right_child] or array[i] > array[left_child]:
                if array[right_child] > array[left_child]:
                    go = left_child
                else:
                    go = right_child
                array[i], array[go] = array[go], array[i]
                i = go
            else:
                break
        else:
            if array[i] > array[left_child]:
                array[i], array[left_child] = array[left_child], array[i]
                i = left_child
            else:
                #print(i)
                break

def heap_low_bubble_down(array, ID):
    i = ID
    #print(i, array)
    while i < len(array):
        #print(i)
        if len(array) > 2 * i + 1:
            left_child = 2 * i + 1
        else:
            break

        if len(array) > 2 * i + 2:
            right_child = 2 * i + 2
        else:
            right_child = None
        #print(list(array[i].values())[0], list(array[left_child].values())[0], list(array[right_child].values())[0])    
        if right_child != None:            
            if array[i] < array[right_child] or array[i] < array[left_child]:
                if array[right_child] < array[left_child]:
                    go = left_child
                else:
                    go = right_child
                array[i], array[go] = array[go], array[i]
                i = go
            else:
                break
        else:
            if array[i] < array[left_child]:
                array[i], array[left_child] = array[left_child], array[i]
                i = left_child
            else:
                #print(i)
                break
            
def heap_high_insert(array, node):
    #print(node)
    array.append(node)
    heap_high_bubble_up(array, len(array) - 1)

def heap_low_insert(array, node):
    #print(node)
    array.append(node)
    heap_low_bubble_up(array, len(array) - 1)
    
def heap_high_bubble_up(array, i):
    #print(array)
    while i > 0:
        #print(list(array[i].values())[0], [array[math.ceil(i / 2) - 1].values()][0])
        if array[i] < array[(int)(math.ceil(i / 2)) - 1]:
            array[i], array[(int)(math.ceil(i / 2)) - 1] = array[(int)(math.ceil(i / 2)) - 1], array[i]
            i = math.ceil(i / 2) - 1
        else:
            break

def heap_low_bubble_up(array, i):
    #print(array)
    while i > 0:
        #print(list(array[i].values())[0], [array[math.ceil(i / 2) - 1].values()][0])
        if array[i] > array[(int)(math.ceil(i / 2)) - 1]:
            array[i], array[(int)(math.ceil(i / 2)) - 1] = array[(int)(math.ceil(i / 2)) - 1], array[i]
            i = math.ceil(i / 2) - 1
        else:
            break

class Node:
    def __init__(self, val):
        self.l_child = None
        self.r_child = None
        self.data = val
        self.size = 1
        
def search_tree_insert(root, node):
    if root is None:
        root = node
    else:
        if root.data > node.data:
            if root.l_child is None:
                root.l_child = node
                root.size = root.size + 1
            else:
                root.size = root.size + 1
                search_tree_insert(root.l_child, node)
                
        else:
            if root.r_child is None:
                root.r_child = node
                root.size = root.size + 1
            else:
                root.size = root.size + 1
                search_tree_insert(root.r_child, node)



def find_median(root, number):
    #print(number, root.size)
    #left = root.l_child
    if root.l_child == None:
        size = 0
        #print(size, size)
    else:
        size = root.l_child.size
    #print(size)
    if size + 1 == number:
        return root.data
    elif size < number:
        return find_median(root.r_child, number - size - 1)
    else:
        return find_median(root.l_child, number)

def pre_order_print(root):
    if not root:
        return
    print(root.data)
    pre_order_print(root.l_child)
    pre_order_print(root.r_child)
    
def median_with_tree(integers):
    medians = [integers[0]]
    r = Node(integers[0])
    for i in range(1, len(integers)):
        search_tree_insert(r, Node(integers[i]))
        #print('')
        #pre_order_print(r)
        if (i + 1) % 2 == 0:
            num = (i + 1) / 2
        else:
            num = (i / 2) + 1
        #if r.l_child is not None:
        #    print(r.r_child)
        medians.append(find_median(r, num))
        #print(num)    
            
    return medians

if __name__ == '__main__':
    ints = initialize()
    #ints = [20, 5, 15, 1, 3, 2, 8, 7, 9, 10, 6, 11, 4]
    #ints = [6331, 2793, 1640, 9290, 225, 625, 6195, 2303, 5685, 1354]
    #ints = [1, 10, 2, 4]
    time_start = time.clock()
    med = median_with_heap(ints)
    print(time.clock() - time_start)
    time_start = time.clock()
    med1 = median_with_tree(ints)
    print(time.clock() - time_start)
    print(sum(med) % 10000)        
    print(sum(med1) % 10000)
    #print(med)
    #print(med1)
