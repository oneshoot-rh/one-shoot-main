package ma.oneshoot.tenantService.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FreeTrialRepository extends JpaRepository<FreeTrial, Long>{
    
}
