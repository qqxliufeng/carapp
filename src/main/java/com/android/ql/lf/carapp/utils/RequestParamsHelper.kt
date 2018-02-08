package com.android.ql.lf.carapp.utils

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

//        private fun getWithIdParams(): ApiParams {
//            val params = getBaseParams()
//            params.addParam("uid", UserInfo.getInstance().memberId)
//            return params
//        }
//
//        private fun getWithPageParams(page: Int, pageSize: Int = 10): ApiParams {
//            val param = if (UserInfo.getInstance().isLogin) {
//                getWithIdParams()
//            } else {
//                getBaseParams()
//            }
//            param.addParam("page", page)
//            param.addParam("pagesize", pageSize)
//            return param
//        }

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
            params.addParam("tel", tel).addParam("pw", pw)
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

    }
    /**              login model  end           **/

}