/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.services.ContactsService;

/**
 * RESTful-ish controller for handling contacts dynamically.
 * Is not totally fool proof, and not consistent but what can you do. 
 */
@Controller
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @PostMapping(path = "/contacts/pendingInvites/{contactId}", produces  = MediaType.APPLICATION_JSON_VALUE)    
    public ResponseEntity<?> sendInvite(Authentication auth, @PathVariable Long contactId) {
        
        int res = contactsService.sendInvite(auth.getName(), contactId);
        
        ResultBuilder builder =  new ResultBuilder().put("id", contactId);
        
        if(res == ContactsService.SUCCESS){
            return builder.put("message", "invite sent").status(HttpStatus.OK).build();
        } else {
            return builder.put("message", "error").status(HttpStatus.BAD_REQUEST).build();
        }
        
    }    

    @DeleteMapping(path = "/contacts/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Object> removeContact(
            Authentication auth,
            @PathVariable Long contactId,
            @RequestParam(required = false) String redirect
    ) {
        contactsService.removeContact(auth.getName(), contactId);

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", contactId);
        result.put("message", "removed successfully");

        return result;
    }

    @DeleteMapping(path = "/contacts/pendingInvites/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> cancelPendingInvite(
            Authentication auth,
            @PathVariable Long contactId,
            @RequestParam(required = false) String redirect
    ) {
        contactsService.cancelPendingInvite(auth.getName(), contactId);

        return new ResultBuilder()
                .put("id", contactId)
                .put("message", "invite cancelled")
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(path = "/contacts/pendingApprovals/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> acceptPendingApproval(
            Authentication auth,
            @PathVariable Long contactId,
            @RequestParam(required = false) String redirect
    ) {
        int res = contactsService.acceptPendingApproval(auth.getName(), contactId);

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", contactId);

        if (res == ContactsService.SUCCESS) {
            return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.GONE);
        }

    }

    @DeleteMapping(path = "/contacts/pendingApprovals/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rejectPendingApproval(
            Authentication auth,
            @PathVariable Long contactId,
            @RequestParam(required = false) String redirect
    ) {
        contactsService.rejectPendingApproval(auth.getName(), contactId);

        return new ResultBuilder()
                .put("id", contactId)
                .put("message", "invite rejected")
                .status(HttpStatus.OK)
                .build();

    }

    private class ResultBuilder {

        private HashMap res;
        private HttpStatus status;

        public ResultBuilder() {
            res = new HashMap<String, Object>();
            status = HttpStatus.OK;
        }

        public ResultBuilder put(String key, Object value) {
            res.put(key, value);
            return this;
        }

        public ResultBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseEntity<HashMap<String, Object>> build() {
            return new ResponseEntity<HashMap<String, Object>>(res, status);
        }
    }
}
