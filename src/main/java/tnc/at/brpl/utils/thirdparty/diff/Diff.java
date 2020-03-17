package tnc.at.brpl.utils.thirdparty.diff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tnc.at.brpl.utils.data.HistoryDiffType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diff {

    private String onId;

    private HistoryDiffType diffType;

    private List<DiffProp> props;

}
