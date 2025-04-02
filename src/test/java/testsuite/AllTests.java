package testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.example.spideremporium.model.CustomerTest;

@RunWith(value= Suite.class)
@Suite.SuiteClasses(value = {
        CustomerTest.class
})

public class AllTests {
}
