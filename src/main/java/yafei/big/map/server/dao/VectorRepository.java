package yafei.big.map.server.dao;


import yafei.big.map.server.entity.po.Vector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VectorRepository extends JpaRepository<Vector, Integer> {
}
