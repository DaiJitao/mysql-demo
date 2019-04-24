package news.demo.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 统计用户的平均发布时间间隔
 * Created by dell on 2019/3/28.
 */
public class UserIntervalPostTime {
    private static Long intervalSeconds = 24 * 60 * 60L;

    public static void main(String[] args) {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        arrayList.add(1548162214515L);
        arrayList.add(1548899746558L);
        arrayList.add(1550739875783L);
        arrayList.add(1551484391673L);
        System.out.println(arrayList);
        Collections.sort(arrayList);
        System.out.println(arrayList); // 从左到右升序
        UserIntervalPostTime postTime = new UserIntervalPostTime();
        System.out.println(postTime.avgIntervalTime(arrayList, TimeUnit.MILLISECONDS));

    }

    /**
     * @param times
     * @param timeUnit 传入的单位
     * @return 秒
     */
    public double avgIntervalTime(ArrayList<Long> times, TimeUnit timeUnit) {
        long threshold = timeUnit.equals(TimeUnit.MILLISECONDS) ? intervalSeconds * 1000 : intervalSeconds;
        int size = times.size();
        // 如果只发布一篇
        if (size == 1)
            return -1;
        // 发布多篇
        Collections.sort(times);
        ArrayList<Long> allTime = new ArrayList<Long>();

        for (int i = 0; i < size - 1; i++) {
            int j = i + 1;
            Long val = times.get(j) - times.get(i);
            if (val <= threshold) {
                allTime.add(val);
            }
        }
        int allTimeSize = allTime.size();
        if (allTimeSize > 0) {
            double result = sum(allTime) / allTimeSize;
            result = timeUnit.equals(TimeUnit.MILLISECONDS) ? (result / 1000) : result; // 转换成秒
            return result;
        } else
            return -24; // 发布1 篇以上，超过24小时
    }

    private double sum(List<Long> values) {
        long sum = 0;
        for (long v : values) {
            sum += v;
        }
        return (double) sum;
    }

}
