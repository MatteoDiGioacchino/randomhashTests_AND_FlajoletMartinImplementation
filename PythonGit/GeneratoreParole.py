import random
import string


def generate_random_word(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for _ in range(length))


num_words = 5000

with open('distinct_words.txt', 'w') as file:
    distinct_words = set()
    while len(distinct_words) < num_words:
        word = generate_random_word(8)  
        distinct_words.add(word)
    
    for word in distinct_words:
        file.write(word + '\n')

print(f"Numero di parole distinte generate: {len(distinct_words)} ")