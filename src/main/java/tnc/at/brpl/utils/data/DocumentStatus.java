
package tnc.at.brpl.utils.data;


/**
 * Merepresentasikan status-status dari Dokumen Data Sampling yang ada dalam Sistem e-BRPL
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public enum DocumentStatus {





    /**
     * Data Sampling yang telah diinputkan oleh enumerator, Namun belum dapat
     * di cek kevalidan datanya oleh Peneliti, dikarenakan enumerator belum
     * mengirimkan data yang telah dia input untuk bisa digunakan oleh Peneliti
     * pada proses pengecekan.  (0)
     */

    DRAFT,


    /**
     * Menunggu proses pengecekan yang dilakukan oleh Peneliti terhadap
     * kevalidan data sampling yang didapat dari Enumerator  (1)
     */
    NOT_VERIFIED,



    /**
     * Data sampling yang diperoleh telah melalui pengecekan oleh peneliti
     * dan telah di setujui oleh pihak yang berhak melakukan proses approval
     * untuk kemudian dapat digunakan untuk melakukan pencetakan Laporan Penelitian (2)
     */
    VERIFIED,


    /**
     * Data sampling yang ada telah dianggap tidak dapat digunakan untuk proses selanjutnya. (3)
     */
    CANCELED,


    /**
     * status data jika di upload oleh user biasa dari ngo (4)
     */
    PENDING,

    /**
     * terverifikasi oleh ngo yang bersangkutan (5)
     */
    WAITING;


    public static DocumentStatus toDocumentStatus(String status) {
        if (status.toUpperCase().equals(DocumentStatus.VERIFIED.toString().toUpperCase())) {
            return DocumentStatus.VERIFIED;
        } else if (status.toUpperCase().equals(DocumentStatus.NOT_VERIFIED.toString().toUpperCase())) {
            return  DocumentStatus.NOT_VERIFIED;
        } else if (status.toUpperCase().equals(DocumentStatus.DRAFT.toString().toUpperCase())) {
            return DocumentStatus.DRAFT;
        } else if (status.toUpperCase().equals(DocumentStatus.PENDING.toString().toUpperCase())) {
            return DocumentStatus.PENDING;
        } else if (status.toUpperCase().equals(DocumentStatus.WAITING.toString().toUpperCase())) {
            return DocumentStatus.WAITING;
        } else {
            return DocumentStatus.CANCELED;
        }
    }
}
