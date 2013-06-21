Provider = {
	providerData : {},
	providerLatLongMap : {},
	template : {},
	productCategory : "provider-search",
	displayLength : 10,
	contextPath:'',
	onProviderSelection:'',
	isPCPSearch:false,
	printAllProviders : function(providers){
		console.log(providers);
	},
	selectProvider : function(callbackFunc){
		 var selectedProviders = $("input[name*='providerSelect']").find(":checked");
		 return selectedProviders;
	},
	// To search providers and get all provider data.
	searchProviders : function(callbackFunc) {
		// $("#js-" + productCategory + "-link .dataList > ul").empty();
		if (Provider.isValidZip()) {
			var queryString = Provider.getQueryString();
			$.ajax({
				url : Provider.contextPath + "/provider/getProviders?" + queryString,
				async : false,
				type : "GET",
				success : function(data, status, jqXHR) {
					Provider.providerData = data;
					callbackFunc(Provider.productCategory, data);
				},
				error : function(data, status, jqXHR) {

				}
			});
		}
	},
	getQueryString : function() {
		var queryArray = $("#provider-search-fields").serializeArray();
		queryArray = $.grep(queryArray, function(query) {
			if (query.value === "") {
				return false;
			}
			return true;
		});
		return $.param(queryArray);

	},
	createPaginator : function(productCategory, totalRecords) {

		$("#js-" + productCategory + "-link").smartpaginator({
			totalrecords : totalRecords,
			recordsperpage : 10,
			controlsalways : false,
			next : 'Next',
			prev : 'Prev',
			first : 'First',
			last : 'Last',
			length : 4,
			display : 'single',
			datacontainer : 'providersTable',
			dataelement : 'tr',
			// onchange : function(newPage) {
			//						
			//						
			// $("#maxResults").val(Provider.displayLength);
			// $("#startIndex").val(Provider.displayLength * (newPage - 1));
			// Provider.searchProviders(Provider.populateProviders);
			// },
			theme : 'black'
		});
	},

	// To Populate Providers
	populateProviders : function(productCategory, providers) {
		var template = Provider.getTemplate(productCategory);
		var tbody = $("#providersTable tbody");
		tbody.empty();
		if(providers.length < 1){
			tbody.append("<tr><td>" +$("#noResultText").val() +"</td></tr>");
		}
		// distanceArr=calculateDistance(providers);
		var populatedProvider=0;
		$.each(providers, function(index, provider) {
			if (provider.providerType == 'DOCTOR') {
					if(jQuery.type(provider.providerDTOs)==="null"){
						tbody.append(template(provider));
						populatedProvider++;
					}else{
			 			$.each(provider.providerDTOs, function(index,midLevelProvider) {
						provider.midLevelProviderId = midLevelProvider.providerId;
					    provider.midLevelFirstName = midLevelProvider.firstName;
					 provider.midLevelMiddleName = midLevelProvider.middleName;
					 provider.midLevelLastName = midLevelProvider.lastName;
					 provider.midLevelGender = midLevelProvider.gender;
					 provider.midLevelTitle = midLevelProvider.title;
					 tbody.append(template(provider));
					 populatedProvider++;
				 });

				// $.each(provider.providerDTOs, function(index, mainProvider) {
					// mainProvider.midLevelFirstName = provider.firstName;
					// mainProvider.midLevelMiddleName = provider.middleName;
					// mainProvider.midLevelLastName = provider.lastName;
					// mainProvider.midLevelGender = provider.gender;
					// mainProvider.midLevelTitle = provider.title;
					// tbody.append(template(mainProvider));
					// populatedProvider++;
				// });
			} }else {
				tbody.append(template(provider));
				populatedProvider++;
			}
			
		});
			return populatedProvider;
	},
	// Populate paginated providers
	populatePaginatedProviders : function(providerCategory, providers) {
		if(providers.length > 0 && Provider.isValidZip()){
			$("#displayNearZipLabel").show();
			$("#zipDisplay").html($("#zipCode").val());
		}
		
		var providerCount=Provider.populateProviders(providerCategory, providers);
		Provider.createPaginator(providerCategory, providerCount);
		Provider.createMap(providers);
	},
	getTemplate : function(productCategory) {
		if (!Provider.template[productCategory]) {
			Provider.template[productCategory] = Handlebars.compile($(
					"#provider_list_template").html());
		}
		return Provider.template[productCategory];
	},
	initializeAutoComplete : function() {
		$("#searchText")
				.autocomplete(
						{
							source : function(request, response) {
							if (Provider.isValidZip()) {
								var queryString = Provider.getQueryString();
								var searchText = queryString[searchText];
								$.ajax({
											url : Provider.contextPath
													+ "/provider/searchProviderName?"
													+ queryString,
											dataType : "json",
											data : {
												maxRows : 12
												
											},
											success : function(data) {
												if(data.length > 0){
												response($.map(data,function(item) {
																	var title = '';
																	if (item.providerType == 'HOSPITAL') {
																		title = item.name;
																	} else{
																	if(jQuery.type(item.providerDTOs)==="null") {
																		title = item.firstName
																				+ " "
																				+ item.middleName+" "
																				+ item.lastName;
																	}else{
																		title =  item.providerDTOs[0].firstName
																				+ " "
																				+ item.providerDTOs[0].lastName;
																	}}
																	return {
																		label : title,
																		value : title
																	}
																}));
												}else{
													response([{label: $("#noResultText").val(), category:"", href:""}]);
												}
											}
										});
									}
							},
							minLength : 2,
							select : function(event, ui) {
								if(ui.item.label == $("#noResultText").val()){
//							        $(ui.item).disable();
								}else{  
//									 $(ui.item).enable();
									$('#searchText').val(ui.item.label);
									Provider.searchProviders(Provider.populatePaginatedProviders);
								}
							},
							open : function() {
								$(this).removeClass("ui-corner-all").addClass(
										"ui-corner-top");
							},
							close : function() {
								$(this).removeClass("ui-corner-top").addClass(
										"ui-corner-all");
							},focus: function(event, ui){ 
							    if(ui.item.label == $("#noResultText").val()){
//							        $(ui.item).disable();
							    }else{
//							    	$(ui.item).enable();
							    }
							}
						});

	},

	bindUI : function() {
		$("#displayNearZipLabel").hide();

		$('#providerHospital')
				.click(
						function() {
							var thisCheck = $(this);
							// Clear and Disable non applicable filters
							if (thisCheck.is(':checked')) {
								// $("#genderFilter, #languageFilter,
								// #affiliationsFilter").unbind('click');
								$(
										"#genderFilter, #languageFilter, #affiliationsFilter")
										.bind('click', function(e) {
											e.preventDefault();
											return false;
										});
								$(
										"#genderFilter, #languageFilter, #affiliationsFilter")
										.parent().find('.filterData').toggle(
												false);
								$('input[name="affiliations"]').attr('checked',
										false);
								$('input[name="gender"]')
										.attr('checked', false);
								$('input[name="languages"]').attr('checked',
										false);

							}
							// Enable all filters.
							else {
								$("#genderFilter").unbind('click');
								$("#languageFilter").unbind('click');
								$("#affiliationsFilter").unbind('click');
							}
						});
		$("#js-selectProvider").click(function() {
					var selectedProviders = Provider.getSelectedProviders(Provider.printAllProviders);
					Provider.onProviderSelection(selectedProviders);
				});
		$("#js-back").click(function() {
			document.location.href = $("#backButtonURL").val();
		});

		$("input[name='providerSelect']")
				.live(
						'click',
						function() {
							var thisCheck = $(this);
							// Clear and Disable non applicable filters
							if (thisCheck.is(':checked')) {
								var destLocation = Provider.providerLatLongMap[thisCheck
										.attr("id")];
								getDirection(destLocation);

							}

						});
		$('#zipCode').keyup(function(e){
			if(e.keyCode == 13 || Provider.isValidZip())
			{
			  Provider.searchProviders(Provider.populatePaginatedProviders);
			}
		});				
	},
	getSelectedProviders : function(callBackFunc) {
		var selectedProviders;
		$("input[name='providerSelect']:checked").each(function(index,providerElement) {
			selectedProviders[index] = providerElement.attr("id");
		});
		callBackFunc(selectedProviders);
		return false;
	},
	initializeFiltersUI : function() {

		$('#js-addFilter').on(
				'click',
				function() {
					$('.filterBlock').slideToggle('slow');
					$('span.ui-icon-plusthickwhite', '#js-addFilter')
							.toggleClass('ui-icon-minusthick');
					return false;
				});
		$('.filterBlock').on('click', 'ul li a', function() {
			var self = $(this);
			// self.find('span').toggleClass('icon-plus-sign icon-minus-sign');
			self.parent().find('.filterData').toggle('fast');
			return false;
		});
		$('.filterTitleBox span').click(function() {
			$(this).parents('li').find('a').trigger('click');
		});
		$(
				'.filterContent input[type=button], .filterContent input[type=submit]')
				.click(
						function() {
							$(this).parents('li').find('a').trigger('click');
							Provider
									.searchProviders(Provider.populatePaginatedProviders);
						});
		// IE 7 zindex issue Fixed
		$('.filterBlock ul li').each(function(index, element) {
			$(this).css('z-index', 1000 - index);
		});
		// $('#js-selectProvider, #js-back').on('click', function() {
		// document.location.href = 'plan-listing-registered-user.html';
		// });
		$(".btn-navbar").live("click", function() {
			var o = $(this).next();
			o.slideToggle(function() {
				if (o.is(":hidden")) {
					o.removeAttr("style");
				}
			});
		}).siblings('ul').find('li').bind('click', function(e) {
			var o = $('ul', this);
			o.parents('ul').find('li ul').slideUp();
			o.slideToggle();
			if (o.is(":hidden")) {
				o.removeAttr("style");
			}
		});
		if(Provider.isPCPSearch){
			$("#providerTypeFilter").bind('click', function(e) {
											e.preventDefault();
											return false;
										});
								$("#providerTypeFilter")
										.parent().find('.filterData').toggle(
												false);
								$('input[name="providerTypes"]').attr('checked',
										false);
		}
		
		$(document).ready(function(){
		if($("#userZip").val()!==""){
			$("#zipCode").val($("#userZip").val());
		}
		if($("#userSearchText").val()!==""){
			$("#searchText").val($("#userSearchText").val());
		}
		if($("#userZip").val()!==""||$("#userSearchText").val()!==""){
			Provider.searchProviders(Provider.populatePaginatedProviders);
		}
		});
		$(document)
				.on(
						'click',
						function(event) {
							if (!($(event.target)
									.is($("nav.nav ul, ul.topLinks, .btn-navbar, div.options")))
									&& $('.btn-navbar').is(':visible')) {
								var o = $("nav.nav ul, ul.topLinks, .options");
								o.slideUp(function() {
									o.removeAttr("style");
								});
							}
						});
	},

	createMap : function(providers) {
		var addressMap = getAddressProviderMap(providers);
		Provider.providerLatLongMap = populateMap(addressMap);
	},
	isValidZip:function(){
		var isValid =true;
		if($("#zipCode").val().length > 0){
			isValid = $("#provider-search-fields").validate().element("#zipCode");
		} 
		return isValid;
	}

};

Handlebars.registerHelper('isHospital', function(block) {
	if (this.providerType == "HOSPITAL") {
		return block.fn(this);
	} else {
		return block.inverse(this);
	}
});



Handlebars.registerHelper('isMidLevelProvider', function(block) {
	if (this.providerType == "DOCTOR"&&jQuery.type(provider.providerDTOs)!=="null") {
		return block.fn(this);
	} else {
		return block.inverse(this);
	}
});
