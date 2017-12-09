$(document).ready(function() {
				showMyAssignedTicketsModal(); // When 'My Assigned Tickets' link clicked ! ^_^
		    	showModal(); //if action == create or update
		    	showTicketDetailsModal(); //if action == show Detais of
		    	showAssignTicketToModal(); //if action == assign To
		    	showAssignTicketSuccessAlert(); //if assign ticket X to Z done successfully !
		    	showManageTicketSuccessAlert(); //if ticket creadted or updated successufully !
		    	displayConfirmDeleteTicketAlert(); //if delete button clicked !
		    	activateTicketsLi(); // When tickets link clicked !
		    	displayTicketsStatistic(); // display tickets statistic !
		    	ajaxSearch(); // Enable ajax search !
		    	enableRadioBox(); // Enable Search tickets by status !
		    	assignedTicketsChart(); // assigned tickets statistics	
		    	filterTicketsByStatus(); // get tickets by status
		    	displayAgentProfileModal(); //diplay agent profile modal
		    	uploadImages(); //upload user images
		    	uploadCvs(); //upload user resumes
		    	showProfileUpdatedWithSuccessAlert(); //show success alert after profile updated !
		    });
		    
		    
		    /* show the modal if the action requested is update or create */
		    function showModal() {
		    	
		    	var action = $("#myModal").attr("action");
		    
		    	if (action == 'update' || action == 'create') {
		    		$("#myModal").modal("show");
		    	}
		    	
		    }
		    
		    
		    /* show ticket Details modal */
		    function showTicketDetailsModal() {
		    	var action = $("#ticketDetailsModal").attr("action");
		    	
		    	if (action == 'show Details of') {
		    		$("#ticketDetailsModal").modal("show");
		    	}
		    }
		    
		    
		    /* show assign ticket to.... modal */
		    function showAssignTicketToModal() {
		    	var action = $("#assignTicketToModal").attr("action");
		    	
		    	if(action == 'assign To') {
		    		$("#assignTicketToModal").modal("show");
		    		setAssignTicketToUrl();
		    		$("#toTeamSelect").hide();
		    		$("#toAgentSelect").hide();
		    	}
		    }
		    
		    
		    
		    function setAssignTicketToUrl() {
		    	
		    	var to = $("#assignToSelect").val(); //default => all
		    	
		    	var baseFormAction = $("#assignToForm").attr("action");
		    
		    	var actionForm = $("#assignToForm").attr("action") + "/" + to;
		    	
		    	$("#assignToSelect").change(function () {

		    		to = $("#assignToSelect").val();
		    		
		    		actionForm = baseFormAction + "/" + to;
			    		
		    		if (to == 'all') {
		    			//hide team and agent select inputs
		    			$("#toTeamSelect").hide();
			    		$("#toAgentSelect").hide();
			    		
			    	} else if (to == 'team') {
			    		
			    		//display team select input
			    		$("#toTeamSelect").show();
			    		//hide agent select input
			    		$("#toAgentSelect").hide();
			    	} else {
			    		
			    		//dispaly agents select input
			    		$("#toAgentSelect").show();
			    		//hide team select input
			    		$("#toTeamSelect").hide();
			    	}
		    		
		    		//set action attribute
		    		$("#assignToForm").attr("action", actionForm);
		    		
		    	});
		    	
		    	//set action attribute
		    	$("#assignToForm").attr("action", actionForm);

		    }
		    
		    
		    
		    /* show success alert when ticket X has been assigned successfully */
		    function showAssignTicketSuccessAlert() {
		    	var successMsg = $("#assignTicketToModal .modal-dialog").attr("assignTicketWithSuccess");
		    	
		    	if (successMsg !== undefined) {
		    		displaySuccessMsg(successMsg);
		    	}
		    }
		    
		    
		    /* show success alert when ticket X has been successfully created or updated */
		    function showManageTicketSuccessAlert() {
		    	var successMsg = $("#myModal .modal-dialog").attr("saveTicketWithSuccess");
		    	
		    	if (successMsg !== undefined) {
		    		displaySuccessMsg(successMsg);
		    	}
		    }
		    
		    
		    
		    /* this function display success messages for all actions on tickets, get as parameter the message to display */
		    function displaySuccessMsg(msg) {
		    	swal({
		    		  title: "The action was successfully completed",
		    		  text: msg,
		    		  timer: 5000,
		    		  type: "success",
		    		  showConfirmButton: false
		    	});
		    }
		    
		    
		    
		    /* show confirm alert before delete ticket X */
		    function displayConfirmDeleteTicketAlert() {
		    	$(".deleteTicket").on("click", function() {
		    		var delBtn = $(this);
		    		swal({
		    			  title: "Are you sure?",
		    			  text: "You will not be able to recover this ticket !",
		    			  type: "warning",
		    			  showCancelButton: true,
		    			  confirmButtonColor: "#DD6B55",
		    			  confirmButtonText: "Yes, delete it!",
		    			  closeOnConfirm: false,
		    			  showLoaderOnConfirm: true,
		    			},
		    			function(){
		    				var url = "/admin/" + $(delBtn).attr("admin_id") + "/delete/ticket/" + $(delBtn).attr("ticket_id");
<<<<<<< HEAD

		    				$.ajax( url )
		    				  .done(function(msg) {
		    				  	displayTicketsStatistic();
=======
		    				$.ajax( url )
		    				  .done(function(msg) {
>>>>>>> 3611e4b3dcf10afa1c903082f997d9a03bad8465
		    				    swal({
		    				    	title: "The action was successfully completed !",
		    				    	text: "The ticket number " + $(delBtn).attr("ticket_id") + " has been successfully deleted.",
		    				    	type: "success",
		    				    	timer: 3000,
		    				    	showConfirmButton: false
		    				    });
		    				  })
		    				  .fail(function() {
		    					  swal("Error!", "Ticket not found or already deleted !", "error");
		    				  });
		    				
		    				setTimeout(function() {
		    					$(delBtn).parent().parent().remove();
<<<<<<< HEAD
		    					var ticket_id = $(delBtn).attr("ticket_id");
                                assignedTicketsChart();
                                $($("#mainTable tbody tr td:contains(" + ticket_id + ")")[0]).parent().remove();
=======
>>>>>>> 3611e4b3dcf10afa1c903082f997d9a03bad8465
		    				}, 3000);
		    			});
		    	});
		    }
		   
		    
		    
		    
		    
		    /* activate tickets li */
		    function activateTicketsLi() {
		    	
		    	$(".sidebar-menu li:contains('Tickets')").addClass("active");
		    	
		    }
		    
		    
		    
		    /* tickets statistic */
		    function displayTicketsStatistic() {
		    	
		    	$.ajax({
		    		method: "GET",
		    		url: "http://localhost:8080/tickets/statistic",
		    	}).done(function( result ) {
		    	    
		    		var ctx = $("#myChart");
					
					var myChart = new Chart(ctx, {
					    type: 'doughnut',
					    data: {
					        labels: ["Opened : " + result[0], "In Progress : " + result[1], "Closed : " + result[2]],
					        datasets: [{
					            label: 'Tickets',
					            data: result,
					            backgroundColor: [
					                '#ef9a9a',
					                '#ffd54f',
					                '#a5d6a7',
					            ],
					            borderColor: [
					                '#ef5350 ',
					                '#ff6f00',
					                '#ffa000',
					            ],
					            borderWidth: 1
					        }]
					    },
					    options: {
					    	
					    }
					});
		    		
		    		
		    	  });
		    	
		    }
		    
		    
		    
		    
		    
		    /* this function search tickets by description or solution with ajax request */
		    
		    function ajaxSearch() {
		    	
		    	$("input[name='key']").keyup(function(event) {
		    		
		    		var keyWord = event.target.value;
		    		
		    		$.ajax({
		    			
		    			type: 'get',
		    		
		    			url: 'http://localhost:8080/search/tickets?keyWord=' + keyWord,
		    		
		    			data: {key: keyWord, page: 0, size: 5}
		    		
		    		}).done(function(result) {
		    			
		    			fillTheMainTable(result);

		    		});
		    		
		    	});
		    	
		    }
		    
		    
		    
		    
		    /* this function search tickets by status with ajax request */
		    function ajaxSearchByStatus(status) {
		    	
		    	$.ajax({
		    		
		    		type: 'get',
		    		
		    		url: 'http://localhost:8080/search/tickets?status=' + status,
		    		
	    			data: {page: 0, size: 5}
		    		
		    	}).done(function(result) {
		    		
		    		fillTheMainTable(result);
		    		
		    	});
		    	
		    }
		    
		    
		    /* search tickets by status */
		    
		    function enableRadioBox() {
		    	
		    	var status = "all"; //default
		    	
		    	$("#radioBox label").each(function(index) {
		    	
		    		$(this).click(function() {
		    			
		    			status = $(this).find('input').val();
		    			
		    			ajaxSearchByStatus(status);
		    			
		    		});
		    		
		    	});
		    	
		    }
		    
		    
		    
		    
		    /* fill table */
		    function fillTheMainTable(result) {
		    	
		    	$("#mainTable tbody").empty();
    			
    			$(result).find("#mainTable tbody tr").each(function(index) {
    				
    				var tr = this;
    				
    				setTimeout(function() {
    					
    					$("#mainTable tbody").append(tr);
    					
    				}, 500 + index * 100);
    				
    			});
    			
		    }
		    
		    
		    
		    
		    /* this function show assignedTickets modal */
		    function showMyAssignedTicketsModal() {
		    	
		    	var action = $("#assignedTicketsModal").attr("action");

		    	if (action == 'assignedTickets') {
		    		
		    		$("#assignedTicketsModal").modal("show");
		    		
		    	}
		    	
		    }
		    
		    
		    
		    
		    
		    
		    /* this function display assigned tickets to agent X statistics */
		    function assignedTicketsChart() {
		    	
		    	var ctx = $("#assignedTicketsChart");
		    	
		    	var result = getStatisticsResult();
				
				var myChart = new Chart(ctx, {
				    type: 'doughnut',
				    data: {
				        labels: ["Opened : " + result[0], "In Progress : " + result[1], "Closed : " + result[2]],
				        datasets: [{
				            label: 'Tickets',
				            data: result,
				            backgroundColor: [
				                '#ef9a9a',
				                '#ffd54f',
				                '#a5d6a7',
				            ],
				            borderColor: [
				                '#ef5350 ',
				                '#ff6f00',
				                '#ffa000',
				            ],
				            borderWidth: 1
				        }]
				    },
				    options: {
				    	
				    }
				    
				});
				
		    }
		    
		    
		    
		    
		    /* this function calculate the number of the closed tickets */
		    function nbOfTicketsByStatus(status) {
		    	
		    	var nbT = $("#assignedTicketsTable tbody td span:contains(" + status + ")").length;
		    	
		    	return nbT;
		    	
		    }
		    
		    
		    
		    /* get statistic results for assignedTicketsChart */
		    
		    function getStatisticsResult() {
		    	
		    	var result = [];
		    	
		    	result.push(
		    			nbOfTicketsByStatus("opened"),
		    			nbOfTicketsByStatus("in_progress"),
		    			nbOfTicketsByStatus("closed")
		    			);
		    	
		    	return result;
		    	
		    }
		    
		    
		    
		    
		    /* filter tickets by status */
		    
		    function filterTicketsByStatus() {
		    	
		    	$("#radioBox2 label").each(function(index) {
		    	
		    		$(this).click(function() {
		    			
		    			status = $(this).find('input').val();
		    			
		    			if (status == 'all') {
		    				
		    				//show all assigned tickets
		    				
		    				$("#assignedTicketsTable tbody td span:not(:contains(" + status + "))").each(function() {
			    				
			    				$(this).parent().parent().show();
			    				
			    			});
		    				
		    			} else {
		    				
		    				$("#assignedTicketsTable tbody td span:not(:contains(" + status + "))").each(function() {
			    				
			    				$(this).parent().parent().hide();
			    				
			    			});
			    			
			    			
			    			$("#assignedTicketsTable tbody td span:contains(" + status + ")").each(function() {
			    				
			    				$(this).parent().parent().show();
			    				
			    			});
		    				
		    			}
		    			
		    		});
		    		
		    	});
		    	
		    }
		    
		    
		    
		    
		    /*------------------------------------ agents function ------------------------------------*/
		    /* display agentProfileModal */
		    function displayAgentProfileModal() {
		    	var action = $("#agentProfileModal").attr("action");
		    	if(action == 'Show Profile of') {
		    		$("#agentProfileModal").modal("show");
		    		showHideOldPassword();
		    		showHideChangePassModal();
		    	}
		    }
		    
		    
		    
		    function showHideOldPassword() {
		    	$("#showhide3").click(function() {
		    		if ($(this).data('val') == "1") {
	    	               $("#oldPass").prop('type','text');
	    	               $("#eye3").attr("class","glyphicon glyphicon-eye-close");
	    	               $(this).data('val','0');
	    	        } else {
	    	        	 $("#oldPass").prop('type', 'password');
	    	             $("#eye3").attr("class","glyphicon glyphicon-eye-open");
	    	             $(this).data('val','1');
	    	        } 
		    	});
		    }
		    
		    
		    
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
		    
		    
		    
		    /* this function save the new password */
		    function saveNewPass() {
		    	
		    	$("#saveNewPassBtn").click(function(event) {
		    		
		    		var oldPwd = $("#oldPass").val();

		    		//verify if the confirm pass match the new one !
		    		var newPass = $("#newPass").val();
		    		
		    		var confirmNewPass = $("#confirmNewPass").val();
		    		
		    		if(!isOldPasswordCorrect(oldPwd)) {
		    			
		    			$("#oldPassError").text("Your old password not correct ! Try again or contact your Administrator.");
		    			
		    			$("#oldPassError").css("color", "red");
		    		}
		    		
		    		
		    		if (newPass == confirmNewPass && newPass != '' && confirmNewPass != '') {
		    			
		    			//fill the main input in the main form
		    			//valid the entered password:
		    			resetInputsForNewPassModal();
		    			
	    				$("#newConfirmPassError").text("");
		    			
		    			var testMsg = passwordValid(newPass);
		    			
		    			if (testMsg == 'ok' && isOldPasswordCorrect(oldPwd)) {

		    				$("#passInput").val(newPass);
		    				
		    				$("#confirmPassI").val(newPass);
		    				
		    				$("#changePassModal").modal("hide");
		    				
		    			}
		    			
		    			else if (testMsg != 'ok') {
		    				
		    				$("#newConfirmPassError").text(testMsg);
		    				
		    			}
		    			
		    			
		    		} else {
		    			
		    			$("#newConfirmPassError").text("Error ! The tow field the not match ! Please try again.");
		    			
		    			$("#newConfirmPassError").css("color", "red");

		    			resetInputsForNewPassModal();
		    		}
		    		
		    	});
		    	
		    }
		    
		    
		    function isOldPasswordCorrect(oldPwd) {
		    	
		    	var originPwd = $("#passInput").val();
		    	
		    	console.log(originPwd + " --- " + oldPwd + " --- " + oldPwd == originPwd);
		    	
		    	return oldPwd == originPwd;
		    	
		    }
		    
		    
		    function resetInputsForNewPassModal() {
		    	//reset pass inputs
    			$("#newPass").val("");
    			$("#confirmNewPass").val("");
		    }
		    
		    
		    
		    
		    
		    
		    
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
		    
		    
		    function passwordValid(pwd) {
		    	
		    	if (pwd == '' || pwd == ' ' || pwd === undefined) {
		    		
		    		return "Your password can't be empty !";
		    		
		    	}
		    	
		    	if (pwd.match(/^[a-z0-9]+$/)) {
		    		
		    		return "Your password must contain a combination between upper and lower charachters and digits.";
		    		
		    	}
		    	
		    	
		    	if (pwd.length < 8 || pwd.length > 10) {
		    		
		    		return "Your password must be between 8 and 10 characters.";
		    		
		    	}
		    	
		    	return "ok";
		    	
		    }
		    
		    
		    
		    
		    
		    function showProfileUpdatedWithSuccessAlert() {
		    	
		    	var profileUpdatedResult = $("#profileUpdatedMsg").attr("profileUpdatedResult");
		    	
		    	var msg = "Your profile has been successfully updated.";
		    	
		    	console.log(profileUpdatedResult);
		    	
		    	if (profileUpdatedResult !== undefined || profileUpdatedResult == '') {
		    		
		    		displaySuccessMsg(msg);
			    	
			    	$("#profileUpdatedMsg").attr("");
			    	
		    	}
		    	
		    }
		    
		    
		    