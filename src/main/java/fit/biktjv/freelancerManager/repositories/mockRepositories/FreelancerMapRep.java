package fit.biktjv.freelancerManager.repositories.mockRepositories;

import fit.biktjv.freelancerManager.entities.Address;
import fit.biktjv.freelancerManager.entities.Freelancer;
import fit.biktjv.freelancerManager.entities.Skill;
import fit.biktjv.freelancerManager.repositories.FreelancerDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FreelancerMapRep implements FreelancerDAO {

    private Map<Long, Freelancer> freelancers = new HashMap<>();
    private Map<Long, Address> addresses = new HashMap<>();
    private Map<Long, Skill> skills = new HashMap<>();


    public FreelancerMapRep() {
        init();
    }

    void init() {
        Freelancer freelancer = new Freelancer();
        freelancer.setFirstName("Tom");
        freelancer.setMiddleName("M");
        freelancer.setLastName("Sawyer");
        freelancer.setEmail("email@email.com");
        freelancer.setPhoneNumber("+420 123456789");
        LocalDate birthday = LocalDate.parse("1990-04-04", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        freelancer.setBirthday(birthday);
        freelancer.setAdditionalInformation("Some additional information");

        Address address = new Address(
                1L, "California", "Los Angeles", "LA St", "1", "12322"
        );
        freelancer.setAddress(address);

        Skill skill = new Skill(1L, "Java", 5, "Good");

        freelancer.setSkills(List.of(skill));

        createFreelancer(freelancer);
    }

    public void clear() {
        // reset the tables after each test
        freelancers.clear();
        addresses.clear();
        skills.clear();

        init();
    }


    @Override
    public List<Freelancer> getAllFreelancers() {
        return new ArrayList<>(freelancers.values());
    }


    @Override
    public Long createFreelancer(Freelancer freelancer) {
        Long freelancerId = freelancers.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0l) + 1;
        freelancer.setFreelancerId(freelancerId);

        Address address = freelancer.getAddress();
        if (address != null) {
            Long addressId = addresses.keySet().stream()
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0l) + 1;
            address.setAddressId(addressId);
            addresses.put(addressId, address);
        }

        List<Skill> skills = freelancer.getSkills();
        if (skills != null) {
            for (Skill skill : skills) {
                Long skillId = this.skills.keySet().stream()
                        .mapToLong(Long::longValue)
                        .max()
                        .orElse(0l) + 1;
                skill.setSkillId(skillId);
                this.skills.put(skillId, skill);
            }
        }

        freelancers.put(freelancerId, freelancer);
        return freelancerId;
    }

    @Override
    public void updateFreelancer(Freelancer freelancer, List<Skill> skillsToDelete) {
        freelancers.put(freelancer.getFreelancerId(), freelancer);
    }

    @Override
    public void deleteFreelancer(Long id) {
        freelancers.remove(id);
    }

    @Override
    public Freelancer findFreelancer(Long freelancerId) {
        return freelancers.get(freelancerId);
    }

}
