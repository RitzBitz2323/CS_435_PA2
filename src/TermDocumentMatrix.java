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
    private List<String> totalDocuments = new ArrayList<String>();

    /**
     * ordered set of all terms from documents
     */
    private LinkedHashSet<String> totalTerms = new LinkedHashSet<>();

    /**
     * Default constructor
     *
     * @param documentName name of document
     * @param terms        list of terms in document
     */
    public TermDocumentMatrix(String documentName, List<String> terms) {
        loadTerms(documentName, terms);
    }

    /**
     * Loads list of terms into matrix for a provided document
     *
     * @param documentName name of document
     * @param terms        list of terms in document
     */
    public void loadTerms(String documentName, List<String> terms) {
        HashMap<String, Integer> termMap = new HashMap<String, Integer>();

        for (String term : terms) {
            if (termMap.containsKey(term)) {
                termMap.put(term, termMap.get(term) + 1);
            } else {
                termMap.put(term, 1);
            }
            this.totalTerms.add(term);
        }

        matrix.put(documentName, termMap);
        totalDocuments.add(documentName);
    }

    /**
     * return the term document matrix
     *
     * @return int[][] termDocumentMatrix
     */
    public int[][] getTermDocumentMatrix() {

        int[][] termDocumentMatrix =
                new int[totalDocuments.size()][totalTerms.size()];

        int documentIndex = 0;
        for (String doc : totalDocuments) {
            HashMap<String, Integer> terms = matrix.get(doc);
            int termIndex = 0;

            for (String term : totalTerms) {
                if (terms.containsKey(term)) {
                    termDocumentMatrix[documentIndex][termIndex] = terms.get(term);
                } else {
                    termDocumentMatrix[documentIndex][termIndex] = 0;
                }
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
        return totalDocuments.size();
    }

    /**
     * Returns the number of terms in the matrix
     */
    public int numTerms() {
        return totalTerms.size();
    }

    /**
     * Returns the list of documents in the matrix
     */
    public List<String> getDocuments() {
        return totalDocuments;
    }

    /**
     * Returns the list of terms in the matrix
     */
    public List<String> getTerms() {
        return new ArrayList<>(totalTerms);
}
}