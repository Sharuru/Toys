package self.srr.bot.biz.fish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.bot.biz.fish.entity.TblFishTimeRecord;

/**
 * Fish time repository
 * <p>
 * Created by Sharuru on 2017/06/07.
 */
@Repository
public interface FishTimeRepository extends JpaRepository<TblFishTimeRecord, Long>, QueryByExampleExecutor<TblFishTimeRecord> {

    /**
     * Get today's record by user id
     *
     * @param userId user id
     * @return record
     */
    @Query(value = "SELECT * FROM fish_time_record WHERE checkin_time > CURRENT_DATE AND checkin_time < CURRENT_DATE + 1 AND user_id = :userId ORDER BY id LIMIT 1", nativeQuery = true)
    TblFishTimeRecord findTodayByUserId(@Param("userId") String userId);
}
