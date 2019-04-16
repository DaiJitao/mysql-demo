package news.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 11:53
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Comment implements Serializable {
    String uid;
    String newsId;
    String comment_context;
    String post_time_str;
    Date post_time;
    String post_loc;
    int isNew;

}