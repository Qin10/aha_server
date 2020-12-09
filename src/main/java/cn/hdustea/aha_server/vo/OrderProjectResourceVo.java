package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单-资源中间表VO
 *
 * @author STEA_YY
 **/
@Data
public class OrderProjectResourceVo {

    /**
     * 项目资源id
     */
    private ProjectResourceVo resource;

    /**
     * 资源折扣
     */
    private BigDecimal discount;

    /**
     * 贡献点小计
     */
    private BigDecimal price;
}
