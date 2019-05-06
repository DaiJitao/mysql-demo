package news.demo.utilities;

import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2019/4/10.
 */
public class CSVUtils {
    private static FileUtils fileUtils = FileUtils.getInstance();

    public static void main(String[] args) {
        String file = "F:/";
        String[] headers = {"编号", "姓名", "年龄"};
        String[][] contents = {{"12365", "张山", "34"}, {"1236", "zhanghai", "56"}};
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<ArrayList<String>>();
        for (String[] c : contents) {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add("12365");
            tmp.add("34");
            tmp.add("张珊");
            arrayLists.add(tmp);
        }
        CSVUtils.saveToCSV(file, "demo.csv", headers, arrayLists);

        for (String[] c : contents) {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add("654");
            tmp.add("66");
            tmp.add("xiaodai");
            arrayLists.add(tmp);
        }
        CSVUtils.saveToCSV(file, "demo.csv", headers, arrayLists);
    }

//    public static void main(String[] args) {
//        String file = "F:/data/parsedSouhuData/";
//        String[] headers = {"编号","姓名","年龄"};
//        String[][] contents = {{"12365","张山","34"}, {"1236", "zhanghai", "56"}};
//        CSVUtils.saveToCSV(file, "souhu.csv", headers, contents);
//
//    }
    public static void saveToCSV(String csvPath, String csvName, String[] headers, String[][] contents) {
        fileUtils.createFile(csvPath, csvName);
        CsvWriter csvWriter = new CsvWriter(csvPath + csvName, ',', Charset.forName("utf8"));
        try {
            csvWriter.writeRecord(headers);
            for (String[] content : contents) {
                csvWriter.writeRecord(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
        System.out.println("save csv success!");
    }

    /**
     * 追加写入
     * @param csvPath
     * @param csvName
     * @param headers
     * @param contents
     */
    public static void saveToCSV(String csvPath, String csvName, String[] headers, List<ArrayList<String>> contents) {
        fileUtils.createFile(csvPath, csvName);
        CsvWriter csvWriter = new CsvWriter(csvPath + csvName, ',', Charset.forName("utf8"));
        try {
            csvWriter.writeRecord(headers);
            for (ArrayList<String> content : contents) {
                String[] result = new String[content.size()];
                csvWriter.writeRecord(content.toArray(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
        System.out.println("save csv success!");
    }
}
