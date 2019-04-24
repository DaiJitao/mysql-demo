package news.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import news.demo.dao.CommentDao;
import news.demo.dao.NewsDao;
import news.demo.dao.UserDao;
import news.demo.entities.Comment;
import news.demo.entities.News;
import news.demo.entities.User;
import news.demo.parse.ParseXinLangData;
import news.demo.server.TextHandler;
import news.demo.utilities.TimeParse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 13:23
 */

public class App {
    private static CommentDao commentDao = null;
    private static NewsDao newsDao = null;
    private static UserDao userDao = null;
    private static Reader reader = null;
    private static SqlSession session = null;
    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd"); //

    static {
        String path = "mybatis-config.xml";
        try {
            reader = Resources.getResourceAsReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sessionFactory.openSession();
        commentDao = session.getMapper(CommentDao.class);
        newsDao = session.getMapper(NewsDao.class);
        userDao = session.getMapper(UserDao.class);
    }

    public static void main(String[] args) {
        //1 遍历文件夹
        // 2 该文件夹下文件大于2:  1 抽取URL, 2 解析json
        // session.commit();
        Map<String, Double> spammerV = xiangSiDu();
        Iterator iter = spammerV.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter.next();
            String uid = entry.getKey();
            Double value = entry.getValue();
            userDao.updateUserTypeByUid(uid);
        }
        //session.commit();
    }

    /**
     * 计算文本相似度
     * @return
     */
    public static Map<String, Double> xiangSiDu() {
        List<Comment> comments = commentDao.getAllUid3(); //
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        for (Comment comment : comments) {
            String text = comment.getComment_context();
            String uid = comment.getUid();
            if (hashMap.containsKey(uid)) {
                hashMap.get(uid).add(text);
            } else {
                List<String> list = new ArrayList<String>();
                list.add(text);
                hashMap.put(uid, list);
            }
        }

        Iterator iter = hashMap.entrySet().iterator();
        Map<String, Double> spammerV = new HashMap<String, Double>();
        while (iter.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iter.next();
            String uid = entry.getKey();
            List<String> context = entry.getValue();
            Double v = TextHandler.getMaxSpammer(context);
            if (v >= 0.80) {
                spammerV.put(uid, v);
            }
        }
        System.out.println(spammerV.size());
        //FileUtils.printMap(spammerV);
        return spammerV;
    }

    /**
     * 获取某一用户的评论列表
     * @param uid
     * @return
     */
    public List<String> getCommentByUid(String uid) {
        List<Comment> comments = commentDao.findAllByUID(uid);
        List<String> list = new ArrayList<String>();
        for (Comment comment : comments) {
            list.add(comment.getComment_context());
        }
        return list;
    }

    /**
     * 统计发帖比数量
     */
    public static void insertPostNum() {
        List<Comment> commentList = commentDao.getAllDistinctUid();  // 获取所有用户
        for (Comment comment : commentList) {
            String uid = comment.getUid();
            List<Comment> comments = commentDao.countUidByUid(uid);
            int count = comments.size();
            System.out.println(userDao.updatePostNumByUid(uid, count));
        }
        // session.commit();
    }

    /**
     * 统计用户活跃天数
     */
    public static void insertActiveDays() throws ParseException {
        List<Comment> commentList = commentDao.findAll();  // 获取所有用户
        for (Comment comment : commentList) {
            String uid = comment.getUid();
            List<Comment> days = commentDao.findDaysByUid(uid);
            int count = days.size();
            System.out.println(userDao.updateDaysByUid(uid, count));
        }
        //session.commit();
    }

    public static void isNew() throws IOException {
        String dataPath = "F:/scrapy/xinlang/data";
        List<List<String>> result = new ArrayList<List<String>>();
        for (int i = 1; i < 147; i++) {
            List<String> re = App.insertCommentData(dataPath + i);
            result.add(re);
        }
        for (List<String> list : result) {
            for (String tmp : list) {
                if (!tmp.equals("0")) {
                    System.out.println(tmp);
                }
            }
        }
    }

    public static HashMap<String, ArrayList<Long>> getAllUserTime() throws IOException {
        TimeParse timeParser = TimeParse.getInstance();
        //1 找出所有用户
        List<Comment> comments = commentDao.findAll();
        HashMap<String, ArrayList<Long>> resultHashMap = new HashMap<String, ArrayList<Long>>();
        String uid = null;
        for (Comment comment : comments) {
            uid = comment.getUid();
            List<Comment> commentsTmp = commentDao.findAllByUID(uid); // 2 找出每一用户的所有时间
            ArrayList<Long> times = new ArrayList<Long>();
            for (Comment tmp : commentsTmp) {
                String date = tmp.getPost_time_str();
                long timeSeconds = timeParser.dateToSeconds(date) / 1000;
                times.add(timeSeconds);
            }
            User user = new User();
            user.setUid(uid).setUserType(0).setAvgTime(times.toString());
            userDao.addUser(user);
            System.out.println(user);
            resultHashMap.put(uid, times);
        }
        // session.commit();
        return resultHashMap;
    }

    public static void main1111(String[] args) throws IOException {
        for (int i = 142; i < 147; i++) { // F:/scrapy/xinlang/data141 处理完毕！该处理142，共处理：2 session提交完毕！
            String dataPath = "F:/scrapy/xinlang/data";
            dataPath = dataPath + i;
            insertCommentData(dataPath);
        }
        session.commit();
        System.out.println("session提交完毕！");
    }

    public static List<String> insertCommentData(String dir) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File[] files = ParseXinLangData.CountFiles(dir);
        int count = 0;
        List<String> list = new ArrayList<String>();
        try {
            for (File file : files) {
                count++;
                String data = ParseXinLangData.getData(file);
                String js = null;
                int strtIndex = data.indexOf("(");
                if (strtIndex != -1) {
                    int len = data.length();
                    js = data.substring(strtIndex + 1, len - 1);
                } else if (strtIndex == -1) {
                    js = data;
                }
                JSONObject resultJSONObj = JSONObject.parseObject(js).getJSONObject("result");
                JSONArray array = resultJSONObj.getJSONArray("cmntlist");
                for (int j = 0; j < array.size(); j++) {
                    JSONObject currentJSON = array.getJSONObject(j);
                    String rank = currentJSON.getString("rank");
                    String loc = currentJSON.getString("area");
                    String content = currentJSON.getString("content");
                    String nick = currentJSON.getString("nick");
                    String parent_uid = currentJSON.getString("parent_uid");
                    String time = currentJSON.getString("time");
                    String newsId = currentJSON.getString("newsid");
                    String uid = currentJSON.getString("uid");

                    Comment comment = new Comment();
                    comment.setUid(uid);
                    comment.setNewsId(newsId);
                    comment.setPost_time_str(time);
                    comment.setPost_time(sdf.parse(time));
                    comment.setPost_loc(loc);
                    comment.setComment_context(content);
                    if (parent_uid.equals("0")) {
                        comment.setIsNew(0); // 0代表原创
                    } else {
                        comment.setIsNew(1); //1 代表回复
                    }
                    list.add(parent_uid);
                    // System.out.println("插入状态  " + commentDao.addComment(comment));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    public static void insertNewsData() throws IOException {
        String path = "F:/scrapy/xinlang/data";

        for (int i = 2; i < 147; i++) {
            String filePath = ParseXinLangData.getLastFile(path + i);
            System.out.println("访问的文件 " + filePath);
            if (filePath.length() != 0) {
                File file = new File(filePath);
                String data = ParseXinLangData.getData(file);
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
                boolean status = newsDao.addNews(news);
                System.out.println("插入状态：" + status);

            }
        }
        System.out.println("end");
    }

    private void listAll() {
        List<Comment> list = commentDao.findAll();
        Comment comment = new Comment();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }
}
