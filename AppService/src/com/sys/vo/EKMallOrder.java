package com.sys.vo;

/**
 * Created by Maryn on 2016/7/7.
 *
 * 商城订单
 */
public class EKMallOrder {

    private String id;// 订单id
    private String trade_no;// 订单号
    private String score_cost;// 花费积分
    private String ctime;// 订单创建时间
    private String pay_status;// 支付状态：0.未支付，1.支付，2.支付失败 3、交易关闭 4、定时关闭交易
    private String pay_type;// 支付类型：1.微信支付，2.支付宝，3.积分兑换
    private String recv_name;// 收货人名称
    private String recv_phone;// 收货人电话
    private String recv_addr;// 收货人地址
    private String express_code;// 物流code
    private String type;// 商品类型 1：虚拟  2 ：实物 3 : 手机膜
    private String uid;// 用户id
    private String pid;// 商品id
    private String amount;// 购买的数量
    private String carriage;// 运费
    private String pname;// 手机名称
    private String pcode;// 手机型号

    public static final String SELLER_MEMO = "积分商城兑换手机膜";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getScore_cost() {
        return score_cost;
    }

    public void setScore_cost(String score_cost) {
        this.score_cost = score_cost;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRecv_name() {
        return recv_name;
    }

    public void setRecv_name(String recv_name) {
        this.recv_name = recv_name;
    }

    public String getRecv_phone() {
        return recv_phone;
    }

    public void setRecv_phone(String recv_phone) {
        this.recv_phone = recv_phone;
    }

    public String getRecv_addr() {
        return recv_addr;
    }

    public void setRecv_addr(String recv_addr) {
        this.recv_addr = recv_addr;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
