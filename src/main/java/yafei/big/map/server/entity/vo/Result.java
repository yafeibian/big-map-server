package yafei.big.map.server.entity.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.time.ZonedDateTime;

@ApiModel(
        description = "rest请求的返回模型，所有rest正常都返回该类的对象"
)
public class Result<T> {
    public static final String SUCCESSFUL_CODE = "000000";
    public static final String SUCCESSFUL_MESG = "处理成功";
    @ApiModelProperty(
            value = "处理结果code",
            required = true
    )
    private String code;
    @ApiModelProperty("处理结果描述信息")
    private String mesg;
    @ApiModelProperty("请求结果生成时间戳")
    private Instant time;
    @ApiModelProperty("处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    private Result(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
        this.time = ZonedDateTime.now().toInstant();
    }
    private Result(String code, String mesg, T data) {
        this.code = code;
        this.mesg = mesg;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    public static Result success(Object data) {
        return new Result("000000", "处理成功", data);
    }

    public static Result success() {
        return success((Object)null);
    }

    public static Result fail() {
        return new Result("-1","系统异常");
    }

    public static Result fail(String mesg) {
        return new Result("-1",mesg);
    }

    public static Result fail(String mesg, Object data) {
        return new Result("020000","系统异常",data);
    }


    @JsonIgnore
    public boolean isSuccess() {
        return "000000".equals(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    public String getCode() {
        return this.code;
    }

    public String getMesg() {
        return this.mesg;
    }

    public Instant getTime() {
        return this.time;
    }

    public T getData() {
        return this.data;
    }
}
