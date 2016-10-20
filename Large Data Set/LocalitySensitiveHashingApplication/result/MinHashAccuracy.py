import numpy as np
import matplotlib.pyplot as plt


n_groups = 3
index = np.arange(n_groups)
bar_width = 0.3
plt.bar(index, (4211.6, 973.1, 115.9), bar_width, color = 'b', label = 'epsilon = 0.04')
plt.bar(index + bar_width, (9.7, 0.4, 0.0), bar_width, color = 'r', label = 'epsilon = 0.07')

plt.bar(index + 2 * bar_width, (0.0, 0.0, 0.0), bar_width, color = 'g', label = 'epsilon = 0.09')

plt.xticks(index + bar_width, ('400', '600', '800'))

plt.legend()
plt.ylabel('number of pairs for which the similarities differ')
plt.xlabel('number of permutations')
plt.show()
