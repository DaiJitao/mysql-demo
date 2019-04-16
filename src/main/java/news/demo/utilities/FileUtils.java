package news.demo.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2019/4/8.
 */
public class FileUtils {
    private static FileUtils instance = new FileUtils();

    private FileUtils() {
    }

    public static FileUtils getInstance() {
        return instance;
    }

    /**
     * 读取停用词
     *
     * @param file
     * @param code
     * @return
     */
    public static List<String> readLines(File file, String code) {
        FileInputStream inputStream = null;
        List<String> stringList = new ArrayList<String>();
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream, code);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String tmp = null;
            while ((tmp = bufferedReader.readLine()) != null) {
                stringList.add(tmp.trim().replace(" ", ""));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
        return stringList;
    }

    /**
     * 深拷贝
     *
     * @param src
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * 打印map
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    public static <K, V> void printMap(Map<K, V> map) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry<K, V>) iter.next();
            System.out.println("key: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }
    }

    /**
     * 读取数据
     *
     * @param
     * @return
     * @throws IOException
     */
    public String getDataFromFile(File file) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bffReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bffReader.readLine()) != null) {
                stringBuilder.append(line);
            }
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

    /**
     * 获取所有文件(文件夹及普通文件）
     *
     * @param path
     * @return
     */
    public File[] getAllFiles(String path) {
        int countFile = 0;
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                countFile++;
            }
            return files;
        }
        return new File[0];
    }

    public void printFiles(File[] files) {
        System.out.println("公有文件：" + files.length);
        for (File file : files) {
            System.out.println(file);
        }
    }

    /**
     * 创建文件
     *
     * @param
     * @return
     */
    public boolean createFile(String filePath, String fileName) {
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File file = new File(filePath + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
