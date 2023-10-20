import matplotlib.pyplot as plt
import numpy as np


distinct = 50799


measurements = [
    48000, 58112, 53504, 43008, 58624, 62208, 45056, 45312, 56080, 55552,
    56448, 56832, 54784, 43648, 54528, 58368, 49408, 51200, 54528, 57344,
    48128, 45312, 44288, 51712, 46464, 51328, 55552, 57600, 51200, 45440,
    52224, 54400, 49664, 52096, 53504, 51200, 55424, 44672, 52224, 51456
    ]


differences = [measurement - distinct for measurement in measurements]


group1_differences = differences[:10]
group2_differences = differences[10:20]
group3_differences = differences[20:30]
group4_differences = differences[30:]



x_labels = [f"Measurement {i+1}" for i in range(len(measurements))]


x_positions = np.arange(len(measurements))

bar_width = 0.35
group_spacing = 0.5


plt.figure(figsize=(8, 6))


plt.bar(x_positions[:10], group1_differences, color='red', label='256 funzioni Hash', width=0.4)
plt.bar(x_positions[10:20], group2_differences, color='limegreen', label='512 funzioni Hash', width=0.4)
plt.bar(x_positions[20:30], group3_differences, color='cornflowerblue', label='1024 funzioni Hash', width=0.4)
plt.bar(x_positions[30:], group4_differences, color='violet', label='2048 funzioni Hash', width=0.4)


plt.xlabel("Misure")
plt.ylabel("Differenze")
plt.title("Differenza dal numero di elementi distinti (50799)")
plt.axhline(0, color='gray', linestyle='--', linewidth=1)  
plt.xticks([], [])
plt.legend()
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.tight_layout()
plt.show()