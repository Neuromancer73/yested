package net.yested

data class AjaxRequest(val url:String, val type:String = "POST", val data:String,
                       val contentType:String = "application/json; charset=utf-8",
                       val dataType:String = "json", val success: Function0<Unit>)

native
trait JQAjax {
    fun <T> get(url:String, loaded:(response: T?) -> Unit) : Unit = noImpl
    //fun post(url:String, data:Any?, handler:()->Unit, type:String = "json") : Unit = noImpl
    //fun ajax(url:String, type:String, contentType:String, dataType:String, data:Any, success:()->Unit) : Unit = noImpl
    fun ajax(request:AjaxRequest) : Unit = noImpl
}

native("$") public var ajaxJQuery: JQAjax = null!!


fun <T> ajaxGet(url:String, loaded:(response:T?) -> Unit) : Unit {
    ajaxJQuery.get(url = url, loaded = loaded)
}

fun ajaxPost(ajaxRequest: AjaxRequest) : Unit {
    ajaxJQuery.ajax(ajaxRequest)
}