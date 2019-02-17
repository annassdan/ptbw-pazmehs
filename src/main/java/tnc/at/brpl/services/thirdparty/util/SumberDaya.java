package tnc.at.brpl.services.thirdparty.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SumberDaya {

    private String sumberDaya;

    @Builder.Default
    private String deskripsi = "";

    @Builder.Default
    private List<AlatTangkap> daftarAlatTangkap = new ArrayList<>();

}
