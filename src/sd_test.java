import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class sd_test {
    public static void main(String[] args) {
        DocumentPreprocess.clearOutputFolder();
        SimilarDocuments similardoctest1 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/space/", 400, 0.000000000000001);
        similardoctest1.similaritySearch("space-6.txt");

        SimilarDocuments similardoctest2= new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/space/", 1200, 0.5);
        similardoctest2.similaritySearch("space-6.txt");

        

        // SimilarDocuments similardoctest2 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 150, 0.3);
        // SimilarDocuments similardoctest3 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 200, 0.3);
        // SimilarDocuments similardoctest4 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 250, 0.4);
        // SimilarDocuments similardoctest5 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 300, 0.4);
        // SimilarDocuments similardoctest6 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 350, 0.4);
        // SimilarDocuments similardoctest7 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 400, 0.5);
        // SimilarDocuments similardoctest8 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 450, 0.5);
        // SimilarDocuments similardoctest9 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 500, 0.5);
        // SimilarDocuments similardoctest10 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 250, 0.3);

        
        similardoctest1.similaritySearch("space-983.txt");
        similardoctest1.similaritySearch("space-958.txt");
        similardoctest1.similaritySearch("space-803.txt");
        similardoctest1.similaritySearch("space-763.txt");
        similardoctest2.similaritySearch("space-861.txt");
        similardoctest2.similaritySearch("space-761.txt");
        similardoctest2.similaritySearch("space-709.txt");
        similardoctest2.similaritySearch("space-300.txt");
        

    }
}
