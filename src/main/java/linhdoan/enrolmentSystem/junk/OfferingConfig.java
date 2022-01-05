//package linhdoan.enrolmentSystem.offering;
//
//import linhdoan.enrolmentSystem.student.Student;
//import linhdoan.enrolmentSystem.student.StudentRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//
//@Configuration
//public class OfferingConfig {
//    @Bean
//    CommandLineRunner testOffering(OfferingRepository repository) {
//        return args -> {
//            OfferingId id = new OfferingId("FIT2107", LocalDate.of(2021, 1, 1), '1');
//            Offering offer = new Offering(id);
////            repository.saveAll(List.of(offer));
//        };
//    }
//}
