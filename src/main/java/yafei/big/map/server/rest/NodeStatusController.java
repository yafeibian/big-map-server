package yafei.big.map.server.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yafei.big.map.server.dao.NodeStatusRepository;
import yafei.big.map.server.dao.VectorRepository;
import yafei.big.map.server.entity.po.NodeStatus;
import yafei.big.map.server.entity.po.Vector;
import yafei.big.map.server.entity.vo.Result;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "节点状态")
@RequestMapping("/node")
public class NodeStatusController {
    @Resource
    private NodeStatusRepository nodeStatusRepository;


    // 查询空闲节点
    @GetMapping("/getFreeNode")
    public Result getFreeNode(){
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setStatus("free");
        Example<NodeStatus> example = Example.of(nodeStatus);
        List<NodeStatus> nodeList = nodeStatusRepository.findAll(example);
        if(nodeList!=null && nodeList.size()>0){
            NodeStatus nodeStatus1 = nodeList.get(0);
            return Result.success(nodeStatus1);
        }else{
            return Result.success("无空闲节，请等待");
        }
    }

    @DeleteMapping("/delAllNode")
    public Result delAllNode(){
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setStatus("free");
        Example<NodeStatus> example = Example.of(nodeStatus);
        List<NodeStatus> nodeList = nodeStatusRepository.findAll(example);
        if(nodeList!=null && nodeList.size()>0){
            NodeStatus nodeStatus1 = nodeList.get(0);
            return Result.success(nodeStatus1);
        }else{
            return Result.success("无空闲节，请等待");
        }
    }


    @ApiOperation("上报节点信息")
    @PostMapping("/reportNodeStatus")
    public Result reportNodeStatus(@RequestBody NodeStatus nodeStatus){
        //获取节点name
        NodeStatus parm = new NodeStatus();
        parm.setName(nodeStatus.getName());
        Example<NodeStatus> example = Example.of(parm);
        Optional<NodeStatus> one = nodeStatusRepository.findOne(example);
        if(one.isPresent()){

            NodeStatus dbObj = one.get();
            if(nodeStatus.getLastStartWorkTime()!=null){
                dbObj.setLastStartWorkTime(nodeStatus.getLastStartWorkTime());
            }
            if(nodeStatus.getLastEndWorkTime()!=null){
                dbObj.setLastEndWorkTime(nodeStatus.getLastEndWorkTime());
            }
            if(nodeStatus.getLastHeartTIme()!=null){
                dbObj.setLastHeartTIme(nodeStatus.getLastHeartTIme());
            }
            nodeStatusRepository.save(nodeStatus);
        }else{
            nodeStatusRepository.save(nodeStatus);
        }
        return Result.success();
    }

    @ApiOperation("查询所有运算节点")
    @GetMapping("/queryNodes")
    public Result queryNodes(){
        return Result.success(nodeStatusRepository.findAll());
    }



}
