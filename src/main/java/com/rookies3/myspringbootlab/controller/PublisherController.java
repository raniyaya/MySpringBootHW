package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.controller.dto.PublisherDTO;
import com.rookies3.myspringbootlab.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rookies3.myspringbootlab.controller.dto.PublisherDTO.*;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    // ✅ 전체 출판사 목록 조회 (도서 수 포함)
    @GetMapping
    public ResponseEntity<List<SimpleResponse>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    // ✅ ID로 출판사 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherById(id));
    }

    // ✅ 이름으로 출판사 조회
    @GetMapping("/name/{name}")
    public ResponseEntity<SimpleResponse> getPublisherByName(@PathVariable String name) {
        return ResponseEntity.ok(publisherService.getPublisherByName(name));
    }

    // ✅ 출판사 등록
    @PostMapping
    public ResponseEntity<SimpleResponse> createPublisher(@Valid @RequestBody Request request) {
        return ResponseEntity.ok(publisherService.createPublisher(request));
    }

    // ✅ 출판사 수정
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponse> updatePublisher(
            @PathVariable Long id,
            @Valid @RequestBody Request request) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, request));
    }

    // ✅ 출판사 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
