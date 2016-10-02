import numpy as np
import matplotlib.pyplot as plt
#n_groups = 5
#index = np.arange(n_groups)
#bar_width = 0.4
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- deterministic')
#plt.bar(index, (629.8436, 619.1854, 621.8462, 0, 0), bar_width, color = 'b', label = 'total')
#plt.bar(index, (629.8414, 619.1802, 621.8446, 0, 0), bar_width, color = 'c', label = 'query files')
#plt.bar(index, (514.764, 510.0352, 514.57, 0, 0), bar_width, color = 'm', label = 'query database')
#plt.bar(index, (115.0774, 109.145, 107.2742, 0, 0), bar_width, color = 'y', label = 'query DiffFile')
#plt.bar(index, (18.3648, 19.9968, 21.5794, 0, 0), bar_width, color = 'g', label = 'filter building')
#plt.bar(index, (0.001, 0.0004, 0.0006, 0, 0), bar_width, color = 'r', label = 'query filter')
#
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- random')
#plt.bar(index + bar_width, (626.6928, 630.9594, 628.3756, 0, 0), bar_width, color = 'b', hatch="/", label = 'total')
#plt.bar(index + bar_width, (626.6914, 630.9578, 628.3742, 0, 0), bar_width, color = 'c', hatch="/", label = 'query files')
#plt.bar(index + bar_width, (512.1616, 515.1762, 516.3408, 0, 0), bar_width, color = 'm', hatch="/", label = 'query database')
#plt.bar(index + bar_width, (114.5298, 115.7816, 112.0334, 0, 0), bar_width, color = 'y', hatch="/", label = 'query DiffFile')
#plt.bar(index + bar_width, (4.4116, 6.7888, 7.298, 0, 0), bar_width, color = 'g', hatch="/", label = 'filter building')
#plt.bar(index + bar_width, (0.0002, 0.0006, 0.0006, 0, 0), bar_width, color = 'r', hatch="/", label = 'query filter')
#
#plt.plot([0, 1, 2, 3], [652.8758, 652.8758, 652.8758, 652.8758], label = 'NaiveDifferential')
#plt.plot([0], 'w', label = 'BloomDifferential')
#plt.xticks(index + bar_width, ('4', '8', '10'))
#
#plt.legend()
#plt.ylabel('time (seconds)')
#plt.xlabel('bits per elements (m/n)')
#plt.show()


#n_groups = 5
#index = np.arange(n_groups)
#bar_width = 0.3
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = 'NaiveDifferential')
#plt.bar(index + bar_width + bar_width, (23, 23, 23, 0, 0), bar_width, color = 'm', hatch="//", label = 'total')
#plt.bar(index + bar_width + bar_width, (18, 18, 18, 0, 0), bar_width, color = 'c', hatch="//", label = 'DiffFil')
#plt.bar(index + bar_width + bar_width, (5, 5, 5, 0, 0), bar_width, color = 'b', hatch="//",label = 'database')
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = 'BloomDifferential')
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- deterministic')
#plt.bar(index, (18.8, 18.4, 18.6, 0, 0), bar_width, color = 'm', label = 'total')
#plt.bar(index, (13.8, 13.4, 13.6, 0, 0), bar_width, color = 'c', label = 'DiffFile')
#plt.bar(index, (5, 5, 5, 0, 0), bar_width, color = 'b', label = 'database')
#
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- random')
#plt.bar(index + bar_width, (18.8, 18.6, 18.6, 0, 0), bar_width, color = 'm', hatch="/", label = 'total')
#plt.bar(index + bar_width, (13.8, 13.6, 13.6, 0, 0), bar_width, color = 'c', hatch="/", label = 'DiffFile')
#plt.bar(index + bar_width, (5, 5, 5, 0, 0), bar_width, color = 'b', hatch="/", label = 'database')
#
#plt.xticks(index + bar_width + bar_width, ('4', '8', '10'))
#
#plt.legend()
#plt.ylabel('query counts')
#plt.xlabel('bits per elements (m/n)')
#plt.show()


#n_groups = 3
#index = np.arange(n_groups)
#bar_width = 0.4
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- deterministic')
#plt.bar(index, (9394.667, 10106.82, 0), bar_width, color = 'b', label = 'total')
#plt.bar(index, (9394.656, 10106.81, 0), bar_width, color = 'c', label = 'query files')
#plt.bar(index, (8770.959, 9446.825, 0), bar_width, color = 'm', label = 'query database')
#plt.bar(index, (623.6965, 659.9805, 0), bar_width, color = 'y', label = 'query DiffFile')
#plt.bar(index, (19.969, 21.1745, 0), bar_width, color = 'g', label = 'filter building')
#plt.bar(index, (0.009, 0.002, 0), bar_width, color = 'r', label = 'query filter')
#
#plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- random')
#plt.bar(index + bar_width, (9864.708, 10086.35, 0), bar_width, color = 'b', hatch="/", label = 'total')
#plt.bar(index + bar_width, (9864.704, 10086.34, 0), bar_width, color = 'c', hatch="/", label = 'query files')
#plt.bar(index + bar_width, (9193.73, 9441.24, 0), bar_width, color = 'm', hatch="/", label = 'query database')
#plt.bar(index + bar_width, (670.9735, 650.0975, 0), bar_width, color = 'y', hatch="/", label = 'query DiffFile')
#plt.bar(index + bar_width, (7.127, 7.6645, 0), bar_width, color = 'g', hatch="/", label = 'filter building')
#plt.bar(index + bar_width, (0.002, 0.0005, 0), bar_width, color = 'r', hatch="/", label = 'query filter')
#
#plt.plot([0, 1, 2], [11294.77, 11294.77, 11294.77], label = 'NaiveDifferential')
#plt.plot([0], 'w', label = 'BloomDifferential')
#plt.xticks(index + bar_width, ('8', '10'))
#
#plt.legend()
#plt.ylabel('time (seconds)')
#plt.xlabel('bits per elements (m/n)')
#plt.show()


n_groups = 2
index = np.arange(n_groups)
bar_width = 0.9
plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- deterministic')
plt.bar(index, (40986.22, 0), bar_width, color = 'b', label = 'total')
plt.bar(index, (40986.07, 0), bar_width, color = 'c', label = 'query files')
plt.bar(index, (33289.42, 0), bar_width, color = 'm', label = 'query database')
plt.bar(index, (7696.653, 0), bar_width, color = 'y', label = 'query DiffFile')
plt.bar(index, (20.433, 0), bar_width, color = 'g', label = 'filter building')
plt.bar(index, (0.082, 0), bar_width, color = 'r', label = 'query filter')

plt.bar((0), (0), bar_width, color = 'w', edgecolor = "none", label = '- random')
plt.bar(index + bar_width, (40840.94, 0), bar_width, color = 'b', hatch="/", label = 'total')
plt.bar(index + bar_width, (40840.9, 0), bar_width, color = 'c', hatch="/", label = 'query files')
plt.bar(index + bar_width, (33302.86, 0), bar_width, color = 'm', hatch="/", label = 'query database')
plt.bar(index + bar_width, (7538.038, 0), bar_width, color = 'y', hatch="/", label = 'query DiffFile')
plt.bar(index + bar_width, (7.418, 0), bar_width, color = 'g', hatch="/", label = 'filter building')
plt.bar(index + bar_width, (0.018, 0), bar_width, color = 'r', hatch="/", label = 'query filter')

plt.plot([0, 1, 2], [51232.31, 51232.31, 51232.31], label = 'NaiveDifferential')
plt.plot([0], 'w', label = 'BloomDifferential')
plt.xticks(index + bar_width, ('10',''))

plt.legend()
plt.ylabel('time (seconds)')
plt.xlabel('bits per elements (m/n)')
plt.show()
