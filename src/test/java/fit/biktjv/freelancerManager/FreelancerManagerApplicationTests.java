package fit.biktjv.freelancerManager;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class FreelancerManagerApplicationTests {
//
//    @LocalServerPort
//    int port;
//    @Autowired
//    TestRestTemplate restTemplate;
//
//    @Test
//    void contextLoads() {
//        String root = "http://localhost:" + port;
//        String freelancerRestUrl = root + "/rest/freelancer";
//        String freelancerName = "Tom";
//        String freelancerUri = restTemplate.
//                postForLocation(freelancerRestUrl, new Freelancer.FreelancerDTO(0L, freelancerName)).toString();
//        int lsi = freelancerUri.lastIndexOf('/');
//        String freelancerId = freelancerUri.substring(lsi + 1);
//        String freelancerWebUrl = root + "/freelancer";
//        String page = restTemplate.getForObject(freelancerWebUrl, String.class);
//        assertThat(page.contains(freelancerName) && page.contains(freelancerId));
//
//    }
//
//}
