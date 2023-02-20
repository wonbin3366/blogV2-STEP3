package shop.mtcoding.momo.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoveRepository {

        public List<Love> findAll();

        public Love findById(int id);

        public int insert(Love love);

        public int updateById(Love love);

        public int deleteById(int id);

        public Love findByBoardIdAndUserId(@Param("boardId") int boardId, @Param("userId") int userId);
}