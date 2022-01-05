package linhdoan.enrolmentSystem.assessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
    List<Assessment> findAssessmentByOfferingOfferingId(Integer offeringId);
}
