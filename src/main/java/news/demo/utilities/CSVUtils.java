package news.demo.utilities;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by dell on 2019/4/10.
 */
public class CSVUtils {
    private static FileUtils fileUtils = FileUtils.getInstance();
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
            for (String[] content:contents) {
                csvWriter.writeRecord(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
        System.out.println("save csv success!");
    }
}
