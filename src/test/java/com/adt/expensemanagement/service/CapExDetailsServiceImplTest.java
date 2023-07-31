package com.adt.expensemanagement.service;

import com.adt.expensemanagement.models.CapExDetails;
import com.adt.expensemanagement.repositories.CapExDetailsRepository;
import com.adt.expensemanagement.services.implementations.CapExDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CapExDetailsServiceImplTest {
    private static final Integer id = 1;
    private static final LocalDate date = LocalDate.parse("2023-05-16");
    private static final String expenseDetails = "Chair";
    private static final String gstBill = "23ded23";
    private static final Integer amount = 2221;
    private static final String paidBy = "DJ";
    private static final String comment = "new";
    private static final String mode = "google";

    @InjectMocks
    private CapExDetailsServiceImpl capExDetailsService;

    @Mock
    private CapExDetailsRepository capExDetailsRepository;

    @Test
    public void getAllCapExDetailsTest() {
        CapExDetails capExDetail = giveExpDetails();
        List<CapExDetails> capExDetails = Collections.singletonList(capExDetail);
        when(capExDetailsRepository.findAll()).thenReturn(capExDetails);
        assertEquals(capExDetails, capExDetailsService.getAllCapExDetails());

    }

    @Test
    public void getCapExDetailsByIdTest() {
        CapExDetails capExDetail = giveExpDetails();
        when(capExDetailsRepository.findById(id)).thenReturn(Optional.of(capExDetail));
        assertEquals(capExDetail, capExDetailsService.getCapExDetailsById(id));
    }


    @Test
    public void should_return_null_find_capExDetail_by_id() {
        when(capExDetailsRepository.findById(id)).thenReturn(Optional.empty());
        assertNull(capExDetailsService.getCapExDetailsById(id));
    }


    @Test
    public void createCapExDetailsTest() {
        CapExDetails capExDetail = giveExpDetails();
        capExDetail.setId(0);
        when(capExDetailsRepository.save(capExDetail)).thenReturn(capExDetail);
        assertEquals(capExDetail, capExDetailsService.createCapExDetails(capExDetail));
    }



    @Test
    public void updateCapExDetailsByIdTest() {
        CapExDetails capExDetail = giveExpDetails();
        when(capExDetailsRepository.findById(capExDetail.getId())).thenReturn(Optional.of(capExDetail));
        when(capExDetailsRepository.save(capExDetail)).thenReturn(capExDetail);
        assertEquals(capExDetail, capExDetailsService.updateCapExDetailsById(capExDetail.getId(), capExDetail));
    }



    @Test
    public void deleteCapExDetailsByIdTest() {
        CapExDetails capExDetail = giveExpDetails();
        when(capExDetailsRepository.findById(capExDetail.getId())).thenReturn(Optional.of(capExDetail));
        doNothing().when(capExDetailsRepository).deleteById(id);
        assertEquals(true, capExDetailsService.deleteCapExDetailsById(id));
    }

    @Test
    public void should_return_null_delete_user_by_id() {
        CapExDetails capExDetail = giveExpDetails();
        when(capExDetailsRepository.findById(capExDetail.getId())).thenReturn(Optional.empty());
        doNothing().when(capExDetailsRepository).deleteById(id);
        assertEquals(false,capExDetailsService.deleteCapExDetailsById(id));
    }


    private CapExDetails giveExpDetails() {
        CapExDetails capExDetails = new CapExDetails();
        capExDetails.setId(id);
        capExDetails.setExpenseDetails(expenseDetails);
        capExDetails.setDate(date);
        capExDetails.setMode(mode);
        capExDetails.setAmount(amount);
        capExDetails.setComment(comment);
        capExDetails.setGstBill(gstBill);
        capExDetails.setPaidBy(paidBy);
        return capExDetails;
    }

}
