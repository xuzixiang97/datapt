Date.prototype.format = function(fomatter) {
    var o = {
	    "M+" : this.getMonth()+1, //月份         
	    "d+" : this.getDate(), //日         
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	    "H+" : this.getHours(), //小时         
	    "m+" : this.getMinutes(), //分         
	    "s+" : this.getSeconds(), //秒         
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
	    "S" : this.getMilliseconds() //毫秒         
    };
    
    var week = {
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
    };

    if (/(y+)/.test(fomatter)) {
        fomatter = fomatter.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    
    if (/(E+)/.test(fomatter)) {
        fomatter = fomatter.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);         
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fomatter)) {
            fomatter = fomatter.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00"+ o[k]).substr(("" + o[k]).length)));
        }
    }
    return fomatter;
}       

/**
 * 对日期时间进行格式化
 **/
function format(value, fomatter) {
	if (value == null || value == '') {
		return "";
	}

	if (value instanceof Date) {
		return value.format(fomatter);
	} else {
		return new Date(value).format(fomatter);
	}
	return "";
}

function remote(url, params){
	return $.ajax({url: url, dataType: "json", type: "post", async: false, cache: false, data: params}).responseText;
}

function comboxload(pid, id, url, params, value){
	$("#" + pid).combobox({
		onChange: function(newValue, oldValue) {
			if (params == null || params == undefined) {
				params = {};
			}
			
			params[pid] = newValue;
			var data = remote(url, params);
			$("#" + id).combobox("loadData", jQuery.parseJSON(data));
			$("#" + id).combobox("setValue", "");
		},
		onLoadSuccess: function() {
			if (params == null || params == undefined) {
				params = {};
			}
			
			params[pid] = $(this).combobox("getValue");
			var data = remote(url, params);
			$("#" + id).combobox("loadData", jQuery.parseJSON(data));
			$("#" + id).combobox("setValue", value);
		}
	});
}