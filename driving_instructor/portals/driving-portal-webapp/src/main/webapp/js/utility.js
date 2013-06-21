Utility = {
		validateZip : function(zip, successFn, errorFn){
			var isValid = /^[0-9]{5}?$/.test(zip);
            if (isValid){
            	var url = GlobalVars["app_url"] + "/web/utility/validateZipCode/" + zip;
            	var request = $.ajax({
            		url:url,
            		type:"GET",
            		success : function(data, status, jqXHR){
            			if(data){
            				successFn(data);
            			}else{
            				errorFn(data);
            			}
            		},
            		error : function(data, status, jqXHR){
            			errorFn(data);
            		}
            	});
           		
            }else{
            	errorFn();
            }
		},
		
		initializeZipCountyState : function(zipId, countyId, countyParentId, state){
			var zipElement = $("#" + zipId),
			countyElement = $("#" + countyId),
			countyParentElement = $("#" + countyParentId);
			if(state != undefined)
				stateElement = $("#" + state);
			var successFn = function(data){
				zipElement.valid();
//				if(data.length > 1){
				countyParentElement.show();
//				}else{
//					countyParentElement.hide();
//				}
				var options = '';
				$.each(data, function(id, zip) {
					countyElement.append($("<option value=\"" + zip.county +"\">" + zip.county +"</option>"));
				});
				if(state != undefined){
					$("#" + state).val(data[0].stateCode);
					if($("#" + state).length != 0){
						$("#" + state).valid();
					}
				}
			},
			errorFn = function(data){
			//	countyParentElement.hide();
				zipElement.val("");
				countyElement.empty();
				var map = {};
				map[zipElement.attr("name")] = $.i18nMessage("Valid.zip");
				zipElement.closest("form").validate().showErrors(map);
			};
			zipElement.on("blur", function(event){
				$("#" + countyId).empty();
				if(state != undefined)
					$("#" + state).val("");
				if(this.value == undefined || this.value == ''){
					return;
				}
				var response = Utility.validateZip(this.value, successFn, errorFn);
				/*if(response){
					if(response.length > 1){
						countyParentElement.show();
					}else{
						countyParentElement.hide();
					}
					var options = '';
					$.each(response, function(id, zip) {
						countyElement.append($("<option value=\"" + zip.county +"\">" + zip.county +"</option>"));
					});
					stateElement.val(response[0].stateCode);
					stateElement.valid();
				}else{
					countyParentElement.hide();
					zipElement.val("");
					var map = {};
					map[zipElement.attr("name")] = $.i18nMessage("Valid.zip");
					zipElement.closest("form").validate().showErrors(map);
				}*/
			});
			
			if(zipElement.val()){
				//if(zipElement.placeholder().val() && zipElement.placeholder().val() !== zipElement.val()){
				zipElement.trigger('blur');
//				}else if(!zipElement.placeholder().val()){
//					zipElement.blur();
//				}
			}
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
		    var iPipe = 5, /* Ajust the pipe size */
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
		            json.aaData.splice( oCache.iDisplayLength, json.aaData.length );
		             
		            fnCallback(json)
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
		}
		
		
};