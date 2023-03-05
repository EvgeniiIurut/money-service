package com.example.moneyservice;

import com.example.moneyservice.controller.TransferController;
import com.example.moneyservice.dto.TransferDto;
import com.example.moneyservice.service.TransferService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebMvcTest(TransferController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferControllerTest {
    private static final String TRANSFER_ENDPOINT = "http://localhost:8080/transfer";
    private static final String UTF_8 = "UTF-8";
    private static final long ACCOUNT_ID_FROM = 1L;
    private static final long ACCOUNT_ID_TO = 2L;
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(50);

    @Autowired
    protected TransferService transferService;

    private MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        mockMvc = getMockMvc();
    }

    @Test
    void should_getAllClientNotifications() throws Exception {

        TransferDto transferDto = TransferDto.builder().fromAccount(ACCOUNT_ID_FROM).toAccount(ACCOUNT_ID_TO).amount(AMOUNT).build();

        MvcResult mvcResult = mockMvc.perform(get(TRANSFER_ENDPOINT)
                        .characterEncoding(UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private MockMvc getMockMvc() {
        return standaloneSetup(new TransferController(transferService))
                .build();
    }
}