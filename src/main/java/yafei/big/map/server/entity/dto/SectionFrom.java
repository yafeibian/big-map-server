package yafei.big.map.server.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.File;

@Data
@ApiModel(description="切片表单数据")
public class SectionFrom {
    private File file;
    private String projectId;
    private String fileName;

}
