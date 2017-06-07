package self.srr.bot.biz.fish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.bot.biz.fish.entity.TblFishTimeRecord;

/**
 * Fish repository
 * <p>
 * Created by Sharuru on 2017/06/07.
 */
@Repository
public interface FishRepository extends JpaRepository<TblFishTimeRecord, Long>, QueryByExampleExecutor<TblFishTimeRecord> {
}
