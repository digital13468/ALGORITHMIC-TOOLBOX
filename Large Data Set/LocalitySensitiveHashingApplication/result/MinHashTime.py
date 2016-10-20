import numpy as np
import matplotlib.pyplot as plt


n_groups = 3
index = np.arange(n_groups)
bar_width = 0.2
plt.bar(index, (5.450219974517823, 5.575700001716614, 4.986759977340698), bar_width, color = 'b', label = 'calculate exact Jaccard similarities')
plt.bar(index + bar_width, (3.094879994392395, 10.478920040130616, 18.3860799407959), bar_width, color = 'r', label = 'calculate approximate Jaccard similarities')

plt.bar(index + 2 * bar_width, (14.226339988708496, 218.69162048339842, 646.152802734375), bar_width, color = 'g', label = 'build MinHashMatrix')
plt.bar(index + 3 * bar_width, (17.321219983100892, 229.17054052352904, 664.538882675171), bar_width, color = 'c', label = 'build matrix and calculate similarities')
plt.xticks(index + bar_width, ('100', '350', '600'))

plt.legend(loc = "upper left")
plt.ylabel('run time (second)')
plt.xlabel('number of permutations')
plt.show()
