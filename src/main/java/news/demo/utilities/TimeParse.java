package news.demo.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dell on 2019/3/27.
 */
public class TimeParse {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeParse instance = new TimeParse();

    private TimeParse() {
    }

    public static void main(String[] args) {
        String time = "2019-03-14 07:58:16";
        TimeParse timeParse = getInstance();
        System.out.println(timeParse.dateToSeconds(time));
        System.out.println(timeParse.millisSecondToDate(1553303095620L));
    }

    public static TimeParse getInstance() {
        return instance;
    }

    public long dateToSeconds(String date) {
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);
    }

    /**
     * 毫秒转换为指定格式的日期
     *
     * @param millis
     * @return
     */
    public String millisSecondToDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date date = calendar.getTime();
        String dateString = sdf.format(date);
        return dateString;
    }

}
