import numpy as np
import matplotlib.pyplot as plt


n_groups = 3
index = np.arange(n_groups)
bar_width = 0.3
plt.bar(index, (2.9, 5.904285714285714, 5.945714285714286), bar_width, color = 'b', label = 'final near duplicates')
plt.bar(index + bar_width, (19.972857142857144, 41.02428571428572, 103.03428571428572), bar_width, color = 'r', label = 'false positives')

plt.bar(index + 2 * bar_width, (4.1, 1.0957142857142856, 1.0542857142857143), bar_width, color = 'g', label = 'false negatives')
#plt.bar(index + 3 * bar_width, (), bar_width, color = 'c', label = 'build matrix and calculate similarities')
plt.xticks(index + bar_width, ('20 (0.94)', '40 (0.86)', '100 (0.63)'))

plt.legend(loc = "upper left")
plt.ylabel('number count')
plt.xlabel('number of bands (approximate threshold)')
plt.show()
