package com.g41.trashsmart_server.DTO;
import org.springframework.stereotype.Service;
import com.g41.trashsmart_server.Models.Driver;
import java.util.function.Function;

@Service
public class DriverDTOMapper implements Function<Driver, DriverDTO> {
    @Override
    public DriverDTO apply(Driver driver) {
        return new DriverDTO(
                driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getEmail(),
                driver.getContactNo(),
                driver.getAddress(),
                driver.getRole(),
                driver.getProfileURL(),
                driver.getDob()
                
        );
    }
    
}
