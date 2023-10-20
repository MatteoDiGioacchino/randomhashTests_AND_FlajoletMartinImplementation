#LIBRERIE DA IMPORTARE

import sys
import randomhash

#Input: un valore di hash h(item) binario a 32 bit
#Output: rho(h(item))
def trailing(hashval):
    revHashval = hashval[::-1] # parti dal bit meno significativo
    count = 0
    for k in revHashval: # cerca rho(hashval)...
        if k == '0': 
            count+=1
        else:
            break
    return count #... e restituiscilo

def setBit(value, index):
    return value | (1 << index)
def isBitSet(value, index):
    return (value & (1 << index)) != 0

#Input: il path dello stream di dati, la funzione hash da utilizzare
#Output: una stima del #elementi distinti "d" nello stream
def flajoletMartin(path,hash):
    BITMAP = 0b0 # genera un valore binario che fungera' da BITMAP
    with open(path,'r') as stream: # apri lo stream dal path
        for riga in stream: 
            for parola in riga.split():
                hashval = hash.hashes(parola)[0] # h(parola)...
                binhashval = (bin(hashval)[2:]) # ...in binario
                k = trailing(binhashval) # trova rho(bin(h(item)))
                BITMAP = setBit(BITMAP,k) # aggiorna la BITMAP
    r = 0 # inizializza "r = 0"
    for i in range(0,31):
        if(isBitSet(BITMAP,i) == False):
            r = i
            break
    return 2**r

# L'argomento da passare e' il path dello stream di dati
if __name__=="__main__":
    path = sys.argv[1]
    #Genera una funzione Hash
    hash = randomhash.RandomHashFamily(count=1) 
    d  = flajoletMartin(path,hash)
    print("\n#elementi distinti con Flajolet-Martin e': ",d,"\n") 