import math

def initialize():
    #with open('dijkstraData.txt') as f:
    with open('test case 5.txt') as f:
        adjacent_list = {}
        for line in f:
            
            array = list(map(str, line.split('\t')))
            adjacent_list.update({array[0]: {}})
            for i in array[1:-1]:
                adjacent = list(map(int, i.split(',')))
                adjacent_list[array[0]].update({str(adjacent[0]): adjacent[1]})
        #print(adj_list)
    return adjacent_list

def find_shortest_path(graph, source):
    distance = {source: 0}
    previous = {source: None}
    Q = [{str(source): 0}]
    for i in graph:
        #print("insert " + str(i))
        if i != source:
            distance[i] = 1000000
            previous[i] = None
            heap_insert(Q, {i: distance[i]})
    #print(graph)
    while Q != []:
        #print(Q)
        node = heap_extract_min(Q)
        if node != None and Q != []:
            for i in graph[node]:
                print(graph[node])
                if distance[i] > distance[node] + graph[node][i]:
                    distance[i] = distance[node] + graph[node][i]
                    #print(Q)
                    ind = [j for j in range(len(Q)) if list(Q[j].keys())[0] == i][0]
                    Q[ind][list(Q[ind].keys())[0]] = distance[node] + graph[node][i]
                    heapify(Q, ind)
                    previous[i] = node
                    #print('update distance to ' + str(i))
    return distance, previous

def heapify(array, ID):

    heap_bubble_up(array, ID)
    heap_bubble_down(array, ID)
        
def heap_extract_min(array):
    if array == []:
        
        return None
    else:
        #print(len(array))
        minimum = list(array[0].keys())[0]
        heap_delete(array, 0)
        return minimum

def heap_delete(array, ID):
    if len(array) > 1:
        array[ID], array[-1] = array[-1], array[ID]
        array.pop(-1)
        heap_bubble_down(array, ID)
    else:
        array.pop(-1)

def heap_bubble_down(array, ID):
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
            if list(array[i].values())[0] > list(array[right_child].values())[0] or list(array[i].values())[0] > list(array[left_child].values())[0]:
                if list(array[right_child].values())[0] > list(array[left_child].values())[0]:
                    go = left_child
                else:
                    go = right_child
                array[i], array[go] = array[go], array[i]
                i = go
            else:
                break
        else:
            if list(array[i].values())[0] > list(array[left_child].values())[0]:
                array[i], array[left_child] = array[left_child], array[i]
                i = left_child
            else:
                #print(i)
                break
    
def heap_insert(array, node):
    #print(node)
    array.append(node)
    heap_bubble_up(array, len(array) - 1)

def heap_bubble_up(array, i):
    #print(array)
    while i > 0:
        #print(list(array[i].values())[0], [array[math.ceil(i / 2) - 1].values()][0])
        if list(array[i].values())[0] < list(array[(int)(math.ceil(i / 2)) - 1].values())[0]:
            array[i], array[(int)(math.ceil(i / 2)) - 1] = array[(int)(math.ceil(i / 2)) - 1], array[i]
            i = math.ceil(i / 2) - 1
        else:
            break
    
if __name__ == '__main__':
    adj_list = initialize()
    #print(adj_list)
    dist, pre = find_shortest_path(adj_list, '1')
    #print(dist['7'], dist['37'], dist['59'], dist['82'], dist['99'], dist['115'], dist['133'], dist['165'], dist['188'], dist['197'])
    print(dist['1'], dist['2'], dist['3'], dist['4'], dist['5'], dist['6'])#, dist['7'], dist['8'], dist['9'], dist['10'])#, dist['11'])
