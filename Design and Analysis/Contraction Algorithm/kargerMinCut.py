import random, copy

def initialize():
    adj = []
    with open('kargerMinCut.txt') as f:
    #with open('Test Case 6.txt') as f:
        for line in f:
            array = list(map(int, line.split()))
            adj.append(array)
    return adj

def remove_edge(adj, i):
    #print("removed " + str(i))
    #print(adj[i])
    adj.pop(i)

def contraction(adj):
    #print(id(adj))
    #print(adj)
    while (len(adj) > 2):
        vertex1_ind = random.randint(0, len(adj)-1)
        vertex1 = adj[vertex1_ind][0]
        #print("vertex1_ind " + str(vertex1_ind))
        if len(adj[vertex1_ind]) < 2:
            print('something wrong')
            print(len(adj[vertex1_ind]))
            print(len(adj))
        vertex2_pos = random.randint(1, len(adj[vertex1_ind])-1)
        #print("vertex2_pos " + str(vertex2_pos))
        #print(adj[vertex1_ind])
        vertex2 = adj[vertex1_ind][vertex2_pos]
        vertex2_ind = get_index(adj, vertex2)
        #print("vertex2_ind " + str(vertex2_ind))
        #print("vertex2 " + str(vertex2))
        #print('contract ' + str(vertex2) + ' to ' + str(vertex1))
        add_edge(adj, vertex1_ind, vertex2_ind)
        remove_self_loop(adj, vertex1_ind, vertex2)
        remove_edge(adj, vertex2_ind)
        #print(adj)
    return len(adj[0])-1
            
def remove_self_loop(adj, i, j):
    #print(adj[i])
    array_length = len(adj[i])
    k = 1
    while k < array_length:
        if adj[i][k] == j or adj[i][k] == adj[i][0]:
            #print(adj[i][k])
            #print(k)
            #print(j)
            #print(adj[i][0])
            adj[i].pop(k)
            k = k - 1
            array_length = array_length - 1
        k = k + 1
    if len(adj[i]) < 2:
        print 'only one element in the list'
        print adj[i]
            
def has_edge(adj, i, j):
    for k in xrange(1, len(adj[i])):
        if adj[i][k] == j:
            return True
        else:
            return False
        
def add_edge(adj, i, j):
    #print(adj[j])
    for k in xrange(1, len(adj[j])):
        l = get_index(adj, adj[j][k])
        #print(adj[l])
        for m in xrange(1, len(adj[l])):
            if adj[j][0] == adj[l][m] and adj[l][m] != adj[i][0]:
                adj[i].append(adj[j][k])
                #print(adj[j][k])
                #print("l " + str(l))
                adj[l][m] = adj[i][0]
                
def get_index(adj, i):
    for j in xrange(len(adj)):
        if adj[j][0] == i:
            return j
            
if __name__ == "__main__":
    adj_list = initialize()
    #print(adj_list)
    cut = len(adj_list)
    for i in xrange(len(adj_list)*len(adj_list)):
        adj = copy.deepcopy(adj_list)
        
        temp = contraction(adj)
        if temp < cut:
            cut = temp
        print('iteration ' + str(i) + ': ' + str(cut))
    #print(len(adj[1]))
    
