package news.demo.server;

import java.util.*;

/**
 * 统计用户的平均发布时间间隔
 * Created by dell on 2019/3/28.
 */
public class UserIntervalPostTime {
    private static Long SECONDS = 24 * 60 * 60L;

    public static void main(String[] args) {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        arrayList.add(12L);
        arrayList.add(89L);
        arrayList.add(11L);
        System.out.println(arrayList);
        Collections.sort(arrayList);
        System.out.println(arrayList); // 从左到右升序
        System.out.println(arrayList.get(0));

    }

    public long avgIntervalTime(ArrayList<Long> tmp) {
        int size = tmp.size();
        // 如果只发布一篇
        if (size == 1)
            return -1;

        // 发布多篇
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {

            }

        }
        return 0;
    }
    private long avgTime(ArrayList<Long> arrayList){
        Collections.sort(arrayList);
        for (Long tmp:arrayList){
            Long sum = tmp + SECONDS;

        }
        return 0L;
    }
}
