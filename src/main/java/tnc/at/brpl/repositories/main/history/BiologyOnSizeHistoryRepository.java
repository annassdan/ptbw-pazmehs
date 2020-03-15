package tnc.at.brpl.repositories.main.history;

import org.springframework.data.repository.PagingAndSortingRepository;
import tnc.at.brpl.models.main.history.BiologyOnReproductionHistory;
import tnc.at.brpl.models.main.history.BiologyOnSizeHistory;
import tnc.at.brpl.utils.repository.RepositoryListener;

public interface BiologyOnSizeHistoryRepository extends PagingAndSortingRepository<BiologyOnSizeHistory, String> {
}
