function jspop_dialog(id, title, url, width, height, callback, save, close, expand) {
    var buttons = [];
    
    if (expand) {
    	buttons = expand;
    }
    
    if (save) {
    	buttons.push({
    		text:"保存",
    		iconCls:"iconfont icon-save",
    		areaCls:"",
    		handler:function() {
    			_dialog_frame.jspop_save();
    		}
    	});
    }
    
    if (close) {
    	buttons.push({
    		text: "关闭",
    		iconCls: "iconfont icon-cancel",
    		areaCls: "",
    		handler: function() {
    			jspop_dialog_close(id);
    		}
    	});
    }
    jspop_dialog_expand(id, title, url, width, height, callback, buttons);
}

function jspop_dialog_expand(id, title, url, width, height, callback, buttons) {
    var $el;
    
    if (id) {
        $el = $("#" + id);
    } else {
        id = "jspop_dialog";
    }
    
    if (!$el || $el.length == 0) {
        $el = $('<div id="' + id + '" style="overflow:hidden"></div>');
    }
    
    var iframe = "<iframe name='_dialog_frame' id='_dialog_frame' height='100%' width='100%' style='frameborder:no;allowtransparency:true;border:0' src=" + url + " scrolling='no'></iframe>";
    var dialog_params = {};
    dialog_params.title = title;
    dialog_params.content = iframe;
    dialog_params.width = width;
    dialog_params.height = height;
    dialog_params.closed = false;
    dialog_params.cache = false;
    dialog_params.modal = true;
    dialog_params.buttons = buttons;
    dialog_params.onClose = function() {
		$el.dialog("destroy");
	};
	
	if (callback!=null) {
		dialog_params.onDestroy = callback;
	}
    $el.dialog(dialog_params);
}

function jspop_dialog_close(id) {
	if (parent.$("#" + id)) {
		parent.$("#" + id).dialog("destroy");
	}
	
	if ($("#" + id)) {
		$("#" + id).dialog("destroy");
	}
}

$.fn.serializeObject = function() {
    var o = {};
    
    $.each(this.serializeArray(), function(index) {
    	if (o[this['name']]) {
    		o[this['name']] = o[this['name'] ] + ", " + this['value'];
    	} else {
    		o[this['name']] = this['value'];
        }
    });
    return o;
} 