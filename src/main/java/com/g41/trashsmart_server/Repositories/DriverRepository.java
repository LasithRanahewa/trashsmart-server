package com.g41.trashsmart_server.Repositories;
import com.g41.trashsmart_server.Enums.Status;
import com.g41.trashsmart_server.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

//import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Select a specific driver using ID
    @Query("SELECT driver FROM Driver driver WHERE driver.id = :id")
    Optional<Driver> findDriverById(Long id);

    // Select a specific driver using first name
    @Query("SELECT driver FROM Driver driver WHERE driver.firstName = :firstName")
    Optional<Driver> findDriverByFirstName(String firstName);

    // Select a specific driver using last name
    @Query("SELECT driver FROM Driver driver WHERE driver.lastName = :lastName")
    Optional<Driver> findDriverByLastName(String lastName);

    // Select a specific driver using email without deleted
    @Query("SELECT driver FROM Driver driver WHERE driver.email = :email")
    Optional<Driver> findDriverByEmail(String email);

    // Select a specific active driver using ID
    @Query("SELECT driver FROM Driver driver WHERE driver.id = :id AND driver.deleted = :deleted")
    Optional<Driver> findDriverById(Long id, Boolean deleted);

    // Select all the drivers in the system active/logically deleted
    @Query("SELECT driver FROM Driver driver WHERE driver.deleted = :deleted")
    List<Driver> findAllDrivers(Boolean deleted);

    // Select all drivers registered with the system
    @Query("SELECT driver FROM Driver driver")
    List<Driver> findAllDriversUnFiltered();

    // Select all the drivers based on the status
    @Query("SELECT d from Driver d WHERE d.status = :status AND d.deleted = false")
    List<Driver> findByStatus(Status status);

    // count the number of drivers
    long count();
}
