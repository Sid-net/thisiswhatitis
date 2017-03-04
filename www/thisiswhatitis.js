var exec = require('cordova/exec');

var thisiswhatitis = {
	
	set :function(success, error, key, value) {
	exec(success, error,"thisiswhatitis", "set", [key, value]);
},
	
	get : function(success, error, key) {
	exec(success, error,"thisiswhatitis", "get", [key]);
},

delete :function(success, error, key) {
exec(success, error,"thisiswhatitis","delete", [key]);
}
};

module.exports = thisiswhatitis;