import mapper.ElectionMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import pojo.Election;
import utils.SqlSessionUtil;

public class ElectionMapperTest {
    @Test
    public void testGetElectionById() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        ElectionMapper electionMapper = sqlSession.getMapper(ElectionMapper.class);

        Election election = electionMapper.getElectionById(1);
        System.out.println(election);

        sqlSession.close();
    }
}
