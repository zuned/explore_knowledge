var map;
var geocoder=new google.maps.Geocoder();;
var distanceMatrixService;
var origin ="";
var originLatLong; 
var directionsService = new google.maps.DirectionsService();
var directionsDisplay = new google.maps.DirectionsRenderer();
function initialize() {
    var latlng = new google.maps.LatLng(39.8017, 89.6436);
    var mapOptions = {
      zoom: 8,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
	
	if(jQuery.type($("#userAddress").val())!=""){
		origin= $("#userAddress").val()+", ";
	}
	if(jQuery.type($("#userAddress").val())!=""&&jQuery.type($("#userZip").val()!="")){
		origin = origin + $("#userZip").val();
	}else{
		origin=$("#userZip").val();
	}
    map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);
	directionsDisplay.setMap(map);
  }
  
    function getDirection(destingationLatLong){
        
        var request = {
            origin:originLatLong,
            destination:destingationLatLong,
            travelMode: google.maps.DirectionsTravelMode.DRIVING
        };
        directionsService.route(request, function(response, status) {
          if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
          }
        });
      }
  
  function getAddressProviderMap(providers){
  var addressMap = new Object();
  var index=0;
	for(var j=0; j<providers.length;j++){		
	addressMap[providers[j].providerId+ "-"+providers[j].providerType] = findAddressStr(providers[j].contactInfoDTO);		
  }
  return addressMap;
  }
  
  
  function findAddressStr(address){
  var addressStr =""; 
	addressStr=addressStr.concat(address.addressLine1);
	addressStr=addressStr.concat(", ");
	addressStr=addressStr.concat(address.addressLine2);
	addressStr=addressStr.concat(", ");
	addressStr=addressStr.concat(address.state);
	addressStr=addressStr.concat(", ");
	addressStr=addressStr.concat(address.city);
	addressStr=addressStr.concat(", ");
	addressStr=addressStr.concat(address.zip);
	addressStr=addressStr.concat(", ");
	addressStr=addressStr.concat("US");
	return addressStr
  }
  
  // function calculateDistance(providers){
  // var addressArr = getAddressArray(providers);
	// initialize();
	// var distanceArr=new Array();
	// populateMap(addressArr);
	// if(origin!=""){
	 // distanceMatrixService = new google.maps.DistanceMatrixService();
	 
	// var distanceValue = distanceMatrixService.getDistanceMatrix({destinations: addressArr,
											// origins:[origin],
											// travelMode: google.maps.TravelMode.DRIVING,
											// unitSystem: google.maps.UnitSystem.IMPERIAL}, 
											// function(distanceMatrixResponse,distanceMatrixStatus)
  // {
  // if(distanceMatrixStatus == google.maps.DistanceMatrixStatus.OK){
  // var destinations = distanceMatrixResponse.destinationAddresses;
  // var results = distanceMatrixResponse.rows[0].elements;
      // for (var j = 0; j < results.length; j++) { 
        // var distanceText= results[j].distance.text;
		// distanceArr[j]=distanceText.split(" ")[0];
     // }
  // }});
  // }
	// return distanceArr;
  // }
  
  function populateMap(addressMap){
  initialize();
  if(origin!=""){
	geocoder.geocode( { 'address': origin}, function(result, status) {
      if (status == google.maps.GeocoderStatus.OK) {
		originLatLong = result[0].geometry.location;
		addMarker(result[0]);
			}else {
        alert("Geocode was not able to find " + origin);
      }});
	  }
  var latlangArr = new Object;
	for(var key in addressMap){
	geocoder.geocode( { 'address': addressMap[key]}, function(result, status) {
      if (status == google.maps.GeocoderStatus.OK) {
			addMarker(result[0]);
			latlangArr[key] =  result[0].geometry.location;
      } else {
        alert("Geocode was not able to find: " + addressMap[key]);
      }
    });
	return latlangArr;
  }}
  
 
  function addMarker(result){
        map.setCenter(result.geometry.location);
        var marker = new google.maps.Marker({
            map: map,
            position: result.geometry.location
        });
	}
 
