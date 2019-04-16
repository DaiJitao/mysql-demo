package news.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 11:53
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SouHuComment implements Serializable {
    String uid;
    String commentId;
    String newsId;
    String comment_context;
    String createTime; // 日期
    String createTimeMill; // 毫秒
    String postLoc;
    String newsUrl;
    String replyId;
    int replyCount;
    String userImageUrl;
    String userName;
    int supportCount;



}