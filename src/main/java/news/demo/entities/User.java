package news.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by dell on 2019/3/28.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    String uid;
    int userType;
    String avgTime;
    int postNum;
}
