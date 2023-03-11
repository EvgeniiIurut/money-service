package com.example.moneyservice.controller;

import com.example.moneyservice.dto.TransferFromAccountToCashDto;
import com.example.moneyservice.model.Account;
import com.example.moneyservice.model.Transfer;
import com.example.moneyservice.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.example.moneyservice.utils.JsonStringConverter.asJsonString;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(TransferController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferControllerTest {
    private final String urlTakeEndpointTemplate = "http://localhost:8080/take";
    private final Long idOfFirstAccount = 1L;
    private final BigDecimal amountOfFirstAccount = new BigDecimal(10);

    @MockBean
    private TransferServiceImpl transferServiceImpl;

    private MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        mockMvc = getMockMvc();
    }

    @Test
    void createTransferFromAccountToCash() throws Exception {
        when(transferServiceImpl
                .createTransferFromAccountToCash(1L, new BigDecimal(10)))
                .thenReturn(getResponseTransferDto(getRequestAccount()));

        MvcResult mvcResult = mockMvc
                .perform(post(urlTakeEndpointTemplate)
                        .characterEncoding(UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getTransferFromAccountToCashDto())))
                .andExpect(status().isOk()).andReturn();
        assertThat(asJsonString(getResponseTransferDto(getRequestAccount())))
                .isEqualTo(mvcResult
                        .getResponse()
                        .getContentAsString());
        verify(transferServiceImpl, times(1))
                .createTransferFromAccountToCash(idOfFirstAccount, amountOfFirstAccount);
    }

    @Test
    void createTransferBetweenAccounts() {
    }

    @Test
    void createTransferFromCashToAccount() {
    }

    private MockMvc getMockMvc() {
        return standaloneSetup(new TransferController(transferServiceImpl)).build();
    }

    public static TransferFromAccountToCashDto getTransferFromAccountToCashDto() {
        TransferFromAccountToCashDto transferFromAccountToCashDto = new TransferFromAccountToCashDto();
        transferFromAccountToCashDto.setFromAccount(1L);
        transferFromAccountToCashDto.setAmount(new BigDecimal(10));
        return transferFromAccountToCashDto;
    }

    public static Transfer getResponseTransferDto(Account account) {
        Transfer transfer = new Transfer();
        transfer.setFromAccount(account);
        transfer.setAmount(new BigDecimal(10));
        return transfer;
    }

    public static Account getRequestAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(10));
        account.setVersion(0);
        return account;
    }
}