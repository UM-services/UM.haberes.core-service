package um.haberes.core.controller.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.service.facade.MailingService;
import um.haberes.core.util.transfer.MailInfo;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MailingControllerTest {

    @Mock
    private MailingService service;

    private MailingController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new MailingController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private MailInfo sampleMailInfo() {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setLegajoId(100L);
        mailInfo.setMailto("destino@example.com");
        mailInfo.setMailcc("copia@example.com");
        mailInfo.setReplyto("remite@example.com");
        mailInfo.setSubject("Asunto de prueba");
        mailInfo.setMessage("Mensaje de prueba");
        return mailInfo;
    }

    @Test
    void mailing_returnsNoContent() throws Exception {
        mockMvc.perform(post("/api/haberes/core/mailing/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(sampleMailInfo()))))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).mailing(List.of(sampleMailInfo()));
    }
}
