package com.adt.expensemanagement.services.implementations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.adt.expensemanagement.util.CapExDetailsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adt.expensemanagement.models.CapExDetails;
import com.adt.expensemanagement.repositories.CapExDetailsRepository;
import com.adt.expensemanagement.services.interfaces.CapExDetailsService;
@Service
public class CapExDetailsServiceImpl implements CapExDetailsService {
	
	@Autowired
	private CapExDetailsRepository capExDetailsRepository;

	@Override
	public CapExDetails createCapExDetails(MultipartFile invoice,CapExDetails capExDetails) {
            try {

                if(!CapExDetailsUtility.validateAmount(capExDetails.getAmount())){
                    throw new IllegalArgumentException("Invalid  Amount Details");
                }
                if(!CapExDetailsUtility.validateCapEx(capExDetails.getPaidBy())){
                    throw new IllegalArgumentException("Invalid Paid Details");
                }
                if(!CapExDetailsUtility.validateCapEx(capExDetails.getMode())){
                    throw new IllegalArgumentException("Invalid Mode Details");
                }
                if(!CapExDetailsUtility.validateCapEx(capExDetails.getComment())){
                    throw new IllegalArgumentException("Invalid Comment");
                }
                if(!CapExDetailsUtility.validateGST(capExDetails.getGstBill())){
                    throw new IllegalArgumentException("Invalid Bill Details");
                }
                if(!CapExDetailsUtility.validateExpense(capExDetails.getExpenseDetails())){
                    throw new IllegalArgumentException("Invalid Expense Details");
                }
            	capExDetails.setInvoice(invoice.getBytes());
            	return capExDetailsRepository.save(capExDetails);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
	}

	@Override
    public List<CapExDetails> getAllCapExDetails() {
        return capExDetailsRepository.findAll();
    }
    @Override
    public CapExDetails getCapExDetailsById(int id) {
        return capExDetailsRepository.findById(id).orElse(null);
    }

  @Override
  public String  updateCapExDetailsById(MultipartFile invoice, CapExDetails capExDetails) {
	  int id = capExDetails.getId();
	  CapExDetails capEx = capExDetailsRepository.findById(id).orElse(null);
      if (capEx != null) {
    	  capEx.setDate(capExDetails.getDate());
    	  capEx.setExpenseDetails(capExDetails.getExpenseDetails());;
    	  capEx.setGstBill(capExDetails.getGstBill());
    	  capEx.setAmount(capExDetails.getAmount());
    	  capEx.setPaidBy(capExDetails.getPaidBy());
    	  capEx.setComment(capExDetails.getComment());
    	  capEx.setMode(capExDetails.getMode());
          if (invoice != null && !invoice.isEmpty()) {
              try {
            	  capEx.setInvoice(invoice.getBytes());
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          return " Capital Expenses Updated Successfully for ID : "+capExDetailsRepository.save(capEx).getId() ;
      } else {
          return "Update Operation Faild";
      }
  }

  @Override
    public boolean deleteCapExDetailsById(int id) {
        Optional<CapExDetails> capExDetailsOptional = capExDetailsRepository.findById(id)    ;
        if (capExDetailsOptional.isPresent()) {
            capExDetailsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
