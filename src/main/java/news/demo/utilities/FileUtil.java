package news.demo.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dell on 2019/4/23.
 */
public interface FileUtil {
    /**
     * 创建文件夹
     * @param file
     * @return
     */
    String createDirs(File file);

    /**
     * 从text文件读取数据
     * @param filePath
     * @param fileName
     * @return
     */
    String loadFromTXT(String filePath, String fileName);

    /**
     * 统计path路径下的文件夹数量
     * @param path
     * @return
     */
    int countDirs(String path);

    /**
     * 统计path路径下的文件数量
     * @param path
     * @return
     */
    int countFile(String path);

    /**
     * 保存文件到txt
     * @param path
     * @param fileName
     */
    void saveToTxt(String path, String fileName);

    /**
     * 获取文件夹下的所有文件（不包括文件夹）
     * @param path
     * @return
     */
    List<File> getAllFiles(String path);

    String getData(File file) throws IOException;

    /**
     * 创建文件
     * @param filePath
     * @param name
     * @return
     * @throws Exception
     */
    boolean createFile(String filePath, String name) throws Exception;

}
