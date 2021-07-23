package yafei.big.map.server.entity.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_vector")
@Data
@ToString
public class Vector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String projectId;
    private String inFileName;
    private String inFilePath;
    private String outFileName;
    private String outFilePath;
    private Date startTime;
    private Date endTime;
    private String type;
    /**
     * 节点名称
     */
    private String nodeName;



}
