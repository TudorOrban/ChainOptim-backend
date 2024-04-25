package org.chainoptim.shared.commonfeatures.location.service;

public interface GeocodingService {

    Double[] getCoordinates(String address);
}
