package com.webnest.internship.controller;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.service.InternshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class EnterpriseController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipService internshipService;


}
