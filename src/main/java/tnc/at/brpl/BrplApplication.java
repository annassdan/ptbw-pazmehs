package tnc.at.brpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import tnc.at.brpl.configurations.StorageService;
import tnc.at.brpl.models.administrator.PagesMap;
import tnc.at.brpl.models.administrator.SysUser;
import tnc.at.brpl.models.administrator.SysUserGroup;
import tnc.at.brpl.models.administrator.oauth.CustomUserDetails;
import tnc.at.brpl.repositories.administrator.SysUserRepository;
import tnc.at.brpl.repositories.main.BiologyOnSizeRepository;
import tnc.at.brpl.repositories.main.LandingRepository;
import tnc.at.brpl.services.administrator.PagesMapService;
import tnc.at.brpl.services.administrator.SysUserGroupService;
import tnc.at.brpl.services.administrator.SysUserService;
import tnc.at.brpl.services.master.ResourceService;
import tnc.at.brpl.utils.mainconfig.configs.BiologyOnReproductionConfig;
import tnc.at.brpl.utils.mainconfig.configs.BiologyOnSizeConfig;
import tnc.at.brpl.utils.mainconfig.configs.LandingConfig;
import tnc.at.brpl.utils.mainconfig.configs.OperationalConfig;
import tnc.at.brpl.utils.mainconfig.models.FieldModel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SpringBootApplication
@SuppressWarnings("unused")
public class BrplApplication implements CommandLineRunner {

    @Resource
    StorageService storageService;



    @Autowired
    LandingConfig landingConfig;

    @Autowired
    OperationalConfig operationalConfig;

    @Autowired
    BiologyOnSizeConfig biologyOnSizeConfig;

    @Autowired
    BiologyOnReproductionConfig biologyOnReproductionConfig;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    PagesMapService pagesMapService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    SysUserGroupService sysUserGroupService;

    @Autowired
    BiologyOnSizeRepository onSizeRepository;


    @Autowired
    LandingRepository landingRepository;




    /**
     * inisialisasi time format selalu mengikut pada waktu daerah yang dimaksud
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


    public static void main(String[] args) {
        SpringApplication.run(BrplApplication.class, args);
    }

    /**
     * @param builder
     * @param repository
     * @throws Exception
     */
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, SysUserRepository repository) throws Exception {
        builder.userDetailsService(s -> {
            return new CustomUserDetails(repository.findByPengguna(s));
        });
    }

    /**
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        //        initSuperuser();
//        initPagesMap();
//        initResources();
//        initOrganizations();

        storageService.init(); // init penyimpanan data unggah dan unduh

        initLandingConfig(); // init pengaturan form pendaratan
        initOperationalConfig();
        initBiologyOnSizeConfig();
        initBiologyOnReproductionConfig();
    }


    /**
     *
     */
    private void initSuperuser() throws IOException {
        File filePagesMap = new File("res/initdata/sysuser.json");
        TypeReference<List<SysUser>> typeReference = new TypeReference<List<SysUser>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        List<SysUser> pagesMaps = objectMapper.readValue(filePagesMap, typeReference);

        pagesMaps.forEach(s -> {
//            SysUser find = sysUserService.findOne(s.getUuid());
//            if (find == null)
                sysUserService.save(s);
        });


    }

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    private void initOrganizations() throws IOException {
        File filePagesMap = new File("res/initdata/organisasi.json");
        TypeReference<List<SysUserGroup>> typeReference = new TypeReference<List<SysUserGroup>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        List<SysUserGroup> deserialized = objectMapper.readValue(filePagesMap, typeReference);

//        sysUserGroupService.saves(deserialized);

        deserialized.forEach(s -> {
//            SysUserGroup find = sysUserGroupService.findOne(s.getUuid());
//            if (find == null)
                sysUserGroupService.save(s);
        });
    }

    /**
     * @throws IOException
     */
    private void initResources() throws IOException {
        File filePagesMap = new File("res/initdata/sumberdaya.json");
        TypeReference<List<tnc.at.brpl.models.master.Resource>> typeReference = new TypeReference<List<tnc.at.brpl.models.master.Resource>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        List<tnc.at.brpl.models.master.Resource> deserialized = objectMapper.readValue(filePagesMap, typeReference);

        deserialized.forEach(s -> {
//            tnc.at.brpl.models.master.Resource find = resourceService.findOne(s.getUuid());
//            if (find == null)
                resourceService.save(s);
        });
    }


    /**
     * @throws IOException
     */
    private void initPagesMap() throws IOException {
        File filePagesMap = new File("res/initdata/petahalaman.json");
        TypeReference<List<PagesMap>> typeReference = new TypeReference<List<PagesMap>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        List<PagesMap> pagesMaps = objectMapper.readValue(filePagesMap, typeReference);

        pagesMaps.forEach(s -> {
//            PagesMap find = pagesMapService.findOne(s.getUuid());
//            if (find == null)
                pagesMapService.save(s);
        });
    }


    /**
     * @param configFile
     * @return
     * @throws IOException
     */
    private List<FieldModel> initializingFormConfig(File configFile) throws IOException {
        TypeReference<List<FieldModel>> typeReference = new TypeReference<List<FieldModel>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(configFile, typeReference);
    }


    /**
     * @throws IOException
     */
    private void initLandingConfig() throws IOException {
        File landingJsonConfig = new File("res/settings/landing.json");
        landingConfig.configurationLoaded = initializingFormConfig(landingJsonConfig);
    }


    /**
     * @throws IOException
     */
    private void initOperationalConfig() throws IOException {
        File landingJsonConfig = new File("res/settings/operational.json");
        operationalConfig.configurationLoaded = initializingFormConfig(landingJsonConfig);
    }


    /**
     * @throws IOException
     */
    private void initBiologyOnSizeConfig() throws IOException {
        File landingJsonConfig = new File("res/settings/biologyonsize.json");
        biologyOnSizeConfig.configurationLoaded = initializingFormConfig(landingJsonConfig);
    }


    /**
     * @throws IOException
     */
    private void initBiologyOnReproductionConfig() throws IOException {
        File landingJsonConfig = new File("res/settings/biologyonreproduction.json");
        biologyOnReproductionConfig.configurationLoaded = initializingFormConfig(landingJsonConfig);
    }

}
