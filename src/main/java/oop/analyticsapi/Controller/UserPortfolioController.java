package oop.analyticsapi.Controller;

import oop.analyticsapi.Domain.Models.PortfolioHistorical;
import oop.analyticsapi.Domain.Models.RequestBody.CreatePortfolioBody;
import oop.analyticsapi.Domain.Models.RequestBody.DeletePortfolioBody;
import oop.analyticsapi.Domain.Models.RequestBody.UpdatePortfolioBody;
import oop.analyticsapi.Domain.ViewModel.AllPortfolios;
import oop.analyticsapi.Domain.ViewModel.PortfolioHistoricals;
import oop.analyticsapi.Entity.PortfolioHistoricals.PortfolioValue;
import oop.analyticsapi.Service.UserPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserPortfolioController {
    private final UserPortfolioService userPortfolioService;

    @Autowired
    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }

    @PostMapping("/portfolio/create")
    public ResponseEntity<String> createPortfolio(@RequestBody CreatePortfolioBody createPortfolioBody) {
        try {
            String result = userPortfolioService.createNewPortfolio(
                    createPortfolioBody.getUserId(),
                    createPortfolioBody.getPortfolioId(),
                    createPortfolioBody.getStocks(),
                    createPortfolioBody.getDescription(),
                    createPortfolioBody.getInitialCapital(),
                    createPortfolioBody.getCreatedAt()
            );
            return ResponseEntity.ok("Successfully created portfolio.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/portfolio/get-all/{userId}")
    public ResponseEntity<AllPortfolios> getByUser(@PathVariable String userId) {
        AllPortfolios result = userPortfolioService.getAllPortfoliosByUser(userId);
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
        try {
            String result = userPortfolioService.updatePortfolio(
                    updatePortfolioBody.getUserId(),
                    updatePortfolioBody.getPortfolioId(),
                    updatePortfolioBody.getAction(),
                    updatePortfolioBody.getStocks(),
                    updatePortfolioBody.getDescription(),
                    updatePortfolioBody.getInitialCapital(),
                    updatePortfolioBody.getEditedAt()
            );
            if (result.equals("Failed")) return ResponseEntity.internalServerError().body("Something went wrong!");
        } catch(NullPointerException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Missing field.");
        }

        return ResponseEntity.ok("Successfully updated portfolio");
    }

    @GetMapping("/portfolio/get-historicals/{userId}/{portfolioId}")
    public ResponseEntity<PortfolioHistoricals> getPortfolioHistoricals(@PathVariable String userId, @PathVariable String portfolioId) {
        List<PortfolioValue> result = userPortfolioService.getPortfolioHistoricals(portfolioId, userId);
        List<List<Object>> temp = new ArrayList<>();
        for (PortfolioValue pv : result) {
            ZonedDateTime zonedDateTime = pv.getDate().atStartOfDay(ZoneId.of("UTC"));
            // Get the epoch timestamp in seconds
            long epochTimestamp = zonedDateTime.toInstant().toEpochMilli();
            List<Object> staticList = new ArrayList<>();
            staticList.add(epochTimestamp);
            staticList.add(pv.getValue());

            temp.add(staticList);
        }
        Map<String, List<List<Object>>> map = new HashMap<>();
        map.put(portfolioId, temp);
        PortfolioHistoricals finalRes = PortfolioHistoricals.builder().data(map).build();
        return ResponseEntity.ok(finalRes);
    }
}
