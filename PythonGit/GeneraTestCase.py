import numpy as np
import matplotlib.pyplot as plt

input_file = "distinct_words.txt"

with open(input_file, "r") as file:
    distinct_words = file.read().splitlines()

I = np.arange(len(distinct_words))


draw_count = 10000

mean = len(I) // 2  # Media
std_dev = len(I) // 8  #DevStd

# Indici con distribuzione normale
random_indices = np.clip(np.random.normal(mean, std_dev, draw_count).astype(int), 0, len(I) - 1)

# Elementi degli indici
selected_integers = I[random_indices]

selected_strings = []
for k in selected_integers:
    selected_strings.append(distinct_words[k])
print(len(selected_strings))

# File Test Case
with open("test_case.txt","w") as file:
    for string in selected_strings:
        file.write(string+"\n")

# Grafico elementi
plt.figure(figsize=(8, 6))
plt.hist(selected_integers, bins=500, density=True, alpha=0.6, color='b')
plt.title('Distribuzione normale degli indici per 10.000 elementi')
plt.xlabel('Valore degli indici')
plt.ylabel("Densita' di probabilita'")
plt.grid(True)
plt.show()

