package tnc.at.brpl.utils.thirdparty.diff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiffProp<T> {

    /* key */
    private String onProp;

    private T oldValue;

    private T newValue;


}
