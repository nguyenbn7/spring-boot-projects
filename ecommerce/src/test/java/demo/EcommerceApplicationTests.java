package demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class EcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifiesModularStructure() {
		ApplicationModules modules = ApplicationModules.of(EcommerceApplication.class);
		modules.verify();
	}

}
