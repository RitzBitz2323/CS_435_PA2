
import java.util.*;


public class SimilarDocuments {
    private LSH lsh;
    private MinHash minhash; 
    
    public SimilarDocuments(String nameOfFolder, int permutations, double simThreshold ){
        this.minhash = new MinHash(nameOfFolder, permutations);
       // Helpers.prettyPrintMatrix("matrix cool", this.minhash.allDocs().length, permutations, minhash.minHashMatrix());
        int rowsPerBand = (int) Math.ceil(-permutations / (Math.log(1 - Math.pow(simThreshold, 1.0 / permutations))));
        this.lsh= new LSH(minhash.minHashMatrix(), minhash.allDocs(), rowsPerBand);
    }

    public void similaritySearch(String docName){
        ArrayList<String> resultList = lsh.retrieveSim(docName);
        System.out.println("Computing similarity search for the file " + docName);
        System.out.println("---");
        for(int i = 0; i < resultList.size(); i++){
            System.out.print(resultList.get(i)+ "  ");
        }
    }
    


}