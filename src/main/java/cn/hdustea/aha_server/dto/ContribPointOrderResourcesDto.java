package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 贡献点订单购买资源列表DTO
 *
 * @author STEA_YY
 **/
@Data
public class ContribPointOrderResourcesDto {

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空！")
    private Integer projectId;

    /**
     * 项目资源id数组
     */
    @NotEmpty(message = "资源id不能为空！")
    private Integer[] resourceIds;
}
