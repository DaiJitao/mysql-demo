package news.demo.server;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import news.demo.App;
import news.demo.utilities.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 2019/3/27.
 */
public class TextHandler {
    private final static List<String> tmpList = Arrays.asList(new String[]{" !   ", " ;       "});


    public static void main(String[] args) {
        testNER();
    }


    public static void test(String[] args) {
        App app = new App();
        List<String> context = app.getCommentByUid("6567343275");
        Double v = getMaxSpammer(context);
        if (v > 0.85) {
            System.out.println(true);
        }
        System.out.println(v);
    }

    /**
     * @param context
     * @return
     */
    public static Double getMaxSpammer(List<String> context) {
        HanLP.Config.ShowTermNature = false; // 关闭词性显示
        CustomDictionary.add("特鲁多");
        CustomDictionary.add("加国狗");
        CustomDictionary.add("加狗");
        CustomDictionary.add("5G");

        List<List<String>> lists = new ArrayList<List<String>>();
        for (String text : context) {
            List<Term> termList = HanLP.segment(text); // 分词
            List<String> stopWordsText = removeStopWords(termList); // 去除停用词
            List<String> tmp = removeDuplicate(stopWordsText); // 去除重复的词
            lists.add(tmp);
        }

        List<Double> spammerV = getWebSpammer(lists); // 获取相似度列表
        return Collections.max(spammerV); // 最大值
    }

    /**
     * 获取最大评论相似度
     *
     * @param comments
     * @return
     */
    private static List<Double> getWebSpammer(final List<List<String>> comments) {
        int size = comments.size();
        List<Double> sv = new ArrayList<Double>(); // 存放相似度
        for (int i = 0; i < size; i++) {
            List<String> list1 = comments.get(i);
            int t1 = list1.size();
            for (int j = i + 1; j < size; j++) {
                List<String> tmp = FileUtils.deepCopyList(list1);
                List<String> list2 = comments.get(j);
                int t2 = list2.size();
                tmp.retainAll(list2); // 交集
                double inteLen = (double) tmp.size();
                if (t1 == 0 || t2 == 0 || list1.size() == 0) {
                    sv.add(0D);
                } else {
                    sv.add(inteLen / Math.min(t1, t2));
                }
            }
        }
        return sv;
    }

    public void demoTest() throws IOException {
        /*加载停止词*/
        File file1 = new File("F:/停止词大全/stop_words.txt");//停用词
        List<String> stopword = FileUtils.readLines(file1, "utf8");
        //System.out.println(stopword);

        /*加载自定义词典*/
        File file2 = new File("F:/停止词大全/用户自定义词典.txt");//停用词
        List<String> dic = FileUtils.readLines(file2, "utf8");
        //System.out.println(dic);

        // 动态增加自定义词典
        for (int i = 0; i < dic.size(); i++) {
            CustomDictionary.add(dic.get(i));
        }


        /*中文分词*/
        // 关闭词性显示
        HanLP.Config.ShowTermNature = false;
        //调用HanLP.segment()对句子进行分词处理
        List<Term> termList = HanLP.segment("五星巴西中国科学院啊啊啊啊啊啊啊啊啊啊啊啊啊啊《啊计算技术研究所的宗成庆教授正在教授自然语言处理课程赵乐际?俺们美锦男篮");

        ArrayList<String> TermList = new ArrayList();
        for (int i = 0; i < termList.size(); i++) {
            TermList.add(termList.get(i).toString());
        }
        //去除停止词
        TermList.removeAll(stopword);

        System.out.println(TermList);

        System.out.println(removeDuplicate(TermList));
        /*计算去除停止词之后的分词的长度*/
        Integer tag_length = removeDuplicate(TermList).size();
        System.out.println(tag_length);


    }

    public static void main1(String[] args) {

        System.out.println(HanLP.segment("您好，欢迎使用Eclipse！代继涛是我同学"));
        List<Term> termList = HanLP.segment("商品和服务");
        System.out.println(termList);
        for (Term t : termList
                ) {
            System.out.println(t);
        }
        System.out.println("==================");
        termList = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        List<Term> termList2 = HanLP.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
        System.out.println(termList);
        System.out.println(termList2);
        System.out.println("=====================");
        termList = IndexTokenizer.segment("主副食品");
        for (Term term : termList) {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
        System.out.println("===========================");
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        System.out.println(NLPTokenizer.segment(text));
        System.out.println(HanLP.segment(text));
        System.out.println(SpeedTokenizer.segment(text));
        long start = System.currentTimeMillis();
        int pressure = 1000000;
        for (int i = 0; i < pressure; ++i) {
            SpeedTokenizer.segment(text);
        }
        double costTime = (System.currentTimeMillis() - start) / (double) 1000;
        System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);
        System.out.println("==========================================");
        nShortSegment();

    }

    public static void nShortSegment() {
        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        Segment shortestSegment = new ViterbiSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        String[] testCase = new String[]{
                "刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
        };
        for (String sentence : testCase) {
            System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
        }
    }

    private static void testNER() {
        HanLP.Config.ShowTermNature = false;
        CustomDictionary.add("上Q");
        String[] testCase = new String[]{
                "我对手要求。能打，能接。能上Q，能上微信。其它就是屁",
                "麒麟970芯片前的手机相对差些，麒麟970芯片后的手机，性能已经突破了，接近苹果 的了，和高通距离也窄了，我继续选择华为。",
                "封杀加拿大⛽⛽⛽",
                "特鲁多，必定吊死在美国这棵树叉上......",
                "早做准备```物色一两个美国人```份量越重越好````是抓是放`相机行事"
        };

        TextHandler handler = new TextHandler();
        for (String text : testCase) {
            List<Term> termList = HanLP.segment(text);
            System.out.println(termList);
            List<String> list = TextHandler.removeStopWords(termList);
            System.out.println(list);
            System.out.println();
        }

    }

    /*列表去重*/
    private static List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    /**
     * 去除停用词，指定词典
     *
     * @param termList
     * @param stopList
     * @return
     */
    public static List<String> removeStopWords(List<Term> termList, List<String> stopList) {
        List<String> tmpList = new ArrayList<String>();
        for (Term term : termList) {
            if (stopList.contains(term.toString())) {
                continue;
            }
            tmpList.add(term.toString());
        }
        return tmpList;
    }

    /**
     * 去除停用词，默认词典
     *
     * @param termList
     * @return
     */
    public static List<String> removeStopWords(List<Term> termList) {
        String stopWords = "F:" + File.separator + "data" + File.separator + "stopwords" + File.separator + "stopwords.txt";
        File stopWordsFile = new File(stopWords);
        List<String> stopList = FileUtils.readLines(stopWordsFile, "utf8");
        List<String> tmpList = new ArrayList<String>();
        for (Term term : termList) {
            if (stopList.contains(term.toString())) {
                continue;
            }
            tmpList.add(term.toString());
        }
        return tmpList;
    }
}
