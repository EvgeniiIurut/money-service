package com.example.moneyservice.service;

import com.example.moneyservice.controller.TransferController;
import com.example.moneyservice.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.moneyservice.utils.JsonStringConverter.jsonToObject;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Slf4j
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountMvcServiceTest {
    private MockMvc mockMvc;
    final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @MockBean
    private TransferService transferService;

    @BeforeAll
    void setUp() {
        mockMvc = getMockMvc();
    }

    @Test
    void reduceAccountAmount() throws InterruptedException {
        AtomicReference<Account> accountAtomicReference;
        accountAtomicReference = new AtomicReference<>();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                log.info("Tread " + Thread.currentThread().getName());
                try {
                    MvcResult mvcResult = mockMvc.perform(patch("http://localhost:8080/take").param("fromAccount", "1L").param("amount", "10").characterEncoding(UTF_8).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
                    Account account = jsonToObject(mvcResult.getResponse().getContentAsString(), Account.class);
                    accountAtomicReference.set(account);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(10000);
        assertThat(accountAtomicReference.get().getBalance()).isEqualTo(new BigDecimal(-100));
    }

    private MockMvc getMockMvc() {
        return standaloneSetup(new TransferController(transferService))
//                .setControllerAdvice(new DefaultRestExceptionHandler())
                .build();
    }
}