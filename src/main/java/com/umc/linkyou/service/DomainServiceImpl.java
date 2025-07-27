package com.umc.linkyou.service;

import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.exception.GeneralException;
import com.umc.linkyou.awsS3.AwsS3Service;
import com.umc.linkyou.converter.AwsS3Converter;
import com.umc.linkyou.converter.DomainConverter;
import com.umc.linkyou.domain.classification.Domain;
import com.umc.linkyou.repository.classification.DomainRepository;
import com.umc.linkyou.web.dto.DomainDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService{
    private final DomainRepository domainRepository;
    private final AwsS3Service awsS3Service;

    @Override
    @Transactional
    public DomainDTO.DomainReponseDTO createDomain(Long userId, DomainDTO.DomainRequestDTO dto, MultipartFile image) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = AwsS3Converter.toImageUrl(image, awsS3Service);
        }
        Domain domain = Domain.builder()
                .name(dto.getName())
                .domainTail(dto.getDomainTail())
                .imageUrl(imageUrl)
                .build();
        domain = domainRepository.save(domain);
        return DomainConverter.toDomainResponseDTO(domain.getName(), domain.getDomainTail(), domain.getImageUrl());
    }// 도메인 생성

    @Override
    @Transactional
    public DomainDTO.DomainReponseDTO updateDomain(Long userId, DomainDTO.DomainRequestDTO dto, MultipartFile image) {
        Domain domain = domainRepository.findById(dto.getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus._DOMAIN_NOT_FOUND));
        // null 아닌 필드만 업데이트
        if (dto.getName() != null) {
            domain.setName(dto.getName());
        }
        if (dto.getDomainTail() != null) {
            domain.setDomainTail(dto.getDomainTail());
        }
        if (image != null && !image.isEmpty()) {
            // 기존 이미지가 있을 경우 S3에서 삭제
            if (domain.getImageUrl() != null) {
                awsS3Service.deleteFileByUrl(domain.getImageUrl());  // URL에서 파일명 추출 후 삭제 실행
            }
            // 새 이미지 업로드 후 URL 세팅
            String imageUrl = AwsS3Converter.toImageUrl(image, awsS3Service);
            domain.setImageUrl(imageUrl);
        }

        domainRepository.save(domain);

        return DomainDTO.DomainReponseDTO.builder()
                .name(domain.getName())
                .domainTail(domain.getDomainTail())
                .imageUrl(domain.getImageUrl())
                .build();
    }
//도메인 수정

}
