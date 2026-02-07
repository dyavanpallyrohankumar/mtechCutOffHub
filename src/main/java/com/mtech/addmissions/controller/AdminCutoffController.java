package com.mtech.addmissions.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/cutoffs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCutoffController {

}
