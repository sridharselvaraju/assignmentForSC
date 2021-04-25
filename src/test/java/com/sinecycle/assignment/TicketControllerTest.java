package com.sinecycle.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinecycle.assignment.controller.TicketController;
import com.sinecycle.assignment.entity.CustomerInfo;
import com.sinecycle.assignment.entity.Ticket;
import com.sinecycle.assignment.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
@ActiveProfiles("test")
public class TicketControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper mapper;

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private TicketService ticketService;

  private Ticket mockTicket = new Ticket();

  private Ticket createNewTicket() {
    mockTicket.setType("sampleType");
    mockTicket.setDescription("sample Description");
    mockTicket.setTitle("sample Title");
    mockTicket.setPriority("High");
    mockTicket.setStatus("new");

    CustomerInfo mockCustomer = new CustomerInfo();
    mockCustomer.setCustomerName("sample user");
    mockCustomer.setCustomerMailId("sampleUser@xy.com");
    mockCustomer.setRole("developer");

    mockTicket.setCustomer(mockCustomer);

    return mockTicket;
  }

  private Ticket updateMockTicket(Ticket mockTicketUpdated) {
    mockTicket = mockTicketUpdated;
    return mockTicket;
  }

  @Test
  private void createMockTicket() throws Exception {

    Ticket ticket = createNewTicket();
    OngoingStubbing<Ticket> when = Mockito.when(
            ticketService.createTicket(Mockito.any(Ticket.class))
    );

    when.thenReturn(ticket);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/tickets/create")
            .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(ticket));

    mockMvc.perform(builder).andExpect(status().isCreated()).andExpect((ResultMatcher) jsonPath("$.type", is("sampleType")))
            .andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(ticket)));
  }

}
