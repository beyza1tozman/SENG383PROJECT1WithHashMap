import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SearchEngine {
    HashMap<String, HashSet<String>> map;
    String outputFilePath="C:/Users/LENOVO/Desktop/output.txt";
    public SearchEngine(){
        map=new HashMap<>();
    }

    public void load(String filePath) {//mapimizi doldurduk
        String docName = null;
        boolean isFirstLine = true;
        int lineWithHashTagCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("###")) {
                    lineWithHashTagCount++;
                    continue;
                }
                if (lineWithHashTagCount == 2) {
                    docName = line.toLowerCase();
                    lineWithHashTagCount = 0;
                    continue;
                }
                if (isFirstLine) {
                    docName = line.toLowerCase();
                    isFirstLine = false;
                } else {
                    String[] words = line.split("\\s+");
                    for(String word:words) {
                        word=word.toLowerCase();
                        if (!map.containsKey(word)) {//kelime mapte yoksa eklenicek. ve hashset oluşturulup docname ona eklenip de sete eklenicek. map o keyi içeriyorsa. ve value yani setimiz docname sahip deilse sadece o valueya yani hashsete ulaşıp eklicez
                            HashSet<String> set=new HashSet<>();
                            set.add(docName);
                            map.put(word, set);
                        } else if (map.containsKey(word) && !map.get(word).contains(docName)) {
                            map.get(word).add(docName);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(String docName) {
        docName=docName.toLowerCase();
        for(Map.Entry<String,HashSet<String>>mapElement:map.entrySet()) {
            if(mapElement.getValue().contains(docName)){
                mapElement.getValue().remove(docName);
            }
        }
    }

    public void clearList() {
        try {
            FileWriter writer = new FileWriter(outputFilePath);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.clear();
    }

    public void search(String word) {
        HashSet<String> result=new HashSet<>();
        String[]words=word.split(",");
        for(String query:words){
            if(query.charAt(0)=='!'){//istemediğimiz bir şey ise hashsetten çıkarıcaz
                for(Map.Entry<String,HashSet<String>> mapElement : map.entrySet()){//kelimeyi hashmapte bulucaz
                    if(mapElement.getKey().equals(word)){//kelime hashmapte varsa eğer
                        for(String docName:mapElement.getValue()){//onun docname setini gezip her şeyini result hashsete eklicez
                            result.remove(docName);
                        }
                    }
                }

            }else{//olmasını istediğimiz bir kelime ise
                for(Map.Entry<String,HashSet<String>> mapElement : map.entrySet()){//kelimeyi hashmapte bulucaz
                    if(mapElement.getKey().equals(word)){//kelime hashmapte varsa eğer
                        for(String docName:mapElement.getValue()){//onun docname setini gezip her şeyini result hashsete eklicez
                            result.add(docName);
                        }
                    }


                }
            }

        }
        System.out.println("queue "+word+"\n"+result);
        try(FileWriter writer=new FileWriter(outputFilePath)){
            writer.write("queue "+word+"\n"+result);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
