package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import otee.dev.swipe.api.AddExpenseRequest;
import otee.dev.swipe.service.TransactionService;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Map;

@Controller
public class TransactionController {
    TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    @PostMapping("/add-expense/")
    public ResponseEntity<String> addExpense(@RequestBody AddExpenseRequest addExpenseRequest){
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getGroupId())){
            return new ResponseEntity<String>("Group Id is empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getDescription())){
            return new ResponseEntity<String>("Expense Description is empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getUsername())){
            return new ResponseEntity<String>("Username is empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getAmount())){
            return new ResponseEntity<String>("Expense amount is empty", HttpStatus.BAD_REQUEST);
        }
        if(addExpenseRequest.getAmount() <= 0.D){
            return new ResponseEntity<String>("Expense amount cannot be negative or zero", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = transactionService.addExpense(addExpenseRequest.getGroupId(), addExpenseRequest.getUsername(), addExpenseRequest.getAmount(), addExpenseRequest.getDescription());
        HttpStatus status;
        if (Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response.get("message"), status);
    }
}
