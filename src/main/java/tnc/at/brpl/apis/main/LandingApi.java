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
import tnc.at.brpl.export.main.LandingAsExcel;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.services.administrator.SysUserService;
import tnc.at.brpl.services.main.LandingService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;
import tnc.at.brpl.utils.data.DocumentStatus;
import tnc.at.brpl.utils.other.Shared;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@RestController
@Transactional
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_CORE +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.LANDING + Brpl.URL_DISPATCHER,
        produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class LandingApi
        extends ApiModel<Landing, LandingService> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired SysUserService userService;


    private Landing setDocumentStatusAndOrganization(Landing landing, DocumentStatus status, String org) {

        landing.setStatusDokumen(status);
        for (Operational operational: landing.getDataOperasional()) {
            if (org != null) {
                operational.setOrganisasi(org);
            }
            operational.setStatusDokumen(status);
        }

        for (BiologyOnSize size: landing.getDataUkuran()) {
            if (org != null) {
                size.setOrganisasi(org);
            }
            size.setStatusDokumen(status);
        }

        for (BiologyOnReproduction biology: landing.getDataReproduksi()) {
            if (org != null) {
                biology.setOrganisasi(org);
            }
            biology.setStatusDokumen(status);
        }

        if (org != null) {
            landing.setOrganisasi(org);
        }

        return landing;
    }


    @PostMapping(params = {"userrole", "userid"})
    public ResponseEntity<ResponseResolver<?>> save(@RequestBody Landing landing,
                                                    @RequestParam("userrole") int userrole,
                                                    @RequestParam("userid") String userid) {

        SysUser user = userService.findOne(userid);

        if (userrole == 1) {
            if (landing.getOrganisasi().toLowerCase().equals(DEFAULT_ORGANIZATION.toLowerCase()))
                landing = setDocumentStatusAndOrganization(landing, DocumentStatus.NOT_VERIFIED, null);
            else
                landing = setDocumentStatusAndOrganization(landing, DocumentStatus.WAITING, null);
        } else {
            if (user.getOrganisasi().toLowerCase().equals(DEFAULT_ORGANIZATION.toLowerCase())) {
                if (userrole == 3 || userrole == 4)
                    landing = setDocumentStatusAndOrganization(landing, DocumentStatus.NOT_VERIFIED, null);
                else
                    landing = setDocumentStatusAndOrganization(landing, DocumentStatus.DRAFT, null);
            } else {
                if (userrole == 2)
                    landing = setDocumentStatusAndOrganization(landing, DocumentStatus.WAITING, user.getOrganisasi());
                else
                    landing = setDocumentStatusAndOrganization(landing, DocumentStatus.PENDING, user.getOrganisasi());
            }
        }

        return ResponseResolver.builder()
                .type(ResponseType.WRITE)
                .httpStatus(HttpStatus.OK)
                .body(service.save(landing))
                .build().map();
    }

    @GetMapping(
            params = {PAGING.PAGE, PAGING.SIZE,
                    "userrole",
                    "statusDokumenNot",
                    "withUuidPengupload",
                    "organisasi",
                    "status",
                    "pencatat",
                    "namaLokasiPendaratan",
                    "withWpp",
                    "sumberDaya",
                    "waktuPendaratanMulai",
                    "waktuPendaratanHingga"})
    public ResponseEntity<ResponseResolver<?>> findAllWithManyOptions
            (@RequestParam(PAGING.PAGE) int page,
             @RequestParam(PAGING.SIZE) int size,
             @RequestParam("userrole") int role,
             @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
             @RequestParam("withUuidPengupload") String uuidPengupload,
             @RequestParam("organisasi") String organisasi,
             @RequestParam("status") DocumentStatus status,
             @RequestParam("pencatat") String pencatat,
             @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
             @RequestParam("withWpp") String wpp,
             @RequestParam("sumberDaya") String sumberDaya,
             @RequestParam("waktuPendaratanMulai") String waktuPendaratanMulai,
             @RequestParam("waktuPendaratanHingga") String waktuPendaratanHingga
            ) throws ParseException {

        Date mulai = waktuPendaratanMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanMulai);
        Date hingga = waktuPendaratanHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanHingga);

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);


        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                (service.fetchingDataAsForExportExcel(pageable, organisasi, status, wpp, sumberDaya,
                                        pencatat, namaLokasiPendaratan,
                                        mulai,
                                        hingga))
                        ))
                        .build().map();
            case 2:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(service.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp, uuidPengupload, organisasi, sumberDaya,
                                pencatat, namaLokasiPendaratan, mulai, hingga)
                        ))
                        .build().map();

            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberDaya,
                                pencatat, namaLokasiPendaratan, mulai, hingga)
                        ))
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot, organisasi, status, wpp,
                                        pencatat, namaLokasiPendaratan, mulai, hingga)
                        ))
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status, namaLokasiPendaratan,
                                mulai, hingga)
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


    /**
     * API fetch data as Validator PJ WPP
     *
     * @param page
     * @param size
     * @param withWpp
     * @param statusDokumenNot
     * @param withUuidPengupload
     * @return
     */
    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "withWpp", "statusDokumenNot", "withUuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllByWppAndUuidPenguploadOrStatusDokumenNotPJWPP(
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
                .body(modelingResult(service.findAllByWppOrUuidPenguploadOrStatusDokumenNotPJWPP(
                        pageable,
                        withWpp,
                        withUuidPengupload,
                        DocumentStatus.toDocumentStatus(statusDokumenNot))))
                .build().map();
    }


    /**
     * API fetch data as Validator Peneliti
     *
     * @param page
     * @param size
     * @param withSumberDaya
     * @param statusDokumenNot
     * @param withUuidPengupload
     * @return
     */
    @GetMapping(params = {PAGING.PAGE, PAGING.SIZE, "withSumberDaya", "statusDokumenNot", "withUuidPengupload"})
    public ResponseEntity<ResponseResolver<?>> findAllBySumberdayaAndUuidPenguploadOrStatusDokumenNotPeneliti(
            @RequestParam(PAGING.PAGE) int page,
            @RequestParam(PAGING.SIZE) int size,
            @RequestParam("withSumberDaya") String withSumberDaya,
            @RequestParam("statusDokumenNot") String statusDokumenNot,
            @RequestParam("withUuidPengupload") String withUuidPengupload) {

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllBySumberdayaOrUuidPenguploadOrStatusDokumenNotPeneliti(
                        pageable,
                        withSumberDaya,
                        withUuidPengupload,
                        DocumentStatus.toDocumentStatus(statusDokumenNot))))
                .build().map();
    }


    /**
     * API yang digunakan untuk mengubah status dokumen
     *
     * @param uuidData
     * @param statusDocument
     * @return
     */
    @GetMapping(value = "/approve", params = {"uuidData", "statusDocument"})
    public ResponseEntity<ResponseResolver<?>> updateStatusDokumenLanding(@RequestParam("uuidData") String uuidData,
                                                                          @RequestParam("statusDocument") String statusDocument) {

        Landing landing = service.findOne(uuidData);
        landing.setStatusDokumen(DocumentStatus.toDocumentStatus(statusDocument));

        service.edit(landing);
        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(landing)
                .build().map();
    }


    /**
     * API untuk menghitung jumlah data yang akan diexport
     *
     * @param role
     * @param statusDokumenNot
     * @param uuidPengupload
     * @param organisasi
     * @param pencatat
     * @param namaLokasiPendaratan
     * @param wpp
     * @param sumberDaya
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/excel/count/{userrole}",
            params = {"organisasi", "status", "pencatat",
                    "namaLokasiPendaratan", "withWpp", "sumberDaya",
                    "waktuPendaratanMulai", "waktuPendaratanHingga"})
    public ResponseEntity<ResponseResolver<?>> getCountDataExcel(
            @PathVariable("userrole") int role,
            @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
            @RequestParam("uuidPengupload") String uuidPengupload,
            @RequestParam("organisasi") String organisasi,
            @RequestParam("status") DocumentStatus status,
            @RequestParam("pencatat") String pencatat,
            @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
            @RequestParam("withWpp") String wpp,
            @RequestParam("sumberDaya") String sumberDaya,
            @RequestParam("waktuPendaratanMulai") String waktuPendaratanMulai,
            @RequestParam("waktuPendaratanHingga") String waktuPendaratanHingga
    ) throws ParseException {

        Date mulai = waktuPendaratanMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanMulai);
        Date hingga = waktuPendaratanHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanHingga);


        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsSuperuser(organisasi, status, wpp, sumberDaya, pencatat, namaLokasiPendaratan,
                                mulai, hingga))
                        .build().map();
            case 2:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorNGO( status, wpp, uuidPengupload, organisasi, sumberDaya,
                                pencatat, namaLokasiPendaratan, mulai, hingga))
                        .build().map();
            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPJWPP(wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberDaya,
                                pencatat, namaLokasiPendaratan, mulai, hingga)
                        )
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPeneliti(sumberDaya, uuidPengupload, statusDokumenNot, organisasi, status, wpp,
                                pencatat, namaLokasiPendaratan, mulai, hingga)
                        )
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countDataAsNormalUser(uuidPengupload, organisasi, status, namaLokasiPendaratan, mulai, hingga)
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


    /**
     * @param request
     * @param response
     * @param role
     * @param statusDokumenNot
     * @param uuidPengupload
     * @param organisasi
     * @param pencatat
     * @param namaLokasiPendaratan
     * @param wpp
     * @param sumberDaya
     * @param waktuPendaratanMulai
     * @param waktuPendaratanHingga
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/excel/download/{userrole}",
            params = {"organisasi", "status", "pencatat", "namaLokasiPendaratan", "wpp", "sumberDaya", "waktuPendaratanMulai", "waktuPendaratanHingga"})
    public ModelAndView downloadExcelWithParameters(HttpServletRequest request, HttpServletResponse response,
                                                    @PathVariable("userrole") int role,
                                                    @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
                                                    @RequestParam("status") DocumentStatus status,
                                                    @RequestParam("uuidPengupload") String uuidPengupload,
                                                    @RequestParam("organisasi") String organisasi,
                                                    @RequestParam("pencatat") String pencatat,
                                                    @RequestParam("namaLokasiPendaratan") String namaLokasiPendaratan,
                                                    @RequestParam("wpp") String wpp,
                                                    @RequestParam("sumberDaya") String sumberDaya,
                                                    @RequestParam("waktuPendaratanMulai") String waktuPendaratanMulai,
                                                    @RequestParam("waktuPendaratanHingga") String waktuPendaratanHingga
    ) throws ParseException {

        List<Landing> landings = null;
        Pageable pageable = null;

        Date mulai = waktuPendaratanMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanMulai);
        Date hingga = waktuPendaratanHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(waktuPendaratanHingga);


        switch (role) {
            case 1:
                landings = (service.fetchingDataAsForExportExcel(pageable, organisasi, status, wpp, sumberDaya,
                        pencatat, namaLokasiPendaratan,
                        mulai, hingga)).
                        getContent();
                break;
            case 2:
                landings = (service.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp, uuidPengupload, organisasi, sumberDaya,
                                pencatat, namaLokasiPendaratan, mulai, hingga)).getContent();
                break;
            case 3:
                landings = (service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, status, sumberDaya,
                        pencatat, namaLokasiPendaratan,
                        mulai, hingga))
                        .getContent();
                break;
            case 4:
                landings = (service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot,
                        organisasi, status, wpp,
                        pencatat, namaLokasiPendaratan,
                        mulai, hingga))
                        .getContent();
                break;
            case 5:
                landings = (service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status, namaLokasiPendaratan,
                        mulai, hingga)).getContent();
                break;
            default:
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", landings);
        return new ModelAndView(new LandingAsExcel(), map);
    }


}
