package com.labourseSearching.Controller;

import com.labourseSearching.Entity.ENUM.GenderType;
import com.labourseSearching.Entity.ENUM.LabourType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {

    /**
     * 👉 Frontend dropdown ke liye
     * 👉 LabourType enum ke saare values return karega
     */
    @GetMapping("/labour-types")
    public List<String> getLabourTypes() {
        return Arrays.stream(LabourType.values())
                .map(Enum::name)
                .toList();
    }

    /**
     * 🔹 Gender types (MALE / FEMALE / ANY)
     */
    @GetMapping("/gender-types")
    public List<String> getGenderTypes() {
        return Arrays.stream(GenderType.values())
                .map(Enum::name)
                .toList();
    }
}