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
    /**
     * LSH Constructor
     * @param minHashMatrix
     * @param docNames
     * @param b
     */
    public LSH(int[][] minHashMatrix, String[] docNames, int b) {
        this.minHashMatrix = minHashMatrix;
        this.docNames = docNames;
        this.b = b;
        this.k= minHashMatrix[0].length;
        this.rowsPerBand = k / b;
         for (int g = 0; g < b; g++) {
            hashTables.add(new HashMap<Integer, List<String>>());
         }
        initializeLSH();
    }
    /**
     * Initializes LSH
     */
    private void initializeLSH() {
            for (int i = 0; i < docNames.length; i++) {
                for (int j = 0; j < rowsPerBand; j++) {
                    HashMap<Integer, List<String>> tempTable = hashTables.get(j);
                    int hashValue = Arrays.hashCode(Arrays.copyOfRange(minHashMatrix[i], rowsPerBand * j, rowsPerBand * j + rowsPerBand));

                 if (!tempTable.containsKey(hashValue)) {
                     tempTable.put(hashValue, new ArrayList<String>());
                    }

                tempTable.get(hashValue).add(docNames[i]);
                }
            }
    }
    //finds duplicates of a given document
   public ArrayList<String> retrieveSim(String docName) {
    String outputFilePath = "data/preprocessed_files/output_" + docName;
    int docIndex = Arrays.asList(docNames).indexOf(outputFilePath);
    if (docIndex == -1) {
        throw new IllegalArgumentException("Document name not found: " + docName);
    }
    Set<String> temp = new HashSet<>();
    System.out.println(""+rowsPerBand + "   " + b );
    for (int i = 0; i < rowsPerBand; i++) {
            HashMap<Integer, List<String>> tempMap = hashTables.get(i);
            int hashValue = Arrays.hashCode(Arrays.copyOfRange(minHashMatrix[docIndex], rowsPerBand * i, rowsPerBand * i + rowsPerBand));
            List<String> similarDocs = tempMap.get(hashValue);
            for(String d : similarDocs){
                temp.add(d);
            }
    }

    temp.remove(docName);
    return new ArrayList<>(temp);
}
}

