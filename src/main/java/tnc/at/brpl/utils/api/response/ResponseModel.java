package tnc.at.brpl.utils.api.response;


import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.web.util.UriComponentsBuilder;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiLogger;
import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class ResponseModel<E> extends ApiLogger<E> implements Brpl {

    private List<E> content;

    private boolean last;

    private boolean first;

    private int totalPages;

    private long totalElements;

    private Sort sort;

    private int numberOfElements;

    private int size;

    private int number;

    private ResponseGeneratedUri generatedUri;


    /**
     * Mendapatkan Model Response dari pengambilan datadari tabel di Database
     *
     * @param page  Data Tabel yang didapat dari database berdasarkan halaman
     *              dan jumlah data pada sebuah halaman
     * @param uriComponentsBuilder Komponen Uri Request dari Browser
     * @return ResponseModel
     */
    public ResponseModel<Object> generateModel(Page<?> page, UriComponentsBuilder uriComponentsBuilder){
        return generating(page, uriComponentsBuilder);
    }


    /**
     * Mendapatkan Model Response dari pengambilan datadari tabel di Database
     *
     * @param page  Data Tabel yang didapat dari database berdasarkan halaman
     *              dan jumlah data pada sebuah halaman
     * @return ResponseModel
     */
    public ResponseModel<Object> generateModel(Page<?> page){
        return generating(page, null);
    }


    /**
     * Melakukan generate data models response untuk proses pengambilan data
     * dari database
     *
     * @param page  Data Tabel yang didapat dari database berdasarkan halaman
     *              dan jumlah data pada sebuah halaman
     * @param uriComponentsBuilder Komponen Uri Request dari Browser
     * @return ResponseModel
     */
    @SuppressWarnings("unchecked")
    private ResponseModel<Object> generating(Page<?> page,
                                                                       UriComponentsBuilder uriComponentsBuilder){

        ResponseGeneratedUri generatedUri = (uriComponentsBuilder != null) ? getUri(page, uriComponentsBuilder) : null;

        makeLog("Generating Getting Datas...");
        return ResponseModel.builder()
                .content((List<Object>) page.getContent())
                .last(page.isLast())
                .first(page.isFirst())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .sort(page.getSort())
                .numberOfElements(page.getNumberOfElements())
                .size(page.getSize())
                .number(page.getNumber())
                .generatedUri(generatedUri)
                .build();
    }




    /**
     * Melakukan proses generate URL Link untuk Paging pada data Tabel yang terpilih
     *
     * @param page  Data Tabel yang didapat dari database berdasarkan halaman
     *              dan jumlah data pada sebuah halaman
     * @param uriComponents Komponen Uri Request dari Browser
     * @return ResponseModel
     */
    private ResponseGeneratedUri getUri(Page page,
                                        UriComponentsBuilder uriComponents){

        if (uriComponents == null){
            return ResponseGeneratedUri.builder().build();
        }

//        makeLog("Generating URI Paging....");
        return ResponseGeneratedUri.builder()
                .first(uriComponents.toUriString() + QUESTION_MARK
                        + PAGING.PAGE + EQUALS + (page.isFirst() ? toString(page.getNumber() + 1) : toString(1))
                        + AND + PAGING.SIZE + EQUALS + page.getSize())
                .previous(uriComponents.toUriString() + QUESTION_MARK
                        + PAGING.PAGE + EQUALS + (page.isFirst() ? toString(page.getNumber() + 1) : toString((page.getNumber() + 1) - 1))
                        + AND + PAGING.SIZE + EQUALS + page.getSize())
                .next(uriComponents.toUriString() + QUESTION_MARK
                        + PAGING.PAGE + EQUALS + (page.isLast() ? toString(page.getNumber() + 1) : toString((page.getNumber() + 1) + 1))
                        + AND + PAGING.SIZE + EQUALS + page.getSize())
                .last(uriComponents.toUriString() + QUESTION_MARK
                        + PAGING.PAGE + EQUALS + (page.isLast() ? toString(page.getNumber() + 1) : toString(page.getTotalPages()))
                        + AND + PAGING.SIZE + EQUALS + page.getSize())
               .build();
    }


    /**
     * Mengubah Object data menjadi Sebuah String
     *
     * @param o Data
     * @return String
     */
    private  String toString(Object o){
        return String.valueOf(o);
    }


}
