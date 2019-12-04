package com.lsj.demo.encrypt.bo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品对象
 *
 * @author admin
 * @update 2017/12/01 fujin 首单专区添加字段（isFirstOrderPrice，isSkuList）
 */
public class ItemBo{

    private boolean isNewItem; //true 新品
    private int shipmentsType; //预售
    private String depositText; //设置定金、可抵金额文本信息字段
    private double minCommission;//最小商品佣金值（普通商品、S级商品）
    private double maxCommission;//最大商品佣金值（普通商品、S级商品）
    private long finalPriceEndTime; // 	    尾款结束时间
    private long finalPriceStartTime; // 	尾款开始时间
    private double maxDiscountPrice; // 	可抵金额最大
    private double minDiscountPrice; // 	可抵金额最小
    private double maxDepositPrice; // 	    定金最大价格
    private double minDepositPrice; // 	    定金最小价格
    private double maxPrice;//最大价格
    private double minPrice;//最小价格
    private double actualPrice; //定金预售的实际价格

    private int firstOrderFlag; //不是新人首单  1-是新人首单"
    private double firstOrderMinPrice; //新人首单最低价 "
    private double firstOrderMaxPrice; //新人首单最高价 "
    private double firstOrderMinCommission; //新人首单最低佣金 "
    private double firstOrderMaxCommission; //新人首单最高佣金"

    private int tag; // 分区标记

    /**
     * DES: 排序值
     */
    private int sort;

    /**
     * DES: 1.表示置顶 0表示没有置顶
     */
    private int top;

    /**
     * DES: 是否推荐中
     */
    private int suggest;

    /**
     * DES: 我的推荐末尾标记
     */
    private int myRecommendTag;

    /**
     * DES: 商品访客头像组
     */
    private List<String> headImgs;
    /**
     * DES: 访客人数
     */
    private int visitCount;
    /**
     * DES: 坑位类型，1-专题，2-商详，3-外链
     */
    public int type;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getSuggest() {
        return suggest;
    }

    public void setSuggest(int isRecommend) {
        this.suggest = isRecommend;
    }


    /**
     * 打标
     */
    public boolean hasMarkFlag; //是否参与买赠活动( 1:有买赠活动 0:无买赠活动(不存在买赠活动或活动已结束))
    public boolean fullMinusFlag;//满减
    private boolean feedbackMark;//返券
    private String optional; // 任选 N元X件
    /**
     * DES :itemSource=0代表来自于人工干预的置顶商品，itemSource=1时，代表来自于大数据推荐
     */
    private int itemSource = -1;

    public int getItemSource() {
        return itemSource;
    }

    public void setItemSource(int itemSource) {
        this.itemSource = itemSource;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * 打标标识 0-不打标 1-打满减标 2-打立减标 3-打任选标，且返回markDescribe字段 4-打返券标 5-打买赠标
     * mark : 0
     */
    public int mark;

    /**
     * 商品打标类型 0-不打标 1-品质500 2-极致精选 3-超市 4-专柜品牌
     */
    private int markType = 0;

    /**
     * DES: 打标内容
     */
    private String markText;
    /**
     * 奖励标内容
     */
    private String rewardText;

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

    /**
     * DES: 是否为清仓 0：否，1：是
     */
    private int isClearGoods;

    /**
     * DES: 新人专区商品类型 0-不是新人专区商品 1-新人专享商品 2-新人首单商品
     */
    private int newBornItemType;

    /**
     * 打标
     */
    private String itemCategoryLevel1; // 一级类目,
    private String itemCategoryLevel2; // 二级类目,
    private String sellType; // '出售方式（一口价、拍卖等）
    private String itemType; // '商品类型（实物商品、虚拟商品）'
    private String itemMark;// 氛围标链接（99mall+时间轴）版本
    /**
     * DES: 引擎返回的ItemType,用于埋点上报
     */
    private String engineItemType;
    private int stock; // '总库存'
    private int isShowStock; // '页面中是否显示库存',
    private String itemName; // 商品名称
    private String subtitle; // 商品副标题
    private double itemPrice; // '价格',
    private double itemVipPrice; // '专柜价格',
    private String itemImg; // '店铺中对应商品主图',
    private String itemImgSmall; // '商品详情页使用的小图集合'
    private String itemImgBig; // '商品详情页使用的大图集合',
    private int itemPurchaseMax; // '每人限购数量（0表示不限购）',
    private int ifInvoice; // '有无发票',
    private int ifGuarantee; // '是否保修',
    private String itemDetail; // '商品详情'
    private int likeCount; // '点赞次数',
    private int commissionPoint; // '佣金点数',
    /**
     * 是否可用(0-是 1-否)
     */
    private int disabled; // '是否可用',
    private int soldNumber; // 销量
    private int isLike;
    private int sellPersons; // 多少人在卖
    private String recommend; // 推荐理由
    private int selected; // 是否被店铺上架1：已上架
    private List<String> bigImgList;// 商品主图url列表
    private int packageId;// 0表示普通商品，其它显示365商品


    public int getBrandCounterId() {
        return brandCounterId;
    }

    public void setBrandCounterId(int brandCounterId) {
        this.brandCounterId = brandCounterId;
    }

    private int brandCounterId;//专柜id

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    /**
     * 业务类型（0:常规销售商品 1:品牌专柜礼包 2:招募礼包 3:补差价(废弃) 4:旅游产品(废弃)
     * 5:优惠券(废弃) 6:续费礼包 7:数字专柜礼包 8:品牌专柜商品 9钻石专享 10:云集超市商品
     * 11:虚拟招募礼包 12:新人兑换礼包 13虚拟免费注册 14数字专柜大屏 15：自购礼包  16：新人专区）
     */
    private int packageType;
    private int textCount; //商品文案统计
    private int postion;//埋点使用的位置


    private int recommendStatus;//社群爆款主打状态

    public int getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(int recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    /**
     * 商品发货渠道 0-自营 1-代销 2-保税仓 3-保税自营 4-海外直邮
     * 5-香港直邮 6-包税海外购 7-个物直邮 (3，4，5，6都是全球精品)
     * 8-跨境贸易-阳光直邮-不包税 9-跨境贸易-阳光直邮-包税 10:美国-E特快直邮;
     * 11:美国-个人物品直邮；12：美国-阳光直邮-不包税 13：美国-阳光直邮-包税
     */
    private int itemChannel;
    /**
     * 行邮税率: 10、书报、刊物、教育专用电影片、幻灯片、原版录音带、录像带、金、银及其制品、食品、饮料（10%）
     * 20、纺织品及其制成品、摄像机、摄录一体机、数码相机及其他电器用具、照相机、自行车、手表、钟表配件及附件（20%）
     * 30、高尔夫球及球具、高档手表（30%） 50、烟、酒、化妆品（50%）
     */
    private int bankRate;

    /**
     * 总的库存数量
     */
    private int activityTotalStock;
    /**
     * 商品参数
     */
    private String itemParameters;
    /**
     * 购买须知
     */
    private String purchaseNotice;
    /**
     * 税金
     */
    private double taxPrice;
    /**
     * 税率提供给APP用 如：11.9%时传 11.9
     */
    private String strBankRate;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动发布时间
     */
    private long releaseTime;
    /**
     * 活动开始时间
     */
    private long startTime;
    /**
     * 活动结束时间
     */
    private long endTime;
    /**
     * 活动的商品详情状态(1--敬请期待;2--进行中;3--手慢了;4--已结束)
     */
    private int activityItemStatus;
    /**
     * 商品类别(0--普通商品;1--限时活动商品;2--拼团活动;3--定金预售;4--限时秒杀,5--折扣標)
     */
    private int itemCategory;
    /**
     * 系统当前时间
     */
    private long currentTime;
    /**
     * 求补货状态 (0-未求补货 1-已求补货 )
     */
    private int restockStatus;
    /**
     * 需求总数
     */
    private int restockTotal;
    /**
     * 总库存包括商品库存、限时活动锁定库存
     */
    private int totalStock;
    /**
     * 活动场Id
     */
    private int activityTimesId;
    /**
     * 0:自营 1：代销
     */
    private int manageType;

    private String mShowTimers;
    private double sCommission;//S级商品佣金点，绝对值
    private int fromView = 0;//来源 1 选品上架
    private String fineImg;//精修大图
    private String qrImg;// 二维码生成时显示的图片
    private int isFirstOrderPrice;//是否首单优惠价 默认0不是 1是

    private int isSkuList;//是否多sku默认0不是 1是
    private boolean isEditList = false;//是否编辑列表
    private boolean isSelectItem = false;//是否选中条目

    public boolean isSelectGrop() {
        return isSelectGrop;
    }

    public void setSelectGrop(boolean selectGrop) {
        isSelectGrop = selectGrop;
    }

    /**
     * DES: 是否选中组
     */
    private boolean isSelectGrop = false;
    private int favoriteItemTopstatus;//商品关注---0:不置顶 1:置顶

    // DES: 标题状态：0无标题、1店铺精选、2专属推荐
    private int isTitle = 0;
    /**
     * 用于处理本地蒙层资源Id, 默认是-1
     */
    private int localMaskResId = 0;

    /**
     * 是否是时间轴列表数据
     */
    private boolean isTimelist = false;
    /**
     * DES: sku 数量
     */
    private int skuNum = 0;
    /**
     * DES: 用于埋点上报
     */
    private String itemTrackData;
    /**
     * DES: 定金预售|特卖 预热时间展示文本
     */
    private String preheatDateShowText;
    /**
     * DES: 到货通知功能级开关1开0关，后端控制余下参数都不返回，前端控制不展示
     */
    private int openItemArrivalNotifySwitch;
    /**
     * DES: 是否展示到货通知人数1开0关
     */
    private int showItemArrivalNotifyCountSwitch;
    /**
     * DES: 到货通知按钮文本
     */
    private String arrivalNotifyButtonText;
    /**
     * DES: 点击后到货通知按钮文本
     */
    private String appliedArrivalNotifyButtonText;
    /**
     * DES: 点击后弹框提示语
     */
    private String clickArrivalNotifyButtonDialogInfo;
    /**
     * DES: 点击求关注弹窗配置信息
     */
    private String clickArrivalNotifyButtonDialogFavoriteInfo;
    /**
     * DES: 检查权限通知内容提示
     */
    private String checkAppNotifyPermissionDialogInfo;
    /**
     * DES: 1以求补货0没点击到货通知
     */
    private int notifyStatus;
    /**
     * DES: 多少人求补货
     */
    private String arrivalNotifyCountDesc;
    /**
     * DES: 店铺名
     */
    private String storeName;
    /**
     * DES: 店铺编码
     */
    private String storeCode;
    /**
     * 单位为天
     */
    private int historyLowPrice;
    /**
     * 商品标签
     */
    private String itemTag;
    /**
     * 已售百分数
     */
    private int soldPercent;
    /**
     * 已售XX件
     */
    private int soldStock;
    /**
     * 月销件数(String),后台处理好返回
     */
    private String monthSold;
    /**
     * 是否展示进度条 1是0否
     */
    private int showProgressBar;
    /**
     * DES: 月销数量
     */
    private String salesMonthAmount;
    /**
     * DES: 评论文案
     */
    private String conmentDes;
    /**
     * DES: 好评率文案
     */
    private String probabilityDes;
    /**
     * DES: 排行榜名字
     */
    private String listName;
    /**
     * DES: 排行榜第几名
     */
    private int listRank;
    /**
     * DES: 拼团销售数量
     */
    private int groupbuyNum;
    /**
     * DES: 拼团人数
     */
    private int groupNum;
    /**
     * DES: 拼团数量
     */
    private String groupbuyNumText;

    //DES:商品标签类型 1普通商品 2今日爆款
    private int tagType;
    //DES:99mall图片类型 1 大图 2 横图
    private int mallImgType;
    //DES:99mall大图
    private String mallImg;
    //DES:压缩后的99mall大图
    private String mallImgCompress;
    //DES:压缩后的99mall小图
    private String itemImgSmallCompress;
    //DES:已售XX件 字符串
    private String soldStockStr;
    //DES:99Mall明日预告围观热度
    private int pv;
    //推荐指数
    private double recommendStar;

    private boolean isFromRecruitPackage;//是否来自招募礼包
    //DES: 商品新品标签 0 普通 1新品 2超级新品 3今日爆款
    private int topItemType;

    public int getTopItemType() {
        return topItemType;
    }

    public void setTopItemType(int topItemType) {
        this.topItemType = topItemType;
    }

    public void setIsFromRecruitPackage(boolean isFromRecruitPackage) {
        this.isFromRecruitPackage = isFromRecruitPackage;
    }


    public boolean getIsFromRecruitPackage() {
        return isFromRecruitPackage;
    }

    public double getRecommendStar() {
        return recommendStar;
    }

    public void setRecommendStar(double recommendStar) {
        this.recommendStar = recommendStar;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getSoldStockStr() {
        return soldStockStr;
    }

    public void setSoldStockStr(String soldStockStr) {
        this.soldStockStr = soldStockStr;
    }

    public String getMallImg() {
        return mallImg;
    }

    public void setMallImg(String mallImg) {
        this.mallImg = mallImg;
    }

    public String getMallImgCompress() {
        return mallImgCompress;
    }

    public void setMallImgCompress(String mallImgCompress) {
        this.mallImgCompress = mallImgCompress;
    }

    public String getGroupbuyNumText() {
        return groupbuyNumText;
    }

    public void setGroupbuyNumText(String groupbuyNumText) {
        this.groupbuyNumText = groupbuyNumText;
    }

    public String getConmentDes() {
        return conmentDes;
    }

    public void setConmentDes(String conmentDes) {
        this.conmentDes = conmentDes;
    }

    public String getProbabilityDes() {
        return probabilityDes;
    }

    public void setProbabilityDes(String probabilityDes) {
        this.probabilityDes = probabilityDes;
    }

    public int getGroupbuyNum() {
        return groupbuyNum;
    }

    public void setGroupbuyNum(int groupbuyNum) {
        this.groupbuyNum = groupbuyNum;
    }

    public int getGroupCount() {
        return groupNum;
    }

    public void setGroupCount(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getItemImgSmallCompress() {
        return itemImgSmallCompress;
    }

    public void setItemImgSmallCompress(String itemImgSmallCompress) {
        this.itemImgSmallCompress = itemImgSmallCompress;
    }

    public int getMallImgType() {
        return mallImgType;
    }

    public void setMallImgType(int mallImgType) {
        this.mallImgType = mallImgType;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public int getShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(int showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public String getMonthSold() {
        return monthSold;
    }

    public void setMonthSold(String monthSold) {
        this.monthSold = monthSold;
    }

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public int getSoldPercent() {
        return soldPercent;
    }

    public void setSoldPercent(int soldPercent) {
        this.soldPercent = soldPercent;
    }

    public int getSoldStock() {
        return soldStock;
    }

    public void setSoldStock(int soldStock) {
        this.soldStock = soldStock;
    }

    public int getHistoryLowPrice() {
        return historyLowPrice;
    }

    public void setHistoryLowPrice(int historyLowPrice) {
        this.historyLowPrice = historyLowPrice;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSalesMonthAmount() {
        return salesMonthAmount;
    }

    public void setSalesMonthAmount(String salesMonthAmount) {
        this.salesMonthAmount = salesMonthAmount;
    }

    /**
     * DES: 分组id，以日期组成
     */
    private int groupId;

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public boolean isTimelist() {
        return isTimelist;
    }

    public void setTimelist(boolean timelist) {
        isTimelist = timelist;
    }

    public int getManageType() {
        return manageType;
    }

    public void setManageType(int manageType) {
        this.manageType = manageType;
    }

    private boolean isClickRemind = false;

    public boolean isClickRemind() {
        return isClickRemind;
    }

    public void setClickRemind(boolean clickRemind) {
        isClickRemind = clickRemind;
    }

    public int getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(int isTitle) {
        this.isTitle = isTitle;
    }

    public int getFavoriteItemTopstatus() {
        return favoriteItemTopstatus;
    }

    public void setFavoriteItemTopstatus(int favoriteItemTopstatus) {
        this.favoriteItemTopstatus = favoriteItemTopstatus;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getListRank() {
        return listRank;
    }

    public void setListRank(int listRank) {
        this.listRank = listRank;
    }

    public boolean isEditList() {
        return isEditList;
    }

    public void setEditList(boolean editList) {
        isEditList = editList;
    }

    public boolean isSelectItem() {
        return isSelectItem;
    }

    public void setSelectItem(boolean selectItem) {
        isSelectItem = selectItem;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDepositText() {
        return depositText;
    }

    public void setDepositText(String depositText) {
        this.depositText = depositText;
    }

    public int getTextCount() {
        return textCount;
    }

    public void setTextCount(int textCount) {
        this.textCount = textCount;
    }

    public String getFineImg() {
        return fineImg;
    }

    public int getFromView() {
        return fromView;
    }

    public void setFromView(int fromView) {
        this.fromView = fromView;
    }

    public void setMinCommission(double minCommission) {
        this.minCommission = minCommission;
    }

    public double getMaxCommission() {
        return maxCommission;
    }

    public void setMaxCommission(double maxCommission) {
        this.maxCommission = maxCommission;
    }

    public boolean isNewItem() {
        return isNewItem;
    }

    public void setNewItem(boolean newItem) {
        isNewItem = newItem;
    }

    public int getShipmentsType() {
        return shipmentsType;
    }

    public void setShipmentsType(int shipmentsType) {
        this.shipmentsType = shipmentsType;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public long getFinalPriceEndTime() {
        return finalPriceEndTime;
    }

    public void setFinalPriceEndTime(long finalPriceEndTime) {
        this.finalPriceEndTime = finalPriceEndTime;
    }

    public long getFinalPriceStartTime() {
        return finalPriceStartTime;
    }

    public void setFinalPriceStartTime(long finalPriceStartTime) {
        this.finalPriceStartTime = finalPriceStartTime;
    }

    public double getMaxDiscountPrice() {
        return maxDiscountPrice;
    }

    public void setMaxDiscountPrice(double maxDiscountPrice) {
        this.maxDiscountPrice = maxDiscountPrice;
    }

    public double getMinDiscountPrice() {
        return minDiscountPrice;
    }

    public void setMinDiscountPrice(double minDiscountPrice) {
        this.minDiscountPrice = minDiscountPrice;
    }

    public double getMaxDepositPrice() {
        return maxDepositPrice;
    }

    public void setMaxDepositPrice(double maxDepositPrice) {
        this.maxDepositPrice = maxDepositPrice;
    }

    public int getMarkType() {
        return markType;
    }

    public void setMarkType(int markType) {
        this.markType = markType;
    }

    public void setMinDepositPrice(double minDepositPrice) {
        this.minDepositPrice = minDepositPrice;
    }

    public boolean isFeedbackMark() {
        return feedbackMark;
    }

    public void setFeedbackMark(boolean a) {
        if (!feedbackMark) {
            feedbackMark = a;
        }
    }

    public double getsCommission() {
        return sCommission;
    }

    public void setsCommission(double sCommission) {
        this.sCommission = sCommission;
    }

    public int getActivityTimesId() {
        return activityTimesId;
    }

    public void setActivityTimesId(int activityTimesId) {
        this.activityTimesId = activityTimesId;
    }

    public void setmShowTimers(String mShowTimers) {
        this.mShowTimers = mShowTimers;
    }

    /**
     * 求补货状态 (0-未求补货 1-已求补货 )
     */
    public int getRestockStatus() {
        return restockStatus;
    }

    public void setRestockStatus(int restockStatus) {
        this.restockStatus = restockStatus;
    }

    public int getRestockTotal() {
        return restockTotal;
    }

    public void setRestockTotal(int restockTotal) {
        this.restockTotal = restockTotal;
    }

    public void setRestockTotal(Integer restockTotal) {
        this.restockTotal = restockTotal;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getStrBankRate() {
        return strBankRate;
    }

    public void setStrBankRate(String strBankRate) {
        this.strBankRate = strBankRate;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * 活动的商品详情状态(1--敬请期待;2--进行中;3--手慢了;4--已结束)
     */
    public int getActivityItemStatus() {
        return activityItemStatus;
    }

    public void setActivityItemStatus(int activityItemStatus) {
        this.activityItemStatus = activityItemStatus;
    }

    public void setItemCategory(int itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getPurchaseNotice() {
        return purchaseNotice;
    }

    public String getItemParameters() {
        return itemParameters;
    }

    public int getBankRate() {
        return bankRate;
    }

    public int getItemChannel() {
        return itemChannel;
    }

    public void setItemChannel(int itemChannel) {
        this.itemChannel = itemChannel;
    }

    public int getPackageId() {
        return packageId;
    }

    public List<String> getBigImgList() {
        if (bigImgList == null) {
            bigImgList = new ArrayList<>();
        }
        return bigImgList;
    }

    public void setBigImgList(List<String> bigImgList) {
        this.bigImgList = bigImgList;
    }

    public String getItemCategoryLevel1() {
        return itemCategoryLevel1;
    }

    public void setItemCategoryLevel1(String itemCategoryLevel1) {
        this.itemCategoryLevel1 = itemCategoryLevel1;
    }

    public String getItemCategoryLevel2() {
        return itemCategoryLevel2;
    }

    public void setItemCategoryLevel2(String itemCategoryLevel2) {
        this.itemCategoryLevel2 = itemCategoryLevel2;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    /**
     * 商品类型（0-普通商品, 1-S级别商品,2-钻石专享商品）
     */
    public String getItemType() {
        return itemType;
    }

    public String getEngineItemType() {
        return engineItemType;
    }

    public void setEngineItemType(String engineItemType) {
        this.engineItemType = engineItemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIsShowStock() {
        return isShowStock;
    }

    public void setIsShowStock(int isShowStock) {
        this.isShowStock = isShowStock;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemImgSmall() {
        return itemImgSmall;
    }

    public void setItemImgSmall(String itemImgSmall) {
        this.itemImgSmall = itemImgSmall;
    }

    public String getItemImgBig() {
        return itemImgBig;
    }

    public void setItemImgBig(String itemImgBig) {
        this.itemImgBig = itemImgBig;
    }

    public int getItemPurchaseMax() {
        return itemPurchaseMax;
    }

    public void setItemPurchaseMax(int itemPurchaseMax) {
        this.itemPurchaseMax = itemPurchaseMax;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommissionPoint() {
        return commissionPoint;
    }

    public void setCommissionPoint(int commissionPoint) {
        this.commissionPoint = commissionPoint;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getSellPersons() {
        return sellPersons;
    }

    public void setSellPersons(int sellPersons) {
        this.sellPersons = sellPersons;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public int getIfInvoice() {
        return ifInvoice;
    }

    public void setIfInvoice(int ifInvoice) {
        this.ifInvoice = ifInvoice;
    }

    public int getIfGuarantee() {
        return ifGuarantee;
    }

    public void setIfGuarantee(int ifGuarantee) {
        this.ifGuarantee = ifGuarantee;
    }

    /**
     * 是否可用(0-是 1-否)
     */
    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public void setItemVipPrice(double itemVipPrice) {
        this.itemVipPrice = itemVipPrice;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getQrImg() {
        return qrImg;
    }

    public void setQrImg(String qrImg) {
        this.qrImg = qrImg;
    }

    //是否首单优惠价
    public boolean isFirstOrderPrice() {
        return isFirstOrderPrice == 1;
    }

    //是否是多sku
    public boolean isSkuList() {
        return isSkuList == 1;
    }

    /**
     * 是否是全球精品商品
     */
    public boolean isGlobalOnline() {
        if (itemChannel == 3 || itemChannel == 4 || itemChannel == 5
                || itemChannel == 6) {
            return true;
        }
        return false;
    }

    public void setMarkText(String markText) {
        this.markText = markText;
    }

    public String getMarkText() {
        return markText;
    }

    public int getLocalMaskResId() {
        return localMaskResId;
    }

    public void setLocalMaskResId(int localMaskResId) {
        this.localMaskResId = localMaskResId;
    }

    public List<String> getHeadImgs() {
        if (headImgs == null) {
            return new ArrayList<>();
        }
        return headImgs;
    }

    public int getVisitCount() {
        return visitCount;
    }

    @Override
    public String toString() {
        return "ItemBo{" +
                "finalPriceEndTime=" + finalPriceEndTime +
                ", finalPriceStartTime=" + finalPriceStartTime +
                ", maxDiscountPrice=" + maxDiscountPrice +
                ", minDiscountPrice=" + minDiscountPrice +
                ", maxDepositPrice=" + maxDepositPrice +
                ", minDepositPrice=" + minDepositPrice +
                ", hasMarkFlag=" + hasMarkFlag +
                ", fullMinusFlag=" + fullMinusFlag +
                ", itemCategoryLevel1='" + itemCategoryLevel1 + '\'' +
                ", itemCategoryLevel2='" + itemCategoryLevel2 + '\'' +
                ", sellType='" + sellType + '\'' +
                ", itemType='" + itemType + '\'' +
                ", stock=" + stock +
                ", isShowStock=" + isShowStock +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemVipPrice=" + itemVipPrice +
                ", itemImg='" + itemImg + '\'' +
                ", itemImgSmall='" + itemImgSmall + '\'' +
                ", itemImgBig='" + itemImgBig + '\'' +
                ", itemPurchaseMax=" + itemPurchaseMax +
                ", ifInvoice=" + ifInvoice +
                ", ifGuarantee=" + ifGuarantee +
                ", itemDetail='" + itemDetail + '\'' +
                ", likeCount=" + likeCount +
                ", commissionPoint=" + commissionPoint +
                ", disabled=" + disabled +
                ", soldNumber=" + soldNumber +
                ", isLike=" + isLike +
                ", sellPersons=" + sellPersons +
                ", recommend='" + recommend + '\'' +
                ", selected=" + selected +
                ", bigImgList=" + bigImgList +
                ", packageId=" + packageId +
                ", textCount=" + textCount +
                ", itemChannel=" + itemChannel +
                ", bankRate=" + bankRate +
                ", itemParameters='" + itemParameters + '\'' +
                ", purchaseNotice='" + purchaseNotice + '\'' +
                ", taxPrice=" + taxPrice +
                ", strBankRate='" + strBankRate + '\'' +
                ", activityName='" + activityName + '\'' +
                ", releaseTime=" + releaseTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityItemStatus=" + activityItemStatus +
                ", itemCategory=" + itemCategory +
                ", currentTime=" + currentTime +
                ", restockStatus=" + restockStatus +
                ", restockTotal=" + restockTotal +
                ", totalStock=" + totalStock +
                ", activityTimesId=" + activityTimesId +
                ", mShowTimers='" + mShowTimers + '\'' +
                ", sCommission=" + sCommission +
                ", fromView=" + fromView +
                ", fineImg='" + fineImg + '\'' +
                ", qrImg='" + qrImg + '\'' +
                ", optional=" + optional +
                '}';
    }

    public void setHasMarkFlag(boolean b) {
        if (!hasMarkFlag) {
            hasMarkFlag = b;
        }
    }

    public boolean getFullMinusFlag() {
        return fullMinusFlag;
    }

    public void setFullMinusFlag(boolean b) {
        if (!fullMinusFlag) {
            fullMinusFlag = b;
        }
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setHeadImgs(List<String> headImgs) {
        this.headImgs = headImgs;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public boolean getMarkFlag() {
        return hasMarkFlag;
    }

    /**
     * 是否是自营isSelfSupport 为 false表示pop商品
     */
    private boolean isSelfSupport;

    public boolean isSelfSupport() {
        return isSelfSupport;
    }

    public void setSelfSupport(boolean selfSupport) {
        isSelfSupport = selfSupport;
    }

    /**
     * N元X件
     */
    public boolean isOptional() {
        return !TextUtils.isEmpty(optional);
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String op) {
        if (TextUtils.isEmpty(this.optional)) {
            this.optional = op;
        }
    }

    public ItemBo() {
        super();
    }

    public String getItemMark() {
        return itemMark;
    }

    public void setItemMark(String itemMark) {
        this.itemMark = itemMark;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getActivityTotalStock() {
        return activityTotalStock;
    }

    public void setActivityTotalStock(int activityTotalStock) {
        this.activityTotalStock = activityTotalStock;
    }

    public int getFirstOrderFlag() {
        return firstOrderFlag;
    }

    public void setFirstOrderFlag(int firstOrderFlag) {
        this.firstOrderFlag = firstOrderFlag;
    }

    public double getFirstOrderMinPrice() {
        return firstOrderMinPrice;
    }

    public void setFirstOrderMinPrice(double firstOrderMinPrice) {
        this.firstOrderMinPrice = firstOrderMinPrice;
    }

    public double getFirstOrderMaxPrice() {
        return firstOrderMaxPrice;
    }

    public void setFirstOrderMaxPrice(double firstOrderMaxPrice) {
        this.firstOrderMaxPrice = firstOrderMaxPrice;
    }

    public double getFirstOrderMinCommission() {
        return firstOrderMinCommission;
    }

    public void setFirstOrderMinCommission(double firstOrderMinCommission) {
        this.firstOrderMinCommission = firstOrderMinCommission;
    }

    public double getFirstOrderMaxCommission() {
        return firstOrderMaxCommission;
    }

    public void setFirstOrderMaxCommission(double firstOrderMaxCommission) {
        this.firstOrderMaxCommission = firstOrderMaxCommission;
    }

    public boolean isHasMarkFlag() {
        return hasMarkFlag;
    }

    public boolean isFullMinusFlag() {
        return fullMinusFlag;
    }

    public int getIsClearGoods() {
        return isClearGoods;
    }

    public void setIsClearGoods(int isClearGoods) {
        this.isClearGoods = isClearGoods;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setBankRate(int bankRate) {
        this.bankRate = bankRate;
    }

    public void setItemParameters(String itemParameters) {
        this.itemParameters = itemParameters;
    }

    public void setPurchaseNotice(String purchaseNotice) {
        this.purchaseNotice = purchaseNotice;
    }

    public String getmShowTimers() {
        return mShowTimers;
    }

    public void setFineImg(String fineImg) {
        this.fineImg = fineImg;
    }

    public int getIsFirstOrderPrice() {
        return isFirstOrderPrice;
    }

    public void setIsFirstOrderPrice(int isFirstOrderPrice) {
        this.isFirstOrderPrice = isFirstOrderPrice;
    }

    public int getIsSkuList() {
        return isSkuList;
    }

    public void setIsSkuList(int isSkuList) {
        this.isSkuList = isSkuList;
    }

    public int getMyRecommendTag() {
        return myRecommendTag;
    }

    public void setMyRecommendTag(int myRecommendTag) {
        this.myRecommendTag = myRecommendTag;
    }

    /**
     * 判断是否需要分享, 过滤类型：14数字专柜大屏 、15自购礼包，16新人专区
     *
     * @return true表示需要分享，否则不需要分享
     */
    public boolean isNeedShare() {
        return !(packageType == 14 || packageType == 15 || packageType == 16);
    }

    public String getItemTrackData() {
        return itemTrackData;
    }

    public void setItemTrackData(String itemTrackData) {
        this.itemTrackData = itemTrackData;
    }

    public String getPreheatDateShowText() {
        return preheatDateShowText;
    }

    public void setPreheatDateShowText(String preheatDateShowText) {
        this.preheatDateShowText = preheatDateShowText;
    }

    public int getOpenItemArrivalNotifySwitch() {
        return openItemArrivalNotifySwitch;
    }

    public void setOpenItemArrivalNotifySwitch(int openItemArrivalNotifySwitch) {
        this.openItemArrivalNotifySwitch = openItemArrivalNotifySwitch;
    }

    public int getShowItemArrivalNotifyCountSwitch() {
        return showItemArrivalNotifyCountSwitch;
    }

    public void setShowItemArrivalNotifyCountSwitch(int showItemArrivalNotifyCountSwitch) {
        this.showItemArrivalNotifyCountSwitch = showItemArrivalNotifyCountSwitch;
    }

    public String getArrivalNotifyButtonText() {
        return arrivalNotifyButtonText;
    }

    public void setArrivalNotifyButtonText(String arrivalNotifyButtonText) {
        this.arrivalNotifyButtonText = arrivalNotifyButtonText;
    }

    public int getSort() {
        return sort;
    }

    public String getAppliedArrivalNotifyButtonText() {
        return appliedArrivalNotifyButtonText;
    }

    public void setAppliedArrivalNotifyButtonText(String appliedArrivalNotifyButtonText) {
        this.appliedArrivalNotifyButtonText = appliedArrivalNotifyButtonText;
    }

    public String getClickArrivalNotifyButtonDialogInfo() {
        return clickArrivalNotifyButtonDialogInfo;
    }

    public void setClickArrivalNotifyButtonDialogInfo(String clickArrivalNotifyButtonDialogInfo) {
        this.clickArrivalNotifyButtonDialogInfo = clickArrivalNotifyButtonDialogInfo;
    }

    public String getClickArrivalNotifyButtonDialogFavoriteInfo() {
        return clickArrivalNotifyButtonDialogFavoriteInfo;
    }

    public void setClickArrivalNotifyButtonDialogFavoriteInfo(String clickArrivalNotifyButtonDialogFavoriteInfo) {
        this.clickArrivalNotifyButtonDialogFavoriteInfo = clickArrivalNotifyButtonDialogFavoriteInfo;
    }

    public int getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(int notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getArrivalNotifyCountDesc() {
        return arrivalNotifyCountDesc;
    }

    public void setArrivalNotifyCountDesc(String arrivalNotifyCount) {
        this.arrivalNotifyCountDesc = arrivalNotifyCount;
    }

    public String getCheckAppNotifyPermissionDialogInfo() {
        return checkAppNotifyPermissionDialogInfo;
    }

    public void setCheckAppNotifyPermissionDialogInfo(String checkAppNotifyPermissionDialogInfo) {
        this.checkAppNotifyPermissionDialogInfo = checkAppNotifyPermissionDialogInfo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
