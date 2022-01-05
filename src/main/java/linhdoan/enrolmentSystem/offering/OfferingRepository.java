package linhdoan.enrolmentSystem.offering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Integer> {
    @Query("from Offering o where o.unit.unitCode = :unitCode")
    List<Offering> findByUnitCode(@Param("unitCode") String unitCode);
}
