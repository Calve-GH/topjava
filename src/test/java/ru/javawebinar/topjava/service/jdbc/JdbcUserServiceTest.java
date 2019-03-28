package ru.javawebinar.topjava.service.jdbc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Before
    public void setUp() throws Exception {
        cacheManager.getCacheNames().forEach((name)->cacheManager.getCache(name).clear());
    }

    @Override
    public void get() throws Exception {
        super.get();
    }

    @Ignore
    @Test
    public void testValidation() throws Exception {
        super.testValidation();
    }
}