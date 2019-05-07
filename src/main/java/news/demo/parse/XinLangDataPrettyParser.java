package news.demo.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import news.demo.utilities.CSVUtils;
import news.demo.utilities.FileUtil;
import news.demo.utilities.FileUtilImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XinLangDataPrettyParser {
    private static FileUtil fileUtil = FileUtilImpl.getInstance();

    /**
     * 获取最后一个文件名
     * @param path
     * @return
     */
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

    public static void main(String[] args) throws Exception {
        XinLangDataPrettyParser.parseData("F:\\scrapy\\xinLang_fanChengCheng\\page0.txt");
        XinLangDataPrettyParser.parseData("F:\\scrapy\\xinLang_fanChengCheng\\page1.txt");
    }

    public static void parseData(String dataPath) throws Exception {
        List<File> files = fileUtil.getAllFiles("F:\\scrapy\\xinLang_fanChengCheng\\");
        for (File file : files) {
            if (!file.toString().contains("url")) {
                File fileData = new File(dataPath);
                String data = fileUtil.getData(fileData);
                // 获取前41个字符
                String startString = data.substring(0, 41);
                int startIndex = startString.indexOf("(");
                int len = data.length();
                String js = data.substring(startIndex + 1, len - 1);
                JSONObject resultJSONObj = JSONObject.parseObject(js).getJSONObject("result");
                JSONArray array = resultJSONObj.getJSONArray("cmntlist");
                System.out.println("评论列表：" + array.size());
                ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
                String[] headers = {"uid", "rank", "area", "content", "nick", "parent_nick", "parent_uid", "time", "newsid", "hot"};
                for (int i = 0; i < array.size(); i++) {
                    JSONObject tmp = array.getJSONObject(i);
                    String rank = tmp.getString("rank");
                    String loc = tmp.getString("area");
                    String content = tmp.getString("content"); // 评论内容
                    String nick = tmp.getString("nick");
                    String parent_nick = tmp.getString("parent_nick");
                    String parent_uid = tmp.getString("parent_uid");
                    String time = tmp.getString("time");
                    String newsId = tmp.getString("newsid");
                    String uid = tmp.getString("uid");
                    String hot = tmp.getString("hot");
                    ArrayList<String> intRes = new ArrayList<String>();
                    intRes.add(uid);
                    intRes.add(rank);
                    intRes.add(loc);
                    intRes.add(content);
                    intRes.add(nick);
                    intRes.add(parent_nick);
                    intRes.add(parent_uid);
                    intRes.add(time);
                    intRes.add(newsId);
                    intRes.add(hot);
                    result.add(intRes);
                }
                CSVUtils.saveToCSV("F:\\scrapy\\xinLang_fanChengCheng_parsed\\", "page0.csv", headers, result);

            }
        }

    }

    /*
    * 统计文件夹
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
}
