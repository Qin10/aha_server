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
    @NotNull(message = "项目id不能为空！")
    private Integer projectId;
    @NotEmpty(message = "资源id不能为空！")
    private Integer[] resourceIds;
}
