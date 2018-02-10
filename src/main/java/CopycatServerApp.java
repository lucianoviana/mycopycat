import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class CopycatServerApp {

    public static void main(String[] a){
        SpringApplication.run(CopycatServerApp.class, a);
    }
}
