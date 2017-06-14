package self.srr.bot.biz.roll.entitiy;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created by Sharuru on 2017/06/14.
 */
@Data
@Entity
@Table(name = "roll_user")
@EntityListeners(AuditingEntityListener.class)
public class TblRollUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "roll_user_id_seq")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String username;

    @Column(name = "user_amount")
    private BigDecimal userAmount;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblRollUser() {

    }
}
