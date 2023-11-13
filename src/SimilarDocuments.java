
import java.util.*;


public class SimilarDocuments {
    private LSH lsh;
    private MinHash minhash; 
    
    public SimilarDocuments(String nameOfFolder, int permutations, double simThreshold ){
        this.minhash = new MinHash(nameOfFolder, permutations);
        int rowsPerBand = (int) Math.ceil(-permutations / (Math.log(1 - Math.pow(simThreshold, 1.0 / permutations))));
        this.lsh= new LSH(minhash.minHashMatrix(), minhash.allDocs(), rowsPerBand);
    }

    public void similaritySearch(String docName){
        ArrayList<String> resultList = lsh.retrieveSim(docName);
        System.err.println("Computing similarity search for the file " + docName);
        for(int i = 0; i < resultList.size(); i++){
            System.out.print(resultList.get(i)+ "  ");
        }
    }


}