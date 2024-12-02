package com.g41.trashsmart_server.Configuration;

import com.g41.trashsmart_server.Enums.OrgType;
import com.g41.trashsmart_server.Enums.Scale;
import com.g41.trashsmart_server.Enums.WasteType;
import com.g41.trashsmart_server.Models.Organization;
import com.g41.trashsmart_server.Models.WasteCollectionRequest;
import com.g41.trashsmart_server.Repositories.OrganizationRepository;
import com.g41.trashsmart_server.Repositories.WasteCollectionRequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class WasteCollectionRequestConfig {
    @Bean
    CommandLineRunner wasteCollectionRequestCommandLineRunner(
            WasteCollectionRequestRepository wasteCollectionRequestRepository,
            OrganizationRepository organizationRepository
    ) {
        Organization foa = new Organization(
                "FOA",
                "FOA Admin",
                "foa@cmb.ac.lk",
                "password@12345678",
                "0712990638",
                "35, Reid Avenue, Colombo 07",
                "www.google.com",
                Scale.SMALL,
                OrgType.EDUCATION,
                6.901655107547004,
                79.86182154331027
        );
        Organization fol = new Organization(
                "FOL",
                "FOL Admin",
                "fol@cmb.ac.lk",
                "password@12345678",
                "0712990639",
                "50, Reid Avenue, Colombo 07",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.EDUCATION,
                6.901786908082538,
                79.8590790478905
        );
        Organization thurstan = new Organization(
                "Thurstan College",
                "Thurstan College Admin",
                "thurstan@edu.gov.lk",
                "password@12345678",
                "0712990640",
                "35, Thurstan Road, Colombo 07",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.EDUCATION,
                6.902647660013757,
                79.85882185713773
        );
        Organization laksala = new Organization(
                "Laksala",
                "Laksala Admin",
                "laksala@google.com",
                "password@12345678",
                "0712990641",
                "215, Bauddhaloka Mawatha, Colombo 07",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TOURISM_AND_HOSPITALITY,
                6.897983357274245,
                79.86031726150777
        );
        Organization arcade = new Organization(
                "Arcade Independence Square",
                "Arcade Admin",
                "arcade@gov.lk",
                "password@12345678",
                "0712990642",
                "Independence Square, Colombo 07",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.BUSINESS_AND_COMMERCE,
                6.902189398848052,
                79.86972437769595
        );
        Organization icbt = new Organization(
                "ICBT",
                "ICBT Admin",
                "info@icbt.lk",
                "password@12345678",
                "0712990643",
                "36, De Kretser Pl, Colombo 04",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.EDUCATION,
                6.885525668178224,
                79.86018257508276
        );
        Organization havelock = new Organization(
                "Havelock City Mall",
                "Havelock City Mall Admin",
                "info@havelock.lk",
                "password@12345678",
                "0712990644",
                "324-10, Havelock Rd, Colombo 05",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.BUSINESS_AND_COMMERCE,
                6.883296407127292,
                79.86860578067386
        );
        // 6.879290499935319, 79.85983888464753
        Organization savoy = new Organization(
                "Savoy 3D Cinema",
                "Savoy 3D Cinema Admin",
                "info@savoy.lk",
                "password@12345678",
                "0712990645",
                "12, Galle Rd, Colombo 06",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.ARTS_AND_CULTURE,
                6.879290499935319,
                79.85983888464753
        );
        // 6.882078543103582, 79.85919978445263
        Organization laptoplk = new Organization(
                "laptop.lk",
                "laptop.lk Admin",
                "info@laptop.lk",
                "password@12345678",
                "0712990646",
                "401, Galle Road, Davidson Rd, 04",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TECHNOLOGY,
                6.882078543103582,
                79.85919978445263
        );
        // 6.889152497882933, 79.86410370851993
        Organization ndb = new Organization(
                "NDB Bank - Havelock Town",
                "NDB Admin",
                "info@ndb.lk",
                "password@12345678",
                "0712990647",
                "117, Havelock Rd, Colombo 05",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.FINANCE,
                6.889152497882933,
                79.86410370851993
        );
        // 6.918210886156652, 79.84739396718597
        Organization slithm = new Organization(
                "Sri Lanka Institute of Tourism & Hotel Management",
                "SLITHM Admin",
                "slithm@gov.lk",
                "password@12345678",
                "0712990648",
                "78, Galle Rd, Colombo 03",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TOURISM_AND_HOSPITALITY,
                6.918210886156652,
                79.84739396718597
        );
        // 6.919375807281887, 79.84696615485586
        Organization hci = new Organization(
                "High Commission of India",
                "HCI Admin",
                "hci@gov.lk",
                "password@12345678",
                "0712990649",
                "38, Galle - Colombo Rd, Colombo 03",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.GOVERNMENT,
                6.919375807281887,
                79.84696615485586
        );
        // 6.920202538578425, 79.85345037838276
        Organization nawaloka = new Organization(
                "Nawaloka Construction Company",
                "Nawaloka Admin",
                "info@nawaloka.lk",
                "password@12345678",
                "0712990650",
                "115, Sir James Pieris Mawatha, Colombo 02",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.CONSTRUCTION_AND_REAL_ESTATE,
                6.920202538578425,
                79.85345037838276
        );
        // 6.919167407627983, 79.8679937858135
        Organization NHS = new Organization(
                "National Hospital of Sri Lanka",
                "NHS Admin",
                "info@nhs.lk",
                "password@12345678",
                "0712990651",
                "99, Colombo 07",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.HEALTH,
                6.919167407627983,
                79.8679937858135
        );
        // 6.907767473119954, 79.85295804247822
        Organization eagle = new Organization(
                "Eagle Logistics Colombo (Pvt) Ltd",
                "ELC Admin",
                "info@eagle.lk",
                "password@12345678",
                "0712990651",
                "281-1, 1 R. A. De Mel Mawatha, Colombo 03",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TRANSPORTATION,
                6.907767473119954,
                79.85295804247822
        );
        // 6.901909383456497, 79.85152840813443
        Organization sisili = new Organization(
                "Sisili Hanaro Encare (Pvt) Ltd",
                "sisili Admin",
                "info@sisili.lk",
                "password@12345678",
                "0712990652",
                "14, Rheinland Pl, Colombo 03",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.ENVIRONMENT,
                6.901909383456497,
                79.85152840813443
        );
        // 6.911877265744333, 79.85571278769389
        Organization slsea = new Organization(
                "Sri Lanka Sustainable Energy Authority (SLSEA)",
                "SLSEA Admin",
                "info@slsea.lk",
                "password@12345678",
                "0712990653",
                "72, Ananda Coomaraswamy Mawatha, Colombo 07",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.ENERGY,
                6.911877265744333,
                79.85571278769389
        );
        // 6.892402180165025, 79.87060984815436
        Organization povertyRelief = new Organization(
                "Colombo East People’s Poverty Relief Foundation",
                "Poverty Relief Admin",
                "info@ceppef.lk",
                "password@12345678",
                "0712990654",
                "36, Bernard Soysa Mawatha, Colombo 05",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.SOCIAL_SERVICES,
                6.892402180165025,
                79.87060984815436
        );

        organizationRepository.saveAll(List.of(
                foa,
                fol,
                thurstan,
                laksala,
                arcade,
                icbt,
                havelock,
                savoy,
                laptoplk,
                ndb,
                slithm,
                hci,
                nawaloka,
                NHS,
                eagle,
                sisili,
                slsea,
                povertyRelief
        ));

        Optional<Organization> foaOptional = organizationRepository.findOrganizationByEmail("foa@cmb.ac.lk");
        Optional<Organization> folOptional = organizationRepository.findOrganizationByEmail("fol@cmb.ac.lk");
        Optional<Organization> thurstanOptional = organizationRepository.findOrganizationByEmail("thurstan@edu.gov.lk");
        Optional<Organization> laksalaOptional = organizationRepository.findOrganizationByEmail("laksala@google.com");
        Optional<Organization> arcadeOptional = organizationRepository.findOrganizationByEmail("arcade@gov.lk");
        Optional<Organization> icbtOptional = organizationRepository.findOrganizationByEmail("info@icbt.lk");
        Optional<Organization> havelockOptional = organizationRepository.findOrganizationByEmail("info@havelock.lk");
        Optional<Organization> savoyOptional = organizationRepository.findOrganizationByEmail("info@savoy.lk");
        Optional<Organization> laptoplkOptional = organizationRepository.findOrganizationByEmail("info@laptop.lk");
        Optional<Organization> ndbOptional = organizationRepository.findOrganizationByEmail("info@ndb.lk");
        Optional<Organization> slithmOptional = organizationRepository.findOrganizationByEmail("slithm@gov.lk");
        Optional<Organization> hciOptional = organizationRepository.findOrganizationByEmail("hci@gov.lk");
        Optional<Organization> nawalokaOptional = organizationRepository.findOrganizationByEmail("info@nawaloka.lk");
        Optional<Organization> nhsOptional = organizationRepository.findOrganizationByEmail("info@nhs.lk");
        Optional<Organization> eagleOptional = organizationRepository.findOrganizationByEmail("info@eagle.lk");
        Optional<Organization> sisiliOptional = organizationRepository.findOrganizationByEmail("info@sisili.lk");
        Optional<Organization> slseaOptional = organizationRepository.findOrganizationByEmail("info@slsea.lk");
        Optional<Organization> povertyReliefOptional = organizationRepository.findOrganizationByEmail("info@ceppef.lk");

        return args -> {
            if (foaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_foa_1 = new WasteCollectionRequest(
                    62.35,
                    WasteType.BIO_DEGRADABLE,
                    6.901655107547004,
                    79.86182154331027
            );
            WasteCollectionRequest wcr_foa_2 = new WasteCollectionRequest(
                    62.35,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.901655107547004,
                    79.86182154331027
            );
            WasteCollectionRequest wcr_foa_3 = new WasteCollectionRequest(
                    62.35,
                    WasteType.RECYCLABLE,
                    6.901655107547004,
                    79.86182154331027
            );
            wcr_foa_1.setOrganization(foaOptional.get());
            wcr_foa_2.setOrganization(foaOptional.get());
            wcr_foa_3.setOrganization(foaOptional.get());

            if (folOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_fol_1 = new WasteCollectionRequest(
                    35.34,
                    WasteType.BIO_DEGRADABLE,
                    6.901786908082538,
                    79.8590790478905
            );
            WasteCollectionRequest wcr_fol_2 = new WasteCollectionRequest(
                    35.34,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.901786908082538,
                    79.8590790478905
            );
            WasteCollectionRequest wcr_fol_3 = new WasteCollectionRequest(
                    35.34,
                    WasteType.RECYCLABLE,
                    6.901786908082538,
                    79.8590790478905
            );
            wcr_fol_1.setOrganization(folOptional.get());
            wcr_fol_2.setOrganization(folOptional.get());
            wcr_fol_3.setOrganization(folOptional.get());

            if (thurstanOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_thurstan_1 = new WasteCollectionRequest(
                    45.34,
                    WasteType.BIO_DEGRADABLE,
                    6.902647660013757,
                    79.85882185713773
            );
            WasteCollectionRequest wcr_thurstan_2 = new WasteCollectionRequest(
                    45.34,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.902647660013757,
                    79.85882185713773
            );
            WasteCollectionRequest wcr_thurstan_3 = new WasteCollectionRequest(
                    45.34,
                    WasteType.RECYCLABLE,
                    6.902647660013757,
                    79.85882185713773
            );
            wcr_thurstan_1.setOrganization(thurstanOptional.get());
            wcr_thurstan_2.setOrganization(thurstanOptional.get());
            wcr_thurstan_3.setOrganization(thurstanOptional.get());

            if (laksalaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_laksala_1 = new WasteCollectionRequest(
                    52.46,
                    WasteType.BIO_DEGRADABLE,
                    6.897983357274245,
                    79.86031726150777
            );
            WasteCollectionRequest wcr_laksala_2 = new WasteCollectionRequest(
                    52.46,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.897983357274245,
                    79.86031726150777
            );
            WasteCollectionRequest wcr_laksala_3 = new WasteCollectionRequest(
                    52.46,
                    WasteType.RECYCLABLE,
                    6.897983357274245,
                    79.86031726150777
            );
            wcr_laksala_1.setOrganization(laksalaOptional.get());
            wcr_laksala_2.setOrganization(laksalaOptional.get());
            wcr_laksala_3.setOrganization(laksalaOptional.get());

            if (arcadeOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_arcade_1 = new WasteCollectionRequest(
                    63.67,
                    WasteType.BIO_DEGRADABLE,
                    6.902189398848052,
                    79.86972437769595
            );
            WasteCollectionRequest wcr_arcade_2 = new WasteCollectionRequest(
                    63.67,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.902189398848052,
                    79.86972437769595
            );
            WasteCollectionRequest wcr_arcade_3 = new WasteCollectionRequest(
                    63.67,
                    WasteType.RECYCLABLE,
                    6.902189398848052,
                    79.86972437769595
            );
            wcr_arcade_1.setOrganization(arcadeOptional.get());
            wcr_arcade_2.setOrganization(arcadeOptional.get());
            wcr_arcade_3.setOrganization(arcadeOptional.get());

            if (icbtOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_icbt_1 = new WasteCollectionRequest(
                    35.27,
                    WasteType.BIO_DEGRADABLE,
                    6.885525668178224,
                    79.86018257508276
            );
            WasteCollectionRequest wcr_icbt_2 = new WasteCollectionRequest(
                    35.27,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.885525668178224,
                    79.86018257508276
            );
            WasteCollectionRequest wcr_icbt_3 = new WasteCollectionRequest(
                    35.27,
                    WasteType.RECYCLABLE,
                    6.885525668178224,
                    79.86018257508276
            );
            wcr_icbt_1.setOrganization(icbtOptional.get());
            wcr_icbt_2.setOrganization(icbtOptional.get());
            wcr_icbt_3.setOrganization(icbtOptional.get());

            if (havelockOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_havelock_1 = new WasteCollectionRequest(
                    46.72,
                    WasteType.BIO_DEGRADABLE,
                    6.883296407127292,
                    79.86860578067386
            );
            WasteCollectionRequest wcr_havelock_2 = new WasteCollectionRequest(
                    46.72,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.883296407127292,
                    79.86860578067386
            );
            WasteCollectionRequest wcr_havelock_3 = new WasteCollectionRequest(
                    46.72,
                    WasteType.RECYCLABLE,
                    6.883296407127292,
                    79.86860578067386
            );
            wcr_havelock_1.setOrganization(havelockOptional.get());
            wcr_havelock_2.setOrganization(havelockOptional.get());
            wcr_havelock_3.setOrganization(havelockOptional.get());

            if (savoyOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_savoy_1 = new WasteCollectionRequest(
                    46.72,
                    WasteType.BIO_DEGRADABLE,
                    6.879290499935319,
                    79.85983888464753
            );
            WasteCollectionRequest wcr_savoy_2 = new WasteCollectionRequest(
                    46.72,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.879290499935319,
                    79.85983888464753
            );
            WasteCollectionRequest wcr_savoy_3 = new WasteCollectionRequest(
                    46.72,
                    WasteType.RECYCLABLE,
                    6.879290499935319,
                    79.85983888464753
            );
            wcr_savoy_1.setOrganization(savoyOptional.get());
            wcr_savoy_2.setOrganization(savoyOptional.get());
            wcr_savoy_3.setOrganization(savoyOptional.get());

            if (laptoplkOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_laptoplk_1 = new WasteCollectionRequest(
                    33.22,
                    WasteType.BIO_DEGRADABLE,
                    6.882078543103582,
                    79.85919978445263
            );
            WasteCollectionRequest wcr_laptoplk_2 = new WasteCollectionRequest(
                    33.22,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.882078543103582,
                    79.85919978445263
            );
            WasteCollectionRequest wcr_laptoplk_3 = new WasteCollectionRequest(
                    33.22,
                    WasteType.RECYCLABLE,
                    6.882078543103582,
                    79.85919978445263
            );
            wcr_laptoplk_1.setOrganization(laptoplkOptional.get());
            wcr_laptoplk_2.setOrganization(laptoplkOptional.get());
            wcr_laptoplk_3.setOrganization(laptoplkOptional.get());

            if (ndbOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_ndb_1 = new WasteCollectionRequest(
                    43.59,
                    WasteType.BIO_DEGRADABLE,
                    6.889152497882933,
                    79.86410370851993
            );
            WasteCollectionRequest wcr_ndb_2 = new WasteCollectionRequest(
                    43.59,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.889152497882933,
                    79.86410370851993
            );
            WasteCollectionRequest wcr_ndb_3 = new WasteCollectionRequest(
                    43.59,
                    WasteType.RECYCLABLE,
                    6.889152497882933,
                    79.86410370851993
            );
            wcr_ndb_1.setOrganization(ndbOptional.get());
            wcr_ndb_2.setOrganization(ndbOptional.get());
            wcr_ndb_3.setOrganization(ndbOptional.get());

            if (slithmOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_slithm_1 = new WasteCollectionRequest(
                    51.52,
                    WasteType.BIO_DEGRADABLE,
                    6.918210886156652,
                    79.84739396718597
            );
            WasteCollectionRequest wcr_slithm_2 = new WasteCollectionRequest(
                    51.52,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.918210886156652,
                    79.84739396718597
            );
            WasteCollectionRequest wcr_slithm_3 = new WasteCollectionRequest(
                    51.52,
                    WasteType.RECYCLABLE,
                    6.918210886156652,
                    79.84739396718597
            );
            wcr_slithm_1.setOrganization(slithmOptional.get());
            wcr_slithm_2.setOrganization(slithmOptional.get());
            wcr_slithm_3.setOrganization(slithmOptional.get());

            if (hciOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_hci_1 = new WasteCollectionRequest(
                    56.91,
                    WasteType.BIO_DEGRADABLE,
                    6.919375807281887,
                    79.84696615485586
            );
            WasteCollectionRequest wcr_hci_2 = new WasteCollectionRequest(
                    56.91,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.919375807281887,
                    79.84696615485586
            );
            WasteCollectionRequest wcr_hci_3 = new WasteCollectionRequest(
                    56.91,
                    WasteType.RECYCLABLE,
                    6.919375807281887,
                    79.84696615485586
            );
            wcr_hci_1.setOrganization(hciOptional.get());
            wcr_hci_2.setOrganization(hciOptional.get());
            wcr_hci_3.setOrganization(hciOptional.get());

            if (nawalokaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_nawaloka_1 = new WasteCollectionRequest(
                    48.55,
                    WasteType.BIO_DEGRADABLE,
                    6.920202538578425,
                    79.85345037838276
            );
            WasteCollectionRequest wcr_nawaloka_2 = new WasteCollectionRequest(
                    48.55,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.920202538578425,
                    79.85345037838276
            );
            WasteCollectionRequest wcr_nawaloka_3 = new WasteCollectionRequest(
                    48.55,
                    WasteType.RECYCLABLE,
                    6.920202538578425,
                    79.85345037838276
            );
            wcr_nawaloka_1.setOrganization(nawalokaOptional.get());
            wcr_nawaloka_2.setOrganization(nawalokaOptional.get());
            wcr_nawaloka_3.setOrganization(nawalokaOptional.get());

            if (nhsOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_nhs_1 = new WasteCollectionRequest(
                    43.59,
                    WasteType.BIO_DEGRADABLE,
                    6.919167407627983,
                    79.8679937858135
            );
            WasteCollectionRequest wcr_nhs_2 = new WasteCollectionRequest(
                    43.59,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.919167407627983,
                    79.8679937858135
            );
            WasteCollectionRequest wcr_nhs_3 = new WasteCollectionRequest(
                    43.59,
                    WasteType.RECYCLABLE,
                    6.919167407627983,
                    79.8679937858135
            );
            wcr_nhs_1.setOrganization(nhsOptional.get());
            wcr_nhs_2.setOrganization(nhsOptional.get());
            wcr_nhs_3.setOrganization(nhsOptional.get());

            if (eagleOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_eagle_1 = new WasteCollectionRequest(
                    54.62,
                    WasteType.BIO_DEGRADABLE,
                    6.907767473119954,
                    79.85295804247822
            );
            WasteCollectionRequest wcr_eagle_2 = new WasteCollectionRequest(
                    54.62,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.907767473119954,
                    79.85295804247822
            );
            WasteCollectionRequest wcr_eagle_3 = new WasteCollectionRequest(
                    54.62,
                    WasteType.RECYCLABLE,
                    6.907767473119954,
                    79.85295804247822
            );
            wcr_eagle_1.setOrganization(eagleOptional.get());
            wcr_eagle_2.setOrganization(eagleOptional.get());
            wcr_eagle_3.setOrganization(eagleOptional.get());

            if (sisiliOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_sisili_1 = new WasteCollectionRequest(
                    67.62,
                    WasteType.BIO_DEGRADABLE,
                    6.901909383456497,
                    79.85152840813443
            );
            WasteCollectionRequest wcr_sisili_2 = new WasteCollectionRequest(
                    67.62,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.901909383456497,
                    79.85152840813443
            );
            WasteCollectionRequest wcr_sisili_3 = new WasteCollectionRequest(
                    67.62,
                    WasteType.RECYCLABLE,
                    6.901909383456497,
                    79.85152840813443
            );
            wcr_sisili_1.setOrganization(sisiliOptional.get());
            wcr_sisili_2.setOrganization(sisiliOptional.get());
            wcr_sisili_3.setOrganization(sisiliOptional.get());

            if (slseaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_slsea_1 = new WasteCollectionRequest(
                    70.60,
                    WasteType.BIO_DEGRADABLE,
                    6.911877265744333,
                    79.85571278769389
            );
            WasteCollectionRequest wcr_slsea_2 = new WasteCollectionRequest(
                    70.60,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.911877265744333,
                    79.85571278769389
            );
            WasteCollectionRequest wcr_slsea_3 = new WasteCollectionRequest(
                    70.60,
                    WasteType.RECYCLABLE,
                    6.911877265744333,
                    79.85571278769389
            );
            wcr_slsea_1.setOrganization(slseaOptional.get());
            wcr_slsea_2.setOrganization(slseaOptional.get());
            wcr_slsea_3.setOrganization(slseaOptional.get());

            if (povertyReliefOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            WasteCollectionRequest wcr_pro_1 = new WasteCollectionRequest(
                    51.89,
                    WasteType.BIO_DEGRADABLE,
                    6.892402180165025,
                    79.87060984815436
            );
            WasteCollectionRequest wcr_pro_2 = new WasteCollectionRequest(
                    51.89,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.892402180165025,
                    79.87060984815436
            );
            WasteCollectionRequest wcr_pro_3 = new WasteCollectionRequest(
                    51.89,
                    WasteType.RECYCLABLE,
                    6.892402180165025,
                    79.87060984815436
            );
            wcr_pro_1.setOrganization(povertyReliefOptional.get());
            wcr_pro_2.setOrganization(povertyReliefOptional.get());
            wcr_pro_3.setOrganization(povertyReliefOptional.get());

            wasteCollectionRequestRepository.saveAll(List.of(
                    wcr_foa_1,
                    wcr_foa_2,
                    wcr_foa_3,
                    wcr_fol_1,
                    wcr_fol_2,
                    wcr_fol_3,
                    wcr_thurstan_1,
                    wcr_thurstan_2,
                    wcr_thurstan_3,
                    wcr_laksala_1,
                    wcr_laksala_2,
                    wcr_laksala_3,
                    wcr_arcade_1,
                    wcr_arcade_2,
                    wcr_arcade_3,
                    wcr_icbt_1,
                    wcr_icbt_2,
                    wcr_icbt_3,
                    wcr_havelock_1,
                    wcr_havelock_2,
                    wcr_havelock_3,
                    wcr_savoy_1,
                    wcr_savoy_2,
                    wcr_savoy_3,
                    wcr_laptoplk_1,
                    wcr_laptoplk_2,
                    wcr_laptoplk_3,
                    wcr_ndb_1,
                    wcr_ndb_2,
                    wcr_ndb_3,
                    wcr_slithm_1,
                    wcr_slithm_2,
                    wcr_slithm_3,
                    wcr_hci_1,
                    wcr_hci_2,
                    wcr_hci_3,
                    wcr_nawaloka_1,
                    wcr_nawaloka_2,
                    wcr_nawaloka_3,
                    wcr_nhs_1,
                    wcr_nhs_2,
                    wcr_nhs_3,
                    wcr_eagle_1,
                    wcr_eagle_2,
                    wcr_eagle_3,
                    wcr_sisili_1,
                    wcr_sisili_2,
                    wcr_sisili_3,
                    wcr_slsea_1,
                    wcr_slsea_2,
                    wcr_slsea_3,
                    wcr_pro_1,
                    wcr_pro_2,
                    wcr_pro_3
            ));
        };
    }
}
