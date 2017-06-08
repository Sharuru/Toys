package self.srr.bot.biz.fish.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@Table(name = "fish_time_record")
@EntityListeners(AuditingEntityListener.class)

public class TblFishTimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "fish_time_record_id_seq")
    private Long id;

    private String username;

    @Column(name = "checkin_time")
    @Temporal(TIMESTAMP)
    private Date checkInTime;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date updatedAt;

    public TblFishTimeRecord(String username, Date checkInTime) {
        this.username = username;
        this.checkInTime = checkInTime;
    }
}
