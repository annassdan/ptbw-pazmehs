package tnc.at.brpl.services.thirdparty.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Tkg {

    private String tkg;

    @Builder.Default
    private String deskripsi = "";


}
