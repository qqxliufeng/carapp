package com.android.ql.lf.carapp.utils

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
        val ACT_WX_PERFECT = "wx_perfect"

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
        fun getEditPersonalParam(account: String): ApiParams {
            return getWithIdParams().addParam("account", account)
        }


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
        fun getCommunityLunboDetail(lid:String):ApiParams{
            return getWithIdParams().addParam("lid",lid)
        }

        /**              qaa model  end           **/

    }


}