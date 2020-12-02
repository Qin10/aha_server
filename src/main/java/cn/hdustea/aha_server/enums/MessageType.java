package cn.hdustea.aha_server.enums;

import lombok.Getter;

/**
 * 站内信类型枚举
 *
 * @author STEA_YY
 **/
@Getter
public enum MessageType {
    /**
     * 站内信未读
     */
    STATUS_NOT_READ(0),
    /**
     * 站内信已读
     */
    STATUS_READ(1),
    /**
     * 站内信被删除
     */
    STATUS_DELETED(2),
    /**
     * 系统消息
     */
    TYPE_SYSTEM(0),
    /**
     * 私信
     */
    TYPE_PRIVATE(1),
    /**
     * 站内通知
     */
    TYPE_NOTICE(2),
    /**
     * 站内系统通知
     */
    TYPE_SYSTEM_NOTICE(3);
    private final Integer value;

    MessageType(Integer value) {
        this.value = value;
    }
}
