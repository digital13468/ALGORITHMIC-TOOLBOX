# Uses python3
import sys, math

def get_majority_element(a, left, right):
    print(a)
    if left == right:
        print(-1)
        return -1
    if left + 1 == right:
        print(a[left])
        return a[left]
    k = math.floor((right + left) / 2)
    #print(k)
    left_sub_elem = get_majority_element(a, left, k)
    right_sub_elem = get_majority_element(a, k + 1, right)
    if a[left_sub_elem] == a[right_sub_elem]:
        print(left_sub_elem)
        return left_sub_elem
    lcount = get_frequency(a[left:right + 1], left_sub_elem)
    rcount = get_frequency(a[left:right + 1], right_sub_elem)
    print('lcount: ' + str(lcount))
    print('rcount: ' + str(rcount))
    if lcount > len(a[left:right + 1])/2:
        #print('lcount: ' + lcount)
        return left_sub_elem
    elif rcount > len(a[left:right + 1])/2:
        #print('rcount: ' + rcount)
        return right_sub_elem
    else:
        print(-1)
        return -1

def get_frequency(a, sub_elem):
    frequency = 0
    print(a, sub_elem)
    for i in a:
        if i == sub_elem:
            frequency += 1
    return frequency

if __name__ == '__main__':
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    if get_majority_element(a, 0, n - 1) != -1:
        print(1)
    else:
        print(0)
