Plan = {
	template : {},
	populate : function(productCategory, callbackFunc) {
		$("#js-" + productCategory+ "-link .dataList > ul").empty();
		var queryString = Plan.getQueryString(productCategory);
		$.ajax({
			url:GlobalVars["app_url"] + "/web/plans/search/result?" + queryString,
			//TODO
			type:"GET",
			success : function(data, status, jqXHR){
				callbackFunc(productCategory, data);
				if($('.removeFromCart').length > 0) {
					$("#js-" + productCategory+ "-link").find(".addToCart").attr("disabled", "disabled");
				}
			},
			error : function(data, status, jqXHR){
				
			}
		});
	},
	createPaginator : function(productCategory, totalRecords) {
		var displayLength = 10;
		$("#js-" + productCategory+ "-link .pagination").smartpaginator({ 
			totalrecords: totalRecords,
			recordsperpage: displayLength,
			controlsalways: true,
			next: 'Next', 
			prev: 'Prev', 
			first: 'First', 
			last: 'Last',
			length: 4,
			theme: 'black', 
			onchange: function(newPage) {
			//	var queryString = Plan.getQueryString(productCategory) + "&rs=" + displayLength * (newPage - 1);
				$("#js-" + productCategory+ "-link .listData .displayStart").val(displayLength * (newPage - 1));
				Plan.populate(productCategory, Plan.populatePlans);
			}});
	},
//	populatePlans : function(productCategory) {
//		$("#js-" + productCategory+ "-link .dataList > ul").empty();
//		var data = Plan.fetchPlans(productCategory);
//		var template = Plan.getTemplate(productCategory);
//		$.each(data.planMetaData, function(index, item){
//			$("#js-" + productCategory+ "-link .dataList > ul").append(template(item));
//		});
//		Plan.makeDraggable(productCategory);
//		return data;
//	},
//	initializePlans : function(productCategory) {
//		var data = Plan.populatePlans(productCategory);
//		Plan.createPaginator(productCategory, data.totalRecords);
//		Plan.initializeFilters(productCategory, data.filters);
//		Plan.initializeSorting(productCategory);
//	},
	populatePlans : function(productCategory, data){
		var template = Plan.getTemplate(productCategory);
		$("#js-" + productCategory + "-link .dataList > ul").empty();
		$.each(data.planMetaData, function(index, item){
				$("#js-" + productCategory+ "-link .dataList > ul").append(template(item));
		});
		
		$(".js-compare").attr('disabled',true);
		var index=$(location).attr('href').indexOf('planIds');
   		if(index!=null && index!='' & index!=-1){
   			var cmpVars={};
   			var productcategory = $("input[id^='compare_']").parents(".dataRow").find(".addToCart").attr('id').split('-')[1];
   			if(typeof cmpVars[productcategory] == "undefined"){
   				cmpVars[productcategory] = [];
   			}
   			var planIds=$(location).attr('href').substring(index);
   			var plans=planIds.split("=")[1].split(",");
   			var noOfPlans=0;
   			$.each(plans,function(i,v){
   				$("input[id^='compare_"+v+"']").click();
   				noOfPlans++;
   				cmpVars[productcategory].push(v);
   			});
   			if(cmpVars!=undefined){
   				$(document).data('cmpVars',cmpVars);
   			}
   			if(noOfPlans>=2){
   				$(".js-compare").attr('disabled',false);
   			}
   		}
   		
	},
	populatePlansAndPaginator : function(productCategory, data){
		if(data) {
			Plan.populatePlans(productCategory, data);
			Plan.createPaginator(productCategory, data.totalRecords);
		}
	},
	populatePage : function(productCategory, data){
		if(data) {
			Plan.populatePlansAndPaginator(productCategory, data);
			Plan.initializeFilters(productCategory, data.filters);
			Plan.initializeSorting(productCategory);
		}
	},
	getQueryString : function(productCategory) {
		var queryArray =  $("#js-" + productCategory+ "-link").serializeArray();
//		$.each(queryArray, function(index, query){
//			if(query.value === ""){
//				queryArray.splice(index, 1);
//			}
//		});
//		return $.param(queryArray);
		
		queryArray = $.grep(queryArray, function(query){
			if(query.value === ""){
				return false;
			}
			return true;
		});
		return $.param(queryArray);// + "&t=" + $("#sessionToken").val();
		
		
//		var displayLength = 10;
//		return "rtf=" + displayLength;
	},
	getTemplate : function(productCategory) {
		if(!Plan.template[productCategory]){
			Plan.template[productCategory] = Handlebars.compile($("#" + productCategory + "_list_template").html());
		}
		return Plan.template[productCategory];
	},
	initializeSorting : function(productCategory){
		$('#js-' + productCategory+ '-link .sorting > ul > li > a' ).click(function(){
			var name = $(this).data('name');
			var order = $(this).data('order');
			$('#js-' + productCategory+ '-link .sorting > ul > li > a > span' ).removeClass();
			$('#js-' + productCategory+ '-link .sorting > ul > li > a' ).data("order", "");
			if(order == undefined || order == '' || order == 'desc'){
				order = 'asc';
				$(this).children("span").addClass("icon-sorting-up");
			}else{
				order = 'desc';	
				$(this).children("span").addClass("icon-sorting-down");
			}
			$(this).data('order', order);
			$('#js-' + productCategory+ '-link .listData .sortColumn').val(name);
			$('#js-' + productCategory+ '-link .listData .sortOrder').val(order);
			Plan.populate(productCategory, Plan.populatePlans);
		});
	},
	initializeFilters : function(productCategory, filters){
		if(filters.length) {
			$.each(filters, function(index, filter){
				if(filter.values != undefined){
					Plan.initializeListFilter(productCategory, filter);
				}else if(filter.max != undefined && filter.min != undefined){
					Plan.initializeSliderFilter(productCategory, filter);
				}
			});
		}
	},
	initializeSliderFilter : function(productCategory, filterData){
		var name = filterData.name!= undefined? filterData.name.capitalize() : "",
				min = filterData.min ? filterData.min : 0,
				max = filterData.max ? filterData.max : 0,
				selectedMin = filterData.selectedMin ? filterData.selectedMin : min,
				selectedMax = filterData.selectedMax ? filterData.selectedMax : max;
			
			// Range Slider start
			var selector = $("#js-" + productCategory+ "-link .planFilter .sliders ." + name + "Filter");
			if(name == '') {
				selector = $("#js-" + productCategory+ "-link .planFilter .sliders [class*=Filter]");
			}
			if(selector.hasClass('ui-slider')){
				selector.slider( "destroy" );
				selector.empty();
			}
			selector.slider({
				range: true,
				min: min,
				max: max,
				disabled: (min == max),
				values: [selectedMin, selectedMax],
				slide: function( event, ui ) {
					//$( "#amount" ).val( "$" + ui.values[0] + " - $" + ui.values[1] );
					var filterType = $(this).data('filterType');
					if(filterType == 'currency'){
						$(ui.handle).text(GlobalVars["currency"] + ui.value);
					}else if(filterType == 'percentage'){
						$(ui.handle).text(ui.value + '%');
					}
					$(ui.handle).next().val(ui.value);
				},
				create: function( event, ui ) {
					var firstA = $(this).find('a').first();
					var lastA = $(this).find('a').last();
					var filterType = $(this).data('filterType');
					if(filterType == 'currency'){
						firstA.text(GlobalVars["currency"] + $(this).slider("values", 0));
						lastA.text(GlobalVars["currency"] + $(this).slider("values", 1));
					}else if(filterType == 'percentage'){
						firstA.text($(this).slider("values", 0)+'%');
						lastA.text($(this).slider("values", 1)+'%');
					}
					$('<input type="hidden" name="filter_' + filterData.name + '_min"' + (filterData.selectedMin != null ? ' value="' + filterData.selectedMin + '"' : '') + ' />').insertAfter(firstA); 
					$('<input type="hidden" name="filter_' + filterData.name + '_max"' + (filterData.selectedMin != null ? ' value="' + filterData.selectedMax + '"' : '') + ' />').insertAfter(lastA);
					$(this).parent().show();
				},
				stop: function( event, ui ) {
					Plan.clearInputOnFilterChange(productCategory);
					Plan.populate(productCategory, Plan.populatePlansAndPaginator);
				}
			});
//			$("#js-" + productCategory+ "-link ." + name + "Filter").parent().show();
			
		},
	initializeListFilter : function(productCategory, filterData){
		var name = filterData.name.capitalize(),
		values = filterData.values;
		var filterContainer = $("#js-" + productCategory+ "-link ." + name + "Filter");
		if(filterContainer.is("select")){
			filterContainer.attr('name', 'filter_' + filterData.name);
			$.each(filterData.values, function(index, value){
				filterContainer.append('<option value=' + value + '>' + value + '</option>');
			});
		}else if(filterContainer.hasClass('radio')){
			$.each(filterData.values, function(index, value){
				filterContainer.append('<input type="radio" name='+ "filter_" + filterData.name +' value='+ value +'>'+ value +'</input>');
			});
		}else if(filterContainer.hasClass('checkbox')){
			$.each(filterData.values, function(index, value){
				filterContainer.append('<input type="checkbox" name='+ "filter_" + filterData.name +' value='+ value +'>'+ value +'</input>');
			});
		}else if(name == 'PlanType'){
			filterContainer.find("input[name='planType']").attr('disabled','disabled');
			$.each(filterData.values, function(index, value){
				filterContainer.find("input[name='planType'][value="+ value +"]").removeAttr('disabled');
			});
			$("#js-" + productCategory+ "-link input[name='planType']").click(function() {
				var productCategory = $(this).parents('form').find(".productCategory").val(),
				planTypeNames = "";
				$(this).parents('div.filterGroup').find("input:checkbox[name='planType']:checked").each(function() {
					planTypeNames += this.value;
					planTypeNames += ",";
				});
	
				$("#js-" + productCategory+ "-link input[name='planTypes']").val(planTypeNames);
//				Plan.clearInputOnFilterChange(productCategory);
//				Plan.populate(productCategory, Plan.populatePlansAndPaginator);
				/*if($("#js-" + productCategory+ "-link input[name='planTypes']:not(:disabled)").length == 1){
					
					return false;
				}*/
				Plan.selectOrCheckBoxListener(productCategory , name);	
			});
		}
		
		$("#js-" + productCategory+ "-link ." + name + "Filter").parent().parent().show();
	},
	clearInputOnFilterChange : function(productCategory){
		$("#js-" + productCategory+ "-link .listData .displayStart").val("");
		$("#isBack").val("");
	},
	selectOrCheckBoxListener : function(productCategory , name) {
		$("#js-" + productCategory+ "-link ." + name + "Filter").change(function(){
			var productCategory = $(this).parents('form').find(".productCategory").val();
			Plan.clearInputOnFilterChange(productCategory);
			Plan.populate(productCategory, Plan.populatePlansAndPaginator);
		});
	},
	displayTab : function(tabName){
		var productCategory = tabName.substring(tabName.indexOf('-') + 1, tabName.lastIndexOf('-'));
		if(!$("#" + tabName + " .pageLoaded").val() || $("#" + tabName + " .pageLoaded").val() == "0"){
			Plan.populate(productCategory, Plan.populatePage);
			Plan.initializeWhoIsCovered(productCategory);
			$("#" + tabName + " .pageLoaded").val("1");
		}
	},
	initializeWhoIsCovered : function(productCategory){
		$("#js-" + productCategory+ "-link .coveredMember").change(function(){
			var productCategory = $(this).parents('form').find(".productCategory").val();
			var selectedFamilyMembersId = "";
			$("#js-" + productCategory + "-link .planFilter input[name='coveredMemberId']").each(
					function(index, value) {
						if($(value).is(':checked')) {
							selectedFamilyMembersId += ($(value).val() + ",");
						}
			});
			
			$.ajax({
				url:GlobalVars["app_url"] + "/web/plans/updateSelectedFamilyMembers",
				type:"POST",
				data : {
					"selectedFamilyMembers" : selectedFamilyMembersId
				},
				error : function(data, status, jqXHR){
					alert(data.statusText);
				}
			});
			
			Plan.clearInputOnFilterChange(productCategory);
			Plan.resetFilters(productCategory);
			Plan.populate(productCategory, Plan.populatePage);
		});
	},
	resetFilters : function(productCategory){
		$("#js-" + productCategory+ "-link .planFilter input[name^='filter']").val('');
		$("#js-" + productCategory+ "-link .planFilter input[name='planType']").removeAttr('checked');
	}
};