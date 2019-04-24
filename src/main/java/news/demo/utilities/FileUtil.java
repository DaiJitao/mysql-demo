package news.demo.utilities;

import java.io.File;

/**
 * Created by dell on 2019/4/23.
 */
public interface FileUtil {
    /**
     * 创建文件
     * @param file
     * @return 文件全名
     */
    String createFile(File file);

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
     * 读取pdf
     * @param file
     * @return
     */
    String loadFromPDF(File file);

    /**
     * 读取word
     * @param file
     * @return
     */
    String loadFromDOC(File file);
}
