let map, infoWindow;
let marker = null;
const locationButton = document.createElement("button");

locationButton.textContent = "Pan to Current Location";
locationButton.classList.add("custom-map-control-button");
async function initMap() {
    const position = { lat: 36.9685735, lng: 10.1219855 };

    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerView } = await google.maps.importLibrary("marker");
    infoWindow = new google.maps.InfoWindow();

    map = new Map(document.getElementById("map"), {
        zoom: 8,
        center: position,
        mapId: "DEMO_MAP_ID",
    });

    marker = new AdvancedMarkerView({
        map: map,
        position: null,
        title: "Uluru",
    });

    let longitudeInput = document.getElementById('longitude');
    let latitudeInput = document.getElementById('latitude');
    let addressInput = document.getElementById('adresse');

    map.addListener("click", async (mapsMouseEvent) => {
        const clickedPosition = mapsMouseEvent.latLng.toJSON();

        // Update the input fields with the clicked coordinates
        longitudeInput.value = clickedPosition.lng.toFixed(7);
        latitudeInput.value = clickedPosition.lat.toFixed(7);

        // Reverse geocode the coordinates to get the address
        const geocoder = new google.maps.Geocoder();
        geocoder.geocode({ 'location': clickedPosition }, function(results, status) {
            if (status === 'OK') {
                if (results[0]) {
                    // Split the formatted address at the first comma and keep the part after it
                    const addressParts = results[0].formatted_address.split(',', 2);
                    if (addressParts.length > 1) {
                        addressInput.value = addressParts;
                    } else {
                        addressInput.value = results;
                    }
                } else {
                    addressInput.value = "Address not found";
                }
            } else {
                addressInput.value = "Geocoding failed: " + status;
            }
        });

        if (!marker) {
            marker = new AdvancedMarkerView({
                map: map,
                position: clickedPosition,
            });
        } else {
            marker.position = clickedPosition;
        }
    });

    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    locationButton.addEventListener("click", () => {
        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude,
                    };

                    infoWindow.setPosition(pos);
                    infoWindow.setContent("Location found.");
                    infoWindow.open(map);
                    map.setCenter(pos);
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                },
            );
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(
        browserHasGeolocation
            ? "Error: The Geolocation service failed."
            : "Error: Your browser doesn't support geolocation.",
    );
    infoWindow.open(map);
}
window.initMap = initMap;
initMap();