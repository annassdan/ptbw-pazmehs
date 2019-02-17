package tnc.at.brpl.services.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class Utility3rdPartyService {


    private List<SumberDaya> sumberDayaList
            = Arrays.asList(
                    SumberDaya.builder().sumberDaya("Pelagis Besar")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Huhate/Pole and Line").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pemancing").satuan("orang").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Jaring Insang Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pancing Tonda").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pancing Ulur").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Payang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Cincin").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Cincin Kecil").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Rawai Tuna").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Mata Pancing").satuan("buah").build()
                                    )).build()

                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Pelagis Kecil")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Bagan Apung").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Bagan Perahu/Rambo").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Bagan Tancap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Jaring Insang Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Cincin").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Demersal")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Bubu Ikan").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jenis Badan Bubu").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Umpan").satuan("kg").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bubu").satuan("cm").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bubu").satuan("cm").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bubu").satuan("cm").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Cantrang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Muroami").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Dalam Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Panah").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Mata Panah").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pancing Ulur").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Rawai Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Cucut dan Pari")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Rawai Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Rawai Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Cumi")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Bouke Ami").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Keliling Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pancing Cumi").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Kekerangan")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Garuk").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bagan Mulut").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bagan Mulut").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dorong/Sudu").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Pukat").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Pukat").satuan("m").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Krustase")
                            .daftarAlatTangkap(Arrays.asList(
                                    ALatTangkap.builder().alatTangkap("Bubu Lipat").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jenis Umpan").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Umpan").satuan("kg").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bubu").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bubu").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bubu").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Bubu Tancap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tongkat ").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Diameter").satuan("cm").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Jaring Trammel").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Luar").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Dalam").satuan("inch").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Perangkap").daftarSpesifikasi(Arrays.asList()).build(),
                                    ALatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    ALatTangkap.builder().alatTangkap("Rakang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tongkat").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Diameter").satuan("cm").build()
                                    )).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Lainnya")
                            .daftarAlatTangkap(Arrays.asList())
                            .build()
    );


    public List<TkgObject> tkg() {
        List<TkgObject> tkgObjects = Arrays.asList(
                TkgObject.builder().tkg("1").build(),
                TkgObject.builder().tkg("2").build(),
                TkgObject.builder().tkg("3").build(),
                TkgObject.builder().tkg("4").build(),
                TkgObject.builder().tkg("5").build(),
                TkgObject.builder().tkg("Immature").build(),
                TkgObject.builder().tkg("Unidentified").build(),
                TkgObject.builder().tkg("Berried").build(),
                TkgObject.builder().tkg("Mature").build(),
                TkgObject.builder().tkg("NC").deskripsi("Non-Calcification").build(),
                TkgObject.builder().tkg("NFC").deskripsi("Non-Fully Calcification").build(),
                TkgObject.builder().tkg("FC ").deskripsi("Fully Calcification").build()
        );
        return tkgObjects;
    }



}


@Data
@AllArgsConstructor
@Builder
class SumberDaya {

    private String sumberDaya;

    @Builder.Default
    private String deskripsi = "";

    @Builder.Default
    private List<ALatTangkap> daftarAlatTangkap = new ArrayList<>();

}



@Data
@AllArgsConstructor
@Builder
class ALatTangkap {

    private String alatTangkap;

    @Builder.Default
    private String deskripsi = "";

    @Builder.Default
    private List<Spesifikasi> daftarSpesifikasi = new ArrayList<>();

}


@Data
@AllArgsConstructor
@Builder
class Spesifikasi {

    private String spesifikasi;

    @Builder.Default
    private String satuan = "";

}

@Data
@AllArgsConstructor
@Builder
class TkgObject {
    private String tkg;

    @Builder.Default
    private String deskripsi = "";
}
