//package com.pl.edu.wut.master.thesis.bug.controller;
//
//import com.pl.edu.wut.master.thesis.bug.mapper.ComponentInfoMapper;
//import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
//import com.pl.edu.wut.master.thesis.bug.service.ComponentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/components")
//@RequiredArgsConstructor
//public class ComponentController {
//
//    private final ComponentService componentService;
//    private final ComponentInfoMapper componentMapper;
//
//    /** Return all components */
//    @GetMapping
//    public ResponseEntity<List<BugComponentResponse>> getAllComponents() {
//        List<BugComponentResponse> dtos = componentService.getAllComponents().stream()
//                .map(componentMapper::toResponse)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
//
//    /** Create a new component */
//    @PostMapping
//    public ResponseEntity<BugComponentResponse> createComponent(
//            @RequestBody BugComponentRequest dto) {
//        ComponentInfo entity = componentMapper.toEntity(dto);
//        ComponentInfo saved = componentService.saveComponent(entity);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(componentMapper.toResponse(saved));
//    }
//
//    /** Delete a component by its database ID */
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteComponent(@PathVariable Long id) {
//        componentService.deleteComponent(id);
//    }
//}
