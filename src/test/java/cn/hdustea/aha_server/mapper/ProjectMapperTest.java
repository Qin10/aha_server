package cn.hdustea.aha_server.mapper;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/7
 * @description:
 */
public class ProjectMapperTest {
    private static cn.hdustea.aha_server.mapper.ProjectMapper mapper;

    @org.junit.BeforeClass
    public static void setUpMybatisDatabase() {
        org.apache.ibatis.session.SqlSessionFactory builder = new org.apache.ibatis.session.SqlSessionFactoryBuilder().build(ProjectMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/ProjectMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(cn.hdustea.aha_server.mapper.ProjectMapper.class, builder.openSession(true));
    }

    @org.junit.Test
    public void testSelectRoughByPrimaryKey() {
        mapper.selectRoughByPrimaryKey(1);
    }
}
