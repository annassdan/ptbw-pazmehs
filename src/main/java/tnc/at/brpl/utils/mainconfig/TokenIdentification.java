package tnc.at.brpl.utils.mainconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnc.at.brpl.configurations.CustomDateSerializer;
import tnc.at.brpl.models.main.BiologyOnReproduction;
import tnc.at.brpl.models.main.BiologyOnSize;
import tnc.at.brpl.models.main.Landing;
import tnc.at.brpl.models.main.Operational;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public class TokenIdentification {

    Map<String, List<?>> dataTempLists;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public TokenIdentification(Map<String, List<?>> dataTempLists) {
        this.dataTempLists = dataTempLists;
    }

    public boolean isTokenExists(String tokenUpload) {

        for (Map.Entry<String, List<?>> entry : dataTempLists.entrySet()) {
            if (entry.getKey().equals(tokenUpload)) {
                return true;
            }
        }

        return false;
    }


    public boolean isDataExistOnMemory(String token, Landing source) {

        List<Landing> list = (List<Landing>) dataTempLists.get(token);
        if (list == null) return false;

        for (Landing landing : list) {
            if (landing.getTanggalPendaratan().compareTo(source.getTanggalPendaratan()) == 0 &&
                    landing.getOrganisasi().trim().equals(source.getOrganisasi().trim()) &&
                    landing.getNamaLokasiPendaratan().trim().equals(source.getNamaLokasiPendaratan().trim()) &&
                    landing.getWpp().trim().equals(source.getWpp().trim()) &&
                    landing.getUuidSumberDaya().trim().equals(source.getUuidSumberDaya().trim()) &&
                    landing.getUuidEnumerator().trim().equals(source.getUuidEnumerator().trim()) &&
                    landing.getDataRincianPendaratan().size() == source.getDataRincianPendaratan().size()) {

                if (landing.getDataRincianPendaratan().size() > 0 && source.getDataRincianPendaratan().size() > 0) {
                    if (landing.getDataRincianPendaratan().get(0).getNamaKapal().trim().equals(source.getDataRincianPendaratan().get(0).getNamaKapal().trim())) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isDataExistOnMemory(String token, Operational source, boolean... isChild) {
        List<Operational> list;
        if (isChild != null && isChild.length == 0) {
            list = (List<Operational>) dataTempLists.get(token);
        } else {
            if (isChild[0]) {
                List<Landing> temp = (List<Landing>) dataTempLists.get(token);
                list = temp.get(0).getDataOperasional();
            } else
                list = (List<Operational>) dataTempLists.get(token);
        }

        if (list == null) return false;

        for (Operational operational : list) {

            if (operational.getTanggalSampling().compareTo(source.getTanggalSampling()) == 0 &&
                    operational.getOrganisasi().trim().equals(source.getOrganisasi().trim()) &&
                    operational.getNamaLokasiPendaratan().trim().equals(source.getNamaLokasiPendaratan().trim()) &&
                    operational.getUuidSumberDaya().trim().equals(source.getUuidSumberDaya().trim()) &&
                    operational.getUuidEnumerator().trim().equals(source.getUuidEnumerator().trim()) &&
                    operational.getNamaKapal().trim().equals(source.getNamaKapal().trim()) &&
                    operational.getTanggalBerangkat().compareTo(source.getTanggalBerangkat()) == 0 &&
                    operational.getTanggalKembali().compareTo(source.getTanggalKembali()) == 0 &&
                    operational.getPelabuhanAsal().trim().equals(source.getPelabuhanAsal().trim()) &&
                    operational.getNamaPemilikKapal().trim().equals(source.getNamaPemilikKapal().trim()) &&
                    operational.getNamaKapten().trim().equals(source.getNamaKapten().trim()) &&
                    operational.getUuidAlatTangkap().trim().equals(source.getUuidAlatTangkap().trim()) &&
                    operational.getDataOperasionalDetailTangkapan().size() == source.getDataOperasionalDetailTangkapan().size()) {
                return true;
            }
        }


        return false;
    }


    public boolean isDataExistOnMemory(String token, BiologyOnSize source, boolean... isChild) {
        List<BiologyOnSize> list;
        if (isChild != null && isChild.length == 0) {
            list = (List<BiologyOnSize>) dataTempLists.get(token);
        } else {
            if (isChild[0]) {
                List<Landing> temp = (List<Landing>) dataTempLists.get(token);
                list = temp.get(0).getDataUkuran();
            } else
                list = (List<BiologyOnSize>) dataTempLists.get(token);
        }

        if (list == null) return false;

        for (BiologyOnSize biologyOnSize : list) {
            if (biologyOnSize.getTanggalSampling().compareTo(source.getTanggalSampling()) == 0 &&
                    biologyOnSize.getOrganisasi().trim().equals(source.getOrganisasi().trim()) &&
                    biologyOnSize.getNamaLokasiSampling().trim().equals(source.getNamaLokasiSampling().trim()) &&
                    biologyOnSize.getUuidSumberDaya().trim().equals(source.getUuidSumberDaya().trim()) &&
                    biologyOnSize.getUuidEnumerator().trim().equals(source.getUuidEnumerator().trim()) &&
                    biologyOnSize.getDaerahPenangkapan().trim().equals(source.getDaerahPenangkapan().trim()) &&
                    biologyOnSize.getUuidAlatTangkap().trim().equals(source.getUuidAlatTangkap().trim()) &&
                    biologyOnSize.getTotalTangkapanVolume() == source.getTotalTangkapanVolume() &&
                    biologyOnSize.getTotalTangkapanIndividu() == source.getTotalTangkapanIndividu() &&
                    biologyOnSize.getTotalSampelVolume() == source.getTotalSampelVolume() &&
                    biologyOnSize.getTotalSampelIndividu() == source.getTotalSampelIndividu() &&
                    biologyOnSize.getDataSampleDetail().size() == source.getDataSampleDetail().size() &&
                    biologyOnSize.getDataUkuranDetail().size() == source.getDataUkuranDetail().size()) {

                if (biologyOnSize.getDataUkuranDetail().size() > 0 && source.getDataUkuranDetail().size() > 0) {
                    if (biologyOnSize.getDataUkuranDetail().get(0).getPanjang() == source.getDataUkuranDetail().get(0).getPanjang()) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isDataExistOnMemory(String token, BiologyOnReproduction source, boolean... isChild) {

        List<BiologyOnReproduction> list;
        if (isChild != null && isChild.length == 0) {
            list = (List<BiologyOnReproduction>) dataTempLists.get(token);
        } else {
            if (isChild[0]) {
                List<Landing> temp = (List<Landing>) dataTempLists.get(token);
                list = temp.get(0).getDataReproduksi();
            } else
                list = (List<BiologyOnReproduction>) dataTempLists.get(token);
        }


        if (list == null) return false;

        for (BiologyOnReproduction biologyOnReproduction : list) {
            if (biologyOnReproduction.getTanggalSampling().compareTo(source.getTanggalSampling()) == 0 &&
                    biologyOnReproduction.getOrganisasi().trim().equals(source.getOrganisasi().trim()) &&
                    biologyOnReproduction.getNamaLokasiSampling().trim().equals(source.getNamaLokasiSampling().trim()) &&
                    biologyOnReproduction.getUuidSumberDaya().trim().equals(source.getUuidSumberDaya().trim()) &&
                    biologyOnReproduction.getUuidEnumerator().trim().equals(source.getUuidEnumerator().trim()) &&
                    biologyOnReproduction.getDaerahPenangkapan().trim().equals(source.getDaerahPenangkapan().trim()) &&
                    biologyOnReproduction.getNamaKapal().trim().equals(source.getNamaKapal().trim()) &&
                    biologyOnReproduction.getUuidAlatTangkap().trim().equals(source.getUuidAlatTangkap().trim()) &&
                    biologyOnReproduction.getUuidSpesies().trim().equals(source.getUuidSpesies().trim()) &&
                    biologyOnReproduction.getDataDetailReproduksi().size() == source.getDataDetailReproduksi().size()) {
                if (biologyOnReproduction.getDataDetailReproduksi().size() > 0 && source.getDataDetailReproduksi().size() > 0) {
                    if (biologyOnReproduction.getDataDetailReproduksi().get(0).getPanjang() == source.getDataDetailReproduksi().get(0).getPanjang() &&
                            biologyOnReproduction.getDataDetailReproduksi().get(0).getBerat() == source.getDataDetailReproduksi().get(0).getBerat()) {
                        return true;
                    }

                } else {
                    return true;
                }
            }
        }

        return false;
    }

}
