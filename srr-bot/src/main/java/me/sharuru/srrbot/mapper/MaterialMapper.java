package me.sharuru.srrbot.mapper;

import me.sharuru.srrbot.entity.MaterialEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialMapper {

    @Select("SELECT * FROM material WHERE catalog = #{catalog}")
    List<MaterialEntity> findAllByCatalog(@Param("catalog") String catalog);

    @Select("SELECT * FROM material WHERE catalog = #{catalog} AND threshold <= #{threshold}")
    List<MaterialEntity> findAllByCatalogAndThreshold(@Param("catalog") String catalog, @Param("threshold") int threshold);
}
