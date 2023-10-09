package oop.analyticsapi.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPortfolioController {
    @GetMapping("/portfolio/create")
    public ResponseEntity<String> createPortfolio() {
        return ResponseEntity.ok("IM HEALTHY!");
    }

    @GetMapping("/portfolio/get-all-by-user")
    public ResponseEntity<String> getByUser() {
        return ResponseEntity.ok("IM HEALTHY!");
    }

    @GetMapping("/portfolio/delete")
    public ResponseEntity<String> deletePortfolio() {
        return ResponseEntity.ok("IM HEALTHY!");
    }

    @GetMapping("/portfolio/update")
    public ResponseEntity<String> updatePortfolio() {
        return ResponseEntity.ok("IM HEALTHY!");
    }
}
