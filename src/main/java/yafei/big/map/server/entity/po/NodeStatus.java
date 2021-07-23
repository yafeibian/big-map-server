package yafei.big.map.server.entity.po;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_nodestatus")
@Data
@ToString
@DynamicUpdate
public class NodeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 节点名称
     */
     private String name;
    /**
     * 节点url
     */
    private String url;
    /**
     * 节点状态
     */
    private String status;
    /**
     * 上一次开始工作时间
     */
    private Date lastStartWorkTime;
    /**
     * 上一次工作结束时间
     */
    private Date lastEndWorkTime;
    /**
     * 上一次心跳时间
     */
    private Date lastHeartTIme;

}
