package org.chainoptim.shared.commonfeatures.location.dto;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LocationDTOMapper {

    private LocationDTOMapper() {}

    public static Location convertCreateLocationDTOToLocation(CreateLocationDTO locationDTO) {
        Location location = new Location();
        location.setAddress(locationDTO.getAddress());
        location.setCity(locationDTO.getCity());
        location.setState(locationDTO.getState());
        location.setCountry(locationDTO.getCountry());
        location.setZipCode(locationDTO.getZipCode());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setOrganizationId(locationDTO.getOrganizationId());

        return location;
    }

    public static Location updateLocationFromUpdateLocationDTO(Location location, UpdateLocationDTO locationDTO) {
        setPropertyWithNullCheck(location, "address", locationDTO.getAddress());
        setPropertyWithNullCheck(location, "city", locationDTO.getCity());
        setPropertyWithNullCheck(location, "state", locationDTO.getState());
        setPropertyWithNullCheck(location, "country", locationDTO.getCountry());
        setPropertyWithNullCheck(location, "zipCode", locationDTO.getZipCode());
        setPropertyWithNullCheck(location, "latitude", locationDTO.getLatitude());
        setPropertyWithNullCheck(location, "longitude", locationDTO.getLongitude());

        return location;
    }

    public static <T> void setPropertyWithNullCheck(T object, String property, Object value) {
        if (value != null) {
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property, object.getClass());
                Method setter = propertyDescriptor.getWriteMethod();
                if (setter != null) {
                    setter.invoke(object, value);
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
