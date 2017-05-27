package self.srr.roll;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 抽卡需要的数据查询
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Mapper
@Repository
public interface RollMapper {


    @Select("  SELECT" +
            "   *" +
            "  FROM" +
            "   roll_record" +
            "  WHERE" +
            "   uid = #{uid}" +
            "  LIMIT" +
            "   1")
    RollRecord findOneByUid(@Param("uid") String uid);
}
