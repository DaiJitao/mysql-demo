package news.demo.parse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import news.demo.entities.SouHuComment;
import news.demo.utilities.CSVUtils;
import news.demo.utilities.FileUtils;
import news.demo.utilities.TimeParse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2019/4/9.
 */

public class ParseSouhuData {
    private static ParseSouhuData parseSouhuData = new ParseSouhuData();
    private static FileUtils fileUtils = FileUtils.getInstance();
    private static TimeParse timeParse = TimeParse.getInstance();
    private final static String path = "F:/data/souhu/";
    private static String[] headers = {"uid", "newsId", "newsURL", "create_time", "millTime", "comment_id", "reply_count", "content", "location", "reply_id", "img_url",
            "nickname", "support_count"
    };

    public static void main(String[] args) {
        // saveToCSV();
    }

    public static void saveToCSV() {
        List<SouHuComment> souHuComments = parseSouhuData.getAllSOUHUComment();
        String[][] data = new String[souHuComments.size()][];
        int count = 0;
        for (SouHuComment comment : souHuComments) {
            String[] tmp = parseSouhuData.commentStringList(comment);
            data[count++] = tmp;
        }

        String csvPath = "F:/data/parsedSouHu/";
        String name = "data_array_test.csv";
        CSVUtils.saveToCSV(csvPath, name, headers, data); // 保存数据
    }

    public String[] commentStringList(SouHuComment comment) {
        return new String[]{comment.getUid(), comment.getNewsId(), comment.getNewsUrl(), comment.getCreateTime(), comment.getCreateTimeMill(), comment.getCommentId(), String.valueOf(comment.getReplyCount()), comment.getComment_context(),
                comment.getPostLoc(), comment.getReplyId(), comment.getUserImageUrl(), comment.getUserName(), String.valueOf(comment.getSupportCount())
        };

    }

    public List<SouHuComment> getAllSOUHUComment() {
        File[] files = fileUtils.getAllFiles(path);
        int count = 0;
        List<SouHuComment> resultList = new ArrayList<SouHuComment>();
        for (File file : files) {
            if (file.isDirectory()) {
                File[] datafiles = fileUtils.getAllFiles(file.getPath());
                String url = parseSouhuData.getNewsURL(datafiles);
                if (datafiles.length >= 2) {
                    for (File datafile : datafiles) {
                        if (!datafile.toString().contains("url")) {
                            JSONObject dataJson = parseSouhuData.data2JSONObj(datafile);
                            List<SouHuComment> tmp = parseSouhuData.parseJSON(dataJson, url, String.valueOf(count));
                            resultList.addAll(tmp);
                        }
                    }
                }
            }
            count++;
        }
        return resultList;
    }

    private List<SouHuComment> parseJSON(JSONObject jsonObject, String newsURL, String newsId) {
        JSONArray comments = jsonObject.getJSONObject("jsonObject").getJSONArray("comments");
        List<SouHuComment> resultList = new ArrayList<SouHuComment>();
        for (Object object1 : comments) {
            JSONObject jsonObject1 = (JSONObject) object1;
            Long create_time = jsonObject1.getLong("create_time");
            String cmtId = jsonObject1.getString("comment_id");
            int reply_count = jsonObject1.getIntValue("reply_count");
            String content = jsonObject1.getString("content");
            String loc = jsonObject1.getString("ip_location");
            String reply_id = jsonObject1.getString("reply_id");
            JSONObject passport = jsonObject1.getJSONObject("passport");
            String imageUrl = passport.getString("img_url");
            String nickname = passport.getString("nickname");
            String uid = jsonObject1.getString("user_id");
            int support_count = jsonObject1.getIntValue("support_count");
            SouHuComment comment = new SouHuComment();

            comment.setCreateTimeMill(create_time.toString());
            comment.setCreateTime(timeParse.millisSecondToDate(create_time));
            comment.setCommentId(cmtId);
            comment.setReplyId(reply_id);
            comment.setUserImageUrl(imageUrl);
            comment.setUid(uid);
            comment.setUserName(nickname);
            comment.setReplyCount(reply_count);
            comment.setSupportCount(support_count);
            comment.setComment_context(content);
            comment.setPostLoc(loc);
            comment.setNewsId(newsId);
            comment.setNewsUrl(newsURL);
            resultList.add(comment);
        }
        return resultList;
    }

    /**
     * 数据转JSON
     *
     * @param file
     * @return
     */
    private JSONObject data2JSONObj(File file) {
        String data = fileUtils.getDataFromFile(file);
        int index = data.indexOf("(");
        String tmp = data.substring(index + 1, data.length() - 2);
        return JSONObject.parseObject(tmp);
    }

    private String getNewsURL(File[] files) {
        File file = parseSouhuData.getURLFile(files);
        String data = fileUtils.getDataFromFile(file);
        return data;
    }

    private File getURLFile(File[] files) {
        File returnFile = null;
        for (File file : files) {
            String url = file.getPath();
            if (url.contains("url")) {
                returnFile = file;
            }
        }
        return returnFile;
    }
}
