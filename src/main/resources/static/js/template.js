$(function () {
			    $('#datetimepicker1').datetimepicker({
			    	format: 'DD/MM/YYYY',
			    	viewMode: 'years',
			    	useStrict: true,
			    });
			    
			    /**/
			    $('#datetimepicker6').datetimepicker({format: 'DD/MM/YYYY'});
			    
			    /**/
		        $('#datetimepicker7').datetimepicker({
		        	format: 'DD/MM/YYYY',
		            useCurrent: false //Important! See issue #1075
		        });
			    
			    /**/
		        $("#datetimepicker6").on("dp.change", function (e) {
		            $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
		        });
			    
			    /**/
		        $("#datetimepicker7").on("dp.change", function (e) {
		            $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
		        });
		        
			    /**/
		        $('#datetimepicker8').datetimepicker({format: 'DD/MM/YYYY'});
			    
			});