public class sd_test {
    public static void main(String[] args) {
        SimilarDocuments similardoctest1 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 100, 0.3);
        SimilarDocuments similardoctest2 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 150, 0.3);
        SimilarDocuments similardoctest3 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 200, 0.3);
        SimilarDocuments similardoctest4 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 250, 0.4);
        SimilarDocuments similardoctest5 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 300, 0.4);
        SimilarDocuments similardoctest6 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 350, 0.4);
        SimilarDocuments similardoctest7 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 400, 0.5);
        SimilarDocuments similardoctest8 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 450, 0.5);
        SimilarDocuments similardoctest9 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 500, 0.5);
        SimilarDocuments similardoctest10 = new SimilarDocuments("/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/LSH_Data", 250, 0.3);

        similardoctest1.similaritySearch("baseball2.txt");
        similardoctest2.similaritySearch("space-983.txt");
        similardoctest3.similaritySearch("space-958.txt");
        similardoctest4.similaritySearch("space-803.txt");
        similardoctest5.similaritySearch("space-763.txt");
        similardoctest6.similaritySearch("space-861.txt");
        similardoctest7.similaritySearch("space-761.txt");
        similardoctest8.similaritySearch("space-709.txt");
        similardoctest9.similaritySearch("hockey401.txt");
        similardoctest10.similaritySearch("baseball252.txt");

    }
}
