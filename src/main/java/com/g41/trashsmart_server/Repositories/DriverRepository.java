package com.g41.trashsmart_server.Repositories;
import com.g41.trashsmart_server.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;


@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Select a specific driver using email
    @Query("SELECT d FROM Driver d WHERE d.email = :email AND d.deleted = :deleted")
    Optional<Driver> findDriverByEmail(String email, Boolean deleted);

    // Select a specific driver using email without deleted
    @Query("SELECT d FROM Driver d WHERE d.email = :email")
    Optional<Driver> findDriverByEmail(String email);

    // Select a specific driver using id
    @Query("SELECT d FROM Driver d WHERE d.id = :id AND d.deleted = :deleted")
    Optional<Driver> findDriverById(Long id, Boolean deleted);

    // Select all the drivers in the system active/logically deleted
    @Query("SELECT d FROM Driver d WHERE d.deleted = :deleted")
    List<Driver> findAllDrivers(Boolean deleted);

    // Select all the drivers registered with the system
    @Query("SELECT d FROM Driver d")
    List<Driver> findAllDriversUnFiltered();

    // select specific driver using id
    @Query("SELECT d FROM Driver d WHERE d.id = :id")
    Optional<Driver> findDriverById(Long id);
    
}
