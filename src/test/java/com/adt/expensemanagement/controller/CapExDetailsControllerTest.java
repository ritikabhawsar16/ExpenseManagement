package com.adt.expensemanagement.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.adt.expensemanagement.controllers.CapExDetailsController;
import com.adt.expensemanagement.models.CapExDetails;
import com.adt.expensemanagement.services.interfaces.CapExDetailsService;

public class CapExDetailsControllerTest {
    private static final Integer id = 1;
    private static final LocalDate date = LocalDate.of(2020, 1, 8);
    private static final String expenseDetails = "Chair";
    private static final String gstBill = "23ded23";
    private static final Integer amount = 2221;
    private static final String paidBy = "DJ";
    private static final String comment = "new";
    private static final String mode = "google";
    @Mock
    private CapExDetailsService capExDetailsService;

    @InjectMocks
    private CapExDetailsController capExDetailsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(capExDetailsController).build();
    }


//    @Test
//    public void testCreateCapExDetails() throws Exception {
//        CapExDetails capExDetails = giveExpDetails().get(0);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JSR310Module());
//        when(capExDetailsService.createCapExDetails(any(CapExDetails.class))).thenReturn(capExDetails);
//        mockMvc.perform(MockMvcRequestBuilders.post("/capExDetails/createCapExDetails").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(capExDetails))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.expenseDetails").value(expenseDetails)).andExpect(jsonPath("$.date".toString()).exists()).andExpect(jsonPath("$.mode").value(mode)).andExpect(jsonPath("$.amount").value(amount)).andExpect(jsonPath("$.comment").value(comment)).andExpect(jsonPath("$.gstBill").value(gstBill)).andExpect(jsonPath("$.paidBy").value(paidBy)).andReturn();
//
//        verify(capExDetailsService, times(1)).createCapExDetails(any(CapExDetails.class));
//        verifyNoMoreInteractions(capExDetailsService);
//    }


	/*
	 * @Test public void testGetAllCapExDetails() throws Exception {
	 * List<CapExDetails> capExDetail = giveExpDetails();
	 * when(capExDetailsService.getAllCapExDetails()).thenReturn(capExDetail);
	 * mockMvc.perform(get("/capExDetails/getAllCapExDetails")).andExpect(status().
	 * isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).
	 * andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isNotEmpty()).
	 * andExpect(jsonPath("$[0].id").value(id)).andExpect(jsonPath("$.[0].date".
	 * toString()).exists()).andExpect(jsonPath("$[0].expenseDetails").value(
	 * expenseDetails)).andExpect(jsonPath("$[0].mode").value(mode)).andExpect(
	 * jsonPath("$[0].amount").value(amount)).andExpect(jsonPath("$[0].comment").
	 * value(comment)).andExpect(jsonPath("$[0].gstBill").value(gstBill)).andExpect(
	 * jsonPath("$[0].paidBy").value(paidBy)).andReturn();
	 * verify(capExDetailsService, times(1)).getAllCapExDetails();
	 * verifyNoMoreInteractions(capExDetailsService); }
	 */


    @Test
    public void testGetCapExDetailsById() throws Exception {
        CapExDetails capExDetail = giveExpDetails().get(0);
        when(capExDetailsService.getCapExDetailsById(id)).thenReturn(capExDetail);

        mockMvc.perform(get("/capExDetails/getByCapExDetails/" + id)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.expenseDetails").value(expenseDetails)).andExpect(jsonPath("$.mode").value(mode)).andExpect(jsonPath("$.amount").value(amount)).andExpect(jsonPath("$.comment").value(comment)).andExpect(jsonPath("$.gstBill").value(gstBill)).andExpect(jsonPath("$.paidBy").value(paidBy)).andReturn();

        verify(capExDetailsService, times(1)).getCapExDetailsById(id);
        verifyNoMoreInteractions(capExDetailsService);
    }


//    @Test
//    public void testUpdateCapExDetailsById() throws Exception {
//        CapExDetails capExDetail = giveExpDetails().get(0);
//        when(capExDetailsService.updateCapExDetailsById(eq(id), any(CapExDetails.class))).thenReturn(capExDetail);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JSR310Module());
//        mockMvc.perform(MockMvcRequestBuilders.put("/capExDetails/updateCapExDetails/{id}", id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(capExDetail))).andExpect(status().isOk()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.expenseDetails").value(expenseDetails)).andExpect(jsonPath("$.mode").value(mode)).andExpect(jsonPath("$.amount").value(amount)).andExpect(jsonPath("$.comment").value(comment)).andExpect(jsonPath("$.gstBill").value(gstBill)).andExpect(jsonPath("$.paidBy").value(paidBy)).andReturn();
//
//        verify(capExDetailsService, times(1)).updateCapExDetailsById(eq(id), any(CapExDetails.class));
//        verifyNoMoreInteractions(capExDetailsService);
//    }


    @Test
    public void testDeleteCapExDetailsById() throws Exception {


        when(capExDetailsService.deleteCapExDetailsById(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/capExDetails/deleteCapExDetails/{id}", id)).andExpect(status().isOk()).andExpect(content().string("Data with ID " + id + " deleted successfully")).andReturn();

        verify(capExDetailsService, times(1)).deleteCapExDetailsById(id);
        verifyNoMoreInteractions(capExDetailsService);
    }


    private List<CapExDetails> giveExpDetails() {
        CapExDetails capExDetails = new CapExDetails();
        capExDetails.setId(id);
        capExDetails.setExpenseDetails(expenseDetails);
        capExDetails.setDate(date);
        capExDetails.setMode(mode);
        capExDetails.setAmount(amount);
        capExDetails.setComment(comment);
        capExDetails.setGstBill(gstBill);
        capExDetails.setPaidBy(paidBy);

        List<CapExDetails> list = new ArrayList<>();
        list.add(capExDetails);
        return list;
    }
}
