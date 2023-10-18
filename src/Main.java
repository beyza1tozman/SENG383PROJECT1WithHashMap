public class Main {
    public static void main(String[] args) {
        SearchEngine searchEngine=new SearchEngine();
        String filePath= "C:/Users/LENOVO/Desktop/information.txt";
        searchEngine.load(filePath);
        searchEngine.search("computer");


    }
}