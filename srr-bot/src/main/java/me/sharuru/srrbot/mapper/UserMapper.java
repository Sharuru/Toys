package me.sharuru.srrbot.mapper;

import me.sharuru.srrbot.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 用户表搜索类
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user WHERE number = #{number}")
    UserEntity findOneByNumber(@Param("number") String number);

    @Insert("INSERT INTO user(number, score) VALUES (#{number}, #{score}) ON CONFLICT(number) DO UPDATE SET score = #{score} WHERE number = #{number}")
    void upsertUserInfo(@Param("number") String number, @Param("score") int score);

    @Update("UPDATE user SET potential = #{potential} WHERE number = #{number}")
    void updateUserPotential(@Param("number") String number, @Param("potential") int potential);

}
