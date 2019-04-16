package news.demo.dao;

import news.demo.entities.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 13:12
 */

public interface CommentDao {

    //@Delete("delete from comment_2_tb")
    public int deleteAll();

    //@Insert("INSERT INTO comment_2_tb(uid, newsId, post_time_str, post_time, post_loc, comment_context, is_new) " +
    //        " VALUES (#{uid}, #{newsId}, #{post_time_str}, #{post_time}, #{post_loc}, #{comment_context}, #{isNew});")
    public boolean addComment(Comment comment);

    @Select("select uid, newsId, post_time_str, post_time, post_loc, comment_context, is_new from comment_2_tb;")
    public List<Comment> findAll();

    @Select("select uid, newsId, post_time_str, post_time, post_loc, comment_context, is_new from comment_2_tb where uid=#{uid};")
    public List<Comment> findAllByUID(String uid);


    @Select("SELECT uid FROM comment_2_tb where uid = #{uid};")
    public List<Comment> countUidByUid(String uid);

    @Select("SELECT DISTINCT(STR_TO_DATE(post_time_str,'%Y-%m-%d')) as post_time_str from comment_2_tb where uid =#{uid}")
    public List<Comment> findDaysByUid(String uid);

    @Select("SELECT DISTINCT uid from comment_2_tb;")
    public List<Comment> getAllDistinctUid();

    @Select(" select uid, newsId, post_time_str, post_time, post_loc, comment_context, is_new from comment_2_tb\n" +
            " WHERE uid in ( SELECT uid from \n" +
            " (SELECT uid, count(uid) as count_uid from comment_2_tb GROUP BY uid) tmp WHERE tmp.count_uid>=3);")
    public List<Comment> getAllUid3(); // 找出评论数大于3 的用户

}
