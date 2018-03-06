package com.cdkj.myxb.module.api;

import com.cdkj.baselibrary.api.BaseResponseListModel;
import com.cdkj.baselibrary.api.BaseResponseModel;
import com.cdkj.baselibrary.api.ResponseInListModel;
import com.cdkj.baselibrary.model.CodeModel;
import com.cdkj.baselibrary.model.IntroductionDkeyModel;
import com.cdkj.myxb.models.AccountDetailsModel;
import com.cdkj.myxb.models.AccountListModel;
import com.cdkj.myxb.models.AddressModel;
import com.cdkj.myxb.models.AdviceListModel;
import com.cdkj.myxb.models.AdviceScoreModel;
import com.cdkj.myxb.models.AppointmentListModel;
import com.cdkj.myxb.models.BrandListModel;
import com.cdkj.myxb.models.BrandProductModel;
import com.cdkj.myxb.models.ClassStyleModel;
import com.cdkj.myxb.models.CommentCountAndAverage;
import com.cdkj.myxb.models.CommentItemModel;
import com.cdkj.myxb.models.CommentListMode;
import com.cdkj.myxb.models.CommentTagModel;
import com.cdkj.myxb.models.ExpertRankListModel;
import com.cdkj.myxb.models.FirstPageBanner;
import com.cdkj.myxb.models.HappyMsgModel;
import com.cdkj.myxb.models.IntegraProductDetailsModel;
import com.cdkj.myxb.models.IntegralListModel;
import com.cdkj.myxb.models.IntegralModel;
import com.cdkj.myxb.models.IntegralOrderListModel;
import com.cdkj.myxb.models.InvitationModel;
import com.cdkj.myxb.models.LogoListModel;
import com.cdkj.myxb.models.MouthAppointmentModel;
import com.cdkj.myxb.models.MsgListModel;
import com.cdkj.myxb.models.OrderListModel;
import com.cdkj.myxb.models.RankModel;
import com.cdkj.myxb.models.TripListModel;
import com.cdkj.myxb.models.UpdateUserInfo;
import com.cdkj.myxb.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cdkj on 2018/2/22.
 */

public interface MyApiServer {

    /**
     * 根据ckey查询系统参数
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<IntroductionDkeyModel>> getdKeyListInfo(@Field("code") String code, @Field("json") String json);
    /**
     * 获取邀请信息
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<InvitationModel>> getInvitationInfo(@Field("code") String code, @Field("json") String json);


    /**
     * 获取建议分数
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<AdviceScoreModel>> getAdviceScore(@Field("code") String code, @Field("json") String json);


    /**
     * 获取喜报详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<HappyMsgModel>> getHappyMsgDetail(@Field("code") String code, @Field("json") String json);


    /**
     * 获取品牌 美容院 专家排名
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<RankModel>> getRankList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取喜报列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<HappyMsgModel>>> getHappyMsgList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取首页广告
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<FirstPageBanner>> getFirstBanner(@Field("code") String code, @Field("json") String json);


    /**
     * 获取授课风格列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<LogoListModel>>> getLogoList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取授课风格列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UpdateUserInfo>> getUpdateUserInfo(@Field("code") String code, @Field("json") String json);


    /**
     * 获取授课风格列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<ClassStyleModel>> getClassStyleList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取专家排名列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<ExpertRankListModel>>> getExpertRankList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取行程列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<TripListModel>>> getTripList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取订单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<CommentTagModel>> getCommentTag(@Field("code") String code, @Field("json") String json);

    /**
     * 获取预定列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<MouthAppointmentModel>> getMouthAppointment(@Field("code") String code, @Field("json") String json);


    /**
     * 获取预定列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<AppointmentListModel>>> getAppointmentList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取建议列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<AdviceListModel>>> getAdviceList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取评价列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<CommentListMode>>> getCommentList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取用户列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<UserModel>>> getUserList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取消息列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<MsgListModel>> getMsgList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取评价总数和平均分
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CommentCountAndAverage>> getCommentCountAndAverage(@Field("code") String code, @Field("json") String json);


    /**
     * 获取要评价的项
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<CommentItemModel>> getCommentItem(@Field("code") String code, @Field("json") String json);

    /**
     * 获取预约详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<AppointmentListModel>> getAppointmentDetails(@Field("code") String code, @Field("json") String json);

    /**
     * 获取订单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<OrderListModel>> getOrderDetails(@Field("code") String code, @Field("json") String json);

    /**
     * 获取订单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<OrderListModel>>> getOrderList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取品牌产品详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<BrandProductModel>> getBrandProductDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 获取品牌产品列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<BrandProductModel>>> getBrandProductList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取品牌
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<BrandListModel>> getBrandList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取品牌
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<BrandListModel>>> getSpeBrandList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取积分订单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<IntegralOrderListModel>> getIntegralOrderDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 获取积分订单
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<IntegralOrderListModel>>> getIntegralOrderList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取积分流水
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<IntegralListModel>>> getIntegralList(@Field("code") String code, @Field("json") String json);


    /**
     * 获取积分
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<AccountDetailsModel>> getAccountDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 获取账户列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<AccountListModel>> getAccountList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取积分商品列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<ResponseInListModel<IntegralModel>>> getIntegralProductList(@Field("code") String code, @Field("json") String json);

    /**
     * 获取积分商品详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<IntegraProductDetailsModel>> getIntegralProduct(@Field("code") String code, @Field("json") String json);


    /**
     * 获取用户信息详情
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<UserModel>> getUserInfoDetails(@Field("code") String code, @Field("json") String json);


    /**
     * 添加收货地址
     * AddAddress
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<CodeModel>> AddAddress(@Field("code") String code, @Field("json") String json);

    /**
     * 获取地址列表
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseListModel<AddressModel>> getAddress(@Field("code") String code, @Field("json") String json);

    /**
     * 设置默认地址
     *
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Call<BaseResponseModel<Boolean>> setDefultAddress(@Field("code") String code, @Field("json") String json);


}
