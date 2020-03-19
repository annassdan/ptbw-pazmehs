package tnc.at.brpl.utils.thirdparty.diff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareDiff {

    @Builder.Default
    private boolean identical = true;

    private Diff diff;

}