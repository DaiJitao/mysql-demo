package news.demo.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/4/23.
 */
public class FileUtilImpl implements FileUtil {
    private static FileUtilImpl instance = new FileUtilImpl();

    private FileUtilImpl() {
    }

    public static FileUtilImpl getInstance() {
        return instance;
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

    /**
     * 统计文件的数量
     * @param path
     * @return
     */
    @Override
    public int countFile(String path) {
        return 0;
    }

    @Override
    public void saveToTxt(String path, String fileName) {

    }

    @Override
    public List<File> getAllFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        List<File> reFiles = new ArrayList<File>();
        for (File file1 : files) {
            if (!file1.isDirectory()){
                reFiles.add(file1);
            }
        }
        return reFiles;
    }

    /**
     * 创建文件
     *
     * @param
     * @return
     */
    public boolean createFile(String filePath, String name) throws Exception {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File fileName = new File(filePath + name);
        try {
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 创建文件夹
     * @param path
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return false;
        }
    }

    /**
     * 读取数据
     *
     * @param
     * @return
     * @throws IOException
     */
    public String getData(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bffReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bffReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bffReader.close();
        return stringBuilder.toString();
    }


    /**
     * 写入TXT，追加写入
     *
     * @param filePath
     * @param content
     */
    public static void writeData(String filePath, String content) {
        try {
            //构造函数中的第二个参数true表示以追加形式写文件
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            System.out.println("文件写入失败！" + e);
        }
    }


}
