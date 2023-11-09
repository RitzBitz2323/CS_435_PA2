# PA2 Report

## `MinHash`
We break this problem into two separate dependencies:
### `TermDocumentMatrix`
This class contains the sparse matrix of documents and terms. If the term exists in this document, we store it along with the frequencies in a HashMap.

The procedure to assign an integer to each term in the `TermDocumentMatrix` is by leveraging the ordered behavior of a `LinkedHashSet` to ensure terms are unique and indexed according to the order they were encountered. 
Each term's index in this set corresponds to the term's integer representation used when constructing the term-document frequency matrix.

### `MinHashMatrix`


