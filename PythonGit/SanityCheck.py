input_file = "distinct_words.txt"

distinct_words = set()
num_words = 0

with open(input_file, "r") as file:
    for line in file:
        for word in line.split():
            num_words += 1
            distinct_words.add(word)

num_distinct_words = len(distinct_words)

print(f"Numero di Parole: {num_words}")
print(f"Numero di Parole Distinte: {num_distinct_words}")
