import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LSH {
   // private int[][] minHashMatrix; Need to replace with our MinHash matrix
    private String[] docNames;
    private int bands;
    private int rowsPerBand;
    private int k;
    private int b;
    private List<Map<Integer, List<String>>> hashTables;

    public LSH(int[][] minHashMatrix, String[] docNames, int bands) {
      //  this.minHashMatrix = minHashMatrix;
        this.docNames = docNames;
        this.bands = bands;
        this.k = minHashMatrix.length;
        this.b = minHashMatrix[0].length;
        this.rowsPerBand = k / bands;
        this.hashTables = new ArrayList<>();

        initializeLSH();
    }

    private void initializeLSH() {
        for (int i = 0; i < bands; i++) {
            Map<Integer, List<String>> hashTable = new HashMap<>();
            for (int j = 0; j < b; j++) {
                int bandHash = computeBandHash(i, j);
                if (!hashTable.containsKey(bandHash)) {
                    hashTable.put(bandHash, new ArrayList<>());
                }
                hashTable.get(bandHash).add(docNames[j]);
            }
            hashTables.add(hashTable);
        }
    }
    public List<String> retrieveSim(String docName) {
        List<String> similarDocs = new ArrayList<>();

        for (int i = 0; i < bands; i++) {
            int bandHash = computeBandHash(i, findDocIndex(docName));
            if (hashTables.get(i).containsKey(bandHash)) {
                similarDocs.addAll(hashTables.get(i).get(bandHash));
            }
        }

        return similarDocs;
    }

    private int findDocIndex(String docName) {
        for (int i = 0; i < docNames.length; i++) {
            if (docNames[i].equals(docName)) {
                return i;
            }
        }
        return -1; // Doc not found
    }
}