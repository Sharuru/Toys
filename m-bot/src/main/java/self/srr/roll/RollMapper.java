package self.srr.roll;

import org.apache.ibatis.annotations.*;
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

    @Update("  UPDATE" +
            "    roll_record" +
            "  SET" +
            "    amount = #{amount}," +
            "    stone = #{stone}" +
            "  WHERE" +
            "    uid = #{uid}")
    void updateAmount(@Param("uid") String uid, @Param("amount") Double amount, @Param("stone") Integer stone);


    @Insert("  INSERT INTO" +
            "    roll_record (" +
            "      uid," +
            "      nickname," +
            "      amount," +
            "      stone" +
            "    )" +
            "  VALUES (" +
            "    #{uid}," +
            "    #{nickname}," +
            "    #{amount}," +
            "    #{stone}" +
            "  )")
    void insertUser(@Param("uid") String uid, @Param("nickname") String nickname, @Param("amount") Double amount, @Param("stone") Integer stone);
}
