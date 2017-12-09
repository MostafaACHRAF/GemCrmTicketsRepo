$(document).ready(function() {
		    	showModal(); // if action == create or update
		    	displayConfirmDeleteAgentAlert(); // if delete button clicked !
		    	showManageAgentSuccessAlert(); // if create or update action has been done successfully !
		    	displayAgentDetailsModal(); // if action equal 'show Details of'
		    	displayAgentProfileModal(); // if action equal 'Show Profile of'
		    	activateAgentsLi(); // When agents link clicked !
		    	ajaxSearch(); //ajax serach ^_^
		    	uploadImages();
		    	uploadCvs();
		    	 /*$("button[title='Upload selected files']").click(function() {
		    		 
		    		 var t = $("div.file-caption-name").text();
		    		 
		    		 console.log(t);
		    		 
		    		 $.ajax({
		    			 
		    			 type: "post",
		    			 
		    			 contentType: 'multipart/form-data',
		    			 
		    			 url: "http://localhost:8080/upload/file?file=t"
		    			 
		    		 }).done(function(result) {
		    			
		    			 console.log(result);
		    			 
		    			 alert("The file was successfully uploaded");
		    			 
		    		 });
		    		
		    	 });*/
		    	
		    	
		    	
		    	
		    	
		    	
		    	/*$("#uploadImagesForm").submit(function(event) {
		    		
		    		
		    		var form = new FormData($("#uploadImagesForm")[0]);
		    		
		    		$.ajax({
		    	        url: "/upload/file",
		    	        method: "POST",
		    	        
		    	        data: form,
		    	        processData: false,
		    	        contentType: false,
		    	        success: function(result){ console.log("success"); },
		    	        error: function(er){ console.log(er); }
		    		});
		    		
		    	});*/
		    	
		    });
		    
		   
		    /* show the modal if the action requested is update or create */
		    function showModal() {
		    	var action = $("#myModal").attr("action");
		    
		    	if (action == 'update' || action == 'create') {
		    		$("#myModal").modal("show");
		    	}
		    	
		    	showContractEndDateSelect();
		    	showHidePassword();
		    	showHideConfirmPassword();
		    }

		    
		    
		    /* this function remove action attribute from modal when closed button clicked */
		    function removeModalActionAttr() {
		    	$(".close").click(function() {
		    		$("#myModal").removeAttr( "action" );
		    	});
		    }
		    
		    
		    function showContractEndDateSelect() {
		    	if ($("#contractTypeSelect").val() == 'CDI') {
		    		$("#endDateInput").parent().parent().hide();
		    	}
		    	
		    	$("#contractTypeSelect").change(function(){
		    	   if ($(this).val() == 'CDI') {
		    		   /* display endDateInput */
		    		   $("#endDateInput").parent().parent().hide();
		    	   } else {
		    		   $("#endDateInput").parent().parent().show();
		    	   }
		    	});
		    }
		    
		    
		    function showHidePassword() {
		    	$("#showhide1").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#passwordInput").prop('type','text');
	    	               $("#eye1").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#passwordInput").prop('type', 'password');
	    	             $("#eye1").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    
		    
		    function showHideConfirmPassword() {
		    	$("#showhide2").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#confirmPasswordInput").prop('type','text');
	    	               $("#eye2").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#confirmPasswordInput").prop('type', 'password');
	    	             $("#eye2").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    
		    
		    /*#####################################################*/
		    function showHideOldPassword() {
		    	$("#showhide3").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#passInput").prop('type','text');
	    	               $("#eye3").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#passInput").prop('type', 'password');
	    	             $("#eye3").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    /* I'm stupid to day because of this i write this terrible code (._.) :( */
		    //I won't it just work well !!
		    /*###########################################################*/
		    function showHideChangePassword1() {
		    	$("#showHidePassBtn1").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#newPass").prop('type','text');
	    	               $("#eye11").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#newPass").prop('type', 'password');
	    	             $("#eye11").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    
		    /*##################################################*/
		    function showHideChangePassword2() {
		    	$("#showHidePassBtn2").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#confirmNewPass").prop('type','text');
	    	               $("#eye22").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#confirmNewPass").prop('type', 'password');
	    	             $("#eye22").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    
		    
		    
		    
		    /* show success alert when agent X has been successfully created or updated */
		    function showManageAgentSuccessAlert() {
		    	var successMsg = $("#myModal").attr("saveAgentWithSuccess");
		    	
		    	if (successMsg !== undefined) {
		    		displaySuccessMsg(successMsg);
		    	}
		    }
		    
		    
		    
		    /* this function display success messages for all actions on agent, get as parameter the message to display */
		    function displaySuccessMsg(msg) {
		    	swal({
		    		  title: "The action was successfully completed",
		    		  text: msg,
		    		  timer: 5000,
		    		  type: "success",
		    		  showConfirmButton: false
		    	});
		    }
		    
		    
		    
		    
		    
		    /* display agentDetailsModal */
		    function displayAgentDetailsModal() {
		    	var action = $("#agentDetailsModal").attr("action");
		    	
		    	if(action == 'show Details of') {
		    		$("#agentDetailsModal").modal("show");
		    	}
		    }
		    
		    
		    
		    
		   /*#####################################################3*/ 
		    /* display agentProfileModal */
		    function displayAgentProfileModal() {
		    	var action = $("#agentProfileModal").attr("action");
		    	if(action == 'Show Profile of') {
		    		$("#agentProfileModal").modal("show");
		    		showHideOldPassword();
		    		showHideChangePassModal();
		    	}
		    }
		    
		    
		    
		    
		    
		    /* show confirm alert before delete agent X */
		    function displayConfirmDeleteAgentAlert() {
		    	$(".deleteAgent").on("click", function() {
		    		var delBtn = $(this);
		    		swal({
		    			  title: "Are you sure?",
		    			  text: "You will not be able to recover this agent account !",
		    			  type: "warning",
		    			  showCancelButton: true,
		    			  confirmButtonColor: "#DD6B55",
		    			  confirmButtonText: "Yes, delete it!",
		    			  closeOnConfirm: false,
		    			  showLoaderOnConfirm: true,
		    			},
		    			function(){
		    				var url = "/admin/" + $(delBtn).attr("admin_id") + "/delete/agent/" + $(delBtn).attr("agent_id");
		    				$.ajax( url )
		    				  .done(function(msg) {
		    				    swal({
		    				    	title: "The action was successfully completed !",
		    				    	text: "The agent number " + $(delBtn).attr("agent_id") + " has been successfully deleted.",
		    				    	type: "success",
		    				    	timer: 3000,
		    				    	showConfirmButton: false
		    				    });
		    				  })
		    				  .fail(function() {
		    					  swal("Error!", "Agent account not found or already deleted !", "error");
		    				  });
		    				
		    				setTimeout(function() {
		    					$(delBtn).parent().parent().remove();
		    				}, 3000);
		    			});
		    	});
		    }
		    
		    
		    
		    
	    
		    /* activate agents li */
		    function activateAgentsLi() {
		    	
		    	$(".sidebar-menu li:contains('Agents')").addClass("active");
		    	
		    }
		    
		    
		    
		    
		    
		    /* fill table */
		    function fillTheMainTable(result) {
		    	
		    	$("#mainTable tbody").empty();
    			
    			$(result).find("#mainTable tbody tr").each(function(index) {
    				
    				var tr = $(this);
    				
					setTimeout(function() {
    					
    					$("#mainTable tbody").append(tr);
    					
    				}, 500 + index * 100);
    				
    			});
    			
		    }
		    
		    
		    
		    
		    
		    
		    /* this function search agents by name with ajax request */
		    
		    function ajaxSearch() {
		    	
		    	$("input[name='name']").keyup(function(event) {
		    		
		    		var keyWord = event.target.value;
		    		
		    		console.log(keyWord);
		    		
		    		$.ajax({
		    			
		    			type: 'get',
		    		
		    			url: 'http://localhost:8080/search/agents?name=' + keyWord,
		    		
		    			data: {key: keyWord, page: 0, size: 5}
		    		
		    		}).done(function(result) {
		    			
		    			fillTheMainTable(result);

		    		});
		    		
		    	}); 
		    	
		    }
		    
		    
		    
		    /*$(document).keypress(function(event) {
	    		
		    	var code = event.keyCode || event.which;
		    	
		    	console.log("jjjjdjd   " + code);
	    		
	    		if (event.keyCode == 13) {

	    			var keyWord = $("input[name='name']").val();
	    			
	    			console.log(keyWord);
		    		
		    		$.ajax({
		    			
		    			type: 'get',
		    		
		    			url: 'http://localhost:8080/search/agents?name=' + keyWord,
		    		
		    			data: {key: keyWord, page: 0, size: 5}
		    		
		    		}).done(function(result) {
		    			
		    			fillTheMainTable(result);

		    		});
	    		}
	    		
	    	});*/
		    
		    
		    
		    
		    /*###############################################*/
		    /* this function show/hide change password modal */
		    function showHideChangePassModal() {
		    	
		    	$("#changeOldPass").click(function() {
		    		$("#newConfirmPassError").text("");
		    		$("#changePassModal").modal("show");
		    		showHideChangePassword1();
		    		showHideChangePassword2();
		    		saveNewPass();
		    	});
		    	
		    }
		    
		    
		    
		    /*##############################################################3*/
		    /* this function save the new password */
		    function saveNewPass() {
		    	
		    	$("#saveNewPassBtn").click(function(event) {
		    		
		    		//verify if the confirm pass match the new one !
		    		var newPass = $("#newPass").val();
		    		
		    		var confirmNewPass = $("#confirmNewPass").val();
		    		
		    		if (newPass == confirmNewPass && newPass != '' && confirmNewPass != '') {
		    			
		    			//fill the main input in the main form
		    			
		    		} else {
		    			
		    			//error  !
		    			$("#newConfirmPassError").text("Error ! The tow field the not match. Please try again.");
		    			
		    			$("#newConfirmPassError").css("color", "red");
		    			
		    			//reset pass inputs
		    			$("#newPass").val("");
		    			$("#confirmNewPass").val("");
		    		}
		    		
		    	});
		    	
		    }
		    
		    
		    
		    
		    /*###############################################################*/
		    function uploadImages() {

		    	$("#uploadImagesForm").submit(function(event) {
		    		
		    		event.preventDefault();

		    		var form = new FormData($("#uploadImagesForm")[0]);

		    		$.ajax({
		    	        url: "/upload/file",
		    	        method: "POST",
		    	        enctype: 'multipart/form-data',
		    	        data: form,
		    	        processData: false,
		    	        contentType: false,
		    	        cache: false,
		    	        success: function(result){ 
		    	        	$("#uploadImageMsg").text("Successfullt uploaded - " + result);
		    	        	$("#uploadImageMsg").addClass("text-success"); 
		    	        	$("#imageI").val(result);
		    	        	},
		    	        	
		    	        error: function(er){ 
		    	        	$("#uploadImageMsg").text(result); 
		    	        	$("#uploadImageMsg").addClass("text-danger");
		    	        	}
		    		});
		    		
		    	});
		    	
	    	}
		    
		    
		    
		    
		    
		    
		    
		    /*###############################################################*/
		    function uploadCvs() {

		    	$("#uploadCvForm").submit(function(event) {
		    		
		    		event.preventDefault();

		    		var form = new FormData($("#uploadCvForm")[0]);

		    		$.ajax({
		    	        url: "/upload/file",
		    	        method: "POST",
		    	        enctype: 'multipart/form-data',
		    	        data: form,
		    	        processData: false,
		    	        contentType: false,
		    	        cache: false,
		    	        success: function(result){ 
		    	        	$("#uploadCvMsg").text("Successfullt uploaded - " + result);
		    	        	$("#uploadCvMsg").addClass("text-success"); 
		    	        	$("#cvI").val(result);
		    	        	},
		    	        	
		    	        error: function(er){ 
		    	        	$("#uploadCvMsg").text(result); 
		    	        	$("#uploadCvMsg").addClass("text-danger");
		    	        	}
		    		});
		    		
		    	});
		    	
	    	}
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		  
   
		    