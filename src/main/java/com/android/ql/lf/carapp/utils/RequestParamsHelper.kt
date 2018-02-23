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

        val ACT_MY_GRADES = "my_grades"
        fun getMyGradesParam() = getWithIdParams()



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
        fun getGetMyAnswerParam() = getWithIdParams()

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
        fun getArticlePraiseParam(qid:String):ApiParams{
            return getWithIdParams().addParam("qid",qid)
        }

        /**              qaa model  end           **/
        /**              order model start        **/

        val ORDER_MODEL = "order"
        val ACT_QORDER = "qorder"
        fun getQorderParam(location:String):ApiParams{
            return getWithIdParams().addParam("location",location)
        }
        /**              order model end        **/
    }
}