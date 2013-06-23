jQuery.download = function(url, data, method){
	//url and data options required
	if( url && data ){ 
		//data can be string of parameters or array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		//split params into form inputs
		var inputs = '';
		jQuery.each(data.split('&'), function(){ 
			var pair = this.split('=');
			inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
		});
		//send request
		jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
		.appendTo('body').submit().remove();
	};
};

ManageDrivingInstructor={
		createDataTable:function(documentObj){
			var tableOptions = {
					 "aaSorting":[[0, "asc"]],
					 "aoColumns": [
									{ 
										"mDataProp": "id",
										"sWidth":"10%"
									},
									{ 
										"mDataProp": "name",
										"sWidth":"15%"
									},
									{ 
										"mDataProp": "memberType",
										"sWidth":"12%"
									},
									{ 
										"mDataProp": "dateOfRegistration",
										"sWidth":"15%"
									},
									{ 
										"mDataProp": "contactPrefrence",
										"sWidth":"10%"
									},
									{
										"mDataProp": "licensce",
										"sWidth":"10%"
									},
									{
										"mDataProp": "areaCodes",
										"sWidth":"15%"
									}
								],
								"aoColumnDefs":[
												{
													"fnRender": function(oObj)
												    	{
															var id = oObj.aData.id;
															
															var edit="<a href="+"sp/" + id + " title="+"'edit'"+ "class="+"'icon-edit hide-text'"+"></a>";
															var dlt="<a id='"+id+"'"+ "title="+"'delete'"+ "href="+"'javascript:void(0)'"+"class="+"'icon-trash hide-text removeRow'"+"> </a>";
															return "&nbsp;"+"&nbsp;"+edit+"&nbsp;"+"&nbsp;" +dlt;
												    	},
												    	"aTargets" :[7]
													}
											],
									"fnRowCallback" : function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
										$(nRow).attr('data-sp-id',aData.id);
										return nRow;
									},
						             
			},
			url = GlobalVars["app_url"]+"/admin/sp/list",
			validation = { "rules":{}, "messages":{} };
			DataTableUtils.createDataTable("serviceProviderTable" , "serviceProviderTableSearchForm",tableOptions, url, validation);
			 $("#js-add_service_provider").click(function() {
				window.location = GlobalVars["app_url"] +"/admin/sp";
			});
			 
			 $('.removeRow').live('click', function() {
				 $('#popup').dialog('open');
				 $("#serviceProviderId").val($(this).attr('id'));
			 });
		}
		
};

Report =
{
		initReport: function(){
			var tableDiv ,  dataTableUsed , aoColumnUsed;
			$("#dropDown").change(function(){
				$(".dateOptional").datepicker('option',{maxDate:null,minDate:null});
				$(".dateOptionalFrom").datepicker('option',{maxDate:null,minDate:null});
				$(".dateOptionalTo").datepicker('option',{maxDate:null,minDate:null});
				var index=$(this).val();
				Report.clearTable();
				Report.clearFilter();
				switch(index)
 				{
					case "0":
						break;
					case "popular_service_provider":
						$("#filterDivNew").show();
						$("#serviceProviderFilter").show();
						$("#dateRangeFilter").show();
						tableDiv = $("#popular_service_providerTableDiv");
						dataTableUsed = $("#popular_service_providerTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Service Provider Id",
											"sWidth":"16%",
											"sTitle": "Service Provider Id"
										},
										{ 
											"mDataProp": "Service Provider Name",
											"sWidth":"16%",
											"sTitle": "Service Provider Name"
										},
										{ 
											"mDataProp": "Membership Type",
											"sWidth":"16%",
											"sTitle": "Membership Type"
										},
										{ 
											"mDataProp": "Date Of Registration",
											"sWidth":"16%",
											"sTitle":"Date Of Registration"
										},
										{ 
											"mDataProp": "Click Count",
											"sWidth":"20%",
											"sTitle": "Click Count"
										}
						];
						break;
					case "least_popular_service_provider":
						$("#filterDivNew").show();
						$("#serviceProviderFilter").show();
						$("#dateRangeFilter").show();
						tableDiv = $("least_popular_service_providerTableDiv");
						dataTableUsed = $("least_popular_service_providerTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Service Provider Id",
											"sWidth":"16%",
											"sTitle": "Service Provider Id"
										},
										{ 
											"mDataProp": "Service Provider Name",
											"sWidth":"16%",
											"sTitle": "Service Provider Name"
										},
										{ 
											"mDataProp": "Membership Type",
											"sWidth":"16%",
											"sTitle": "Membership Type"
										},
										{ 
											"mDataProp": "Date Of Registration",
											"sWidth":"16%",
											"sTitle":"Date Of Registration"
										},
										{ 
											"mDataProp": "Click Count",
											"sWidth":"20%",
											"sTitle": "Click Count"
										}
									];
						break;
					case "areas_need_promotion":
						$("#filterDivNew").show();
						$("#serviceProviderFilter").show();
						$("#dateRangeFilter").show();
						tableDiv = $("areas_need_promotionTableDiv");
						dataTableUsed = $("areas_need_promotionTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Post Code",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Area Name",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Service Name",
											"sWidth":"20%"
										},
										{
											"mDataProp": "Click Count",
											"sWidth":"20%"
										}
									
										];
						break;
					case "most_popular_area":
						$("#filterDivNew").show();
						$("#serviceProviderFilter").show();
						$("#dateRangeFilter").show();
						tableDiv = $("most_popular_areaTableDiv");
						dataTableUsed = $("most_popular_areaTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Post Code",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Area Name",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Service Name",
											"sWidth":"20%"
										},
										{
											"mDataProp": "Click Count",
											"sWidth":"20%"
										}
									
										];
						break;
					case "least_popular_areas":
						$("#filterDivNew").show();
						$("#serviceProviderFilter").show();
						$("#dateRangeFilter").show();
						tableDiv = $("least_popular_areasTableDiv");
						dataTableUsed = $("least_popular_areasTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Post Code",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Area Name",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Service Name",
											"sWidth":"20%"
										},
										{
											"mDataProp": "Click Count",
											"sWidth":"20%"
										}
										];
						break;
					case "service_providers_report":
						$("#filterDivNew").show();
						$("#dateRangeFilter").show();
						$("#serviceProviderIdFilter").show();
						tableDiv = $("service_providers_reportTableDiv");
						dataTableUsed = $("service_providers_reportTable");
						aoColumnUsed = [
										{ 
											"mDataProp": "Service Provider Id",
											"sWidth":"16%"
										},
										{ 
											"mDataProp": "Service Provider Name",
											"sWidth":"16%"
										},
										{ 
											"mDataProp": "Membership Type",
											"sWidth":"16%"
										},
										{ 
											"mDataProp": "Date Of Registration",
											"sWidth":"16%"
										},
										{ 
											"mDataProp": "Post Code",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Service Name",
											"sWidth":"20%"
										},
										{ 
											"mDataProp": "Total Hits Received",
											"sWidth":"20%"
										},
										{
											"mDataProp": "Total Email Received",
											"sWidth":"16%"
										}
									
										];
						break;
					default:
				}
			});
			
			
			$("#searchReport").click( function() {
				if($("#dropDown").val()==0)
					alert("Please select a report");
				else
				{
					var url = GlobalVars["app_url"] + "/admin/reports/list";
					
					exportButtons=$('#exportButtons');
					var tableOptions = {
							"aaSorting":[[0, "asc"]],
							"aoColumns":aoColumnUsed ,
							 "fnDrawCallback": function( oSettings ) {
						        	$(tableDiv).show();
									$(".dateOptional").datepicker('option',{maxDate:null,minDate:null});
									$(".dateOptionalTo").datepicker('option',{maxDate:null,minDate:null});
									$(".dateOptionalFrom").datepicker('option',{maxDate:null,minDate:null});
						        }
					},validation = { "rules":{}, "messages":{} };
					$("#tableDiv").show();
					$("#"+$("#dropDown").val()+"TableDiv").show();
					exportButtons.show();
					DataTableUtils.createDataTable($("#dropDown").val() , "adminReportsSearchForm",tableOptions, url, validation);
//					var oTable = $(dataTableUsed).dataTable({
//						"bDestroy":true,
//						"bPaginate": true,
//						// "bFilter": true,
//						"bServerSide": true,
//				        "fnServerData": DataTableUtils.fnDataTablesPipeline,
//				        "sAjaxSource": url,
//						"iDisplayLength":10,
//				        "sPaginationType": "full_numbers",
//				        "aaSorting":[[0, "asc"]],
//				        "oLanguage": {
//							"oPaginate": {
//								"sFirst": "",
//								"sNext": "",
//								"sPrevious": "",
//								"sLast": ""
//							},
//							"sEmptyTable": "No records in the table."
//						},
//						"bAutoWidth":false,
//						"aoColumns": aoColumnUsed,
//				        "fnServerParams": function(aoData)
//				        {
//							var serializedForm = $("#adminReportsForm").formParams(false),
//							key = null;
//							for(key in serializedForm)
//							{
//								if(serializedForm.hasOwnProperty(key))
//								{
//									aoData.push({"name":key, "value":serializedForm[key]});
//								}
//							}
//				        },
//				        "fnDrawCallback": function( oSettings ) {
//				        	$(tableDiv).show();
//							$(".dateOptional").datepicker('option',{maxDate:null,minDate:null});
//							$(".dateOptionalTo").datepicker('option',{maxDate:null,minDate:null});
//							$(".dateOptionalFrom").datepicker('option',{maxDate:null,minDate:null});
//				        },
//						"sDom":"tlip"
//					});
				}
			});
			$("#exportXLS").click(
					function()
					{
						data=$("#adminReportsSearchForm").serialize();
						var url = GlobalVars["app_url"] + "/admin/reports/export";
						$.download(url,data+"&format="+ "XLS",'GET' );
					}
			);
			$("#exportPDF").click(
					function()
					{
						data=$("#adminReportsSearchForm").serialize();
						var url = GlobalVars["app_url"] + "/admin/reports/export";
						$.download(url,data+"&format="+ "PDF",'GET' );
					}
			);
		},
		clearTable : function() {
			$("#popular_service_providerTableDiv").hide();
			$("#least_popular_service_providerTableDiv").hide();
			$("#areas_need_promotionTableDiv").hide();
			$("#most_popular_areaTableDiv").hide();
			$("#least_popular_areasTableDiv").hide();
			$("#service_providers_reportTableDiv").hide();
		},
		clearFilter :  function() {
			$("#filterDivNew").hide();
			$("#serviceProviderFilter").hide();
			$("#dateRangeFilter").hide();
			$("#serviceProviderIdFilter").hide();
		}
};


var DataTableUtils = 
{
		 oCache : {
				    iCacheLower: -1
				},
		fnGetKey: function( aoData, sKey )
		{
			for ( var i=0, iLen=aoData.length ; i<iLen ; i++ )
		    {
		        if ( aoData[i].name == sKey )
		        {
		            return aoData[i].value;
		        }
		    }
		    return null;		
					
		},
		fnSetKey: function( aoData, sKey, mValue )
		{
			for ( var i=0, iLen=aoData.length ; i<iLen ; i++ )
		    {
		        if ( aoData[i].name == sKey )
		        {
		            aoData[i].value = mValue;
		        }
		    }
			
		},
		fnDataTablesPipeline: function ( sSource, aoData, fnCallback ) {
		    var iPipe = 1, /* Ajust the pipe size */
		    bNeedServer = false,
		    sEcho = DataTableUtils.fnGetKey(aoData, "sEcho"),
		    iRequestStart = DataTableUtils.fnGetKey(aoData, "iDisplayStart"),
		    iRequestLength = DataTableUtils.fnGetKey(aoData, "iDisplayLength"),
		    iRequestEnd = iRequestStart + iRequestLength,
		    oCache = DataTableUtils.oCache;
		    oCache.iDisplayStart = iRequestStart;
		     
		    /* outside pipeline? */
		    if ( oCache.iCacheLower < 0 || iRequestStart < oCache.iCacheLower || iRequestEnd > oCache.iCacheUpper )
		    {
		        bNeedServer = true;
		    }
		     
		    /* sorting etc changed? */
		    if ( oCache.lastRequest && !bNeedServer )
		    {
		        for( var i=0, iLen=aoData.length ; i<iLen ; i++ )
		        {
		            if ( aoData[i].name != "iDisplayStart" && aoData[i].name != "iDisplayLength" && aoData[i].name != "sEcho" )
		            {
		                if ( aoData[i].value != oCache.lastRequest[i].value )
		                {
		                    bNeedServer = true;
		                    break;
		                }
		            }
		        }
		    }
		     
		    /* Store the request for checking next time around */
		    oCache.lastRequest = aoData.slice();

		    if ( !bNeedServer ) {
		    	 json = jQuery.extend(true, {}, oCache.lastJson);
		    	 if(json.aaData == undefined) {
		    		 bNeedServer = true;
		    	 }
		    }
		     
		    if ( bNeedServer )
		    {
		        if ( iRequestStart < oCache.iCacheLower )
		        {
		            iRequestStart = iRequestStart - (iRequestLength*(iPipe-1));
		            if ( iRequestStart < 0 )
		            {
		                iRequestStart = 0;
		            }
		        }
		         
		        oCache.iCacheLower = iRequestStart;
		        oCache.iCacheUpper = iRequestStart + (iRequestLength * iPipe);
		        oCache.iDisplayLength = DataTableUtils.fnGetKey( aoData, "iDisplayLength" );
		        DataTableUtils.fnSetKey( aoData, "iDisplayStart", iRequestStart );
		        DataTableUtils.fnSetKey( aoData, "iDisplayLength", iRequestLength*iPipe );
		         
		        $.getJSON( sSource, aoData, function (json) { 
		            /* Callback processing */
		            oCache.lastJson = jQuery.extend(true, {}, json);
		             
		            if ( oCache.iCacheLower != oCache.iDisplayStart )
		            {
		                json.aaData.splice( 0, oCache.iDisplayStart-oCache.iCacheLower );
		            }
		            if(json.aaData.length > 1 ){
		            	json.aaData.splice( oCache.iDisplayLength, json.aaData.length );
		            }
		            DataTableUtils.handleSearchErrors(json.errors);
		            fnCallback(json);
		        } );
		    }
		    else
		    {
		        json = jQuery.extend(true, {}, oCache.lastJson);
		        json.sEcho = sEcho; /* Update the echo for each response */
		        json.aaData.splice( 0, iRequestStart-oCache.iCacheLower );
		        json.aaData.splice( iRequestLength, json.aaData.length );
		        fnCallback(json);
		        return;
		    }
		},
		handleSearchErrors: function(errors)
		{
			var errorsContainer = $("div.error-message");
			errorsContainer.find("p").empty();
			if(errors)
			{
				var key;
				for(key in errors)
				{
					var pContainer = $("#err_"+key);
					pContainer.html(errors[key]);
				}
				errorsContainer.show();
			}
			else
			{
				errorsContainer.hide();
			}
			
		},
		createDataTable: function(tableId,searchFormId , tableParams, url, validation)
		{
			var tableObj = $("#"+tableId),
			searchForm = $("#"+searchFormId),
//			validationConfig = $.extend({rules:{},messages:{}}, validation),
//			errorsContainer = $("div.error-message"),
			defaultOptions = {
					"bDestroy":true,
					"bProcessing": false,
			        "bServerSide": true,
			        "fnServerData": DataTableUtils.fnDataTablesPipeline,
			        "sAjaxSource": url,
			        "iDisplayLength":10,
			        //"bPaginate": true, 
					"sPaginate": true,
			        "sPaginationType": "full_numbers",
			        "oLanguage":{
			        	"sEmptyTable": "No data available in table" ,
			        	"sLoadingRecords": "Please wait - loading...",
			        	"sZeroRecords": "No records to display",
						"sInfo":"<span>_START_ to _END_</span><span class='of'>of</span><span> _TOTAL_</span>",
						"sInfoEmpty": "<span>0 to 0 </span><span>of</span><span> 0 </span>"
					},
					"fnDrawCallback" : function(
							oSettings) {
						$("#waiting_a").hide();
						$(".dataTables_filter").hide();
						$(".dataTables_length").hide();
						$("#"+ tableId +"_info").addClass("pull-left");
						$("#" +tableId+ "_paginate").addClass("pull-right clr");
						$(".paginate_button").addClass("btn btn-secondry btn-mini marginR5");
						$(".pagination-mini").hide();
					},
			        "fnServerParams": function(aoData)
			        {
						var serializedForm = searchForm.formParams(false),
						key = null;
						for(key in serializedForm)
						{
							if(serializedForm.hasOwnProperty(key))
							{
								aoData.push({"name":key, "value":serializedForm[key]});
							}
						}
			        },
			        "sDom":"tlip"
			};
			$.extend($.fn.dataTableExt.oStdClasses,{
				"sPagePrevDisabled":"col1 previous disabled pull-left",
				"sPagePrevEnabled":"col1 previous pull-left",
				"sPageNextEnabled":"col2 next pull-left",
				"sPageNextDisabled":"col2 next disabled pull-left",
				"sPaging":"pull-left dataTables_paginate paging_",
			});
			$.extend(defaultOptions, tableParams);
			tableObj.dataTable(defaultOptions);
		
//			tableObj.fnDraw();
//			searchForm.validate({
//				rules:validationConfig.rules,
//				messages:validationConfig.messages,
//				highlight: function(elem)
//				{
//					errorsContainer.show()
//				},
//				success: function(errorElement)
//				{
//					if(errorsContainer.find("label:visible").length == 0)
//						errorsContainer.hide();
//				},
//				submitHandler:function(){
//					tableObj.fnDraw();
//				}
//			});
			return tableObj;
		}
};
