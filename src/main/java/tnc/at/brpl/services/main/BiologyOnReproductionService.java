package tnc.at.brpl.services.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.services.main.listeners.BiologyOnReproductionListener;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceModel;

import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class BiologyOnReproductionService extends ServiceModel<BiologyOnReproduction, BiologyOnReproductionRepository>
        implements BiologyOnReproductionListener {


    @Override
    public boolean isDuplicated(BiologyOnReproduction biologyOnReproduction) {
        long d = repository.countDuplicateData(
                biologyOnReproduction.getUuidSumberDaya(),
                biologyOnReproduction.getNamaLokasiSampling(),
                biologyOnReproduction.getNamaKapal(),
                biologyOnReproduction.getUuidSpesies(),
                biologyOnReproduction.getDaerahPenangkapan(),
                biologyOnReproduction.isPenampung(),
                biologyOnReproduction.isPenangkap(),
                biologyOnReproduction.getUuidAlatTangkap(),
                biologyOnReproduction.getTanggalSampling(),
                biologyOnReproduction.getUuidEnumerator(),
                biologyOnReproduction.getOrganisasi(),
                biologyOnReproduction.getWpp()
        );

        return d > 0;
    }

    @Override
    public Page<BiologyOnReproduction> findAllByUuidPengupload(Pageable var1, String uuidPengupload) {
        return repository.findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(var1, uuidPengupload);
    }

    @Override
    public Page<BiologyOnReproduction> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidPengupload, statusDokumenNot);
    }

    @Override
    public Page<BiologyOnReproduction> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidSumberDaya, uuidPengupload, statusDokumenNot);
    }


    @Override
    public Page<BiologyOnReproduction> findAllByWppOrUuidPenguploadOrStatusDokumenNot(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByWppOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, wpp, uuidPengupload, statusDokumenNot);
    }


    @Override
    public Page<BiologyOnReproduction> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
                                                                    DocumentStatus status, String wpp, String sumberdaya, String uuidPengupload, String pencatat, String namaLokasiSampling,
                                                                    String namaKapal,
                                                                    String alatTangkap,
                                                                    String spesies,
                                                                    String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                                                    Date tanggalSamplingHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, uuidPengupload, pencatat, namaLokasiSampling,
                    namaKapal,
                    alatTangkap,
                    spesies,
                    gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcel(pageable,
                organisasi, status, wpp, sumberdaya, uuidPengupload, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public Page<BiologyOnReproduction> fetchingDataAsForExportExcel(Pageable var1, String organisasi,
//                                                                    DocumentStatus status, String wpp, String sumberdaya, String pencatat, String namaLokasiSampling,
//                                                                    String namaKapal,
//                                                                    String alatTangkap,
//                                                                    String spesies,
//                                                                    String gridDaerahPenangkapan) {
//        long size = 0;
//        if (var1 == null) {
//            size = repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, pencatat, namaLokasiSampling,
//                    namaKapal,
//                    alatTangkap,
//                    spesies,
//                    gridDaerahPenangkapan);
//            size = (size == 0) ? 1 : size;
//        }
//        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
//        return repository.fetchingDataAsForExportExcel(pageable,
//                organisasi, status, wpp, sumberdaya, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1,
                                                                                  DocumentStatus status,
                                                                                  String wpp,
                                                                                  String uuidPengupload, String organisasi,
                                                                                  String sumberdaya, String pencatat, String namaLokasiSampling,
                                                                                    String namaKapal,
                                                                                    String alatTangkap,
                                                                                    String spesies,
                                                                                    String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                                                                    Date tanggalSamplingHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, sumberdaya, pencatat, namaLokasiSampling,
                    namaKapal,
                    alatTangkap,
                    spesies,
                    gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp,
                uuidPengupload, organisasi, sumberdaya, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }


    @Override
    public Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                                                                    DocumentStatus status, String sumberdaya, String pencatat, String namaLokasiSampling,
                                                                                    String namaKapal,
                                                                                    String alatTangkap,
                                                                                    String spesies,
                                                                                    String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                                                                    Date tanggalSamplingHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
                    namaKapal,
                    alatTangkap,
                    spesies,
                    gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable,
                wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
//                                                                                    DocumentStatus status, String sumberdaya, String pencatat, String namaLokasiSampling,
//                                                                                    String namaKapal,
//                                                                                    String alatTangkap,
//                                                                                    String spesies,
//                                                                                    String gridDaerahPenangkapan) {
//        long size = 0;
//        if (var1 == null) {
//            size = repository.countAsValidatorPJWPP(wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
//                    namaKapal,
//                    alatTangkap,
//                    spesies,
//                    gridDaerahPenangkapan);
//            size = (size == 0) ? 1 : size;
//        }
//        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
//        return repository.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable,
//                wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                                                                       DocumentStatus status, String wpp, String pencatat, String namaLokasiSampling,
                                                                                       String namaKapal,
                                                                                       String alatTangkap,
                                                                                       String spesies,
                                                                                       String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                                                                       Date tanggalSamplingHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, organisasi, status, wpp, pencatat, namaLokasiSampling,
                    namaKapal,
                    alatTangkap,
                    spesies,
                    gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable,
                uuidSumberDaya, uuidPengupload, organisasi, status, wpp, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public Page<BiologyOnReproduction> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
//                                                                                       DocumentStatus status, String wpp, String pencatat, String namaLokasiSampling,
//                                                                                       String namaKapal,
//                                                                                       String alatTangkap,
//                                                                                       String spesies,
//                                                                                       String gridDaerahPenangkapan) {
//        long size = 0;
//        if (var1 == null) {
//            size = repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, statusDokumenNot, organisasi, status, wpp, pencatat, namaLokasiSampling,
//                    namaKapal,
//                    alatTangkap,
//                    spesies,
//                    gridDaerahPenangkapan);
//            size = (size == 0) ? 1 : size;
//        }
//        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
//        return repository.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable,
//                uuidSumberDaya, uuidPengupload, statusDokumenNot, organisasi, status, wpp, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public Page<BiologyOnReproduction> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload, String organisasi,
                                                                DocumentStatus status,
                                                                String namaLokasiSampling,
                                                                String namaKapal,
                                                                String alatTangkap,
                                                                String spesies,
                                                                String gridDaerahPenangkapan,
                                                                Date tanggalSamplingMulai,
                                                                Date tanggalSamplingHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countDataAsNormalUser(uuidPengupload, organisasi, status,
                    namaLokasiSampling,
                    namaKapal,
                    alatTangkap,
                    spesies,
                    gridDaerahPenangkapan,
                    tanggalSamplingMulai, tanggalSamplingHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status,
                namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan,
                tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public Page<BiologyOnReproduction> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload, String organisasi,
//                                                                DocumentStatus status,
//                                                                String namaLokasiSampling,
//                                                                String namaKapal,
//                                                                String alatTangkap,
//                                                                String spesies,
//                                                                String gridDaerahPenangkapan) {
//        long size = 0;
//        if (var1 == null) {
//            size = repository.countDataAsNormalUser(uuidPengupload, organisasi,
//                    namaLokasiSampling,
//                    namaKapal,
//                    alatTangkap,
//                    spesies,
//                    gridDaerahPenangkapan);
//            size = (size == 0) ? 1 : size;
//        }
//        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
//        return repository.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi,
//                namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public long countAsSuperuser(String organisasi,
                                 DocumentStatus status, String wpp, String sumberdaya, String uuidPengupload, String pencatat, String namaLokasiSampling,
                                 String namaKapal,
                                 String alatTangkap,
                                 String spesies,
                                 String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                 Date tanggalSamplingHingga) {
        return repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, uuidPengupload, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public long countAsSuperuser(String organisasi,
//                                 DocumentStatus status, String wpp, String sumberdaya, String pencatat, String namaLokasiSampling,
//                                 String namaKapal,
//                                 String alatTangkap,
//                                 String spesies,
//                                 String gridDaerahPenangkapan) {
//        return repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public long countAsValidatorNGO( DocumentStatus status,
                                     String wpp, String uuidPengupload,  String organisasi,
                                     String sumberdaya, String pencatat, String namaLokasiSampling,
                                      String namaKapal,
                                      String alatTangkap,
                                      String spesies,
                                      String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga) {
        return repository.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, sumberdaya, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

    @Override
    public long countAsValidatorPJWPP(String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                      DocumentStatus status, String sumberdaya, String pencatat, String namaLokasiSampling,
                                      String namaKapal,
                                      String alatTangkap,
                                      String spesies,
                                      String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga) {
        return repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public long countAsValidatorPJWPP(String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
//                                      DocumentStatus status, String sumberdaya, String pencatat, String namaLokasiSampling,
//                                      String namaKapal,
//                                      String alatTangkap,
//                                      String spesies,
//                                      String gridDaerahPenangkapan) {
//        return repository.countAsValidatorPJWPP(wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberdaya, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public long countAsValidatorPeneliti(String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                         DocumentStatus status, String wpp, String pencatat, String namaLokasiSampling,
                                         String namaKapal,
                                         String alatTangkap,
                                         String spesies,
                                         String gridDaerahPenangkapan, Date tanggalSamplingMulai,
                                         Date tanggalSamplingHingga) {
        return repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, organisasi, status, wpp, pencatat, namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan, tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public long countAsValidatorPeneliti(String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
//                                         DocumentStatus status, String wpp, String pencatat, String namaLokasiSampling,
//                                         String namaKapal,
//                                         String alatTangkap,
//                                         String spesies,
//                                         String gridDaerahPenangkapan) {
//        return repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, statusDokumenNot, organisasi, status, wpp, pencatat, namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

    @Override
    public long countDataAsNormalUser(String uuidPengupload, String organisasi,
                                      DocumentStatus status,
                                      String namaLokasiSampling,
                                      String namaKapal,
                                      String alatTangkap,
                                      String spesies,
                                      String gridDaerahPenangkapan,
                                      Date tanggalSamplingMulai,
                                      Date tanggalSamplingHingga) {
        return repository.countDataAsNormalUser(uuidPengupload, organisasi, status,
                namaLokasiSampling,
                namaKapal,
                alatTangkap,
                spesies,
                gridDaerahPenangkapan,
                tanggalSamplingMulai, tanggalSamplingHingga);
    }

//    @Override
//    public long countDataAsNormalUser(String uuidPengupload, String organisasi,
//                                      DocumentStatus status,
//                                      String namaLokasiSampling,
//                                      String namaKapal,
//                                      String alatTangkap,
//                                      String spesies,
//                                      String gridDaerahPenangkapan) {
//        return repository.countDataAsNormalUser(uuidPengupload, organisasi,
//                namaLokasiSampling,
//                namaKapal,
//                alatTangkap,
//                spesies,
//                gridDaerahPenangkapan);
//    }

//    @Override
//    public Page<BiologyOnReproduction> fetchingDataAsForExportExcel() {
//        long size = repository.count();
////        long size = 300;
//        size = (size == 0) ? 1 : size;
//        Pageable pageable = new PageRequest(0, (int) size);
//        return repository.fetchingDataAsForExportExcel(pageable);
//    }
}
