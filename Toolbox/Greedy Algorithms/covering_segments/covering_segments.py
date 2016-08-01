# Uses python3
import sys
from collections import namedtuple

Segment = namedtuple('Segment', 'start end')

def optimal_points(segments):
    points = []
    
    
    #for i in range(len(segments)):
    #    print(segments[i])
    while len(segments) > 0:
        end_sorted_index = sorted(range(len(segments)), key = lambda x: segments[x].end)
        end = end_sorted_index[0]
        points.append(segments[end].end)
        s = 0
        while s < len(segments):
            
            if segments[s].start <= points[-1]:
                #print(segments)
                #print(segments[s])
                #print(points[-1])
                segments.pop(s)
                #print(segments)
                s -= 1
            s += 1
        
        #segments.pop(end)
    return points

if __name__ == '__main__':
    input = sys.stdin.read()
    n, *data = map(int, input.split())
    segments = list(map(lambda x: Segment(x[0], x[1]), zip(data[::2], data[1::2])))
    points = optimal_points(segments)
    print(len(points))
    for p in points:
        print(p, end=' ')
