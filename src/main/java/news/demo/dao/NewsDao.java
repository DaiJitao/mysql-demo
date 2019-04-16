package news.demo.dao;

import news.demo.entities.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:hejr
 * @Description:
 * @Date: 2019/3/25 16:35
 */

public interface NewsDao {

//    @Delete("delete from news_tb")
//    private int deleteAll();



    @Insert("INSERT INTO news_tb(newsId, news_title, news_content, post_time_str, post_time, url, channel)" +
            "VALUES (#{newsId}, #{title}, #{content}, #{postTime}, #{postTime}, #{url}, #{channel});")
    public boolean addNews(News news);

//    @Select("select uid, newsId, post_time_str, post_time, post_loc, comment_context, is_new from comment_2_tb;")
//    public List<News> findAll();

    @Update("UPDATE t_user SET gmt_modified = now(), user_name = #{userName} WHERE id = #{id}")
    public int udpateById(@Param("userName") String userName, @Param("id") Long id) ;


}
