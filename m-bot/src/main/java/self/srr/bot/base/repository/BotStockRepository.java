package self.srr.bot.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import self.srr.bot.base.entity.TblBotStock;

/**
 * Bot stock repository
 * <p>
 * Created by Sharuru on 2017/06/27.
 */
@Repository
public interface BotStockRepository extends JpaRepository<TblBotStock, Long>, QueryByExampleExecutor<TblBotStock> {
}
