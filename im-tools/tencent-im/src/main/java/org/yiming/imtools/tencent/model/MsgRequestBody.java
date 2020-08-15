package org.yiming.imtools.tencent.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.yiming.imtools.exception.IMToolsException;
import org.yiming.localtools.random.RandomValue;

import java.util.List;

/**
 * 消息请求
 */
public class MsgRequestBody {

    public MsgRequestBody(){}

    public MsgRequestBody(MsgRequestBody msgRequestBody){
        this.syncOtherMachine = msgRequestBody.syncOtherMachine;
        this.fromAccount = msgRequestBody.fromAccount;
        this.toAccount = msgRequestBody.toAccount;
        this.msgLifeTime = msgRequestBody.msgLifeTime;
        this.msgRandom = msgRequestBody.msgRandom;
        this.msgTimeStamp = msgRequestBody.msgTimeStamp;
        this.msgBodyList = msgRequestBody.msgBodyList;
        this.offlinePushInfo = msgRequestBody.offlinePushInfo;
        this.toAccountList = msgRequestBody.toAccountList;
    }

    @JSONField(name = "SyncOtherMachine")
    private Integer syncOtherMachine;// 1. 同步到From_Account终端和漫游上 2.消息不同步至From_Account 不填写则漫游至From_Account

    @JSONField(name = "From_Account")
    private String fromAccount;// 消息发送方 Identifier（用于指定发送消息方帐号）

    @JSONField(name = "To_Account")
    private String toAccount;// 消息接收方 Identifier 若设置该字段为0，则消息只发在线用户，不保存离线

    @JSONField(name = "To_Account_List")
    private List<String> toAccountList;//消息接收方用户 Identifier

    @JSONField(name = "MsgLifeTime")
    private Integer msgLifeTime;//消息离线保存时长（单位：秒），最长为7天（604800秒）若不设置该字段，则默认保存7天

    @JSONField(name = "MsgRandom")
    private Integer msgRandom;//消息随机数，由随机函数产生，用于后台定位问题

    @JSONField(name = "MsgTimeStamp")
    private Integer msgTimeStamp;// 消息时间戳，UNIX 时间戳（单位：秒）

    @JSONField(name = "MsgBody")
    private List<MsgBody> msgBodyList;//消息内容，具体格式请参考 消息格式描述（注意，一条消息可包括多种消息元素，MsgBody 为 Array 类型）

    @JSONField(name = "OfflinePushInfo")
    private OfflinePushInfo offlinePushInfo;//离线推送信息配置，具体可参考 消息格式描述

    public List<String> getToAccountList() {
        return toAccountList;
    }

    public Integer getSyncOtherMachine() {
        return syncOtherMachine;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public Integer getMsgLifeTime() {
        return msgLifeTime;
    }

    public Integer getMsgTimeStamp() {
        return msgTimeStamp;
    }

    public List<MsgBody> getMsgBodyList() {
        return msgBodyList;
    }

    public OfflinePushInfo getOfflinePushInfo() {
        return offlinePushInfo;
    }

    public Integer getMsgRandom() {
        return msgRandom;
    }

    /**
     * 消息内容
     */
    public static class MsgBody{

        public MsgBody(){}

        public MsgBody(MsgBody msgBody){
            this.msgType = msgBody.msgType;
            this.msgContent = msgBody.msgContent;
        }

        @JSONField(name = "MsgType")
        private String msgType;//TIM 消息对象类型，目前支持的消息对象包括：TIMTextElem(文本消息)，TIMFaceElem(表情消息)，TIMLocationElem(位置消息)，TIMCustomElem(自定义消息)

        @JSONField(name = "MsgContent")
        private MsgContent msgContent;//对于每种 MsgType 用不同的 MsgContent 格式，具体可参考 消息格式描述

        public  enum  MsgType{
            TIMTEXTELEM("TIMTextElem"),//文本消息。
            TIMLOCATIONELEM("TIMLocationElem"),//地理位置消息。
            TIMFACEELEM("TIMFaceElem"),//表情消息
            TIMCUSTOMELEM("TIMCustomElem"),//自定义消息，当接收方为 iOS 系统且应用处在后台时，此消息类型可携带除文本以外的字段到 APNs。一条组合消息中只能包含一个 TIMCustomElem 自定义消息元素。
            ;

            private String msgType;

            public String getMsgType() {
                return msgType;
            }

            MsgType(String msgType) {
                this.msgType = msgType;
            }
        }

        /**
         * 消息内容
         */
        public static class MsgContent{

            public MsgContent(){}

            public MsgContent(MsgContent msgContent){
                this.text = msgContent.text;
                this.desc = msgContent.desc;
                this.latitude = msgContent.latitude;
                this.longitude = msgContent.longitude;
                this.index = msgContent.index;
                this.data = msgContent.data;
                this.ext = msgContent.ext;
                this.sound = msgContent.sound;
            }

            @JSONField(name = "Text")
            private String text;//消息内容。当接收方为 iOS 或 Android 后台在线时，作为离线推送的文本展示。

            @JSONField(name = "Desc")
            private String desc;// 地理位置描述信息。 自定义消息中：自定义消息描述信息；当接收方为 iOS 或 Android 后台在线时，做离线推送文本展示。

            @JSONField(name = "Latitude")
            private Number latitude;//纬度。

            @JSONField(name = "Longitude")
            private Number longitude;// 经度

            @JSONField(name = "Index")
            private Number index;//表情索引，用户自定义。

            @JSONField(name = "Data")
            private String data;// 额外数据。 自定义消息中：自定义消息数据。 不作为 APNs 的 payload 字段下发，故从 payload 中无法获取 Data 字段。

            @JSONField(name = "Ext")
            private String ext;// 扩展字段；当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。

            @JSONField(name = "Sound")
            private String sound;// 自定义 APNs 推送铃音。

            public String getText() {
                return text;
            }

            public String getDesc() {
                return desc;
            }

            public Number getLatitude() {
                return latitude;
            }

            public Number getLongitude() {
                return longitude;
            }

            public Number getIndex() {
                return index;
            }

            public String getData() {
                return data;
            }

            public String getExt() {
                return ext;
            }

            public String getSound() {
                return sound;
            }

            public static  class Builder{
                private MsgContent msgContent;
                public Builder(){
                    msgContent = new MsgContent();
                }

                public Builder addText(String text) {
                    msgContent.text = text;
                    return this;
                }

                public Builder addDesc(String desc) {
                    msgContent.desc = desc;
                    return this;
                }

                public Builder addLatitude(Number latitude) {
                    msgContent.latitude = latitude;
                    return this;
                }

                public Builder addLongitude(Number longitude) {
                    msgContent.longitude = longitude;
                    return this;
                }

                public Builder addIndex(Number index) {
                    msgContent.index = index;
                    return this;
                }

                public Builder addData(String data) {
                    msgContent.data = data;
                    return this;
                }

                public Builder addExt(String ext) {
                    msgContent.ext = ext;
                    return this;
                }

                public Builder addSound(String sound) {
                    msgContent.sound = sound;
                    return this;
                }

                public MsgContent build(){
                    return new MsgContent(msgContent);
                }
            }
        }


        public String getMsgType() {
            return msgType;
        }

        public MsgContent getMsgContent() {
            return msgContent;
        }

        public static class Builder{
            private MsgBody msgBody;
            public Builder(){
                msgBody = new MsgBody();
            }

            public Builder addMsgType(MsgType msgType){
                msgBody.msgType = msgType.getMsgType();
                return this;
            }

            public Builder addMsgContent(MsgContent msgContent){
                msgBody.msgContent = msgContent;
                return this;
            }

            public MsgBody build() throws IMToolsException {
                if(msgBody.msgType == null){
                    throw new IMToolsException("消息类型不存在！");
                }
                if(msgBody.msgContent == null){
                    throw new IMToolsException("消息内容MsgContent不存在！");
                }
                //文本消息验证
                if (msgBody.msgType.equals("TIMTextElem")){
                    if (msgBody.msgContent.text == null || msgBody.msgContent.text.equals("")){
                        throw new IMToolsException("消息内容Text不存在！");
                    }
                }
                //地理位置消息
                if (msgBody.msgType.equals("TIMLocationElem")){
                    if(msgBody.msgContent.desc == null || msgBody.msgContent.desc.equals("")){
                        throw new IMToolsException("地理位置描述信息不存在！");
                    }
                    if(msgBody.msgContent.latitude == null){
                        throw new IMToolsException("纬度不存在！");
                    }
                    if(msgBody.msgContent.longitude == null){
                        throw new IMToolsException("经度不存在！");
                    }
                }
                //表情消息
                if (msgBody.msgType.equals("TIMFaceElem")){
                    if(msgBody.msgContent.index == null ){
                        throw new IMToolsException("表情索引不存在！");
                    }
//                    if(msgBody.msgContent.data == null || msgBody.msgContent.data.equals("")){
//                        throw new IMToolsException("额外数据不存在！");
//                    }
                }
                //自定义消息
                if (msgBody.msgType.equals("TIMCustomElem")){
                    if(msgBody.msgContent.data == null || msgBody.msgContent.data.equals("")){
                        throw new IMToolsException("自定义消息数据不存在！");
                    }
                    if(msgBody.msgContent.desc == null || msgBody.msgContent.desc.equals("")){
                        throw new IMToolsException("自定义消息描述信息不存在！");
                    }
//                    if(msgBody.msgContent.ext == null || msgBody.msgContent.ext.equals("")){
//                        throw new IMToolsException("扩展字不存在！");
//                    }
//                    if(msgBody.msgContent.sound == null || msgBody.msgContent.sound.equals("")){
//                        throw new IMToolsException("自定义 APNs 推送铃音不存在！");
//                    }
                }
                return new MsgBody(msgBody);
            }
        }
    }


    /**
     * 推送消息
     */
    public static class OfflinePushInfo{

        @JSONField(name = "PushFlag")
        private Integer pushFlag;//0表示推送，1表示不离线推送。

        public static class AndroidInfo{

            @JSONField(name = "sound")
            private String sound;//Android 离线推送声音文件路径。
        }

    }

    public static class Builder{
        private MsgRequestBody msgRequestBody;

        public Builder(){
            msgRequestBody = new MsgRequestBody();
        }

        public Builder addSyncOtherMachine(Integer syncOtherMachine) {
            msgRequestBody.syncOtherMachine = syncOtherMachine;
            return this;
        }

        public Builder addFromAccount(String fromAccount) {
            msgRequestBody.fromAccount = fromAccount;
            return this;
        }

        public Builder addToAccount(String toAccount) {
            msgRequestBody.toAccount = toAccount;
            return this;
        }

        public Builder addMsgLifeTime(Integer msgLifeTime) {
            msgRequestBody.msgLifeTime = msgLifeTime;
            return this;
        }

        public Builder addMsgTimeStamp(Integer msgTimeStamp) {
            msgRequestBody.msgTimeStamp = msgTimeStamp;
            return this;
        }

        public Builder addMsgBodyList(List<MsgBody> msgBodyList) {
            msgRequestBody.msgBodyList = msgBodyList;
            return this;
        }

        public Builder addOfflinePushInfo(OfflinePushInfo offlinePushInfo) {
            msgRequestBody.offlinePushInfo = offlinePushInfo;
            return this;
        }

        public Builder addMsgRandom(Integer msgRandom) {
            msgRequestBody.msgRandom = msgRandom;
            return this;
        }

        public Builder addToAccountList(List<String> toAccountList) {
            msgRequestBody.toAccountList = toAccountList;
            return this;
        }

        public MsgRequestBody builder() throws IMToolsException {
            if(msgRequestBody.syncOtherMachine != null && !msgRequestBody.syncOtherMachine.equals(0)){
                if(msgRequestBody.fromAccount == null || msgRequestBody.fromAccount.equals("")){
                    throw new IMToolsException("当SyncOtherMachine存在时,FromAccount不允许为空！");
                }
            }
            if(msgRequestBody.toAccount != null && !msgRequestBody.toAccount.equals("")){
                if (msgRequestBody.toAccountList != null && !(msgRequestBody.toAccountList.size() <= 0)){
                    throw new IMToolsException("toAccount与toAccountList不能同时存在！");
                }
            }else if (msgRequestBody.toAccountList == null || msgRequestBody.toAccountList.size() <= 0){
                throw new IMToolsException("接收方不存在！");
            }
            if(msgRequestBody.msgRandom == null || msgRequestBody.msgRandom.equals(0)){
                msgRequestBody.msgRandom = Math.toIntExact(RandomValue.randomLong(7));
            }
            if(msgRequestBody.msgBodyList == null || msgRequestBody.msgBodyList.size() <= 0){
                throw new IMToolsException("消息内容列表不存在！");
            }
            return new MsgRequestBody(msgRequestBody);
        }
    }
}
