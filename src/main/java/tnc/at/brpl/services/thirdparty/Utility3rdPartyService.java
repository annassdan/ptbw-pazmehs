package tnc.at.brpl.services.thirdparty;

import org.springframework.stereotype.Service;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.services.thirdparty.util.AlatTangkap;
import tnc.at.brpl.services.thirdparty.util.Spesifikasi;
import tnc.at.brpl.services.thirdparty.util.SumberDaya;
import tnc.at.brpl.services.thirdparty.util.Tkg;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class Utility3rdPartyService {


    final private static List<SumberDaya> sumberDayaList
            = Arrays.asList(
                    SumberDaya.builder().sumberDaya("Pelagis Besar")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Huhate/Pole and Line").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pemancing").satuan("orang").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Jaring Insang Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pancing Tonda").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pancing Ulur").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Payang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Cincin").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Cincin Kecil").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Rawai Tuna").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Mata Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Pelagis Kecil")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Bagan Apung").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Bagan Perahu/Rambo").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Bagan Tancap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bingkai").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bingkai").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Jaring Insang Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Cincin").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Daya Lampu").satuan("watt").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Lampu").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Demersal")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Bubu Ikan").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jenis Badan Bubu").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Umpan").satuan("kg").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bubu").satuan("cm").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bubu").satuan("cm").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bubu").satuan("cm").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Cantrang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Muroami").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Dalam Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Panah").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Mata Panah").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pancing Ulur").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Rawai Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Cucut dan Pari")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Rawai Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Rawai Hanyut").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Cumi")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Bouke Ami").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Keliling Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pancing Cumi").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Nomor Mata Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Nomor Tali Pancing").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Pancing").satuan("buah").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Kekerangan")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Garuk").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bagan Mulut").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bagan Mulut").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dorong/Sudu").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Pukat").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Pukat").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Krustase")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Bubu Lipat").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Jenis Umpan").build(),
                                            Spesifikasi.builder().spesifikasi("Jumlah Umpan").satuan("kg").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Bubu").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Lebar Bubu").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Bubu").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Bubu Tancap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tongkat ").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Diameter").satuan("cm").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Jaring Insang Dasar/Tetap").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Jaring").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Tinggi Jaring").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Jaring Trammel").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Jumlah Pis").satuan("buah").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Luar").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Dalam").satuan("inch").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Perangkap").daftarSpesifikasi(Arrays.asList()).build(),
                                    AlatTangkap.builder().alatTangkap("Pukat Dasar").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring Kantong").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Badan").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Sayap").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Kantong").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tali Ris Atas").satuan("m").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Rakang").daftarSpesifikasi(Arrays.asList(
                                            Spesifikasi.builder().spesifikasi("Ukuran Mata Jaring").satuan("inch").build(),
                                            Spesifikasi.builder().spesifikasi("Panjang Tongkat").satuan("m").build(),
                                            Spesifikasi.builder().spesifikasi("Diameter").satuan("cm").build()
                                    )).build(),
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build(),
                    SumberDaya.builder().sumberDaya("Lainnya")
                            .daftarAlatTangkap(Arrays.asList(
                                    AlatTangkap.builder().alatTangkap("Lainnya").daftarSpesifikasi(Arrays.asList()).build()
                            ))
                            .build()
    );


    final public static List<String> lengthTypes = Arrays.asList("CL", "CW", "DW", "FL", "ML", "SL", "TL", "OT");

    final public static List<String> wpps = Arrays.asList("571", "572", "573", "711", "712", "713", "714", "715", "716", "717", "718");


    public static List<Tkg> tkg() {
        List<Tkg> Tkgs = Arrays.asList(
                Tkg.builder().tkg("1").build(),
                Tkg.builder().tkg("2").build(),
                Tkg.builder().tkg("3").build(),
                Tkg.builder().tkg("4").build(),
                Tkg.builder().tkg("5").build(),
                Tkg.builder().tkg("Immature").build(),
                Tkg.builder().tkg("Unidentified").build(),
                Tkg.builder().tkg("Berried").build(),
                Tkg.builder().tkg("Mature").build(),
                Tkg.builder().tkg("NC").deskripsi("Non-Calcification").build(),
                Tkg.builder().tkg("NFC").deskripsi("Non-Fully Calcification").build(),
                Tkg.builder().tkg("FC ").deskripsi("Fully Calcification").build()
        );

        return Tkgs;
    }


    public static List<SumberDaya> sumberDaya() {
        return sumberDayaList;
    }


    public List<AlatTangkap> alatTangkap(String sumberdaya) {

        Optional<List<AlatTangkap>> opt = sumberDayaList.stream()
                .filter(d -> d.getSumberDaya().trim().toUpperCase().equals(sumberdaya.trim().toUpperCase()))
                .findFirst()
                .map(sumberDaya -> sumberDaya.getDaftarAlatTangkap());

        if (!opt.isPresent())
            throw new ResourceInternalServerErrorException("Maaf, daftar alat tangkap untuk sumberdaya '" + sumberdaya + "' tidak ditemukan!!");

        return opt.get();
    }



    public List<Spesifikasi> listSpesifikasi(String sumberdaya, String alatTangkap) {

        Optional<List<AlatTangkap>> opt = sumberDayaList.stream()
                .filter(d -> d.getSumberDaya().trim().toUpperCase().equals(sumberdaya.trim().toUpperCase()))
                .findFirst()
                .map(sumberDaya -> sumberDaya.getDaftarAlatTangkap());

        if (!opt.isPresent())
            throw new ResourceInternalServerErrorException("Maaf, daftar alat tangkap untuk sumberdaya '" + sumberdaya + "' tidak ditemukan!!");

        Optional<List<Spesifikasi>>  opt1 = opt.get().stream()
                .filter(d -> d.getAlatTangkap().trim().toUpperCase().equals(alatTangkap.trim().toUpperCase()))
                .findFirst()
                .map(e -> e.getDaftarSpesifikasi());

        if (!opt1.isPresent())
            throw new ResourceInternalServerErrorException("Maaf, spesifikasi untuk alat tangkap '" + alatTangkap + "' tidak ditemukan!!");


        return opt1.get();
    }



}


