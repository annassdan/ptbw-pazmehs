

package tnc.at.brpl.utils;

/**
 * Merepresentasikan dasar-dasar nilai/standard yang konstan untuk kebutuhan berjalannya Sistem e-BRPL
 *
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public interface Brpl {

    String STATIC_TOKEN = "105ce49f-6b15-440c-a78d-91e04f58aDAN";

    String DB_NAME = "db_brpl_final";

    String FORM_SURRENT_VERSION = "21-03-2018";

    String DEFAULT_ORGANIZATION = "BRPL";

    /**
     *
     */

    String MAIN_URL = "ebrpl";
    String URL_DISPATCHER = "/";
    String BEGAN_BRACER = "{";
    String END_BRACER = "}";
    String ERROR_URL = "/error";

    String PACKAGE_MASTER = "/master";
    String PACKAGE_ADMINISTRATOR = "/administrator";
    String PACKAGE_CORE = "/sampling";

    String API = "/api";
    String PRIMARY_KEY = "uuid";
    String UNDERSCORE = "_";
    String QUESTION_MARK = "?";
    String EQUALS = "=";
    String AND = "&";
    String DATE_PATTERN = "dd/MM/yyyy";
    String TIME_PATTERN = "HH:mm";
    String DATE_TIME_PATTERN_MINIMAL = "dd/MM/yyyy HH:mm:ss";
    String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss.SSS";




    /** Kode yang digunakan untuk memeberi tanda unik pada sebuah tabel */
    String UNIQUE = "brpl" + UNDERSCORE;

    String OTHER = "eksternal" + UNDERSCORE;

    /** Kode yang digunakan untuk memeberi tanda master data*/
    String MASTER = "master" + UNDERSCORE;

    /** Kode yang digunakan untuk memeberi tanda data administrator/superuser */
    String ADMIN = "administrator" + UNDERSCORE;

    /** Kode yang digunakan untuk memberi tanda unik pada sebuah kolom di tabel di database*/
//    String XMARK = UNDERSCORE + "data";
    String XMARK = "";


    /** Lama expire token upload data**/
    int BRPL_TOKEN_UPLOAD_EXPIRE = 60;



    interface MODE {
        String JSON = "application/json";
    }

    interface PAGING {
        String PAGE = "page";
        String SIZE = "size";
    }


    interface REQUEST {

        /**
         *
         */
        String FIND = "/";

        /**
         *
         */
        String SAVE = "/save";

        /**
         *
         */
        String EDIT = "/edit";

        /**
         *
         */
        String DELETE = "/delete";

    }

    interface KEY {

        /**
         *
         */
        String GENERATOR = "uuid";

        /**
         *
         */
        String NAME = "uuid";

        /**
         *
         */
        String STRATEGY = "uuid2";
    }


    interface JSON {

        String SUCCESS = "success";
        String FAILED = "failed";

        String NO_REASON = "no reason";
        String UNKNOWN_REASON = "unknown reason";

    }


    interface CONTENT {

        /** SETTINGs **/
        String SYSUSER = "sysuser";
        String ACCESSES = "accesses";
        String ROLES = "roles";
        String SYSUSER_GROUP = "sysusergroup";
        String PAGES_MAP = "petahalaman";
        String PAGES_MAP_ROLES = "petahalamanakses";
        String PAGES_MAP_ACCESS = "petahalamanaksesrole";

        /** MASTERs **/
        String SPECIES = "spesies";
        String RESOURCE = "sumberdaya";
        String RESEARCHER = "pegawai";
        String FISHING_TOOL = "alattangkap";
        String FISHING_AREA = "daerahpenangkapan";
        String ENUMERATOR = "enumerator";


        /** MAINs **/
        String LANDING = "pendaratan";
        String LANDING_DETAILS = "rincianpendaratan";
        String LANDING_CATCH_DETAILS = "pendaratandetailtangkapan";
        String OPERATIONAL = "operasional";
        String OPERATIONAL_ON_FISHING_TOOL_SPEC = "operasionalspesifikasialattangkap";
        String CATCH_DETAIL = "operasionaldetailtangkapan";
        String BIOLOGY_ON_REPRODUCTION = "biologireproduksi";
        String BIOLOGY_ON_REPRODUCTION_DETAIL = "biologireproduksidetail";
        String BIOLOGY_ON_REPRODUCTION_STOMACH_CONTENTS_DETAIL = "biologireproduksiisiperutdetail";
        String BIOLOGY_ON_SIZE = "biologiukuran";
        String BIOLOGY_ON_SIZE_DETAIL = "biologiukurandetail";
        String BIOLOGY_ON_SIZE_SAMPLE_DETAIL = "biologiukuranrinciansample";

        interface OTHER {
            String BOAT = "boat";
            String BOAT_TRACKER = "boat_tracker";
            String DEEPSLOPE = "deepslope";
            String FINDMESPOT = "findmespot";
            String SIZING = "sizing";
            String TRACKER = "tracker";
        }
        
    }
    

    /**  */
    String REDIRECT = "redirect:";


    /**
     * Request timeout standard
     */
    int STANDARD_TIMEOUT = 10;
    int EXTRA_TIMEOUT = 15;
    int REQUEST_TIMEOUT = 30;



    interface RESPONSE {
        String SUCCESS = "Successfully";
        String ERROR = "";
        String BAD_REQUEST = "Bad Request";
        String NOT_FOUND = "Not Found";
    }


    interface  CORS {
        String ALL_ORIGINS = "*";
        String ORIGIN_1 = "http://localhost:4200";
        String ORIGIN_2 = "http://localhost:8787";
        String ORIGIN_3 = "http://localhost:3000";
        String ORIGIN_4 = "http://localhost:3001";
        String ORIGIN_5 = "http://192.81.128.126:4200";
        String ORIGIN_6 = "http://192.81.128.126:8787";
        String ORIGIN_7 = "http://192.81.128.126:3000";
        String ORIGIN_8 = "http://192.81.128.126:443";
        String ORIGIN_9 = "http://192.81.128.126:80";
        String ORIGIN_10 = "http://192.81.128.126";
        int MAX_AGE_1 = 10000;
        int MAX_AGE_2 = 30000;
        int MAX_AGE_3 = 86000;
        int ONE_DAY = 86400;
        int ONE_MINUTE = 60;
        String ALLOW_CREDENTIALS = "false";
    }

    interface HEADERS {
        String ACCESS_CONTROL_ALLOW_ORIGIN =  "Access-Control-Allow-Origin";
        String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
        String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
        String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    }


}
