package ma.oneshoot.oneshootmain.upload;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UploadReactiveRepository extends ReactiveCrudRepository<Upload,Long> {


    @Query("SELECT * FROM uploads where userId= :userId")
    Flux<Upload> findUploadByUserId(Long userId);
}
