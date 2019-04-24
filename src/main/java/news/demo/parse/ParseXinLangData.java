package news.demo.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import news.demo.entities.Comment;
import news.demo.entities.News;

import java.io.*;
import java.util.ArrayList;

public class ParseXinLangData {
    public static void parseNewsInsert(String[] args) throws IOException {
        String path = "F:/scrapy/xinlang/data";

        for (int i = 1; i < 147; i++) {
            String filePath = getLastFile(path + i);
            System.out.println("访问的文件 " + filePath);
            if (filePath.length() != 0) {
                File file = new File(filePath);
                String data = getData(file);
                String js = null;

                int strtIndex = data.indexOf("(");
                if (strtIndex != -1) { //如果含有“（”
                    int len = data.length();
                    js = data.substring(strtIndex + 1, len - 1);
                } else if (strtIndex == -1) {
                    js = data;
                }
                JSONObject resultJSONObj = JSONObject.parseObject(js).getJSONObject("result");
                //解析 新闻数据
                JSONObject newsJSON = resultJSONObj.getJSONObject("news");
                String title = newsJSON.getString("title");
                String url = newsJSON.getString("url");
                String time = newsJSON.getString("time");
                String newsid = newsJSON.getString("newsid");
                String channel = newsJSON.getString("channel");

                News news = new News();
                news.setNewsId(newsid).setTitle(title).setContent(title).setPostTime(time).setUrl(url).setChannel(channel);
                System.out.println(news);
            }
        }
        System.out.println("end");
    }

    public static String getLastFile(String path) {
        ArrayList<File> files = listCountFiles(path);
        int len = files.size();
        if (len != 0) {
            int lastIndex = len - 1;
            path = path + "/page" + lastIndex + ".txt";
            return path;
        }
        return "";
    }

    public static void parseData(String dataPath) throws Exception {
        File fileData = new File(dataPath);
        String data = ParseXinLangData.getData(fileData);
        int strtIndex = data.indexOf("(");
        int len = data.length();
        System.out.println("总长度： " + len);
        String js = data.substring(strtIndex + 1, len - 1);
        JSONObject resultJSONObj = JSONObject.parseObject(js).getJSONObject("result");
        JSONArray array = resultJSONObj.getJSONArray("cmntlist");
        System.out.println("评论列表：" + array.size());

        String outFilePath = "F:\\scrapy\\xinlangOutData\\data1\\";
        String fileName = "news1_data1.txt";
        String file = outFilePath + fileName;
        createFile(outFilePath, fileName);
        for (int i = 0; i < array.size(); i++) {
            JSONObject tmp = array.getJSONObject(i);
            String rank = tmp.getString("rank");
            String loc = tmp.getString("area");
            String content = tmp.getString("content");
            String nick = tmp.getString("nick");
            String parent_uid = tmp.getString("parent_uid");
            String time = tmp.getString("time");
            String newsId = tmp.getString("newsid");
            String uid = tmp.getString("uid");

            Comment comments = new Comment();
            if (parent_uid.equals("0")) {
                comments.setIsNew(0); // 0 代表原创
            } else {
                comments.setIsNew(1); //1 代表回复
            }
        }
    }

    /*
    * 统计文件夹的数量
    */
    public static ArrayList<File> listCountFiles(String path) {
        ArrayList<File> arrayList = new ArrayList<File>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                arrayList.add(fileIndex);
            }
        }
        return arrayList;
    }

    /*
     * 统计文件夹的数量
     */
    public static File[] CountFiles(String path) {
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

    public static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param
     * @return
     */
    public static boolean createFile(String filePath, String name) throws Exception {
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

    /**
     * 读取数据
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String getData(File file) throws IOException {
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
}
