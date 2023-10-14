package oop.analyticsapi.Controller;

import oop.analyticsapi.Domain.Models.RequestBody.CreatePortfolioBody;
import oop.analyticsapi.Domain.Models.RequestBody.DeletePortfolioBody;
import oop.analyticsapi.Domain.Models.RequestBody.UpdatePortfolioBody;
import oop.analyticsapi.Entity.UserPortfolio.UserPortfolioEntity;
import oop.analyticsapi.Enums.ActionEnum;
import oop.analyticsapi.Service.UserPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserPortfolioController {
    private final UserPortfolioService userPortfolioService;

    @Autowired
    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }

    @PostMapping("/portfolio/create")
    public ResponseEntity<String> createPortfolio(@RequestBody CreatePortfolioBody createPortfolioBody) {
        String result = userPortfolioService.createNewPortfolio(
                createPortfolioBody.getUserId(),
                createPortfolioBody.getPortfolioId(),
                createPortfolioBody.getStocks(),
                createPortfolioBody.getCreatedAt()
        );
        if (result.equals("Failed")) return ResponseEntity.internalServerError().body("Something went wrong!");

        return ResponseEntity.ok("Successfully created portfolio.");
    }

    @GetMapping("/portfolio/get-all/{userId}")
    public ResponseEntity<List<UserPortfolioEntity>> getByUser(@PathVariable String userId) {
        List<UserPortfolioEntity> result = userPortfolioService.getAllPortfoliosByUser(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/portfolio/delete")
    public ResponseEntity<String> deletePortfolio(@RequestBody DeletePortfolioBody deletePortfolioBody) {
        System.out.println(deletePortfolioBody.getUserId());
        String result = userPortfolioService.deletePortfolio(
                deletePortfolioBody.getUserId(),
                deletePortfolioBody.getPortfolioId()
        );
        if (result.equals("Failed")) return ResponseEntity.internalServerError().body("Something went wrong!");

        return ResponseEntity.ok("Successfully deleted portfolio.");
    }

    @PostMapping("/portfolio/update")
    public ResponseEntity<String> updatePortfolio(@RequestBody UpdatePortfolioBody updatePortfolioBody) {
        ActionEnum action = ActionEnum
                .getActionFromString(updatePortfolioBody.getAction());

        if (action == ActionEnum.Add
            && updatePortfolioBody.getAddedQuantity().isEmpty()) {
            return ResponseEntity.badRequest().body("Missing addedQuantity field.");
        }
        try {
            String result = userPortfolioService.updatePortfolio(
                    updatePortfolioBody.getUserId(),
                    updatePortfolioBody.getPortfolioId(),
                    updatePortfolioBody.getAction(),
                    updatePortfolioBody.getStock(),
                    updatePortfolioBody.getEditedAt(),
                    //Should never be null
                    updatePortfolioBody.getAddedQuantity()
            );
            if (result.equals("Failed")) return ResponseEntity.internalServerError().body("Something went wrong!");
        } catch(NullPointerException e) {
            return ResponseEntity.badRequest().body("Missing addedQuantity field.");
        }

        return ResponseEntity.ok("Successfully updated portfolio");
    }
}
