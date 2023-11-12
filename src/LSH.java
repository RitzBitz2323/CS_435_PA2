import java.util.*;

public class LSH {
    //minHash matrix 
    private int[][] minHashMatrix; 
    // string array of document names that are within the matrix
    private String[] docNames;
    // number of lsh bands
    private int b;
    // number of rows per band
    private int rowsPerBand;
    // number of permutations 
    private int k;
    // lsh result
    private List<HashMap<Integer, List<String>>> hashTables = new ArrayList<HashMap<Integer, List<String>>>();

    public LSH(int[][] minHashMatrix, String[] docNames, int b) {
        this.minHashMatrix = minHashMatrix;
        this.docNames = docNames;
        this.b = b;
        this.k= minHashMatrix[0].length;
        this.rowsPerBand = k / b;

        initializeLSH();
    }

    private void initializeLSH() {
        for (int g = 0; g < b; g++) {
            hashTables.add(new HashMap<Integer, List<String>>());
            for (int i = 0; i < docNames.length; i++) {
                for (int j = 0; j < b; j++) {
                    HashMap<Integer, List<String>> tempTable = hashTables.get(j);
                    int hashValue = Arrays.hashCode(Arrays.copyOfRange(minHashMatrix[i], rowsPerBand * j, rowsPerBand * j + rowsPerBand));

                 if (!tempTable.containsKey(hashValue)) {
                     tempTable.put(hashValue, new ArrayList<String>());
                    }

                tempTable.get(hashValue).add(docNames[i]);
                }
            }
        }
    }
    //finds duplicates of a given document
    public ArrayList<String> retrieveSim (String docName) {
        int docIndex = Arrays.asList(docNames).indexOf(docName);
        Set<String> temp = new HashSet<String>();
        // do lsh in order to get similar documents
        for (int i = 0; i < b; i++) {
        HashMap<Integer, List<String>> tempMap = hashTables.get(i);
        int hashValue =Arrays.hashCode(Arrays.copyOfRange(minHashMatrix[docIndex], rowsPerBand * i, rowsPerBand * i + rowsPerBand));
        List<String> similarDocs = tempMap.get(hashValue);
        for (String doc : similarDocs) {
            temp.add(doc);
        }
        }
        temp.remove(docName);
        ArrayList<String> nearDups = new ArrayList<String>();
        nearDups.addAll(temp);
        return nearDups;
  }
}
