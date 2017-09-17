$(function () {
				
				showEditCompanyModal(); // if action equal 'create' or 'update'
				displayConfirmDeleteCompanyAlert(); // if delete button clicked !
				showManageCompanySuccessAlert(); // if create or update company X done successfully !
				showCompanyDetailsModal(); // if action equal 'show Details of'
				activateCompaniesLi(); // When companies link clicked !
				ajaxSearch(); // Enable ajax search !
			});
			
			
			/* this function display editCompanyForm */
			function showEditCompanyModal() {
				var action = $("#editCompanyModal").attr("action");
				
				if (action == 'create' || action == 'update') {
					$("#editCompanyModal").modal("show");
				}
			}
			
			
			
			/* this function display companyDetailsModal */
			function showCompanyDetailsModal() {
				var action = $("#companyDetailsModal").attr("action");
				
				if (action == 'show Details of') {
					$("#companyDetailsModal").modal("show");
				}
			}
			
			
			
			/* show success alert when company has been successfully created or updated */
			function showManageCompanySuccessAlert() {
				var successMsg = $("#editCompanyModal").attr("saveCompanyWithSuccess");
				
				if(successMsg !== undefined) {
					displaySuccessMsg(successMsg);
				}
				
			}
			
			
			
			
			/* this function display success messages for all actions on companies, get as parameter the message to display */
		    function displaySuccessMsg(msg) {
		    	swal({
		    		  title: "The action was successfully completed",
		    		  text: msg,
		    		  timer: 5000,
		    		  type: "success",
		    		  showConfirmButton: false
		    	});
		    }
			
			
			
			/* show confirm alert before delete X company */
		    function displayConfirmDeleteCompanyAlert() {
		    	$(".deleteCompany").on("click", function() {
		    		var delBtn = $(this);
		    		swal({
		    			  title: "Are you sure?",
		    			  text: "You will not be able to recover this company !",
		    			  type: "warning",
		    			  showCancelButton: true,
		    			  confirmButtonColor: "#DD6B55",
		    			  confirmButtonText: "Yes, delete it!",
		    			  closeOnConfirm: false,
		    			  showLoaderOnConfirm: true,
		    			},
		    			function(){
		    				var url = "/admin/" + $(delBtn).attr("admin_id") + "/delete/company/" + $(delBtn).attr("company_id");
		    				$.ajax( url )
		    				  .done(function(msg) {
		    				    swal({
		    				    	title: "The action was successfully completed !",
		    				    	text: "The company number " + $(delBtn).attr("company_id") + " has been successfully deleted.",
		    				    	type: "success",
		    				    	timer: 3000,
		    				    	showConfirmButton: false
		    				    });
		    				  })
		    				  .fail(function() {
		    					  swal("Error!", "Company not found or already deleted !", "error");
		    				  });
		    				
		    				setTimeout(function() {
		    					$(delBtn).parent().parent().remove();
		    				}, 3000);
		    			});
		    	});
		    }
			
			
			
			
			
		    /* activate companies li */
		    function activateCompaniesLi() {
		    	
		    	$(".sidebar-menu li:contains('Companies')").addClass("active");
		    	
		    }
		    
		    
		    
		    
		    /* search companies by ajax request */
		    function ajaxSearch() {
		    	
		    	$("input[name='name']").keyup(function(event) {
		    		
		    		var companyName = event.target.value;
		    		
		    		$.ajax({
		    			
		    			type: 'get',
		    		
		    			url: 'http://localhost:8080/search/companies',
		    		
		    			data: {name: companyName, page: 0, size: 5}
		    		
		    		}).done(function(result) {
		    			
		    			fillTheMainTable(result);

		    		});
		    		
		    	});
		    	
		    }
		    
		    
		    
		    
		    
		    
		    /* fill the main table by searchAjax results */
		    function fillTheMainTable(result) {
		    	
		    	$("#mainTable tbody").empty();
    			
    			$(result).find("#mainTable tbody tr").each(function(index) {
    				
    				var tr = this;
    				
    				setTimeout(function() {
    					
    					$("#mainTable tbody").append(tr);
    					
    				}, 500 + index * 100);
    				
    			});
    			
		    }
		    
		    