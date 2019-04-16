package news.demo.dao;

import news.demo.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by dell on 2019/3/28.
 */
public interface UserDao {
    @Insert("INSERT INTO user_tb(uid, user_type, avg_time) VALUES(#{uid}, #{userType}, #{avgTime});")
    public boolean addUser(User user);

    @Select("select uid, user_type, avg_time from user_tb;")
    public List<User> findAll();

    @Update("UPDATE user_tb set active_days=#{activeDays} WHERE uid=#{uid};")
    public int updateDaysByUid(@Param(value = "uid") String uid, @Param(value = "activeDays") int activeDays);

    @Update("UPDATE user_tb set post_num=#{postNum} WHERE uid=#{uid};")
    public int updatePostNumByUid(@Param(value = "uid") String uid, @Param(value = "postNum") int postNum);
    // similarity_degree
    @Update("UPDATE user_tb set similarity_degree=#{similarityDegree} WHERE uid=#{uid};")
    public int updateSimilarityDegreeByUid(@Param(value = "uid") String uid, @Param(value = "similarityDegree") double similarityDegree);

    @Update("UPDATE user_tb set user_type=1 WHERE uid=#{uid};")
    public int updateUserTypeByUid(@Param(value = "uid") String uid);
}
