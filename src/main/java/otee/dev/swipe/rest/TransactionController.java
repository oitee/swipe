package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import otee.dev.swipe.api.AddExpenseRequest;
import otee.dev.swipe.service.TransactionService;
import otee.dev.swipe.util.ServiceResponse;

import java.util.HashMap;
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
        Map<String, String> response = transactionService
                .addExpense(addExpenseRequest.getGroupId(), addExpenseRequest.getUsername(), addExpenseRequest.getAmount(), addExpenseRequest.getDescription());
        HttpStatus status;
        if (Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response.get("message"), status);
    }
    @GetMapping("/groups/{groupId}/users/{username}/dues")
    public ResponseEntity<String> getTransactionStatus(@PathVariable Long groupId, @PathVariable String username){
        if(ServiceResponse.isNullOrBlank(groupId)){
            return new ResponseEntity<String>("GroupId is empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(username)){
            return new ResponseEntity<String>("Username is empty", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = transactionService.getTransactionStatusForAUser(groupId, username);
        HttpStatus status;
        if (Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response.get("message"), status);

    }
    @GetMapping("/groups/{groupId}/dues")
    public ResponseEntity<Map> getTransactionStatusForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "GroupId is empty");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = transactionService.getTransactionStatusForGroup(groupId);
        HttpStatus status;
        if (Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/groups/{groupId}/settlements")
    public ResponseEntity<Map> getSettlementsForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "GroupId is empty");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response =transactionService.getSettlementsForGroup(groupId);
        HttpStatus status;
        if (Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response, status);
    }
}
