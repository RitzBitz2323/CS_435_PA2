# PA2 Report
#### Shiva Neelakantan, Ritvik Ambekar

## `MinHash`

This class constructs the two matrices as dependencies: 

### `TermDocumentMatrix`

This class contains the construction of the sparse 2-dimensional matrix of documents and terms. 
After conditionally adding only the acceptable terms within `getTermsFromFile()` via iterating over each term in the 
document line by line,
**we store the frequency of each term in its specific Hashmap mapping the term to its frequency for each document.** 
We use this hashmap to then construct the term-document matrix data structure, which is effectively an array of term-frequency vectors. Refer to the `TermDocumentMatrix` class.

### `MinHashMatrix`

This class contains tthe construction of the MinHash matrix data structure, a 2-dimensional matrix of documents and permutations (hash functions). **We assign each term to an integer via its hashcode, generated by Java’s `hashCode()` method.** **We then generate the permutation within the class `Permutation`, in which two large parameters, $a$ and $b$, as well as a prime number $p$, are selected uniformly at random to create the hash function** $\Pi(x)=(ax + b)\ \%\ p$. Refer to the `MinHashMatrix` class.

---

## `MinHashAccuracy`

Here are the results from one specific run `MinHashAccuracy.main()`.

Notes for grader:

- these results may vary slightly depending on the machine’s random seeding for the permutation parameters.
- running with a large directory like `space` may take upwards of 15 minutes to finish processing, due to the time 
  complexity of computing the actual Jaccard similarity between documents. This specific run took 823 seconds to 
  terminate.

**Results from `MinHashAccuracy.main()`:**
```
Number of bad approximations: 
Epsilon                0.04      0.07      0.09
400 permutations:     16282       128         6
600 permutations:      4386        78        10
800 permutations:      5613        30         2

Total Runtime: 823.0s
```

We can see that as the number of permutations increases, so does the accuracy of our MinHash estimate. We also see that as the error threshold increases by even just a few percentage points, the number of misses decreases drastically.


## `LSH`
If we increase the bands, that means we reduce the number of rows ber band because bands * rowsPerBand = amount of hash functions. This means each band is more specific and narrows our results thus reducing the amount of similar documents. If we decrease the number of bands, we increase the number of rows per band and thus we consider more documents to be similar and also increasing the likelihood of more false positives. 


### `SimilarDocuments`
The LSH_Data directory was extremely large, so we ran this program on a subset of roughly 1500 files instead.
We ran the program against ten different inputs, five with similarity threshold of 0.5 and other five with threshold 
0.7. We noticed that as we increase the number of bands, we make the algorithm pickier about what it considers to be 
a similar item, as each band represents a chance for the two items to be considered similar.

Here are the results for the 0.5 similarity threshold:

```
The following documents have similarity >= 0.50 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt.copy1:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt.copy4


The following documents have similarity >= 0.50 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt.copy1


The following documents have similarity >= 0.50 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt.copy3


The following documents have similarity >= 0.50 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt.copy7


The following documents have similarity >= 0.50 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt.copy7
```

Here are the results for the 0.7 similarity threshold:
```
The following documents have similarity >= 0.70 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt.copy1


The following documents have similarity >= 0.70 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt.copy3


The following documents have similarity >= 0.70 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt.copy5


The following documents have similarity >= 0.70 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy4
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt.copy3


The following documents have similarity >= 0.70 with /Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt:
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy7
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy6
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy1
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy3
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy2
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy5
	/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt.copy4
```