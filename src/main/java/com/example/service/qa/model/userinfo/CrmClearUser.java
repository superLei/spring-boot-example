package com.example.service.qa.model.userinfo;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sulei
 */
@Data
public class CrmClearUser implements Serializable {

    private static final long serialVersionUID = 1579244923131L;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 盐值
     */
    private String salt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String state;

    /**
     * 主键
     * 用户编号
     * isNullAble:0
     */
    private Integer id;

    /**
     * 用户名
     * isNullAble:0
     */
    private String username;

    /**
     * 密码
     * isNullAble:0
     */
    private String password;

    /**
     * 邮箱
     * isNullAble:0
     */
    private String email;

    /**
     * 性别
     * isNullAble:0,defaultVal:0
     */
    private Integer sex;

    /**
     * 电话
     * isNullAble:0
     */
    private String tel;

    /**
     * 地址
     * isNullAble:0,defaultVal:beijing
     */
    private String addr;

    /**
     * 身份证号
     * isNullAble:0
     */
    private String card;

    private String realName;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return this.tel;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCard() {
        return this.card;
    }

    @Override
    public String toString() {
        return "CrmClearUser{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                "password='" + password + '\'' +
                "email='" + email + '\'' +
                "sex='" + sex + '\'' +
                "tel='" + tel + '\'' +
                "addr='" + addr + '\'' +
                "card='" + card + '\'' +
                '}';
    }

    public static Builder Build() {
        return new Builder();
    }

    public static ConditionBuilder ConditionBuild() {
        return new ConditionBuilder();
    }

    public static UpdateBuilder UpdateBuild() {
        return new UpdateBuilder();
    }

    public static QueryBuilder QueryBuild() {
        return new QueryBuilder();
    }

    public static class UpdateBuilder {

        private CrmClearUser set;

        private ConditionBuilder where;

        public UpdateBuilder set(CrmClearUser set) {
            this.set = set;
            return this;
        }

        public CrmClearUser getSet() {
            return this.set;
        }

        public UpdateBuilder where(ConditionBuilder where) {
            this.where = where;
            return this;
        }

        public ConditionBuilder getWhere() {
            return this.where;
        }

        public UpdateBuilder build() {
            return this;
        }
    }

    public static class QueryBuilder extends CrmClearUser {
        /**
         * 需要返回的列
         */
        private Map<String, Object> fetchFields;

        public Map<String, Object> getFetchFields() {
            return this.fetchFields;
        }

        private List<Integer> idList;

        public List<Integer> getIdList() {
            return this.idList;
        }

        private Integer idSt;

        private Integer idEd;

        public Integer getIdSt() {
            return this.idSt;
        }

        public Integer getIdEd() {
            return this.idEd;
        }

        private List<String> usernameList;

        public List<String> getUsernameList() {
            return this.usernameList;
        }


        private List<String> fuzzyUsername;

        public List<String> getFuzzyUsername() {
            return this.fuzzyUsername;
        }

        private List<String> rightFuzzyUsername;

        public List<String> getRightFuzzyUsername() {
            return this.rightFuzzyUsername;
        }

        private List<String> passwordList;

        public List<String> getPasswordList() {
            return this.passwordList;
        }


        private List<String> fuzzyPassword;

        public List<String> getFuzzyPassword() {
            return this.fuzzyPassword;
        }

        private List<String> rightFuzzyPassword;

        public List<String> getRightFuzzyPassword() {
            return this.rightFuzzyPassword;
        }

        private List<String> emailList;

        public List<String> getEmailList() {
            return this.emailList;
        }


        private List<String> fuzzyEmail;

        public List<String> getFuzzyEmail() {
            return this.fuzzyEmail;
        }

        private List<String> rightFuzzyEmail;

        public List<String> getRightFuzzyEmail() {
            return this.rightFuzzyEmail;
        }

        private List<Integer> sexList;

        public List<Integer> getSexList() {
            return this.sexList;
        }

        private Integer sexSt;

        private Integer sexEd;

        public Integer getSexSt() {
            return this.sexSt;
        }

        public Integer getSexEd() {
            return this.sexEd;
        }

        private List<String> telList;

        public List<String> getTelList() {
            return this.telList;
        }


        private List<String> fuzzyTel;

        public List<String> getFuzzyTel() {
            return this.fuzzyTel;
        }

        private List<String> rightFuzzyTel;

        public List<String> getRightFuzzyTel() {
            return this.rightFuzzyTel;
        }

        private List<String> addrList;

        public List<String> getAddrList() {
            return this.addrList;
        }


        private List<String> fuzzyAddr;

        public List<String> getFuzzyAddr() {
            return this.fuzzyAddr;
        }

        private List<String> rightFuzzyAddr;

        public List<String> getRightFuzzyAddr() {
            return this.rightFuzzyAddr;
        }

        private List<String> cardList;

        public List<String> getCardList() {
            return this.cardList;
        }


        private List<String> fuzzyCard;

        public List<String> getFuzzyCard() {
            return this.fuzzyCard;
        }

        private List<String> rightFuzzyCard;

        public List<String> getRightFuzzyCard() {
            return this.rightFuzzyCard;
        }

        private QueryBuilder() {
            this.fetchFields = new HashMap<>();
        }

        public QueryBuilder idBetWeen(Integer idSt, Integer idEd) {
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public QueryBuilder idGreaterEqThan(Integer idSt) {
            this.idSt = idSt;
            return this;
        }

        public QueryBuilder idLessEqThan(Integer idEd) {
            this.idEd = idEd;
            return this;
        }


        public QueryBuilder id(Integer id) {
            setId(id);
            return this;
        }

        public QueryBuilder idList(Integer... id) {
            this.idList = solveNullList(id);
            return this;
        }

        public QueryBuilder idList(List<Integer> id) {
            this.idList = id;
            return this;
        }

        public QueryBuilder fetchId() {
            setFetchFields("fetchFields", "id");
            return this;
        }

        public QueryBuilder excludeId() {
            setFetchFields("excludeFields", "id");
            return this;
        }

        public QueryBuilder fuzzyUsername(List<String> fuzzyUsername) {
            this.fuzzyUsername = fuzzyUsername;
            return this;
        }

        public QueryBuilder fuzzyUsername(String... fuzzyUsername) {
            this.fuzzyUsername = solveNullList(fuzzyUsername);
            return this;
        }

        public QueryBuilder rightFuzzyUsername(List<String> rightFuzzyUsername) {
            this.rightFuzzyUsername = rightFuzzyUsername;
            return this;
        }

        public QueryBuilder rightFuzzyUsername(String... rightFuzzyUsername) {
            this.rightFuzzyUsername = solveNullList(rightFuzzyUsername);
            return this;
        }

        public QueryBuilder username(String username) {
            setUsername(username);
            return this;
        }

        public QueryBuilder usernameList(String... username) {
            this.usernameList = solveNullList(username);
            return this;
        }

        public QueryBuilder usernameList(List<String> username) {
            this.usernameList = username;
            return this;
        }

        public QueryBuilder fetchUsername() {
            setFetchFields("fetchFields", "username");
            return this;
        }

        public QueryBuilder excludeUsername() {
            setFetchFields("excludeFields", "username");
            return this;
        }

        public QueryBuilder fuzzyPassword(List<String> fuzzyPassword) {
            this.fuzzyPassword = fuzzyPassword;
            return this;
        }

        public QueryBuilder fuzzyPassword(String... fuzzyPassword) {
            this.fuzzyPassword = solveNullList(fuzzyPassword);
            return this;
        }

        public QueryBuilder rightFuzzyPassword(List<String> rightFuzzyPassword) {
            this.rightFuzzyPassword = rightFuzzyPassword;
            return this;
        }

        public QueryBuilder rightFuzzyPassword(String... rightFuzzyPassword) {
            this.rightFuzzyPassword = solveNullList(rightFuzzyPassword);
            return this;
        }

        public QueryBuilder password(String password) {
            setPassword(password);
            return this;
        }

        public QueryBuilder passwordList(String... password) {
            this.passwordList = solveNullList(password);
            return this;
        }

        public QueryBuilder passwordList(List<String> password) {
            this.passwordList = password;
            return this;
        }

        public QueryBuilder fetchPassword() {
            setFetchFields("fetchFields", "password");
            return this;
        }

        public QueryBuilder excludePassword() {
            setFetchFields("excludeFields", "password");
            return this;
        }

        public QueryBuilder fuzzyEmail(List<String> fuzzyEmail) {
            this.fuzzyEmail = fuzzyEmail;
            return this;
        }

        public QueryBuilder fuzzyEmail(String... fuzzyEmail) {
            this.fuzzyEmail = solveNullList(fuzzyEmail);
            return this;
        }

        public QueryBuilder rightFuzzyEmail(List<String> rightFuzzyEmail) {
            this.rightFuzzyEmail = rightFuzzyEmail;
            return this;
        }

        public QueryBuilder rightFuzzyEmail(String... rightFuzzyEmail) {
            this.rightFuzzyEmail = solveNullList(rightFuzzyEmail);
            return this;
        }

        public QueryBuilder email(String email) {
            setEmail(email);
            return this;
        }

        public QueryBuilder emailList(String... email) {
            this.emailList = solveNullList(email);
            return this;
        }

        public QueryBuilder emailList(List<String> email) {
            this.emailList = email;
            return this;
        }

        public QueryBuilder fetchEmail() {
            setFetchFields("fetchFields", "email");
            return this;
        }

        public QueryBuilder excludeEmail() {
            setFetchFields("excludeFields", "email");
            return this;
        }

        public QueryBuilder sexBetWeen(Integer sexSt, Integer sexEd) {
            this.sexSt = sexSt;
            this.sexEd = sexEd;
            return this;
        }

        public QueryBuilder sexGreaterEqThan(Integer sexSt) {
            this.sexSt = sexSt;
            return this;
        }

        public QueryBuilder sexLessEqThan(Integer sexEd) {
            this.sexEd = sexEd;
            return this;
        }


        public QueryBuilder sex(Integer sex) {
            setSex(sex);
            return this;
        }

        public QueryBuilder sexList(Integer... sex) {
            this.sexList = solveNullList(sex);
            return this;
        }

        public QueryBuilder sexList(List<Integer> sex) {
            this.sexList = sex;
            return this;
        }

        public QueryBuilder fetchSex() {
            setFetchFields("fetchFields", "sex");
            return this;
        }

        public QueryBuilder excludeSex() {
            setFetchFields("excludeFields", "sex");
            return this;
        }

        public QueryBuilder fuzzyTel(List<String> fuzzyTel) {
            this.fuzzyTel = fuzzyTel;
            return this;
        }

        public QueryBuilder fuzzyTel(String... fuzzyTel) {
            this.fuzzyTel = solveNullList(fuzzyTel);
            return this;
        }

        public QueryBuilder rightFuzzyTel(List<String> rightFuzzyTel) {
            this.rightFuzzyTel = rightFuzzyTel;
            return this;
        }

        public QueryBuilder rightFuzzyTel(String... rightFuzzyTel) {
            this.rightFuzzyTel = solveNullList(rightFuzzyTel);
            return this;
        }

        public QueryBuilder tel(String tel) {
            setTel(tel);
            return this;
        }

        public QueryBuilder telList(String... tel) {
            this.telList = solveNullList(tel);
            return this;
        }

        public QueryBuilder telList(List<String> tel) {
            this.telList = tel;
            return this;
        }

        public QueryBuilder fetchTel() {
            setFetchFields("fetchFields", "tel");
            return this;
        }

        public QueryBuilder excludeTel() {
            setFetchFields("excludeFields", "tel");
            return this;
        }

        public QueryBuilder fuzzyAddr(List<String> fuzzyAddr) {
            this.fuzzyAddr = fuzzyAddr;
            return this;
        }

        public QueryBuilder fuzzyAddr(String... fuzzyAddr) {
            this.fuzzyAddr = solveNullList(fuzzyAddr);
            return this;
        }

        public QueryBuilder rightFuzzyAddr(List<String> rightFuzzyAddr) {
            this.rightFuzzyAddr = rightFuzzyAddr;
            return this;
        }

        public QueryBuilder rightFuzzyAddr(String... rightFuzzyAddr) {
            this.rightFuzzyAddr = solveNullList(rightFuzzyAddr);
            return this;
        }

        public QueryBuilder addr(String addr) {
            setAddr(addr);
            return this;
        }

        public QueryBuilder addrList(String... addr) {
            this.addrList = solveNullList(addr);
            return this;
        }

        public QueryBuilder addrList(List<String> addr) {
            this.addrList = addr;
            return this;
        }

        public QueryBuilder fetchAddr() {
            setFetchFields("fetchFields", "addr");
            return this;
        }

        public QueryBuilder excludeAddr() {
            setFetchFields("excludeFields", "addr");
            return this;
        }

        public QueryBuilder fuzzyCard(List<String> fuzzyCard) {
            this.fuzzyCard = fuzzyCard;
            return this;
        }

        public QueryBuilder fuzzyCard(String... fuzzyCard) {
            this.fuzzyCard = solveNullList(fuzzyCard);
            return this;
        }

        public QueryBuilder rightFuzzyCard(List<String> rightFuzzyCard) {
            this.rightFuzzyCard = rightFuzzyCard;
            return this;
        }

        public QueryBuilder rightFuzzyCard(String... rightFuzzyCard) {
            this.rightFuzzyCard = solveNullList(rightFuzzyCard);
            return this;
        }

        public QueryBuilder card(String card) {
            setCard(card);
            return this;
        }

        public QueryBuilder cardList(String... card) {
            this.cardList = solveNullList(card);
            return this;
        }

        public QueryBuilder cardList(List<String> card) {
            this.cardList = card;
            return this;
        }

        public QueryBuilder fetchCard() {
            setFetchFields("fetchFields", "card");
            return this;
        }

        public QueryBuilder excludeCard() {
            setFetchFields("excludeFields", "card");
            return this;
        }

        private <T> List<T> solveNullList(T... objs) {
            if (objs != null) {
                List<T> list = new ArrayList<>();
                for (T item : objs) {
                    if (item != null) {
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public QueryBuilder fetchAll() {
            this.fetchFields.put("AllFields", true);
            return this;
        }

        public QueryBuilder addField(String... fields) {
            List<String> list = new ArrayList<>();
            if (fields != null) {
                for (String field : fields) {
                    list.add(field);
                }
            }
            this.fetchFields.put("otherFields", list);
            return this;
        }

        @SuppressWarnings("unchecked")
        private void setFetchFields(String key, String val) {
            Map<String, Boolean> fields = (Map<String, Boolean>) this.fetchFields.get(key);
            if (fields == null) {
                fields = new HashMap<>();
            }
            fields.put(val, true);
            this.fetchFields.put(key, fields);
        }

        public CrmClearUser build() {
            return this;
        }
    }


    public static class ConditionBuilder {
        private List<Integer> idList;

        public List<Integer> getIdList() {
            return this.idList;
        }

        private Integer idSt;

        private Integer idEd;

        public Integer getIdSt() {
            return this.idSt;
        }

        public Integer getIdEd() {
            return this.idEd;
        }

        private List<String> usernameList;

        public List<String> getUsernameList() {
            return this.usernameList;
        }


        private List<String> fuzzyUsername;

        public List<String> getFuzzyUsername() {
            return this.fuzzyUsername;
        }

        private List<String> rightFuzzyUsername;

        public List<String> getRightFuzzyUsername() {
            return this.rightFuzzyUsername;
        }

        private List<String> passwordList;

        public List<String> getPasswordList() {
            return this.passwordList;
        }


        private List<String> fuzzyPassword;

        public List<String> getFuzzyPassword() {
            return this.fuzzyPassword;
        }

        private List<String> rightFuzzyPassword;

        public List<String> getRightFuzzyPassword() {
            return this.rightFuzzyPassword;
        }

        private List<String> emailList;

        public List<String> getEmailList() {
            return this.emailList;
        }


        private List<String> fuzzyEmail;

        public List<String> getFuzzyEmail() {
            return this.fuzzyEmail;
        }

        private List<String> rightFuzzyEmail;

        public List<String> getRightFuzzyEmail() {
            return this.rightFuzzyEmail;
        }

        private List<Integer> sexList;

        public List<Integer> getSexList() {
            return this.sexList;
        }

        private Integer sexSt;

        private Integer sexEd;

        public Integer getSexSt() {
            return this.sexSt;
        }

        public Integer getSexEd() {
            return this.sexEd;
        }

        private List<String> telList;

        public List<String> getTelList() {
            return this.telList;
        }


        private List<String> fuzzyTel;

        public List<String> getFuzzyTel() {
            return this.fuzzyTel;
        }

        private List<String> rightFuzzyTel;

        public List<String> getRightFuzzyTel() {
            return this.rightFuzzyTel;
        }

        private List<String> addrList;

        public List<String> getAddrList() {
            return this.addrList;
        }


        private List<String> fuzzyAddr;

        public List<String> getFuzzyAddr() {
            return this.fuzzyAddr;
        }

        private List<String> rightFuzzyAddr;

        public List<String> getRightFuzzyAddr() {
            return this.rightFuzzyAddr;
        }

        private List<String> cardList;

        public List<String> getCardList() {
            return this.cardList;
        }


        private List<String> fuzzyCard;

        public List<String> getFuzzyCard() {
            return this.fuzzyCard;
        }

        private List<String> rightFuzzyCard;

        public List<String> getRightFuzzyCard() {
            return this.rightFuzzyCard;
        }

        public ConditionBuilder idBetWeen(Integer idSt, Integer idEd) {
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public ConditionBuilder idGreaterEqThan(Integer idSt) {
            this.idSt = idSt;
            return this;
        }

        public ConditionBuilder idLessEqThan(Integer idEd) {
            this.idEd = idEd;
            return this;
        }


        public ConditionBuilder idList(Integer... id) {
            this.idList = solveNullList(id);
            return this;
        }

        public ConditionBuilder idList(List<Integer> id) {
            this.idList = id;
            return this;
        }

        public ConditionBuilder fuzzyUsername(List<String> fuzzyUsername) {
            this.fuzzyUsername = fuzzyUsername;
            return this;
        }

        public ConditionBuilder fuzzyUsername(String... fuzzyUsername) {
            this.fuzzyUsername = solveNullList(fuzzyUsername);
            return this;
        }

        public ConditionBuilder rightFuzzyUsername(List<String> rightFuzzyUsername) {
            this.rightFuzzyUsername = rightFuzzyUsername;
            return this;
        }

        public ConditionBuilder rightFuzzyUsername(String... rightFuzzyUsername) {
            this.rightFuzzyUsername = solveNullList(rightFuzzyUsername);
            return this;
        }

        public ConditionBuilder usernameList(String... username) {
            this.usernameList = solveNullList(username);
            return this;
        }

        public ConditionBuilder usernameList(List<String> username) {
            this.usernameList = username;
            return this;
        }

        public ConditionBuilder fuzzyPassword(List<String> fuzzyPassword) {
            this.fuzzyPassword = fuzzyPassword;
            return this;
        }

        public ConditionBuilder fuzzyPassword(String... fuzzyPassword) {
            this.fuzzyPassword = solveNullList(fuzzyPassword);
            return this;
        }

        public ConditionBuilder rightFuzzyPassword(List<String> rightFuzzyPassword) {
            this.rightFuzzyPassword = rightFuzzyPassword;
            return this;
        }

        public ConditionBuilder rightFuzzyPassword(String... rightFuzzyPassword) {
            this.rightFuzzyPassword = solveNullList(rightFuzzyPassword);
            return this;
        }

        public ConditionBuilder passwordList(String... password) {
            this.passwordList = solveNullList(password);
            return this;
        }

        public ConditionBuilder passwordList(List<String> password) {
            this.passwordList = password;
            return this;
        }

        public ConditionBuilder fuzzyEmail(List<String> fuzzyEmail) {
            this.fuzzyEmail = fuzzyEmail;
            return this;
        }

        public ConditionBuilder fuzzyEmail(String... fuzzyEmail) {
            this.fuzzyEmail = solveNullList(fuzzyEmail);
            return this;
        }

        public ConditionBuilder rightFuzzyEmail(List<String> rightFuzzyEmail) {
            this.rightFuzzyEmail = rightFuzzyEmail;
            return this;
        }

        public ConditionBuilder rightFuzzyEmail(String... rightFuzzyEmail) {
            this.rightFuzzyEmail = solveNullList(rightFuzzyEmail);
            return this;
        }

        public ConditionBuilder emailList(String... email) {
            this.emailList = solveNullList(email);
            return this;
        }

        public ConditionBuilder emailList(List<String> email) {
            this.emailList = email;
            return this;
        }

        public ConditionBuilder sexBetWeen(Integer sexSt, Integer sexEd) {
            this.sexSt = sexSt;
            this.sexEd = sexEd;
            return this;
        }

        public ConditionBuilder sexGreaterEqThan(Integer sexSt) {
            this.sexSt = sexSt;
            return this;
        }

        public ConditionBuilder sexLessEqThan(Integer sexEd) {
            this.sexEd = sexEd;
            return this;
        }


        public ConditionBuilder sexList(Integer... sex) {
            this.sexList = solveNullList(sex);
            return this;
        }

        public ConditionBuilder sexList(List<Integer> sex) {
            this.sexList = sex;
            return this;
        }

        public ConditionBuilder fuzzyTel(List<String> fuzzyTel) {
            this.fuzzyTel = fuzzyTel;
            return this;
        }

        public ConditionBuilder fuzzyTel(String... fuzzyTel) {
            this.fuzzyTel = solveNullList(fuzzyTel);
            return this;
        }

        public ConditionBuilder rightFuzzyTel(List<String> rightFuzzyTel) {
            this.rightFuzzyTel = rightFuzzyTel;
            return this;
        }

        public ConditionBuilder rightFuzzyTel(String... rightFuzzyTel) {
            this.rightFuzzyTel = solveNullList(rightFuzzyTel);
            return this;
        }

        public ConditionBuilder telList(String... tel) {
            this.telList = solveNullList(tel);
            return this;
        }

        public ConditionBuilder telList(List<String> tel) {
            this.telList = tel;
            return this;
        }

        public ConditionBuilder fuzzyAddr(List<String> fuzzyAddr) {
            this.fuzzyAddr = fuzzyAddr;
            return this;
        }

        public ConditionBuilder fuzzyAddr(String... fuzzyAddr) {
            this.fuzzyAddr = solveNullList(fuzzyAddr);
            return this;
        }

        public ConditionBuilder rightFuzzyAddr(List<String> rightFuzzyAddr) {
            this.rightFuzzyAddr = rightFuzzyAddr;
            return this;
        }

        public ConditionBuilder rightFuzzyAddr(String... rightFuzzyAddr) {
            this.rightFuzzyAddr = solveNullList(rightFuzzyAddr);
            return this;
        }

        public ConditionBuilder addrList(String... addr) {
            this.addrList = solveNullList(addr);
            return this;
        }

        public ConditionBuilder addrList(List<String> addr) {
            this.addrList = addr;
            return this;
        }

        public ConditionBuilder fuzzyCard(List<String> fuzzyCard) {
            this.fuzzyCard = fuzzyCard;
            return this;
        }

        public ConditionBuilder fuzzyCard(String... fuzzyCard) {
            this.fuzzyCard = solveNullList(fuzzyCard);
            return this;
        }

        public ConditionBuilder rightFuzzyCard(List<String> rightFuzzyCard) {
            this.rightFuzzyCard = rightFuzzyCard;
            return this;
        }

        public ConditionBuilder rightFuzzyCard(String... rightFuzzyCard) {
            this.rightFuzzyCard = solveNullList(rightFuzzyCard);
            return this;
        }

        public ConditionBuilder cardList(String... card) {
            this.cardList = solveNullList(card);
            return this;
        }

        public ConditionBuilder cardList(List<String> card) {
            this.cardList = card;
            return this;
        }

        private <T> List<T> solveNullList(T... objs) {
            if (objs != null) {
                List<T> list = new ArrayList<>();
                for (T item : objs) {
                    if (item != null) {
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public ConditionBuilder build() {
            return this;
        }
    }

    public static class Builder {

        private CrmClearUser obj;

        public Builder() {
            this.obj = new CrmClearUser();
        }

        public Builder id(Integer id) {
            this.obj.setId(id);
            return this;
        }

        public Builder username(String username) {
            this.obj.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            this.obj.setPassword(password);
            return this;
        }

        public Builder email(String email) {
            this.obj.setEmail(email);
            return this;
        }

        public Builder sex(Integer sex) {
            this.obj.setSex(sex);
            return this;
        }

        public Builder tel(String tel) {
            this.obj.setTel(tel);
            return this;
        }

        public Builder addr(String addr) {
            this.obj.setAddr(addr);
            return this;
        }

        public Builder card(String card) {
            this.obj.setCard(card);
            return this;
        }

        public CrmClearUser build() {
            return obj;
        }
    }

}
