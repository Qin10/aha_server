package cn.hdustea.aha_server.enums;

import lombok.Getter;

/**
 * 站内信类型枚举
 *
 * @author STEA_YY
 **/
@Getter
public enum MessageType {
    STATUS_NOT_READ(0, "未读"),
    STATUS_READ(1, "已读"),
    STATUS_DELETED(2, "已删除"),
    TYPE_SYSTEM(0, "系统消息"),
    TYPE_PRIVATE(1, "私信"),
    TYPE_NOTICE(2, "站内通知");

    private final Integer value;
    private final String desc;

    MessageType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
