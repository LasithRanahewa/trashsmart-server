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
                OrgType.EDUCATION
        );
        Organization fos = new Organization(
                "FOL",
                "FOL Admin",
                "fol@cmb.ac.lk",
                "password@12345678",
                "0712990639",
                "50, Reid Avenue, Colombo 07",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.EDUCATION
        );
        // 6.902647660013757, 79.85882185713773
        Organization thurstan = new Organization(
                "Thurstan College",
                "Thurstan College Admin",
                "thurstan@edu.gov.lk",
                "password@12345678",
                "0712990640",
                "35, Thurstan Road, Colombo 07",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.EDUCATION
        );
        // 6.897983357274245, 79.86031726150777
        Organization laksala = new Organization(
                "Laksala",
                "Laksala Admin",
                "laksala@google.com",
                "password@12345678",
                "0712990641",
                "215, Bauddhaloka Mawatha, Colombo 07",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TOURISM_AND_HOSPITALITY
        );
        // 6.902189398848052, 79.86972437769595
        Organization arcade = new Organization(
                "Arcade Independence Square",
                "Arcade Admin",
                "arcade@gov.lk",
                "password@12345678",
                "0712990642",
                "Independence Square, Colombo 07",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.BUSINESS_AND_COMMERCE
        );
        // 6.885525668178224, 79.86018257508276
        Organization icbt = new Organization(
                "ICBT",
                "ICBT Admin",
                "info@icbt.lk",
                "password@12345678",
                "0712990643",
                "36, De Kretser Pl, Colombo 04",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.EDUCATION
        );
        // 6.883296407127292, 79.86860578067386
        Organization havelock = new Organization(
                "Havelock City Mall",
                "Havelock City Mall Admin",
                "info@havelock.lk",
                "password@12345678",
                "0712990644",
                "324-10, Havelock Rd, Colombo 05",
                "www.facebook.com",
                Scale.LARGE,
                OrgType.BUSINESS_AND_COMMERCE
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
                OrgType.ARTS_AND_CULTURE
        );
        // 6.879290499935319, 79.85983888464753
        Organization laptoplk = new Organization(
                "laptop.lk",
                "laptop.lk Admin",
                "info@laptop.lk",
                "password@12345678",
                "0712990646",
                "401, Galle Road, Davidson Rd, 04",
                "www.facebook.com",
                Scale.SMALL,
                OrgType.TECHNOLOGY
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
                OrgType.FINANCE
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
                OrgType.TOURISM_AND_HOSPITALITY
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
                OrgType.GOVERNMENT
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
                OrgType.CONSTRUCTION_AND_REAL_ESTATE
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
                OrgType.HEALTH
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
                OrgType.TRANSPORTATION
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
                OrgType.ENVIRONMENT
        );
        // 6.901909383456497, 79.85152840813443
        Organization slsea = new Organization(
                "Sri Lanka Sustainable Energy Authority (SLSEA)",
                "SLSEA Admin",
                "info@slsea.lk",
                "password@12345678",
                "0712990653",
                "72, Ananda Coomaraswamy Mawatha, Colombo 07",
                "www.facebook.com",
                Scale.MEDIUM,
                OrgType.ENERGY
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
                OrgType.SOCIAL_SERVICES
        );

        organizationRepository.saveAll(List.of(foa, fos));

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
            WasteCollectionRequest wcr1 = new WasteCollectionRequest(
                    620.35,
                    WasteType.BIO_DEGRADABLE,
                    6.901655107547004,
                    79.86182154331027
            );
            if (foaOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            wcr1.setOrganization(foaOptional.get());

            WasteCollectionRequest wcr2 = new WasteCollectionRequest(
                    35.34,
                    WasteType.NON_BIO_DEGRADABLE,
                    6.901786908082538,
                    79.8590790478905
            );
            if (folOptional.isEmpty()) {
                throw new IllegalStateException("No Organization");
            }
            wcr2.setOrganization(folOptional.get());

            wasteCollectionRequestRepository.saveAll(List.of(wcr1, wcr2));
        };
    }
}
