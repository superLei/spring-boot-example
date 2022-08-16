package com.example.service.qa.common;


public class BusinessException extends ServiceException {

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(String code) {
        super(code, ImportErrorCodeProperties.getErrorMessage(code));
    }


//    public String  toStr() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code",this.getErrorCode());
//        jsonObject.put("message",this.getErrorMessage());
//        return jsonObject.toJSONString();
//    }
}
