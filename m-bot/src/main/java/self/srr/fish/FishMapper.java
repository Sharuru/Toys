package self.srr.fish;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 摸鱼计时需要的数据查询
 * <p>
 * Created by Sharuru on 2017/4/29 0029.
 */
@Mapper
@Repository
public interface FishMapper {

    @Select("SELECT * FROM fish_record WHERE user = #{user} AND check_time >= #{beginTs} AND check_time <= #{endTs} LIMIT 1")
    FishRecord findOneByTime(@Param("user") String user, @Param("beginTs") Long beginTs, @Param("endTs") Long endTs);

    @Update("UPDATE fish_record" +
            " SET " +
            " check_time = #{checkTime}," +
            " input_time = #{inputTime}" +
            " WHERE" +
            " id = #{id}")
    void updateTimeById(@Param("id") Integer id, @Param("checkTime") Long checkTime, @Param("inputTime") Long inputTime);

}
