package self.srr.bot.base.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@Table(name = "bot_stock")
@EntityListeners(AuditingEntityListener.class)
public class TblBotStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "fish_time_record_id_seq")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_count")
    private BigDecimal itemCount;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblBotStock() {

    }
}
