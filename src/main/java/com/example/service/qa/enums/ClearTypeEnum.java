package com.example.service.qa.enums;


import lombok.Getter;

/**
 * @author sulei
 * @date 2019/11/15
 * @desc 数据清除类型枚举
 */
public enum ClearTypeEnum {

    DEFAULT_TYPE(0,"未知"),
    CLEAR_CARD_INFO(1,"清除会员卡信息"),
    CLEAR_CARD_POINT(2,"清除会员积分"),
    CLEAR_CUSTOMER_CHANNEL(3,"清除会员渠道信息"),
    CLEAR_WECHAT_CARD(4,"解绑微信卡包"),
    UPDATE_CARD_LEVEL(5,"更新卡等级信息"),
    VOUCHER_EXTEND_VAILD(6,"券有效期变更"),
    POINT_DOUBLE(7, "积分扩大n倍"),
	UPDATE_CUSTOMERBIRTHDAY(8, "修复会员birthday数据"),
    ADD_CARDTYPELEVEL(9,"添加卡等级"),
	CLEAR_CARD_POINT_ALL(10, "清除集团下的所有积分"),
	CLEAR_CARD_POINT_CRETESHOPID(11, "清除指定入会店铺的所有积分"),
	CLEAR_CARD_POINT_CARDTYPE(12, "清除指定卡类型的所有积分"),
	CLEAR_CARD_POINT_CARDLEVEL(13, "清除指定卡等级的所有积分"),
    UPDATE_VOUCHER_PRICE(14, "变更券售价"),
    UPDATE_VOUCHER_VALUE(15, "变更券面值"),
	CROSS_SHOPCONSUME_ALL(16, "重算指定集团下的跨店交易"),
	CROSS_SHOPCONSUME_CARDTYPE(17, "重算指定卡类型下的跨店交易"),
	CROSS_SHOPCONSUME_CARDLEVEL(18, "重算指定卡等级下的跨店交易"),
    FILL_CUSTOMER_CHANNEL_INFO(19, "针对CRM导入补充渠道信息"),
    EXTEND_CARD_LEVEL_TIME(20, "延长卡等级时间"),
    UPDATE_CARD_TYPE_LIMIT(21, "变更卡类型类型"),
    DEL_CUSTOMER_BY_MOBILE(22, "删除手机号下会员人"),
    DEL_CUSTOMER_BY_GROUPID(23, "删除集团下会员人"),
    EXPORT_CUSTOMER_INFO(24, "导出会员信息"),
    CHANGE_VOUCHER_STATUS(25, "变更券状态");


    @Getter
    private int value;
    @Getter
    private String name;

    ClearTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据传入的typeID取出对应枚举值
     *
     * @param typeID 渠道ID
     * @return 对应枚举信息
     */
    public static ClearTypeEnum getChannelInfoEnum(int typeID) {
        ClearTypeEnum[] channelInfoEnums = ClearTypeEnum.values();
        for (ClearTypeEnum channelInfoEnum : channelInfoEnums) {
            if (channelInfoEnum.getValue() == typeID) {
                return channelInfoEnum;
            }
        }
        return null;
    }

}
