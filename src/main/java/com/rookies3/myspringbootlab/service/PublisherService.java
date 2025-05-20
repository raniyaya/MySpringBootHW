package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.PublisherDTO;
import com.rookies3.myspringbootlab.entity.Publisher;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.exception.ErrorCode;
import com.rookies3.myspringbootlab.repository.BookRepository;
import com.rookies3.myspringbootlab.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.rookies3.myspringbootlab.controller.dto.PublisherDTO.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public List<SimpleResponse> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(publisher -> {
                    Long count = bookRepository.countByPublisherId(publisher.getId());
                    return SimpleResponse.fromEntityWithCount(publisher, count);
                }).collect(Collectors.toList());
    }

    public Response getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID: " + id));
        Long count = (long) (publisher.getBooks() != null ? publisher.getBooks().size() : 0);
        return Response.fromEntity(publisher);
    }

    public SimpleResponse getPublisherByName(String name) {
        Publisher publisher = publisherRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with name: " + name));
        Long count = bookRepository.countByPublisherId(publisher.getId());
        return SimpleResponse.fromEntityWithCount(publisher, count);
    }

    @Transactional
    public SimpleResponse createPublisher(Request request) {
        if (publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.PUBLISHER_NAME_DUPLICATE.formatMessage(request.getName()));
        }

        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .establishedDate(request.getEstablishedDate())
                .address(request.getAddress())
                .build();

        Publisher saved = publisherRepository.save(publisher);
        return SimpleResponse.fromEntityWithCount(saved, 0L);
    }

    @Transactional
    public SimpleResponse updatePublisher(Long id, Request request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID: " + id));

        if (!publisher.getName().equals(request.getName()) &&
                publisherRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.PUBLISHER_NAME_DUPLICATE.formatMessage(request.getName()));
        }

        publisher.setName(request.getName());
        publisher.setEstablishedDate(request.getEstablishedDate());
        publisher.setAddress(request.getAddress());

        return SimpleResponse.fromEntityWithCount(publisher, bookRepository.countByPublisherId(id));
    }

    @Transactional
    public void deletePublisher(Long id) {
        Long count = bookRepository.countByPublisherId(id);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PUBLISHER_HAS_BOOKS.formatMessage(id, count));
        }

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID: " + id));

        publisherRepository.delete(publisher);
    }
}
