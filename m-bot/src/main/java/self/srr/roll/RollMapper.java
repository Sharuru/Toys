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

    /**
     * 根据 uid 获取用户信息
     *
     * @param uid 用户 uid
     * @return 用户信息
     */
    @Select("  SELECT" +
            "   *" +
            "  FROM" +
            "   roll_record" +
            "  WHERE" +
            "   uid = #{uid}" +
            "  LIMIT" +
            "   1")
    RollRecord findOneByUid(@Param("uid") String uid);

    /**
     * 更新用户金额信息
     *
     * @param uid    用户 uid
     * @param amount 金额
     * @param stone  水晶余额
     */
    @Update("  UPDATE" +
            "    roll_record" +
            "  SET" +
            "    amount = #{amount}," +
            "    stone = #{stone}" +
            "  WHERE" +
            "    uid = #{uid}")
    void updateAmount(@Param("uid") String uid, @Param("amount") Double amount, @Param("stone") Integer stone);

    /**
     * 新用户插入
     *
     * @param uid      用户 uid
     * @param nickname 用户昵称
     * @param amount   用户余额
     * @param stone    用户水晶
     */
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
