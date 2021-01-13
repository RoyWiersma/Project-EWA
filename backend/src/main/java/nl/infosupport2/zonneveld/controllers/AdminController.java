package nl.infosupport2.zonneveld.controllers;


import nl.infosupport2.zonneveld.entities.GP;
import nl.infosupport2.zonneveld.exceptions.ItemNotFoundException;
import nl.infosupport2.zonneveld.repositories.GPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private GPRepository repository;

    @GetMapping("get")
    public Iterable<GP> getAllGp() {
        return repository.findAll();
    }

    @PatchMapping("setAdmin")
    public GP setAdmin(@RequestBody GP gp) {
        GP admin = repository.findById(gp.getId()).orElseThrow(() -> new ItemNotFoundException("User not found"));
        admin.setIsAdmin(true);
        return repository.save(admin);
    }

    @PatchMapping("setRemoveAdmin")
    public GP getAllGp(@RequestBody GP admin) {
        GP gp = repository.findById(admin.getId()).orElseThrow(() -> new ItemNotFoundException("User not found"));
        gp.setIsAdmin(false);
        return repository.save(gp);
    }
}
