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
import tnc.at.brpl.export.main.BiologyOnReproductionAsExcel;
import tnc.at.brpl.export.main.BiologyOnReproductionAsExcel;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Operational;
import tnc.at.brpl.repositories.main.BiologyOnReproductionRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.services.main.BiologyOnReproductionService;
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
        Brpl.URL_DISPATCHER + Brpl.CONTENT.BIOLOGY_ON_REPRODUCTION + Brpl.URL_DISPATCHER,
        produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class BiologyOnReproductionApi extends ApiModel<BiologyOnReproduction, BiologyOnReproductionService> {


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    @GetMapping(
            params = {PAGING.PAGE, PAGING.SIZE,
                    "userrole",
                    "statusDokumenNot",
                    "withUuidPengupload",
                    "organisasi", "status", 
                    "pencatat",
                    "namaLokasiSampling",
                    "namaKapal",
                    "alatTangkap",
                    "spesies",
                    "gridDaerahPenangkapan",
                    "withWpp",
                    "sumberDaya",
                    "tanggalSamplingMulai",
                    "tanggalSamplingHingga"})
    public ResponseEntity<ResponseResolver<?>> findAllWithManyOptions
            (@RequestParam(PAGING.PAGE) int page,
             @RequestParam(PAGING.SIZE) int size,
             @RequestParam("userrole") int role,
             @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
             @RequestParam("withUuidPengupload") String uuidPengupload,
             @RequestParam("organisasi") String organisasi,
             @RequestParam("status") DocumentStatus status,
             @RequestParam("pencatat") String pencatat,
             @RequestParam("namaLokasiSampling") String namaLokasiSampling,
             @RequestParam("namaKapal") String namaKapal,
             @RequestParam("alatTangkap") String alatTangkap,
             @RequestParam("spesies") String spesies,
             @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
             @RequestParam("withWpp") String wpp,
             @RequestParam("sumberDaya") String sumberDaya,
             @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
             @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga
            ) throws ParseException {

        Date mulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date hingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);

        page = (page > 0) ? page - 1 : page;
        Pageable pageable = new PageRequest(page, size);

        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(service.fetchingDataAsForExportExcel(pageable, organisasi, status,  wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiSampling,
                                namaKapal,
                                alatTangkap,
                                spesies,
                                gridDaerahPenangkapan, mulai,
                                hingga)
                        ))
                        .build().map();
            case 2:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp, uuidPengupload,  organisasi, sumberDaya,
                                        pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)
                        ))
                        .build().map();

            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, status,  sumberDaya,
                                        pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)
                        ))
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot, organisasi, status,  wpp,
                                        pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)                                
                        ))
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(modelingResult(
                                service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status,  namaLokasiSampling,
                                                namaKapal,
                                                alatTangkap,
                                                spesies,
                                                gridDaerahPenangkapan, mulai,
                                                hingga)
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
    public ResponseEntity<ResponseResolver<?>> updateStatusDokumenReproduction(@RequestParam("uuidData") String uuidData,
                                                                               @RequestParam("statusDocument") String statusDocument) {

        BiologyOnReproduction biologyOnReproduction = service.findOne(uuidData);
        biologyOnReproduction.setStatusDokumen(DocumentStatus.toDocumentStatus(statusDocument));

        service.edit(biologyOnReproduction);
        return ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(biologyOnReproduction)
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


    @PostMapping(value = "/excel/count/{userrole}",
            params = {"organisasi", "status",  "pencatat", "namaLokasiSampling",
                    "namaKapal",
                    "alatTangkap",
                    "spesies",
                    "gridDaerahPenangkapan",
                    "withWpp", "sumberDaya", "tanggalSamplingMulai", "tanggalSamplingHingga"})
    public ResponseEntity<ResponseResolver<?>> getCountDataExcel(
            @PathVariable("userrole") int role,
            @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
            @RequestParam("uuidPengupload") String uuidPengupload,
            @RequestParam("organisasi") String organisasi,
             @RequestParam("status") DocumentStatus status,
            @RequestParam("pencatat") String pencatat,
            @RequestParam("namaLokasiSampling") String namaLokasiSampling,
            @RequestParam("namaKapal") String namaKapal,
            @RequestParam("alatTangkap") String alatTangkap,
            @RequestParam("spesies") String spesies,
            @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
            @RequestParam("withWpp") String wpp,
            @RequestParam("sumberDaya") String sumberDaya,
            @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
            @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga
    ) throws ParseException {

        Date mulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date hingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);

        switch (role) {
            case 1:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsSuperuser(organisasi, status,  wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan,
                                        mulai,
                                        hingga) 
                        )
                        .build().map();
            case 2: return ResponseResolver.builder()
                    .type(ResponseType.READ)
                    .httpStatus(HttpStatus.OK)
                    .body(service.countAsValidatorNGO(status, wpp, uuidPengupload,  organisasi, sumberDaya,
                            pencatat, namaLokasiSampling,
                            namaKapal,
                            alatTangkap,
                            spesies,
                            gridDaerahPenangkapan, mulai,
                            hingga)
                    )
                    .build().map();
            case 3:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPJWPP(wpp, uuidPengupload, statusDokumenNot, organisasi, status,  sumberDaya,
                                        pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)
                        )
                        .build().map();
            case 4:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countAsValidatorPeneliti(sumberDaya, uuidPengupload, statusDokumenNot, organisasi, status,  wpp,
                                        pencatat, namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)
                        )
                        .build().map();
            case 5:
                return ResponseResolver.builder()
                        .type(ResponseType.READ)
                        .httpStatus(HttpStatus.OK)
                        .body(service.countDataAsNormalUser(uuidPengupload, organisasi, status,  namaLokasiSampling,
                                        namaKapal,
                                        alatTangkap,
                                        spesies,
                                        gridDaerahPenangkapan, mulai,
                                        hingga)
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
            params = {"organisasi", "status",  "pencatat", "namaLokasiSampling",
                    "namaKapal",
                    "alatTangkap",
                    "spesies",
                    "gridDaerahPenangkapan",
                    "withWpp", "sumberDaya", "tanggalSamplingMulai", "tanggalSamplingHingga"})
    public ModelAndView downloadExcelWithParameters(HttpServletRequest request, HttpServletResponse response,
                                                    @PathVariable("userrole") int role,
                                                    @RequestParam("statusDokumenNot") DocumentStatus statusDokumenNot,
                                                    @RequestParam("uuidPengupload") String uuidPengupload,
                                                    @RequestParam("organisasi") String organisasi,
             @RequestParam("status") DocumentStatus status,
                                                    @RequestParam("pencatat") String pencatat,
                                                    @RequestParam("namaLokasiSampling") String namaLokasiSampling,
                                                    @RequestParam("namaKapal") String namaKapal,
                                                    @RequestParam("alatTangkap") String alatTangkap,
                                                    @RequestParam("spesies") String spesies,
                                                    @RequestParam("gridDaerahPenangkapan") String gridDaerahPenangkapan,
                                                    @RequestParam("withWpp") String wpp,
                                                    @RequestParam("sumberDaya") String sumberDaya,
                                                    @RequestParam("tanggalSamplingMulai") String tanggalSamplingMulai,
                                                    @RequestParam("tanggalSamplingHingga") String tanggalSamplingHingga
    ) throws ParseException {

        List<BiologyOnReproduction> reproductions = null;
        Pageable pageable = null;

        Date mulai = tanggalSamplingMulai.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingMulai);
        Date hingga = tanggalSamplingHingga.isEmpty() ? null : Shared.stringDDMMYYYYToDateYYYYMMDD(tanggalSamplingHingga);


        switch (role) {
            case 1:
                reproductions = (service.fetchingDataAsForExportExcel(pageable, organisasi, status,  wpp, sumberDaya, uuidPengupload, pencatat, namaLokasiSampling,
                                namaKapal,
                                alatTangkap,
                                spesies,
                                gridDaerahPenangkapan, mulai,
                                hingga)).
                                getContent();
                break;
            case 2:
                reproductions = service.fetchingDataAsForExportExcelAsValidatorNGO(pageable, status, wpp, uuidPengupload,  organisasi, sumberDaya,
                        pencatat, namaLokasiSampling,
                        namaKapal,
                        alatTangkap,
                        spesies,
                        gridDaerahPenangkapan, mulai,
                        hingga).getContent();
                break;
            case 3:
                reproductions = (service.fetchingDataAsForExportExcelAsValidatorPJWPP(pageable, wpp, uuidPengupload, statusDokumenNot, organisasi, status,  sumberDaya,
                                pencatat, namaLokasiSampling,
                                namaKapal,
                                alatTangkap,
                                spesies,
                                gridDaerahPenangkapan, mulai,
                                hingga))
                                .getContent();
                break;
            case 4:
                reproductions = (service.fetchingDataAsForExportExcelAsValidatorPeneliti(pageable, sumberDaya, uuidPengupload, statusDokumenNot, organisasi, status,  wpp,
                                pencatat, namaLokasiSampling,
                                namaKapal,
                                alatTangkap,
                                spesies,
                                gridDaerahPenangkapan, mulai,
                                hingga))
                                .getContent();
                break;
            case 5:
                reproductions = (service.fetchingDataAsNormalUser(pageable, uuidPengupload, organisasi, status,  namaLokasiSampling,
                                namaKapal,
                                alatTangkap,
                                spesies,
                                gridDaerahPenangkapan, mulai,
                                hingga)).getContent();
                break;
            default:
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", reproductions);
        return new ModelAndView(new BiologyOnReproductionAsExcel(), map);
    }


}
