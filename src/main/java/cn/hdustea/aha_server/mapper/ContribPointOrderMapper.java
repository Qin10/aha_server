package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ContribPointOrder;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ContribPointOrderMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(ContribPointOrder record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(ContribPointOrder record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    ContribPointOrder selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ContribPointOrder record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(ContribPointOrder record);
}