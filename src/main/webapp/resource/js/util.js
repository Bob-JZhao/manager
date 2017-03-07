/**
 * Url规则
 * @param url
 * @returns
 */
function validateUrl(url){
	var strRegex = "^((https|http|ftp|rtsp|mms)?://){1}"                    + //# protocal
	"(([0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+:)?"                     + //# ftp-user
	"[0-9a-zA-Z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]+@)?"                       + //# ftp-password
	"(([0-9]{1,3}\\.){3}[0-9]{1,3}"                                     + //# IP
	"|"                                                                 + //# or
	"([0-9a-zA-Z_!~\\*'\\(\\)\\-]+\\.)*"                                + //# domain
	"([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\."                    + //# domain
	"[a-zA-Z]{2,6})"                                                    + //# domain
	"(:[0-9]{1,4})?"                                                    + //# port
	"((/?)|"                                                            + //# slash
	"(/[0-9a-zA-Z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#\\-\\[\\]\\|\\{\\}]*)+/?)$";  // # args
	//var reg = /^(http|https):\/\/[\w\$\-\.\+!\*;\/\?:@=&\|]{3,}$/
	var reg = new RegExp(strRegex);
	return  reg.test(url);
}

function isNull(str){
	if(str==''||str==null){
		return true;
	}else return false;
}

function digitsRange(num, min, max){
	if (isNaN(num)) {
		return false;
	}
	var r = /^\+?[0-9]*$/;
    if(!r.test(num)) {
    	return false;
    }
	if (parseInt(num) < min || parseInt(num) > max) {
		return false;
	}
	return true;
}