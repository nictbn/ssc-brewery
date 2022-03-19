package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerIT extends BaseIT {

    @ParameterizedTest(name= "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminCustomer")
    void testListCustomersAuth(String user, String pwd) throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic(user, pwd)))
                .andExpect(status().isOk());
    }

    @Test
    public void testListCustomersNoAuth() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testListCustomersNotLoggedIn() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }
}
