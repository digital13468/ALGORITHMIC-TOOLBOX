import sys

sys.setrecursionlimit(5000)

t = 0
s = -1
#level = 0
def initialize():
    adj = {}
    rev = {}
    
    with open('SCC.txt') as f:
        j = 0
        for line in f:
            array = list(map(int, line.split()))
            j += 1
            if j % 600000 == 0:
                print(j)
            if array[0] in adj:
                adj[array[0]].append(array[1])
            else:
                adj.update({array[0]: [array[1]]})

            #print(adj)
            #if array[0] > len(adj):
            #    for i in range(len(adj), array[0] - 1):
            #        adj.insert(i, list([i + 1]))
            #    adj.append(array)
            #else:
            #    adj[array[0] - 1].append(array[1])

            if array[1] in rev:
                rev[array[1]].append(array[0])
            else:
                rev.update({array[1]: [array[0]]})
            #if array[1] > len(rev):
            #    for i in range(len(rev), array[1] - 1):
            #        rev.insert(i, list([i + 1]))
            #    rev.append(list([array[1], array[0]]))
            #else:
            #    rev[array[1] - 1].append(array[0])


    return adj, rev

def finishing_time(array):
    exp = set()
    ft = {}
    nodes = array.keys()
    for i in range(len(array)-1, -1, -1):
        node = nodes[i]
        if node in exp:
            continue
        else:
            finishing_time_DFS(array, node, exp, ft)
    return ft

def finishing_time_DFS(array, i, explored, ft):
    global t
    #global level
    #level += 1
    #print(level)
    #if i not in [x[0] for x in array]:
    if i not in array:
        explored.add(i)
        #global t
        t += 1
        ft.update({i: t})
    else:
        explored.add(i)
        #pos = [x[0] for x in array].index(i)
        #pos = i - 1    
        for j in array[i]:
            #print(len(array[pos]))
            if j in explored:
                continue
            else:
                finishing_time_DFS(array, j, explored, ft)
        #global t
        t += 1
        ft.update({i: t})
        #print(ft)
    #level -= 1

def leader_DFS_loop(array, ft):
    sorted_x = sorted(ft.items(), key = lambda x: x[1])
    nodes = [x[0] for x in sorted_x]
    exp = set()
    leader = {}
    #nodes = ft.keys()
    scc = {}
    for i in range(len(ft)-1, -1, -1):
        node = nodes[i]
        if node in exp:
            continue
        else:
            global s
            s = node
            scc.update({s: []})
            leader_DFS(array, node, exp, leader, scc)
    return leader, scc

def leader_DFS(array, i, explored, leader, scc):
    #if i not in [x[0] for x in array]:
    if i not in array:
        explored.add(i)
        leader.update({i: s})
        scc[s].append(i)
    else:
        explored.add(i)
        leader.update({i: s})
        scc[s].append(i)
        #pos = i - 1
        for j in array[i]:
            if j in explored:
                continue
            else:
                leader_DFS(array, j, explored, leader, scc)
        
if __name__ == "__main__":
    adj_list, rev_list = initialize()
    #print(adj_list)
    print('adjacent lists built')
    #print(rev_list)
    ft = finishing_time(rev_list)
    #print(ft)
    print('finishing times computed')
    leader, SCC = leader_DFS_loop(adj_list, ft)
    #print(leader)
    print('leaders and SCCs computed')
    SCC_size = [len(x) for x in SCC.values()]
    
        
    #SCC = []
    #while len(leader) > 0:
    #    temp = 0
    #    x = len(leader)-1
    #    i = 1
    #    while i <= x:
    #        #print(leader[i])
    #        if leader[i][1] == leader[0][1]:
    #            temp = temp + 1
    #            leader.pop(i)
    #            i = i-1
    #            x = x - 1
    #        i = i + 1
    #    temp = temp + 1
    #    leader.pop(0)
    #    #print(leader)
    #    SCC.append(temp)
    print(sorted(SCC_size, reverse = True))
