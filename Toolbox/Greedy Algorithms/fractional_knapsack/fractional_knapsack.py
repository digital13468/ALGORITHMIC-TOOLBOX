# Uses python3
import sys, operator

def get_optimal_value(capacity, weights, values):
    value = 0.
    ratios = [(vi*1.0)/wi for vi, wi in zip(values, weights)]
    ratios_sorted_index = sorted(range(len(ratios)), key = lambda k: ratios[k])
    weight = 0
    while weight < capacity and len(weights) > 0:
        if (weight + weights[-1] <= capacity):
            value = value + values[ratios_sorted_index[-1]]
            weight = weight + weights[ratios_sorted_index[-1]]
            #values.pop(len(values)-1)
            #weights.pop(len(weights)-1)
            ratios_sorted_index.pop(len(ratios_sorted_index)-1)
            #ratios.pop(len(ratios)-1)
        else:
            value = value + (capacity - weight) * ratios[ratios_sorted_index[-1]]
            break
        print value
    return value


if __name__ == "__main__":
    data = list(map(int, sys.stdin.read().split()))
    n, capacity = data[0:2]
    values = data[2:(2 * n + 2):2]
    weights = data[3:(2 * n + 2):2]
    #print data
    #print values
    #print weights
    opt_value = get_optimal_value(capacity, weights, values)
    print("{:.10f}".format(opt_value))
