package yafei.big.map.server.dao;


import yafei.big.map.server.entity.po.NodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeStatusRepository extends JpaRepository<NodeStatus, Integer> {
}
