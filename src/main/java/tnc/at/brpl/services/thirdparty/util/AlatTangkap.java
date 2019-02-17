package tnc.at.brpl.services.thirdparty.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AlatTangkap {

    private String alatTangkap;

    @Builder.Default
    private String deskripsi = "";

    @Builder.Default
    private List<Spesifikasi> daftarSpesifikasi = new ArrayList<>();

}
