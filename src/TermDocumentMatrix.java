import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * TermDocumentMatrix
 * describes the frequency of terms that occur in documents
 * rows correspond to terms
 * columns correspond to documents
 *
 * matrix[i][j] = frequency of ith term in jth document
 */
public class TermDocumentMatrix {
    /**
     * map of all terms in each document
     */
    private HashMap<String, HashMap<String, Integer>> matrix =
            new HashMap<String, HashMap<String, Integer>>();

    /**
     * list of all documents in matrix
     */
    private List<String> allDocuments = new ArrayList<>();

    /**
     * ordered set of all terms from documents
     */
    private LinkedHashSet<String> allTerms = new LinkedHashSet<>();

    private HashMap<String, Integer> termMap;

    /**
     * Default constructor
     *
     */
    public TermDocumentMatrix() {
    }

    /**
     * Loads list of terms into matrix for a provided document
     *
     * @param documentName name of document
     * @param terms        list of terms in document
     */
    public void loadTermsIntoTdMatrix(String documentName, List<String> terms) {
        this.termMap = new HashMap<String, Integer>();

        for (String term : terms) {
            if (termMap.containsKey(term)) {
                termMap.put(term, termMap.get(term) + 1);
            } else {
                termMap.put(term, 1);
            }
            this.allTerms.add(term);
        }

        matrix.put(documentName, termMap);
        allDocuments.add(documentName);
    }

    /**
     * return the term document matrix
     *
     * @return int[][] termDocumentMatrix
     */
    public int[][] getTermDocumentMatrix() {

        int[][] termDocumentMatrix =
                new int[allDocuments.size()][allTerms.size()];

        int documentIndex = 0;
        for (String doc : allDocuments) {
            HashMap<String, Integer> terms = matrix.get(doc);
            int termIndex = 0;

            for (String term : allTerms) {
                termDocumentMatrix[documentIndex][termIndex] = terms.getOrDefault(term, 0);
                termIndex++;
            }
            documentIndex++;
        }
        return termDocumentMatrix;
    }

    /**
     * Returns the number of documents in the matrix
     */
    public int numDocuments() {
        return allDocuments.size();
    }

    /**
     * Returns the number of terms in the matrix
     */
    public int numTerms() {
        return allTerms.size();
    }

    /**
     * Returns the list of documents in the matrix
     */
    public List<String> getDocuments() {
        return allDocuments;
    }

    /**
     * Returns the list of terms in the matrix
     */
    public List<String> getTerms() {
        return new ArrayList<>(allTerms);
}
}