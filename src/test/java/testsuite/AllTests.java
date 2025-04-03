package testsuite;

import com.example.spideremporium.model.SpiderOpsTest;
import com.example.spideremporium.model.VenomousSpiderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.example.spideremporium.model.CustomerTest;

@RunWith(value= Suite.class)
@Suite.SuiteClasses(value = {
        CustomerTest.class,
        VenomousSpiderTest.class,
        SpiderOpsTest.class
})

public class AllTests {
}
