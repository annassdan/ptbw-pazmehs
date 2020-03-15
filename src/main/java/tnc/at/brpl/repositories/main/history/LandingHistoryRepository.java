package tnc.at.brpl.repositories.main.history;

import org.springframework.data.repository.PagingAndSortingRepository;
import tnc.at.brpl.models.main.history.LandingHistory;

public interface LandingHistoryRepository extends PagingAndSortingRepository<LandingHistory, String> {
}
