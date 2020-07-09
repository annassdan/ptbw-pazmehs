package tnc.at.brpl.services.main;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.services.main.listeners.LandingListener;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.service.ServiceModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Service
public class LandingService extends ServiceModel<Landing, LandingRepository> implements LandingListener {


    @Override
    public boolean isDuplicated(Landing landing) {
        Long d = repository.countDuplicateData(
                landing.getTanggalPendaratan(),
                landing.getNamaLokasiPendaratan(),
                landing.getUuidSumberDaya(),
                landing.getUuidEnumerator(),
                landing.getOrganisasi(),
                landing.getWpp());

        return d != null && (d.longValue() > 0);
    }

    @Override
    public Page<Landing> findAllByUuidPengupload(Pageable var1, String uuidPengupload) {
        return repository.findAllByUuidPenguploadOrderByDibuatPadaTanggalAsc(var1, uuidPengupload);
    }


    @Override
    public Page<Landing> findAllByUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidPengupload, statusDokumenNot);
    }

    @Override
    public Page<Landing> findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNotOrderByDibuatPadaTanggalAsc(var1, uuidSumberDaya, uuidPengupload, statusDokumenNot);
    }


    @Override
    public Page<Landing> findAllByWppOrUuidPenguploadOrStatusDokumenNotPJWPP(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.fetchingDataAsValidatorPJWPP(var1, wpp, uuidPengupload, statusDokumenNot);
    }

    @Override
    public Page<Landing> findAllBySumberdayaOrUuidPenguploadOrStatusDokumenNotPeneliti(Pageable var1, String sumberdaya, String uuidPengupload, DocumentStatus statusDokumenNot) {
        return repository.fetchingDataAsValidatorPeneliti(var1, sumberdaya, uuidPengupload, statusDokumenNot);
    }


    @Override
    public Page<Landing> fetchingDataAsForExportExcel(Pageable var1,
                                                      String organisasi,
                                                      DocumentStatus status,
                                                      String wpp,
                                                      String sumberdaya,
                                                      String pencatat,
                                                      String lokasiPendaratan,
                                                      Date waktuPendaratanMulai,
                                                      Date waktuPendaratanHingga) {


        long size = 0;
        if (var1 == null) {
            size = repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
            size = (size == 0) ? 1 : size;
        }


        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcel(pageable,
                organisasi, status, wpp, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }

    @Override
    public Page<Landing> fetchingDataAsForExportExcelAsValidatorNGO(Pageable var1, DocumentStatus status,
                                                                    String wpp, String uuidPengupload,
                                                                      String organisasi,
                                                                    String sumberdaya, String pencatat, String lokasiPendaratan,
                                                                      Date waktuPendaratanMulai,
                                                                      Date waktuPendaratanHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp,
                uuidPengupload,  organisasi, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public Page<Landing> fetchingDataAsForExportExcelAsValidatorPJWPP(Pageable var1, String wpp, String uuidPengupload, DocumentStatus statusDokumenNot,
                                                                      String organisasi,
                                                                      DocumentStatus status, String sumberdaya, String pencatat, String lokasiPendaratan,
                                                                      Date waktuPendaratanMulai,
                                                                      Date waktuPendaratanHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable,
                wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }

    @Override
    public Page<Landing> fetchingDataAsForExportExcelAsValidatorPeneliti(Pageable var1, String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot,
                                                                         String organisasi,
                                                                         DocumentStatus status, String wpp, String pencatat, String lokasiPendaratan,
                                                                         Date waktuPendaratanMulai,
                                                                         Date waktuPendaratanHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload, organisasi, status, wpp, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable,
                uuidSumberDaya, uuidPengupload, organisasi, status, wpp, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public Page<Landing> fetchingDataAsNormalUser(Pageable var1, String uuidPengupload, String organisasi,
                                                  DocumentStatus status, String lokasiPendaratan, Date waktuPendaratanMulai,
                                                  Date waktuPendaratanHingga) {
        long size = 0;
        if (var1 == null) {
            size = repository.countDataAsNormalUser(uuidPengupload, organisasi, status, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
            size = (size == 0) ? 1 : size;
        }
        Pageable pageable = (var1 == null) ? new PageRequest(0, (int) size) : var1;
        return repository.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public long countAsSuperuser(String organisasi,
                                 DocumentStatus status,
                                 String wpp,
                                 String sumberdaya,
                                 String pencatat,
                                 String lokasiPendaratan,
                                 Date waktuPendaratanMulai,
                                 Date waktuPendaratanHingga) {
        return repository.countAsSuperuser(organisasi, status, wpp, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public long countAsValidatorNGO(DocumentStatus status,
                                    String wpp, String uuidPengupload, String organisasi,
                                    String sumberdaya, String pencatat,
                                      String lokasiPendaratan,
                                      Date waktuPendaratanMulai,
                                      Date waktuPendaratanHingga) {
        return repository.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public long countAsValidatorPJWPP(String wpp, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                      DocumentStatus status, String sumberdaya, String pencatat,
                                      String lokasiPendaratan,
                                      Date waktuPendaratanMulai,
                                      Date waktuPendaratanHingga) {
        return repository.countAsValidatorPJWPP(wpp, uuidPengupload, organisasi, status, sumberdaya, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }

    @Override
    public long countAsValidatorPeneliti(String uuidSumberDaya, String uuidPengupload, DocumentStatus statusDokumenNot, String organisasi,
                                         DocumentStatus status, String wpp, String pencatat,
                                         String lokasiPendaratan,
                                         Date waktuPendaratanMulai,
                                         Date waktuPendaratanHingga) {
        return repository.countAsValidatorPeneliti(uuidSumberDaya, uuidPengupload,  organisasi, status, wpp, pencatat, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


    @Override
    public long countDataAsNormalUser(String uuidPengupload, String organisasi,
                                      DocumentStatus status, String lokasiPendaratan, Date waktuPendaratanMulai,
                                      Date waktuPendaratanHingga) {
        return repository.countDataAsNormalUser(uuidPengupload, organisasi, status, lokasiPendaratan, waktuPendaratanMulai, waktuPendaratanHingga);
    }


}

