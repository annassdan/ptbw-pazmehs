package tnc.at.brpl.repositories.main.history;

import org.springframework.data.repository.PagingAndSortingRepository;
import tnc.at.brpl.models.main.history.OperationalHistory;

public interface OperationalHistoryRepository extends PagingAndSortingRepository<OperationalHistory, String> {
}
