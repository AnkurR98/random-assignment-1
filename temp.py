import pandas as pd
import matplotlib.pyplot as plt

#UserInfo.tsv
leaf_info=pd.read_csv('./leaf-dataset/leaf.csv',delimiter=',')
print(list(leaf_info.columns.values)) #file header
print(leaf_info.head(15)) #first N rows
plot_list = leaf_info.head(25)

plt.scatter(plot_list['Specimen Number'], plot_list['Elongation'], marker="*")
plt.show()