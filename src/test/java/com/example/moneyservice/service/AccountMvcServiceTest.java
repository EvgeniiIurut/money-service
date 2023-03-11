//package com.example.moneyservice.service;
//
//import com.example.moneyservice.controller.TransferController;
//import com.example.moneyservice.dto.TransferFromAccountToCashDto;
//import com.example.moneyservice.model.Account;
//import com.example.moneyservice.model.Transfer;
//import com.example.moneyservice.service.impl.TransferServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.math.BigDecimal;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicReference;
//
//import static com.example.moneyservice.utils.JsonStringConverter.asJsonString;
//import static java.nio.charset.StandardCharsets.UTF_8;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
//@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WebMvcTest(TransferController.class)
//class AccountMvcServiceTest {
//    private MockMvc mockMvc;
//    final private ExecutorService executorService = Executors.newFixedThreadPool(1);
//    final private TransferFromAccountToCashDto transferFromAccountToCashDto = new TransferFromAccountToCashDto();
//    final private Transfer transfer = new Transfer(1L,new Account(1L,));
//
//    @MockBean
//    private TransferServiceImpl transferServiceImpl;
//
//    @BeforeAll
//    void setUp() {
//        mockMvc = getMockMvc();
//        transferFromAccountToCashDto.setFromAccount(1L);
//        transferFromAccountToCashDto.setAmount(new BigDecimal(10));
//    }
//
//    @Test
//    void reduceAccountAmount() throws InterruptedException {
//        AtomicReference<Transfer> accountAtomicReference = new AtomicReference<>();
//        for (int i = 0; i < 1; i++) {
//            executorService.execute(() -> {
//                log.info("Tread " + Thread.currentThread().getName());
//                try {
////                    MvcResult mvcResult = mockMvc
////                            .perform(post("http://localhost:8080/take")
////                                    .characterEncoding(UTF_8)
////                                    .contentType(MediaType.APPLICATION_JSON)
////                                    .content(asJsonString(transferFromAccountToCashDto)))
////                            .andExpect(status().isOk()).andReturn();
////                    Transfer transfer = new ObjectMapper().createParser(mvcResult.getResponse().getContentAsString()).readValueAs(Transfer.class);
////                    System.out.println(transfer.toString());
//
//                    doReturn(transferFromAccountToCashDto).when(transferServiceImpl).createTransferFromAccountToCash(1L, new BigDecimal(10));
//
//                    MvcResult mvcResult = mockMvc
//                            .perform(post("http://localhost:8080/take")
//                                    .characterEncoding(UTF_8)
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(asJsonString(transferFromAccountToCashDto)))
//                            .andExpect(status().isOk()).andReturn();
//
//                    String contentAsString = mvcResult.getResponse().getContentAsString();
//                    System.out.println(contentAsString);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//        Thread.sleep(5000);
//        assertThat(accountAtomicReference.get().getAmount()).isEqualTo(new BigDecimal(10));
//    }
//
//    private MockMvc getMockMvc() {
//        return standaloneSetup(new TransferController(transferServiceImpl)).build();
//    }
//}