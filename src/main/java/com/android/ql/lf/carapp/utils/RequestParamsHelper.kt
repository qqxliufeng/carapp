package com.android.ql.lf.carapp.utils

import android.text.TextUtils
import com.android.ql.lf.carapp.data.UserInfo

/**
 * Created by lf on 2017/11/13 0013.
 * @author lf on 2017/11/13 0013
 */
class RequestParamsHelper {

    companion object {

        private fun getBaseParams(): ApiParams {
            val params = ApiParams()
            params.addParam("pt", "android")
            return params
        }

        private fun getWithIdParams(): ApiParams {
            val params = getBaseParams()
            params.addParam("uid", UserInfo.getInstance().memberId)
            return params
        }

        private fun getWithPageParams(page: Int, pageSize: Int = 10): ApiParams {
            val param = if (UserInfo.getInstance().isLogin) {
                getWithIdParams()
            } else {
                getBaseParams()
            }
            param.addParam("page", page)
            param.addParam("pagesize", pageSize)
            return param
        }

        /**              login model  start           **/
        val LOGIN_MODEL = "login"
        val ACT_REGISTER = "Register"
        val ACT_CODE = "getcode"
        val ACT_LOGIN = "Login"
        val ACT_FORGETPW = "forgetpw"
        fun getCodeParams(tel: String = ""): ApiParams {
            val params = getBaseParams()
            return params.addParam("tel", tel)
        }

        fun getRegisterParams(tel: String = "", pw: String = ""): ApiParams {
            val params = getBaseParams()
            params.addParam("tel", tel).addParam("pw", pw)
            return params
        }

        fun getLoginParams(tel: String = "", pw: String = ""): ApiParams {
            val params = getBaseParams()
            params.addParam("tel", tel).addParam("pw", pw).addParam("stoken", "1")
            return params
        }

        fun getForgetPWParams(tel: String = "", pw: String = ""): ApiParams {
            val params = getBaseParams()
            params.addParam("tel", tel).addParam("pw", pw)
            return params
        }


        val ACT_QQLOGIN = "qqlogin"
        fun getQQloginParam(phone: String, openid: String, accessToken: String) =
                getBaseParams().addParam("phone", phone).addParam("openid", openid).addParam("access_token", accessToken)

        val ACT_WX_PERFECT = "wx_perfect"
        fun getWXCompleteDataParam(phone: String, headimgurl: String, openid: String, nickname: String): ApiParams {
            val params = getBaseParams()
            params.addParam("phone", phone)
            params.addParam("headimgurl", headimgurl)
            params.addParam("openid", openid)
            params.addParam("nickname", nickname)
            return params
        }
        /**              login model  end           **/


        /**              member model  start           **/

        val MEMBER_MODEL = "member"
        val ACT_EDIT_PW = "edit_pw"
        fun getEditPWParams(pw: String, newpw: String): ApiParams {
            val param = getWithIdParams()
            return param.addParam("pw", pw).addParam("newpw", newpw)
        }

        val ACT_EDIT_PERSONAL = "edit_personal"
        fun getEditPersonalParam(account: String = "", idcard: String = ""): ApiParams {
            val params = getWithIdParams()
            if (!TextUtils.isEmpty(account)) {
                params.addParam("account", account)
            }
            if (!TextUtils.isEmpty(idcard)) {
                params.addParam("idcard", idcard)
            }
            return params
        }

        val ACT_MY_GRADES = "my_grades"
        fun getMyGradesParam() = getWithIdParams()

        val ACT_PTGG = "ptgg"
        fun getPtggParam(pid: String) = getBaseParams().addParam("pid", pid)

        val ACT_INTEGRAL = "integral"
        fun getIntegralParam(page: Int) = getWithPageParams(page)

        val ACT_PERSONAL_SERVICE = "personal_service"
        fun getPersonalServiceParam() = getWithIdParams()

        val ACT_APPLY = "apply"
        fun getApplyParam(type: String, name: String, mpic: ArrayList<String>, sypic: ArrayList<String>, phone: String, address: String, detailAddress:String,num: String, content: String): ApiParams {
            val param = getWithIdParams()
            param.addParam("type", type)
            param.addParam("name", name)
            param.addParam("mpic", mpic.toString().removePrefix("[").removeSuffix("]"))
            param.addParam("sypic", sypic.toString().removePrefix("[").removeSuffix("]"))
            param.addParam("phone", phone)
            param.addParam("address", address)
            param.addParam("detail_address", detailAddress)
            param.addParam("num", num)
            param.addParam("content", content)
            return param
        }

        val ACT_MY_WITHDRAW = "my_withdraw"
        fun getMyWithdrawParam() = getWithIdParams()

        val ACT_MY_WITHDRAW_RECORD = "my_withdraw_record"
        fun getMyWithdrawRecordParam(page: Int) = getWithPageParams(page)

        val ACT_MY_WITHDRAW_OPERATION = "my_withdraw_operation"
        fun getMyWithdrawOperationParam(price: String, type: String) = getWithIdParams().addParam("price", price).addParam("type", type)

        val ACT_BIND_ALIPAY = "bind_alipay"
        fun getBindAlipayParam(account: String, autonym: String) = getWithIdParams().addParam("account", account).addParam("autonym", autonym)

        val ACT_BIND_WXPAY = "bind_wxpay"
        fun getBindWxpayParam(idcard: String, autonym: String) = getWithIdParams().addParam("idcard", idcard).addParam("autonym", autonym)

        val ACT_WX_AUTHORIZATION = "wx_authorization"
        fun getWxAuthorizationParam(code: String) = getWithIdParams().addParam("code", code)

        val ACT_M_P = "m_p"
        fun getEnsureMoneyProductParam() = getWithIdParams()

        val ACT_EDIT_PERSONAL_SERVICE = "edit_personal_service"
        fun getEditePersonalServiceParam(sid: String, address: String,detailAddress: String, ppa: String, starttime: String, endtime: String, content: String): ApiParams {
            val param = getWithIdParams()
            param.addParam("sid", sid)
            param.addParam("address", address)
            param.addParam("detail_address", detailAddress)
            param.addParam("ppa", ppa)
            param.addParam("starttime", starttime)
            param.addParam("endtime", endtime)
            param.addParam("content", content)
            return param
        }

        val ACT_PAYMENT_DEPOSIT = "payment_deposit"
        fun getPaymentDepositParam(type: String, mid: String, paytype: String): ApiParams {
            val param = getWithIdParams()
            param.addParam("type", type)
            param.addParam("mid", mid)
            param.addParam("paytype", paytype)
            return param
        }

        val ACT_REFUND_DEPOSIT = "refund_deposit"
        fun getRefundDepositParam(type: String) = getWithIdParams().addParam("type", type)

        val ACT_PERSONAL = "personal"
        fun getPersonalParam(uid: String) = getBaseParams().addParam("uid", uid)

        val ACT_MY_MSG = "my_msg"
        fun getMyMsgParam() = getWithIdParams()

        val ACT_MY_MSG_DETAIL = "my_msg_detail"
        fun getMyMsgDetailParam(status: String, page: Int) = getWithPageParams(page).addParam("status", status)

        val ACT_EDIT_MYMSG_STATUS = "edit_mymsg_status"
        fun getEditMyMsgStatus(mid: String) = getWithIdParams().addParam("mid", mid)

        /**              member model  end           **/


        /**              qaa model  start           **/
        val QAA_MODEL = "qaa"
        val ACT_QUIZ = "quiz"

        fun getQuizParam(ktype: Int, page: Int): ApiParams {
            val param = getWithPageParams(page)
            param.addParam("ktype", ktype)
            return param
        }

        val ACT_COMMUNITY_LUNBO_DETAIL = "community_lunbo_detail"
        fun getCommunityLunboDetail(lid: String): ApiParams {
            return getWithIdParams().addParam("lid", lid)
        }

        val ACT_QUIZ_DETAIL = "quiz_detail"
        fun getQuizDetailParams(qid: String, page: Int): ApiParams {
            return getWithPageParams(page).addParam("qid", qid)
        }

        val ACT_QUIZ_LOOK = "quiz_look"
        fun getQuizLookParams(qid: String): ApiParams {
            return getWithIdParams().addParam("qid", qid)
        }

        val ACT_ADD_ANSWER = "add_answer"
        fun getAddAnswerParams(qid: String, content: String): ApiParams {
            return getWithIdParams().addParam("qid", qid).addParam("content", content)
        }

        val ACT_GET_MYQUIZ = "get_myquiz"
        fun getGetMyQuizParam() = getWithIdParams()

        val ACT_GET_MYANSWER = "get_myanswer"
        fun getGetMyAnswerParam(page: Int) = getWithPageParams(page)

        val ACT_DEL_QAA = "del_qaa"
        fun getDelQaaParam(qid: String = "", aid: String = ""): ApiParams {
            return getWithIdParams().addParam("qid", qid).addParam("aid", aid)
        }

        val ACT_TAG = "tag"
        fun getTagParam() = getBaseParams()

        val ACT_ADD_QUIZ = "add_quiz"
        fun getAddQuizParam(title: String, content: String, type: String): ApiParams {
            return getWithIdParams().addParam("title", title).addParam("content", content).addParam("type", type)
        }

        val ACT_QUIZ_TYPE_SEARCH = "quiz_type_search"
        fun getQuizTypeSearchParam(type: String = "", page: Int): ApiParams {
            return getWithPageParams(page).addParam("type", type)
        }

        val ACT_QUIZ_SEARCH = "quiz_search"
        fun getQuizSearchParam(keyword: String): ApiParams {
            return getBaseParams().addParam("keyword", keyword)
        }

        val ACT_PRAISE = "praise"
        fun getPraiseParams(aid: String): ApiParams {
            return getWithIdParams().addParam("aid", aid)
        }

        val ACT_ARTICLE_PRAISE = "quiz_praise"
        fun getArticlePraiseParam(qid: String): ApiParams {
            return getWithIdParams().addParam("qid", qid)
        }

        /**              qaa model  end           **/
        /**              order model start        **/

        val ORDER_MODEL = "order"
        val ACT_QORDER = "qorder"
        fun getQorderParam(location: String = "", page: Int): ApiParams {
            return getWithPageParams(page).addParam("location", location)
        }

        val ACT_ORDER_RECEIVING = "order_receiving"
        fun getOrderReceivingParam(oid: String) = getWithIdParams().addParam("oid", oid)

        val ACT_MY_QORDER = "my_qorder"
        fun getMyQorderParam(status: String, page: Int) = getWithPageParams(page).addParam("status", status)

        val ACT_MY_ROADWORK_QORDER = "my_roadwork_qorder"
        fun getMyRoadworkQorder(page: Int) = getWithPageParams(page)

        val ACT_PLAY_PIC = "play_pic"

        val ACT_EDIT_QORDER_STATUS = "edit_qorder_status"
        fun getEditQorderStatusParam(oid: String, status: String,code: String,appointment_time:String,checkCode:String = "") =
                getWithIdParams()
                        .addParam("oid", oid)
                        .addParam("status", status)
                        .addParam("code",code)
                        .addParam("appointment_time",appointment_time)
                        .addParam("check_code",checkCode)

        val ACT_QORDER_DETAIL = "qorder_detail"
        fun getOrderDetailParam(oid: String) = getWithIdParams().addParam("oid", oid)

        val ACT_QORDER_DEPOSIT = "qorder_deposit"
        fun getQorderDepositParam(oid: String) = getWithIdParams().addParam("oid", oid)

        val ACT_MY_SALE_QORDER = "my_sale_qorder"
        fun getMySaleQorderParam(page: Int) = getWithPageParams(page)


        /**              order model end        **/

        /**              web url                **/
        val ACT_ABOUT_URL = "${Constants.BASE_IP}view/about.html"
        val ACT_HELP_URL = "${Constants.BASE_IP}view/help.html"
        val ACT_PROTOCOL_URL = "${Constants.BASE_IP}view/t_rules.html?pid=13"



        val ACT_PROVINCE = "province"
        val ACT_CITY = "city"
        val ACT_PROVINCE_CITY_AREA = "province_city_area"

        fun getDefaultAddress() = getWithIdParams()

        fun getProvinceParam() = getBaseParams()

        fun getCityParam(pid: String): ApiParams {
            val param = getBaseParams()
            param.addParam("pid", pid)
            return param
        }

        fun getProvinceCityAreaParam(cid: String): ApiParams {
            val param = getBaseParams()
            param.addParam("cid", cid)
            return param
        }

    }
}