/**
 *封装常用js方法
 */
//jquery ajax
var ajaxPost=function(url,data,successCallback){
	$.ajax({
		url:url,
		dataType:"json",
		type:"post",
		//contentType:"application/json",
		data:data,
		success:function(result){
			successCallback(result);
		}
	});
};
/**
 * json数据会封装成javabean，controller要使用RequestBody注解
 */
var ajaxPostBean=function(url,data,successCallback){
	$.ajax({
		url:url,
		dataType:"json",
		type:"post",
		contentType:"application/json",
		data:data,
		success:function(result){
			successCallback(result);
		}
	});
};