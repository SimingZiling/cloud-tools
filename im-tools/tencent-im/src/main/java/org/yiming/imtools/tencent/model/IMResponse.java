package org.yiming.imtools.tencent.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class IMResponse {

    @JSONField(name = "ActionStatus")
    private String actionStatus;

    @JSONField(name = "ErrorCode")
    private Integer errorCode;

    @JSONField(name = "ErrorInfo")
    private String errorInfo;

    @JSONField(name = "QueryResult")
    private List<QueryResult> queryResultList;

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public List<QueryResult> getQueryResultList() {
        return queryResultList;
    }

    public void setQueryResultList(List<QueryResult> queryResultList) {
        this.queryResultList = queryResultList;
    }

    public static class QueryResult{

        @JSONField(name = "To_Account")
        private String toAccount;

        @JSONField(name = "State")
        private String state;

        public String getToAccount() {
            return toAccount;
        }

        public void setToAccount(String toAccount) {
            this.toAccount = toAccount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
