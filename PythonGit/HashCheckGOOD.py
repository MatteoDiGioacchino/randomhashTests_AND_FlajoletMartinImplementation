import matplotlib
from matplotlib import pyplot as plt
import randomhash
import numpy as np
import time


input_file = "distinct_words.txt"

with open(input_file, "r") as file:
    distinct_words = file.read().splitlines()

num_inputs = len(distinct_words)

total_collisions = 0
hash_value_counts = {}
num_tests = 125

hash = randomhash.RandomHashFamily(count=num_tests)
sets = [set() for _ in range(0,num_tests)]


total_collisions = 0
max_collisions = 0

Timestamps = []
cols = set()

for k in range (0,num_tests):
    hash = randomhash.RandomHashFamily(count=1)
    collisions = 0
    start_time = time.time()
    for word in distinct_words:
        hashed = hash.hashes(word)[0]
        hash_result = str(bin(hashed)[2:])
        if hash_result in sets[k]:
            hash_value_counts[hash_result] += 1
            collisions += 1
            cols.add(hash_result)
        else:
            sets[k].add(hash_result)
            hash_value_counts[hash_result] = 1
    Timestamps.append(time.time()-start_time)
    total_collisions += collisions
    max_collisions = max(max_collisions,collisions)

sumtotal = 0
for k in range(0,num_tests):
    sumtotal += Timestamps[k]
#print(cols)
print(len(cols))

print("Numero di collisioni: ", total_collisions)
print("Numero di collisioni massime per funzione: ", max_collisions)
print("Tempo di esecuzione complessivo su N*",num_inputs," misurazioni: ",sumtotal)
print("Tempo di esecuzione medio per ",num_inputs," misurazioni: ",(sumtotal/num_tests))