import time

def initialize_dict():
    #with open('test case 1.txt') as f:
    with open('2sum.txt') as f:
        int_dict = {}
        #hash_open = HashTable_open_addressing()
        #hash_chain = HashTable_chaining()
        #i = 1
        for line in f:
            int_dict.update({int(line): int(line)})
            #hash_open.put(int(line))
            #i += 1
    return int_dict#, hash_open, hash_chain

def initialize_open():
    #with open('test case 1.txt') as f:
    with open('2sum.txt') as f:
        #int_dict = {}
        hash_open = HashTable_open_addressing()
        #hash_chain = HashTable_chaining()
        #i = 1
        for line in f:
            #print(line)
            hash_open.put(int(line))
            #i += 1
            #print(hash_open.slots)
    return hash_open#, hash_chain

def initialize_chain():
    #with open('test case 1.txt') as f:
    with open('2sum.txt') as f:
        #int_dict = {}
        #hash_open = HashTable_open_addressing()
        hash_chain = HashTable_chaining()
        #i = 1
        for line in f:
            #integers.update({int(line): int(line)})
            hash_chain.put(int(line))
            #i += 1
    return hash_chain

def two_sum_dict(integers):
    count = 0
    #for i in range(-6, 7):
    for i in range(-10000, 10001):
        #print(i)
        for j in integers:
            if (i - j) in integers and (i - j) != j:
                #print(i, j)
                count += 1
                break
    return count

def two_sum_open(integers):
    count = 0
    #for i in range(-6, 7):
    for i in range(-10000, 10001):
        #print(i)
        for j in integers.slots:
            #print(j)
            if j != None and integers.get((i - j)) == True and (i - j) != j:
                count += 1
                break
    return count

def two_sum_chain(integers):
    
    count = 0
    found = False
    #for i in range(-6, 7):
    for i in range(-10000, 10001):
        #print(i)
        found = False
        for j in integers.slots:
            if j != [None]:
                for x in j:
                    if integers.get((i - x)) == True and (i - x) != x:
                        count += 1
                        found = True
                        break
            if found == True:
                break
    return count

class HashTable_open_addressing:
    def __init__(self):
        self.size = 1000003
        self.slots = [None] * self.size
        #self.data = [None] * self.size

    #def put(self, key, data):
    def put(self, key):
        hashvalue = self.hashfunction(key, len(self.slots))

        if self.slots[hashvalue] == None:
            self.slots[hashvalue] = key
            #self.data[hashvalue] = data
        else:
            if self.slots[hashvalue] == key:
                #self.data[hashvalue] = data
                pass
            else:
                nextslot = self.rehash(hashvalue, len(self.slots))
                while self.slots[nextslot] != None and self.slots[nextslot] != key:
                    nextslot = self.rehash(nextslot, len(self.slots))

                if self.slots[nextslot] == None:
                    self.slots[nextslot] = key
                    #self.data[nextslot] = data
                else:
                    #self.data[nextslot] = data
                    pass
                                        
    def hashfunction(self, key, size):
        return key % size

    def rehash(self, oldhash, size):
        return (oldhash + 1) % size

    def get(self, key):
        startslot = self.hashfunction(key, len(self.slots))
        #data = None
        
        stop = False
        found = False
        position = startslot
        while self.slots[position] != None and not found and not stop:
            if self.slots[position] == key:
                found = True
                #data = self.data[position]
                
            else:
                position = self.rehash(position, len(self.slots))
                if position == startslot:
                    stop = True
        #return data
        return found
                                        
    def __getitem__(self, key):
        return self.get(key)

    #def __setitem__(self, key, data):
    def __setitem__(self, key):
        #return self.put(key, data)
        return self.put(key)

class HashTable_chaining:
    def __init__(self):
        self.size = 999983
        self.slots = [[None]] * self.size
        #self.data = [None] * self.size

    #def put(self, key, data):
    def put(self, key):
        hashvalue = self.hashfunction(key, len(self.slots))

        if self.slots[hashvalue] == [None]:
            self.slots[hashvalue] = [key]
            #self.data[hashvalue] = data
        else:
            if key in self.slots[hashvalue]:
                #self.data[hashvalue] = data
                pass
            else:
                self.slots[hashvalue].append(key)
                #nextslot = self.rehash(hashvalue, len(self.slots))
                #while self.slots[nextslot] != None and self.slots[nextslot] != key:
                #    nextslot = self.rehash(nextslot, len(self.slots))
                #
                #if self.slots[nextslot] == None:
                #    self.slots[nextslot] = key
                
                #else:
                    
                #    continue
                                        
    def hashfunction(self, key, size):
        return key % size

    #def rehash(self, oldhash, size):
    #    return (oldhash + 1) % size

    def get(self, key):
        startslot = self.hashfunction(key, len(self.slots))
        
        
        #stop = False
        found = False
        #position = startslot
        if key in self.slots[startslot]:
            found = True
        #while self.slots[position] != None and not found and not stop:
        #    if self.slots[position] == key:
        #        found = True
        #        
        #        
        #    else:
        #        position = self.rehash(position, len(self.slots))
        #        if position == startslot:
        #            stop = True
        
        return found
                                        
    def __getitem__(self, key):
        return self.get(key)

    #def __setitem__(self, key, data):
    def __setitem__(self, key):
        #return self.put(key, data)
        return self.put(key)
    
if __name__ == '__main__':
    time_start = time.clock()
    int_dict = initialize_dict()
    
    print(two_sum_dict(int_dict))
    print(time.clock() - time_start)
    time_start = time.clock()
    hash_open = initialize_open()
    print(two_sum_open(hash_open))
    print(time.clock() - time_start)
    time_start = time.clock()
    hash_chain = initialize_chain()
    print(two_sum_chain(hash_chain))
    print(time.clock() - time_start)
