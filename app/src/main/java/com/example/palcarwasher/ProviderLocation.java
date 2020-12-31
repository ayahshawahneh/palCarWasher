package com.example.palcarwasher;

public class ProviderLocation {

   String locationId;
   String providerId;
   String latitudeX;
   String longitudeY;

   public ProviderLocation() {
   }


   public ProviderLocation(String locationId, String providerId, String latitudeX, String longitudeY) {
      this.locationId = locationId;
      this.providerId = providerId;
      this.latitudeX = latitudeX;
      this.longitudeY = longitudeY;
   }


   public String getLocationId() {
      return locationId;
   }

   public void setLocationId(String locationId) {
      this.locationId = locationId;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String getLatitudeX() {
      return latitudeX;
   }

   public void setLatitudeX(String latitudeX) {
      this.latitudeX = latitudeX;
   }

   public String getLongitudeY() {
      return longitudeY;
   }

   public void setLongitudeY(String longitudeY) {
      this.longitudeY = longitudeY;
   }
}
