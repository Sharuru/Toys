package self.srr.bot.biz.roll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.bot.biz.roll.entitiy.TblRollUser;

/**
 * Roll user repository
 * <p>
 * Created by Sharuru on 2017/06/14.
 */
@Repository
public interface RollUserRepository extends JpaRepository<TblRollUser, Long>, QueryByExampleExecutor<TblRollUser> {
}
