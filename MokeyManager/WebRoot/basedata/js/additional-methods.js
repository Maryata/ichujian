$.validator.addMethod("validateSpecialChars", function(value, element) {
		// declare which special chars to validate
	    // var illegalChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
	     var illegalChars = "";//"!@#$%^*()+[]\\\';,{}|\"<>";
	     var str = value;
	 	 var result = true;
	     for (var i = 0; i < str.length; i++) {
	  		if (illegalChars.indexOf(str.charAt(i)) != -1) {
	  			result = false;
	  			break;
	  		}
     	}
     	
  		return this.optional(element) || result;
  		
}, "The Field has special characters.\nThese are not allowed.\nPlease remove them and try again.");