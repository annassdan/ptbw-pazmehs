package tnc.at.brpl.apis.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tnc.at.brpl.export.main.OperationalAsExcel;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.repositories.main.OperationalRepository;
import tnc.at.brpl.services.main.OperationalService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.other.Shared;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@RestController
@Transactional
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_CORE +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.OPERATIONAL + Brpl.URL_DISPATCHER,
        produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class OperationalApi extends ApiModel<Operational, OperationalService> {


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    @GetMapping(
            params = {PAGING.PAGE, PAGING.SIZE,
                    "userrole",
                    "statusDokumenNot",
                    "withUuidPengupload",
                    "organisasi",
                    "gridDaerahPenangkapan",
                    "namaKapal",
                    "namaPemilikKapal",
                    "namaKapten",
                    "pelabuhanAsal",
                    "alatTangkap",
                    "status",
                    "pencatat",
                    "namaLokasiPendaratan",
                    "withWpp",
                    "sumberDaya",
                    "tanggalSamplingMulai",
                    "tanggalSamplingHingga",
                    "tanggalBerangkatMulai",
                    "tanggalBerangkatHingga",
                    "tanggalKembaliMulai",
                    "tanggalKembaliHingga"})
    public ResponseEntity<ResponseResolver<?>> findAllWithManyOptions
            (@RequestParam(PAGING.PAGE) int page,
             @RequestParam(PAGING.SIZE) int size,
             @RequestParam("userrole") int role,
             @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
             @RequestParam("withUuidPengupload") String uuidPengupload,
             @RequestParam("organisasi") String organisasi,
             @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
             @RequestParam("namaKapal") String namaKapal,
             @RequestParam("namaPemilikKapal") String namaPemilikKapal,
             @RequestParam("namaKapten") String namaKapten,
             @RequestParam("pelabuhanAsal") String pelabuhanAsal,
             @RequestParam("alatTangkap") String alatTangkap,
             @RequestParam("status") DocumentStatus status,
             @RequestParam("pencatat") String pencatat,
             @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
             @RequestParam("withWpp") String wpp,
             @RequestParam("sumberDaya") String sumberDaya,
             @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
             @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga,
             @RequestParam("tanggalBerangkatMulai") String tanggalBerangkatMulai,
             @RequestParam("tanggalBerangkatHingga") String tanggalBerangkatHingga,
             @RequestParam("tanggalKembaliMulai") String tanggalKembaliMulai,
             @RequestParam("tanggalKembaliHingga") String tanggalKembaliHingga
            ) throws ParseException {

        Date samplingMulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date samplingHingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);
        Date berangkatMulai = tanggalBerangkatMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatMulai);
        Date berangkatHingga = tanggalBerangkatHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatHingga);
        Date kembaliMulai = tanggalKembaliMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliMulai);
        Date kembaliHingga = tanggalKembaliHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliHingga);

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcel(pageable, organisasi, gridDaerahPenangkapan,
                                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiPendaratan,
                                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        ))
                        .build().map();
            case 2:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap,  sumberDaya,
                                        pencatat, namaLokasiPendaratan,
                                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        ))
                        .build().map();

            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberDaya,
                                        pencatat, namaLokasiPendaratan,
                                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        ))
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp,
                                        pencatat, namaLokasiPendaratan,
                                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        ))
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, gridDaerahPenangkapan,
                                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, namaLokasiPendaratan,
                                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        ))
                        .build().map();
            default:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(null)
                        .build().map();
        }


    }

    /**
     * @param uuidData
     * @param statusDocument
     * @return
     */
    @GetMapping(value = "/approve", params = {"uuidData", "statusDocument"})
    public ResponseEntity<ResponseResolver<?>> updateStatusDokumenOperational(
            @RequestParam("uuidData") String uuidData,
            @RequestParam("statusDocument") String statusDocument) {


        Operational operational = service.findOne(uuidData);
        operational.setStatusDokumen(DocumentStatus.toDocumentStatus(statusDocument));

        service.edit(operational);
        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(operational)
                .build().map();
    }


    /**
     * Digunakan untuk mengambil data berdasarkan siapa yang mengupload data (Pengguna/ CLient/ tamu)
     *
     * @param page
     * @param size
     * @param uuidPengupload
     * @return
     */
    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "uuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllByUuidPengupload(
            @RequestParam(PAGING.PAGE) int page,
            @RequestParam(PAGING.SIZE) int size,
            @RequestParam("uuidPengupload") String uuidPengupload) {

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllByUuidPengupload(pageable, uuidPengupload)))
                .build().map();
    }

    /**
     * Fungsi yang digunakan untuk menampilkand validator dengan membaypass  janis sumber daya
     *
     * @param page
     * @param size
     * @param statusDokumenNot
     * @param withUuidPengupload
     * @return
     */
    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "statusDokumenNot", "withUuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllByUuidPenguploadOrStatusDokumenNot(
            @RequestParam(PAGING.PAGE) int page,
            @RequestParam(PAGING.SIZE) int size,
            @RequestParam("statusDokumenNot") String statusDokumenNot,
            @RequestParam("withUuidPengupload") String withUuidPengupload) {


        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);


        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllByUuidPenguploadOrStatusDokumenNot(
                        pageable,
                        withUuidPengupload,
                        DocumentStatus.toDocumentStatus(statusDokumenNot))))
                .build().map();
    }


    /**
     * Fungsi yang digunakan untuk menampilkand data oleh validator
     *
     * @param page
     * @param size
     * @param withUuidSumberDaya
     * @param statusDokumenNot
     * @param withUuidPengupload
     * @return
     */
    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "withUuidSumberDaya", "statusDokumenNot", "withUuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllByUuidSumberDayaAndUuidPenguploadOrStatusDokumenNot(
            @RequestParam(PAGING.PAGE) int page,
            @RequestParam(PAGING.SIZE) int size,
            @RequestParam("withUuidSumberDaya") String withUuidSumberDaya,
            @RequestParam("statusDokumenNot") String statusDokumenNot,
            @RequestParam("withUuidPengupload") String withUuidPengupload) {

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllByUuidSumberDayaOrUuidPenguploadOrStatusDokumenNot(
                        pageable,
                        withUuidSumberDaya,
                        withUuidPengupload,
                        DocumentStatus.toDocumentStatus(statusDokumenNot))))
                .build().map();
    }


    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "withWpp", "statusDokumenNot", "withUuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllByWppAndUuidPenguploadOrStatusDokumenNot(
            @RequestParam(PAGING.PAGE) int page,
            @RequestParam(PAGING.SIZE) int size,
            @RequestParam("withWpp") String withWpp,
            @RequestParam("statusDokumenNot") String statusDokumenNot,
            @RequestParam("withUuidPengupload") String withUuidPengupload) {

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllByWppOrUuidPenguploadOrStatusDokumenNot(
                        pageable,
                        withWpp,
                        withUuidPengupload,
                        DocumentStatus.toDocumentStatus(statusDokumenNot))))
                .build().map();
    }


//    @GetMapping(value = "/excel/download")
//    public ModelAndView downloadExcelWithNoParameters(HttpServletRequest request, HttpServletResponse response) {
//
////        List<Operational> operationals = service.fetchingDataAsForExportExcel().getContent();
//        List<Operational> operationals = service.fetchingDataAsForExportExcelUsingSpecification().getContent();
//        Map<String, Object> map = new HashMap<>();
//        map.put("data", operationals);
//        return new ModelAndView(new OperationalAsExcel(), map);
//    }

    @PostMapping(value = "/excel/count/{userrole}",
            params = {"organisasi",
                    "gridDaerahPenangkapan",
                    "namaKapal",
                    "namaPemilikKapal",
                    "namaKapten",
                    "pelabuhanAsal",
                    "alatTangkap",
                    "status",
                    "pencatat", "namaLokasiPendaratan", "withWpp", "sumberDaya",
                    "tanggalSamplingMulai", "tanggalSamplingHingga",
                    "tanggalBerangkatMulai",
                    "tanggalBerangkatHingga",
                    "tanggalKembaliMulai",
                    "tanggalKembaliHingga"})
    public ResponseEntity<ResponseResolver<?>> getCountDataExcel(
            @PathVariable("userrole") int role,
            @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
            @RequestParam("uuidPengupload") String uuidPengupload,
            @RequestParam("organisasi") String organisasi,
            @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
            @RequestParam("namaKapal") String namaKapal,
            @RequestParam("namaPemilikKapal") String namaPemilikKapal,
            @RequestParam("namaKapten") String namaKapten,
            @RequestParam("pelabuhanAsal") String pelabuhanAsal,
            @RequestParam("alatTangkap") String alatTangkap,
            @RequestParam("status") DocumentStatus status,
            @RequestParam("pencatat") String pencatat,
            @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
            @RequestParam("withWpp") String wpp,
            @RequestParam("sumberDaya") String sumberDaya,
            @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
            @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga,
            @RequestParam("tanggalBerangkatMulai") String tanggalBerangkatMulai,
            @RequestParam("tanggalBerangkatHingga") String tanggalBerangkatHingga,
            @RequestParam("tanggalKembaliMulai") String tanggalKembaliMulai,
            @RequestParam("tanggalKembaliHingga") String tanggalKembaliHingga
    ) throws ParseException {

        Date samplingMulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date samplingHingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);
        Date berangkatMulai = tanggalBerangkatMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatMulai);
        Date berangkatHingga = tanggalBerangkatHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatHingga);
        Date kembaliMulai = tanggalKembaliMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliMulai);
        Date kembaliHingga = tanggalKembaliHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliHingga);

        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsSuperuser(organisasi, gridDaerahPenangkapan,
                                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiPendaratan,
                                samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga))
                        .build().map();
            case 2:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorNGO(status, wpp, uuidPengupload, organisasi, gridDaerahPenangkapan,
                                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap,  sumberDaya,
                                pencatat, namaLokasiPendaratan,
                                samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga))
                        .build().map();
            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPJWPP(wpp, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberDaya,
                                pencatat, namaLokasiPendaratan,
                                samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        )
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPeneliti(sumberDaya, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp,
                                pencatat, namaLokasiPendaratan,
                                samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        )
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countDataAsNormalUser(uuidPengupload, organisasi, gridDaerahPenangkapan,
                                namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, namaLokasiPendaratan,
                                samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)
                        )
                        .build().map();
            default:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(0)
                        .build().map();


        }
    }


    @GetMapping(value = "/excel/download/{userrole}",
            params = {"organisasi",
                    "gridDaerahPenangkapan",
                    "namaKapal",
                    "namaPemilikKapal",
                    "namaKapten",
                    "pelabuhanAsal",
                    "alatTangkap",
                    "status",
                    "pencatat", "namaLokasiPendaratan", "withWpp", "sumberDaya",
                    "tanggalSamplingMulai", "tanggalSamplingHingga",
                    "tanggalBerangkatMulai",
                    "tanggalBerangkatHingga",
                    "tanggalKembaliMulai",
                    "tanggalKembaliHingga"})
    public ModelAndView downloadExcelWithParameters(HttpServletRequest request, HttpServletResponse response,
                                                    @PathVariable("userrole") int role,
                                                    @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
                                                    @RequestParam("uuidPengupload") String uuidPengupload,
                                                    @RequestParam("organisasi") String organisasi,
                                                    @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                    @RequestParam("namaKapal") String namaKapal,
                                                    @RequestParam("namaPemilikKapal") String namaPemilikKapal,
                                                    @RequestParam("namaKapten") String namaKapten,
                                                    @RequestParam("pelabuhanAsal") String pelabuhanAsal,
                                                    @RequestParam("alatTangkap") String alatTangkap,
                                                    @RequestParam("status") DocumentStatus status,
                                                    @RequestParam("pencatat") String pencatat,
                                                    @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
                                                    @RequestParam("withWpp") String wpp,
                                                    @RequestParam("sumberDaya") String sumberDaya,
                                                    @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
                                                    @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga,
                                                    @RequestParam("tanggalBerangkatMulai") String tanggalBerangkatMulai,
                                                    @RequestParam("tanggalBerangkatHingga") String tanggalBerangkatHingga,
                                                    @RequestParam("tanggalKembaliMulai") String tanggalKembaliMulai,
                                                    @RequestParam("tanggalKembaliHingga") String tanggalKembaliHingga
    ) throws ParseException {

        List<Operational> operationals = null;
        Pageable pageable = null;

        Date samplingMulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date samplingHingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);
        Date berangkatMulai = tanggalBerangkatMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatMulai);
        Date berangkatHingga = tanggalBerangkatHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalBerangkatHingga);
        Date kembaliMulai = tanggalKembaliMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliMulai);
        Date kembaliHingga = tanggalKembaliHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalKembaliHingga);


        switch (role) {
            case 1:
                operationals = (service.fetchingDataAsForExportExcel(pageable, organisasi, gridDaerahPenangkapan,
                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiPendaratan,
                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga))
                        .getContent();
                break;
            case 2:
                operationals = service.fetchingDataAsForExportExcel(pageable, organisasi, gridDaerahPenangkapan,
                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiPendaratan,
                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga).getContent();
                break;
            case 3:
                operationals = (service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, sumberDaya,
                        pencatat, namaLokasiPendaratan,
                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga))
                        .getContent();
                break;
            case 4:
                operationals = (service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot, organisasi, gridDaerahPenangkapan,
                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, wpp,
                        pencatat, namaLokasiPendaratan,
                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga))
                        .getContent();
                break;
            case 5:
                operationals = (service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, gridDaerahPenangkapan,
                        namaKapal, namaPemilikKapal, namaKapten, pelabuhanAsal, alatTangkap, status, namaLokasiPendaratan,
                        samplingMulai, samplingHingga, berangkatMulai, berangkatHingga, kembaliMulai, kembaliHingga)).getContent();
                break;
            default:
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", operationals);
        return new ModelAndView(new OperationalAsExcel(), map);
    }

}
