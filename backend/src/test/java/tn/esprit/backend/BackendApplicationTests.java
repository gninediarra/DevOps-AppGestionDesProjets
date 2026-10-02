package tn.esprit.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Désactivé : nécessite une connexion MySQL active. Utiliser Testcontainers en production.")
@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
