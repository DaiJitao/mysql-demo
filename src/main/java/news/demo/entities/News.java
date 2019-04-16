package news.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 16:33
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class News {
    String newsId;
    String title;
    String content;
    String postTime;
    String url;
    String channel;
    int activeDays;

}
