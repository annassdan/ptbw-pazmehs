package tnc.at.brpl.services.thirdparty.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class Spesifikasi {

    private String spesifikasi;

    @Builder.Default
    private String satuan = "";


}
