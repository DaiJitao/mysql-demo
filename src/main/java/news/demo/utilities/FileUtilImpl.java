package news.demo.utilities;

import java.io.*;

/**
 * Created by dell on 2019/4/23.
 */
public class FileUtilImpl implements FileUtil{
    private static FileUtilImpl instance = new FileUtilImpl();
    private FileUtilImpl() {
    }
    public static FileUtilImpl getInstance() {
        return instance;
    }
    @Override
    public String createFile(File file) {
        return null;
    }

    @Override
    public String createDirs(File file) {
        return null;
    }

    @Override
    public String loadFromTXT(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        BufferedReader bffReader = null;
        FileReader fileReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileReader = new FileReader(file);
            bffReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bffReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bffReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int countDirs(String path) {
        return 0;
    }

    @Override
    public int countFile(String path) {
        return 0;
    }

    @Override
    public void saveToTxt(String path, String fileName) {

    }

    @Override
    public String loadFromPDF(File file) {
        return null;
    }

    @Override
    public String loadFromDOC(File file) {
        return null;
    }
}
