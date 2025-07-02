package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otee.dev.swipe.api.AddExpenseRequest;
import otee.dev.swipe.dto.TransactionDtos;
import otee.dev.swipe.service.TransactionService;
import otee.dev.swipe.util.ServiceResponse;

@RestController
public class TransactionController {
    TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    @PostMapping("/add-expense/")
    public ResponseEntity<TransactionDtos.DefaultDto> addExpense(@RequestBody AddExpenseRequest addExpenseRequest){
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getGroupId())){
            TransactionDtos.DefaultDto badResponse = new TransactionDtos.DefaultDto(false, "Group Id is empty");
            return new ResponseEntity<TransactionDtos.DefaultDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getDescription())){
            TransactionDtos.DefaultDto badResponse = new TransactionDtos.DefaultDto(false, "Expense Description is empty");
            return new ResponseEntity<TransactionDtos.DefaultDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getUsername())){
            TransactionDtos.DefaultDto badResponse = new TransactionDtos.DefaultDto(false, "Username is empty");
            return new ResponseEntity<TransactionDtos.DefaultDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(addExpenseRequest.getAmount())){
            TransactionDtos.DefaultDto badResponse = new TransactionDtos.DefaultDto(false, "Expense amount is empty");
            return new ResponseEntity<TransactionDtos.DefaultDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(addExpenseRequest.getAmount() <= 0.D){
            TransactionDtos.DefaultDto badResponse = new TransactionDtos.DefaultDto(false, "Expense amount cannot be negative or zero");
            return new ResponseEntity<TransactionDtos.DefaultDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionDtos.DefaultDto response = transactionService
                .addExpense(addExpenseRequest.getGroupId(), addExpenseRequest.getUsername(), addExpenseRequest.getAmount(), addExpenseRequest.getDescription());
        HttpStatus status;
        if (response.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
    @GetMapping("/groups/{groupId}/users/{username}/dues")
    public ResponseEntity<TransactionDtos.UserDuesDto> getTransactionStatus(@PathVariable Long groupId, @PathVariable String username){
        if(ServiceResponse.isNullOrBlank(groupId)){
            TransactionDtos.UserDuesDto badResponse = new TransactionDtos.UserDuesDto(false, "GroupId is empty");
            return new ResponseEntity<TransactionDtos.UserDuesDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(username)){
            TransactionDtos.UserDuesDto badResponse = new TransactionDtos.UserDuesDto(false, "Username is empty");
            return new ResponseEntity<TransactionDtos.UserDuesDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionDtos.UserDuesDto response = transactionService.getTransactionStatusForAUser(groupId, username);
        HttpStatus status;
        if (response.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);

    }
    @GetMapping("/groups/{groupId}/dues")
    public ResponseEntity<TransactionDtos.GroupDuesDto> getTransactionStatusForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            TransactionDtos.GroupDuesDto badResponse = new TransactionDtos.GroupDuesDto(false, "GroupId is empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionDtos.GroupDuesDto response = transactionService.getTransactionStatusForGroup(groupId);
        HttpStatus status;
        if (response.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/groups/{groupId}/settlements")
    public ResponseEntity<TransactionDtos.SettlementDto> getSettlementsForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            TransactionDtos.SettlementDto badResponse = new TransactionDtos.SettlementDto(false, "GroupId is empty");
            return new ResponseEntity<TransactionDtos.SettlementDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionDtos.SettlementDto response = transactionService.getSettlementsForGroup(groupId);
        HttpStatus status;
        if (response.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<TransactionDtos.SettlementDto>(response, status);
    }
}
